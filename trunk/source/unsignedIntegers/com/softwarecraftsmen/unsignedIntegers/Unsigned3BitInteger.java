/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.unsignedIntegers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.String.format;
import static java.util.Locale.UK;

public class Unsigned3BitInteger
{
	@NotNull
	public static Unsigned3BitInteger Zero = new Unsigned3BitInteger(0);
	private final int value;

	private Unsigned3BitInteger(final int value)
	{
		if (value < 0 || value > 7)
		{
			throw new IllegalArgumentException(format(UK, "The value %1$s is not a valid unsigned 3 bit integer", value));
		}
		this.value = value;
	}

	@NotNull
	public static Unsigned3BitInteger unsigned3BitInteger(final int value)
	{
		if (value == 0)
		{
			return Zero;
		}
		return new Unsigned3BitInteger(value);
	}

	@NotNull
	public String toString()
	{
		return format(UK, "%1$s", value);
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

		final Unsigned3BitInteger that = (Unsigned3BitInteger) o;

		return value == that.value;
	}

	public int hashCode()
	{
		return value;
	}
}