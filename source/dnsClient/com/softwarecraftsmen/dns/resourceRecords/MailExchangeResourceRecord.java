package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.names.DomainName;
import com.softwarecraftsmen.dns.MailExchange;
import com.softwarecraftsmen.dns.Seconds;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.MX;
import org.jetbrains.annotations.NotNull;

public class MailExchangeResourceRecord extends AbstractResourceRecord<DomainName, MailExchange>
{
	public MailExchangeResourceRecord(final @NotNull DomainName owner, final @NotNull Seconds timeToLive, final @NotNull MailExchange mailExchange)
	{
		super(owner, MX, Internet, timeToLive, mailExchange);
	}

	@NotNull
	public static MailExchangeResourceRecord mailExchangeResourceRecord(final @NotNull DomainName owner, final @NotNull Seconds timeToLive, final @NotNull MailExchange mailExchange)
	{
		return new MailExchangeResourceRecord(owner, timeToLive, mailExchange);
	}
}
