/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.Pair;
import com.softwarecraftsmen.dns.names.Name;
import com.softwarecraftsmen.dns.Seconds;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public interface ResourceRecord<S extends Name, T extends Serializable>  extends Serializable
{
	void appendDataIfIs(final @NotNull InternetClassType internetClassType, final @NotNull Set<T> set);

	void addToCache(final @NotNull Seconds maximumTimeToLivePermitted, final @NotNull SortedMap<Seconds, Set<ResourceRecord<? extends Name, ? extends Serializable>>> bestBeforeTimesForResourceRecords, final @NotNull Map<Pair<Name, InternetClassType>, Set<ResourceRecord<? extends Name, ? extends Serializable>>> cache);

	void removeFromCache(final @NotNull Map<Pair<Name, InternetClassType>, Set<ResourceRecord<? extends Name, ? extends Serializable>>> cache);
}
