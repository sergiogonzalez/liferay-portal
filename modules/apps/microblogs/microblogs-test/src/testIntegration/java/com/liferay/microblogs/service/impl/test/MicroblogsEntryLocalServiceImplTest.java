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

package com.liferay.microblogs.service.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.model.MicroblogsEntryConstants;
import com.liferay.microblogs.service.MicroblogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.social.kernel.service.SocialActivityLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian I. Kim
 */
@RunWith(Arquillian.class)
public class MicroblogsEntryLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user1 = UserTestUtil.addGroupAdminUser(_group);
		_user2 = UserTestUtil.addGroupAdminUser(_group);
		_user3 = UserTestUtil.addGroupAdminUser(_group);
	}

	@Test
	public void testDeleteReply() throws Exception {
		MicroblogsEntry microblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			microblogsEntry.getMicroblogsEntryId());

		assertMicroBlogsEntries(true);
		assertAssetEntries(true);
		assertSocialActivities(true);

		MicroblogsEntryLocalServiceUtil.deleteMicroblogsEntry(microblogsEntry);

		assertMicroBlogsEntries(false);
		assertAssetEntries(false);
		assertSocialActivities(false);
	}

	@Test
	public void testDeleteReplyAndRepost() throws Exception {
		MicroblogsEntry microblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			microblogsEntry.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user3.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			microblogsEntry.getMicroblogsEntryId());

		MicroblogsEntry microblogsEntry1 = addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPOST,
			microblogsEntry.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			microblogsEntry1.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user3.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			microblogsEntry1.getMicroblogsEntryId());

		MicroblogsEntry microblogsEntry2 = addMicroblogsEntry(
			_user3.getUserId(), MicroblogsEntryConstants.TYPE_REPOST,
			microblogsEntry1.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			microblogsEntry2.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			microblogsEntry2.getMicroblogsEntryId());

		assertMicroBlogsEntries(true);
		assertAssetEntries(true);
		assertSocialActivities(true);

		MicroblogsEntryLocalServiceUtil.deleteMicroblogsEntry(microblogsEntry);

		assertMicroBlogsEntries(false);
		assertAssetEntries(false);
		assertSocialActivities(false);
	}

	@Test
	public void testDeleteRepost() throws Exception {
		MicroblogsEntry microblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPOST,
			microblogsEntry.getMicroblogsEntryId());

		assertMicroBlogsEntries(true);
		assertAssetEntries(true);
		assertSocialActivities(true);

		MicroblogsEntryLocalServiceUtil.deleteMicroblogsEntry(microblogsEntry);

		assertMicroBlogsEntries(false);
		assertAssetEntries(false);
		assertSocialActivities(false);
	}

	protected MicroblogsEntry addMicroblogsEntry(
			long userId, int type, long parentMicroblogsEntryId)
		throws Exception {

		String content = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, userId);

		MicroblogsEntry microblogsEntry =
			MicroblogsEntryLocalServiceUtil.addMicroblogsEntry(
				userId, content, type, parentMicroblogsEntryId, 0,
				serviceContext);

		_microblogsEntries.add(microblogsEntry);

		return microblogsEntry;
	}

	protected void assertAssetEntries(boolean exist) {
		EntityCacheUtil.clearCache();

		if (exist) {
			for (MicroblogsEntry microblogsEntry : _microblogsEntries) {
				Assert.assertNotNull(
					AssetEntryLocalServiceUtil.fetchEntry(
						MicroblogsEntry.class.getName(),
						microblogsEntry.getPrimaryKey()));
			}
		}
		else {
			for (MicroblogsEntry microblogsEntry : _microblogsEntries) {
				Assert.assertNull(
					AssetEntryLocalServiceUtil.fetchEntry(
						MicroblogsEntry.class.getName(),
						microblogsEntry.getPrimaryKey()));
			}
		}
	}

	protected void assertMicroBlogsEntries(boolean exist) {
		EntityCacheUtil.clearCache();

		if (exist) {
			for (MicroblogsEntry microblogsEntry : _microblogsEntries) {
				Assert.assertNotNull(
					MicroblogsEntryLocalServiceUtil.fetchMicroblogsEntry(
						microblogsEntry.getMicroblogsEntryId()));
			}
		}
		else {
			for (MicroblogsEntry microblogsEntry : _microblogsEntries) {
				Assert.assertNull(
					MicroblogsEntryLocalServiceUtil.fetchMicroblogsEntry(
						microblogsEntry.getMicroblogsEntryId()));
			}
		}
	}

	protected void assertSocialActivities(boolean exist) {
		EntityCacheUtil.clearCache();

		if (exist) {
			Assert.assertNotEquals(
				0,
				SocialActivityLocalServiceUtil.getActivitiesCount(
					MicroblogsEntry.class.getName()));
		}
		else {
			Assert.assertEquals(
				0,
				SocialActivityLocalServiceUtil.getActivitiesCount(
					MicroblogsEntry.class.getName()));
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private final List<MicroblogsEntry> _microblogsEntries = new ArrayList<>();

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@DeleteAfterTestRun
	private User _user3;

}