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

package com.liferay.portlet.comments.notifications;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.BaseUserNotificationTestCase;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousMailExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class CommentsUserNotificationTest extends BaseUserNotificationTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_entry = BlogsTestUtil.addEntry(group, true);
	}

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		return MBTestUtil.addDiscussionMessage(
			group.getGroupId(), BlogsEntry.class.getName(),
			_entry.getEntryId());
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.COMMENTS;
	}

	@Override
	protected void subscribeToContainer() throws Exception {
		MBDiscussionLocalServiceUtil.subscribeDiscussion(
			user.getUserId(), group.getGroupId(), BlogsEntry.class.getName(),
			_entry.getEntryId());
	}

	@Override
	protected BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception {

		return MBTestUtil.updateDiscussionMessage(
			group.getGroupId(), (Long)baseModel.getPrimaryKeyObj(),
			BlogsEntry.class.getName(), _entry.getEntryId());
	}

	@Override
	protected List<JSONObject> getUserNotificationEventsJSONObjects(
			long userId, long baseEntryId)
		throws Exception {

		List<UserNotificationEvent> userNotificationEvents =
			UserNotificationEventLocalServiceUtil.getUserNotificationEvents(
				userId);

		List<JSONObject> userNotificationEventJSONObjects =
			new ArrayList<JSONObject>(userNotificationEvents.size());

		for (UserNotificationEvent userNotificationEvent :
			userNotificationEvents) {

			JSONObject userNotificationEventJSONObject =
				JSONFactoryUtil.createJSONObject(
					userNotificationEvent.getPayload());

			long classPK = userNotificationEventJSONObject.getLong("classPK");

			MBMessage message = MBMessageLocalServiceUtil.getMessage(
				baseEntryId);

			if (!message.isDiscussion()) {
				continue;
			}

			MBDiscussion discussion =
				MBDiscussionLocalServiceUtil.getThreadDiscussion(
					message.getThreadId());

			if (discussion.getDiscussionId() != classPK) {
				continue;
			}

			userNotificationEventJSONObjects.add(
				userNotificationEventJSONObject);
		}

		return userNotificationEventJSONObjects;
	}

	private BlogsEntry _entry;

}