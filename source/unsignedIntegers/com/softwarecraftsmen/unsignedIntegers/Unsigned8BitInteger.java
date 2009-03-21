package com.softwarecraftsmen.unsignedIntegers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.OutputStream;
import java.io.IOException;
import static java.lang.String.format;
import static java.util.Locale.UK;

public class Unsigned8BitInteger
{
	public static final Unsigned8BitInteger Zero = new Unsigned8BitInteger(0);
	private final int value;

	public Unsigned8BitInteger(final int value)
	{
		if (value < 0 || value > 255)
		{
			throw new IllegalArgumentException(format(UK, "The value %1$s is not a valid unsigned 8 bit integer", value));
		}
		this.value = value;
	}

	@NotNull
	public static Unsigned8BitInteger unsigned8BitInteger(final int value)
	{
		return new Unsigned8BitInteger(value);
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

		final Unsigned8BitInteger that = (Unsigned8BitInteger) o;
		return value == that.value;
	}

	public int hashCode()
	{
		return value;
	}

	public char toAsciiCharacter()
	{
		return (char)value;
	}

	@NotNull
	public char[] createCharacterArray()
	{
		return new char[value];
	}

	@NotNull
	public Unsigned8BitInteger and(final Unsigned8BitInteger mask)
	{
		return new Unsigned8BitInteger(value & mask.value);
	}

	@NotNull
	public Unsigned8BitInteger not()
	{
		return unsigned8BitInteger(~value & 0xFF);
	}

	public int shiftToSigned32BitInteger(final Unsigned8BitInteger lowerOffset)
	{
		return (value << 8) + lowerOffset.value;
	}

	public void write(final OutputStream stream) throws IOException
	{
		stream.write(value & 0xFF);
	}
}
