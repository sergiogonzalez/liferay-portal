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
package com.liferay.notifications.web.internal.util.comparator;

import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alejandro Tardín
 */
public class UserNotificationEventComparator
	extends OrderByComparator<UserNotificationEvent> {

	public UserNotificationEventComparator(
		String orderByCol, String orderByType) {

		_ascending = "asc".equals(orderByType);
	}

	@Override
	public int compare(
		UserNotificationEvent userNotificationEvent1,
		UserNotificationEvent userNotificationEvent2) {

		long difference =
			userNotificationEvent1.getTimestamp() -
				userNotificationEvent2.getTimestamp();

		if (_ascending) {
			return (int)difference;
		}
		else {
			return (int)-difference;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return "UserNotificationEvent.timestamp ASC";
		}
		else {
			return "UserNotificationEvent.timestamp DESC";
		}
	}

	private final boolean _ascending;

}