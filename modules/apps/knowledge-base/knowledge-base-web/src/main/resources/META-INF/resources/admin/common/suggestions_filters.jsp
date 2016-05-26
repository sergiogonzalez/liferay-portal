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

<%@ include file="/admin/common/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "all");

String orderByCol = GetterUtil.getString((String)request.getAttribute("view_suggestions_by_status.jsp-orderByCol"));
String orderByType = GetterUtil.getString((String)request.getAttribute("view_suggestions_by_status.jsp-orderByType"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/admin/view_suggestions.jsp");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("navigation", navigation);

Map<String, String> orderColumns = new HashMap<String, String>();

orderColumns.put("modifiedDate", "modified-date");
orderColumns.put("userId", "user");
%>

<liferay-frontend:management-bar-navigation
	navigationKeys='<%= new String[] {"all", "new", "in-progress", "resolved"} %>'
	portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
/>

<liferay-frontend:management-bar-sort
	orderByCol="<%= orderByCol %>"
	orderByType="<%= orderByType %>"
	orderColumns="<%= orderColumns %>"
	portletURL="<%= portletURL %>"
/>