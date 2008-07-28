package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.CanNeverHappenException;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import static com.softwarecraftsmen.dns.names.PointerName.pointerName;
import com.softwarecraftsmen.dns.names.PointerName;
import static com.softwarecraftsmen.toString.ToString.string;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import static java.util.Locale.UK;

public class SerializableInternetProtocolAddress<A extends InetAddress> implements Serializable
{
	public static final SerializableInternetProtocolAddress<Inet4Address> InternetProtocolVersion4LocalHost = serializableInternetProtocolVersion4Address(127, 0 , 0, 1);
	public static final SerializableInternetProtocolAddress<Inet4Address> InternetProtocolVersion4UnspecifiedAddress = serializableInternetProtocolVersion4Address(0, 0 , 0, 0);
	public static final SerializableInternetProtocolAddress<Inet6Address> InternetProtocolVersion6LocalHost = serializableInternetProtocolVersion6Address(0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0001);
	public static final SerializableInternetProtocolAddress<Inet6Address> InternetProtocolVersion6UnspecifiedAddress = serializableInternetProtocolVersion6Address(0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000);

	public static final SerializableInternetProtocolAddress<Inet6Address> InternetProtocolVersion6LocalNetworkAddress = serializableInternetProtocolVersion6Address(0xfe00, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000);
	public static final SerializableInternetProtocolAddress<Inet6Address> InternetProtocolVersion6MulticastPrefixAddress = serializableInternetProtocolVersion6Address(0xff00, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000);
	public static final SerializableInternetProtocolAddress<Inet6Address> InternetProtocolVersion6AllNodesAddress = serializableInternetProtocolVersion6Address(0xff02, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0001);
	public static final SerializableInternetProtocolAddress<Inet6Address> InternetProtocolVersion6AllRoutersAddress = serializableInternetProtocolVersion6Address(0xff02, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0002);
	public static final SerializableInternetProtocolAddress<Inet6Address> InternetProtocolVersion6AllHostsAddress = serializableInternetProtocolVersion6Address(0xff02, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0000, 0x0003);

	public final A address;

	public SerializableInternetProtocolAddress(final @NotNull A address)
	{
		this.address = address;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeBytes(address.getAddress());
	}

	@NotNull
	public String toString()
	{
		return string(this, address);
	}

	public boolean equals(final @Nullable Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final SerializableInternetProtocolAddress that = (SerializableInternetProtocolAddress) o;
		return address.equals(that.address);
	}

	public int hashCode()
	{
		return address.hashCode();
	}

	@NotNull
	public static <A extends InetAddress> SerializableInternetProtocolAddress<A> serializableInternetProtocolAddress(final @NotNull A address)
	{
		return new SerializableInternetProtocolAddress<A>(address);
	}

	@NotNull
	public static SerializableInternetProtocolAddress<Inet4Address> serializableInternetProtocolVersion4Address(final @NotNull String dottedString)
	{
		final String[] parts = dottedString.split("\\.");
		if (parts.length != 4)
		{
			throw new IllegalArgumentException(format(UK, "%1$s is not a valid Internet Protocol version 4 dotted address string of four parts", dottedString));
		}
		return  serializableInternetProtocolVersion4Address(parseInteger(parts[0]), parseInteger(parts[1]), parseInteger(parts[2]), parseInteger(parts[3]));
	}

	private static int parseInteger(final @NotNull String value)
	{
		try
		{
			return parseInt(value);
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("%1$s is not a valid unsigned byte between 0 and 255");
		}
	}

	@NotNull
	public static SerializableInternetProtocolAddress<Inet4Address> serializableInternetProtocolVersion4Address(final int one, final int two, final int three, final int four)
	{
		guardArgumentIsUnsignedByte(one);
		guardArgumentIsUnsignedByte(two);
		guardArgumentIsUnsignedByte(three);
		guardArgumentIsUnsignedByte(four);
		try
		{
			final Inet4Address inet4Address = (Inet4Address) Inet4Address.getByAddress(new byte[]{(byte) one, (byte) two, (byte) three, (byte) four});
			return serializableInternetProtocolAddress(inet4Address);
		}
		catch (UnknownHostException e)
		{
			throw new CanNeverHappenException(e);
		}
	}

	private static void guardArgumentIsUnsignedByte(final int potentialUnsignedByte)
	{
		if (potentialUnsignedByte < 0 || potentialUnsignedByte > 255)
		{
			throw new IllegalArgumentException(format(UK, "%1$s is not between 0 and 255 inclusive", potentialUnsignedByte));
		}
	}

	// TODO: Make this work!
	// Does not support escaped IP addresses used with ports, eg http://[::1]:8080/
	/*
	@NotNull
	public static SerializableInternetProtocolAddress<Inet6Address> serializableInternetProtocolVersion6Address(final @NotNull String colonString)
	{
		final String[] leftAndRight = colonString.split("::");
		if (leftAndRight.length > 2)
		{
			throw new IllegalArgumentException("It is illegal to have more than one :: in a Internet Protocol version 6 colon string");
		}
		if (leftAndRight.length == 0)
		{
			colonString.split(":");
		}
		else
		{
			leftAndRight[0].split(":");
			leftAndRight[1].split(":");
		}
		throw new UnsupportedOperationException("To finish");
	}*/

	@NotNull
	public static SerializableInternetProtocolAddress<Inet6Address> serializableInternetProtocolVersion6Address(final long one, final long two, final long three, final long four, final long five, final long six, final long seven, final long eight)
	{
		try
		{
			final Inet6Address inet6Address = (Inet6Address) Inet6Address.getByAddress(toBytes(one, two, three, four, five, six, seven, eight));
			return serializableInternetProtocolAddress(inet6Address);
		}
		catch (UnknownHostException e)
		{
			throw new CanNeverHappenException(e);
		}
	}

	@NotNull
	public PointerName toInternetProtocolName()
	{
		if (address instanceof Inet4Address)
		{
			return pointerName((Inet4Address)address);
		}
		else if (address instanceof Inet6Address)
		{
			return pointerName((Inet6Address)address);
		}
		throw new IllegalStateException("We only support instances of InetAddress which are for Internet Protocol Version 4 and Internet Protocol Version 6");
	}

	@NotNull
	private static byte[] toBytes(final long ... values)
	{
		final byte[] bytes = new byte[values.length * 2];
		for(int index = 0; index < values.length; index++)
		{
			bytes[index * 2] = (byte) (values[index] & 0xFF00);
			bytes[index * 2 + 1] = (byte) (values[index] & 0x00FF);
		}
		return bytes;
	}
}
