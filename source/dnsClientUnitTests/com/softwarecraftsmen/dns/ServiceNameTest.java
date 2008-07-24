/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.ConvenientArrayList.toList;
import static com.softwarecraftsmen.dns.DomainName.domainName;
import static com.softwarecraftsmen.dns.ServiceName.serviceName;
import static com.softwarecraftsmen.dns.ServiceProtocolLabel.TCP;
import static com.softwarecraftsmen.dns.ServiceClassLabel.serviceClass;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.List;

public class ServiceNameTest
{
	@Test
	public void toLabelsMatchesExactStructure()
	{
		final List<String> labels = serviceName(serviceClass("http"), TCP, domainName("softwarecraftsmen.com")).toLabels();
		assertThat(toList("_http", "_tcp", "softwarecraftsmen", "com", ""), equalTo(labels));
	}
}
