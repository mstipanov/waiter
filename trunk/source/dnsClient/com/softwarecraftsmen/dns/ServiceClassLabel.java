package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServiceClassLabel implements Label
{
	private final String label;

	public ServiceClassLabel(final @NotNull String label)
	{
		if (label.startsWith("_"))
		{
			this.label = label;
			if (label.length() == 1)
			{
				throw new IllegalArgumentException("label must be more than _");
			}
		}
		else
		{
			if (label.length() == 0)
			{
				throw new IllegalArgumentException("label must have a substantive value");
			}
			this.label = "_" + label;
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

		final ServiceClassLabel that = (ServiceClassLabel) o;
		return label.equals(that.label);
	}

	public int hashCode()
	{
		return label.hashCode();
	}

	@NotNull
	public String toString()
	{
		return label;
	}

	@NotNull
	public static ServiceClassLabel serviceClass(final @NotNull String label)
	{
		return new ServiceClassLabel(label);
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeCharacterString(label);
	}

	@NotNull
	public String toStringRepresentation()
	{
		return label;
	}
}
