package com.softwarecraftsmen.dns;

import static java.util.Locale.UK;
import static java.lang.String.format;
import static java.lang.Character.isISOControl;

public final class NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException extends IllegalArgumentException
{
	public NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException(final char nonAsciiCharacter)
	{
		super(format(UK, "Non ASCII characters, such as %1$s, are not supported in DNS names", nonAsciiCharacter));
	}

	public static void throwExceptionIfUnsupportedCharacterCode(final char toWrite)
	{
		if (isISOControl(toWrite) || toWrite > 255)
		{
			throw new NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException(toWrite);
		}
	}
}
