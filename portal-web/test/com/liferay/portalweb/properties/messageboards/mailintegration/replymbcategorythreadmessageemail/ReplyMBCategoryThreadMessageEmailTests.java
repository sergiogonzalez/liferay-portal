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

package com.liferay.portalweb.properties.messageboards.mailintegration.replymbcategorythreadmessageemail;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmbsite.AddPageMBSiteTest;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmbsite.AddPortletMBSiteTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ReplyMBCategoryThreadMessageEmailTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(ConfigureServerAdministrationMailTest.class);
		testSuite.addTestSuite(EditPortalInstanceMailDomainTest.class);
		testSuite.addTestSuite(AddUserGmailTest.class);
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AssignMembersSiteUserTest.class);
		testSuite.addTestSuite(AddPageMBSiteTest.class);
		testSuite.addTestSuite(AddPortletMBSiteTest.class);
		testSuite.addTestSuite(ConfigureMBPortletEmailFromGmailServerTest.class);
		testSuite.addTestSuite(AddMBCategoryTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Gmail_SignInTest.class);
		testSuite.addTestSuite(Gmail_SubscribeMBCategoryTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessageTest.class);
		testSuite.addTestSuite(Gmail_ReplyMBCategoryThreadMessageEmailTest.class);
		testSuite.addTestSuite(ViewMBCategoryThreadMessageEmailTest.class);
		testSuite.addTestSuite(TearDownMBCategoryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);
		testSuite.addTestSuite(TearDownServerTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}
}