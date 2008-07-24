/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.Unsigned32BitInteger;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Locale.UK;
import static java.lang.String.format;

public class Seconds implements Serializable
{
	private final Unsigned32BitInteger value;

	public Seconds(final @NotNull Unsigned32BitInteger value)
	{
		this.value = value;
	}

	@NotNull
	public static Seconds seconds(final long value)
	{
		return seconds(new Unsigned32BitInteger(value));
	}

	@NotNull
	public static Seconds seconds(final @NotNull Unsigned32BitInteger value)
	{
		return new Seconds(value);
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

		final Seconds seconds = (Seconds) o;
		return value.equals(seconds.value);
	}

	public int hashCode()
	{
		return value.hashCode();
	}

	@NotNull
	public String toString()
	{
		return format(UK, "%1$s second(s)", value);
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeUnsigned32BitInteger(value);
	}
}
