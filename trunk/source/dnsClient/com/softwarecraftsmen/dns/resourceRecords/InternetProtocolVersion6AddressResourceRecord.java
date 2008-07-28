package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.names.HostName;
import com.softwarecraftsmen.dns.Seconds;
import com.softwarecraftsmen.dns.SerializableInternetProtocolAddress;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.AAAA;
import org.jetbrains.annotations.NotNull;

import java.net.Inet6Address;

public class InternetProtocolVersion6AddressResourceRecord extends AbstractResourceRecord<HostName, SerializableInternetProtocolAddress<Inet6Address>>
{
	public InternetProtocolVersion6AddressResourceRecord(final @NotNull HostName owner, final @NotNull Seconds timeToLive, final @NotNull SerializableInternetProtocolAddress<Inet6Address> internetProtocolVersion6Address)
	{
		super(owner, AAAA, Internet, timeToLive, internetProtocolVersion6Address);
	}

	@NotNull
	public static InternetProtocolVersion6AddressResourceRecord internetProtocolVersion6AddressResourceRecord(final @NotNull HostName owner, final @NotNull Seconds timeToLive, final @NotNull SerializableInternetProtocolAddress<Inet6Address> internetProtocolVersion6Address)
	{
		return new InternetProtocolVersion6AddressResourceRecord(owner, timeToLive, internetProtocolVersion6Address);
	}
}
