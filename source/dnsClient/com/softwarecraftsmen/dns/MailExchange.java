package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.labels.SimpleLabel;
import com.softwarecraftsmen.dns.names.HostName;
import static com.softwarecraftsmen.toString.ToString.string;
import com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import static java.util.Collections.reverse;
import java.util.List;

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
		final int initialPreference = this.preference.compareTo(that.preference);
		if (initialPreference != 0)
		{
			return initialPreference;
		}
		final List<SimpleLabel> thisLabels = reverseLabelsInHostName(this);
		final List<SimpleLabel> thatLabels = reverseLabelsInHostName(that);

		if (thisLabels.size() < thatLabels.size())
		{
			return -1;
		}
		if (thisLabels.size() > thatLabels.size())
		{
			return 1;
		}
		for(int index = 0; index < thisLabels.size(); index++)
		{
			final int compareTo = thisLabels.get(index).compareTo(thatLabels.get(index));
			if (compareTo != 0)
			{
				return compareTo;
			}
		}
		return 0;
	}

	private List<SimpleLabel> reverseLabelsInHostName(final MailExchange mailExchange)
	{
		return new ArrayList<SimpleLabel>(mailExchange.hostName.toLabels())
		{{
			reverse(this);
		}};
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
