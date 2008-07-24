/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.resourceRecords;

import com.softwarecraftsmen.dns.Name;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ResourceRecord<S extends Name, T extends Serializable>  extends Serializable
{
	void appendDataIfIs(final @NotNull InternetClassType internetClassType, final @NotNull Set<T> set);
}
