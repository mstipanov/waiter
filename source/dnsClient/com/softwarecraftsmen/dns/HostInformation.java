package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.toString.ToString.string;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HostInformation implements Serializable
{
	public final String cpuType;
	public final String operatingSystemType;

	public HostInformation(final @NotNull String cpuType, final @NotNull String operatingSystemType)
	{
		this.cpuType = cpuType;
		this.operatingSystemType = operatingSystemType;
		if (cpuType.length() > 255)
		{
			throw new IllegalArgumentException("cpuType is a character strign which DNS restricts to a maximum length of 255 characters");
		}
		if (operatingSystemType.length() > 255)
		{
			throw new IllegalArgumentException("operatingSystemType is a character strign which DNS restricts to a maximum length of 255 characters");
		}
	}

	@NotNull
	public String toString()
	{
		return string(this, cpuType, operatingSystemType);
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

		final HostInformation that = (HostInformation) o;
		return cpuType.equals(that.cpuType) && operatingSystemType.equals(that.operatingSystemType);
	}

	public int hashCode()
	{
		int result;
		result = cpuType.hashCode();
		result = 31 * result + operatingSystemType.hashCode();
		return result;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeCharacterString(cpuType);
		writer.writeCharacterString(operatingSystemType);
	}

	@NotNull
	public static HostInformation hostInformation(final @NotNull String cpuType, final @NotNull String operatingSystemType)
	{
		return new HostInformation(cpuType, operatingSystemType);
	}
}
