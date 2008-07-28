package com.softwarecraftsmen.dns.names;

import static com.softwarecraftsmen.toString.ToString.string;
import static com.softwarecraftsmen.dns.labels.SimpleLabel.labelsFromDottedName;
import com.softwarecraftsmen.dns.labels.SimpleLabel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DomainName extends AbstractName
{
	public DomainName(final @NotNull List<SimpleLabel> labels)
	{
		super(labels);
	}

	public DomainName(final @NotNull SimpleLabel... labels)
	{
		super(labels);
	}

	@NotNull
	public String toString()
	{
		return string(this, super.toString());
	}

	@NotNull
	public static DomainName domainName(final @NotNull String dottedName)
	{
		return new DomainName
		(
			labelsFromDottedName(dottedName)
		);
	}
}
