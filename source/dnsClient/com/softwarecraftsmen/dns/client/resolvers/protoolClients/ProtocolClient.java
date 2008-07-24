package com.softwarecraftsmen.dns.client.resolvers.protoolClients;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ProtocolClient
{
	public static final byte[] EmptyByteArray = new byte[] {};

	@NotNull
	byte[] sendAndReceive(@NotNull byte[] sendData) throws IOException;

	void close();
}
