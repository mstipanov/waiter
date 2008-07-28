/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging;

import com.softwarecraftsmen.dns.names.Name;
import static com.softwarecraftsmen.dns.messaging.MessageHeader.outboundMessageHeader;
import static com.softwarecraftsmen.dns.messaging.MessageId.messageId;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.resourceRecords.ResourceRecord;
import static com.softwarecraftsmen.toString.ToString.string;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Arrays.asList;
import java.util.Collection;
import static java.util.Collections.emptyList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Message implements Serializable
{
	private final MessageHeader messageHeader;
	private final List<Question> questions;
	private final List<ResourceRecord<? extends Name, ? extends Serializable>> answers;
	private final List<ResourceRecord<? extends Name, ? extends Serializable>> nameServerAuthorities;
	private final List<ResourceRecord<? extends Name, ? extends Serializable>> additionalRecords;
	public static final List<ResourceRecord<? extends Name, ? extends Serializable>> NoResourceRecords = emptyList();

	public Message(final @NotNull MessageHeader messageHeader, final @NotNull List<Question> questions, final List<ResourceRecord<? extends Name, ? extends Serializable>> answers, final List<ResourceRecord<? extends Name, ? extends Serializable>> nameServerAuthorities, final List<ResourceRecord<? extends Name, ? extends Serializable>> additionalRecords)
	{
		this.messageHeader = messageHeader;
		this.questions = questions;
		this.answers = answers;
		this.nameServerAuthorities = nameServerAuthorities;
		this.additionalRecords = additionalRecords;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		messageHeader.serialize(writer);
		writeDnsQuestions(writer);
		writeResourceRecordsWhichAnswerTheQuestion(writer);
		writeResourceRecordsWhichPointToTheDomainAuthority(writer);
		writeResourceRecordsWhichMayHoldAdditionalInformation(writer);
	}

	@NotNull
	public String toString()
	{
		return string(this, messageHeader, questions, answers, nameServerAuthorities, additionalRecords);
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

		final Message message = (Message) o;

		if (!additionalRecords.equals(message.additionalRecords))
		{
			return false;
		}
		if (!answers.equals(message.answers))
		{
			return false;
		}
		if (!messageHeader.equals(message.messageHeader))
		{
			return false;
		}
		if (!nameServerAuthorities.equals(message.nameServerAuthorities))
		{
			return false;
		}
		if (!questions.equals(message.questions))
		{
			return false;
		}
		return true;
	}

	public int hashCode()
	{
		int result;
		result = messageHeader.hashCode();
		result = 31 * result + questions.hashCode();
		result = 31 * result + answers.hashCode();
		result = 31 * result + nameServerAuthorities.hashCode();
		result = 31 * result + additionalRecords.hashCode();
		return result;
	}

	private void writeDnsQuestions(final @NotNull AtomicWriter writer)
	{
		for(Question question : questions)
		{
			question.serialize(writer);
		}
	}

	private void writeResourceRecordsWhichAnswerTheQuestion(final @NotNull AtomicWriter writer)
	{
		for (ResourceRecord<? extends Name, ? extends Serializable> answer : answers)
		{
			answer.serialize(writer);
		}
	}

	// These MUST always (on send or receive) be of InternetClassType.NS
	private void writeResourceRecordsWhichPointToTheDomainAuthority(final @NotNull AtomicWriter writer)
	{
		for (ResourceRecord<? extends Name, ? extends Serializable> nameServerAuthority : nameServerAuthorities)
		{
			nameServerAuthority.serialize(writer);
		}
	}

	private void writeResourceRecordsWhichMayHoldAdditionalInformation(final @NotNull AtomicWriter writer)
	{
		for (ResourceRecord<? extends Name, ? extends Serializable> additionalRecord : additionalRecords)
		{
			additionalRecord.serialize(writer);
		}
	}

	@NotNull
	public static Message query(final @NotNull Question ... questions)
	{
		return query(messageId(), questions);
	}

	@NotNull
	public static Message query(final @NotNull MessageId messageId, final @NotNull Question ... questions)
	{
		final List<Question> questionList = asList(questions);
		return new Message(outboundMessageHeader(messageId, questionList), questionList, NoResourceRecords, NoResourceRecords, NoResourceRecords);
	}

	public static Message emptyReply(final @NotNull Message request)
	{
		return new Message(MessageHeader.emptyReply(request.messageHeader), request.questions, NoResourceRecords, NoResourceRecords, NoResourceRecords);
	}

	@NotNull
	public Set<ResourceRecord<? extends Name, ? extends Serializable>> allResourceRecords()
	{
		return new LinkedHashSet<ResourceRecord<? extends Name, ? extends Serializable>>()
		{{
			addAll(answers);
			addAll(nameServerAuthorities);
			addAll(additionalRecords);
		}};
	}

	@SuppressWarnings({"unchecked"})
	@NotNull
	public <T extends Serializable> Set<T> allAnswersMatching(final @NotNull InternetClassType internetClassType)
	{
		return allAnswersMatching(answers, internetClassType);
	}

	@SuppressWarnings({"unchecked"})
	@NotNull
	public static <T extends Serializable> Set<T> allAnswersMatching(final @NotNull Collection<ResourceRecord<? extends Name, ? extends Serializable>> resourceRecords, final @NotNull InternetClassType internetClassType)
	{
		final Set<T> set = new LinkedHashSet<T>(resourceRecords.size());
		for (ResourceRecord<? extends Name, ? extends Serializable> answer : resourceRecords)
		{
			answer.appendDataIfIs(internetClassType, (Set) set);
		}
		return set;
	}
}
