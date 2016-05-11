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

<%@ include file="init.jsp" %>

<%
FileEntry fileEntry = ActionUtil.getFileEntry(liferayPortletRequest);

FileVersion fileVersion = ActionUtil.getFileVersion(liferayPortletRequest, fileEntry);

DLWebComponentProvider dlWebComponentProvider = DLWebComponentProvider.getDLWebComponentProvider();

DLDisplayContextProvider dlDisplayContextProvider = dlWebComponentProvider.getDLDisplayContextProvider();

ImageEditorDLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = (ImageEditorDLViewFileVersionDisplayContext)dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileVersion);

JavaScriptMenuItem javaScriptMenuItem = dlViewFileVersionDisplayContext.getJavacriptEditWithImageEditorMenuItem();
%>

<liferay-ui:menu-item menuItem="<%= javaScriptMenuItem %>" />