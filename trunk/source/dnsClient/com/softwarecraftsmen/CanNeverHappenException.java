/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen;

import org.jetbrains.annotations.NotNull;

public class CanNeverHappenException extends RuntimeException
{
	public CanNeverHappenException(final @NotNull Exception cause)
	{
		super(cause);
	}

	public CanNeverHappenException()
	{}
}
