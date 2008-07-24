package com.softwarecraftsmen.dns.messaging.deserializer;

public final class BadlyFormedDnsMessageException extends Exception
{
	public BadlyFormedDnsMessageException(final String message)
	{
		super(message);
	}

	public BadlyFormedDnsMessageException(final String message, final Exception exception)
	{
		super(message, exception);
	}
}
