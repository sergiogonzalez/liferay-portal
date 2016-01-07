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
List<BookmarksFolder> folders = (List<BookmarksFolder>)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDERS);
List<BookmarksEntry> entries = (List<BookmarksEntry>)request.getAttribute(BookmarksWebKeys.BOOKMARKS_ENTRIES);

if (ListUtil.isEmpty(folders) && ListUtil.isEmpty(entries)) {
	long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"), ParamUtil.getLong(request, "folderId"));

	folders = new ArrayList<BookmarksFolder>();

	BookmarksFolder folder = (BookmarksFolder)request.getAttribute("view.jsp-folder");

	if (folder != null) {
		folders.add(folder);
	}
	else if (folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		folders.add(BookmarksFolderLocalServiceUtil.getFolder(folderId));
	}
	else {
		folders.add(null);
	}
}
%>

<c:choose>
	<c:when test="<%= (ListUtil.isEmpty(entries) && ListUtil.isNotEmpty(folders) && (folders.size() == 1)) %>">

		<%
		BookmarksFolder folder = folders.get(0);

		request.setAttribute("info_panel.jsp-folder", folder);
		%>

		<div class="sidebar-header">
			<ul class="list-inline list-unstyled sidebar-header-actions">
				<li>
					<liferay-util:include page="/bookmarks/subscribe.jsp" servletContext="<%= application %>" />
				</li>

				<li>
					<liferay-util:include page="/bookmarks/folder_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= (folder != null) ? folder.getName() : LanguageUtil.get(request, "home") %></h4>

			<div>
				<liferay-ui:message key="folder" />
			</div>
		</div>

		<liferay-ui:tabs names="details" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">
					<h5><liferay-ui:message key="num-of-items" /></h5>

					<%
					long folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

					if (folder != null) {
						folderId = folder.getFolderId();
					}
					%>

					<p>
						<%= BookmarksFolderServiceUtil.getFoldersAndEntriesCount(scopeGroupId, folderId, WorkflowConstants.STATUS_APPROVED) %>
					</p>

					<c:if test="<%= folder != null %>">
						<h5><liferay-ui:message key="created" /></h5>

						<p>
							<%= HtmlUtil.escape(folder.getUserName()) %>
						</p>
					</c:if>
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:when>
	<c:when test="<%= ListUtil.isEmpty(folders) && ListUtil.isNotEmpty(entries) && (entries.size() == 1) %>">

		<%
		BookmarksEntry entry = entries.get(0);

		request.setAttribute("info_panel.jsp-entry", entry);
		%>

		<div class="sidebar-header">
			<ul class="list-inline list-unstyled sidebar-header-actions">
				<li>
					<liferay-util:include page="/bookmarks/subscribe.jsp" servletContext="<%= application %>" />
				</li>

				<li>
					<liferay-util:include page="/bookmarks/entry_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= entry.getName() %></h4>

			<div>
				<liferay-ui:message key="entry" />
			</div>
		</div>

		<%
		String tabNames = "details,categorization,custom-fields";

		if (bookmarksGroupServiceOverriddenConfiguration.enableRelatedAssets()) {
			tabNames += ",related-assets";
		}
		%>

		<liferay-ui:tabs names="<%= tabNames %>" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">
					<h5><liferay-ui:message key="created" /></h5>

					<p>
						<%= HtmlUtil.escape(entry.getUserName()) %>
					</p>

					<c:if test="<%= Validator.isNotNull(entry.getDescription()) %>">
						<h5><liferay-ui:message key="description" /></h5>

						<p>
							<%= entry.getDescription() %>
						</p>
					</c:if>

					<h5><liferay-ui:message key="url" /></h5>

					<p>
						<%= entry.getUrl() %>
					</p>

					<h5><liferay-ui:message key="visits" /></h5>

					<p>
						<%= entry.getVisits() %>
					</p>

					<liferay-ui:ratings
						className="<%= BookmarksEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
					/>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="sidebar-body">
					<liferay-ui:asset-categories-summary
						className="<%= BookmarksEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
					/>

					<liferay-ui:asset-tags-summary
						className="<%= BookmarksEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
						message="tags"
					/>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="sidebar-body">
					<liferay-ui:custom-attribute-list
						className="<%= BookmarksEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
						editable="<%= false %>"
						label="<%= true %>"
					/>
				</div>
			</liferay-ui:section>

			<c:if test="<%= bookmarksGroupServiceOverriddenConfiguration.enableRelatedAssets() %>">

				<%
				AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.getEntry(BookmarksEntry.class.getName(), entry.getEntryId());
				%>

				<liferay-ui:section>
					<liferay-ui:asset-links
						assetEntryId="<%= layoutAssetEntry.getEntryId() %>"
					/>
				</liferay-ui:section>
			</c:if>
		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= folders.size() + entries.size() %>" key="x-items-selected" /></h4>
		</div>

		<liferay-ui:tabs names="details" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">
					<h5><liferay-ui:message arguments="<%= folders.size() + entries.size() %>" key="x-items-selected" /></h5>
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:otherwise>
</c:choose>