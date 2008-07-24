package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.Unsigned32BitInteger;
import com.softwarecraftsmen.dns.Seconds;
import static com.softwarecraftsmen.dns.messaging.Class.Internet;
import com.softwarecraftsmen.dns.messaging.GenericName;
import com.softwarecraftsmen.dns.messaging.GenericResourceRecordData;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import org.jetbrains.annotations.NotNull;

public class GenericResourceRecord extends AbstractResourceRecord<GenericName, GenericResourceRecordData>
{
	public GenericResourceRecord(final @NotNull GenericName owner, final @NotNull InternetClassType internetClassType, final @NotNull com.softwarecraftsmen.dns.messaging.Class qClass, final @NotNull Seconds timeToLive, final @NotNull GenericResourceRecordData data)
	{
		super(owner, internetClassType, qClass, timeToLive, data);
	}

	@NotNull
	public static GenericResourceRecord genericResourceRecord(final @NotNull GenericName owner, final @NotNull InternetClassType internetClassType, final @NotNull Seconds timeToLive, final @NotNull GenericResourceRecordData data)
	{
		return new GenericResourceRecord(owner, internetClassType, Internet, timeToLive, data);
	}
}
