package com.softwarecraftsmen.dns.messaging.deserializer;

import com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger;
import com.softwarecraftsmen.unsignedIntegers.Unsigned32BitInteger;
import com.softwarecraftsmen.unsignedIntegers.Unsigned8BitInteger;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger.unsigned16BitInteger;
import org.jetbrains.annotations.NotNull;

public class ByteArrayReader
{
	private final byte[] bytes;
	private int currentPosition;
	private final int count;

	public ByteArrayReader(final @NotNull byte[] bytes)
	{
		this.bytes = bytes;
		this.currentPosition = 0;
		this.count = bytes.length;
	}

	@NotNull
	public Unsigned8BitInteger readUnsigned8BitInteger()
	{
		return new Unsigned8BitInteger(read());
	}

	@NotNull
	public Unsigned16BitInteger readUnsigned16BitInteger()
	{
		return unsigned16BitInteger((read() << 8) + read());
	}

	@NotNull
	public Unsigned32BitInteger readUnsigned32BitInteger()
	{
		return readUnsigned16BitInteger().leftShift16().add(readUnsigned16BitInteger());
	}

	public char readByteAsAsciiCharacter()
	{
		return readUnsigned8BitInteger().toAsciiCharacter();
	}

	@NotNull
	public String readAsciiString(final @NotNull Unsigned8BitInteger numberOfCharacters)
	{
		return readAsciiString(numberOfCharacters.createCharacterArray());
	}

	@NotNull
	public String readAsciiString(final Unsigned16BitInteger numberOfCharacters)
	{
		return readAsciiString(numberOfCharacters.createCharacterArray());
	}

	private String readAsciiString(final char[] asciiCharacters)
	{
		for (int characterIndex = 0; characterIndex < asciiCharacters.length; characterIndex++)
		{
			asciiCharacters[characterIndex] = readByteAsAsciiCharacter();
		}
		return String.valueOf(asciiCharacters);
	}

	@NotNull
	public byte[] readRawByteArray(final Unsigned16BitInteger numberOfBytes)
	{
		final byte[] byteArray = numberOfBytes.createByteArray();
		for (int byteIndex = 0; byteIndex < byteArray.length; byteIndex++)
		{
			byteArray[byteIndex] = rawRead();
		}
		return byteArray;
	}

	public void moveToOffset(final int offset)
	{
		this.currentPosition = offset;
	}

    private int read()
    {
	    if (currentPosition >= count)
	    {
		    throw new IllegalStateException("You've read beyond the end of the byte array");
	    }
	    return (rawRead() & 0xFF);
    }

	private byte rawRead()
	{
		return bytes[currentPosition++];
	}

	public int currentPosition()
	{
		return currentPosition;
	}
}
