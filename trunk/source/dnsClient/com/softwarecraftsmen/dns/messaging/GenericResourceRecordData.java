package com.softwarecraftsmen.dns.messaging;

import static com.softwarecraftsmen.toString.ToString.string;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class GenericResourceRecordData implements Serializable
{
	private final byte[] data;

	public GenericResourceRecordData(final @NotNull byte[] data)
	{
		this.data = data;
	}

	public String toString()
	{
		return string(this, Arrays.toString(data));
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeBytes(data);
	}
}
