/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.dns.NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException.throwExceptionIfUnsupportedCharacterCode;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.StringWriter;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.Locale.UK;

public abstract class AbstractName implements Name
{
	@NonNls
	private static final char[] EmptyFinalLabel = {};
	private final List<char[]> labels;

	public AbstractName(final @NotNull List<String> labels)
	{
		this(labels.toArray(new String[labels.size()]));
	}

	public AbstractName(final @NotNull String... labels)
	{
		if (labels.length == 0)
		{
			throw new IllegalArgumentException("There must be at least one label");
		}
		this.labels = new ArrayList<char[]>();
		for (int index = 0; index < labels.length; index++)
		{
			final char[] label = labels[index].toCharArray();
			if (label.length > 63)
			{
				throw new LabelsCanNotBeLongerThan63CharactersException(valueOf(label));
			}
			if (label.length == 0 && index != labels.length - 1)
			{
				throw new EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd();
			}
			this.labels.add(label);
			for (char toWrite : label)
			{
				throwExceptionIfUnsupportedCharacterCode(toWrite);
				if (toWrite == '.')
				{
					throw new LabelsCanNotContainPeriodsException(valueOf(label));
				}
			}
			if (label.length != 0 && index == labels.length - 1)
			{
				this.labels.add(EmptyFinalLabel);
			}
		}
		if (this.labels.size() > 128)
		{
			throw new TooManyLabelsException();
		}
	}

	@NotNull
	public String toDotSeparatedString()
	{
		final StringWriter writer = new StringWriter();
		for (char[] label : labels)
		{
			if (label.length == 0)
			{
				break;
			}
			writer.write(valueOf(label));
			writer.write(".");
		}
		return writer.toString();
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		for (char[] label : labels)
		{
			writer.writeCharacterString(label);
		}
	}

	public boolean equals(final @Nullable Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final AbstractName name = (AbstractName) o;
		if (labels.size() != name.labels.size())
		{
			return false;
		}
		for (int index = 0; index < labels.size(); index++)
		{
			final char[] chars = labels.get(index);
			final char[] thatChars = name.labels.get(index);
			if (!Arrays.equals(chars, thatChars))
			{
				return false;
			}
		}
		return true;
	}

	public int hashCode()
	{
		return labels.hashCode();
	}

	@NotNull
	public String toString()
	{
		return toDotSeparatedString();
	}

	public final class LabelsCanNotBeLongerThan63CharactersException extends IllegalArgumentException
	{
		public LabelsCanNotBeLongerThan63CharactersException(final @NotNull String label)
		{
			super(format(UK, "Labels (the strings between dots in a DNS name) can not be longer than 63 character. This label, %1$s, is.", label));
		}
	}

	public final class EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd extends IllegalArgumentException
	{
		public EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd()
		{
			super("Empty labels are not allowed in names except at the end");
		}
	}

	public final class LabelsCanNotContainPeriodsException extends IllegalArgumentException
	{
		public LabelsCanNotContainPeriodsException(final @NotNull String label)
		{
			super(format(UK, "Labels (the strings between dots in a DNS name) can not contain the period character. This label, %1$s, does.", label));
		}
	}

	public final class TooManyLabelsException extends IllegalArgumentException
	{
		public TooManyLabelsException()
		{
			super("More than 128 labels are not allowed in a DNS name");
		}
	}
}
