package com.softwarecraftsmen.dns.names;

import static com.softwarecraftsmen.toString.ToString.string;
import static com.softwarecraftsmen.dns.labels.SimpleLabel.labelsFromDottedName;
import com.softwarecraftsmen.dns.labels.SimpleLabel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HostName extends AbstractName
{
	public HostName(final @NotNull List<SimpleLabel> labels)
	{
		super(labels);
	}

	public HostName(final @NotNull SimpleLabel... labels)
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
		return new HostName
		(
			labelsFromDottedName(dottedName)
		);
	}
}
