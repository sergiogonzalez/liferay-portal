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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.util.DateUtil" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.service.ServiceContext" %><%@
page import="com.liferay.portal.service.ServiceContextFactory" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portlet.social.model.SocialActivity" %><%@
page import="com.liferay.portlet.social.model.SocialActivityFeedEntry" %><%@
page import="com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil" %><%@
page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.Date" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.ResourceURL" %>

<liferay-theme:defineObjects />

<%
List<SocialActivity> activities = (List<SocialActivity>)request.getAttribute("liferay-social:activities:activities");
String className = (String)request.getAttribute("liferay-social:activities:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-social:activities:classPK"));
int feedDelta = GetterUtil.getInteger((String)request.getAttribute("liferay-social:activities:feedDelta"));
String feedDisplayStyle = (String)request.getAttribute("liferay-social:activities:feedDisplayStyle");
boolean feedEnabled = !PortalUtil.isRSSFeedsEnabled() ? false : GetterUtil.getBoolean((String)request.getAttribute("liferay-social:activities:feedEnabled"));
ResourceURL feedResourceURL = (ResourceURL)request.getAttribute("liferay-social:activities:feedResourceURL");
String feedTitle = (String)request.getAttribute("liferay-social:activities:feedTitle");
String feedType = (String)request.getAttribute("liferay-social:activities:feedType");
String feedURL = (String)request.getAttribute("liferay-social:activities:feedURL");
String feedURLMessage = (String)request.getAttribute("liferay-social:activities:feedURLMessage");

if (activities == null) {
	activities = SocialActivityLocalServiceUtil.getActivities(0, className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
}

String selector = StringPool.BLANK;

Format dateFormatDate = FastDateFormatFactoryUtil.getSimpleDateFormat("MMMM d", locale, timeZone);
Format timeFormatDate = FastDateFormatFactoryUtil.getTime(locale, timeZone);
%>

<%@ include file="/activities/init-ext.jsp" %>