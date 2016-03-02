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

<%@ page import="com.liferay.activities.web.display.context.ActivitiesDisplayContext" %><%@
page import="com.liferay.activities.web.display.context.DefaultActivitiesDisplayContext" %><%@
page import="com.liferay.activities.web.display.context.util.ActivitiesRequestHelper" %><%@
page import="com.liferay.message.boards.kernel.model.MBMessage" %><%@
page import="com.liferay.microblogs.model.MicroblogsEntry" %><%@
page import="com.liferay.microblogs.service.MicroblogsEntryLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.ServiceContextFactory" %><%@
page import="com.liferay.portal.kernel.util.Time" %><%@
page import="com.liferay.social.kernel.model.SocialActivity" %><%@
page import="com.liferay.social.kernel.model.SocialActivitySet" %><%@
page import="com.liferay.social.kernel.service.SocialActivityLocalServiceUtil" %>

<%
ActivitiesRequestHelper activitiesRequestHelper = new ActivitiesRequestHelper(request);
ActivitiesDisplayContext activitiesDisplayContext = new DefaultActivitiesDisplayContext(activitiesRequestHelper);
%>

<%!
private static final int _DELTA = 10;
%>