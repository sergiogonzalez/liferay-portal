<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String rootPortletId = (String)request.getAttribute("configuration.jsp-rootPortletId");
%>

<c:choose>
	<c:when test="<%= rootPortletId.equals(PortletKeys.RELATED_ASSETS) %>">
		<aui:input name="preferences--selectionStyle--" type="hidden" value="dynamic" />
	</c:when>
	<c:otherwise>
		<aui:select label="asset-selection" name="preferences--selectionStyle--" onChange='<%= renderResponse.getNamespace() + "chooseSelectionStyle();" %>'>
			<aui:option label="dynamic" selected='<%= selectionStyle.equals("dynamic") %>'/>
			<aui:option label="manual" selected='<%= selectionStyle.equals("manual") %>'/>
		</aui:select>
	</c:otherwise>
</c:choose>