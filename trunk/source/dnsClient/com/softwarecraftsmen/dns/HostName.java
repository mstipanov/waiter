package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.toString.ToString.string;
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
		return string(this, super.toString());
	}

	@NotNull
	public static HostName hostName(final @NotNull String dottedName)
	{
		final String[] strings = dottedName.split("\\.");
		return new HostName(strings);
	}
}
