/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.editorganizationteam;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.TearDownOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationsite.EditOrganizationSiteTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.addorganizationteam.AddOrganizationTeamTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EditOrganizationTeamTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddOrganizationTest.class);
		testSuite.addTestSuite(EditOrganizationSiteTest.class);
		testSuite.addTestSuite(AddOrganizationTeamTest.class);
		testSuite.addTestSuite(EditOrganizationTeamTest.class);
		testSuite.addTestSuite(ViewEditOrganizationTeamTest.class);
		testSuite.addTestSuite(TearDownOrganizationTest.class);

		return testSuite;
	}
}