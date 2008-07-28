package com.softwarecraftsmen.dns.names;

import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.labels.Label;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Name<L extends Label> extends Serializable
{
	@NotNull
	List<L> toLabels();
}
