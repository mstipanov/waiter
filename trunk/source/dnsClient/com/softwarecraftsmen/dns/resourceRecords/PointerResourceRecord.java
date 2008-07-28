package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.names.HostName;
import com.softwarecraftsmen.dns.names.PointerName;
import com.softwarecraftsmen.dns.Seconds;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.PTR;
import org.jetbrains.annotations.NotNull;

public class PointerResourceRecord extends AbstractResourceRecord<PointerName, HostName>
{
	public PointerResourceRecord(final @NotNull PointerName owner, final @NotNull Seconds timeToLive, final @NotNull HostName hostName)
	{
		super(owner, PTR, Internet, timeToLive, hostName);
	}

	@NotNull
	public static PointerResourceRecord pointerResourceRecord(final @NotNull PointerName owner, final @NotNull Seconds timeToLive, final @NotNull HostName hostName)
	{
		return new PointerResourceRecord(owner, timeToLive, hostName);
	}
}
