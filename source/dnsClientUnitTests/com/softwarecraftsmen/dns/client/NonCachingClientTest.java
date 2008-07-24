package com.softwarecraftsmen.dns.client;

import com.softwarecraftsmen.Optional;
import static com.softwarecraftsmen.Unsigned16BitInteger.Zero;
import static com.softwarecraftsmen.Unsigned16BitInteger.unsigned16BitInteger;
import com.softwarecraftsmen.dns.*;
import static com.softwarecraftsmen.dns.DomainName.domainName;
import static com.softwarecraftsmen.dns.HostInformation.hostInformation;
import static com.softwarecraftsmen.dns.HostName.hostName;
import static com.softwarecraftsmen.dns.MailExchange.mailExchange;
import static com.softwarecraftsmen.dns.Seconds.seconds;
import static com.softwarecraftsmen.dns.SerializableInternetProtocolAddress.serializableInternetProtocolVersion4Address;
import static com.softwarecraftsmen.dns.SerializableInternetProtocolAddress.serializableInternetProtocolVersion6Address;
import static com.softwarecraftsmen.dns.ServiceClassLabel.serviceClass;
import static com.softwarecraftsmen.dns.ServiceInformation.serviceInformation;
import static com.softwarecraftsmen.dns.ServiceProtocolLabel.TCP;
import static com.softwarecraftsmen.dns.Text.text;
import com.softwarecraftsmen.dns.client.resolvers.DnsResolver;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.messaging.Message;
import static com.softwarecraftsmen.dns.messaging.Message.NoResourceRecords;
import com.softwarecraftsmen.dns.messaging.MessageHeader;
import static com.softwarecraftsmen.dns.messaging.MessageHeaderFlags.reply;
import static com.softwarecraftsmen.dns.messaging.MessageId.messageId;
import com.softwarecraftsmen.dns.messaging.Question;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import static com.softwarecraftsmen.dns.resourceRecords.CanonicalNameResourceRecord.canonicalNameResourceRecord;
import static com.softwarecraftsmen.dns.resourceRecords.HostInformationResourceRecord.hostInformationResourceRecord;
import static com.softwarecraftsmen.dns.resourceRecords.InternetProtocolVersion4AddressResourceRecord.internetProtocolVersion4AddressResourceRecord;
import static com.softwarecraftsmen.dns.resourceRecords.InternetProtocolVersion6AddressResourceRecord.internetProtocolVersion6AddressResourceRecord;
import static com.softwarecraftsmen.dns.resourceRecords.MailExchangeResourceRecord.mailExchangeResourceRecord;
import static com.softwarecraftsmen.dns.resourceRecords.PointerResourceRecord.pointerResourceRecord;
import com.softwarecraftsmen.dns.resourceRecords.ResourceRecord;
import static com.softwarecraftsmen.dns.resourceRecords.ServiceInformationResourceRecord.serviceInformationResourceRecord;
import static com.softwarecraftsmen.dns.resourceRecords.TextResourceRecord.textResourceRecord;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparisons.greaterThan;
import org.jetbrains.annotations.NotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NonCachingClientTest
{
	private static final HostName CanonicalName = hostName("www.l.google.com");
	private static final HostName AliasName = hostName("www.google.com");
	private static final DomainName SomeDomainName = domainName("google.com");
	private static final HostName ResolvedReverseLookupHostName = hostName("nf-in-f104.google.com");
	private static final SerializableInternetProtocolAddress<Inet4Address> ExampleInternetProtocolVersion4Address = serializableInternetProtocolVersion4Address(64, 233, 183, 104);
	private static final SerializableInternetProtocolAddress<Inet6Address> ExampleInternetProtocolVersion6Address = serializableInternetProtocolVersion6Address(0x4321, 0x0, 0x1, 0x2, 0x3, 0x4, 0x567, 0x89ab);

	@Test
	public void findAllInternetProtocolVersion4AddressesForNonCanonicalName()
	{
		dnsResolver.program(internetProtocolVersion4AddressResourceRecord(CanonicalName, seconds(1000), serializableInternetProtocolVersion4Address(1, 2, 3, 4)));
		dnsResolver.program(internetProtocolVersion4AddressResourceRecord(CanonicalName, seconds(1000), serializableInternetProtocolVersion4Address(2, 2, 3, 4)));
		dnsResolver.program(canonicalNameResourceRecord(AliasName, seconds(1000), CanonicalName));
		final Set<Inet4Address> version4Addresses = nonCachingClient.findAllInternetProtocolVersion4Addresses(AliasName);
		assertThat(version4Addresses.size(), is(greaterThan(0)));
	}

	@Test
	public void findAllInternetProtocolVersion4AddressesForCanonicalName()
	{
		dnsResolver.program(internetProtocolVersion4AddressResourceRecord(CanonicalName, seconds(1000), serializableInternetProtocolVersion4Address(1, 2, 3, 4)));
		dnsResolver.program(internetProtocolVersion4AddressResourceRecord(CanonicalName, seconds(1000), serializableInternetProtocolVersion4Address(2, 2, 3, 4)));
		final Set<Inet4Address> version4Addresses = nonCachingClient.findAllInternetProtocolVersion4Addresses(CanonicalName);
		assertThat(version4Addresses.size(), is(greaterThan(0)));
	}

	@Test
	public void findAllInternetProtocolVersion6AddressesForNonCanonicalName()
	{

		dnsResolver.program(internetProtocolVersion6AddressResourceRecord(CanonicalName, seconds(1000), serializableInternetProtocolVersion6Address(2001, 0x0db8, 0x0000, 0x0000, 0x0000, 0x0000, 0x1428, 0x57ab)));
		dnsResolver.program(internetProtocolVersion6AddressResourceRecord(CanonicalName, seconds(1000), serializableInternetProtocolVersion6Address(2001, 0x0db8, 0x0000, 0x0000, 0x0000, 0x0000, 0x1428, 0x57ac)));
		dnsResolver.program(canonicalNameResourceRecord(AliasName, seconds(1000), CanonicalName));

		final Set<Inet6Address> version6Addresses = nonCachingClient.findAllInternetProtocolVersion6Addresses(AliasName);
		assertThat(version6Addresses.size(), is(greaterThan(0)));
	}

	@Test
	public void findAllInternetProtocolVersion6AddressesForCanonicalName()
	{
		dnsResolver.program(internetProtocolVersion6AddressResourceRecord(CanonicalName, seconds(1000), serializableInternetProtocolVersion6Address(2001, 0x0db8, 0x0000, 0x0000, 0x0000, 0x0000, 0x1428, 0x57ab)));
		dnsResolver.program(internetProtocolVersion6AddressResourceRecord(CanonicalName, seconds(1000), serializableInternetProtocolVersion6Address(2001, 0x0db8, 0x0000, 0x0000, 0x0000, 0x0000, 0x1428, 0x57ac)));
		final Set<Inet6Address> version6Addresses = nonCachingClient.findAllInternetProtocolVersion6Addresses(CanonicalName);
		assertThat(version6Addresses.size(), is(greaterThan(0)));
	}

	@Test
	public void findMailServers()
	{
		dnsResolver.program(mailExchangeResourceRecord(SomeDomainName, seconds(1000), mailExchange(unsigned16BitInteger(10), hostName("smtp1.google.com"))));
		dnsResolver.program(mailExchangeResourceRecord(SomeDomainName, seconds(1000), mailExchange(unsigned16BitInteger(10), hostName("smtp2.google.com"))));
		final Set<MailExchange> mailExchanges = nonCachingClient.findMailServers(SomeDomainName);
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
		final Text text = text("hello=world");
		dnsResolver.program(textResourceRecord(CanonicalName, seconds(1000), text));
		final Optional<Text> texts = nonCachingClient.findText(CanonicalName);
		assertThat(texts.value(), is(equalTo(text)));
	}

	@Test
	public void findHostInformation()
	{
		final HostInformation hostInformation = hostInformation("i386", "Linux");
		dnsResolver.program(hostInformationResourceRecord(CanonicalName, seconds(1000), hostInformation));
		final Optional<HostInformation> hostInformations = nonCachingClient.findHostInformation(CanonicalName);
		assertThat(hostInformations.value(), is(equalTo(hostInformation)));
	}

	@Test
	public void findCanonicalName()
	{
		dnsResolver.program(canonicalNameResourceRecord(AliasName, seconds(1000), CanonicalName));
		final Optional<HostName> canonicalName = nonCachingClient.findCanonicalName(AliasName);
		assertThat(canonicalName.value(), is(equalTo(CanonicalName)));
	}

	// Does not have any values returned. Odd.
	// How do we distinguish a canonical name from no name - ?SOA?
	@Test
	public void findCanonicalNameHasAValueIfNameIsAlsoCanonical()
	{
		final Optional<HostName> canonicalName = nonCachingClient.findCanonicalName(CanonicalName);
		assertThat(canonicalName.size(), is(equalTo(0)));
	}

	@Test
	public void findNameFromInternetProtocolVersion4Address()
	{
		dnsResolver.program(pointerResourceRecord(PointerName.pointerName(ExampleInternetProtocolVersion4Address.address), seconds(1000), ResolvedReverseLookupHostName));
		final Optional<HostName> resolvedName = nonCachingClient.findNameFromInternetProtocolVersion4Address(ExampleInternetProtocolVersion4Address.address);
		assertThat(resolvedName.value(), is(equalTo(ResolvedReverseLookupHostName)));
	}

	@Test
	public void findNameFromInternetProtocolVersion4AddressFromSerializable()
	{
		dnsResolver.program(pointerResourceRecord(PointerName.pointerName(ExampleInternetProtocolVersion4Address.address), seconds(1000), ResolvedReverseLookupHostName));
		final Optional<HostName> resolvedName = nonCachingClient.findNameFromInternetProtocolVersion4Address(ExampleInternetProtocolVersion4Address);
		assertThat(resolvedName.value(), is(equalTo(ResolvedReverseLookupHostName)));
	}

	@Test
	public void findNameFromInternetProtocolVersion6Address()
	{
		// 4321:0:1:2:3:4:567:89ab
		dnsResolver.program(pointerResourceRecord(PointerName.pointerName(ExampleInternetProtocolVersion6Address.address), seconds(1000), ResolvedReverseLookupHostName));
		final Optional<HostName> resolvedName = nonCachingClient.findNameFromInternetProtocolVersion6Address(ExampleInternetProtocolVersion6Address.address);
		assertThat(resolvedName.value(), is(equalTo(ResolvedReverseLookupHostName)));
	}

	@Test
	public void findNameFromInternetProtocolVersion6AddressFromSerializable()
	{
		// 4321:0:1:2:3:4:567:89ab
		dnsResolver.program(pointerResourceRecord(PointerName.pointerName(ExampleInternetProtocolVersion6Address.address), seconds(1000), ResolvedReverseLookupHostName));
		final Optional<HostName> resolvedName = nonCachingClient.findNameFromInternetProtocolVersion6Address(ExampleInternetProtocolVersion6Address);
		assertThat(resolvedName.value(), is(equalTo(ResolvedReverseLookupHostName)));
	}

	@Test
	public void findServiceInformation()
	{
		final ServiceClassLabel serviceClassLabel = serviceClass("_ldap");
		final ServiceInformation expectedServiceInformation = serviceInformation(unsigned16BitInteger(100), unsigned16BitInteger(10), unsigned16BitInteger(8080), AliasName);
		dnsResolver.program(serviceInformationResourceRecord(ServiceName.serviceName(serviceClassLabel, TCP, SomeDomainName), seconds(1000), expectedServiceInformation));
		final Set<ServiceInformation> actualServiceInformation = nonCachingClient.findServiceInformation(serviceClassLabel, TCP, SomeDomainName);
		assertThat(actualServiceInformation.size(), is(greaterThan(0)));
	}

	@Before
	public void before()
	{
		dnsResolver = new StubDnsResolver();
		nonCachingClient = new NonCachingClient(dnsResolver);
	}

	private static final class StubDnsResolver implements DnsResolver
	{
		private List<ResourceRecord<? extends Name, ? extends Serializable>> resourceRecords;

		public StubDnsResolver()
		{
			resourceRecords = new ArrayList<ResourceRecord<? extends Name, ? extends Serializable>>();
		}

		public void program(final @NotNull ResourceRecord<? extends Name, ? extends Serializable> resourceRecord)
		{
			resourceRecords.add(resourceRecord);
		}

		@NotNull
		public Message resolve(final @NotNull Name name, final @NotNull InternetClassType internetClassType)
		{
			final MessageHeader messageHeader = new MessageHeader(messageId(), reply(true), Zero, unsigned16BitInteger(resourceRecords.size()), Zero, Zero);
			return new Message(messageHeader, new ArrayList<Question>(), resourceRecords, NoResourceRecords, NoResourceRecords);
		}
	}

	private StubDnsResolver dnsResolver;
	private NonCachingClient nonCachingClient;
}