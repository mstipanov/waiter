package com.softwarecraftsmen.unsignedIntegers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.String.format;
import static java.lang.Character.MAX_VALUE;
import static java.util.Locale.UK;
import java.io.OutputStream;
import java.io.IOException;

import static com.softwarecraftsmen.unsignedIntegers.Unsigned3BitInteger.unsigned3BitInteger;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned4BitInteger.unsigned4BitInteger;

public class Unsigned16BitInteger implements Comparable<Unsigned16BitInteger>
{
	public int toSigned32BitInteger()
	{
		return value;
	}

	private final int value;
	@NotNull
	public static final Unsigned16BitInteger Zero = new Unsigned16BitInteger(0);
	@NotNull
	public static final Unsigned16BitInteger One = new Unsigned16BitInteger(1);
	@NotNull
	public static Unsigned16BitInteger Four = new Unsigned16BitInteger(4);
	@NotNull
	public static Unsigned16BitInteger Sixteen = new Unsigned16BitInteger(16);
	@NotNull
	public static Unsigned16BitInteger MaximumValue = new Unsigned16BitInteger(MAX_VALUE);

	private static final int TopBit = 15;

	private Unsigned16BitInteger(final int value)
	{
		if (value < 0 || value > 65536)
		{
			throw new IllegalArgumentException(format(UK, "The value %1$s is not a valid unsigned 16 bit integer", value));
		}
		this.value = value;
	}

	@NotNull
	public static Unsigned16BitInteger unsigned16BitInteger(final int value)
	{
		switch (value)
		{
			case 0:
				return Zero;
			case 1:
				return One;
			case 4:
				return Four;
			case 16:
				return Sixteen;
			default:
				return new Unsigned16BitInteger(value);
		}
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

		final Unsigned16BitInteger that = (Unsigned16BitInteger) o;
		return value == that.value;
	}

	public int hashCode()
	{
		return value;
	}

	@NotNull
	public Unsigned32BitInteger leftShift16()
	{
		 return new Unsigned32BitInteger(value << 16);
	}

	public long toLong()
	{
		return value;
	}

	@NotNull
	public char[] createCharacterArray()
	{
		return new char[value];
	}

	@NotNull
	public byte[] createByteArray()
	{
		return new byte[value];
	}

	public boolean getBitIetf(final int zeroBasedIetfBitNumber)
	{
		return getBitPowerOfTwo(TopBit - zeroBasedIetfBitNumber);
	}

	@NotNull
	public Unsigned4BitInteger getUnsigned4BitIntegerIetf(final int zeroBasedIetfBitNumberStart)
	{
		return unsigned4BitInteger(getBitsIetf(zeroBasedIetfBitNumberStart, 0x0F));
	}

	@NotNull
	public Unsigned3BitInteger getThreeBitsIetf(final int zeroBasedIetfBitNumberStart)
	{
		return unsigned3BitInteger(getBitsIetf(zeroBasedIetfBitNumberStart, 0x07));
	}

	@NotNull
	public Unsigned16BitInteger set4BitsIetf(final Unsigned4BitInteger unsigned4BitInteger, final int zeroBasedIetfBitNumberStart)
	{
		return set4BitsPowerOfTwo(unsigned4BitInteger, TopBit - zeroBasedIetfBitNumberStart);
	}

	@NotNull
	public Unsigned16BitInteger setBitIetf(final boolean bitOnOrOff, final int zeroBasedIetfBitNumber)
	{
		return setBitPowerOfTwo(bitOnOrOff, TopBit - zeroBasedIetfBitNumber);
	}

	@NotNull
	public Unsigned16BitInteger setBitPowerOfTwo(final boolean bitOnOrOff, final int zeroBasedPowerOfTwoBitNumber)
	{
		return unsigned16BitInteger(value | (bitOnOrOff ? 1 << zeroBasedPowerOfTwoBitNumber : 0));
	}

	public void write(final @NotNull OutputStream stream) throws IOException
	{
		stream.write((value >>> 8) & 0xFF);
		stream.write(value & 0xFF);
	}

	@NotNull
	private Unsigned16BitInteger set4BitsPowerOfTwo(final Unsigned4BitInteger unsigned4BitInteger, final int zeroBasedPowerOfTwoBitNumber)
	{
		return unsigned16BitInteger(set4Bits(unsigned4BitInteger, zeroBasedPowerOfTwoBitNumber));
	}

	private boolean getBitPowerOfTwo(final int zeroBasedPowerOfTwoBitNumber)
	{
		final int mask = 1 << zeroBasedPowerOfTwoBitNumber;
		return (value & mask) == mask;
	}

	private int getBitsIetf(final int zeroBasedIetfBitNumberStart, final int mask)
	{
		return getBitsPowerOfTwo(TopBit - zeroBasedIetfBitNumberStart, mask);
	}

	private int getBitsPowerOfTwo(final int zeroBasedPowerOfTwoBitNumberStart, final int mask)
	{
		final int mask2 = mask << zeroBasedPowerOfTwoBitNumberStart;
		final int value2 = value & mask2;
		return value2 >> zeroBasedPowerOfTwoBitNumberStart;
	}

	private int set4Bits(final Unsigned4BitInteger unsigned4BitInteger, final int zeroBasedPowerOfTwoBitNumber) {return value | (unsigned4BitInteger.toUnsigned16BitInteger().value << zeroBasedPowerOfTwoBitNumber);}

	public int compareTo(final Unsigned16BitInteger that)
	{
		if (this.value < that.value)
		{
			return -1;
		}
		if (this.value > that.value)
		{
			return 1;
		}
		return 0;
	}

	@NotNull
	public Unsigned16BitInteger increment()
	{
		if (value == MAX_VALUE)
		{
			return Zero;
		}
		return unsigned16BitInteger(value + 1);
	}
}
