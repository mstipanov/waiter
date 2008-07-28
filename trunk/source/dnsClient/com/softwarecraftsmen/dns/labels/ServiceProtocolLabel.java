package com.softwarecraftsmen.dns.labels;

import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;
import static java.util.Locale.UK;

public enum ServiceProtocolLabel implements Label
{
	TCP("_tcp"),
	UDP("_udp");
	private final String value;

	private ServiceProtocolLabel(final @NotNull String value)
	{
		this.value = value;
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
		return true;
	}

	public int length()
	{
		return value.length();
	}

	@NotNull
	public static ServiceProtocolLabel toServiceProtocolLabel(final @NotNull String value)
	{
		final String searchValue = (value.charAt(0) == '_') ? value : "_" + value;
		final ServiceProtocolLabel[] serviceProtocolLabels = values();
		for (ServiceProtocolLabel serviceProtocolLabel : serviceProtocolLabels)
		{
			if (serviceProtocolLabel.value.equals(searchValue))
			{
				return serviceProtocolLabel;
			}
		}
		throw new IllegalArgumentException(format(UK, "The value %1$s is not a valid ServiceProtocolLabel", value));
	}
}
