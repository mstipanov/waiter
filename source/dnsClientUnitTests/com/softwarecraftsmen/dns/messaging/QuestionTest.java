/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging;

import com.softwarecraftsmen.dns.names.AbstractName;
import static com.softwarecraftsmen.dns.names.HostName.hostName;
import static com.softwarecraftsmen.dns.messaging.Question.internetQuestion;
import static com.softwarecraftsmen.dns.messaging.serializer.ByteSerializer.serialize;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.ArrayList;

public class QuestionTest
{
	@Test
	public void serializesFieldsInCorrectSequence()
	{
		final AbstractName name = hostName("www.softwarecraftsmen.com");
		final InternetClassType internetClassType = InternetClassType.DLV;
		final QClass clazz = QClass.Internet;

		final byte[] expected = new ArrayList<Byte>()
		{
			{
				appendSerializedForm(name);
				appendSerializedForm(internetClassType);
				appendSerializedForm(clazz);
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

		final Question question = internetQuestion(name, internetClassType);
		assertThat(serialize(question), is(equalTo(expected)));
	}
}
