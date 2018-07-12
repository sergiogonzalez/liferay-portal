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

<%@ include file="/dynamic_include/init.jsp" %>

<%
GoogleDriveFileReference googleDriveFileReference = (GoogleDriveFileReference)request.getAttribute(GoogleDriveOpenerWebKeys.GOOGLE_DRIVE_FILE_REFERENCE);
%>

<c:if test="<%= Validator.isNotNull(googleDriveFileReference) %>">
	<portlet:renderURL var="renderURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="/document_library/open_google_docs" />
		<portlet:param name="fileEntryId" value="<%= String.valueOf(googleDriveFileReference.getFileEntryId()) %>" />
		<portlet:param name="cookie" value="<%= String.valueOf(googleDriveFileReference.getCookie()) %>" />
	</portlet:renderURL>

	<aui:script>
		window.open('<%= renderURL %>');
	</aui:script>
</c:if>