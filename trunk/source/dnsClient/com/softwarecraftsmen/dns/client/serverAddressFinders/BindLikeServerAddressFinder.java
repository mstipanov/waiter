package com.softwarecraftsmen.dns.client.serverAddressFinders;

import static com.softwarecraftsmen.dns.client.serverAddressFinders.InternetProtocolVersion4LocalHostServerAddressFinder.InternetProtocolVersion4LocalHostServerAddressFinderOnPort53;
import static com.softwarecraftsmen.dns.client.serverAddressFinders.PosixServerAddressFinder.CachedPosixServerAddressFinder;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.List;

public class BindLikeServerAddressFinder implements ServerAddressFinder
{
	public static final BindLikeServerAddressFinder CachedBindLikeServerAddressFinder = new BindLikeServerAddressFinder(CachedPosixServerAddressFinder, InternetProtocolVersion4LocalHostServerAddressFinderOnPort53);
	
	private final PosixServerAddressFinder posixServerAddressFinder;
	private final InternetProtocolVersion4LocalHostServerAddressFinder internetProtocolVersion4LocalHostServerAddressFinder;

	public BindLikeServerAddressFinder(final @NotNull PosixServerAddressFinder posixServerAddressFinder, final @NotNull InternetProtocolVersion4LocalHostServerAddressFinder internetProtocolVersion4LocalHostServerAddressFinder)
	{
		this.posixServerAddressFinder = posixServerAddressFinder;
		this.internetProtocolVersion4LocalHostServerAddressFinder = internetProtocolVersion4LocalHostServerAddressFinder;
	}

	@NotNull
	public List<InetSocketAddress> find()
	{
		final List<InetSocketAddress> addressList = posixServerAddressFinder.find();
		if (addressList.isEmpty())
		{
			return internetProtocolVersion4LocalHostServerAddressFinder.find();
		}
		return addressList;
	}
}
