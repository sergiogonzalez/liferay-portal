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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public class DefaultMembershipPolicy implements MembershipPolicy {

	public void checkAddUserToOrganization(
			long[] userIds, long[] organizationIds)
		throws PortalException, SystemException {
	}

	public void checkAddUserToSites(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {

		MembershipPolicyException membershipPolicyException = null;

		for (long userId : userIds) {
			User user = UserLocalServiceUtil.getUser(userId);

			for (long groupId : groupIds) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				if (group.getParentGroupId() == 0) {
					continue;
				}

		// Allow membership only to parent members

				Group parentGroup = group.getParentGroup();

				List<Group> userSites = getUserSites(user.getCompanyId(), user);

				if (userSites.contains(parentGroup)) {
					continue;
				}

				if (membershipPolicyException == null) {
					membershipPolicyException = new MembershipPolicyException(
						MembershipPolicyException.GROUP_MEMBERSHIP_NOT_ALLOWED);
				}

				if (!membershipPolicyException.getUsers().contains(user)) {
					membershipPolicyException.addUser(user);
				}

				if (!membershipPolicyException.getGroups().contains(group)) {
					membershipPolicyException.addGroup(group);
				}
			}
		}

		if (membershipPolicyException != null) {
			throw membershipPolicyException;
		}
	}

	public void checkAddUserToUserGroup(long[] userIds, long[] userGroupIds)
		throws PortalException, SystemException {
	}

	public void checkRemoveUserFromOrganization(
			long[] user, long[] organizationIds)
		throws PortalException, SystemException {
	}

	public void checkRemoveUserFromSite(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {

		MembershipPolicyException membershipPolicyException = null;

		//Inherit memberships from parent

		for (long userId : userIds) {
			User user = UserLocalServiceUtil.getUser(userId);

			for (long groupId : groupIds) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				if (group.getParentGroupId() == 0) {
					continue;
				}

				Group parentGroup = group.getParentGroup();

				List<Group> userSites = getUserSites(user.getCompanyId(), user);

				if (!userSites.contains(parentGroup)) {
					continue;
				}

				if (membershipPolicyException == null) {
					membershipPolicyException = new MembershipPolicyException(
						MembershipPolicyException.GROUP_MEMBERSHIP_NOT_ALLOWED);
				}

				if (!membershipPolicyException.getUsers().contains(user)) {
					membershipPolicyException.addUser(user);
				}

				if (!membershipPolicyException.getGroups().contains(group)) {
					membershipPolicyException.addGroup(group);
				}
			}
		}

		if (membershipPolicyException != null) {
			throw membershipPolicyException;
		}
	}

	public void checkRemoveUserFromUserGroup(
			long[] userIds, long[] userGroupIds)
		throws PortalException, SystemException {
	}

	public void checkSetOrganizationRoleToUser(
			long[] userIds, long[] organizations, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void checkSetRoleToUser(long[] userIds, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void checkSetUserGroupRoleToUser(
			long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void checkUnsetOrganizationRoleToUser(
			long[] userIds, long[] organizationIds, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void checkUnsetRoleToUser(long[] userIds, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void checkUnsetUserGroupRoleToUser(
			long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void membershipPolicyVerifier()
		throws PortalException, SystemException {
		long[] companyIds = PortalUtil.getCompanyIds();

		for (long companyId : companyIds) {
			siteMembershipVerifier(companyId);
		}
	}

	private List<Group> getUserSites(long companyId, User user)
		throws PortalException, SystemException {
		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		if (user != null) {
			groupParams.put("inherit", Boolean.FALSE);
			groupParams.put("usersGroups", user.getUserId());
		}

		groupParams.put("site", Boolean.TRUE);

		return GroupLocalServiceUtil.search(
			companyId, groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	private void siteMembershipVerifier(long companyId)
		throws PortalException, SystemException {
		List<Group> userSites = getUserSites(companyId, null);

		for (Group group : userSites) {

			if (group.getParentGroupId() == 0) {
				continue;
			}

			List<User> parentGroupUsers = UserLocalServiceUtil.getGroupUsers(
				group.getParentGroupId());

			List<User> currentGroupUsers = UserLocalServiceUtil.getGroupUsers(
				group.getGroupId());

			//Inherit membership from parent

			long[] inheritUserIds = null;

			for (User user : parentGroupUsers) {
				if (currentGroupUsers.contains(user)) {
					continue;
				}

				if (inheritUserIds == null) {
					inheritUserIds = new long[]{user.getUserId()};
				}
				else {
					inheritUserIds = ArrayUtil.append(
						inheritUserIds, user.getUserId());
				}
			}

			if ((inheritUserIds != null) && (inheritUserIds.length > 0)) {
				UserLocalServiceUtil.addGroupUsers(
					group.getGroupId(), inheritUserIds);
			}

			// Allow membership only to parent members

			long[] notAllowedtUserIds = null;

			for (User user : currentGroupUsers) {
				if (parentGroupUsers.contains(user)) {
					continue;
				}

				if (notAllowedtUserIds == null) {
					notAllowedtUserIds = new long[]{user.getUserId()};
				}
				else {
					notAllowedtUserIds = ArrayUtil.append(
						notAllowedtUserIds, user.getUserId());
				}
			}

			if ((notAllowedtUserIds != null) &&
				(notAllowedtUserIds.length > 0)) {
				UserLocalServiceUtil.unsetGroupUsers(
					group.getGroupId(), notAllowedtUserIds, null);
			}
		}
	}

}