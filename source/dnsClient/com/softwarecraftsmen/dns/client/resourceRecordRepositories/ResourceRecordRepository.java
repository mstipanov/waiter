/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client.resourceRecordRepositories;

import com.softwarecraftsmen.dns.names.Name;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ResourceRecordRepository
{
	@NotNull
	<T extends Serializable> Set<T> findData(final @NotNull Name name, final @NotNull InternetClassType internetClassType);
}
