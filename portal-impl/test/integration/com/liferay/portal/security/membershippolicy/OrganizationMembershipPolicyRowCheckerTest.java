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

import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.sites.search.OrganizationRoleUserChecker;
import com.liferay.portlet.usersadmin.search.UserOrganizationChecker;

import javax.portlet.RenderResponse;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Roberto Díaz
 */
public class OrganizationMembershipPolicyRowCheckerTest
	extends BaseOrganizationMembershipPolicyTestCase {

	@Test
	public void testIsDisabledCheckerWhenSettingForbiddenOrganizationToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenOrganizationId = addForbiddenOrganizations()[0];

		Organization forbiddenOrganization =
			OrganizationLocalServiceUtil.getOrganization(
				forbiddenOrganizationId);

		UserOrganizationChecker userOrganizationChecker =
			new UserOrganizationChecker(renderResponse, forbiddenOrganization);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		Assert.assertTrue(userOrganizationChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenSettingForbiddenRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		OrganizationRoleUserChecker organizationRoleUserChecker =
			new OrganizationRoleUserChecker(renderResponse, organization, role);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		Assert.assertTrue(organizationRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenSettingRequiredOrganizationToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredOrganizationId = addRequiredOrganizations()[0];

		Organization requiredOrganization =
			OrganizationLocalServiceUtil.getOrganization(
				requiredOrganizationId);

		UserOrganizationChecker userOrganizationChecker =
			new UserOrganizationChecker(renderResponse, requiredOrganization);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		Assert.assertFalse(userOrganizationChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenSettingRequiredRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredRoleId = addRequiredRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(requiredRoleId);

		OrganizationRoleUserChecker organizationRoleUserChecker =
			new OrganizationRoleUserChecker(renderResponse, organization, role);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		Assert.assertFalse(organizationRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingForbiddenOrganizationToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenOrganizationId = addForbiddenOrganizations()[0];

		Organization forbiddenOrganization =
			OrganizationLocalServiceUtil.getOrganization(
				forbiddenOrganizationId);

		UserOrganizationChecker userOrganizationChecker =
			new UserOrganizationChecker(renderResponse, forbiddenOrganization);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		OrganizationLocalServiceUtil.addUserOrganization(
			user.getUserId(), forbiddenOrganizationId);

		Assert.assertFalse(userOrganizationChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingForbiddenRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		OrganizationRoleUserChecker organizationRoleUserChecker =
			new OrganizationRoleUserChecker(renderResponse, organization, role);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), organization.getGroupId(),
			new long[] {forbiddenRoleId});

		Assert.assertFalse(organizationRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingRequiredOrganizationToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredOrganizationId = addRequiredOrganizations()[0];

		Organization requiredOrganization =
			OrganizationLocalServiceUtil.getOrganization(
				requiredOrganizationId);

		UserOrganizationChecker userOrganizationChecker =
			new UserOrganizationChecker(renderResponse, requiredOrganization);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		OrganizationLocalServiceUtil.addUserOrganization(
			user.getUserId(), requiredOrganizationId);

		Assert.assertTrue(userOrganizationChecker.isDisabled(user));
	}

	@Test
	public void testIsDisabledCheckerWhenUnsettingRequiredRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredRoleId = addRequiredRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(requiredRoleId);

		OrganizationRoleUserChecker organizationRoleUserChecker =
			new OrganizationRoleUserChecker(renderResponse, organization, role);

		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), organization.getGroupId(),
			new long[] {requiredRoleId});

		Assert.assertTrue(organizationRoleUserChecker.isDisabled(user));
	}

}