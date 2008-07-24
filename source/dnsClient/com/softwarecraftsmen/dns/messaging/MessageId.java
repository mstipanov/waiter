/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging;

import static com.softwarecraftsmen.toString.ToString.string;
import com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger.MaximumValue;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MessageId implements Serializable
{
	private final Unsigned16BitInteger messageId;

	public MessageId(final @NotNull Unsigned16BitInteger messageId)
	{
		this.messageId = messageId;
	}

	private static final Object lockObject = new Object();

	private static Unsigned16BitInteger lastMessageId = MaximumValue;

	private static Unsigned16BitInteger getMessageId()
	{
		synchronized(lockObject)
		{
			lastMessageId = lastMessageId.increment();
		}
		return lastMessageId;
	}

	@NotNull
	public static MessageId messageId()
	{
		return new MessageId(getMessageId());
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeUnsigned16BitInteger(messageId);
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

		final MessageId that = (MessageId) o;
		return messageId.equals(that.messageId);
	}

	public int hashCode()
	{
		return messageId.hashCode();
	}

	@NotNull
	public String toString()
	{
		return string(this, messageId);
	}
}
