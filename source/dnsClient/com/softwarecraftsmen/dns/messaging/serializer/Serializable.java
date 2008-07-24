/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging.serializer;

import org.jetbrains.annotations.NotNull;

public interface Serializable
{
	void serialize(final @NotNull AtomicWriter writer);
}
