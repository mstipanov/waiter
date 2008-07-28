package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Name<L extends Label> extends Serializable
{
	@NotNull
	List<L> toLabels();
}
