/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Pair<A, B>
{
	private final A name;
	private final B internetClassType;

	public Pair(final @NotNull A name, final @NotNull B internetClassType)
	{
		this.name = name;
		this.internetClassType = internetClassType;
	}

	public boolean equals(final @Nullable Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final Pair key = (Pair) o;
		return internetClassType == key.internetClassType && name.equals(key.name);
	}

	public int hashCode()
	{
		int result;
		result = name.hashCode();
		result = 31 * result + internetClassType.hashCode();
		return result;
	}

	@NotNull
	public String toString()
	{
		return com.softwarecraftsmen.toString.ToString.string(this, name, internetClassType);
	}
}
