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

package com.liferay.portlet.discussion.notification;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Roberto Díaz
 */
public class DiscussionUserNotificationHandler
	extends BaseUserNotificationHandler {

	public DiscussionUserNotificationHandler() {
		setPortletId(PortletKeys.DISCUSSION);
	}

	protected AssetRenderer getAssetRender(MBMessage message)
		throws PortalException, SystemException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				message.getClassName());

		if (assetRendererFactory == null) {
			return null;
		}

		return assetRendererFactory.getAssetRenderer(message.getClassPK());
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		MBMessage message = MBMessageLocalServiceUtil.fetchMBMessage(classPK);

		if (message == null) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		int notificationType = jsonObject.getInt("notificationType");

		String title = StringPool.BLANK;

		if (notificationType ==
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY) {

			title = "x-added-a-new-comment-to-x";
		}
		else if (notificationType ==
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY) {

			title = "x-updated-a-comment-to-x";
		}

		AssetRenderer assetRenderer = getAssetRender(message);

		Locale locale = serviceContext.getLocale();

		StringBundler sb = new StringBundler(5);

		sb.append("<div class=\"title\">");
		sb.append(
			serviceContext.translate(
				title,
				HtmlUtil.escape(
					PortalUtil.getUserName(
						message.getUserId(), StringPool.BLANK)),
				HtmlUtil.escape(assetRenderer.getTitle(locale))));
		sb.append("</div><div class=\"body\">");
		sb.append(
			HtmlUtil.escape(StringUtil.shorten(message.getSubject(), 50)));
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

		MBMessage message = MBMessageLocalServiceUtil.fetchMBMessage(classPK);

		if (message == null) {
			return null;
		}

		AssetRenderer assetRenderer = getAssetRender(message);

		if (assetRenderer == null) {
			return null;
		}

		PortletURL noSuchEntryRedirect = assetRenderer.getURLView(
			serviceContext.getLiferayPortletResponse(), WindowState.MAXIMIZED);

		return assetRenderer.getURLViewInContext(
			serviceContext.getLiferayPortletRequest(),
			serviceContext.getLiferayPortletResponse(),
			noSuchEntryRedirect.toString());
	}

}