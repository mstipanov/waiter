/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client.serverAddressFinders;

import static com.softwarecraftsmen.dns.SerializableInternetProtocolAddress.InternetProtocolVersion4LocalHost;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;

public class InternetProtocolVersion4LocalHostServerAddressFinder implements ServerAddressFinder
{
	public static final InternetProtocolVersion4LocalHostServerAddressFinder InternetProtocolVersion4LocalHostServerAddressFinderOnPort53 = new InternetProtocolVersion4LocalHostServerAddressFinder(StandardUnicastDnsServerPort);
	private final List<InetSocketAddress> addresses;

	public InternetProtocolVersion4LocalHostServerAddressFinder(final int dnsServerPort)
	{
		addresses = unmodifiableList(new ArrayList<InetSocketAddress>()
		{{
			add(new InetSocketAddress(InternetProtocolVersion4LocalHost.address, dnsServerPort));
		}});
	}

	@NotNull
	public List<InetSocketAddress> find()
	{
		return addresses;
	}
}
