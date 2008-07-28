/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client.resolvers;

import com.softwarecraftsmen.dns.names.Name;
import com.softwarecraftsmen.dns.client.resolvers.protoolClients.ProtocolClient;
import com.softwarecraftsmen.dns.client.resolvers.protoolClients.TcpProtocolClient;
import com.softwarecraftsmen.dns.client.resolvers.protoolClients.UdpProtocolClient;
import com.softwarecraftsmen.dns.client.serverAddressFinders.ServerAddressFinder;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.messaging.Message;
import static com.softwarecraftsmen.dns.messaging.Message.emptyReply;
import static com.softwarecraftsmen.dns.messaging.Message.query;
import static com.softwarecraftsmen.dns.messaging.Question.internetQuestion;
import com.softwarecraftsmen.dns.messaging.deserializer.BadlyFormedDnsMessageException;
import com.softwarecraftsmen.dns.messaging.deserializer.MessageDeserializer;
import com.softwarecraftsmen.dns.messaging.deserializer.TruncatedDnsMessageException;
import static com.softwarecraftsmen.dns.messaging.serializer.ByteSerializer.serialize;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class SynchronousDnsResolver implements DnsResolver
{
	private final ServerAddressFinder serverAddressFinder;
	private static final int MaximumNonEDNS0UdpMessageSize = 512;

	public SynchronousDnsResolver(final @NotNull ServerAddressFinder serverAddressFinder)
	{
		this.serverAddressFinder = serverAddressFinder;
	}

	@SuppressWarnings({"EmptyCatchBlock"})
	@NotNull
	public Message resolve(final @NotNull Name name, final @NotNull InternetClassType internetClassType)
	{
		return resolveAgainstAllServers
		(
			query(internetQuestion(name, internetClassType)),
			new ArrayList<InetSocketAddress>()
			{{
				addAll(serverAddressFinder.find());
				addAll(serverAddressFinder.find());
			}}
		);
	}

	@SuppressWarnings({"EmptyCatchBlock"})
	private Message resolveAgainstAllServers(final Message request, final List<InetSocketAddress> dnsServers)
	{
		for (InetSocketAddress dnsServer : dnsServers)
		{
			try
			{
				return resolve(request, dnsServer, false);
			}
			catch (IOException e)
			{}
			catch (BadlyFormedDnsMessageException e)
			{}
		}
		return emptyReply(request);
	}

	@NotNull
	private Message resolve(final @NotNull Message request, final InetSocketAddress remoteSocketAddress, final boolean forceTcpUse) throws IOException, BadlyFormedDnsMessageException
	{
		final byte[] bytes = serialize(request);
		final boolean useTcp = forceTcpUse || bytes.length > MaximumNonEDNS0UdpMessageSize;
		final ProtocolClient protocolClient = useTcp ? new TcpProtocolClient(null, remoteSocketAddress, 1, 100) : new UdpProtocolClient(null, remoteSocketAddress, 1, 100);

		boolean tryAgainWithTcp = false;
		try
		{
			return new MessageDeserializer(protocolClient.sendAndReceive(bytes)).readMessage();
		}
		catch(TruncatedDnsMessageException exception)
		{
			if (forceTcpUse)
			{
				throw new BadlyFormedDnsMessageException("TCP DNS message was truncated; this should never happen", exception);
			}
			tryAgainWithTcp = true;
		}
		finally
		{
			protocolClient.close();
		}
		return resolve(request, remoteSocketAddress, tryAgainWithTcp);
	}
}
