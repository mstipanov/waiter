package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.names.HostName;
import com.softwarecraftsmen.dns.Seconds;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.CNAME;
import org.jetbrains.annotations.NotNull;

public class CanonicalNameResourceRecord extends AbstractResourceRecord<HostName, HostName>
{
	public CanonicalNameResourceRecord(final @NotNull HostName alias, final @NotNull Seconds timeToLive, final @NotNull HostName canonicalName)
	{
		super(alias, CNAME, Internet, timeToLive, canonicalName);
	}

	@NotNull
	public static CanonicalNameResourceRecord canonicalNameResourceRecord(final @NotNull HostName alias, final @NotNull Seconds timeToLive, final @NotNull HostName canonicalName)
	{
		return new CanonicalNameResourceRecord(alias, timeToLive, canonicalName);
	}
}
