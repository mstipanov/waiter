package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.names.DomainName;
import com.softwarecraftsmen.dns.Seconds;
import com.softwarecraftsmen.dns.StatementOfAuthority;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.SOA;
import org.jetbrains.annotations.NotNull;

public class StatementOfAuthorityResourceRecord extends AbstractResourceRecord<DomainName, StatementOfAuthority>
{
	public StatementOfAuthorityResourceRecord(final @NotNull DomainName owner, final @NotNull Seconds timeToLive, final @NotNull StatementOfAuthority statementOfAuthority)
	{
		super(owner, SOA, Internet, timeToLive, statementOfAuthority);
	}

	public static StatementOfAuthorityResourceRecord statementOfAuthorityResourceRecord(final @NotNull DomainName owner, final @NotNull Seconds timeToLive, final @NotNull StatementOfAuthority statementOfAuthority)
	{
		return new StatementOfAuthorityResourceRecord(owner, timeToLive, statementOfAuthority);
	}
}