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

package com.liferay.friendly.url.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.friendly.url.service.FriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
@Sync
public class FriendlyURLLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAddDuplicateFriendlyURLWithSameClassPK() throws Exception {
		long classNameId = PortalUtil.getClassNameId(User.class);

		String urlTitle1 = "url-title-1";

		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), classNameId,
			_user.getUserId(), urlTitle1);

		String urlTitle2 = "url-title-2";

		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), classNameId,
			_user.getUserId(), urlTitle2);

		List<FriendlyURL> friendlyURLs =
			FriendlyURLLocalServiceUtil.getFriendlyURLs(
				_group.getCompanyId(), _group.getGroupId(), classNameId,
				_user.getUserId());

		Assert.assertEquals(2, friendlyURLs.size());

		FriendlyURL mainFriendlyURL =
			FriendlyURLLocalServiceUtil.getMainFriendlyURL(
				_group.getCompanyId(), _group.getGroupId(), User.class,
				_user.getUserId());

		Assert.assertEquals(urlTitle2, mainFriendlyURL.getUrlTitle());

		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), classNameId,
			_user.getUserId(), urlTitle1);

		friendlyURLs = FriendlyURLLocalServiceUtil.getFriendlyURLs(
			_group.getCompanyId(), _group.getGroupId(), classNameId,
			_user.getUserId());

		Assert.assertEquals(2, friendlyURLs.size());

		mainFriendlyURL = FriendlyURLLocalServiceUtil.getMainFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId());

		Assert.assertEquals(urlTitle1, mainFriendlyURL.getUrlTitle());
	}

	@Test(expected = FriendlyURLLengthException.class)
	public void testAddFriendlyURLWithBlankUrlTitle() throws Exception {
		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId(), StringPool.BLANK);
	}

	@Test(expected = FriendlyURLLengthException.class)
	public void testAddFriendlyURLWithNullUrlTitle() throws Exception {
		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId(), null);
	}

	@Test
	public void testAddFriendlyURLWithValidUrlTitle() throws Exception {
		long classNameId = PortalUtil.getClassNameId(User.class);

		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), classNameId,
			_user.getUserId(), StringUtil.randomString());

		List<FriendlyURL> friendlyURLs =
			FriendlyURLLocalServiceUtil.getFriendlyURLs(
				_group.getCompanyId(), _group.getGroupId(), classNameId,
				_user.getUserId());

		Assert.assertEquals(1, friendlyURLs.size());
	}

	@Test
	public void testDeleteFriendlyURLByClassPK() throws Exception {
		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId(), StringUtil.randomString());

		FriendlyURLLocalServiceUtil.deleteFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId());

		long classNameId = PortalUtil.getClassNameId(User.class);

		List<FriendlyURL> friendlyURLs =
			FriendlyURLLocalServiceUtil.getFriendlyURLs(
				_group.getCompanyId(), _group.getGroupId(), classNameId,
				_user.getUserId());

		Assert.assertEquals(0, friendlyURLs.size());
	}

	@Test
	public void testDeleteFriendlyURLByClassPKAndUrlTitle() throws Exception {
		String urlTitle1 = "url-title-1";

		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId(), urlTitle1);

		String urlTitle2 = "url-title-2";

		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId(), urlTitle2);

		FriendlyURLLocalServiceUtil.deleteFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId(), urlTitle1);

		long classNameId = PortalUtil.getClassNameId(User.class);

		List<FriendlyURL> friendlyURLs =
			FriendlyURLLocalServiceUtil.getFriendlyURLs(
				_group.getCompanyId(), _group.getGroupId(), classNameId,
				_user.getUserId());

		Assert.assertEquals(1, friendlyURLs.size());
		Assert.assertNotNull(
			FriendlyURLLocalServiceUtil.fetchFriendlyURL(
				_group.getCompanyId(), _group.getGroupId(), User.class,
				urlTitle2));
	}

	@Test
	public void testDeleteGroupFriendlyURLs() throws Exception {
		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId(), StringUtil.randomString());

		long classNameId = PortalUtil.getClassNameId(User.class);

		FriendlyURLLocalServiceUtil.deleteGroupFriendlyURLs(
			_group.getGroupId(), classNameId);

		List<FriendlyURL> friendlyURLs =
			FriendlyURLLocalServiceUtil.getFriendlyURLs(
				_group.getCompanyId(), _group.getGroupId(), classNameId,
				_user.getUserId());

		Assert.assertEquals(0, friendlyURLs.size());
	}

	@Test
	public void testGetUniqueUrlTitle() throws Exception {
		String urlTitle = "url-title";

		FriendlyURLLocalServiceUtil.addFriendlyURL(
			_group.getCompanyId(), _group.getGroupId(), User.class,
			_user.getUserId(), "url-title");

		long classNameId = PortalUtil.getClassNameId(User.class);

		String uniqueUrlTitle = FriendlyURLLocalServiceUtil.getUniqueUrlTitle(
			_group.getCompanyId(), _group.getGroupId(), classNameId,
			TestPropsValues.getUserId(), urlTitle);

		Assert.assertTrue(
			uniqueUrlTitle.startsWith(
				urlTitle.substring(0, urlTitle.length() - 2)));
		Assert.assertNotEquals(urlTitle, uniqueUrlTitle);
	}

	@Test(expected = FriendlyURLLengthException.class)
	public void testValidateFriendlyURLWithBlankUrlTitle() throws Exception {
		long classNameId = PortalUtil.getClassNameId(User.class);

		FriendlyURLLocalServiceUtil.validate(
			_group.getCompanyId(), _group.getGroupId(), classNameId,
			_user.getUserId(), StringPool.BLANK);
	}

	@Test(expected = FriendlyURLLengthException.class)
	public void testValidateFriendlyURLWithNullUrlTitle() throws Exception {
		long classNameId = PortalUtil.getClassNameId(User.class);

		FriendlyURLLocalServiceUtil.validate(
			_group.getCompanyId(), _group.getGroupId(), classNameId,
			_user.getUserId(), null);
	}

	@Test
	public void testValidateFriendlyURLWithValidUrlTitle() throws Exception {
		long classNameId = PortalUtil.getClassNameId(User.class);

		FriendlyURLLocalServiceUtil.validate(
			_group.getCompanyId(), _group.getGroupId(), classNameId,
			_user.getUserId(), StringUtil.randomString());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}