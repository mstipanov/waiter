/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client.resourceRecordRepositories;

import org.jetbrains.annotations.NotNull;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.names.Name;
import com.softwarecraftsmen.dns.client.resolvers.DnsResolver;

import java.util.Set;

public class NonCachingResourceRecordRepository implements ResourceRecordRepository
{
	private final DnsResolver dnsResolver;

	public NonCachingResourceRecordRepository(final @NotNull DnsResolver dnsResolver)
	{
		this.dnsResolver = dnsResolver;
	}

	@NotNull
	public <T extends Serializable> Set<T> findData(final @NotNull Name name, final @NotNull InternetClassType internetClassType)
	{
		return dnsResolver.resolve(name, internetClassType).allAnswersMatching(internetClassType);
	}
}
