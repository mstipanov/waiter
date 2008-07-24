package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;
import static java.util.Locale.UK;

public class MailBox implements Name
{
	private final String userName;
	private final DomainName domainName;

	public MailBox(final @NotNull String userName, final @NotNull DomainName domainName)
	{
		this.userName = userName;
		this.domainName = domainName;
		if (userName.length() > 63)
		{
			throw new IllegalArgumentException("An userName must be less than 64 characters in length");
		}
	}

	public String toString()
	{
		return format(UK, "%1$s@%2$s", userName, domainName);
	}

	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final MailBox mailBox = (MailBox) o;
		return domainName.equals(mailBox.domainName) && userName.equals(mailBox.userName);
	}

	public int hashCode()
	{
		int result;
		result = userName.hashCode();
		result = 31 * result + domainName.hashCode();
		return result;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeCharacterString(userName);
		domainName.serialize(writer);
	}
}
