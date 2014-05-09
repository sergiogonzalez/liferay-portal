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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
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
			long userId, long groupId, String className, long classPK,
			String body)
		throws PortalException, SystemException {

		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				userId, groupId, className, classPK,
				WorkflowConstants.STATUS_APPROVED);

		MBThread thread = messageDisplay.getThread();

		long threadId = thread.getThreadId();
		long parentMessageId = thread.getRootMessageId();

		List<MBMessage> messages =
			MBMessageLocalServiceUtil.getThreadMessages(
				threadId, WorkflowConstants.STATUS_APPROVED);

		for (MBMessage message : messages) {
			if (message.getBody().equals(body)) {
				throw new DuplicateCommentException();
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		String pingbackUserName = LanguageUtil.get(
			LocaleUtil.getSiteDefault(), "pingback");

		serviceContext.setAttribute("pingbackUserName", pingbackUserName);

		StringBundler sb = new StringBundler(5);

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			groupId, PortletKeys.BLOGS);

		sb.append(layoutFullURL);

		sb.append(Portal.FRIENDLY_URL_SEPARATOR);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, PortletKeys.BLOGS);

		sb.append(portlet.getFriendlyURLMapping());
		sb.append(StringPool.SLASH);
		sb.append(entry.getUrlTitle());

		serviceContext.setAttribute("redirect", sb.toString());

		serviceContext.setLayoutFullURL(layoutFullURL);

		MBMessageLocalServiceUtil.addDiscussionMessage(
			userId, StringPool.BLANK, groupId, className, classPK, threadId,
			parentMessageId, StringPool.BLANK, body, serviceContext);
	}

}