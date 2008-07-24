/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.ConvenientArrayList.toList;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AbstractNameTest
{
	@Test
	public void toLabelsMatchesExactStructure()
	{
		final List<String> labels = new AbstractNameForTest().toLabels();
		Assert.assertThat(toList("www", "google", "com", ""), equalTo(labels));
	}

	private final static class AbstractNameForTest extends AbstractName
	{
		public AbstractNameForTest()
		{
			super("www", "google", "com");
		}
	}
}
