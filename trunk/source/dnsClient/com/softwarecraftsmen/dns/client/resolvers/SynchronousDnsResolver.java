/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client.resolvers;

import com.softwarecraftsmen.dns.Name;
import com.softwarecraftsmen.dns.client.resolvers.protools.ProtocolClient;
import com.softwarecraftsmen.dns.client.resolvers.protools.TcpClient;
import com.softwarecraftsmen.dns.client.resolvers.protools.UdpClient;
import com.softwarecraftsmen.dns.client.serverAddressFinders.ServerAddressFinder;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.messaging.Message;
import static com.softwarecraftsmen.dns.messaging.Message.emptyReply;
import static com.softwarecraftsmen.dns.messaging.Message.query;
import static com.softwarecraftsmen.dns.messaging.Question.internetQuestion;
import com.softwarecraftsmen.dns.messaging.deserializer.BadlyFormedDnsMessageException;
import com.softwarecraftsmen.dns.messaging.deserializer.MessageDeserializer;
import static com.softwarecraftsmen.dns.messaging.serializer.ByteSerializer.serialize;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
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
		final Message request = query(internetQuestion(name, internetClassType));

		final List<InetSocketAddress> dnsServers = serverAddressFinder.find();
		Message response = emptyReply(request);
		for (InetSocketAddress dnsServer : dnsServers)
		{
			try
			{
				response = resolve(request, dnsServer);
				break;
			}
			catch (DidNotReceiveAValidResponseMessageForRequestMessageException e)
			{}
		}
		return response;
	}

	@NotNull
	private Message resolve(final @NotNull Message requestMessage, final InetSocketAddress remoteSocketAddress) throws DidNotReceiveAValidResponseMessageForRequestMessageException
	{
		final byte[] bytes = serialize(requestMessage);
		final ProtocolClient protocolClient = (bytes.length < MaximumNonEDNS0UdpMessageSize) ? new UdpClient(null, remoteSocketAddress, 1, 100) : new TcpClient(null, remoteSocketAddress, 1, 100);
		try
		{
			return new MessageDeserializer(protocolClient.sendAndReceive(bytes)).readMessage();
		}
		catch (IOException e)
		{
			throw new DidNotReceiveAValidResponseMessageForRequestMessageException(requestMessage, e);
		}
		catch (BadlyFormedDnsMessageException e)
		{
			throw new DidNotReceiveAValidResponseMessageForRequestMessageException(requestMessage, e);
		}
		finally
		{
			protocolClient.close();
		}
	}

}
