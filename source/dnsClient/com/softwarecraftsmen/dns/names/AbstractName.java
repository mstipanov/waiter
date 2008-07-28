/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.names;

import static com.softwarecraftsmen.dns.labels.SimpleLabel.Empty;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.labels.Label;
import com.softwarecraftsmen.dns.labels.SimpleLabel;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.StringWriter;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;

public abstract class AbstractName implements Name<SimpleLabel>
{
	@NonNls
	private final List<SimpleLabel> labels;

	public AbstractName(final @NotNull List<SimpleLabel> labels)
	{
		this(labels.toArray(new SimpleLabel[labels.size()]));
	}

	public AbstractName(final @NotNull SimpleLabel... labels)
	{
		if (labels.length == 0)
		{
			throw new IllegalArgumentException("There must be at least one label");
		}
		this.labels = new ArrayList<SimpleLabel>();
		for (int index = 0; index < labels.length; index++)
		{
			final SimpleLabel label = labels[index];
			if (label.isEmpty() && index != labels.length - 1)
			{
				throw new EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd();
			}
			this.labels.add(label);
			if (!label.isEmpty() && index == labels.length - 1)
			{
				this.labels.add(Empty);
			}
		}
		if (this.labels.size() > 128)
		{
			throw new TooManyLabelsException();
		}
		throwExceptionIfNameLongerThan255Bytes();
	}

	private void throwExceptionIfNameLongerThan255Bytes()
	{
		int totalLength = 0;
		for (Label label : labels)
		{
			totalLength += label.length();
			totalLength += 1;
		}
		if (totalLength > 255)
		{
			throw new NameIncludingPeriodsAndFinalEmptyLabelCanNotBeMoreThan255Characters();
		}
	}

	@NotNull
	public List<SimpleLabel> toLabels()
	{
		return unmodifiableList(labels);
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		for (Label label : labels)
		{
			label.serialize(writer);
		}
	}

	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final AbstractName that = (AbstractName) o;
		return labels.equals(that.labels);
	}

	public int hashCode()
	{
		return labels.hashCode();
	}

	@NotNull
	public String toString()
	{
		final StringWriter writer = new StringWriter();
		for (Label label : labels)
		{
			if (label.isEmpty())
			{
				break;
			}
			writer.write(label.toString());
			writer.write(".");
		}
		return writer.toString();
	}

	public final class EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd extends IllegalArgumentException
	{
		public EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd()
		{
			super("Empty labels are not allowed in names except at the end");
		}
	}

	public final class TooManyLabelsException extends IllegalArgumentException
	{
		public TooManyLabelsException()
		{
			super("More than 128 labels are not allowed in a DNS name");
		}
	}

	public final class NameIncludingPeriodsAndFinalEmptyLabelCanNotBeMoreThan255Characters extends IllegalArgumentException
	{
		public NameIncludingPeriodsAndFinalEmptyLabelCanNotBeMoreThan255Characters()
		{
			super("More than 255 characters including dots (and the final trailing dot) are in this DNS name");
		}
	}
}
