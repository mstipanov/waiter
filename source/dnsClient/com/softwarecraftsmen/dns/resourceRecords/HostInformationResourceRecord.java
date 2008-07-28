package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.HostInformation;
import com.softwarecraftsmen.dns.names.HostName;
import com.softwarecraftsmen.dns.Seconds;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.HINFO;
import org.jetbrains.annotations.NotNull;

public class HostInformationResourceRecord extends AbstractResourceRecord<HostName, HostInformation>
{
	public HostInformationResourceRecord(final @NotNull HostName owner, final @NotNull Seconds timeToLive, final @NotNull HostInformation hostInformation)
	{
		super(owner, HINFO, Internet, timeToLive, hostInformation);
	}

	@NotNull
	public static HostInformationResourceRecord hostInformationResourceRecord(final @NotNull HostName owner, final @NotNull Seconds timeToLive, final @NotNull HostInformation hostInformation)
	{
		return new HostInformationResourceRecord(owner, timeToLive, hostInformation);
	}
}