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

package com.liferay.portlet.announcements.service;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Christopher Kian
 */
@Sync
public class AnnouncementsEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_announcementsEntryLocalService =
			(AnnouncementsEntryLocalService)PortalBeanLocatorUtil.locate(
				AnnouncementsEntryLocalService.class.getName());
	}

	@Test
	public void testDeleteGroupAnnouncements() throws Exception {
		GroupLocalService _groupLocalService =
			(GroupLocalService)PortalBeanLocatorUtil.locate(
				GroupLocalService.class.getName());

		Group group = GroupTestUtil.addGroup();

		AnnouncementsEntry entry = createAnnouncementsEntry(
			group.getClassNameId(), group.getGroupId());

		long entryId = entry.getEntryId();

		Assert.assertNotNull(
			_announcementsEntryLocalService.fetchAnnouncementsEntry(entryId));

		_groupLocalService.deleteGroup(group);

		Assert.assertNull(
			_announcementsEntryLocalService.fetchAnnouncementsEntry(entryId));
	}

	@Test
	public void testDeleteRoleAnnouncements() throws Exception {
		deleteRoleAnnouncements(RoleConstants.TYPE_ORGANIZATION);
		deleteRoleAnnouncements(RoleConstants.TYPE_REGULAR);
		deleteRoleAnnouncements(RoleConstants.TYPE_SITE);
	}

	@Test
	public void testDeleteUserGroupAnnouncements() throws Exception {
		UserGroupLocalService _userGroupLocalService =
			(UserGroupLocalService)PortalBeanLocatorUtil.locate(
				UserGroupLocalService.class.getName());

		ClassNameLocalService _classNameLocalService =
			(ClassNameLocalService)PortalBeanLocatorUtil.locate(
				ClassNameLocalService.class.getName());

		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		long classNameId = _classNameLocalService.getClassNameId(
			"com.liferay.portal.kernel.model.UserGroup");

		AnnouncementsEntry entry = createAnnouncementsEntry(
			classNameId, userGroup.getUserGroupId());

		long entryId = entry.getEntryId();

		Assert.assertNotNull(
			_announcementsEntryLocalService.fetchAnnouncementsEntry(entryId));

		_userGroupLocalService.deleteUserGroup(userGroup);

		Assert.assertNull(
			_announcementsEntryLocalService.fetchAnnouncementsEntry(entryId));
	}

	protected AnnouncementsEntry createAnnouncementsEntry(
			long classNameId, long classPK)
		throws Exception {

		AnnouncementsEntry entry =
			_announcementsEntryLocalService.createAnnouncementsEntry(
				TestPropsValues.getPlid());

		entry.setClassNameId(classNameId);
		entry.setClassPK(classPK);

		_announcementsEntryLocalService.addAnnouncementsEntry(entry);

		return entry;
	}

	protected void deleteRoleAnnouncements(int roleType) throws Exception {
		RoleLocalService _roleLocalService =
			(RoleLocalService)PortalBeanLocatorUtil.locate(
				RoleLocalService.class.getName());

		Role role = RoleTestUtil.addRole(roleType);

		AnnouncementsEntry entry = createAnnouncementsEntry(
			role.getClassNameId(), role.getRoleId());

		long entryId = entry.getEntryId();

		Assert.assertNotNull(
			_announcementsEntryLocalService.fetchAnnouncementsEntry(entryId));

		_roleLocalService.deleteRole(role);

		Assert.assertNull(
			_announcementsEntryLocalService.fetchAnnouncementsEntry(entryId));
	}

	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

}