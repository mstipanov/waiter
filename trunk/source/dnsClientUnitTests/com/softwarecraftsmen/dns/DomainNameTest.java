/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.AbstractName.EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd;
import com.softwarecraftsmen.dns.AbstractName.LabelsCanNotBeLongerThan63CharactersException;
import com.softwarecraftsmen.dns.AbstractName.LabelsCanNotContainPeriodsException;
import com.softwarecraftsmen.dns.AbstractName.TooManyLabelsException;
import static com.softwarecraftsmen.dns.DomainName.domainName;
import static com.softwarecraftsmen.dns.messaging.serializer.ByteSerializer.serialize;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.io.IOException;

public class DomainNameTest
{
	@Test
	public void constructsWithTerminalEmptyLabel()
	{
		assertThat(new DomainName("softwarecraftsmen", "com"), hasToString(equalTo("softwarecraftsmen.com.")));
	}

	@Test
	public void doesNotConstructsWithEmptyTerminalLabelWhenSpecified()
	{
		assertThat(new DomainName("softwarecraftsmen", "com", ""), hasToString(equalTo("softwarecraftsmen.com.")));
	}

	@Test(expected = EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd.class)
	public void emptyLabelsAreNotAllowedExceptTerminally()
	{
		new DomainName("softwarecraftsmen", "", "com", "");
	}

	@Test(expected = LabelsCanNotBeLongerThan63CharactersException.class)
	public void labelsCanNotBeLongerThan63Bytes()
	{
		new DomainName("01234567890123456789012345678901234567890123456789012345678901234");
	}

	@Test(expected = NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException.class)
	public void labelsCanNotContainNonAsciiCharacters()
	{
		new DomainName("com", "\u0100");
	}

	@Test(expected = NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException.class)
	public void labelsCanNotContainControlCharacters()
	{
		new DomainName("com", "\u0000");
	}

	@Test(expected = LabelsCanNotContainPeriodsException.class)
	public void labelsCanNotContainPeriods()
	{
		new DomainName("softwarecraftsmen.com");
	}

	@Test(expected = TooManyLabelsException.class)
	public void tooManyLabels()
	{
		final String[] tooManyLabels = new String[128];
		for(int index = 0; index < tooManyLabels.length; index++)
		{
			tooManyLabels[index] = "somelabel";
		}
		new DomainName(tooManyLabels);
	}

	@Test(expected = IllegalArgumentException.class)
	public void noLabels()
	{
		new DomainName();
	}

	@Test
	public void nameHelperStaticMethodWorks()
	{
		assertThat(domainName("softwarecraftsmen.com"), hasToString(equalTo("softwarecraftsmen.com.")));
	}

	@Test
	public void nameHelperStaticMethodWorksWithRootDomain()
	{
		assertThat(domainName("softwarecraftsmen.com."), hasToString(equalTo("softwarecraftsmen.com.")));
	}

	@Test
	public void serializeProducesTheExpectedBytes() throws IOException
	{
		assertThat(serialize(domainName("mydomain.com")), is(equalTo(new byte[]
		{
			0x03, 0x77,	0x77, 0x77,
			0x08, 0x6D, 0x79, 0x64,	0x6F, 0x6D,	0x61, 0x69,	0x6E,
			0x03, 0x63, 0x6F, 0x6D,
            0x00
		})));
	}
}