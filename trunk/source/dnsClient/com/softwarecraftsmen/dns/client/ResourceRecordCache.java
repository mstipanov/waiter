/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client;

import com.softwarecraftsmen.dns.HostName;
import com.softwarecraftsmen.dns.Name;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.resourceRecords.ResourceRecord;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ResourceRecordCache
{
	void add(final @NotNull ResourceRecord<? extends Name, ? extends Serializable> resourceRecord);

	@NotNull
	<T extends Serializable> Set<T> findResourceRecords(final @NotNull HostName hostName, final @NotNull InternetClassType internetClassType);
}
