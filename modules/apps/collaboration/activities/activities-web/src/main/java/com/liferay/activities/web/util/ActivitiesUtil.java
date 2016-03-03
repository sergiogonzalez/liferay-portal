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

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.social.kernel.model.SocialActivitySet;

/**
 * @author Matthew Kong
 */
public class ActivitiesUtil {

	public static String getDiscussionClassName(
		SocialActivitySet socialActivitySet) {

		if (isMultipleAddFileEntryActivitySet(socialActivitySet)) {
			return SocialActivitySet.class.getName();
		}

		return socialActivitySet.getClassName();
	}

	public static long getDiscussionClassPK(
		SocialActivitySet socialActivitySet) {

		if (isMultipleAddFileEntryActivitySet(socialActivitySet)) {
			return socialActivitySet.getActivitySetId();
		}

		return socialActivitySet.getClassPK();
	}

	private static boolean isMultipleAddFileEntryActivitySet(
		SocialActivitySet socialActivitySet) {

		String className = socialActivitySet.getClassName();

		if (className.equals(DLFileEntryConstants.getClassName()) &&
			(socialActivitySet.getActivityCount() > 1) &&
			(socialActivitySet.getType() == DLActivityKeys.ADD_FILE_ENTRY)) {

			return true;
		}

		return false;
	}

}