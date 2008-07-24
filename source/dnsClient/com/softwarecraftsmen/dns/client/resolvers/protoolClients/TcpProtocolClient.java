/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client.resolvers.protoolClients;

import com.softwarecraftsmen.CanNeverHappenException;
import static com.softwarecraftsmen.dns.messaging.serializer.ByteSerializer.MaximumDnsMessageSize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import static java.lang.System.arraycopy;
import java.net.SocketAddress;
import static java.nio.ByteBuffer.wrap;
import java.nio.channels.*;
import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_CONNECT;
import static java.nio.channels.SelectionKey.OP_WRITE;
import static java.util.Arrays.copyOf;

public class TcpProtocolClient implements ProtocolClient
{
	private boolean closed;
	private final SocketChannel channel;
	private final SelectionKey key;
	private final SelectorKeyHelper selectorKeyHelper;

	public TcpProtocolClient(@Nullable final SocketAddress localSocketAddress, @NotNull final SocketAddress remoteSocketAddress, final int blockInMilliseconds, final int numberOfRetries)
	{
		this.closed = true;
		this.channel = openChannel();
		try
		{
			channel.configureBlocking(false);
		}
		catch(final IOException exception)
		{
			closeChannel(true);
			throw new IllegalStateException(exception);
		}
		key = obtainSelectorKey(openSelector());
		selectorKeyHelper = new SelectorKeyHelper(key, blockInMilliseconds, numberOfRetries);
		bind(localSocketAddress);
		connect(remoteSocketAddress);

		this.closed = false;
	}

	public void close()
	{
		if (closed)
		{
			return;
		}
		closed = true;
		try
		{
			closeChannel(false);
		}
		catch(IllegalStateException exception)
		{
			closeSelector(selector(), true);
			throw new IllegalStateException(exception);
		}
		closeSelector(selector(), false);
	}

	@SuppressWarnings({"EmptyCatchBlock"})
	protected void finalize() throws Throwable
	{
		super.finalize();
		try
		{
			close();
		}
		catch (final Exception exception)
		{}
	}

	@SuppressWarnings({"ThrowableInstanceNeverThrown"})
	private IllegalStateException closeDueToError(final IOException e)
	{
		closeChannel(true);
		closeSelector(selector(), true);
		return new IllegalStateException(e);
	}

	private Selector selector() {return key.selector();}

	private SelectionKey obtainSelectorKey(final Selector selector)
	{
		try
		{
			return channel.register(selector, 0);
		}
		catch (final ClosedChannelException e)
		{
			closeSelector(selector, true);
			closeChannel(true);
			throw new CanNeverHappenException(e);
		}
	}

	private void closeSelector(final Selector selector, final boolean inResponseToAnEarlierException)
	{
		try
		{
			selector.close();
		}
		catch (IOException e)
		{
			if (inResponseToAnEarlierException)
			{
				return;
			}
			throw new IllegalStateException(e);
		}
	}

	private SocketChannel openChannel()
	{
		try
		{
			return SocketChannel.open();
		}
		catch (final IOException exception)
		{
			throw new CanNeverHappenException(exception);
		}
	}

	private Selector openSelector()
	{
		try
		{
			return Selector.open();
		}
		catch(IOException exception)
		{
			closeChannel(true);
			throw new CanNeverHappenException(exception);
		}
	}

	private void closeChannel(final boolean inResponseToAnEarlierException)
	{
		try
		{
			channel.close();
		}
		catch (IOException e)
		{
			if (inResponseToAnEarlierException)
			{
				return;
			}
			throw new IllegalStateException(e);
		}
	}

	private void connect(final SocketAddress remoteSocketAddress)
	{
		try
		{
			channel.connect(remoteSocketAddress);
		}
		catch (IOException e)
		{
			throw closeDueToError(e);
		}
	}

	private void bind(final SocketAddress localSocketAddress)
	{
		try
		{
			channel.socket().bind(localSocketAddress);
		}
		catch (IOException e)
		{
			throw closeDueToError(e);
		}
	}

	@NotNull
	public byte[] sendAndReceive(final @NotNull byte[] sendData) throws IOException
	{
		send(sendData);
		return receive();
	}

	public void send(final @NotNull byte[] data) throws IOException
	{
		int length = data.length;
		final byte[] dataWithTcpBytes = new byte[length + 2];
		arraycopy(data, 0, dataWithTcpBytes, 2, length);
		dataWithTcpBytes[0] = (byte) ((length >>> 8) & 0xFF);
		dataWithTcpBytes[1] = (byte) (length & 0xFF);

		selectorKeyHelper.blockUntilReady(OP_CONNECT);
		if (!channel.finishConnect())
		{
			throw new IOException("Could not connect to TCP address");
		}
		selectorKeyHelper.blockUntilReady(OP_WRITE);
		channel.write(wrap(dataWithTcpBytes));
	}

	@NotNull
	private byte[] receive() throws IOException
	{
		selectorKeyHelper.blockUntilReady(OP_READ);
		byte[] buffer = new byte[MaximumDnsMessageSize];
		long numberOfBytesRead = channel.read(wrap(buffer));
		if (numberOfBytesRead <= 0)
		{
			return EmptyByteArray;
		}
		return copyOf(buffer, (int) numberOfBytesRead);
	}
}