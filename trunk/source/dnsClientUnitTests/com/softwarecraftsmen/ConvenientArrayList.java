package com.softwarecraftsmen;

import com.softwarecraftsmen.dns.names.Name;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.resourceRecords.ResourceRecord;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConvenientArrayList<T> extends ArrayList<T>
{
	public ConvenientArrayList(final @NotNull T ... values)
	{
		super(java.util.Arrays.asList(values));
	}

	@NotNull
	public static <T> List<T> toList(final @NotNull T ... values)
	{
		return new ConvenientArrayList<T>(values);
	}

	public static List<ResourceRecord<? extends Name, ? extends Serializable>> toResourceRecordList(final @NotNull ResourceRecord<? extends Name, ? extends Serializable>... values)
	{
		return new ConvenientArrayList<ResourceRecord<? extends Name, ? extends Serializable>>(values);
	}
}
