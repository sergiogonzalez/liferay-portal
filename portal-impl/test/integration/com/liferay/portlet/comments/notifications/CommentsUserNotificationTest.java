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

package com.liferay.portlet.comments.notifications;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseUserNotificationTestCase;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.BlogsTestUtil;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class CommentsUserNotificationTest extends BaseUserNotificationTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		User siteAdmin = UserTestUtil.addGroupUser(
			group, RoleConstants.SITE_ADMINISTRATOR);

		_commentedEntry = BlogsTestUtil.addEntry(
			siteAdmin.getUserId(), group, true);

		siteMember = UserTestUtil.addGroupUser(
			group, RoleConstants.SITE_MEMBER);
	}

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				siteMember.getUserId(), group.getGroupId(),
				BlogsEntry.class.getName(), _commentedEntry.getEntryId(),
				WorkflowConstants.STATUS_APPROVED);

		MBThread thread =  messageDisplay.getThread();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return MBMessageLocalServiceUtil.addDiscussionMessage(
			siteMember.getUserId(), user.getFullName(), group.getGroupId(),
			BlogsEntry.class.getName(), _commentedEntry.getEntryId(),
			thread.getThreadId(), thread.getRootMessageId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50),
			serviceContext);
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.COMMENTS;
	}

	@Override
	protected void subscribeToContainer() throws Exception {
		SubscriptionLocalServiceUtil.addSubscription(
			user.getUserId(), group.getGroupId(), BlogsEntry.class.getName(),
			_commentedEntry.getEntryId());
	}

	@Override
	protected BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");

		return MBMessageLocalServiceUtil.updateDiscussionMessage(
			siteMember.getUserId(), (Long)baseModel.getPrimaryKeyObj(),
			BlogsEntry.class.getName(), _commentedEntry.getEntryId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50),
			serviceContext);
	}

	private BlogsEntry _commentedEntry;
	private User siteMember;

}