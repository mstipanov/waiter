package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.ToString.string;
import org.jetbrains.annotations.NotNull;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PointerName extends AbstractName
{
	public PointerName(final @NotNull Inet4Address address)
	{
		super(toInternetProtocolVersion4Labels(address.getAddress()));
	}

	public PointerName(final @NotNull Inet6Address address)
	{
		super(toInternetProtocolVersion6Labels(address.getAddress()));
	}

	public PointerName(final List<String> labels)
	{
		super(labels);
	}

	@NotNull
	public String toString()
	{
		return string(this, super.toString());
	}

	private static final Map<Integer,String> HexadecimalMap = new LinkedHashMap<Integer, String>()
	{{
		put(10, "a");
		put(11, "b");
		put(12, "c");
		put(13, "d");
		put(14, "e");
		put(15, "f");
	}};
	
	@NotNull
	private static String[] toInternetProtocolVersion4Labels(final @NotNull byte[] address)
	{
		return new String[]
		{
			networkByteToString(address, 3),
			networkByteToString(address, 2),
			networkByteToString(address, 1),
			networkByteToString(address, 0),
			"IN-ADDR",
			"ARPA"
		};
	}

	@NotNull
	private static String[] toInternetProtocolVersion6Labels(final @NotNull byte[] address)
	{
		//4321:0:1:2:3:4:567:89ab
		// is
		// b.a.9.8.7.6.5.0.4.0.0.0.3.0.0.0.2.0.0.0.1.0.0.0.0.0.0.0.1.2.3.4.IP6.ARPA
		final String[] labels = new String[34];
		for(int index = 0; index < 16; index ++)
		{
			final int unsignedValue = address[index] & 0xFF;
			labels[index * 2] = hexValue(unsignedValue & 0x0F);
			labels[index * 2 + 1] = hexValue(unsignedValue & 0xF0 >> 4);
		}
		labels[32] = "IP6";
		labels[33] = "ARPA";
		return labels;
	}

	private static String hexValue(final int unsignedNibble)
	{
		if (unsignedNibble < 0)
		{
			throw new IllegalStateException();
		}
		if (unsignedNibble < 10)
		{
			return String.valueOf(unsignedNibble);
		}
		return HexadecimalMap.get(unsignedNibble);
	}

	@NotNull
	private static String networkByteToString(final @NotNull byte[] address, int offset)
	{
		return networkByteToString(address[offset]);
	}

	@NotNull
	private static String networkByteToString(final byte addressByte)
	{
		final int i = addressByte & 0xFF;
		return String.valueOf(i);
	}

	@NotNull
	public static PointerName pointerName(final @NotNull Inet4Address address)
	{
		return new PointerName(address);
	}

	@NotNull
	public static PointerName pointerName(final @NotNull Inet6Address address)
	{
		return new PointerName(address);
	}
}
