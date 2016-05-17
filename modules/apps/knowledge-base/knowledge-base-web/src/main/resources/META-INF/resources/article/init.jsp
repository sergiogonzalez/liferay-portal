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
KBArticlePortletInstanceConfiguration kbArticlePortletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(KBArticlePortletInstanceConfiguration.class);

String portletResource = ParamUtil.getString(request, "portletResource");

long resourcePrimKey = kbArticlePortletInstanceConfiguration.resourcePrimKey();
boolean enableKBArticleDescription = kbArticlePortletInstanceConfiguration.enableKBArticleDescription();
boolean enableKBArticleRatings = kbArticlePortletInstanceConfiguration.enableKBArticleRatings();
String kbArticleRatingsType = kbArticlePortletInstanceConfiguration.kbArticleRatingsType();
boolean showKBArticleAssetEntries = kbArticlePortletInstanceConfiguration.showKBArticleAssetEntries();
boolean showKBArticleAttachments = kbArticlePortletInstanceConfiguration.showKBArticleAttachments();
boolean enableKBArticleAssetLinks = kbArticlePortletInstanceConfiguration.enableKBArticleAssetLinks();
boolean enableKBArticleViewCountIncrement = kbArticlePortletInstanceConfiguration.enableKBArticleViewCountIncrement();
boolean enableKBArticleSubscriptions = kbArticlePortletInstanceConfiguration.enableKBArticleSubscriptions();
boolean enableKBArticleHistory = kbArticlePortletInstanceConfiguration.enableKBArticleHistory();
boolean enableKBArticlePrint = kbArticlePortletInstanceConfiguration.enableKBArticlePrint();
boolean enableSocialBookmarks = kbArticlePortletInstanceConfiguration.enableSocialBookmarks();
String socialBookmarksDisplayStyle = kbArticlePortletInstanceConfiguration.socialBookmarksDisplayStyle();
String socialBookmarksDisplayPosition = kbArticlePortletInstanceConfiguration.socialBookmarksDisplayPosition();
String socialBookmarksTypes = kbArticlePortletInstanceConfiguration.socialBookmarksTypes();

boolean enableRSS = !PortalUtil.isRSSFeedsEnabled() ? false : kbGroupServiceConfiguration.enableRSS();
int rssDelta = kbGroupServiceConfiguration.rssDelta();
String rssDisplayStyle = kbGroupServiceConfiguration.rssDisplayStyle();
String rssFeedType = kbGroupServiceConfiguration.rssFeedType();
%>