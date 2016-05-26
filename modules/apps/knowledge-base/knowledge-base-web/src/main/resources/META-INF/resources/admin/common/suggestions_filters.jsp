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

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/admin/view_suggestions.jsp");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("navigation", navigation);
%>

<liferay-frontend:management-bar-navigation
	navigationKeys='<%= new String[] {"all", "new", "in-progress", "resolved"} %>'
	portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
/>