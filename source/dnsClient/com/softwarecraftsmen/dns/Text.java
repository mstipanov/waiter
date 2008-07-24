package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.toString.ToString.string;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Arrays.asList;
import java.util.List;

public class Text implements Serializable
{
	private final List<String> lines;

	public Text(final @NotNull List<String> lines)
	{
		this.lines = lines;
		for (String line : lines)
		{
			if (line.length() > 255)
			{
				throw new IllegalArgumentException("Maximum length of a character string in DNS is 255 characters");
			}
		}
	}

	@NotNull
	public String toString()
	{
		return string(this, lines);
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

		final Text text = (Text) o;
		return lines.equals(text.lines);
	}

	public int hashCode()
	{
		return lines.hashCode();
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		for (String line : lines)
		{
			writer.writeCharacterString(line);
		}
	}

	@NotNull
	public static Text text(final @NotNull String ... lines)
	{
		return new Text(asList(lines));
	}
}
