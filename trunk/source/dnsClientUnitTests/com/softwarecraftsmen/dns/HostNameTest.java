/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.AbstractName.EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd;
import com.softwarecraftsmen.dns.AbstractName.LabelsCanNotBeLongerThan63CharactersException;
import com.softwarecraftsmen.dns.AbstractName.LabelsCanNotContainPeriodsException;
import com.softwarecraftsmen.dns.AbstractName.NameIncludingPeriodsAndFinalEmptyLabelCanNotBeMoreThan255Characters;
import com.softwarecraftsmen.dns.AbstractName.TooManyLabelsException;
import static com.softwarecraftsmen.dns.HostName.hostName;
import static com.softwarecraftsmen.dns.messaging.serializer.ByteSerializer.serialize;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.io.IOException;

public class HostNameTest
{
	@Test
	public void constructsWithTerminalEmptyLabel()
	{
		assertThat(new HostName("www", "softwarecraftsmen", "com"), hasToString(equalTo("HostName(www.softwarecraftsmen.com.)")));
	}

	@Test
	public void doesNotConstructsWithEmptyTerminalLabelWhenSpecified()
	{
		assertThat(new HostName("www", "softwarecraftsmen", "com", ""), hasToString(equalTo("HostName(www.softwarecraftsmen.com.)")));
	}

	@Test(expected = EmptyLabelsAreNotAllowedInNamesExceptAtTheEnd.class)
	public void emptyLabelsAreNotAllowedExceptTerminally()
	{
		new HostName("www", "", "com", "");
	}

	@Test(expected = LabelsCanNotBeLongerThan63CharactersException.class)
	public void labelsCanNotBeLongerThan63Bytes()
	{
		new HostName("01234567890123456789012345678901234567890123456789012345678901234");
	}

	@Test(expected = NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException.class)
	public void labelsCanNotContainNonAsciiCharacters()
	{
		new HostName("www", "\u0100");
	}

	@Test(expected = NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException.class)
	public void labelsCanNotContainControlCharacters()
	{
		new HostName("www", "\u0000");
	}

	@Test(expected = LabelsCanNotContainPeriodsException.class)
	public void labelsCanNotContainPeriods()
	{
		new HostName("www.softwarecraftsmen.com");
	}

	@Test(expected = TooManyLabelsException.class)
	public void tooManyLabels()
	{
		final String[] tooManyLabels = new String[128];
		for(int index = 0; index < tooManyLabels.length; index++)
		{
			tooManyLabels[index] = "somelabel";
		}
		new HostName(tooManyLabels);
	}

	@Test(expected = NameIncludingPeriodsAndFinalEmptyLabelCanNotBeMoreThan255Characters.class)
	public void nameLongerThan255Characters()
	{
		hostName("123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.123456789.12345.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void noLabels()
	{
		new HostName();
	}

	@Test
	public void nameHelperStaticMethodWorks()
	{
		assertThat(hostName("www.softwarecraftsmen.com"), hasToString(equalTo("HostName(www.softwarecraftsmen.com.)")));
	}

	@Test
	public void nameHelperStaticMethodWorksWithRootDomain()
	{
		assertThat(hostName("www.softwarecraftsmen.com."), hasToString(equalTo("HostName(www.softwarecraftsmen.com.)")));
	}

	@Test
	public void serializeProducesTheExpectedBytes() throws IOException
	{
		assertThat(serialize(hostName("www.mydomain.com")), is(equalTo(new byte[]
		{
			0x03, 0x77,	0x77, 0x77,
			0x08, 0x6D, 0x79, 0x64,	0x6F, 0x6D,	0x61, 0x69,	0x6E,
			0x03, 0x63, 0x6F, 0x6D,
            0x00
		})));
	}
}
