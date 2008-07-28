/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging;

import static com.softwarecraftsmen.toString.ToString.string;
import com.softwarecraftsmen.dns.names.Name;
import static com.softwarecraftsmen.dns.messaging.QClass.Internet;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Question implements Serializable
{
	private final Name name;
	private final InternetClassType internetClassType;
	private final QClass qClass;

	public Question(final @NotNull Name name, final @NotNull InternetClassType internetClassType, final @NotNull QClass qClass)
	{
		this.name = name;
		this.internetClassType = internetClassType;
		this.qClass = qClass;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		name.serialize(writer);
		internetClassType.serialize(writer);
		qClass.serialize(writer);
	}

	@NotNull
	public static Question internetQuestion(final @NotNull Name name, final @NotNull InternetClassType internetClassType)
	{
		return new Question(name, internetClassType, Internet);
	}

	public boolean equals(final @Nullable Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final Question question = (Question) o;
		if (internetClassType != question.internetClassType)
		{
			return false;
		}
		if (!name.equals(question.name))
		{
			return false;
		}
		//noinspection RedundantIfStatement
		if (qClass != question.qClass)
		{
			return false;
		}
		return true;
	}

	public int hashCode()
	{
		int result;
		result = name.hashCode();
		result = 31 * result + internetClassType.hashCode();
		result = 31 * result + qClass.hashCode();
		return result;
	}

	@NotNull
	public String toString()
	{
		return string(this, name, internetClassType, qClass);
	}
}
