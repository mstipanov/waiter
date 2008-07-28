package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.names.HostName;
import com.softwarecraftsmen.dns.Seconds;
import com.softwarecraftsmen.dns.Text;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.TXT;
import org.jetbrains.annotations.NotNull;

public class TextResourceRecord extends AbstractResourceRecord<HostName, Text>
{
	public TextResourceRecord(final @NotNull HostName owner, final @NotNull Seconds timeToLive, final @NotNull Text text)
	{
		super(owner, TXT, Internet, timeToLive, text);
	}

	public static TextResourceRecord textResourceRecord(final @NotNull HostName owner, final @NotNull Seconds timeToLive, final @NotNull Text text)
	{
		return new TextResourceRecord(owner, timeToLive, text);
	}
}
