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

<%@ include file="/bookmarks/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", rootFolderId);

boolean defaultFolderView = false;

if ((folder == null) && (folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	defaultFolderView = true;
}

if (defaultFolderView) {
	try {
		folder = BookmarksFolderServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = (assetCategoryId > 0) || Validator.isNotNull(assetTagName);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/bookmarks/view");
portletURL.setParameter("navigation", navigation);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());

request.setAttribute("view.jsp-useAssetEntryQuery", String.valueOf(useAssetEntryQuery));

BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
%>

<portlet:actionURL name="/bookmarks/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-util:include page="/bookmarks/navigation.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/bookmarks/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="entries" />
</liferay-util:include>

<%
SearchContainer bookmarksSearchContainer = new SearchContainer(liferayPortletRequest, null, null, "curEntry", SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-bookmarks-in-this-folder");

List results = null;
int total = 0;

if (navigation.equals("mine") || navigation.equals("recent")) {
	long groupEntriesUserId = 0;

	if (navigation.equals("mine") && themeDisplay.isSignedIn()) {
		groupEntriesUserId = user.getUserId();
	}

	results = BookmarksEntryServiceUtil.getGroupEntries(scopeGroupId, groupEntriesUserId, bookmarksSearchContainer.getStart(), bookmarksSearchContainer.getEnd());
	total = BookmarksEntryServiceUtil.getGroupEntriesCount(scopeGroupId, groupEntriesUserId);
}
else {
	if (useAssetEntryQuery) {
			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(BookmarksEntry.class.getName(), bookmarksSearchContainer);

			assetEntryQuery.setExcludeZeroViewCount(false);
			assetEntryQuery.setEnd(bookmarksSearchContainer.getEnd());
			assetEntryQuery.setStart(bookmarksSearchContainer.getStart());

			results = AssetEntryServiceUtil.getEntries(assetEntryQuery);
	}
	else {
		results = BookmarksFolderServiceUtil.getFoldersAndEntries(scopeGroupId, folderId, WorkflowConstants.STATUS_APPROVED, bookmarksSearchContainer.getStart(), bookmarksSearchContainer.getEnd());
		total = BookmarksFolderServiceUtil.getFoldersAndEntriesCount(scopeGroupId, folderId);
	}
}
%>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<div class="sidenav-menu-slider">
		<div class="sidebar sidebar-default sidenav-menu">
			<liferay-util:include page="/bookmarks/info_panel.jsp" servletContext="<%= application %>" />
		</div>
	</div>
	<div class="sidenav-content">
		<div class="bookmakrs-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
			<c:if test='<%= !navigation.equals("recent") && !navigation.equals("mine") %>'>
				<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />
			</c:if>
		</div>

		<%
		String searchContainerId = ParamUtil.getString(request, "searchContainerId");
		%>

		<liferay-ui:search-container
			id="<%= searchContainerId %>"
			searchContainer="<%= bookmarksSearchContainer %>"
			total="<%= total %>"
			totalVar="bookmarksSearchContainerTotal"
		>
			<liferay-ui:search-container-results
				results="<%= results %>"
				resultsVar="bookmarksSearchContainerResults"
			/>

			<liferay-ui:search-container-row
				className="Object"
				modelVar="result"
			>
				<%@ include file="/bookmarks/cast_result.jspf" %>

				<c:choose>
					<c:when test="<%= curFolder != null %>">
						<liferay-portlet:renderURL varImpl="rowURL">
							<portlet:param name="mvcRenderCommandName" value="/bookmarks/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</liferay-portlet:renderURL>

						<%@ include file="/bookmarks/folder_columns.jspf" %>
					</c:when>
					<c:otherwise>

						<%
						String rowHREF = null;

						if (BookmarksEntryPermissionChecker.contains(permissionChecker, entry, ActionKeys.VIEW)) {
							PortletURL tempRowURL = renderResponse.createRenderURL();

							tempRowURL.setParameter("mvcRenderCommandName", "/bookmarks/view_entry");
							tempRowURL.setParameter("redirect", currentURL);
							tempRowURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

							rowHREF = tempRowURL.toString();
						}
						%>

						<%@ include file="/bookmarks/entry_columns.jspf" %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" resultRowSplitter="<%= new BookmarksResultRowSplitter() %>" searchContainer="<%= bookmarksSearchContainer %>" />
		</liferay-ui:search-container>
	</div>
</div>

<c:if test="<%= portletName.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN) %>">
	<liferay-util:include page="/bookmarks/add_button.jsp" servletContext="<%= application %>" />
</c:if>

<%
if (navigation.equals("home") && !defaultFolderView && (folder != null) && (portletName.equals(BookmarksPortletKeys.BOOKMARKS) || portletName.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN))) {
	PortalUtil.setPageSubtitle(folder.getName(), request);
	PortalUtil.setPageDescription(folder.getDescription(), request);
}
else {
	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, navigation), currentURL);
	}

	PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(navigation, StringPool.UNDERLINE, StringPool.DASH)), request);
}
%>

<aui:script>
	$('#<portlet:namespace />infoPanelId').sideNavigation(
		{
			gutter: 15,
			position: 'right',
			toggler: '.infoPanelToggler',
			type: 'relative',
			typeMobile: 'fixed',
			width: 320
		}
	);
</aui:script>