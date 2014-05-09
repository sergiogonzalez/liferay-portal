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

package com.liferay.portlet.blogs.pingback;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;

/**
 * @author Alexander Chow
 * @author André de Oliveira
 */
public class PingbackCommentsImpl implements PingbackComments {

	@Override
	public void addComment(
			long companyId, long groupId, String className, long classPK,
			String body, String urlTitle)
		throws PortalException, SystemException {

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				userId, groupId, className, classPK,
				WorkflowConstants.STATUS_APPROVED);

		MBThread thread = messageDisplay.getThread();

		long threadId = thread.getThreadId();

		List<MBMessage> messages =
			MBMessageLocalServiceUtil.getThreadMessages(
				threadId, WorkflowConstants.STATUS_APPROVED);

		for (MBMessage message : messages) {
			if (message.getBody().equals(body)) {
				throw new DuplicateCommentException();
			}
		}

		PingbackServiceContextFunction pingbackServiceContextFunction =
			new PingbackServiceContextFunction(companyId, groupId, urlTitle);

		ServiceContext serviceContext = pingbackServiceContextFunction.apply(
			StringPool.BLANK);

		MBMessageLocalServiceUtil.addDiscussionMessage(
			userId, StringPool.BLANK, groupId, className, classPK, threadId,
			thread.getRootMessageId(), StringPool.BLANK, body, serviceContext);
	}

}