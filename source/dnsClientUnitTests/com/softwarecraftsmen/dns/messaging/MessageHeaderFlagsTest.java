/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static com.softwarecraftsmen.dns.messaging.serializer.ByteSerializer.serialize;
import static com.softwarecraftsmen.dns.messaging.ResponseCode.NoErrorCondition;
import com.softwarecraftsmen.dns.messaging.OperationCode;
import com.softwarecraftsmen.dns.messaging.MessageHeaderFlags;

public class MessageHeaderFlagsTest
{
	@Test
	public void serializesQueryAsTwoOctets()
	{
		assertThat(serialize(MessageHeaderFlags.Query), is(equalTo(new byte[]{0x01, 0x00})));
	}

	@Test
	public void serializesSomethingElseCorrectly()
	{
		assertThat(serialize(new MessageHeaderFlags(true, OperationCode.Query, false, false, true, true, NoErrorCondition)), is(equalTo(new byte[]{65, 0x02})));
	}
}
