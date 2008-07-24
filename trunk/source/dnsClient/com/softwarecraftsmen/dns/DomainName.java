package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DomainName extends AbstractName
{
	public DomainName(final @NotNull List<String> labels)
	{
		super(labels);
	}

	public DomainName(final @NotNull String ... labels)
	{
		super(labels);
	}

	@NotNull
	public String toString()
	{
		return ToString.string(this, super.toString());
	}

	@NotNull
	public static DomainName domainName(final @NotNull String dottedName)
	{
		final String[] strings = dottedName.split("\\.");
		return new DomainName(strings);
	}
}
