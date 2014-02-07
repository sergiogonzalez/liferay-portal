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

package com.liferay.portlet.blogs.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.notifications.BaseContentUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Sergio González
 */
public class BlogsUserNotificationHandler
	extends BaseContentUserNotificationHandler<BlogsEntry> {

	public BlogsUserNotificationHandler() {
		super(PortletKeys.BLOGS, "/blogs/view_entry", _NOTIFICATION_TITLES);
	}

	@Override
	protected BlogsEntry fetchClassedModel(long classPK)
		throws PortalException, SystemException {

		return BlogsEntryLocalServiceUtil.fetchBlogsEntry(classPK);
	}

	@Override
	protected String getTitle(BlogsEntry blogsEntry) {
		return blogsEntry.getTitle();
	}

	@Override
	protected long getUserId(BlogsEntry blogsEntry) {
		return blogsEntry.getUserId();
	}

	@Override
	protected void initPortletURL(
			PortletURL portletURL, boolean inPage, BlogsEntry blogsEntry)
		throws PortletException {

		if (inPage) {
			portletURL.setParameter(
				"entryId", String.valueOf(blogsEntry.getEntryId()));
		}
		else {
			portletURL.setParameter(
				"entryId", String.valueOf(blogsEntry.getEntryId()));
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}
	}

	private static final Map<Integer, String> _NOTIFICATION_TITLES =
			new HashMap<Integer, String>();

	static {
		_NOTIFICATION_TITLES.put(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			"x-wrote-a-new-blog-entry");
		_NOTIFICATION_TITLES.put(
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			"x-updated-a-blog-entry");
	}

}