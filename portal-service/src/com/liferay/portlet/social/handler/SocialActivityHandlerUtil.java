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

package com.liferay.portlet.social.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SocialActivityHandlerUtil {

	public static void addActivity(
			long userId, long groupId, String className, long classPK, int type,
			String extraData, long receiverUserId)
		throws PortalException {

		getSocialActivityHandler().addActivity(
			userId, groupId, className, classPK, type, extraData,
			receiverUserId);
	}

	public static void addUniqueActivity(
			long userId, long groupId, Date createDate, String className,
			long classPK, int type, String extraData, long receiverUserId)
		throws PortalException {

		getSocialActivityHandler().addUniqueActivity(
			userId, groupId, createDate, className, classPK, type, extraData,
			receiverUserId);
	}

	public static void addUniqueActivity(
			long userId, long groupId, String className, long classPK, int type,
			String extraData, long receiverUserId)
		throws PortalException {

		getSocialActivityHandler().addUniqueActivity(
			userId, groupId, className, classPK, type, extraData,
			receiverUserId);
	}

	public static void deleteActivities(String className, long classPK)
		throws PortalException {

		getSocialActivityHandler().deleteActivities(className, classPK);
	}

	public static SocialActivityHandler getSocialActivityHandler() {
		PortalRuntimePermission.checkGetBeanProperty(
			SocialActivityHandlerUtil.class);

		return _socialActivityHandler;
	}

	public void setSocialActivityHandler(
		SocialActivityHandler socialActivityHandler) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_socialActivityHandler = socialActivityHandler;
	}

	private static SocialActivityHandler _socialActivityHandler;

}