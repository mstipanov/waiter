package com.softwarecraftsmen.dns.messaging;

import com.softwarecraftsmen.dns.DomainName;
import com.softwarecraftsmen.dns.HostName;
import com.softwarecraftsmen.dns.Name;
import com.softwarecraftsmen.dns.PointerName;
import com.softwarecraftsmen.dns.ServiceClassLabel;
import com.softwarecraftsmen.dns.ServiceName;
import com.softwarecraftsmen.dns.ServiceProtocolLabel;
import com.softwarecraftsmen.dns.messaging.deserializer.BadlyFormedDnsMessageException;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import static com.softwarecraftsmen.toString.ToString.string;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.String.format;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import static java.util.Locale.UK;

public class GenericName implements Name
{
	private final List<String> labels;

	public GenericName(final @NotNull List<String> labels)
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
		final ServiceClassLabel serviceClassLabel;
		try
		{
			serviceClassLabel = new ServiceClassLabel(labels.get(0));
		}
		catch(IndexOutOfBoundsException exception)
		{
			throw new BadlyFormedDnsMessageException("There must be at least a service class label in a service name", exception);
		}
		final ServiceProtocolLabel serviceProtocolLabel;
		try
		{
			serviceProtocolLabel = ServiceProtocolLabel.valueOf(labels.get(1));
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
		return new ServiceName(serviceClassLabel, serviceProtocolLabel, domainName);
	}

	// TODO: Generic name serialization...
	public void serialize(final @NotNull AtomicWriter writer)
	{
		throw new UnsupportedOperationException("Write some code!");
	}

	@NotNull
	public List<String> toLabels()
	{
		return new ArrayList<String>(labels);
	}

	@NotNull
	public static GenericName genericName(final @NotNull String dottedName)
	{
		final String[] labels = dottedName.split("\\.");
		return new GenericName(asList(labels));
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
