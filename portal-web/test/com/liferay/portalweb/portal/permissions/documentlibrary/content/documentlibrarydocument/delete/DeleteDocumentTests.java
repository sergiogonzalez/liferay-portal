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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.delete;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteDocumentTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddMemberTest.class);
		testSuite.addTestSuite(AddMemberRoleTest.class);
		testSuite.addTestSuite(DefineMemberRoleTest.class);
		testSuite.addTestSuite(RemoveRolePowerUserTest.class);
		testSuite.addTestSuite(AssignUserRolesTest.class);
		testSuite.addTestSuite(AddDocumentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertCannotDeleteDLDocumentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(AddPermissionDeleteDLDocumentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_DeleteDocumentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(TearDownDLContentTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownRolesTest.class);

		return testSuite;
	}
}