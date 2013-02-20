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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicyUtil;
import com.liferay.portal.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.service.base.UserGroupRoleServiceBaseImpl;
import com.liferay.portal.service.permission.UserGroupRolePermissionUtil;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupRoleServiceImpl extends UserGroupRoleServiceBaseImpl {

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);
		}

		SiteMembershipPolicyUtil.checkAddRoles(
			new long[]{userId}, new long[]{groupId}, roleIds);

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.addUserGroupRoles(
				userId, groupId, roleIds);

		SiteMembershipPolicyUtil.propagateAddRoles(userGroupRoles);
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		SiteMembershipPolicyUtil.checkAddRoles(
			userIds, new long[]{groupId}, new long[]{roleId});

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.addUserGroupRoles(
				userIds, groupId, roleId);

		SiteMembershipPolicyUtil.propagateAddRoles(userGroupRoles);
	}

	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		long[] filteredRoleIds = roleIds;

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);

			Role role = roleLocalService.getRole(roleId);

			if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
				(role.getType() == RoleConstants.TYPE_SITE)) {

				if (SiteMembershipPolicyUtil.isRoleProtected(
						getPermissionChecker(), userId, groupId, roleId)) {

					filteredRoleIds = ArrayUtil.remove(filteredRoleIds, roleId);
				}
			}
		}

		if (filteredRoleIds.length == 0) {
			return;
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		SiteMembershipPolicyUtil.checkRemoveRoles(
			new long[]{userId}, new long[]{groupId}, roleIds);

		OrganizationMembershipPolicyUtil.checkRemoveRoles(
			new long[]{group.getOrganizationId()},
			new long[]{groupId}, roleIds);

		userGroupRoleLocalService.deleteUserGroupRoles(
			userId, groupId, roleIds);

		for (long roleId : roleIds) {
			Role role = rolePersistence.findByPrimaryKey(roleId);

			if (role.getType() == RoleConstants.TYPE_SITE) {
				SiteMembershipPolicyUtil.propagateRemoveRoles(
					userId, groupId, roleId);
			}
			else if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				OrganizationMembershipPolicyUtil.propagateRemoveRoles(
					userId, group.getOrganizationId(), roleId );
			}
		}
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		Role role = roleLocalService.getRole(roleId);

		if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
			(role.getType() == RoleConstants.TYPE_SITE)) {

			userIds = UsersAdminUtil.filterDeleteGroupRoleUserIds(
				getPermissionChecker(), groupId, role.getRoleId(), userIds);
		}

		if (userIds.length == 0) {
			return;
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		if (role.getType() == RoleConstants.TYPE_SITE) {
			SiteMembershipPolicyUtil.checkRemoveRoles(
				userIds, new long[] {groupId}, new long[] {roleId});
		}
		else if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			OrganizationMembershipPolicyUtil.checkRemoveRoles(
				userIds, new long[] {group.getOrganizationId()},
				new long[] {roleId});
		}

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, groupId, roleId);

		for (long userId : userIds) {

			if (role.getType() == RoleConstants.TYPE_SITE) {
				SiteMembershipPolicyUtil.propagateRemoveRoles(
					userId, groupId, roleId);
			}
			else if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				OrganizationMembershipPolicyUtil.propagateRemoveRoles(
					userId, group.getOrganizationId(), roleId);
			}
		}
	}

}