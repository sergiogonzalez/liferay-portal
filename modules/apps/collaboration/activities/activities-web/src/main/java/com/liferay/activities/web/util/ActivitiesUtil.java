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

package com.liferay.activities.web.util;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.social.model.SocialActivitySet;

/**
 * @author Matthew Kong
 */
public class ActivitiesUtil {

	public static Object[] getCommentsClassNameAndClassPK(
			SocialActivitySet activitySet)
		throws Exception {

		String className = activitySet.getClassName();
		long classPK = activitySet.getClassPK();

		if (className.equals(DLFileEntry.class.getName()) &&
			(activitySet.getActivityCount() > 1) &&
			(activitySet.getType() ==
				SocialActivityKeyConstants.DL_ADD_FILE_ENTRY)) {

			className = SocialActivitySet.class.getName();
			classPK = activitySet.getActivitySetId();
		}

		return new Object[] {className, classPK};
	}

}