/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging;

import com.softwarecraftsmen.Unsigned16BitInteger;
import static com.softwarecraftsmen.Unsigned16BitInteger.unsigned16BitInteger;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;
import static java.util.Locale.UK;

public enum Class implements Serializable
{
	Reserved0000(unsigned16BitInteger(0x0000), "Reserved", false, true, false),
	Internet(unsigned16BitInteger(0x0001), "Internet", false, false, false),
	CSNET(unsigned16BitInteger(0x0002), "CS", true, false, false),
	Chaos(unsigned16BitInteger(0x0003), "CH", false, true, false),
	Hesiod(unsigned16BitInteger(0x0004), "HS", false, false, false),
	Any(unsigned16BitInteger(0x00FF), "Any", false, false, true),
	ReservedFFFF(unsigned16BitInteger(0x0000), "Reserved", false, true, false),
	;

	private final Unsigned16BitInteger value;
	private final String description;
	private final boolean obsolete;
	private final boolean reserved;
	private final boolean onlyQClass;

	private Class(final @NotNull Unsigned16BitInteger value, final @NotNull String description, final boolean obsolete, final boolean reserved, final boolean isOnlyQClass)
	{
		this.value = value;
		this.description = description;
		this.obsolete = obsolete;
		this.reserved = reserved;
		onlyQClass = isOnlyQClass;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeUnsigned16BitInteger(value);
	}

	public String toString()
	{
		return format(UK, "%1$s (%2$s)", name(), description);
	}

	public static Class fromUnsigned16BitInteger(final @NotNull Unsigned16BitInteger value)
	{
		for (Class aClass : values())
		{
			if (aClass.value.equals(value))
			{
				return aClass;
			}
		}
		throw new IllegalArgumentException(format(UK, "Unrecognised class code %1$s", value));
	}
}
