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
SocialBookmark socialBookmark = (SocialBookmark)request.getAttribute("liferay-social:bookmark:socialBookmark");
String url = GetterUtil.getString((String)request.getAttribute("liferay-social:bookmark:url"));
String title = GetterUtil.getString((String)request.getAttribute("liferay-social:bookmark:title"));
%>

<a href="<%= socialBookmark.getPostUrl(title, url) %>" style="background: #333333; color: white; border-radius: 2px; padding: 6px 8px;">
	<img src="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/share.png") %>" style="width: 22px; vertical-align: middle;"/> <%= socialBookmark.getName(locale) %>
</a>