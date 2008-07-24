package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.ToString.string;
import com.softwarecraftsmen.Unsigned16BitInteger;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MailExchange implements Comparable<MailExchange>, Serializable
{
	private final Unsigned16BitInteger preference;
	private final HostName hostName;

	public MailExchange(final @NotNull Unsigned16BitInteger preference, final @NotNull HostName hostName)
	{
		this.preference = preference;
		this.hostName = hostName;
	}

	@NotNull
	public String toString()
	{
		return string(this, preference, hostName);
	}

	@SuppressWarnings({"RedundantIfStatement"})
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

		final MailExchange that = (MailExchange) o;

		if (!hostName.equals(that.hostName))
		{
			return false;
		}
		if (!preference.equals(that.preference))
		{
			return false;
		}
		return true;
	}

	public int hashCode()
	{
		int result;
		result = preference.hashCode();
		result = 31 * result + hostName.hashCode();
		return result;
	}

	public int compareTo(final @NotNull MailExchange that)
	{
		return this.preference.compareTo(that.preference);
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeUnsigned16BitInteger(preference);
		hostName.serialize(writer);
	}

	@NotNull
	public static MailExchange mailExchange(final @NotNull Unsigned16BitInteger preference, final @NotNull HostName hostName)
	{
		return new MailExchange(preference, hostName);
	}
}
