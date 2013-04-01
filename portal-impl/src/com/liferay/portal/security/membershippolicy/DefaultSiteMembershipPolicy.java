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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 */
public class DefaultSiteMembershipPolicy extends BaseSiteMembershipPolicy {

	public void checkMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException, SystemException {

		if (addGroupIds != null) {
			checkAddUsersLimitedGroup(userIds, addGroupIds);
		}
	}

	@Override
	public boolean isMembershipAllowed(long userId, long groupId) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLimitedToParentSiteMembers()) {
				if (!GroupLocalServiceUtil.hasUserGroup(
						userId, group.getParentGroupId(), false)) {

					return false;
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return true;
	}

	public void propagateMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException, SystemException {

		if (removeGroupIds != null) {
			for (long removeGroupId : removeGroupIds) {
				removeUsersFromLimitedChildrenGroups(userIds, removeGroupId);
			}
		}
	}

	@Override
	public void verifyPolicy(Group group)
		throws PortalException, SystemException {

		if (group.isLimitedToParentSiteMembers()) {
			verifyLimitedParentMembership(group);
		}
	}

	public void verifyPolicy(
			Group group, Group oldGroup, List<AssetCategory> oldAssetCategories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes,
			UnicodeProperties oldTypeSettingsProperties)
		throws PortalException, SystemException {

		if (group.isLimitedToParentSiteMembers()) {
			if ((group.getParentGroupId() == oldGroup.getParentGroupId()) &&
				oldGroup.isLimitedToParentSiteMembers()) {

				verifyPolicy(group);
			}
			else {
				List<Group> children = getLimitedChildrenGroups(group);

				for (Group child : children) {
					verifyPolicy(child);
				}
			}
		}
	}

	protected void checkAddUsersLimitedGroup(long[] userIds, long[] groupIds)
		throws PortalException, SystemException {

		MembershipPolicyException membershipPolicyException = null;

		for (long groupId : groupIds) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (!group.isLimitedToParentSiteMembers()) {
				continue;
			}

			for (long userId : userIds) {
				if (!GroupLocalServiceUtil.hasUserGroup(
						userId, group.getParentGroupId(), false)) {

					if (membershipPolicyException == null) {
						membershipPolicyException =
							new MembershipPolicyException(
								MembershipPolicyException.
									SITE_MEMBERSHIP_NOT_ALLOWED);
					}

					User user = UserLocalServiceUtil.getUser(userId);

					membershipPolicyException.addUser(user);
				}
			}

			if (membershipPolicyException != null) {
				membershipPolicyException.addGroup(group);
			}
		}

		if (membershipPolicyException != null) {
			throw membershipPolicyException;
		}
	}

	protected List<Group> getLimitedChildrenGroups(Group group)
		throws PortalException, SystemException {

		List<Group> parentGroups = new ArrayList<Group>();

		parentGroups.add(group);

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("groupsTree", parentGroups);
		groupParams.put("site", Boolean.TRUE);
		groupParams.put(
			"type", GroupConstants.TYPE_SITE_LIMITED_TO_PARENT_SITE_MEMBERS);

		List<Group> children = GroupLocalServiceUtil.search(
			group.getCompanyId(), null, StringPool.BLANK, groupParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<Group> filteredChildren = ListUtil.copy(children);

		for (Group child : children) {
			for (Group ancestor : child.getAncestors()) {
				if ((ancestor.getGroupId() != group.getGroupId()) &&
					!ancestor.isLimitedToParentSiteMembers()) {

					filteredChildren.remove(child);

					break;
				}
			}
		}

		return filteredChildren;
	}

	protected void removeUsersFromLimitedChildrenGroups(
			long[] userIds, long groupId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		List<Group> children = getLimitedChildrenGroups(group);

		for (Group child : children) {
			if (!child.isLimitedToParentSiteMembers()) {
				continue;
			}

			for (long userId : userIds) {
				UserLocalServiceUtil.unsetGroupUsers(
					child.getGroupId(), new long[] {userId}, null);
			}
		}
	}

	protected void verifyLimitedParentMembership(Group group)
		throws PortalException, SystemException {

		List<User> users = UserLocalServiceUtil.getGroupUsers(
			group.getGroupId());

		List<User> parentUsers = UserLocalServiceUtil.getGroupUsers(
			group.getParentGroupId());

		for (User user : users) {
			if (!parentUsers.contains(user)) {
				UserLocalServiceUtil.unsetGroupUsers(
					group.getGroupId(), new long[] {user.getUserId()},
					null);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultSiteMembershipPolicy.class);

}