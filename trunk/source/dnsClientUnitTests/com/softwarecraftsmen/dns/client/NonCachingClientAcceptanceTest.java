package com.softwarecraftsmen.dns.client;

import com.softwarecraftsmen.Optional;
import com.softwarecraftsmen.dns.*;
import static com.softwarecraftsmen.dns.DomainName.domainName;
import static com.softwarecraftsmen.dns.HostName.hostName;
import static com.softwarecraftsmen.dns.SerializableInternetProtocolAddress.serializableInternetProtocolVersion4Address;
import static com.softwarecraftsmen.dns.SerializableInternetProtocolAddress.serializableInternetProtocolVersion6Address;
import static com.softwarecraftsmen.dns.ServiceClassLabel.serviceClass;
import static com.softwarecraftsmen.dns.ServiceProtocolLabel.TCP;
import com.softwarecraftsmen.dns.client.resolvers.SynchronousDnsResolver;
import static com.softwarecraftsmen.dns.client.serverAddressFinders.BindLikeServerAddressFinder.CachedBindLikeServerAddressFinder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparisons.greaterThan;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.Set;

public class NonCachingClientAcceptanceTest
{
	private static final HostName GoogleAliasHostName = hostName("www.google.com");
	private static final HostName GoogleCanonicalHostName = hostName("www.l.google.com");
	private static final DomainName GoogleDomainName = domainName("google.com");
	private static final HostName GooglePointerName = hostName("nf-in-f104.google.com");

	@Test
	public void findAllInternetProtocolVersion4AddressesForNonCanonicalName()
	{
		final Set<Inet4Address> version4Addresses = nonCachingClient.findAllInternetProtocolVersion4Addresses(GoogleAliasHostName);
		assertThat(version4Addresses.size(), is(greaterThan(0)));
	}
	
	@Test
	public void findAllInternetProtocolVersion4AddressesForCanonicalName()
	{
		final Set<Inet4Address> version4Addresses = nonCachingClient.findAllInternetProtocolVersion4Addresses(GoogleCanonicalHostName);
		assertThat(version4Addresses.size(), is(greaterThan(0)));
	}

	@Test
	public void findAllInternetProtocolVersion6AddressesForNonCanonicalName()
	{
		final Set<Inet6Address> version6Addresses = nonCachingClient.findAllInternetProtocolVersion6Addresses(GoogleAliasHostName);
		assertThat(version6Addresses.size(), is(equalTo(0)));
	}

	@Test
	public void findAllInternetProtocolVersion6AddressesForCanonicalName()
	{
		final Set<Inet6Address> version6Addresses = nonCachingClient.findAllInternetProtocolVersion6Addresses(GoogleCanonicalHostName);
		assertThat(version6Addresses.size(), is(equalTo(0)));
	}

	@Test
	public void findMailServers()
	{
		final Set<MailExchange> mailExchanges = nonCachingClient.findMailServers(GoogleDomainName);
		assertThat(mailExchanges.size(), is(greaterThan(0)));
	}

	@Test
	public void findNonExistentMailServers()
	{
		final Set<MailExchange> mailExchanges = nonCachingClient.findMailServers(domainName("doesnotexist.google.com"));
		assertThat(mailExchanges.size(), is(equalTo(0)));
	}

	@Test
	public void findText()
	{
		final Optional<Text> texts = nonCachingClient.findText(GoogleAliasHostName);
		assertThat(texts.size(), is(equalTo(0)));
	}

	@Test
	public void findHostInformation()
	{
		final Optional<HostInformation> hostInformations = nonCachingClient.findHostInformation(GoogleAliasHostName);
		assertThat(hostInformations.size(), is(equalTo(0)));
	}

	@Test
	public void findCanonicalName()
	{
		final Optional<HostName> canonicalName = nonCachingClient.findCanonicalName(GoogleAliasHostName);
		assertThat(canonicalName.value(), is(equalTo(GoogleCanonicalHostName)));
	}

	// Does not have any values returned. Odd.
	@Test
	public void findCanonicalNameHasAValueIfNameIsAlsoCanonical()
	{
		final Optional<HostName> canonicalName = nonCachingClient.findCanonicalName(GoogleCanonicalHostName);
		assertThat(canonicalName.value(), is(equalTo(GoogleCanonicalHostName)));
	}

	@Test
	public void findNameFromInternetProtocolVersion4Address()
	{
		final Optional<HostName> name = nonCachingClient.findNameFromInternetProtocolVersion4Address(serializableInternetProtocolVersion4Address(64, 233, 183, 104).address);
		assertThat(name.value(), is(equalTo(GooglePointerName)));
	}

	@Test
	public void findNameFromInternetProtocolVersion4AddressFromSerializable()
	{
		final Optional<HostName> name = nonCachingClient.findNameFromInternetProtocolVersion4Address(serializableInternetProtocolVersion4Address(64, 233, 183, 104));
		assertThat(name.value(), is(equalTo(GooglePointerName)));
	}

	@Test
	public void findNameFromInternetProtocolVersion6Address()
	{
		// 4321:0:1:2:3:4:567:89ab
		final Optional<HostName> resolvedName = nonCachingClient.findNameFromInternetProtocolVersion6Address(serializableInternetProtocolVersion6Address(0x4321, 0x0, 0x1, 0x2, 0x3, 0x4, 0x567, 0x89ab).address);
		assertThat(resolvedName.size(), is(equalTo(0)));		
	}

	@Test
	public void findNameFromInternetProtocolVersion6AddressFromSerializable()
	{
		// 4321:0:1:2:3:4:567:89ab
		final Optional<HostName> resolvedName = nonCachingClient.findNameFromInternetProtocolVersion6Address(serializableInternetProtocolVersion6Address(0x4321, 0x0, 0x1, 0x2, 0x3, 0x4, 0x567, 0x89ab));
		assertThat(resolvedName.size(), is(equalTo(0)));
	}

	@Test
	public void findServiceInformation()
	{
		final ServiceClassLabel serviceClassLabel = serviceClass("_ldap");
		final Set<ServiceInformation> actualServiceInformation = nonCachingClient.findServiceInformation(serviceClassLabel, TCP, GoogleDomainName);
		assertThat(actualServiceInformation.size(), is(equalTo(0)));
	}

	@Before
	public void before()
	{
		nonCachingClient = new NonCachingClient(new SynchronousDnsResolver(CachedBindLikeServerAddressFinder));
	}

	private NonCachingClient nonCachingClient;
}
