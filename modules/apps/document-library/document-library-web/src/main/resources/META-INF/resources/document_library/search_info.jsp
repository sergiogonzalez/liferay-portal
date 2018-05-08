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
long repositoryId = ParamUtil.getLong(request, "repositoryId");

if (repositoryId == 0) {
	repositoryId = scopeGroupId;
}

long folderId = ParamUtil.getLong(request, "folderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");

Folder folder = DLAppServiceUtil.getFolder(folderId);

String keywords = ParamUtil.getString(request, "keywords");

boolean searchEverywhere = false;

if ((folder == null) || (searchFolderId == rootFolderId)) {
	searchEverywhere = true;
}
%>

<c:if test="<%= (folder != null) && (folderId != rootFolderId) %>">
	<div class="search-info">

		<%
		PortletURL searchEverywhereURL = liferayPortletResponse.createRenderURL();

		searchEverywhereURL.setParameter("folderId", String.valueOf(folderId));
		searchEverywhereURL.setParameter("keywords", keywords);
		searchEverywhereURL.setParameter("mvcRenderCommandName", "/document_library/search");
		searchEverywhereURL.setParameter("repositoryId", String.valueOf(repositoryId));
		searchEverywhereURL.setParameter("searchFolderId", String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
		searchEverywhereURL.setParameter("searchRepositoryId", String.valueOf(repositoryId));
		searchEverywhereURL.setParameter("showRepositoryTabs", Boolean.TRUE.toString());
		searchEverywhereURL.setParameter("showSearchInfo", Boolean.TRUE.toString());

		PortletURL searchFolderURL = PortletURLUtil.clone(searchEverywhereURL, liferayPortletResponse);

		searchFolderURL.setParameter("folderId", String.valueOf(folderId));
		searchFolderURL.setParameter("searchFolderId", String.valueOf(folderId));
		searchFolderURL.setParameter("searchRepositoryId", String.valueOf(scopeGroupId));
		searchFolderURL.setParameter("showRepositoryTabs", Boolean.FALSE.toString());
		%>

		<liferay-util:whitespace-remover>
			<liferay-ui:message key="search-colon" />

			<clay:link
				buttonStyle="secondary"
				elementClasses='<%= "btn-sm" + (searchEverywhere ? " active" : "") %>'
				href="<%= searchEverywhereURL.toString() %>"
				label='<%= LanguageUtil.get(resourceBundle, "everywhere") %>'
			/>

			<clay:link
				buttonStyle="secondary"
				elementClasses='<%= "btn-sm" + (!searchEverywhere ? " active" : "") %>'
				href="<%= searchFolderURL.toString() %>"
				icon="folder"
				label="<%= folder.getName() %>"
			/>
		</liferay-util:whitespace-remover>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(document.getElementsByName('<portlet:namespace />keywords')[0]);
			</aui:script>
		</c:if>
	</div>
</c:if>