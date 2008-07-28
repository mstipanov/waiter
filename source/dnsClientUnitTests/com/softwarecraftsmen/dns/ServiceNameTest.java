/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.ConvenientArrayList;
import static com.softwarecraftsmen.dns.DomainName.domainName;
import static com.softwarecraftsmen.dns.ServiceLabel.serviceLabel;
import static com.softwarecraftsmen.dns.ServiceName.serviceName;
import static com.softwarecraftsmen.dns.ServiceProtocolLabel.TCP;
import static com.softwarecraftsmen.dns.SimpleLabel.simpleLabel;
import static com.softwarecraftsmen.dns.SimpleLabel.Empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.List;

public class ServiceNameTest
{
	@Test
	public void toLabelsMatchesExactStructure()
	{
		final List<Label> labels = serviceName(serviceLabel("http"), TCP, domainName("softwarecraftsmen.com")).toLabels();
		assertThat(new ConvenientArrayList<Label>(serviceLabel("http"), TCP, simpleLabel("softwarecraftsmen"), simpleLabel("com"), Empty), equalTo(labels));
	}
}
