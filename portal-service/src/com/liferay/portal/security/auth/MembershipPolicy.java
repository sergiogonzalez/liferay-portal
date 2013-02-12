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
public interface MembershipPolicy {

	public void checkAddUserToOrganization(
			long[] userIds, long[] organizationIds)
		throws PortalException, SystemException;

	public void checkAddUserToSites(long[] userIds, long[] groupIds)
		throws PortalException, SystemException;

	public void checkAddUserToUserGroup(long[] userIds, long[] userGroupIds)
		throws PortalException, SystemException;

	public void checkRemoveUserFromOrganization(
			long[] userIds, long[] organizationIds)
		throws PortalException, SystemException;

	public void checkRemoveUserFromSite(long[] userIds, long[] groupIds)
		throws PortalException, SystemException;

	public void checkRemoveUserFromUserGroup(
			long[] userIds, long[] userGroupIds)
		throws PortalException, SystemException;

	public void checkSetOrganizationRoleToUser(
			long[] userIds, long[] organizationIds, long[] roleIds)
		throws PortalException, SystemException;

	public void checkSetRoleToUser(long[] userIds, long[] roleIds)
		throws PortalException, SystemException;

	public void checkSetUserGroupRoleToUser(
			long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException;

	public void checkUnsetOrganizationRoleToUser(
			long[] userIds, long[] organizationIds, long[] roleIds)
		throws PortalException, SystemException;

	public void checkUnsetRoleToUser(long[] userIds, long[] roleIds)
		throws PortalException, SystemException;

	public void checkUnsetUserGroupRoleToUser(
			long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException;

	public void membershipPolicyVerifier()
		throws PortalException, SystemException;

}