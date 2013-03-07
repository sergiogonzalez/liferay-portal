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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;


import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DefaultSiteMembershipPolicyTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testAddGroupWithTypeSettings() throws Exception {

		Group group = addOnlyAllowParentMembershipPolicyGroup(0);

		Group groupFromPersistence = GroupLocalServiceUtil.getGroup(
			group.getGroupId());

		UnicodeProperties typeSettingsProperties =
			groupFromPersistence.getTypeSettingsProperties();

		String property = typeSettingsProperties.getProperty(
			MembershipPolicyKeys.MEMBERSHIP_POLICY_TYPE);

		Assert.assertEquals(String.valueOf(
			MembershipPolicyKeys.ONLY_ALLOW_PARENT_SITE_MEMBERSHIPS), property);
	}

	@Test
	public void testAddGroupWithTypeSettings2() throws Exception {

		Group group = addOnlyAllowParentMembershipPolicyGroup(0);

		Assert.assertFalse(group.isImplicitMembership());
	}

	@Test
	public void testAddGroupWithTypeSettings3() throws Exception {

		Group group = addInheritMembershipPolicyGroup(0);

		Group groupFromPersistence = GroupLocalServiceUtil.getGroup(
			group.getGroupId());

		UnicodeProperties typeSettingsProperties =
			groupFromPersistence.getTypeSettingsProperties();

		String property = typeSettingsProperties.getProperty(
			MembershipPolicyKeys.MEMBERSHIP_POLICY_TYPE);

		Assert.assertEquals(String.valueOf(
			MembershipPolicyKeys.INHERIT_PARENT_SITE_MEMBERSHIPS), property);
	}

	@Test
	public void testAddGroupWithTypeSettings4() throws Exception {

		Group group = addInheritMembershipPolicyGroup(0);

		Assert.assertTrue(group.isImplicitMembership());
	}

	@Test
	public void testFindImplicitGroupUser() throws Exception {
		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		UserTestUtil.addGroupMemberUser(parentGroup);

		List<User> parentGroupUsers = UserLocalServiceUtil.getGroupUsers(
			parentGroup.getGroupId());
		List<User> groupGroupUsers = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(parentGroupUsers.size(), groupGroupUsers.size());
	}

	@Test
	public void testFindImplicitGroupUsers() throws Exception {
		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		addNUsersToGroup(parentGroup);

		List<User> parentGroupUsers = UserLocalServiceUtil.getGroupUsers(
			parentGroup.getGroupId());
		List<User> groupGroupUsers = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(parentGroupUsers.size(), groupGroupUsers.size());
	}

	@Test
	public void testFindImplicitParentGroupUser() throws Exception {
		Group grandParentGroup = ServiceTestUtil.addGroup();
		Group parentGroup = addInheritMembershipPolicyGroup(
			grandParentGroup.getGroupId());
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		UserTestUtil.addGroupMemberUser(grandParentGroup);

		List<User> grandParentGroupUsers = UserLocalServiceUtil.getGroupUsers(
			grandParentGroup.getGroupId());
		List<User> groupGroupUsers = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(
			grandParentGroupUsers.size(), groupGroupUsers.size());
	}

	@Test
	public void testFindImplicitExplicitParentGroupUsers() throws Exception {
		Group grandParentGroup = ServiceTestUtil.addGroup();
		Group parentGroup = addInheritMembershipPolicyGroup(
			grandParentGroup.getGroupId());
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		UserTestUtil.addGroupMemberUser(grandParentGroup);
		UserTestUtil.addGroupMemberUser(parentGroup);

		List<User> grandGroupParent = UserLocalServiceUtil.getGroupUsers(
				grandParentGroup.getGroupId());
		List<User> parentGroupUsers = UserLocalServiceUtil.getInheritGroupUsers(
			parentGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(parentGroupUsers.size(), groupUserIds.size());
		Assert.assertTrue(groupUserIds.containsAll(grandGroupParent));
	}

	@Test
	public void testFindImplicitParentGroupUsers() throws Exception {
		Group grandParentGroup = ServiceTestUtil.addGroup();
		Group parentGroup = addInheritMembershipPolicyGroup(
			grandParentGroup.getGroupId());
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		addNUsersToGroup(grandParentGroup);

		List<User> grandParentUsers = UserLocalServiceUtil.getGroupUsers(
			grandParentGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(grandParentUsers.size(), groupUserIds.size());
	}

	@Test
	public void testFindImplicitNAncestorsGroupUser() throws Exception {
		Group nAncestorGroup= ServiceTestUtil.addGroup();
		Group baseGroup = addInheritMembershipPolicyGroup(
			nAncestorGroup.getGroupId());

		baseGroup = addNInheritPolicyChildGroups(baseGroup, false);

		Group group = addInheritMembershipPolicyGroup(
			baseGroup.getGroupId());

		UserTestUtil.addGroupMemberUser(nAncestorGroup);

		List<User> ancestorGroupUsers = UserLocalServiceUtil.getGroupUsers(
			nAncestorGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(ancestorGroupUsers.size(), groupUserIds.size());
	}

	@Test
	public void testFindImplicitNAncestorsGroupUsers() throws Exception {
		Group nAncestorGroup= ServiceTestUtil.addGroup();
		Group baseGroup = addInheritMembershipPolicyGroup(
			nAncestorGroup.getGroupId());

		baseGroup = addNInheritPolicyChildGroups(baseGroup, false);

		Group group = addInheritMembershipPolicyGroup(
			baseGroup.getGroupId());

		addNUsersToGroup(nAncestorGroup);

		List<User> ancestorGroupUsers = UserLocalServiceUtil.getGroupUsers(
			nAncestorGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(ancestorGroupUsers.size(), groupUserIds.size());
	}

	@Test
	public void testFindNImplicitExplicitParentGroupUsers() throws Exception {
		Group grandParentGroup = ServiceTestUtil.addGroup();
		Group parentGroup = addInheritMembershipPolicyGroup(
			grandParentGroup.getGroupId());
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		addNUsersToGroup(grandParentGroup);
		addNUsersToGroup(parentGroup);

		List<User> grandParentGroupUsers = UserLocalServiceUtil.getGroupUsers(
			grandParentGroup.getGroupId());
		List<User> parentGroupUsers = UserLocalServiceUtil.getInheritGroupUsers(
			parentGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(parentGroupUsers.size(), groupUserIds.size());
		Assert.assertTrue(groupUserIds.containsAll(grandParentGroupUsers));
	}

	@Test
	public void testFindNImplicitExplicitNAncestorsGroupUsers()
		throws Exception {

		Group nAncestorGroup= ServiceTestUtil.addGroup();
		Group baseGroup = addInheritMembershipPolicyGroup(
			nAncestorGroup.getGroupId());

		baseGroup = addNInheritPolicyChildGroups(baseGroup, true);

		Group group = addInheritMembershipPolicyGroup(
			baseGroup.getGroupId());

		addNUsersToGroup(nAncestorGroup);

		List<User> grandParentUsers = UserLocalServiceUtil.getGroupUsers(
			nAncestorGroup.getGroupId());
		List<User> parentGroupUsers = UserLocalServiceUtil.getInheritGroupUsers(
			baseGroup.getGroupId());
		List<User>groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(parentGroupUsers, groupUserIds);
		Assert.assertTrue(groupUserIds.containsAll(grandParentUsers));
	}

	@Test
	public void testFindAnyGroupUsers()
		throws Exception {

		Group nAncestorGroup= ServiceTestUtil.addGroup();
		Group baseGroup = addInheritMembershipPolicyGroup(
			nAncestorGroup.getGroupId());

		baseGroup = addNInheritPolicyChildGroups(baseGroup, true);

		Group group = addInheritMembershipPolicyGroup(
			baseGroup.getGroupId());

		addNUsersToGroup(nAncestorGroup);
		addNUsersToGroup(group);

		List<User> nAncestorGroupUsers =  UserLocalServiceUtil.getGroupUsers(
			nAncestorGroup.getGroupId());
		List<User> parentGroupUsers = UserLocalServiceUtil.getInheritGroupUsers(
			baseGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertTrue(groupUserIds.containsAll(parentGroupUsers));
		Assert.assertTrue(parentGroupUsers.containsAll(nAncestorGroupUsers));
		Assert.assertTrue(groupUserIds.size() != (parentGroupUsers.size()));
	}

	@Test
	public void testAddMembershipInInheritMP() throws Exception {
		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		long[] parentUserIds = addNUsersToGroup(parentGroup);

		User user = addUser();
		parentUserIds = ArrayUtil.append(parentUserIds, user.getUserId());

		User adminUser = UserTestUtil.addGroupAdminUser(parentGroup);

		addGroupUsers(group.getGroupId(),adminUser, parentUserIds);

		List<User> parentGroupUsers = UserLocalServiceUtil.getGroupUsers(
			parentGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals((parentGroupUsers.size() + 1),
			groupUserIds.size());
	}

	@Test
	public void testRemoveMembershipInInheritMP() throws Exception {
		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		long[] userIds = addNUsersToGroup(parentGroup);

		User adminUser = UserTestUtil.addGroupAdminUser(parentGroup);
		userIds = ArrayUtil.append(userIds, adminUser.getUserId());

		addGroupUsers(group.getGroupId(), adminUser, userIds);
		unsetGroupUsers(group.getGroupId(), adminUser, userIds);

		List<User> parentGroupUsers = UserLocalServiceUtil.getGroupUsers(
			parentGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(parentGroupUsers, groupUserIds);
	}

	@Test
	public void testRemoveMembershipInInheritMP2() throws Exception {
		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		long[] userIds = addNUsersToGroup(parentGroup);

		User adminUser = UserTestUtil.addGroupAdminUser(parentGroup);
		userIds = ArrayUtil.append(userIds, adminUser.getUserId());

		User user = UserTestUtil.addGroupMemberUser(group);
		userIds = ArrayUtil.append(userIds, user.getUserId());

		addGroupUsers(group.getGroupId(), adminUser, userIds);
		unsetGroupUsers(group.getGroupId(), adminUser, userIds);

		List<User> parentGroupUsers = UserLocalServiceUtil.getGroupUsers(
			parentGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getInheritGroupUsers(
			group.getGroupId());

		Assert.assertEquals(parentGroupUsers, groupUserIds);
	}

	@Test
	public void testRemoveMembershipExceptionCreationInInheritMP()
		throws Exception {

		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		long[] userIds = addNUsersToGroup(parentGroup);

		User adminUser = UserTestUtil.addGroupAdminUser(parentGroup);
		userIds = ArrayUtil.append(userIds, adminUser.getUserId());

		List<User> parenGrouptUsers = UserLocalServiceUtil.getGroupUsers(
			parentGroup.getParentGroupId());

		try {
			addGroupUsers(group.getGroupId(), adminUser, userIds);
			unsetGroupUsers(group.getGroupId(), adminUser, userIds);
		}
		catch (Exception e) {
			if (e instanceof MembershipPolicyException) {
				Assert.assertEquals(
					((MembershipPolicyException)e).getUsers().size(),
					parenGrouptUsers);
				Assert.assertEquals(
					((MembershipPolicyException)e).getGroups().get(0), group);
			}
		}
	}

	@Test
	public void testRemoveMembershipExceptionCreationInInheritMP2()
		throws Exception {

		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addInheritMembershipPolicyGroup(
			parentGroup.getGroupId());

		long[] userIds = addNUsersToGroup(parentGroup);

		User adminUser = UserTestUtil.addGroupAdminUser(parentGroup);
		userIds = ArrayUtil.append(userIds, adminUser.getUserId());

		User user = UserTestUtil.addGroupMemberUser(group);
		userIds = ArrayUtil.append(userIds, user.getUserId());

		try {
			addGroupUsers(group.getGroupId(), adminUser, userIds);
			unsetGroupUsers(group.getGroupId(), adminUser, userIds);
		}
		catch (Exception e) {
			if (e instanceof MembershipPolicyException) {
				List<User> errorUserList =
					((MembershipPolicyException)e).getUsers();

				Assert.assertFalse(errorUserList.contains(user));
				Assert.assertEquals(
					((MembershipPolicyException)e).getGroups().size(), 1);
			}
		}
	}

	@Test
	public void testAddMembershipInOnlyAllowParentMP()
		throws Exception {

		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addOnlyAllowParentMembershipPolicyGroup(
			parentGroup.getGroupId());

		long[] parentUserIds = addNUsersToGroup(parentGroup);

		try {
			UserServiceUtil.addGroupUsers(
				group.getGroupId(), parentUserIds, null);
		}
		finally {
			List<User> parentGroupUsers =
				UserLocalServiceUtil.getGroupUsers(parentGroup.getGroupId());
			List<User> groupUserIds =
				UserLocalServiceUtil.getGroupUsers(group.getGroupId());

			Assert.assertEquals(groupUserIds.size(), parentGroupUsers.size());
		}
	}

	@Test
	public void testAddAncestorMembershipInOnlyAllowParentMP()
		throws Exception {

		Group nAncestorGroup = ServiceTestUtil.addGroup();

		long[] ancestorGroupUsers = addNUsersToGroup(nAncestorGroup);

		Group baseGroup = addOnlyAllowParentMembershipPolicyGroup(
			nAncestorGroup.getGroupId());

		Group group = addOnlyAllowParentMembershipPolicyGroup(
			baseGroup.getGroupId());

		try {
			UserServiceUtil.addGroupUsers(
				group.getGroupId(), ancestorGroupUsers, null);
		}
		finally {
			List<User> nAncestorGroupUsers =
				UserLocalServiceUtil.getGroupUsers(nAncestorGroup.getGroupId());
			List<User> groupUser =
				UserLocalServiceUtil.getGroupUsers(group.getGroupId());

			Assert.assertFalse(nAncestorGroupUsers.size() == groupUser.size());
		}
	}

	@Test
	public void testAddMembershipExceptionCreationInOnlyAllowParentMP()
		throws Exception {

		Exception exception = null;

		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addOnlyAllowParentMembershipPolicyGroup(
			parentGroup.getGroupId());

		addNUsersToGroup(parentGroup);

		User adminUser = UserTestUtil.addGroupAdminUser(parentGroup);

		User user = addUser();

		try {
			addGroupUsers(
				group.getGroupId(),adminUser, new long[]{user.getUserId()});
		}
		catch (Exception e) {
			if (e instanceof MembershipPolicyException) {
				exception = e;
			}
		}

		Assert.assertNotNull(exception);
	}

	@Test
	public void testRemoveParentMembershipInOnlyAllowParentMP()
		throws Exception {

		Group parentGroup = ServiceTestUtil.addGroup();
		Group group = addOnlyAllowParentMembershipPolicyGroup(
			parentGroup.getGroupId());

		long[] parentUserIds = null;

		parentUserIds = addNUsersToGroup(parentGroup);

		UserServiceUtil.addGroupUsers(group.getGroupId(), parentUserIds, null);

		long removedUserId = parentUserIds[0];

		User removedUser = UserLocalServiceUtil.getUser(removedUserId);

		UserServiceUtil.unsetGroupUsers(
			parentGroup.getGroupId(), new long[]{removedUserId}, null);

		List<User> parentUsers = UserLocalServiceUtil.getGroupUsers(
			parentGroup.getGroupId());
		List<User> groupUserIds = UserLocalServiceUtil.getGroupUsers(
			group.getGroupId());

		Assert.assertEquals(parentUsers.size(), groupUserIds.size());
		Assert.assertFalse(groupUserIds.contains(removedUser));
	}

	@Test
	public void testRemoveAncestorMembershipInOnlyAllowParentMP()
		throws Exception {

		Group nAncestorGroup = ServiceTestUtil.addGroup();

		long[] ancestorGroupUsers = addNUsersToGroup(nAncestorGroup);

		Group baseGroup = addOnlyAllowParentMembershipPolicyGroup(
			nAncestorGroup.getGroupId());

		UserServiceUtil.addGroupUsers(
			baseGroup.getGroupId(), ancestorGroupUsers, null);

		Group group = addOnlyAllowParentMembershipPolicyGroup(
			baseGroup.getGroupId());

		UserServiceUtil.addGroupUsers(
			group.getGroupId(), ancestorGroupUsers, null);

		long[] deletedUsersId = {ancestorGroupUsers[0], ancestorGroupUsers[1]};

		UserServiceUtil.unsetGroupUsers(
			nAncestorGroup.getGroupId(), deletedUsersId, null);

		List<User> nAncestorGroupUsers = UserLocalServiceUtil.getGroupUsers(
			nAncestorGroup.getGroupId());
		List<User> parentGroupUsers = UserLocalServiceUtil.getGroupUsers(
			baseGroup.getGroupId());
		List<User> groupUsers = UserLocalServiceUtil.getGroupUsers(
			group.getGroupId());

		Assert.assertEquals(nAncestorGroupUsers.size(), groupUsers.size());
		Assert.assertEquals(parentGroupUsers.size(), groupUsers.size());
	}

	@Test
	public void testRemoveAncestorMembershipInOnlyAllowParentMP2()
		throws Exception {

		Group nAncestorGroup = ServiceTestUtil.addGroup();

		long[] ancestorGroupUsers = addNUsersToGroup(nAncestorGroup);

		Group baseGroup = addOnlyAllowParentMembershipPolicyGroup(
			nAncestorGroup.getGroupId());

		baseGroup = addNOnlyAllowParentMembershipPolicyGroup(
			baseGroup, ancestorGroupUsers);

		UserServiceUtil.addGroupUsers(
			baseGroup.getGroupId(), ancestorGroupUsers, null);

		Group group = addOnlyAllowParentMembershipPolicyGroup(
			baseGroup.getGroupId());

		UserServiceUtil.addGroupUsers(
			group.getGroupId(), ancestorGroupUsers, null);

		long[] deletedUsersId = {ancestorGroupUsers[0], ancestorGroupUsers[1]};

		UserServiceUtil.unsetGroupUsers(
			group.getGroupId(), deletedUsersId, null);

		UserServiceUtil.unsetGroupUsers(
			nAncestorGroup.getGroupId(), deletedUsersId, null);

		List<User> nAncestorGroupUsers = UserLocalServiceUtil.getGroupUsers(
			nAncestorGroup.getGroupId());
		List<User> parentGroupUsers = UserLocalServiceUtil.getGroupUsers(
			baseGroup.getGroupId());
		List<User> groupUsers = UserLocalServiceUtil.getGroupUsers(
			group.getGroupId());

		Assert.assertEquals(nAncestorGroupUsers.size(), groupUsers.size());
		Assert.assertEquals(
			nAncestorGroupUsers.size(), parentGroupUsers.size());
	}

	protected Group addInheritMembershipPolicyGroup(
			long parentGroupId)
		throws Exception {

		return addMemberhipPolicyGroup(
			parentGroupId,
			MembershipPolicyKeys.INHERIT_PARENT_SITE_MEMBERSHIPS);
	}

	protected Group addNInheritPolicyChildGroups(
			Group baseGroup, boolean addNUsers)
		throws Exception {

		int n = 5;

		for (int i = 0; i < n; i++) {
			Group parentGroup = addInheritMembershipPolicyGroup(
				baseGroup.getGroupId());

			if (addNUsers) {
				addNUsersToGroup(parentGroup);
			}

			baseGroup = parentGroup;
		}

		return baseGroup;
	}

	protected Group addNOnlyAllowParentMembershipPolicyGroup(
			Group baseGroup, long[] userGroupsIds)
		throws Exception {

		int n = 5;

		for (int i = 0; i < n; i++) {
			Group parentGroup = addOnlyAllowParentMembershipPolicyGroup(
				baseGroup.getGroupId());

			if (userGroupsIds != null && userGroupsIds.length > 0) {
				UserLocalServiceUtil.addGroupUsers(
					baseGroup.getGroupId(), userGroupsIds);
			}

			baseGroup = parentGroup;
		}

		return baseGroup;
	}

	protected Group addMemberhipPolicyGroup(
			long parentGroupId, int membershipPolicyType)
		throws Exception {

		/*String name = ServiceTestUtil.randomString();
		String description = "This is a test group.";
		int type = GroupConstants.TYPE_SITE_OPEN;
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);
		boolean site = true;
		boolean active = true;
		boolean implicitMembership = false;

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			MembershipPolicyKeys.MEMBERSHIP_POLICY_TYPE,
			String.valueOf(membershipPolicyType));

		if (membershipPolicyType ==
				MembershipPolicyKeys.INHERIT_PARENT_SITE_MEMBERSHIPS) {

			implicitMembership = true;
		}

		Group group = null;

		long groupId = ServiceTestUtil.randomLong();

		while (GroupLocalServiceUtil.fetchGroup(groupId) != null) {
			groupId = ServiceTestUtil.randomLong();
		}

		group = GroupLocalServiceUtil.createGroup(groupId);

		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);
		group.setSite(site);
		group.setActive(active);
		group.setCompanyId(TestPropsValues.getCompanyId());
		group.setParentGroupId(parentGroupId);
		group.setImplicitMembership(implicitMembership);
		group.setTypeSettingsProperties(typeSettingsProperties);

		return GroupLocalServiceUtil.addGroup(group);*/

		Group group = ServiceTestUtil.addGroup(
			parentGroupId, ServiceTestUtil.randomString());

		UnicodeProperties props = new UnicodeProperties();

		props.setProperty(
			MembershipPolicyKeys.MEMBERSHIP_POLICY_TYPE,
			String.valueOf(membershipPolicyType));

		boolean implicitMembership = false;

		if (membershipPolicyType ==
			MembershipPolicyKeys.INHERIT_PARENT_SITE_MEMBERSHIPS) {

			implicitMembership = true;
		}

		group.setImplicitMembership(implicitMembership);

		GroupLocalServiceUtil.updateGroup(group);

		return GroupLocalServiceUtil.updateGroup(
			group.getGroupId(), props.toString());

	}

	protected long[] addNUsersToGroup(Group group) throws Exception {
		int n = 5;

		long[] groupUsers = new long[n];

		for (int i = 0; i < n; i++)  {
			User newUser = UserTestUtil.addGroupMemberUser(group);

			groupUsers[i] = newUser.getUserId();
		}

		return groupUsers;
	}

	protected Group addOnlyAllowParentMembershipPolicyGroup(long parentGroupId)
		throws Exception {

		return addMemberhipPolicyGroup(
			parentGroupId,
			MembershipPolicyKeys.ONLY_ALLOW_PARENT_SITE_MEMBERSHIPS);
	}

	protected User addUser() throws Exception {
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		String emailAddress =
			"UserServiceTest." + ServiceTestUtil.nextLong() + "@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "UserServiceTest";
		String middleName = StringPool.BLANK;
		String lastName = "UserServiceTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		ServiceContext serviceContext = new ServiceContext();

		return UserServiceUtil.addUser(
			TestPropsValues.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendMail, serviceContext);
	}

	protected void addGroupUsers (
			long groupId, User adminUser, long[] userGroupIds)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(adminUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext = new ServiceContext();

		UserServiceUtil.addGroupUsers(groupId, userGroupIds, serviceContext);
	}

	protected void unsetGroupUsers(
			long groupId, User subjectUser, long[] userGroupIds)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext = new ServiceContext();

		UserServiceUtil.unsetGroupUsers(groupId, userGroupIds, serviceContext);
	}

}
