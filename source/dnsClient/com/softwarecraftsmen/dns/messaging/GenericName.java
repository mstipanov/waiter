package com.softwarecraftsmen.dns.messaging;

import com.softwarecraftsmen.dns.names.DomainName;
import com.softwarecraftsmen.dns.names.HostName;
import com.softwarecraftsmen.dns.labels.Label;
import com.softwarecraftsmen.dns.names.Name;
import com.softwarecraftsmen.dns.names.PointerName;
import com.softwarecraftsmen.dns.labels.ServiceLabel;
import com.softwarecraftsmen.dns.names.ServiceName;
import com.softwarecraftsmen.dns.labels.ServiceProtocolLabel;
import com.softwarecraftsmen.dns.labels.SimpleLabel;
import static com.softwarecraftsmen.dns.labels.SimpleLabel.labelsFromDottedName;
import com.softwarecraftsmen.dns.messaging.deserializer.BadlyFormedDnsMessageException;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import static com.softwarecraftsmen.toString.ToString.string;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.String.format;
import java.util.ArrayList;
import java.util.List;
import static java.util.Locale.UK;

public class GenericName implements Name
{
	private final List<SimpleLabel> labels;

	public GenericName(final @NotNull List<SimpleLabel> labels)
	{
		this.labels = labels;
	}

	@NotNull
	public String toString()
	{
		return string(this, labels);
	}

	@NotNull
	public HostName toHostName()
	{
		return new HostName(labels);
	}

	@NotNull
	public DomainName toDomainName()
	{
		return new DomainName(labels);
	}

	@NotNull
	public PointerName toPointerName()
	{
		return new PointerName(labels);
	}

	@NotNull
	public ServiceName toServiceName() throws BadlyFormedDnsMessageException
	{
		final ServiceLabel serviceLabel;
		try
		{
			serviceLabel = labels.get(0).toServiceLabel();
		}
		catch(IndexOutOfBoundsException exception)
		{
			throw new BadlyFormedDnsMessageException("There must be at least a service class label in a service name", exception);
		}
		final ServiceProtocolLabel serviceProtocolLabel;
		try
		{
			serviceProtocolLabel = labels.get(1).toServiceProtocolLabel();
		}
		catch(IndexOutOfBoundsException exception)
		{
			throw new BadlyFormedDnsMessageException("There must be at least a service protocol label in a service name", exception);
		}
		catch(IllegalArgumentException exception)
		{
			throw new BadlyFormedDnsMessageException(format(UK, "The service protocol label %1$s was unrecognised", labels.get(1)), exception);
		}

		final DomainName domainName;
		try
		{
			domainName = new DomainName(labels.subList(2, labels.size()));
		}
		catch(IndexOutOfBoundsException exception)
		{
			throw new BadlyFormedDnsMessageException("There must be at least one domain label in a service name", exception);
		}
		return new ServiceName(serviceLabel, serviceProtocolLabel, domainName);
	}

	// TODO: Generic name serialization...
	public void serialize(final @NotNull AtomicWriter writer)
	{
		throw new UnsupportedOperationException("Write some code!");
	}

	@NotNull
	public List<Label> toLabels()
	{
		return new ArrayList<Label>(labels);
	}

	@NotNull
	public static GenericName genericName(final @NotNull String dottedName)
	{
		return new GenericName(labelsFromDottedName(dottedName));
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

		final GenericName that = (GenericName) o;
		return labels.equals(that.labels);
	}

	public int hashCode()
	{
		return labels.hashCode();
	}
}
