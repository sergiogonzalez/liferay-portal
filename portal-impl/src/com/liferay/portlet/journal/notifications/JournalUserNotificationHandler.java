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

package com.liferay.portlet.journal.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.notifications.BaseContentUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Iván Zaera
 */
public class JournalUserNotificationHandler
	extends BaseContentUserNotificationHandler<JournalArticle> {

	public JournalUserNotificationHandler() {
		super(
			PortletKeys.JOURNAL, "/journal/edit_article", _NOTIFICATION_TITLES);
	}

	@Override
	protected JournalArticle fetchClassedModel(long classPK)
		throws PortalException, SystemException {

		try {
			return JournalArticleServiceUtil.getArticle(classPK);
		}
		catch (NoSuchArticleException nsae) {
			return null;
		}
	}

	@Override
	protected String getTitle(JournalArticle article) {
		return article.getUrlTitle();
	}

	@Override
	protected long getUserId(JournalArticle article) {
		return article.getUserId();
	}

	@Override
	protected void initPortletURL(
			PortletURL portletURL, boolean inPage, JournalArticle article)
		throws PortletException {

		if (inPage) {
			portletURL.setParameter(
				"articleId", String.valueOf(article.getId()));
		}
		else {
			portletURL.setParameter(
				"groupId", String.valueOf(article.getGroupId()));
			portletURL.setParameter(
				"folderId", String.valueOf(article.getFolderId()));
			portletURL.setParameter(
				"articleId", String.valueOf(article.getArticleId()));
			portletURL.setParameter(
				"status", String.valueOf(article.getStatus()));
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}
	}

	private static final Map<Integer, String> _NOTIFICATION_TITLES =
		new HashMap<Integer, String>();

	static {
		_NOTIFICATION_TITLES.put(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			"x-created-an-article");
		_NOTIFICATION_TITLES.put(
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			"x-updated-an-article");
	}

}