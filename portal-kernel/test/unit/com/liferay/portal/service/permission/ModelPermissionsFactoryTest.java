/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jorge Ferrer
 */
@PrepareForTest({ResourceActionsUtil.class, RoleLocalServiceUtil.class})
@RunWith(PowerMockRunner.class)
public class ModelPermissionsFactoryTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		mockStatic(RoleLocalServiceUtil.class);

		Role guestRole = Mockito.mock(Role.class);

		Mockito.when(
			guestRole.getName()
		).thenReturn(
			RoleConstants.GUEST
		);

		when(
			RoleLocalServiceUtil.getRole(
				Mockito.anyLong(), Mockito.eq(RoleConstants.GUEST))
		).thenReturn(
			guestRole
		);

		Role organizationUserRole = Mockito.mock(Role.class);

		Mockito.when(
			organizationUserRole.getName()
		).thenReturn(
			RoleConstants.ORGANIZATION_USER
		);

		when(
			RoleLocalServiceUtil.getRole(
				Mockito.anyLong(), Mockito.eq(RoleConstants.ORGANIZATION_USER))
		).thenReturn(
			organizationUserRole
		);

		Role powerUserRole = Mockito.mock(Role.class);

		Mockito.when(
			powerUserRole.getName()
		).thenReturn(
			RoleConstants.POWER_USER
		);

		when(
			RoleLocalServiceUtil.getRole(
				Mockito.anyLong(), Mockito.eq(RoleConstants.POWER_USER))
		).thenReturn(
			powerUserRole
		);

		Role siteMemberRole = Mockito.mock(Role.class);

		Mockito.when(
			siteMemberRole.getName()
		).thenReturn(
			RoleConstants.SITE_MEMBER
		);

		when(
			RoleLocalServiceUtil.getDefaultGroupRole(Mockito.anyLong())
		).thenReturn(
			siteMemberRole
		);

		when(
			RoleLocalServiceUtil.getRole(
				Mockito.anyLong(), Mockito.eq(RoleConstants.SITE_MEMBER))
		).thenReturn(
			siteMemberRole
		);
	}

	@Test
	public void testCreateWithEmptyPermissions() throws Exception {
		String[] groupPermissions = {};
		String[] guestPermissions = {};

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, guestPermissions);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertEquals(roleNames.toString(), 0, roleNames.size());
	}

	@Test
	public void testCreateWithGroupPermissions() throws Exception {
		String[] groupPermissions = {ActionKeys.VIEW};
		String[] guestPermissions = {};

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, guestPermissions);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertEquals(roleNames.toString(), 1, roleNames.size());

		String roleName = roleNames.iterator().next();

		Assert.assertEquals(
			RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE, roleName);
		Assert.assertEquals(
			ListUtil.fromArray(groupPermissions),
			modelPermissions.getActionIdsList(roleName));
	}

	@Test
	public void testCreateWithGuestAndGroupPermissions() throws Exception {
		String[] groupPermissions = {ActionKeys.VIEW};
		String[] guestPermissions = {ActionKeys.VIEW};

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, guestPermissions);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertEquals(roleNames.toString(), 2, roleNames.size());

		Collection<String> viewActionIdRoleNames =
			modelPermissions.getRoleNames(ActionKeys.VIEW);

		Assert.assertEquals(
			viewActionIdRoleNames.toString(), 2, viewActionIdRoleNames.size());
	}

	@Test
	public void testCreateWithGuestPermissions() throws Exception {
		String[] groupPermissions = {};
		String[] guestPermissions = {ActionKeys.VIEW};

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, guestPermissions);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertEquals(roleNames.toString(), 1, roleNames.size());

		String roleName = roleNames.iterator().next();

		Assert.assertEquals(RoleConstants.GUEST, roleName);
		Assert.assertArrayEquals(
			guestPermissions, modelPermissions.getActionIds(roleName));
	}

	@Test
	public void testCreateWithNullPermissions() throws Exception {
		String[] groupPermissions = null;
		String[] guestPermissions = null;

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, guestPermissions);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertTrue(roleNames.toString(), roleNames.isEmpty());
	}

	@Test
	public void testCreateWithoutParameters() throws Exception {
		Map<String, String[]> parameterMap = new HashMap<>();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameters(parameterMap);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			mockHttpServletRequest);

		Assert.assertNull(modelPermissions);
	}

	@Test
	public void testCreateWithoutParametersAndWithClassName() throws Exception {
		Map<String, String[]> parameterMap = new HashMap<>();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameters(parameterMap);

		String className = RandomTestUtil.randomString();

		mockStatic(ResourceActionsUtil.class);

		when(
			ResourceActionsUtil.getModelResourceGroupDefaultActions(className)
		).thenReturn(
			Arrays.asList(ActionKeys.VIEW)
		);

		when(
			ResourceActionsUtil.getModelResourceGuestDefaultActions(className)
		).thenReturn(
			Arrays.asList(ActionKeys.VIEW)
		);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			mockHttpServletRequest, className);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertEquals(roleNames.toString(), 2, roleNames.size());

		Assert.assertTrue(roleNames.contains(RoleConstants.GUEST));

		Assert.assertTrue(
			roleNames.contains(RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE));

		Assert.assertArrayEquals(
			new String[] {ActionKeys.VIEW},
			modelPermissions.getActionIds(RoleConstants.GUEST));

		Assert.assertArrayEquals(
			new String[] {ActionKeys.VIEW},
			modelPermissions.getActionIds(
				RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE));
	}

	@Test
	public void testCreateWithParameterForOneRole() throws Exception {
		Map<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX +
				RoleConstants.GUEST,
			new String[] {ActionKeys.VIEW});

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameters(parameterMap);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			mockHttpServletRequest);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertEquals(roleNames.toString(), 1, roleNames.size());

		Assert.assertTrue(roleNames.contains(RoleConstants.GUEST));

		Assert.assertArrayEquals(
			new String[] {ActionKeys.VIEW},
			modelPermissions.getActionIds(RoleConstants.GUEST));
	}

	@Test
	public void testCreateWithParameterForOneRoleAndClassName()
		throws Exception {

		Map<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX +
				RoleConstants.GUEST,
			new String[] {ActionKeys.VIEW});

		String className = RandomTestUtil.randomString();

		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX + className +
				RoleConstants.GUEST,
			new String[] {ActionKeys.UPDATE, ActionKeys.VIEW});

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameters(parameterMap);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			mockHttpServletRequest, className);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertEquals(roleNames.toString(), 1, roleNames.size());

		Assert.assertTrue(roleNames.contains(RoleConstants.GUEST));

		Assert.assertArrayEquals(
			new String[] {ActionKeys.UPDATE, ActionKeys.VIEW},
			modelPermissions.getActionIds(RoleConstants.GUEST));
	}

	@Test
	public void testCreateWithParameterForTwoRoles() throws Exception {
		Map<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX +
				RoleConstants.GUEST,
			new String[] {ActionKeys.VIEW});
		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX +
				RoleConstants.SITE_MEMBER,
			new String[] {ActionKeys.UPDATE, ActionKeys.VIEW});

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameters(parameterMap);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			mockHttpServletRequest);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertEquals(roleNames.toString(), 2, roleNames.size());

		Assert.assertTrue(roleNames.contains(RoleConstants.GUEST));
		Assert.assertTrue(roleNames.contains(RoleConstants.SITE_MEMBER));

		Assert.assertArrayEquals(
			new String[] {ActionKeys.VIEW},
			modelPermissions.getActionIds(RoleConstants.GUEST));
		Assert.assertArrayEquals(
			new String[] {ActionKeys.UPDATE, ActionKeys.VIEW},
			modelPermissions.getActionIds(RoleConstants.SITE_MEMBER));
	}

	@Test
	public void testCreateWithParameterForTwoRolesAndClassName()
		throws Exception {

		Map<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX +
				RoleConstants.GUEST,
			new String[] {ActionKeys.VIEW});
		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX +
				RoleConstants.SITE_MEMBER,
			new String[] {ActionKeys.VIEW});

		String className = RandomTestUtil.randomString();

		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX + className +
				RoleConstants.POWER_USER,
			new String[] {ActionKeys.UPDATE, ActionKeys.VIEW});
		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX + className +
				RoleConstants.ORGANIZATION_USER,
			new String[] {ActionKeys.DELETE, ActionKeys.VIEW});

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameters(parameterMap);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			mockHttpServletRequest, className);

		Collection<String> roleNames = modelPermissions.getRoleNames();

		Assert.assertEquals(roleNames.toString(), 2, roleNames.size());

		Assert.assertTrue(roleNames.contains(RoleConstants.POWER_USER));
		Assert.assertTrue(roleNames.contains(RoleConstants.ORGANIZATION_USER));

		Assert.assertArrayEquals(
			new String[] {ActionKeys.UPDATE, ActionKeys.VIEW},
			modelPermissions.getActionIds(RoleConstants.POWER_USER));
		Assert.assertArrayEquals(
			new String[] {ActionKeys.DELETE, ActionKeys.VIEW},
			modelPermissions.getActionIds(RoleConstants.ORGANIZATION_USER));
	}

}