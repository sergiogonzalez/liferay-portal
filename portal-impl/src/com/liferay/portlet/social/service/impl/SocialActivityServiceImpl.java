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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.service.base.SocialActivityServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the social activity remote service.
 *
 * @author Zsolt Berentey
 */
public class SocialActivityServiceImpl extends SocialActivityServiceBaseImpl {

	protected List<SocialActivity> filterActivities(
			List<SocialActivity> activities, int start, int end)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		ServiceContext serviceContext = new ServiceContext();

		List<SocialActivityInterpreter> activityInterpreters =
			socialActivityInterpreterLocalService.getActivityInterpreters(
				StringPool.BLANK);

		List<SocialActivity> filteredActivities =
			new ArrayList<SocialActivity>();

		for (SocialActivity activity : activities) {
			for (int i = 0; i < activityInterpreters.size(); i++) {
				SocialActivityInterpreterImpl activityInterpreter =
					(SocialActivityInterpreterImpl)activityInterpreters.get(i);

				if (activityInterpreter.hasClassName(activity.getClassName())) {
					try {
						if (activityInterpreter.hasPermission(
								permissionChecker, activity, ActionKeys.VIEW,
								serviceContext)) {

							filteredActivities.add(activity);

							break;
						}
					}
					catch (Exception e) {
					}
				}
			}

			if ((end != QueryUtil.ALL_POS) &&
				(filteredActivities.size() > end)) {

				break;
			}
		}

		if ((end != QueryUtil.ALL_POS) && (start != QueryUtil.ALL_POS)) {
			if (end > filteredActivities.size()) {
				end = filteredActivities.size();
			}

			if (start > filteredActivities.size()) {
				start = filteredActivities.size();
			}

			filteredActivities = filteredActivities.subList(start, end);
		}

		return filteredActivities;
	}

}