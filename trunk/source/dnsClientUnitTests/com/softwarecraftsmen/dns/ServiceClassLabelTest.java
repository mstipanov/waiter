/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns;

import com.softwarecraftsmen.dns.ServiceClassLabel.ServiceClassLabelMustBeLessThan15CharactersException;
import static com.softwarecraftsmen.dns.ServiceClassLabel.serviceClass;
import org.junit.Test;

public class ServiceClassLabelTest
{
	@Test(expected = ServiceClassLabelMustBeLessThan15CharactersException.class)
	public void aServiceClassLabelMustBeLessThan14Characters()
	{
		serviceClass("012345678901234");
	}

	@Test(expected = ServiceClassLabelMustBeLessThan15CharactersException.class)
	public void aServiceClassLabelMustBeLessThan15CharactersIfItStartsWithAnUnderscore()
	{
		serviceClass("_012345678901234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void aServiceClassLabelCanNotBeEmpty()
	{
		serviceClass("");
	}
}
