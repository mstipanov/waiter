package com.softwarecraftsmen.dns.client;

import com.softwarecraftsmen.CanNeverHappenException;
import com.softwarecraftsmen.Optional;
import com.softwarecraftsmen.dns.*;
import static com.softwarecraftsmen.dns.ServiceName.serviceName;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.*;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.LinkedHashSet;
import java.util.Set;

public class Client
{
	private final ResourceRecordRepository resourceRecordRepository;

	public Client(final @NotNull ResourceRecordRepository resourceRecordRepository)
	{
		this.resourceRecordRepository = resourceRecordRepository;
	}

	@NotNull
	public Optional<HostName> findNameFromInternetProtocolVersion4Address(@NotNull Inet4Address internetProtocolVersion4Address)
	{
		return findNameFromInternetProtocolVersion4Address(new SerializableInternetProtocolAddress<Inet4Address>(internetProtocolVersion4Address));
	}

	@NotNull
	public Set<Inet4Address> findAllInternetProtocolVersion4Addresses(final @NotNull HostName hostName)
	{
		final Set<SerializableInternetProtocolAddress<Inet4Address>> set = resourceRecordRepository.findData(hostName, A);
		final Set<Inet4Address> addresses = new LinkedHashSet<Inet4Address>(set.size());
		for (SerializableInternetProtocolAddress<Inet4Address> inet4AddressSerializableInternetProtocolAddress : set)
		{
			addresses.add(inet4AddressSerializableInternetProtocolAddress.address);
		}
		return addresses;
	}

	@NotNull
	public Optional<HostName> findNameFromInternetProtocolVersion6Address(@NotNull Inet6Address internetProtocolVersion6Address)
	{
		return findNameFromInternetProtocolVersion6Address(new SerializableInternetProtocolAddress<Inet6Address>(internetProtocolVersion6Address));
	}

	@NotNull
	public Set<Inet6Address> findAllInternetProtocolVersion6Addresses(final @NotNull HostName hostName)
	{
		final Set<SerializableInternetProtocolAddress<Inet6Address>> set = resourceRecordRepository.findData(hostName, AAAA);
		final Set<Inet6Address> addresses = new LinkedHashSet<Inet6Address>(set.size());
		for (SerializableInternetProtocolAddress<Inet6Address> inet4AddressSerializableInternetProtocolAddress : set)
		{
			addresses.add(inet4AddressSerializableInternetProtocolAddress.address);
		}
		return addresses;
	}

	@NotNull
	public Set<MailExchange> findMailServers(final @NotNull DomainName domainName)
	{
		return resourceRecordRepository.findData(domainName, MX);
	}

	@NotNull
	public Optional<Text> findText(final @NotNull HostName hostName)
	{
		return findOptionalData(hostName, TXT);
	}

	@NotNull
	public Optional<HostInformation> findHostInformation(final @NotNull HostName hostName)
	{
		return findOptionalData(hostName, HINFO);
	}

	@NotNull
	public Optional<HostName> findCanonicalName(final @NotNull HostName hostName)
	{
		return findOptionalData(hostName, CNAME);
	}

	@NotNull
	public Optional<HostName> findNameFromInternetProtocolVersion4Address(final @NotNull SerializableInternetProtocolAddress<Inet4Address> internetProtocolVersion4Address)
	{
		return findOptionalData(internetProtocolVersion4Address.toInternetProtocolName(), PTR);
	}

	@NotNull
	public Optional<HostName> findNameFromInternetProtocolVersion6Address(final @NotNull SerializableInternetProtocolAddress<Inet6Address> internetProtocolVersion6Address)
	{
		return findOptionalData(internetProtocolVersion6Address.toInternetProtocolName(), PTR);
	}

	@NotNull
	public Set<ServiceInformation> findServiceInformation(final @NotNull ServiceClassLabel serviceClassLabel, final @NotNull ServiceProtocolLabel serviceProtocolLabel, final @NotNull DomainName domainName)
	{
		return resourceRecordRepository.findData(serviceName(serviceClassLabel, serviceProtocolLabel, domainName), SRV);
	}

	@SuppressWarnings({"LoopStatementThatDoesntLoop"})
	private <T extends Serializable> Optional<T> findOptionalData(final Name name, final InternetClassType internetClassType)
	{
		final Set<T> set = resourceRecordRepository.findData(name, internetClassType);
		if (set.isEmpty())
		{
			return com.softwarecraftsmen.Optional.empty();
		}
		else
		{
			for (T serializable : set)
			{
				return new Optional<T>(serializable);
			}
		}
		throw new CanNeverHappenException();
	}
}
