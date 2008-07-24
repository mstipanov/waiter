package com.softwarecraftsmen.dns.client;

import com.softwarecraftsmen.Optional;
import com.softwarecraftsmen.dns.HostName;
import com.softwarecraftsmen.dns.SerializableInternetProtocolAddress;
import org.jetbrains.annotations.NotNull;

import java.net.Inet4Address;
import java.net.Inet6Address;

public abstract class AbstractClient implements Client
{
	@NotNull
	public abstract Optional<HostName> findNameFromInternetProtocolVersion4Address(final @NotNull SerializableInternetProtocolAddress<Inet4Address> internetProtocolVersion4Address);

	@NotNull
	public abstract Optional<HostName> findNameFromInternetProtocolVersion6Address(final @NotNull SerializableInternetProtocolAddress<Inet6Address> internetProtocolVersion6Address);

	@NotNull
	public Optional<HostName> findNameFromInternetProtocolVersion4Address(@NotNull Inet4Address internetProtocolVersion4Address)
	{
		return findNameFromInternetProtocolVersion4Address(new SerializableInternetProtocolAddress<Inet4Address>(internetProtocolVersion4Address));
	}
	
	@NotNull
	public Optional<HostName> findNameFromInternetProtocolVersion6Address(@NotNull Inet6Address internetProtocolVersion6Address)
	{
		return findNameFromInternetProtocolVersion6Address(new SerializableInternetProtocolAddress<Inet6Address>(internetProtocolVersion6Address));
	}
}
