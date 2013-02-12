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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public class MembershipPolicyUtil {

	public static void checkAddUserToOrganization(
			long[] user, long[] organizationIds)
		throws PortalException, SystemException {
		getInstance().checkAddUserToOrganization(user, organizationIds);
	}

	public static void checkAddUserToSites(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {
		getInstance().checkAddUserToSites(userIds, groupIds);
	}

	public static void checkAddUserToUserGroup(
			long[] userIds, long[] userGroupIds)
		throws PortalException, SystemException {
		getInstance().checkAddUserToUserGroup(userIds, userGroupIds);
	}

	public static void checkRemoveUserFromOrganization(
			long[] userIds, long[] organizationIds)
		throws PortalException, SystemException {
		getInstance().checkRemoveUserFromOrganization(userIds, organizationIds);
	}

	public static void checkRemoveUserFromSite(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {
		getInstance().checkRemoveUserFromSite(userIds, groupIds);
	}

	public static void checkRemoveUserFromUserGroup(
			long[] userIds, long[] userGroupIds)
		throws PortalException, SystemException {
		getInstance().checkRemoveUserFromUserGroup(userIds, userGroupIds);
	}

	public static void checkSetOrganizationRoleToUser(
			long[] userIds, long[] organizationIds, long[] roleIds)
		throws PortalException, SystemException {
		getInstance().checkSetOrganizationRoleToUser(
			userIds, organizationIds, roleIds);
	}

	public static void checkSetRoleToUser(long[] userIds, long[] roleIds)
		throws PortalException, SystemException {
		getInstance().checkSetRoleToUser(userIds, roleIds);
	}

	public static void checkSetUserGroupRoleToUser(
			long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {
		getInstance().checkSetUserGroupRoleToUser(userIds, groupIds, roleIds);
	}

	public static void checkUnsetOrganizationRoleToUser(
			long[] userIds, long[] organizationIds, long[] roleIds)
		throws PortalException, SystemException {
		getInstance().checkUnsetOrganizationRoleToUser(
			userIds, organizationIds, roleIds);
	}

	public static void checkUnsetRoleToUser(long[] userIds, long[] roleIds)
		throws PortalException, SystemException {
		getInstance().checkUnsetRoleToUser(userIds, roleIds);
	}

	public static void checkUnsetUserGroupRoleToUser(
			long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {
		getInstance().checkUnsetUserGroupRoleToUser(userIds, groupIds, roleIds);
	}

	public static void membershipPolicyVerfier()
		throws PortalException, SystemException {
		getInstance().membershipPolicyVerifier();
	}

	private static MembershipPolicy getInstance() {
		return MembershipPolicyFactoryUtil.getMembershipPolicy();
	}

}