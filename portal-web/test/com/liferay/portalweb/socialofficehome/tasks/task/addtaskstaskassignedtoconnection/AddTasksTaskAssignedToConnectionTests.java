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

package com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtoconnection;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTasksTaskAssignedToConnectionTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSOUserTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOUs_SignInTest.class);
		testSuite.addTestSuite(SOUs_AddAsConnectionCCUserTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOSignInTest.class);
		testSuite.addTestSuite(ConfirmNotificationsAddConnnectionTest.class);
		testSuite.addTestSuite(AddTasksTaskAssignedToConnectionTest.class);
		testSuite.addTestSuite(ViewTasksTaskAssignedToConnectionTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOUs_SignInTest.class);
		testSuite.addTestSuite(SOUs_ViewTasksTaskAssignedToConnectionTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOSignInTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownTasksTaskTest.class);

		return testSuite;
	}
}