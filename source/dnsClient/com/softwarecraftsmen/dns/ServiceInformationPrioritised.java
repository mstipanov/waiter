/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger;
import org.jetbrains.annotations.NotNull;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import java.util.Iterator;
import java.util.List;

public class ServiceInformationPrioritised implements Iterable<ServiceInformation>
{
	@NotNull
	public static final WeightRandomNumberGenerator RegularRandomNumberGenerator = new RegularWeightRandomNumberGenerator();

	private final WeightRandomNumberGenerator weightRandomNumberGenerator;
	private final List<ServiceInformation> serviceInformationPrioritised;

	public ServiceInformationPrioritised(final @NotNull WeightRandomNumberGenerator weightRandomNumberGenerator, final @NotNull List<ServiceInformation> serviceInformationPrioritised)
	{
		this.weightRandomNumberGenerator = weightRandomNumberGenerator;
		this.serviceInformationPrioritised = serviceInformationPrioritised;
		sort(this.serviceInformationPrioritised);
	}

	@NotNull
	public static Iterable<ServiceInformation> prioritise(final @NotNull ServiceInformation ... serviceInformation)
	{
		return new ServiceInformationPrioritised(RegularRandomNumberGenerator, asList(serviceInformation));
	}

	@NotNull
	public Iterator<ServiceInformation> iterator()
	{
		return unmodifiableList(serviceInformationPrioritised).iterator();
	}

	public interface WeightRandomNumberGenerator
	{
		@NotNull
		Unsigned16BitInteger generate(final @NotNull Unsigned16BitInteger maximum);
	}

	private static final class RegularWeightRandomNumberGenerator implements WeightRandomNumberGenerator
	{
		@NotNull
		public Unsigned16BitInteger generate(final @NotNull Unsigned16BitInteger maximum)
		{
			return null;
		}
	}
}
