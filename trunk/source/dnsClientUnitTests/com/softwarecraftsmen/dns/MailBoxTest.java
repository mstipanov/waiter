/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.ConvenientArrayList.toList;
import static com.softwarecraftsmen.dns.DomainName.domainName;
import static com.softwarecraftsmen.dns.MailBox.mailBox;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.List;

public class MailBoxTest
{
	@Test
	public void toLabelsMatchesExactStructure()
	{
		final List<String> labels = mailBox("raphael.james.cohn", domainName("softwarecraftsmen.com")).toLabels();
		assertThat(toList("raphael.james.cohn", "softwarecraftsmen", "com", ""), equalTo(labels));
	}
}
