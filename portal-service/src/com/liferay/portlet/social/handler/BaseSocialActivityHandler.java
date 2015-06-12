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
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.service.SocialActivityLocalService;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseSocialActivityHandler
	implements SocialActivityHandler {

	@Override
	public void addActivity(
			long userId, long groupId, String className, long classPK, int type,
			String extraData, long receiverUserId)
		throws PortalException {

		if (type == SocialActivityConstants.TYPE_SUBSCRIBE) {
			if (classPK != groupId) {
				getSocialActivityLocalService().addActivity(
					userId, groupId, className, classPK,
					SocialActivityConstants.TYPE_SUBSCRIBE, extraData, 0);
			}
		}
		else {
			getSocialActivityLocalService().addActivity(
				userId, groupId, className, classPK, type, extraData,
				receiverUserId);
		}
	}

	@Override
	public void addUniqueActivity(
			long userId, long groupId, Date createDate, String className,
			long classPK, int type, String extraData, long receiverUserId)
		throws PortalException {

		getSocialActivityLocalService().addUniqueActivity(
			userId, groupId, createDate, className, classPK, type, extraData,
			receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, long groupId, String className, long classPK, int type,
			String extraData, long receiverUserId)
		throws PortalException {

		getSocialActivityLocalService().addUniqueActivity(
			userId, groupId, className, classPK, type, extraData,
			receiverUserId);
	}

	@Override
	public void deleteActivities(String className, long classPK)
		throws PortalException {

		getSocialActivityLocalService().deleteActivities(className, classPK);
	}

	protected abstract SocialActivityLocalService
		getSocialActivityLocalService();

}