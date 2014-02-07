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

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Iván Zaera
 */
public abstract class BaseContentUserNotificationHandler<T extends ClassedModel>
	extends BaseUserNotificationHandler {

	public BaseContentUserNotificationHandler(
		String portletId, String strutsAction,
		Map<Integer, String> notificationTitles) {

		setPortletId(portletId);
		_strutsAction = strutsAction;
		_notificationTitles = notificationTitles;
	}

	protected abstract T fetchClassedModel(long classPK)
		throws PortalException, SystemException;

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		T classedModel = fetchClassedModel(classPK);

		if (classedModel == null) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		int notificationType = jsonObject.getInt("notificationType");

		String notificationTitle = _notificationTitles.get(notificationType);

		if (notificationTitle == null) {
			notificationTitle = StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<div class=\"title\">");

		long userId = getUserId(classedModel);

		sb.append(
			serviceContext.translate(
				notificationTitle,
				HtmlUtil.escape(
					PortalUtil.getUserName(userId, StringPool.BLANK))));
		sb.append("</div><div class=\"body\">");

		String baseModelTitle = getTitle(classedModel);

		String shortUrlTitle = StringUtil.shorten(baseModelTitle, 50);

		sb.append(HtmlUtil.escape(shortUrlTitle));

		sb.append("</div>");

		return sb.toString();
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		T classedModel = fetchClassedModel(classPK);

		if (classedModel == null) {
			return null;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		User user = themeDisplay.getUser();

		Group group = user.getGroup();

		long portletPlid = PortalUtil.getPlidFromPortletId(
			group.getGroupId(), true, getPortletId());

		PortletURL portletURL = null;

		if (portletPlid != 0) {
			portletURL = PortletURLFactoryUtil.create(
				serviceContext.getLiferayPortletRequest(), getPortletId(),
				portletPlid, PortletRequest.RENDER_PHASE);

			initPortletURL(portletURL, true, classedModel);
		}
		else {
			LiferayPortletResponse liferayPortletResponse =
				serviceContext.getLiferayPortletResponse();

			portletURL = liferayPortletResponse.createRenderURL(getPortletId());

			initPortletURL(portletURL, false, classedModel);
		}

		portletURL.setParameter("struts_action", _strutsAction);

		return portletURL.toString();
	}

	protected abstract String getTitle(T classedModel);

	protected abstract long getUserId(T classedModel);

	protected abstract void initPortletURL(
			PortletURL portletURL, boolean inPage, T classedModel)
		throws PortletException;

	private Map<Integer, String> _notificationTitles;
	private String _strutsAction;

}