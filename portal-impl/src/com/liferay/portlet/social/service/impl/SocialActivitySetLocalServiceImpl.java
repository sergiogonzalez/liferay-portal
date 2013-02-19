/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.service.base.SocialActivitySetLocalServiceBaseImpl;

/**
 * @author Matthew Kong
 */
public class SocialActivitySetLocalServiceImpl
	extends SocialActivitySetLocalServiceBaseImpl {

	public SocialActivitySet addActivitySet(
			long userId, long activityId, String className, long classPK,
			int type)
		throws PortalException, SystemException {

		// Activity set

		User user = userPersistence.findByPrimaryKey(userId);
		SocialActivity activity = socialActivityPersistence.findByPrimaryKey(
			activityId);

		long activitySetId = counterLocalService.increment();

		SocialActivitySet activitySet = socialActivitySetPersistence.create(
			activitySetId);

		activitySet.setGroupId(activity.getGroupId());
		activitySet.setCompanyId(user.getCompanyId());
		activitySet.setUserId(userId);
		activitySet.setCreateDate(activity.getCreateDate());
		activitySet.setModifiedDate(activity.getCreateDate());
		activitySet.setClassName(className);
		activitySet.setClassPK(classPK);
		activitySet.setType(type);
		activitySet.setActivityCount(1);

		socialActivitySetPersistence.update(activitySet);

		// Activity

		activity.setActivitySetId(activitySetId);

		socialActivityPersistence.update(activity);

		return activitySet;
	}

}