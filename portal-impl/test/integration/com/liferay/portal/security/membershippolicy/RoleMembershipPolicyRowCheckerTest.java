/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.rolesadmin.search.UserRoleChecker;

import javax.portlet.RenderResponse;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Roberto Díaz
 */
public class RoleMembershipPolicyRowCheckerTest
	extends BaseRoleMembershipPolicyTestCase {

	@Test
	public void testIsDisabledCheckerWhenSettingForbiddenRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role forbiddenRole = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		UserRoleChecker userRoleChecker = new UserRoleChecker(
			renderResponse, forbiddenRole);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		Assert.assertTrue(userRoleChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenSettingRequiredRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredRoleId = addRequiredRoles()[0];

		Role requiredRole = RoleLocalServiceUtil.getRole(requiredRoleId);

		UserRoleChecker userRoleChecker = new UserRoleChecker(
			renderResponse, requiredRole);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		Assert.assertFalse(userRoleChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingForbiddenRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role forbiddenRole = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		UserRoleChecker userRoleChecker = new UserRoleChecker(
			renderResponse, forbiddenRole);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		RoleLocalServiceUtil.addUserRole(user.getUserId(), forbiddenRoleId);

		Assert.assertFalse(userRoleChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingRequiredRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredRoleId = addRequiredRoles()[0];

		Role requiredRole = RoleLocalServiceUtil.getRole(requiredRoleId);

		UserRoleChecker userRoleChecker = new UserRoleChecker(
			renderResponse, requiredRole);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		RoleLocalServiceUtil.addUserRole(user.getUserId(), requiredRoleId);

		Assert.assertTrue(userRoleChecker.isDisabled(user));
	}

}