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
long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long resourceClassNameId = kbFolderClassNameId;
long resourcePrimKey = 0;

if (resourceClassNameId == 0) {
	resourceClassNameId = kbFolderClassNameId;
}

String kbArticlesOrderByCol = "priority";
String kbArticlesOrderByType = "desc";

boolean enableKBArticleDescription = false;
boolean enableKBArticleRatings = true;
String kbArticleRatingsType = "thumbs";
boolean showKBArticleAssetEntries = true;
boolean showKBArticleAttachments = true;
boolean enableKBArticleAssetLinks = true;
boolean enableKBArticleViewCountIncrement = true;
boolean enableKBArticleSubscriptions = true;
boolean enableKBArticleHistory = true;
boolean enableKBArticlePrint = true;
boolean enableSocialBookmarks = false;
String socialBookmarksDisplayStyle = "menu";
String socialBookmarksDisplayPosition = "bottom";
String socialBookmarksTypes = PropsKeys.SOCIAL_BOOKMARK_TYPES;

boolean enableKBTemplateKBComments = true;
boolean showKBTemplateKBComments = true;

boolean enableRSS = kbGroupServiceConfiguration.enableRSS();
int rssDelta = kbGroupServiceConfiguration.rssDelta();
String rssDisplayStyle = kbGroupServiceConfiguration.rssDisplayStyle();
String rssFeedType = kbGroupServiceConfiguration.rssFeedType();
%>