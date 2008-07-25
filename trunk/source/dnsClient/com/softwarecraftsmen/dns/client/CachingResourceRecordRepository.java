/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client;

import com.softwarecraftsmen.dns.Name;
import com.softwarecraftsmen.dns.Seconds;
import com.softwarecraftsmen.dns.HostName;
import com.softwarecraftsmen.dns.client.resolvers.DnsResolver;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.resourceRecords.ResourceRecord;
import org.jetbrains.annotations.NotNull;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Set;
import static java.lang.System.currentTimeMillis;

public class CachingResourceRecordRepository implements ResourceRecordRepository
{
	private final DnsResolver dnsResolver;
	private final Seconds maximumTimeToLivePermitted;
	private final SortedMap<Long, ResourceRecord<? extends Name, ? extends Serializable>> bestBeforeTimesForResourceRecords;

	public CachingResourceRecordRepository(final @NotNull DnsResolver dnsResolver, final @NotNull Seconds maximumTimeToLivePermitted)
	{
		this.dnsResolver = dnsResolver;
		this.maximumTimeToLivePermitted = maximumTimeToLivePermitted;
		bestBeforeTimesForResourceRecords = new TreeMap<Long, ResourceRecord<? extends Name, ? extends Serializable>>();
	}

	@NotNull
	public <T extends Serializable> Set<T> findData(final @NotNull Name name, final @NotNull InternetClassType internetClassType)
	{
		return dnsResolver.resolve(name, internetClassType).allAnswersMatching(internetClassType);
	}

	private void add(final @NotNull ResourceRecord<? extends Name, ? extends Serializable> resourceRecord)
	{
		final long expiresAtSystemTime = resourceRecord.expiresAtSystemTime(maximumTimeToLivePermitted);
		if (expiresAtSystemTime < currentTimeMillis())
		{
			return;
		}
		bestBeforeTimesForResourceRecords.put(expiresAtSystemTime, resourceRecord);
	}

	@NotNull
	private <T extends Serializable> Set<T> findResourceRecords(final @NotNull HostName hostName, final @NotNull InternetClassType internetClassType)
	{
		return null;
	}

	// TODO: Replace
	// TODO: Look ups!
	// TODO: Decide whether to dead head on each request or once a second?

	private void removeStaleEntries()
	{
		final SortedMap<Long, ResourceRecord<? extends Name, ? extends Serializable>> map = bestBeforeTimesForResourceRecords.headMap(currentTimeMillis());
		for (Long key : map.keySet())
		{
			bestBeforeTimesForResourceRecords.remove(key);
		}
	}
}
