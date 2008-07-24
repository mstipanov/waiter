/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.String.format;
import static java.util.Locale.UK;

public class Unsigned3BitInteger
{
	private final int value;

	public Unsigned3BitInteger(final int value)
	{
		if (value < 0 || value > 15)
		{
			throw new IllegalArgumentException(format(UK, "The value %1$s is not a valid unsigned 4 bit integer (nibble)", value));
		}
		this.value = value;
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