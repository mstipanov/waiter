package com.softwarecraftsmen.dns.names;

import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.labels.Label;
import com.softwarecraftsmen.dns.labels.ServiceLabel;
import com.softwarecraftsmen.dns.labels.ServiceProtocolLabel;
import static com.softwarecraftsmen.toString.ToString.string;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ServiceName implements Name
{
	private final ServiceLabel serviceLabel;
	private final ServiceProtocolLabel serviceProtocolLabel;
	private final DomainName domainName;

	public ServiceName(final @NotNull ServiceLabel serviceLabel, final @NotNull ServiceProtocolLabel serviceProtocolLabel, final @NotNull DomainName domainName)
	{
		this.serviceLabel = serviceLabel;
		this.serviceProtocolLabel = serviceProtocolLabel;
		this.domainName = domainName;
	}

	@NotNull
	public static ServiceName serviceName(final @NotNull ServiceLabel serviceLabel, final @NotNull ServiceProtocolLabel serviceProtocolLabel, final @NotNull DomainName domainName)
	{
		return new ServiceName(serviceLabel, serviceProtocolLabel, domainName);
	}

	@NotNull
	public String toString()
	{
		return string(this, serviceLabel, serviceProtocolLabel, domainName);
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
		if (!serviceLabel.equals(that.serviceLabel))
		{
			return false;
		}
		if (!serviceProtocolLabel.equals(that.serviceProtocolLabel))
		{
			return false;
		}
		return true;
	}

	public int hashCode()
	{
		int result;
		result = serviceLabel.hashCode();
		result = 31 * result + serviceProtocolLabel.hashCode();
		result = 31 * result + domainName.hashCode();
		return result;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		serviceLabel.serialize(writer);
		serviceProtocolLabel.serialize(writer);
		domainName.serialize(writer);
	}

	@NotNull
	public List<Label> toLabels()
	{
		return new ArrayList<Label>()
		{{
			add(serviceLabel);
			add(serviceProtocolLabel);
			addAll(domainName.toLabels());
		}};
	}
}
