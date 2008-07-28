package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.names.HostName;
import com.softwarecraftsmen.dns.Seconds;
import com.softwarecraftsmen.dns.SerializableInternetProtocolAddress;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.A;
import org.jetbrains.annotations.NotNull;

import java.net.Inet4Address;

public class InternetProtocolVersion4AddressResourceRecord extends AbstractResourceRecord<HostName, SerializableInternetProtocolAddress<Inet4Address>>
{
	public InternetProtocolVersion4AddressResourceRecord(final @NotNull HostName owner, final @NotNull Seconds timeToLive, final @NotNull SerializableInternetProtocolAddress<Inet4Address> internetProtocolVersion4Address)
	{
		super(owner, A, Internet, timeToLive, internetProtocolVersion4Address);
	}

	@NotNull
	public static InternetProtocolVersion4AddressResourceRecord internetProtocolVersion4AddressResourceRecord(final @NotNull HostName owner, final @NotNull Seconds timeToLive, final @NotNull SerializableInternetProtocolAddress<Inet4Address> internetProtocolVersion4Address)
	{
		return new InternetProtocolVersion4AddressResourceRecord(owner, timeToLive, internetProtocolVersion4Address);
	}
}
