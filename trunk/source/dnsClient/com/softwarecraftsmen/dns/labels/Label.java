/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.labels;

import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

public interface Label extends Serializable
{
	@NotNull
	String toStringRepresentation();

	boolean isEmpty();

	int length();
}
