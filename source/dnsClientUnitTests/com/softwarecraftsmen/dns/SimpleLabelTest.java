/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.dns.ServiceLabel.serviceLabel;
import static com.softwarecraftsmen.dns.ServiceProtocolLabel.TCP;
import static com.softwarecraftsmen.dns.SimpleLabel.Empty;
import com.softwarecraftsmen.dns.SimpleLabel.LabelsCanNotBeLongerThan63CharactersException;
import com.softwarecraftsmen.dns.SimpleLabel.LabelsCanNotContainPeriodsException;
import static com.softwarecraftsmen.dns.SimpleLabel.simpleLabel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class SimpleLabelTest
{
	@Test(expected = LabelsCanNotContainPeriodsException.class)
	public void labelsCanNotContainPeriods()
	{
		simpleLabel("www.softwarecraftsmen.com");
	}

	@Test(expected = LabelsCanNotBeLongerThan63CharactersException.class)
	public void labelsCanNotBeLongerThan63Bytes()
	{
		simpleLabel("01234567890123456789012345678901234567890123456789012345678901234");
	}

	@Test(expected = NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException.class)
	public void labelsCanNotContainNonAsciiCharacters()
	{
		simpleLabel("\u0100");
	}

	@Test(expected = NonAsciiAndControlCharactersAreNotSupportedInCharacterStringsException.class)
	public void labelsCanNotContainControlCharacters()
	{
		simpleLabel("\u0000");
	}

	@Test
	public void emptyIsEmpty()
	{
		assertThat(Empty.isEmpty(), is(true));
	}

	@Test
	public void notEmptyIsNotEmpty()
	{
		assertThat(simpleLabel("www").isEmpty(), is(false));
	}

	@Test
	public void toStringRepresentationHasAnUnderscore()
	{
		assertThat(simpleLabel("www").toStringRepresentation(), is("www"));
	}

	@Test
	public void length()
	{
		assertThat(simpleLabel("www").length(), is(3));
	}

	@Test
	public void toServiceLabel()
	{
		assertThat(simpleLabel("_http").toServiceLabel(), is(serviceLabel("_http")));
	}

	@Test
	public void toServiceProtocolLabelPresent()
	{
		assertThat(simpleLabel("tcp").toServiceProtocolLabel(), is(TCP));
		assertThat(simpleLabel("_tcp").toServiceProtocolLabel(), is(TCP));
	}

	@Test(expected = IllegalStateException.class)
	public void toServiceProtocolLabelUnrecognised()
	{
		simpleLabel("notrecognised").toServiceProtocolLabel();
	}

	@Test
	public void compareToSortsSimpleLabelLast()
	{
		final SimpleLabel empty = Empty;
		final SimpleLabel a = simpleLabel("a");
		final SimpleLabel b = simpleLabel("b");

		assertThat(empty, lessThanOrEqualTo(empty));
		assertThat(empty, lessThan(a));
		assertThat(a, lessThan(b));
		assertThat(b, greaterThan(a));
	}
}
