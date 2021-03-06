package com.softwarecraftsmen.dns.names;

import static com.softwarecraftsmen.toString.ToString.string;
import static com.softwarecraftsmen.dns.labels.SimpleLabel.simpleLabel;
import com.softwarecraftsmen.dns.labels.SimpleLabel;
import org.jetbrains.annotations.NotNull;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.lang.String.valueOf;

public class PointerName extends AbstractName
{
	private static final SimpleLabel Arpa = simpleLabel("ARPA");
	private static final SimpleLabel InAddr = simpleLabel("IN-ADDR");
	private static final SimpleLabel IP6 = simpleLabel("IP6");
	private static final Map<Integer, String> HexadecimalMap = new LinkedHashMap<Integer, String>()
	{{
		put(10, "a");
		put(11, "b");
		put(12, "c");
		put(13, "d");
		put(14, "e");
		put(15, "f");
	}};

	private PointerName(final @NotNull Inet4Address address)
	{
		super(toInternetProtocolVersion4Labels(address.getAddress()));
	}

	private PointerName(final @NotNull Inet6Address address)
	{
		super(toInternetProtocolVersion6Labels(address.getAddress()));
	}

	public PointerName(final @NotNull List<SimpleLabel> labels)
	{
		super(labels);
	}

	@NotNull
	public String toString()
	{
		return string(this, super.toString());
	}

	@NotNull
	private static List<SimpleLabel> toInternetProtocolVersion4Labels(final @NotNull byte[] address)
	{
		return new ArrayList<SimpleLabel>()
		{{
			add(networkByteToString(address, 3));
			add(networkByteToString(address, 2));
			add(networkByteToString(address, 1));
			add(networkByteToString(address, 0));
			add(InAddr);
			add(Arpa);
		}};
	}

	@NotNull
	private static List<SimpleLabel> toInternetProtocolVersion6Labels(final @NotNull byte[] address)
	{
		// 4321:0:1:2:3:4:567:89ab
		// is
		// b.a.9.8.7.6.5.0.4.0.0.0.3.0.0.0.2.0.0.0.1.0.0.0.0.0.0.0.1.2.3.4.IP6.ARPA
		return new ArrayList<SimpleLabel>()
		{{
			for(int index = 0; index < 16; index ++)
			{
				final int unsignedValue = address[index] & 0xFF;
				add(index * 2, simpleLabel(hexValue(unsignedValue & 0x0F)));
				add(index * 2 + 1, simpleLabel(hexValue(unsignedValue & 0xF0 >> 4)));
			}
			add(32, IP6);
			add(33, Arpa);
		}};
	}

	private static String hexValue(final int unsignedNibble)
	{
		if (unsignedNibble < 0)
		{
			throw new IllegalStateException();
		}
		if (unsignedNibble < 10)
		{
			return valueOf(unsignedNibble);
		}
		return HexadecimalMap.get(unsignedNibble);
	}

	@NotNull
	private static SimpleLabel networkByteToString(final @NotNull byte[] address, int offset)
	{
		return simpleLabel(networkByteToString(address[offset]));
	}

	@NotNull
	private static String networkByteToString(final byte addressByte)
	{
		final int i = addressByte & 0xFF;
		return valueOf(i);
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
