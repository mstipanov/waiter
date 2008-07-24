package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.toString.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HostName extends AbstractName
{
	public HostName(final @NotNull List<String> labels)
	{
		super(labels);
	}

	public HostName(final @NotNull String... labels)
	{
		super(labels);
	}

	@NotNull
	public String toString()
	{
		return ToString.string(this, super.toString());
	}

	@NotNull
	public static HostName hostName(final @NotNull String dottedName)
	{
		final String[] strings = dottedName.split("\\.");
		return new HostName(strings);
	}
}
