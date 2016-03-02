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

package com.liferay.activities.web.display.context;

import com.liferay.activities.web.display.context.util.ActivitiesRequestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.social.kernel.model.SocialActivitySet;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.service.SocialActivitySetLocalServiceUtil;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

/**
 * @author Adolfo Pérez
 */
public class DefaultActivitiesDisplayContext
	implements ActivitiesDisplayContext {

	public DefaultActivitiesDisplayContext(
		ActivitiesRequestHelper activitiesRequestHelper) {

		_activitiesRequestHelper = activitiesRequestHelper;
	}

	@Override
	public String getRepostMicroblogsEntryURL() throws PortletException {
		LiferayPortletResponse liferayPortletResponse =
			_activitiesRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		portletURL.setParameter(
			"mvcPath", "/activities/repost_microblogs_entry.jsp");
		portletURL.setParameter(
			"redirect", _activitiesRequestHelper.getCurrentURL());

		return portletURL.toString();
	}

	@Override
	public String getSelectedTabName() {
		return _activitiesRequestHelper.getTabs1();
	}

	@Override
	public List<SocialActivitySet> getSocialActivitySets(int start, int end) {
		ThemeDisplay themeDisplay = _activitiesRequestHelper.getThemeDisplay();
		Group group = themeDisplay.getScopeGroup();

		if (!group.isUser()) {
			return SocialActivitySetLocalServiceUtil.getGroupActivitySets(
				group.getGroupId(), start, end);
		}

		Layout layout = _activitiesRequestHelper.getLayout();

		if (!layout.isPrivateLayout()) {
			return SocialActivitySetLocalServiceUtil.getUserActivitySets(
				group.getClassPK(), start, end);
		}

		String tabs1 = _activitiesRequestHelper.getTabs1();

		if (tabs1.equals("connections")) {
			return SocialActivitySetLocalServiceUtil.getRelationActivitySets(
				group.getClassPK(), SocialRelationConstants.TYPE_BI_CONNECTION,
				start, end);
		}
		else if (tabs1.equals("following")) {
			return SocialActivitySetLocalServiceUtil.getRelationActivitySets(
				group.getClassPK(), SocialRelationConstants.TYPE_UNI_FOLLOWER,
				start, end);
		}
		else if (tabs1.equals("me")) {
			return SocialActivitySetLocalServiceUtil.getUserActivitySets(
				group.getClassPK(), start, end);
		}
		else if (tabs1.equals("my-sites")) {
			return SocialActivitySetLocalServiceUtil.getUserGroupsActivitySets(
				group.getClassPK(), start, end);
		}
		else {
			return SocialActivitySetLocalServiceUtil.
				getUserViewableActivitySets(group.getClassPK(), start, end);
		}
	}

	@Override
	public int getSocialActivitySetsCount() {
		ThemeDisplay themeDisplay = _activitiesRequestHelper.getThemeDisplay();
		Group group = themeDisplay.getScopeGroup();
		Layout layout = _activitiesRequestHelper.getLayout();

		if (!group.isUser()) {
			return SocialActivitySetLocalServiceUtil.getGroupActivitySetsCount(
				group.getGroupId());
		}

		if (!layout.isPrivateLayout()) {
			return SocialActivitySetLocalServiceUtil.getUserActivitySetsCount(
				group.getClassPK());
		}

		String tabs1 = _activitiesRequestHelper.getTabs1();

		if (tabs1.equals("connections")) {
			return SocialActivitySetLocalServiceUtil.
				getRelationActivitySetsCount(
					group.getClassPK(),
					SocialRelationConstants.TYPE_BI_CONNECTION);
		}
		else if (tabs1.equals("following")) {
			return SocialActivitySetLocalServiceUtil.
				getRelationActivitySetsCount(
					group.getClassPK(),
					SocialRelationConstants.TYPE_UNI_FOLLOWER);
		}
		else if (tabs1.equals("me")) {
			return SocialActivitySetLocalServiceUtil.getUserActivitySetsCount(
				group.getClassPK());
		}
		else if (tabs1.equals("my-sites")) {
			return SocialActivitySetLocalServiceUtil.
				getUserGroupsActivitySetsCount(group.getClassPK());
		}
		else {
			return SocialActivitySetLocalServiceUtil.
				getUserViewableActivitySetsCount(group.getClassPK());
		}
	}

	@Override
	public String getTabsNames() {
		return "all,connections,following,my-sites,me";
	}

	@Override
	public String getTabsURL() {
		LiferayPortletResponse liferayPortletResponse =
			_activitiesRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", getSelectedTabName());

		return portletURL.toString();
	}

	@Override
	public String getViewActivitySetURL() throws PortletException {
		LiferayPortletResponse liferayPortletResponse =
			_activitiesRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);

		if (_activitiesRequestHelper.isSocialActivitySetsEnabled()) {
			portletURL.setParameter(
				"mvcPath", "/activities/view_activity_sets.jsp");
		}
		else {
			portletURL.setParameter(
				"mvcPath", "/activities/view_activities.jsp");
		}

		portletURL.setParameter("tabs1", _activitiesRequestHelper.getTabs1());

		return portletURL.toString();
	}

	@Override
	public String getViewCommentsURL() {
		LiferayPortletResponse liferayPortletResponse =
			_activitiesRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createResourceURL(
			"getComments");

		return portletURL.toString();
	}

	@Override
	public boolean isTabsVisible() {
		ThemeDisplay themeDisplay = _activitiesRequestHelper.getThemeDisplay();

		Group group = themeDisplay.getScopeGroup();
		Layout layout = _activitiesRequestHelper.getLayout();

		if (group.isUser() && layout.isPrivateLayout()) {
			return true;
		}
		else {
			return false;
		}
	}

	private static final int _DELTA = 10;

	private final ActivitiesRequestHelper _activitiesRequestHelper;

}