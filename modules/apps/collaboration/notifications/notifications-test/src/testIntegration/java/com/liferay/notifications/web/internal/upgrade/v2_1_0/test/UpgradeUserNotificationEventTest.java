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

package com.liferay.notifications.web.internal.upgrade.v2_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notifications.test.util.NotificationsUpgradeTestUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.UserNotificationEventImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 */
@RunWith(Arquillian.class)
public class UpgradeUserNotificationEventTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_upgradeProcess = NotificationsUpgradeTestUtil.getUpgradeStep(
			"com.liferay.notifications.web.internal.upgrade.v2_1_0." +
				"UpgradeUserNotificationEvent");
	}

	@Before
	public void setUp() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("create table Notifications_UserNotificationEvent ");
		sb.append("(notificationEventId LONG not null primary key, companyId ");
		sb.append("LONG, userId LONG, userNotificationEventId LONG, ");
		sb.append("timestamp LONG, delivered BOOLEAN, actionRequired ");
		sb.append("BOOLEAN, archived BOOLEAN)");

		_db.runSQL(sb.toString());

		_userNotificationEvent1 = _addUserNotificationEvent(false);
		_userNotificationEvent2 = _addUserNotificationEvent(true);

		sb = new StringBundler(5);

		sb.append("insert into Notifications_UserNotificationEvent ");
		sb.append("(notificationEventId, userNotificationEventId, ");
		sb.append("actionRequired) values (1, ");
		sb.append(_userNotificationEvent1.getUserNotificationEventId());
		sb.append(", false)");

		_db.runSQL(sb.toString());

		sb.setIndex(2);

		sb.append("actionRequired) values (2, ");
		sb.append(_userNotificationEvent2.getUserNotificationEventId());
		sb.append(", true)");

		_db.runSQL(sb.toString());
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL("drop table Notifications_UserNotificationEvent");
	}

	@Test
	public void testUpdateUserNotificationEventActionRequired()
		throws Exception {

		_upgradeProcess.upgrade();

		EntityCacheUtil.clearCache(UserNotificationEventImpl.class);

		_userNotificationEvent1 =
			UserNotificationEventLocalServiceUtil.fetchUserNotificationEvent(
				_userNotificationEvent1.getUserNotificationEventId());

		Assert.assertFalse(_userNotificationEvent1.getActionRequired());

		_userNotificationEvent2 =
			UserNotificationEventLocalServiceUtil.fetchUserNotificationEvent(
				_userNotificationEvent2.getUserNotificationEventId());

		Assert.assertTrue(_userNotificationEvent2.getActionRequired());
	}

	private static UserNotificationEvent _addUserNotificationEvent(
			boolean actionRequired)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("classPK", 0);
		jsonObject.put("userId", TestPropsValues.getUserId());

		if (actionRequired) {
			jsonObject.put("actionRequired", true);
		}

		return UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
			TestPropsValues.getUserId(), StringPool.BLANK,
			System.currentTimeMillis(),
			UserNotificationDeliveryConstants.TYPE_WEBSITE, 0,
			jsonObject.toString(), false, serviceContext);
	}

	private static DB _db;
	private static UpgradeProcess _upgradeProcess;

	@DeleteAfterTestRun
	private UserNotificationEvent _userNotificationEvent1;

	@DeleteAfterTestRun
	private UserNotificationEvent _userNotificationEvent2;

}