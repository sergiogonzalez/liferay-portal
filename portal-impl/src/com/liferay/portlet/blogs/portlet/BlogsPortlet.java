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

package com.liferay.portlet.blogs.portlet;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.action.TrackbackActionHelper;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Adolfo Pérez
 */
public class BlogsPortlet extends BaseBlogsPortlet {

	public void addTrackback(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		_trackbackActionHelper.addTrackback(resourceRequest, resourceResponse);
	}

	public void subscribe(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		BlogsEntryServiceUtil.subscribe(themeDisplay.getScopeGroupId());
	}

	public void unsubscribe(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		BlogsEntryServiceUtil.unsubscribe(themeDisplay.getScopeGroupId());
	}

	private final TrackbackActionHelper _trackbackActionHelper =
		new TrackbackActionHelper();

}