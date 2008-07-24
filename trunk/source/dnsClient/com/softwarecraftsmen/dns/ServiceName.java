package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import static com.softwarecraftsmen.toString.ToString.string;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ServiceName implements Name
{
	private final ServiceClassLabel serviceClassLabel;
	private final ServiceProtocolLabel serviceProtocolLabel;
	private final DomainName domainName;

	public ServiceName(final @NotNull ServiceClassLabel serviceClassLabel, final @NotNull ServiceProtocolLabel serviceProtocolLabel, final @NotNull DomainName domainName)
	{
		this.serviceClassLabel = serviceClassLabel;
		this.serviceProtocolLabel = serviceProtocolLabel;
		this.domainName = domainName;
	}

	@NotNull
	public static ServiceName serviceName(final @NotNull ServiceClassLabel serviceClassLabel, final @NotNull ServiceProtocolLabel serviceProtocolLabel, final @NotNull DomainName domainName)
	{
		return new ServiceName(serviceClassLabel, serviceProtocolLabel, domainName);
	}

	@NotNull
	public String toString()
	{
		return string(this, serviceClassLabel, serviceProtocolLabel, domainName);
	}

	@SuppressWarnings({"RedundantIfStatement"})
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final ServiceName that = (ServiceName) o;

		if (!domainName.equals(that.domainName))
		{
			return false;
		}
		if (!serviceClassLabel.equals(that.serviceClassLabel))
		{
			return false;
		}
		if (serviceProtocolLabel != that.serviceProtocolLabel)
		{
			return false;
		}
		return true;
	}

	public int hashCode()
	{
		int result;
		result = serviceClassLabel.hashCode();
		result = 31 * result + serviceProtocolLabel.hashCode();
		result = 31 * result + domainName.hashCode();
		return result;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		serviceClassLabel.serialize(writer);
		serviceProtocolLabel.serialize(writer);
		domainName.serialize(writer);
	}

	@NotNull
	public List<String> toLabels()
	{
		return new ArrayList<String>()
		{{
			add(serviceClassLabel.toStringRepresentation());
			add(serviceProtocolLabel.toStringRepresentation());
			addAll(domainName.toLabels());
		}};
	}
}
