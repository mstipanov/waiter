package com.softwarecraftsmen.dns.messaging;

import static com.softwarecraftsmen.ToString.string;
import com.softwarecraftsmen.dns.*;
import com.softwarecraftsmen.dns.messaging.deserializer.BadlyFormedDnsMessageException;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;
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
}
