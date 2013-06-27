<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute("view_entries.jsp-folder");

String folderImage = (String)request.getAttribute("view_entries.jsp-folderImage");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");

String actionJsp = "";

boolean showCheckbox = false;

if (ArrayUtil.contains(entryColumns, "action")) {
	actionJsp = "/html/portlet/document_library/folder_action.jsp";

	if (DLFolderPermission.contains(permissionChecker, folder, ActionKeys.DELETE) || DLFolderPermission.contains(permissionChecker, folder, ActionKeys.UPDATE)) {
		showCheckbox = true;
	}
}
%>

<liferay-ui:app-view-entry
	actionJsp="<%= actionJsp %>"
	author="<%= folder.getUserName() %>"
	createDate="<%= folder.getCreateDate() %>"
	description="<%= folder.getDescription() %>"
	displayStyle="descriptive"
	folder="<%= true %>"
	modifiedDate="<%= folder.getModifiedDate() %>"
	rowCheckerId="<%= String.valueOf(folder.getFolderId()) %>"
	rowCheckerName="<%= Folder.class.getSimpleName() %>"
	showCheckbox="<%= showCheckbox %>"
	thumbnailSrc='<%= themeDisplay.getPathThemeImages() + "/file_system/large/" + folderImage + ".png" %>'
	thumbnailStyle='<%= "width: " + PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH) + "px" %>'
	title="<%= folder.getName() %>"
	url="<%= tempRowURL.toString() %>"
/>