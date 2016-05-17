<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

String portletResource = ParamUtil.getString(request, "portletResource");

KBDisplayPortletInstanceConfiguration kbDisplayPortletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(KBDisplayPortletInstanceConfiguration.class);

long resourceClassNameId = kbDisplayPortletInstanceConfiguration.resourceClassNameId();

if (resourceClassNameId == 0) {
	resourceClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());
}

long resourcePrimKey = kbDisplayPortletInstanceConfiguration.resourcePrimKey();

boolean enableKBArticleDescription = kbDisplayPortletInstanceConfiguration.enableKBArticleDescription();
boolean enableKBArticleRatings = kbDisplayPortletInstanceConfiguration.enableKBArticleRatings();
String kbArticleRatingsType = kbDisplayPortletInstanceConfiguration.kbArticleRatingsType();
boolean showKBArticleAssetEntries = kbDisplayPortletInstanceConfiguration.showKBArticleAssetEntries();
boolean showKBArticleAttachments = kbDisplayPortletInstanceConfiguration.showKBArticleAttachments();
boolean enableKBArticleAssetLinks = kbDisplayPortletInstanceConfiguration.enableKBArticleAssetLinks();
boolean enableKBArticleViewCountIncrement = kbDisplayPortletInstanceConfiguration.enableKBArticleViewCountIncrement();
boolean enableKBArticleSubscriptions = kbDisplayPortletInstanceConfiguration.enableKBArticleSubscriptions();
boolean enableKBArticleHistory = kbDisplayPortletInstanceConfiguration.enableKBArticleHistory();
boolean enableKBArticlePrint = kbDisplayPortletInstanceConfiguration.enableKBArticlePrint();
boolean enableSocialBookmarks = kbDisplayPortletInstanceConfiguration.enableSocialBookmarks();
String socialBookmarksDisplayStyle = kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayStyle();
String socialBookmarksDisplayPosition = kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayPosition();
String socialBookmarksTypes = kbDisplayPortletInstanceConfiguration.socialBookmarksTypes();
String contentRootPrefix = kbDisplayPortletInstanceConfiguration.contentRootPrefix();

boolean enableRSS = kbGroupServiceConfiguration.enableRSS();
int rssDelta = kbGroupServiceConfiguration.rssDelta();
String rssDisplayStyle = kbGroupServiceConfiguration.rssDisplayStyle();
String rssFeedType = kbGroupServiceConfiguration.rssFeedType();
%>