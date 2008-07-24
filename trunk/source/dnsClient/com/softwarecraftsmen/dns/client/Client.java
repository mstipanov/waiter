package com.softwarecraftsmen.dns.client;

import com.softwarecraftsmen.Optional;
import com.softwarecraftsmen.dns.*;
import org.jetbrains.annotations.NotNull;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.Set;

public interface Client
{
	@NotNull
	Set<Inet4Address> findAllInternetProtocolVersion4Addresses(final @NotNull HostName hostName);

	@NotNull
	Set<Inet6Address> findAllInternetProtocolVersion6Addresses(final @NotNull HostName hostName);

	@NotNull
	Set<MailExchange> findMailServers(final @NotNull DomainName domainName);

	@NotNull
	Optional<Text> findText(final @NotNull HostName hostName);

	@NotNull
	Optional<HostInformation> findHostInformation(final @NotNull HostName hostName);

	@NotNull
	Optional<HostName> findCanonicalName(final @NotNull HostName hostName);

	@NotNull
	Optional<HostName> findNameFromInternetProtocolVersion4Address(final @NotNull Inet4Address internetProtocolVersion4Address);

	@NotNull
	Optional<HostName> findNameFromInternetProtocolVersion6Address(final @NotNull Inet6Address internetProtocolVersion6Address);

	@NotNull
	Optional<HostName> findNameFromInternetProtocolVersion4Address(@NotNull SerializableInternetProtocolAddress<Inet4Address> internetProtocolVersion4Address);

	@NotNull
	Optional<HostName> findNameFromInternetProtocolVersion6Address(@NotNull SerializableInternetProtocolAddress<Inet6Address> internetProtocolVersion6Address);

	@NotNull
	Set<ServiceInformation> findServiceInformation(final @NotNull ServiceClassLabel serviceClassLabel, final @NotNull ServiceProtocolLabel serviceProtocolLabel, final @NotNull DomainName domainName);
}
