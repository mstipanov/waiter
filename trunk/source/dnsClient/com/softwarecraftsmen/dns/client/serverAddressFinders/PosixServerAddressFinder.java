/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client.serverAddressFinders;

import com.softwarecraftsmen.CanNeverHappenException;
import com.softwarecraftsmen.dns.SerializableInternetProtocolAddress;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import static java.lang.String.format;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import static java.util.Locale.UK;

public class PosixServerAddressFinder implements ServerAddressFinder
{
	public static final PosixServerAddressFinder CachedPosixServerAddressFinder = new PosixServerAddressFinder();
	
	@SuppressWarnings({"ThrowFromFinallyBlock"})
	@NotNull
	public List<InetSocketAddress> find()
	{
		final File resolvConfFile = new File("/etc/resolv.conf");
		final boolean nameserverDetailsExist = (resolvConfFile.exists() && resolvConfFile.isFile() && resolvConfFile.canRead());
		if (!nameserverDetailsExist)
		{
			return new ArrayList<InetSocketAddress>();
		}

		final List<InetSocketAddress> domainNameServerIpAddresses;
		Reader reader = null;
		boolean thrown = true;
		try
		{
			reader = new FileReader(resolvConfFile);
			domainNameServerIpAddresses = read(reader);
			thrown = false;
		}
		catch (FileNotFoundException cause)
		{
			throw new CanNeverHappenException(cause);
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					if (!thrown)
					{
						throw new CanNeverHappenException(e);
					}
				}
			}
		}
		return domainNameServerIpAddresses;
	}

	private List<InetSocketAddress> read(final Reader reader)
	{
		final List<InetSocketAddress> domainNameServerIpAddresses = new ArrayList<InetSocketAddress>();
		final LineNumberReader lineNumberReader = new LineNumberReader(reader);
		try
		{
			String line;
			while((line = lineNumberReader.readLine()) != null)
			{
				if (!line.startsWith("nameserver"))
				{
					continue;
				}
				domainNameServerIpAddresses.add(parseNameServerLine(line));
			}
			return domainNameServerIpAddresses;
		}
		catch (final IOException cause)
		{
			final class CouldNotReadResolvConfException extends RuntimeException
			{
				public CouldNotReadResolvConfException()
				{
					super(format(UK, "Could not read line %1$s from /etc/resolv.conf", lineNumberReader.getLineNumber()), cause);
				}
			}
			throw new CouldNotReadResolvConfException();
		}
	}

	private InetSocketAddress parseNameServerLine(final String line)
	{
		final String domainNameServer = line.substring(10).trim();
		final Inet4Address address;
		try
		{
			address = SerializableInternetProtocolAddress.serializableInternetProtocolVersion4Address(domainNameServer).address;
		}
		catch (final IllegalArgumentException couldNotParseNameServerLine)
		{
			final class CouldNotReadNameServerLineInConfException extends RuntimeException
			{
				public CouldNotReadNameServerLineInConfException()
				{
					super(format(UK, "Could not read line %1$s from /etc/reolv.conf, possibly because we could not parse the name server. Internet Protocol version 6 addresses are not yet supported", line), couldNotParseNameServerLine);
				}
			}
			throw new CouldNotReadNameServerLineInConfException();
		}
		return new InetSocketAddress(address, StandardUnicastDnsServerPort);
	}
}
