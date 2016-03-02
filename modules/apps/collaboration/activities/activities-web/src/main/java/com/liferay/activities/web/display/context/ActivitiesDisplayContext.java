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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityFeedEntry;
import com.liferay.social.kernel.model.SocialActivitySet;

import java.util.List;

import javax.portlet.PortletException;

/**
 * @author Adolfo Pérez
 */
public interface ActivitiesDisplayContext {

	public String getActivityItemCssClassWrapper(
		SocialActivityFeedEntry socialActivityFeedEntry);

	public String getDiscussionClassName(SocialActivitySet socialActivitySet);

	public long getDiscussionClassPK(SocialActivitySet socialActivitySet);

	public String getRepostMicroblogsEntryURL() throws PortletException;

	public String getSelectedTabName();

	public List<SocialActivity> getSocialActivities();

	public int getSocialActivitiesCount();

	public SocialActivityFeedEntry getSocialActivityFeedEntry(
			SocialActivitySet socialActivitySet)
		throws PortalException;

	public List<SocialActivitySet> getSocialActivitySets();

	public int getSocialActivitySetsCount();

	public String getTabsNames();

	public String getTabsURL();

	public String getViewActivitySetURL() throws PortletException;

	public String getViewCommentsURL();

	public boolean isActivityFooterVisible(SocialActivitySet socialActivitySet);

	public boolean isActivityLinkVisible(
		SocialActivityFeedEntry socialActivityFeedEntry);

	public boolean isMicroblogsRepostActionVisible(
		SocialActivitySet socialActivitySet);

	public boolean isTabsVisible();

}