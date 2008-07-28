package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.names.DomainName;
import com.softwarecraftsmen.dns.names.HostName;
import com.softwarecraftsmen.dns.Seconds;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.NS;
import org.jetbrains.annotations.NotNull;

public class NameServerResourceRecord extends AbstractResourceRecord<DomainName, HostName>
{
	public NameServerResourceRecord(final @NotNull DomainName owner, final @NotNull Seconds timeToLive, final @NotNull HostName nameServerHostName)
	{
		super(owner, NS, Internet, timeToLive, nameServerHostName);
	}

	@NotNull
	public static NameServerResourceRecord nameServerResourceRecord(final @NotNull DomainName owner, final @NotNull Seconds timeToLive, final @NotNull HostName nameServerHostName)
	{
		return new NameServerResourceRecord(owner, timeToLive, nameServerHostName);
	}
}
