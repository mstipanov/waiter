/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging;

import static com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger.One;
import static com.softwarecraftsmen.dns.names.HostName.hostName;
import static com.softwarecraftsmen.dns.messaging.InternetClassType.A;
import static com.softwarecraftsmen.dns.messaging.Message.NoResourceRecords;
import static com.softwarecraftsmen.dns.messaging.MessageHeader.outboundMessageHeader;
import static com.softwarecraftsmen.dns.messaging.Question.internetQuestion;
import static com.softwarecraftsmen.dns.messaging.serializer.ByteSerializer.serialize;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.ArrayList;

public class MessageTest
{
	@Test
	public void serializesQueriesWithTwoQuestionsInCorrectSequence()
	{
		final ArrayList<Question> questions = new ArrayList<Question>()
		{{
			add(internetQuestion(hostName("www.softwarecraftsmen.com"), A));
			add(internetQuestion(hostName("www.softwarecraftsmen.co.uk"), A));
		}};
		final MessageHeader messageHeader = outboundMessageHeader(new MessageId(One), questions);

		final byte[] expected = new ArrayList<Byte>()
		{
			{
				appendSerializedForm(messageHeader);
				for (Question question : questions)
				{
					appendSerializedForm(question);
				}
			}

			private void appendSerializedForm(final Serializable serializable)
			{
				for (byte aByte : serialize(serializable))
				{
					add(aByte);
				}
			}

			public byte[] toPrimitiveByteArray()
			{
				final byte[] bytes = new byte[size()];
				for (int index = 0; index < size(); index++)
				{
					bytes[index] = get(index);
				}
				return bytes;
			}
		}.toPrimitiveByteArray();

		final Message message = new Message(messageHeader, questions, NoResourceRecords, NoResourceRecords, NoResourceRecords);
		assertThat(serialize(message), is(equalTo(expected)));
	}
}
