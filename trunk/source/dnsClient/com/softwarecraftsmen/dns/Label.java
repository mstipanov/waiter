/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface Label extends Serializable
{
	@NotNull
	String toStringRepresentation();
}
