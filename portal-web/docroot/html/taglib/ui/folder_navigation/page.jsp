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

<%@ include file="/html/taglib/ui/folder_navigation/init.jsp" %>

<%
String actionFolderJSP = (String)request.getAttribute("liferay-ui:folder-navigator:actionFolderJSP");
long curFolderId = GetterUtil.getLong(request.getAttribute("liferay-ui:folder-navigator:curFolderId"));
String curFolderName = (String)request.getAttribute("liferay-ui:folder-navigator:curFolderName");
boolean expandNode = GetterUtil.getBoolean(request.getAttribute("liferay-ui:folder-navigator:expandNode"));
String imageNode = GetterUtil.getString(request.getAttribute("liferay-ui:folder-navigator:imageNode"));
boolean selectedFolder = GetterUtil.getBoolean(request.getAttribute("liferay-ui:folder-navigator:selectedFolder"));
long repositoryId = GetterUtil.getLong(request.getAttribute("liferay-ui:folder-navigator:repositoryId"));
PortletURL viewURL = (PortletURL)request.getAttribute("liferay-ui:folder-navigator:viewURL");
%>

<li class="folder <%= (selectedFolder) ? "selected" : StringPool.BLANK %>">

	<%
	request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	%>

	<liferay-util:include page="<%= actionFolderJSP %>" />

	<c:if test="<%= expandNode %>">

		<%
		viewURL.setParameter("expandFolder", Boolean.TRUE.toString());
		%>

		<a class="expand-folder" data-expand-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(curFolderId) %>" data-view-entries="<%= Boolean.FALSE.toString() %>" href="<%= viewURL.toString() %>">
			<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" message="expand" />
		</a>
	</c:if>

	<%
	viewURL.setParameter("expandFolder", Boolean.FALSE.toString());
	%>

	<a class="browse-folder" data-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(curFolderId) %>" data-repository-id="<%= repositoryId %>" data-title="<%= curFolderName %>" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewURL.toString() %>">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(imageNode) %>">
				<liferay-ui:icon image="<%= imageNode %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:icon image="folder_empty" />
			</c:otherwise>
		</c:choose>

		<span class="article-title">
			<%= curFolderName %>
		</span>
	</a>
</li>