package com.softwarecraftsmen.dns.client.serverAddressFinders;

import com.softwarecraftsmen.dns.SerializableInternetProtocolAddress;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;

public class KnownServerAddressFinder implements ServerAddressFinder
{
	private List<InetSocketAddress> inetSocketAddresses;

	public KnownServerAddressFinder(final @NotNull SerializableInternetProtocolAddress ... addresses)
	{
		final ArrayList<InetSocketAddress> knownAddresses = new ArrayList<InetSocketAddress>();
		for (SerializableInternetProtocolAddress address : addresses)
		{
			knownAddresses.add(new InetSocketAddress(address.address, StandardUnicastDnsServerPort));
		}
		inetSocketAddresses = unmodifiableList(knownAddresses);
	}

	@NotNull
	public List<InetSocketAddress> find()
	{
		return inetSocketAddresses;
	}
}
