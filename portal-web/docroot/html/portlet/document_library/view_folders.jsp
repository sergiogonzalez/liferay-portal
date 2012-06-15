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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

boolean expandFolder = ParamUtil.getBoolean(request, "expandFolder");

Folder parentFolder = null;

if (folder != null) {
	parentFolderId = folder.getParentFolderId();

	if (expandFolder) {
		parentFolderId = folderId;
	}

	if ((parentFolderId == 0) && (repositoryId != scopeGroupId) || (folder.isRoot() && !folder.isDefaultRepository())) {
		if (folder.isMountPoint()) {
			parentFolderId = folderId;
		}
		else {
			parentFolderId = DLAppLocalServiceUtil.getMountFolder(repositoryId).getFolderId();

			folderId = parentFolderId;

			folder = DLAppServiceUtil.getFolder(folderId);
		}
	}

	if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		try {
			parentFolder = DLAppServiceUtil.getFolder(folderId);
		}
		catch (NoSuchFolderException nsfe) {
			parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}
	}
}

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", entriesPerPage);

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

List<Folder> folders = DLAppServiceUtil.getFolders(repositoryId, parentFolderId, false, folderStart, folderEnd);
List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, folderStart, folderEnd);

int total = 0;

if (folderId != rootFolderId) {
	total = DLAppServiceUtil.getFoldersCount(repositoryId, parentFolderId, false);
}

request.setAttribute("view_folders.jsp-total", String.valueOf(total));
%>

<div class="lfr-header-row">
	<div class="lfr-header-row-content" id="<portlet:namespace />parentFolderTitleContainer">
		<div class="parent-folder-title" id="<portlet:namespace />parentFolderTitle">
			<c:choose>
				<c:when test="<%= (folderId != rootFolderId) && (parentFolderId > 0) && (folder != null) && (!folder.isMountPoint() || expandFolder) %>">

					<%
					Folder grandParentFolder = DLAppServiceUtil.getFolder(parentFolderId);
					%>

					<span>
						<%= grandParentFolder.getName() %>
					</span>
				</c:when>
				<c:when test="<%= ((folderId != rootFolderId) && (parentFolderId == 0)) || ((folderId == rootFolderId) && (parentFolderId == 0) && expandFolder) %>">
					<span>
						<liferay-ui:message key="home" />
					</span>
				</c:when>
			</c:choose>
		</div>
	</div>
</div>

