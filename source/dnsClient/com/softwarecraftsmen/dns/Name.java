package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Name extends Serializable
{
	@NotNull
	List<String> toLabels();
}
