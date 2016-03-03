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

package com.liferay.activities.web.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ActivitiesRequestHelper extends BaseRequestHelper {

	public ActivitiesRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public int getStart() {
		return ParamUtil.getInteger(getRequest(), "start");
	}

	public String getTabs1() {
		return ParamUtil.getString(getRequest(), "tabs1", "all");
	}

	public boolean isSocialActivitySetsEnabled() {
		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SOCIAL_ACTIVITY_SETS_ENABLED));
	}

}