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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.NoSuchModelException" %><%@
page import="com.liferay.portal.kernel.sanitizer.SanitizerException" %><%@
page import="com.liferay.portal.kernel.sanitizer.SanitizerUtil" %><%@
page import="com.liferay.portal.util.PortletKeys" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %><%@
page import="com.liferay.portlet.social.model.SocialActivity" %><%@
page import="com.liferay.portlet.social.model.SocialActivityConstants" %><%@
page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil" %><%@
page import="com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil" %><%@
page import="com.liferay.wiki.exception.DuplicateNodeNameException" %><%@
page import="com.liferay.wiki.exception.DuplicatePageException" %><%@
page import="com.liferay.wiki.exception.ImportFilesException" %><%@
page import="com.liferay.wiki.exception.NoSuchNodeException" %><%@
page import="com.liferay.wiki.exception.NoSuchPageException" %><%@
page import="com.liferay.wiki.exception.NodeChangeException" %><%@
page import="com.liferay.wiki.exception.NodeNameException" %><%@
page import="com.liferay.wiki.exception.PageContentException" %><%@
page import="com.liferay.wiki.exception.PageTitleException" %><%@
page import="com.liferay.wiki.exception.PageVersionException" %><%@
page import="com.liferay.wiki.exception.RequiredNodeException" %><%@
page import="com.liferay.wiki.exception.WikiFormatException" %><%@
page import="com.liferay.wiki.WikiPortletInstanceSettings" %><%@
page import="com.liferay.wiki.WikiSettings" %><%@
page import="com.liferay.wiki.web.context.WikiConfigurationDisplayContext" %><%@
page import="com.liferay.wiki.importers.WikiImporterKeys" %><%@
page import="com.liferay.wiki.model.WikiNode" %><%@
page import="com.liferay.wiki.model.WikiPage" %><%@
page import="com.liferay.wiki.model.WikiPageConstants" %><%@
page import="com.liferay.wiki.model.WikiPageDisplay" %><%@
page import="com.liferay.wiki.model.WikiPageResource" %><%@
page import="com.liferay.wiki.model.impl.WikiPageImpl" %><%@
page import="com.liferay.wiki.service.WikiNodeLocalServiceUtil" %><%@
page import="com.liferay.wiki.service.WikiNodeServiceUtil" %><%@
page import="com.liferay.wiki.service.WikiPageLocalServiceUtil" %><%@
page import="com.liferay.wiki.service.WikiPageResourceLocalServiceUtil" %><%@
page import="com.liferay.wiki.service.WikiPageServiceUtil" %><%@
page import="com.liferay.wiki.service.permission.WikiNodePermission" %><%@
page import="com.liferay.wiki.service.permission.WikiPagePermission" %><%@
page import="com.liferay.wiki.service.permission.WikiPermission" %><%@
page import="com.liferay.wiki.social.WikiActivityKeys" %><%@
page import="com.liferay.wiki.util.WikiCacheUtil" %><%@
page import="com.liferay.wiki.util.WikiConstants" %><%@
page import="com.liferay.wiki.util.WikiPageAttachmentsUtil" %><%@
page import="com.liferay.wiki.constants.WikiPortletKeys" %><%@
page import="com.liferay.wiki.util.WikiUtil" %><%@
page import="com.liferay.wiki.constants.WikiWebKeys" %><%@
page import="com.liferay.wiki.util.comparator.PageVersionComparator" %>

<%
String portletId = portletDisplay.getId();

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	 portletId = ParamUtil.getString(request, "portletResource");
}

WikiPortletInstanceSettings wikiPortletInstanceSettings = WikiPortletInstanceSettings.getInstance(layout, portletId);
WikiSettings wikiSettings = WikiSettings.getInstance(scopeGroupId);

WikiConfigurationDisplayContext wikiConfigurationDisplayContext = new WikiConfigurationDisplayContext(request, wikiPortletInstanceSettings);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/wiki/init-ext.jsp" %>