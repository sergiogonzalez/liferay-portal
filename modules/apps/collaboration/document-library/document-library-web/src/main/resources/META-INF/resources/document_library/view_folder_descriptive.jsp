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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Folder folder = (Folder)row.getObject();

folder = folder.toEscapedModel();

String userName = folder.getUserName();
Date createDate = folder.getCreateDate();

String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);

PortletURL rowURL = liferayPortletResponse.createRenderURL();

rowURL.setParameter("mvcRenderCommandName", "/document_library/view_folder");
rowURL.setParameter("redirect", currentURL);
rowURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
%>

<h5 class="text-default">
	<c:choose>
		<c:when test="<%= Validator.isNotNull(userName) %>">
			<liferay-ui:message arguments="<%= new String[] {userName, createDateDescription} %>" key="x-created-x-ago" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments="<%= new String[] {createDateDescription} %>" key="created-x-ago" />
		</c:otherwise>
	</c:choose>
</h5>

<h4>
	<aui:a href="<%= rowURL.toString() %>">
		<%= folder.getName() %>
	</aui:a>
</h4>