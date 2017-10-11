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

<%@ include file="/document_library/init.jsp" %>

<%
String tab = ParamUtil.getString(request, "tab");
%>

<c:choose>
	<c:when test='<%= tab.equals("documents_and_media") %>'>
		<liferay-util:include page="/document_library/view_documents_and_media.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tab.equals("document_types") %>'>
		<liferay-util:include page="/document_library/view_file_entry_types.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/document_library/view_documents_and_media.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>