<div class="body-row">
	<div id="<portlet:namespace />listViewContainer">
		<div class="folder-display-style lfr-list-view-content" id="<portlet:namespace />folderContainer">
			<ul class="lfr-component">
				<c:choose>
					<c:when test="<%= ((folderId == rootFolderId) && !expandFolder) || ((folder != null) && (folder.isRoot() && !folder.isDefaultRepository() && !expandFolder)) %>">

						<%
						int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, folderId);
						%>

						<liferay-portlet:renderURL varImpl="viewDocumentsHomeURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<%
						String navigation = ParamUtil.getString(request, "navigation", "home");

						long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

						request.setAttribute("view_entries.jsp-folder", folder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(folderId));
						request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(repositoryId));
						%>

						<liferay-ui:folder-navigation
							actionFolderJSP="/html/portlet/document_library/folder_action.jsp"
							curFolderId="<%= rootFolderId %>"
							curFolderName="<%= LanguageUtil.get(pageContext, "home") %>"
							expandNode="<%= (foldersCount > 0) %>"
							imageNode="../aui/home"
							selectedFolder="<%= (navigation.equals("home") && (folderId == rootFolderId) && (fileEntryTypeId == -1)) %>"
							viewURL="<%= viewDocumentsHomeURL %>"
						/>

						<c:if test="<%= rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
							<liferay-portlet:renderURL varImpl="viewRecentDocumentsURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="navigation" value="recent" />
								<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<li class="folder <%= navigation.equals("recent") ? "selected" : StringPool.BLANK %>">
								<a class="browse-folder" data-navigation="recent" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewRecentDocumentsURL.toString() %>">
									<liferay-ui:icon image="../aui/clock" message="" />

									<span class="entry-title">
										<%= LanguageUtil.get(pageContext, "recent") %>
									</span>
								</a>
							</li>

							<c:if test="<%= themeDisplay.isSignedIn() %>">
								<liferay-portlet:renderURL varImpl="viewMyDocumentsURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="navigation" value="mine" />
									<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
									<portlet:param name="entryStart" value="0" />
									<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
									<portlet:param name="folderStart" value="0" />
									<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
								</liferay-portlet:renderURL>

								<li class="folder <%= navigation.equals("mine") ? "selected" : StringPool.BLANK %>">
									<a class="browse-folder" data-navigation="mine" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewMyDocumentsURL.toString() %>">
										<liferay-ui:icon image="../aui/person" message="" />

										<span class="entry-title">
											<%= LanguageUtil.get(pageContext, "mine") %>
										</span>
									</a>
								</li>
							</c:if>

							<%
							List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeServiceUtil.getFileEntryTypes(DLUtil.getGroupIds(themeDisplay));
							%>

							<c:if test="<%= !fileEntryTypes.isEmpty() %>">
								<liferay-portlet:renderURL varImpl="viewBasicFileEntryTypeURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
									<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(0) %>" />
									<portlet:param name="entryStart" value="0" />
									<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
									<portlet:param name="folderStart" value="0" />
									<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
								</liferay-portlet:renderURL>

								<li class="folder file-entry-type <%= (fileEntryTypeId == 0) ? "selected" : StringPool.BLANK %>">
									<a class="browse-folder" data-file-entry-type-id="<%= String.valueOf(0) %>" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewBasicFileEntryTypeURL.toString() %>">
										<liferay-ui:icon image="copy" message="" />

										<span class="entry-title">
											<%= LanguageUtil.get(pageContext, "basic-document") %>
										</span>
									</a>
								</li>
							</c:if>

							<%
							for (DLFileEntryType fileEntryType : fileEntryTypes) {
							%>

								<liferay-portlet:renderURL varImpl="viewFileEntryTypeURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="folderId" value="<%= String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
									<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
									<portlet:param name="entryStart" value="0" />
									<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
									<portlet:param name="folderStart" value="0" />
									<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
								</liferay-portlet:renderURL>

								<li class="folder file-entry-type <%= (fileEntryTypeId == fileEntryType.getFileEntryTypeId()) ? "selected" : StringPool.BLANK %>">
									<a class="browse-folder" data-file-entry-type-id="<%= fileEntryType.getFileEntryTypeId() %>" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewFileEntryTypeURL.toString() %>">
										<liferay-ui:icon image="copy" message="" />

										<span class="entry-title">
											<%= HtmlUtil.escape(fileEntryType.getName()) %>
										</span>
									</a>
								</li>

							<%
							}

							for (Folder mountFolder : mountFolders) {
								request.setAttribute("view_entries.jsp-folder", mountFolder);
								request.setAttribute("view_entries.jsp-folderId", String.valueOf(mountFolder.getFolderId()));
								request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(mountFolder.getRepositoryId()));

								try {
									int mountFoldersCount = DLAppServiceUtil.getFoldersCount(mountFolder.getRepositoryId(), mountFolder.getFolderId());
							%>

									<liferay-portlet:renderURL varImpl="viewURL">
										<portlet:param name="struts_action" value="/document_library/view" />
										<portlet:param name="folderId" value="<%= String.valueOf(mountFolder.getFolderId()) %>" />
										<portlet:param name="entryStart" value="0" />
										<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
										<portlet:param name="folderStart" value="0" />
										<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
									</liferay-portlet:renderURL>

									<liferay-ui:folder-navigation
										actionFolderJSP="/html/portlet/document_library/folder_action.jsp"
										curFolderId="<%= mountFolder.getFolderId() %>"
										curFolderName="<%= mountFolder.getName() %>"
										expandNode="<%= (mountFoldersCount > 0) %>"
										imageNode="drive"
										selectedFolder="<%= mountFolder.getFolderId() == folderId %>"
										repositoryId="<%= mountFolder.getRepositoryId() %>"
										viewURL="<%= viewURL %>"
									/>

							<%
								}
								catch (Exception e) {
							%>

									<li class="folder error" title="<%= LanguageUtil.get(pageContext, "an-unexpected-error-occurred-while-connecting-to-the-repository") %>">

										<%
										request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
										%>

										<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />

										<span class="browse-folder">
											<liferay-ui:icon image="drive_error" />

											<span class="entry-title">
												<%= mountFolder.getName() %>
											</span>
										</span>
									</li>

							<%
								}
							}
							%>

						</c:if>
					</c:when>
					<c:otherwise>
						<liferay-portlet:renderURL varImpl="viewURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<%
						PortletURL expandViewURL = PortletURLUtil.clone(viewURL, liferayPortletResponse);

						expandViewURL.setParameter("expandFolder", Boolean.TRUE.toString());
						%>

						<li class="folder">
							<a class="expand-folder" data-direction-right="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(parentFolderId) %>" data-view-entries="<%= Boolean.FALSE.toString() %>" href="<%= expandViewURL.toString() %>">
								<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-l" message="collapse" />
							</a>

							<a class="browse-folder" data-direction-right="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(parentFolderId) %>" href="<%= viewURL.toString() %>">
								<liferay-ui:icon message="up" src='<%= themeDisplay.getPathThemeImages() + "/arrows/01_up.png" %>' />

								<%= LanguageUtil.get(pageContext, "up") %>
							</a>
						</li>

						<%
						for (Folder curFolder : folders) {
							int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, curFolder.getFolderId());
							int fileEntriesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(repositoryId, curFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED);

							request.setAttribute("view_entries.jsp-folder", curFolder);
							request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
							request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(curFolder.getRepositoryId()));
							request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == curFolder.getFolderId()));
						%>

							<liferay-portlet:renderURL varImpl="viewURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:folder-navigation
								actionFolderJSP="/html/portlet/document_library/folder_action.jsp"
								curFolderId="<%= curFolder.getFolderId() %>"
								curFolderName="<%= curFolder.getName() %>"
								expandNode="<%= (foldersCount > 0) %>"
								imageNode="<%= ((foldersCount + fileEntriesCount) > 0) ? "folder_full_document" : "folder_empty" %>"
								selectedFolder="<%= curFolder.getFolderId() == folderId %>"
								repositoryId="<%= curFolder.getRepositoryId() %>"
								viewURL="<%= viewURL %>"
							/>

						<%
						}
						%>

					</c:otherwise>
				</c:choose>
			</ul>

			<aui:script>
				Liferay.fire(
					'<portlet:namespace />pageLoaded',
					{
						paginator: {
							name: 'folderPaginator',
							state: {
								page: <%= folderEnd / (folderEnd - folderStart) %>,
								rowsPerPage: <%= (folderEnd - folderStart) %>,
								total: <%= total %>
							}
						}
					}
				);
			</aui:script>
		</div>
	</div>
</div>