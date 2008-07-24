package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import org.jetbrains.annotations.NotNull;

public enum ServiceProtocolLabel implements Label
{
	TCP("_tcp"),
	UDP("_udp");
	private final String label;

	private ServiceProtocolLabel(final @NotNull String label)
	{
		this.label = label;
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
