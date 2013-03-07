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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 */

public class DefaultSiteMembershipPolicyImpl 
	extends BaseSit {

	public void checkAddMembership(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {

		MembershipPolicyException mpe = null;

		for (long groupId : groupIds) {

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.getParentGroupId() == 0) {
				continue;
			}

			int membershipPolicyType = _getMembershipPolicyType(group);

			if (membershipPolicyType !=
					MembershipPolicyKeys.ONLY_ALLOW_PARENT_SITE_MEMBERSHIPS) {

				continue;
			}

			long[] parentGroupIds = UserLocalServiceUtil.getGroupUserIds(
				group.getParentGroupId());

			for (long userId : userIds) {
				User user = UserLocalServiceUtil.getUser(userId);

				if (ArrayUtil.contains(parentGroupIds,userId)){
					continue;
				}

				if (mpe == null) {
					mpe = new MembershipPolicyException(
						MembershipPolicyException.
							SITE_MEMBERSHIP_NOT_ALLOWED);
				}

				if (!mpe.getGroups().contains(group)) {
					mpe.addGroup(group);
				}

				if (!mpe.getUsers().contains(user)) {
					mpe.addUser(user);
				}
			}
		}

		if (mpe != null) {
			throw mpe;
		}
	}

	public void checkAddRoles(long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void checkRemoveMembership(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {

		MembershipPolicyException mpe = null;

		for (long groupId : groupIds) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.getParentGroupId() == 0) {
				return;
			}

			int membershipPolicyType = _getMembershipPolicyType(group);

			if (membershipPolicyType ==
					MembershipPolicyKeys.INDEPENDENT_SITE_MEMBERSHIPS) {

				continue;
			}
			else if (membershipPolicyType ==
				MembershipPolicyKeys.INHERIT_PARENT_SITE_MEMBERSHIPS) {

				List<User> inheritGroupUsers =
					UserLocalServiceUtil.getInheritGroupUsers(groupId);

				for (long userId : userIds) {
					User user = UserLocalServiceUtil.getUser(userId);

					if (inheritGroupUsers.contains(user)) {
						continue;
					}

					if (mpe == null) {
						mpe = new MembershipPolicyException(
							MembershipPolicyException.SITE_MEMBERSHIP_REQUIRED);
					}

					if (!mpe.getUsers().contains(user)) {
						mpe.addUser(user);
					}

					if (!mpe.getGroups().contains(group)) {
						mpe.addGroup(group);
					}
				}
			}
		}

		if (mpe != null) {
			throw mpe;
		}
	}

	public void checkRemoveRoles(
		long[] userIds, long[] groupIds, long[] roleIds)
		throws PortalException, SystemException {
	}

	public void propagateAddMembership(long[] userIds, long groupId)
		throws PortalException, SystemException {
	}

	public void propagateAddRoles(List<UserGroupRole> userGroupRole)
		throws PortalException, SystemException {
	}

	public void propagateRemoveMembership(long[] userIds, long groupId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		List<Group>childGroups = group.getChildren(true);

		for (Group childGroup : childGroups) {
			int childMembershipPolicyType =
				_getMembershipPolicyType(childGroup);

			if (childMembershipPolicyType !=
					MembershipPolicyKeys.ONLY_ALLOW_PARENT_SITE_MEMBERSHIPS) {

				continue;
			}

			UserLocalServiceUtil.unsetGroupUsers(
				childGroup.getGroupId(), userIds, null);

			propagateRemoveMembership(userIds, childGroup.getGroupId());
		}
	}

	public void propagateRemoveRoles(long userId, long groupId, long roleId)
		throws PortalException, SystemException {
	}

	public void verifyPolicy(Group group)
		throws PortalException, SystemException {

		if (group.getParentGroupId() == 0) {
			return;
		}

		int membershipPolicyType = _getMembershipPolicyType(group);

		if (membershipPolicyType ==
				MembershipPolicyKeys.ONLY_ALLOW_PARENT_SITE_MEMBERSHIPS) {

			verifyOnlyAllowParentSiteMembershipsPolicy(group);
		}
		else if (membershipPolicyType ==
				MembershipPolicyKeys.INDEPENDENT_SITE_MEMBERSHIPS) {
			List<Group> childGroups = group.getChildren(true);

			for (Group childGroup : childGroups) {
				verifyPolicy(childGroup);
			}
		}
	}

	public void verifyPolicy(Role role)
		throws PortalException, SystemException {
	}

	public void verifyUpdatePolicy(
			Group group, Group oldGroup,
			List<AssetCategory> oldAssetCatergories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException {

		if (group.getParentGroupId() == 0) {
			return;
		}

		if (group.getParentGroup() != oldGroup.getParentGroup()) {
			verifyPolicy(group);
		}
	}

	public void verifyUpdatePolicy(
			Group group, Group oldGroup, String oldTypeSettings)
		throws PortalException, SystemException {

		verifyPolicy(group);
	}

	private int _getMembershipPolicyType(Group group)  {
		try {
			return Integer.valueOf(
				group.getTypeSettingsProperty(
					MembershipPolicyKeys.MEMBERSHIP_POLICY_TYPE));
		}
		catch (Exception e) {
			return MembershipPolicyKeys.INDEPENDENT_SITE_MEMBERSHIPS;
		}
	}

	private void verifyOnlyAllowParentSiteMembershipsPolicy(Group group)
		throws PortalException, SystemException {

		long[] currentGroupUsersIds = UserLocalServiceUtil.getGroupUserIds(
			group.getGroupId());

		Group newParentGroup = group.getParentGroup();

		long[] newParentGroupUsersIds = UserLocalServiceUtil.getGroupUserIds(
			newParentGroup.getGroupId());

		for (long newParentGroupUserId : newParentGroupUsersIds) {
			currentGroupUsersIds = ArrayUtil.remove(
				currentGroupUsersIds, newParentGroupUserId);
		}

		if (currentGroupUsersIds == null) {
			return;
		}

		UserLocalServiceUtil.unsetGroupUsers(
			group.getGroupId(), currentGroupUsersIds, null);

		propagateRemoveMembership(
			newParentGroupUsersIds, group.getGroupId());
	}

}
