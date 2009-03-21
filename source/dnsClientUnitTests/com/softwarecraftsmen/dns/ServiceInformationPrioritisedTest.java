/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.dns.ServiceInformation.serviceInformation;
import com.softwarecraftsmen.dns.ServiceInformationPrioritised.WeightRandomNumberGenerator;
import static com.softwarecraftsmen.dns.ServiceInformationPrioritised.prioritise;
import com.softwarecraftsmen.dns.names.HostName;
import static com.softwarecraftsmen.dns.names.HostName.hostName;
import com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger.*;
import static org.hamcrest.CoreMatchers.is;
import org.jetbrains.annotations.NotNull;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class ServiceInformationPrioritisedTest
{
	private static final Unsigned16BitInteger somePort = One;
	private static final HostName someHostName = hostName("www.google.com.");

	@Test
	public void supportsNotHavingAnyRecords()
	{
		final Iterator<ServiceInformation> informationIterator = prioritise().iterator();
		assertFalse(informationIterator.hasNext());
	}

	@Test
	public void supportsOneRecord()
	{
		final ServiceInformation expected = serviceInformation(Zero, Zero, somePort, someHostName);
		final Iterator<ServiceInformation> informationIterator = prioritise(expected).iterator();
		assertTrue(informationIterator.hasNext());
		assertThat(informationIterator.next(), is(expected));
		assertFalse(informationIterator.hasNext());
	}

	@Test
	public void correctlySortsRecordsOfDifferentPrioritiesWhenAlreadySorted()
	{
		final ServiceInformation lower = serviceInformation(Zero, Zero, somePort, someHostName);
		final ServiceInformation higher = serviceInformation(One, Zero, somePort, someHostName);
		final Iterator<ServiceInformation> informationIterator = prioritise(lower, higher).iterator();
		assertThat(informationIterator.next(), is(lower));
		assertThat(informationIterator.next(), is(higher));
		assertFalse(informationIterator.hasNext());
	}

	@Test
	public void correctlySortsRecordsOfDifferentPrioritiesWhenNotAlreadySorted()
	{
		final ServiceInformation lower = serviceInformation(Zero, Zero, somePort, someHostName);
		final ServiceInformation higher = serviceInformation(One, Zero, somePort, someHostName);
		final Iterator<ServiceInformation> informationIterator = prioritise(higher, lower).iterator();
		assertThat(informationIterator.next(), is(lower));
		assertThat(informationIterator.next(), is(higher));
		assertFalse(informationIterator.hasNext());
	}

	@Test
	public void correctlySortsRecordsOfDifferentPrioritiesWhenTwoAreTheSamePriority()
	{
		final ServiceInformation lowest = serviceInformation(Zero, Zero, somePort, someHostName);
		final ServiceInformation middleAndSame1 = serviceInformation(One, Zero, somePort, someHostName);
		final ServiceInformation middleAndSame2 = serviceInformation(One, Zero, somePort, someHostName);
		final ServiceInformation highest = serviceInformation(Four, Zero, somePort, someHostName);
		final Iterator<ServiceInformation> informationIterator = prioritise(middleAndSame1, highest, lowest, middleAndSame2).iterator();
		assertThat(informationIterator.next(), is(lowest));
		assertThat(informationIterator.next(), is(middleAndSame1));
		assertThat(informationIterator.next(), is(middleAndSame2));
		assertThat(informationIterator.next(), is(highest));
		assertFalse(informationIterator.hasNext());
	}

	@Test
	public void randomlyOrdersByWeightThoseRecordsOfTheSamePriority()
	{
		final ServiceInformation lowest = serviceInformation(Zero, Zero, somePort, someHostName);
		final ServiceInformation middleAndSame1 = serviceInformation(Zero, One, somePort, someHostName);
		final ServiceInformation middleAndSame2 = serviceInformation(Zero, One, somePort, someHostName);
		final ServiceInformation highest = serviceInformation(Zero, Four, somePort, someHostName);

		final Iterator<ServiceInformation> informationIterator = new ServiceInformationPrioritised(new BentWeightRandomNumberGenerator(), new ArrayList<ServiceInformation>()
		{{
			add(highest);
			add(middleAndSame2);
			add(lowest);
			add(middleAndSame1);
		}}).iterator();

		assertThat(informationIterator.next(), is(highest));
		assertThat(informationIterator.next(), is(middleAndSame1));
		assertThat(informationIterator.next(), is(middleAndSame2));
		assertThat(informationIterator.next(), is(lowest));
		assertFalse(informationIterator.hasNext());
	}

	public static final class BentWeightRandomNumberGenerator implements WeightRandomNumberGenerator
	{
		@NotNull
		public Unsigned16BitInteger generate(final @NotNull Unsigned16BitInteger maximum)
		{
			return maximum;
		}
	}
}
