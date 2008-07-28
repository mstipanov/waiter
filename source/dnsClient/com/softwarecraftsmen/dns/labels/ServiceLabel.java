package com.softwarecraftsmen.dns.labels;

import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServiceLabel implements Label
{
	private final String value;

	public ServiceLabel(final @NotNull String value)
	{
		if (value.startsWith("_"))
		{
			this.value = value;
			if (value.length() == 1)
			{
				throw new IllegalArgumentException("label must be more than _");
			}
			if (value.length() > 15)
			{
				throw new ServiceClassLabelMustBeLessThan15CharactersException();
			}
		}
		else
		{
			if (value.length() == 0)
			{
				throw new IllegalArgumentException("label must have a substantive value");
			}
			if (value.length() > 14)
			{
				throw new ServiceClassLabelMustBeLessThan15CharactersException();
			}
			this.value = "_" + value;
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

		final ServiceLabel that = (ServiceLabel) o;
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
	public static ServiceLabel serviceLabel(final @NotNull String label)
	{
		return new ServiceLabel(label);
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeCharacterString(value);
	}

	@NotNull
	public String toStringRepresentation()
	{
		return value;
	}

	public boolean isEmpty()
	{
		return false;
	}

	public int length()
	{
		return value.length();
	}

	public static class ServiceClassLabelMustBeLessThan15CharactersException extends IllegalArgumentException
	{
		public ServiceClassLabelMustBeLessThan15CharactersException()
		{
			super("A service class value must be less than 14 characters long");
		}
	}
}
