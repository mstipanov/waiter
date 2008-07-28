/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.labels;

import static com.softwarecraftsmen.dns.NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException.throwExceptionIfUnsupportedCharacterCode;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import static com.softwarecraftsmen.dns.labels.ServiceLabel.serviceLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.String.format;
import java.util.ArrayList;
import java.util.List;
import static java.util.Locale.UK;

public class SimpleLabel implements Label, Comparable<SimpleLabel>
{
	@NotNull
	public static final SimpleLabel Empty = new SimpleLabel("");

	private final String value;

	private SimpleLabel(final @NotNull String value)
	{
		if (value.length() > 63)
		{
			throw new LabelsCanNotBeLongerThan63CharactersException(value);
		}
		for (char toWrite : value.toCharArray())
		{
			throwExceptionIfUnsupportedCharacterCode(toWrite);
			if (toWrite == '.')
			{
				throw new LabelsCanNotContainPeriodsException(value);
			}
		}
		this.value = value;
	}

	@NotNull
	public static SimpleLabel simpleLabel(final @NotNull String value)
	{
		if (value.length() == 0)
		{
			return Empty;
		}
		return new SimpleLabel(value);
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

		final SimpleLabel that = (SimpleLabel) o;
		return value.equals(that.value);
	}

	public int hashCode()
	{
		return value.hashCode();
	}

	@NotNull
	public String toString()
	{
		return value;
	}

	@NotNull
	public String toStringRepresentation()
	{
		return value;
	}

	@NotNull
	public static List<SimpleLabel> labelsFromDottedName(final String dottedName)
	{
		final String[] values = dottedName.split("\\.");
		return new ArrayList<SimpleLabel>()
		{{
			for (String value : values)
			{
				add(simpleLabel(value));
			}
		}};
	}

	public boolean isEmpty()
	{
		return value.length() == 0;
	}

	public int length()
	{
		return value.length();
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeCharacterString(value);
	}

	@NotNull
	public ServiceLabel toServiceLabel()
	{
		return serviceLabel(value);
	}

	@NotNull
	public ServiceProtocolLabel toServiceProtocolLabel()
	{
		try
		{
			return ServiceProtocolLabel.toServiceProtocolLabel(value);
		}
		catch(IllegalArgumentException e)
		{
			throw new IllegalStateException(e);
		}
	}

	public int compareTo(final @NotNull SimpleLabel that)
	{
		return this.value.compareTo(that.value);
	}

	public final class LabelsCanNotContainPeriodsException extends IllegalArgumentException
	{
		public LabelsCanNotContainPeriodsException(final @NotNull String value)
		{
			super(format(UK, "Labels (the strings between dots in a DNS name) can not contain the period character. This label, %1$s, does.", value));
		}

		public void throwExceptionIfCharacterIsAPeriod(final char toWrite)
		{
			if (toWrite == '.')
			{
				throw new LabelsCanNotContainPeriodsException(value);
			}
		}
	}

	public static final class LabelsCanNotBeLongerThan63CharactersException extends IllegalArgumentException
	{
		public LabelsCanNotBeLongerThan63CharactersException(final @NotNull String label)
		{
			super(format(UK, "Labels (the strings between dots in a DNS name) can not be longer than 63 character. This label, %1$s, is.", label));
		}
	}
}
