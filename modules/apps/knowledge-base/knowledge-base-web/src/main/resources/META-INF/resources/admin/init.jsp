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

<%@ page import="com.liferay.document.library.kernel.util.DLUtil" %><%@
page import="com.liferay.knowledge.base.constants.KBCommentConstants" %><%@
page import="com.liferay.knowledge.base.service.util.AdminUtil" %><%@
page import="com.liferay.knowledge.base.web.display.context.KBSuggestionListDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.search.KBArticleDisplayTerms" %><%@
page import="com.liferay.knowledge.base.web.search.KBArticleSearch" %><%@
page import="com.liferay.knowledge.base.web.search.KBArticleSearchTerms" %><%@
page import="com.liferay.knowledge.base.web.search.KBTemplateDisplayTerms" %><%@
page import="com.liferay.knowledge.base.web.search.KBTemplateSearch" %><%@
page import="com.liferay.knowledge.base.web.search.KBTemplateSearchTerms" %><%@
page import="com.liferay.knowledge.base.web.util.KBArticleAssetEntriesUtil" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %>

<%
KBAdminPortletInstanceConfiguration kbAdminPortletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(KBAdminPortletInstanceConfiguration.class);

String kbArticlesOrderByCol = kbAdminPortletInstanceConfiguration.kbArticlesOrderByCol();
String kbArticlesOrderByType = kbAdminPortletInstanceConfiguration.kbArticlesOrderByType();

boolean enableKBArticleDescription = kbAdminPortletInstanceConfiguration.enableKBArticleDescription();
boolean enableKBArticleRatings = kbAdminPortletInstanceConfiguration.enableKBArticleRatings();
String kbArticleRatingsType = kbAdminPortletInstanceConfiguration.kbArticleRatingsType();
boolean showKBArticleAssetEntries = kbAdminPortletInstanceConfiguration.showKBArticleAssetEntries();
boolean showKBArticleAttachments = kbAdminPortletInstanceConfiguration.showKBArticleAttachments();
boolean enableKBArticleAssetLinks = kbAdminPortletInstanceConfiguration.enableKBArticleAssetLinks();
boolean enableKBArticleViewCountIncrement = kbAdminPortletInstanceConfiguration.enableKBArticleViewCountIncrement();
boolean enableKBArticleSubscriptions = kbAdminPortletInstanceConfiguration.enableKBArticleSubscriptions();
boolean enableKBArticleHistory = kbAdminPortletInstanceConfiguration.enableKBArticleHistory();
boolean enableKBArticlePrint = kbAdminPortletInstanceConfiguration.enableKBArticlePrint();
boolean enableSocialBookmarks = kbAdminPortletInstanceConfiguration.enableSocialBookmarks();
String socialBookmarksDisplayStyle = kbAdminPortletInstanceConfiguration.socialBookmarksDisplayStyle();
String socialBookmarksDisplayPosition = kbAdminPortletInstanceConfiguration.socialBookmarksDisplayPosition();
String socialBookmarksTypes = kbAdminPortletInstanceConfiguration.socialBookmarksDisplayPosition();

boolean enableKBTemplateKBComments = kbAdminPortletInstanceConfiguration.enableKBTemplateKBComments();
boolean showKBTemplateKBComments = kbAdminPortletInstanceConfiguration.showKBTemplateKBComments();

boolean enableRSS = kbGroupServiceConfiguration.enableRSS();
int rssDelta = kbGroupServiceConfiguration.rssDelta();
String rssDisplayStyle = kbGroupServiceConfiguration.rssDisplayStyle();
String rssFeedType = kbGroupServiceConfiguration.rssFeedType();
%>