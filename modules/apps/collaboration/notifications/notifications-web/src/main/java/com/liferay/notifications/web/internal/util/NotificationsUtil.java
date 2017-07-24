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

import com.liferay.notifications.web.internal.util.comparator.UserNotificationEventComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alejandro Tardín
 */
public class NotificationsUtil {

	public static void populateResults(
			long userId, boolean actionRequired, String filterBy,
			String orderByCol, String orderByType,
			SearchContainer<UserNotificationEvent> searchContainer)
		throws PortalException {

		int deliveryType = UserNotificationDeliveryConstants.TYPE_WEBSITE;
		OrderByComparator<UserNotificationEvent> orderByComparator =
			new UserNotificationEventComparator(orderByType.equals("asc"));

		if ("read".equals(filterBy) || "unread".equals(filterBy)) {
			boolean archived = "read".equals(filterBy);

			searchContainer.setTotal(
				UserNotificationEventLocalServiceUtil.
					getArchivedUserNotificationEventsCount(
						userId, deliveryType, actionRequired, archived));

			searchContainer.setResults(
				UserNotificationEventLocalServiceUtil.
					getArchivedUserNotificationEvents(
						userId, deliveryType, actionRequired, archived,
						searchContainer.getStart(), searchContainer.getEnd(),
						orderByComparator));
		}
		else {
			searchContainer.setTotal(
				UserNotificationEventLocalServiceUtil.
					getDeliveredUserNotificationEventsCount(
						userId, deliveryType, true, actionRequired));

			searchContainer.setResults(
				UserNotificationEventLocalServiceUtil.
					getDeliveredUserNotificationEvents(
						userId, deliveryType, true, actionRequired,
						searchContainer.getStart(), searchContainer.getEnd(),
						orderByComparator));
		}
	}

}