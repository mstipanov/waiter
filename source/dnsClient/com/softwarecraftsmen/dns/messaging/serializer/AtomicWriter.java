package com.softwarecraftsmen.dns.messaging.serializer;

import com.softwarecraftsmen.CanNeverHappenException;
import com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger;
import com.softwarecraftsmen.unsignedIntegers.Unsigned32BitInteger;
import com.softwarecraftsmen.unsignedIntegers.Unsigned8BitInteger;
import static com.softwarecraftsmen.dns.NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException.throwExceptionIfUnsupportedCharacterCode;
import com.softwarecraftsmen.dns.Seconds;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AtomicWriter
{
	private final ByteArrayOutputStream stream;

	public AtomicWriter(final @NotNull ByteArrayOutputStream stream)
	{
		this.stream = stream;
	}

	@NotNull
	public byte[] toByteArray()
	{
		return stream.toByteArray();
	}

	public void writeTimeToLiveInSeconds(final @NotNull Unsigned32BitInteger timeToLiveInSeconds)
	{
		writeUnsigned32BitInteger(timeToLiveInSeconds);
	}

	public void writeCharacterString(final @NotNull String characterString)
	{
		writeCharacterString(characterString.toCharArray());
	}

	public void writeCharacterString(final @NotNull char[] characterString)
	{
		if (characterString.length > 255)
		{
			throw new IllegalArgumentException("Maximum length of a character string in DNS is 255 characters");
		}
		writeUnsigned8BitUnsignedInteger(new Unsigned8BitInteger(characterString.length));
		for (char toWrite : characterString)
		{
			writeCharacter(toWrite);
		}
	}

	public void writeCharacter(final char toWrite)
	{
		throwExceptionIfUnsupportedCharacterCode(toWrite);
		stream.write(toWrite & 0xFF);
	}

	public void writeUnsignedSeconds(final @NotNull Seconds seconds)
	{
		seconds.serialize(this);
	}

	public void writeUnsigned32BitInteger(final @NotNull Unsigned32BitInteger unsigned32BitInteger)
	{
		try
		{
			unsigned32BitInteger.write(stream);
		}
		catch (IOException e)
		{
			throw new CanNeverHappenException(e);
		}
	}

	public void writeUnsigned16BitInteger(final @NotNull Unsigned16BitInteger unsigned16BitInteger)
	{
		try
		{
			unsigned16BitInteger.write(stream);
		}
		catch (IOException e)
		{
			throw new CanNeverHappenException(e);
		}
	}

	public void writeUnsigned8BitUnsignedInteger(final @NotNull Unsigned8BitInteger unsigned8BitInteger)
	{
		try
		{
			unsigned8BitInteger.write(stream);
		}
		catch (IOException e)
		{
			throw new CanNeverHappenException(e);
		}
	}

	public void writeBytes(final @NotNull byte[] bytes)
	{
		try
		{
			stream.write(bytes);
		}
		catch (IOException e)
		{
			throw new CanNeverHappenException(e);
		}
	}
}
