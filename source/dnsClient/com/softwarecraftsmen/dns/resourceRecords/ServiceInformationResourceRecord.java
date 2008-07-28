package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.Seconds;
import com.softwarecraftsmen.dns.ServiceInformation;
import com.softwarecraftsmen.dns.names.ServiceName;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.SRV;
import org.jetbrains.annotations.NotNull;

public class ServiceInformationResourceRecord extends AbstractResourceRecord<ServiceName, ServiceInformation>
{
	public ServiceInformationResourceRecord(final @NotNull ServiceName owner, final @NotNull Seconds timeToLive, final @NotNull ServiceInformation serviceInformation)
	{
		super(owner, SRV, Internet, timeToLive, serviceInformation);
	}

	@NotNull
	public static ServiceInformationResourceRecord serviceInformationResourceRecord(final @NotNull ServiceName owner, final @NotNull Seconds timeToLive, final @NotNull ServiceInformation serviceInformation)
	{
		return new ServiceInformationResourceRecord(owner, timeToLive, serviceInformation);
	}
}
