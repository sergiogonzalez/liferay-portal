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

package com.liferay.notifications.web.internal.util;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * @author Alejandro Tardín
 */
public class NotificationsUtil {

	public static void populateResults(
			SearchContainer<UserNotificationEvent> searchContainer)
		throws PortalException {

		DynamicQuery dynamicQuery = _getDynamicQuery(
			searchContainer.getPortletRequest());

		searchContainer.setResults(
			UserNotificationEventLocalServiceUtil.
				<UserNotificationEvent>dynamicQuery(
					dynamicQuery, searchContainer.getStart(),
					searchContainer.getEnd()));

		searchContainer.setTotal(
			(int)UserNotificationEventLocalServiceUtil.dynamicQueryCount(
				dynamicQuery));
	}

	private static DynamicQuery _getDynamicQuery(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		int deliveryType = UserNotificationDeliveryConstants.TYPE_WEBSITE;

		boolean actionRequired = ParamUtil.getBoolean(
			portletRequest, "actionRequired");

		String filterBy = ParamUtil.getString(
			portletRequest, "filterBy", "all");

		String orderByType = ParamUtil.getString(
			portletRequest, "orderByType", "desc");

		DynamicQuery dynamicQuery =
			UserNotificationEventLocalServiceUtil.dynamicQuery();

		Property userIdProperty = PropertyFactoryUtil.forName("userId");
		Property timestampProperty = PropertyFactoryUtil.forName("timestamp");
		Property actionRequiredProperty = PropertyFactoryUtil.forName(
			"actionRequired");
		Property deliveredProperty = PropertyFactoryUtil.forName("delivered");
		Property deliveryTypeProperty = PropertyFactoryUtil.forName(
			"deliveryType");

		dynamicQuery.add(userIdProperty.eq(themeDisplay.getUserId()));
		dynamicQuery.add(deliveryTypeProperty.eq(deliveryType));
		dynamicQuery.add(deliveredProperty.eq(true));
		dynamicQuery.add(actionRequiredProperty.eq(actionRequired));

		if (!"all".equals(filterBy)) {
			Property archivedProperty = PropertyFactoryUtil.forName("archived");

			if ("read".equals(filterBy)) {
				dynamicQuery.add(archivedProperty.eq(true));
			}
			else if ("unread".equals(filterBy)) {
				dynamicQuery.add(archivedProperty.eq(false));
			}
		}

		if ("asc".equals(orderByType)) {
			dynamicQuery.addOrder(timestampProperty.asc());
		}
		else {
			dynamicQuery.addOrder(timestampProperty.desc());
		}

		return dynamicQuery;
	}

}