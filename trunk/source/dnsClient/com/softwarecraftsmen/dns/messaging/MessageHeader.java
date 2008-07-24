/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging;

import static com.softwarecraftsmen.dns.messaging.MessageHeaderFlags.Query;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import static com.softwarecraftsmen.toString.ToString.string;
import com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MessageHeader implements Serializable
{
	private final MessageId messageId;
	private final MessageHeaderFlags messageHeaderFlags;
	private final Unsigned16BitInteger numberOfEntriesInQuestionSection;
	private final Unsigned16BitInteger numberOfResourceRecordsInAnswerSection;
	private final Unsigned16BitInteger numberOfNameServerRecordsInAuthoritySection;
	private final Unsigned16BitInteger numberOfResourceRecordsInAdditionalRecordsAnswerSection;
	public static final int SizeOfDnsMessageHeader = 12;

	public MessageHeader(final @NotNull MessageId messageId, final @NotNull MessageHeaderFlags messageHeaderFlags, final @NotNull Unsigned16BitInteger numberOfEntriesInQuestionSection, final @NotNull Unsigned16BitInteger numberOfResourceRecordsInAnswerSection, final @NotNull Unsigned16BitInteger numberOfNameServerRecordsInAuthoritySection, final @NotNull Unsigned16BitInteger numberOfResourceRecordsInAdditionalRecordsAnswerSection)
	{
		this.messageId = messageId;
		this.messageHeaderFlags = messageHeaderFlags;
		this.numberOfEntriesInQuestionSection = numberOfEntriesInQuestionSection;
		this.numberOfResourceRecordsInAnswerSection = numberOfResourceRecordsInAnswerSection;
		this.numberOfNameServerRecordsInAuthoritySection = numberOfNameServerRecordsInAuthoritySection;
		this.numberOfResourceRecordsInAdditionalRecordsAnswerSection = numberOfResourceRecordsInAdditionalRecordsAnswerSection;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		messageId.serialize(writer);
		messageHeaderFlags.serialize(writer);
		writer.writeUnsigned16BitInteger(numberOfEntriesInQuestionSection);
		writer.writeUnsigned16BitInteger(numberOfResourceRecordsInAnswerSection);
		writer.writeUnsigned16BitInteger(numberOfNameServerRecordsInAuthoritySection);
		writer.writeUnsigned16BitInteger(numberOfResourceRecordsInAdditionalRecordsAnswerSection);
	}

	@NotNull
	public Unsigned16BitInteger getNumberOfEntriesInQuestionSection()
	{
		return numberOfEntriesInQuestionSection;
	}

	@NotNull
	public Unsigned16BitInteger getNumberOfResourceRecordsInAnswerSection()
	{
		return numberOfResourceRecordsInAnswerSection;
	}

	@NotNull
	public Unsigned16BitInteger getNumberOfNameServerRecordsInAuthoritySection()
	{
		return numberOfNameServerRecordsInAuthoritySection;
	}

	@NotNull
	public Unsigned16BitInteger getNumberOfResourceRecordsInAdditionalRecordsAnswerSection()
	{
		return numberOfResourceRecordsInAdditionalRecordsAnswerSection;
	}

	@NotNull
	public static MessageHeader outboundMessageHeader(final @NotNull MessageId messageId, final @NotNull List<Question> questions)
	{
		return new MessageHeader(messageId, Query, unsigned16BitInteger(questions.size()), Zero, Zero, Zero);
	}

	@SuppressWarnings({"SimplifiableIfStatement"})
	public boolean matchesReply(final @NotNull MessageHeader reply)
	{
		if (!messageId.equals(reply.messageId))
		{
			return false;
		}
		if (!messageHeaderFlags.matchesReply(reply.messageHeaderFlags))
		{
			return false;
		}
		return numberOfEntriesInQuestionSection.equals(reply.numberOfEntriesInQuestionSection);
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

		final MessageHeader that = (MessageHeader) o;

		if (!messageHeaderFlags.equals(that.messageHeaderFlags))
		{
			return false;
		}
		if (!messageId.equals(that.messageId))
		{
			return false;
		}
		if (!numberOfEntriesInQuestionSection.equals(that.numberOfEntriesInQuestionSection))
		{
			return false;
		}
		if (!numberOfNameServerRecordsInAuthoritySection.equals(that.numberOfNameServerRecordsInAuthoritySection))
		{
			return false;
		}
		if (!numberOfResourceRecordsInAdditionalRecordsAnswerSection.equals(that.numberOfResourceRecordsInAdditionalRecordsAnswerSection))
		{
			return false;
		}
		if (!numberOfResourceRecordsInAnswerSection.equals(that.numberOfResourceRecordsInAnswerSection))
		{
			return false;
		}
		return true;
	}

	public int hashCode()
	{
		int result;
		result = messageId.hashCode();
		result = 31 * result + messageHeaderFlags.hashCode();
		result = 31 * result + numberOfEntriesInQuestionSection.hashCode();
		result = 31 * result + numberOfResourceRecordsInAnswerSection.hashCode();
		result = 31 * result + numberOfNameServerRecordsInAuthoritySection.hashCode();
		result = 31 * result + numberOfResourceRecordsInAdditionalRecordsAnswerSection.hashCode();
		return result;
	}

	@NotNull
	public String toString()
	{
		return string(this, messageId, messageHeaderFlags, numberOfEntriesInQuestionSection, numberOfResourceRecordsInAnswerSection, numberOfNameServerRecordsInAuthoritySection, numberOfResourceRecordsInAdditionalRecordsAnswerSection);
	}

	@NotNull
	public static MessageHeader emptyReply(final @NotNull MessageHeader messageHeader)
	{
		return new MessageHeader(messageHeader.messageId, MessageHeaderFlags.emptyReply(messageHeader.messageHeaderFlags), One, Zero, Zero, Zero);
	}
}
