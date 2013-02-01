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

import com.liferay.portal.RoleMembershipException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.MembershipPolicy;
import com.liferay.portal.security.auth.MembershipPolicyFactory;
import com.liferay.portal.service.base.UserGroupRoleServiceBaseImpl;
import com.liferay.portal.service.permission.UserGroupRolePermissionUtil;

import java.util.ArrayList;
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

		// Membership policy

		_inspectMembershipPolicy(
			new long[] {userId}, roleIds, MembershipPolicy.ROLE_FORBIDDEN );

		userGroupRoleLocalService.addUserGroupRoles(userId, groupId, roleIds);
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		// Membership policy

		_inspectMembershipPolicy(
			userIds, new long[] {roleId}, MembershipPolicy.ROLE_FORBIDDEN );

		userGroupRoleLocalService.addUserGroupRoles(userIds, groupId, roleId);
	}

	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);
		}

		// Membership policy

		_inspectMembershipPolicy(
			new long[] {userId}, roleIds, MembershipPolicy.ROLE_MANDATORY );

		userGroupRoleLocalService.deleteUserGroupRoles(
			userId, groupId, roleIds);
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		// Membership policy

		_inspectMembershipPolicy(
			userIds, new long[] {roleId}, MembershipPolicy.ROLE_MANDATORY );

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, groupId, roleId);
	}

	private void _inspectMembershipPolicy(
			long[]userIds, long[] roleIds, int rolePolicyType)
		throws PortalException, SystemException {
		List<User> errorUsers = new ArrayList<User>();
		List<Role> errorRoles = new ArrayList<Role>();

		MembershipPolicy membershipPolicy =
			MembershipPolicyFactory.getInstance();

		for (long roleId : roleIds) {
			Role role = rolePersistence.findByPrimaryKey(roleId);

			for (long userId : userIds) {
				User user = userPersistence.findByPrimaryKey(userId);

				List<Role>policyRoles = null;

				if (rolePolicyType == MembershipPolicy.ROLE_MANDATORY) {
					policyRoles = membershipPolicy.getMandatoryRoles(
						user.getGroup(), user);
				}
				else if (rolePolicyType == MembershipPolicy.ROLE_FORBIDDEN) {
					policyRoles = membershipPolicy.getForbiddenRoles(
						user.getGroup(), user);
				}

				if (policyRoles.contains(role)) {
					errorUsers.add(user);
					errorRoles.add(role);
				}
			}
		}

		if (!errorUsers.isEmpty()) {
			throw new RoleMembershipException(
				rolePolicyType, errorRoles, errorUsers);
		}
	}

}