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

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.sites.search.UserGroupRoleRoleChecker;
import com.liferay.portlet.sites.search.UserGroupRoleUserChecker;
import com.liferay.portlet.usergroupsadmin.search.UserGroupChecker;

import javax.portlet.RenderResponse;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Roberto Díaz
 */
public class SiteMembershipPolicyRowCheckerTest
	extends BaseSiteMembershipPolicyTestCase {

	@Test
	public void testIsDisabledCheckerWhenSettingForbiddenGroupToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenGroupId = addForbiddenGroups()[0];

		Group forbiddenGroup = GroupLocalServiceUtil.getGroup(forbiddenGroupId);

		UserGroupChecker userGroupChecker = new UserGroupChecker(
			renderResponse, forbiddenGroup);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		Assert.assertTrue(userGroupChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenSettingForbiddenRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		UserGroupRoleUserChecker userGroupRoleUserChecker =
			new UserGroupRoleUserChecker(renderResponse, group, role);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		Assert.assertTrue(userGroupRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenSettingRequiredGroupToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredGroupId = addRequiredGroups()[0];

		Group requiredGroup = GroupLocalServiceUtil.getGroup(requiredGroupId);

		UserGroupChecker userGroupChecker = new UserGroupChecker(
			renderResponse, requiredGroup);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		Assert.assertFalse(userGroupChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenSettingRequiredRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredRoleId = addRequiredRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(requiredRoleId);

		UserGroupRoleUserChecker userGroupRoleUserChecker =
			new UserGroupRoleUserChecker(renderResponse, group, role);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		Assert.assertFalse(userGroupRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenSettingUserToForbiddenRole()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		UserGroupRoleRoleChecker userGroupRoleRoleChecker =
			new UserGroupRoleRoleChecker(renderResponse, user, group);

		Role role = RoleLocalServiceUtil.getRole(addForbiddenRoles()[0]);

		Assert.assertTrue(userGroupRoleRoleChecker.isDisabled(role));
	}

	@Test
	public void testIsDisabledCheckerWhenSettingUserToRequiredRole()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		UserGroupRoleRoleChecker userGroupRoleRoleChecker =
			new UserGroupRoleRoleChecker(renderResponse, user, group);

		Role role = RoleLocalServiceUtil.getRole(addRequiredRoles()[0]);

		Assert.assertFalse(userGroupRoleRoleChecker.isDisabled(role));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingForbiddenGroupFromUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenGroupId = addForbiddenGroups()[0];

		Group forbiddenGroup = GroupLocalServiceUtil.getGroup(forbiddenGroupId);

		UserGroupChecker userGroupChecker = new UserGroupChecker(
			renderResponse, forbiddenGroup);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		GroupLocalServiceUtil.addUserGroup(user.getUserId(), forbiddenGroupId);

		Assert.assertFalse(userGroupChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingForbiddenRoleFromUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		UserGroupRoleUserChecker userGroupRoleUserChecker =
			new UserGroupRoleUserChecker(renderResponse, group, role);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), new long[] {forbiddenRoleId});

		Assert.assertFalse(userGroupRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingRequiredGroupFromUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredGroupId = addRequiredGroups()[0];

		Group requiredGroup = GroupLocalServiceUtil.getGroup(requiredGroupId);

		UserGroupChecker userGroupChecker = new UserGroupChecker(
			renderResponse, requiredGroup);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		GroupLocalServiceUtil.addUserGroup(user.getUserId(), requiredGroupId);

		Assert.assertTrue(userGroupChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingRequiredRoleFromUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredRoleId = addRequiredRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(requiredRoleId);

		UserGroupRoleUserChecker userGroupRoleUserChecker =
			new UserGroupRoleUserChecker(renderResponse, group, role);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), new long[] {requiredRoleId});

		Assert.assertTrue(userGroupRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingUserFromForbiddenRole()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		UserGroupRoleRoleChecker userGroupRoleRoleChecker =
			new UserGroupRoleRoleChecker(renderResponse, user, group);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), new long[] {forbiddenRoleId});

		Assert.assertFalse(userGroupRoleRoleChecker.isDisabled(role));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingUserFromRequiredRole()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		UserGroupRoleRoleChecker userGroupRoleRoleChecker =
			new UserGroupRoleRoleChecker(renderResponse, user, group);

		long requiredRoleId = addRequiredRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(requiredRoleId);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), new long[] {requiredRoleId});

		Assert.assertTrue(userGroupRoleRoleChecker.isDisabled(role));
	}

}