/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.ConvenientArrayList.toList;
import com.softwarecraftsmen.dns.messaging.GenericName;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.List;

public class GenericNameTest
{
	@Test
	public void toLabelsMatchesExactStructure()
	{
		final List<String> labels = new GenericName(toList("www", "google", "com", "")).toLabels();
		assertThat(toList("www", "google", "com", ""), equalTo(labels));
	}
}
