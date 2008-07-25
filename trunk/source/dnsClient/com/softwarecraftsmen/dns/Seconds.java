/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.unsignedIntegers.Unsigned32BitInteger;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Locale.UK;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

public class Seconds implements Serializable, Comparable<Seconds>
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

	public int compareTo(final @NotNull Seconds that)
	{
		return value.compareTo(that.value);
	}

	@NotNull
	public Seconds chooseSmallestValue(final @NotNull Seconds that)
	{
		switch (compareTo(that))
		{
			case -1:
				return this;
			case 0:
				return this;
			case 1:
				return that;
			default:
				return that;
		}
	}

	@NotNull
	public static Seconds currentTime()
	{
		return seconds(currentTimeMillis() / 1000);
	}

	@NotNull
	public Seconds add(final @NotNull Seconds offset)
	{
		return seconds(this.value.add(offset.value));
	}
}
