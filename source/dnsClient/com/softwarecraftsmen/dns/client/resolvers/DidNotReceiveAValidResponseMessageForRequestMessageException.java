package com.softwarecraftsmen.dns.client.resolvers;

import com.softwarecraftsmen.dns.messaging.Message;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;
import static java.util.Locale.UK;

public class DidNotReceiveAValidResponseMessageForRequestMessageException extends Exception
{
	public DidNotReceiveAValidResponseMessageForRequestMessageException(final @NotNull Message requestMessage, final @NotNull Exception cause)
	{
		super(format(UK, "Could not receive a valid response message for the request message %1$s", requestMessage), cause);
	}
}
