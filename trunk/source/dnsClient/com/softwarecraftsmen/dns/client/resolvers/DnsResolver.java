package com.softwarecraftsmen.dns.client.resolvers;

import com.softwarecraftsmen.dns.names.Name;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.messaging.Message;
import org.jetbrains.annotations.NotNull;

public interface DnsResolver
{
	@NotNull
	Message resolve(final @NotNull Name name, final @NotNull InternetClassType internetClassType);
}
