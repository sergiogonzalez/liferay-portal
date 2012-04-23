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

package com.liferay.portalweb.socialofficehome.notifications.notification.requestprofileaddasconnection;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialoffice.users.user.addsouser.AddSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.addsouser.TearDownSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.editsouserpassword.EditSOUserPasswordTest;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser.SelectRegularRolesSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs_SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignOutSOTest;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.sousaddasconnectionccuser.ConfirmNotificationsAddConnectionTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class RequestProfileAddConnnectionTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSOUserTest.class);
		testSuite.addTestSuite(SelectRegularRolesSOUserTest.class);
		testSuite.addTestSuite(EditSOUserPasswordTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_AddAsConnectionProfileUserTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ViewNotificationsAddAsConnectionTest.class);
		testSuite.addTestSuite(IgnoreNotificationsAddConnnectionTest.class);
		testSuite.addTestSuite(ViewIgnoreAddAsConnectionProfileUserTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_AddAsConnectionProfileUserTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ViewNotificationsAddAsConnectionTest.class);
		testSuite.addTestSuite(ConfirmNotificationsAddConnectionTest.class);
		testSuite.addTestSuite(ViewAddAsConnectionProfileUserTest.class);
		testSuite.addTestSuite(TearDownNotificationsTest.class);
		testSuite.addTestSuite(TearDownSOUserTest.class);

		return testSuite;
	}
}