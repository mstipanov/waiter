package com.softwarecraftsmen;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import static java.util.Collections.emptySet;

public class Optional<T> implements Set<T>
{
	private Set<T> internalSet;
	private final T singleValue;

	private Optional()
	{
		internalSet = emptySet();
		singleValue = null;
	}

	public Optional(final @NotNull T singleValue)
	{
		internalSet = new LinkedHashSet<T>(1);
		this.singleValue = singleValue;
		internalSet.add(this.singleValue);
	}

	@Nullable
	public T value()
	{
		if (isEmpty())
		{
			throw new IllegalStateException("IsEmpty");
		}
		return singleValue;
	}

	public int size()
	{
		return internalSet.size();
	}

	public boolean isEmpty()
	{
		return internalSet.isEmpty();
	}

	public boolean contains(final @NotNull Object o)
	{
		return internalSet.contains(o);
	}

	@NotNull
	public Iterator<T> iterator()
	{
		return internalSet.iterator();
	}

	@NotNull
	public Object[] toArray()
	{
		return internalSet.toArray();
	}

	@NotNull
	public <T> T[] toArray(final @NotNull T[] a)
	{
		return internalSet.toArray(a);
	}

	public boolean add(final @NotNull T t)
	{
		throw new UnsupportedOperationException("add");
	}

	public boolean remove(final Object o)
	{
		throw new UnsupportedOperationException("remove");
	}

	public boolean containsAll(final @NotNull Collection<?> c)
	{
		return internalSet.containsAll(c);
	}

	public boolean addAll(final @NotNull Collection<? extends T> c)
	{
		throw new UnsupportedOperationException("addAll");
	}

	public boolean retainAll(final Collection<?> c)
	{
		throw new UnsupportedOperationException("retainAll");
	}

	public boolean removeAll(final Collection<?> c)
	{
		throw new UnsupportedOperationException("removeAll");
	}

	public void clear()
	{
		throw new UnsupportedOperationException("clear");
	}

	public static <T> Optional<T> empty()
	{
		return new Optional<T>();
	}
}
