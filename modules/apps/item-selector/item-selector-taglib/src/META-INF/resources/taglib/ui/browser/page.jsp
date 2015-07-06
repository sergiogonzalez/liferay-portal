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

<%@ include file="/taglib/ui/browser/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_browser_page") + StringPool.UNDERLINE;

String displayStyle = GetterUtil.getString(request.getAttribute("liferay-ui:item-selector-browser:displayStyle"));
PortletURL displayStyleURL = (PortletURL)request.getAttribute("liferay-ui:item-selector-browser:displayStyleURL");
ItemSelectorReturnType draggableFileReturnType = (ItemSelectorReturnType)request.getAttribute("liferay-ui:item-selector-browser:draggableFileReturnType");
ItemSelectorReturnType existingFileEntryReturnType = (ItemSelectorReturnType)request.getAttribute("liferay-ui:item-selector-browser:existingFileEntryReturnType");
String itemSelectedEventName = GetterUtil.getString(request.getAttribute("liferay-ui:item-selector-browser:itemSelectedEventName"));
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:item-selector-browser:searchContainer");
PortletURL searchURL = (PortletURL)request.getAttribute("liferay-ui:item-selector-browser:searchURL");
String tabName = GetterUtil.getString(request.getAttribute("liferay-ui:item-selector-browser:tabName"));
String uploadMessage = GetterUtil.getString(request.getAttribute("liferay-ui:item-selector-browser:uploadMessage"));
%>

<div class="lfr-item-viewer" id="<%= randomNamespace %>ItemSelectorContainer">
	<c:if test="<%= displayStyleURL != null %>">
		<aui:nav-bar>
			<aui:nav collapsible="<%= true %>" cssClass="nav-display-style-buttons navbar-nav" icon="th-list" id="displayStyleButtons">
				<liferay-ui:app-view-display-style
					displayStyle="<%= displayStyle %>"
					displayStyleURL="<%= displayStyleURL %>"
					displayStyles='<%= new String[] {"descriptive", "list"} %>'
				/>
			</aui:nav>

			<c:if test="<%= searchURL != null %>">
				<aui:nav-bar-search>
					<div class="form-search">
						<aui:form action="<%= searchURL %>" method="get" name="searchFm">
							<liferay-portlet:renderURLParams portletURL="<%= searchURL %>" />

							<liferay-ui:input-search />
						</aui:form>
					</div>
				</aui:nav-bar-search>
			</c:if>
		</aui:nav-bar>
	</c:if>

	<c:if test="<%= draggableFileReturnType != null %>">
		<div class="drop-zone" data-returnType="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(draggableFileReturnType)) %>" data-value="<%= ItemSelectorBrowserReturnTypeUtil.getValue(draggableFileReturnType, null, null) %>">
			<label class="btn btn-primary" for="<%= randomNamespace %>InputFile"><liferay-ui:message key="select-file" /></label>

			<input class="hide" id="<%= randomNamespace %>InputFile" type="file" />

			<p>
				<%= uploadMessage %>
			</p>
		</div>
	</c:if>

	<c:if test="<%= existingFileEntryReturnType != null %>">
		<c:choose>
			<c:when test='<%= displayStyle.equals("list") %>'>
				<div class="list-content">
					<liferay-ui:search-container
						searchContainer="<%= searchContainer %>"
						total="<%= searchContainer.getTotal() %>"
						var="listSearchContainer"
					>
						<liferay-ui:search-container-results
							results="<%= searchContainer.getResults() %>"
						/>

						<liferay-ui:search-container-row
							className="com.liferay.portal.kernel.repository.model.RepositoryEntry"
							modelVar="repositoryEntry"
						>

							<%
							FileEntry fileEntry = null;
							FileShortcut fileShortcut = null;
							Folder folder = null;

							if (repositoryEntry instanceof FileEntry) {
								fileEntry = (FileEntry)repositoryEntry;
							}
							else if (repositoryEntry instanceof FileShortcut) {
								fileShortcut = (FileShortcut)repositoryEntry;

								fileEntry = DLAppLocalServiceUtil.getFileEntry(fileShortcut.getToFileEntryId());
							}
							else {
								folder = (Folder)repositoryEntry;
							}

							if (fileEntry != null) {
								FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

								String title = DLUtil.getTitleWithExtension(fileEntry);

								JSONObject itemMedatadaJSONObject = ItemSelectorBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);
							%>

								<liferay-ui:search-container-column-text name="title">
									<a class="item-preview" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(existingFileEntryReturnType)) %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorBrowserReturnTypeUtil.getValue(existingFileEntryReturnType, fileEntry, themeDisplay)) %>" href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" title="<%= HtmlUtil.escapeAttribute(title) %>">

										<%
										String iconCssClass = DLUtil.getFileIconCssClass(fileEntry.getExtension());
										%>

										<c:if test="<%= Validator.isNotNull(iconCssClass) %>">
											<i class="<%= iconCssClass %>"></i>
										</c:if>

										<span class="taglib-text">
											<%= HtmlUtil.escape(title) %>
										</span>
									</a>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text name="size" value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>" />

								<liferay-ui:search-container-column-status name="status" status="<%= latestFileVersion.getStatus() %>" />

								<liferay-ui:search-container-column-text name="modified-date">
									<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - fileEntry.getModifiedDate().getTime(), true), HtmlUtil.escape(fileEntry.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
								</liferay-ui:search-container-column-text>

							<%
							}
							if (folder != null) {
								PortletURL viewFolderURL = PortletURLUtil.clone(searchContainer.getIteratorURL(), liferayPortletResponse);

								viewFolderURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
							%>

								<liferay-ui:search-container-column-text name="title">
									<a href="<%= HtmlUtil.escapeHREF(viewFolderURL.toString()) %>" title="<%= HtmlUtil.escapeAttribute(folder.getName()) %>">

										<i class="icon-folder-open"></i>

										<span class="taglib-text">
											<%= HtmlUtil.escape(folder.getName()) %>
										</span>
									</a>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text name="size" value="--" />

								<liferay-ui:search-container-column-text name="status" value="--" />

								<liferay-ui:search-container-column-text name="modified-date">
									<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - folder.getModifiedDate().getTime(), true), HtmlUtil.escape(folder.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
								</liferay-ui:search-container-column-text>

							<%
							}
							%>

						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator />
					</liferay-ui:search-container>
				</div>
			</c:when>
			<c:otherwise>
				<ul class="tabular-list-group">

				<%
				for (Object result : searchContainer.getResults()) {

					FileEntry fileEntry = null;
					FileShortcut fileShortcut = null;
					Folder folder = null;

					if (result instanceof FileEntry) {
						fileEntry = (FileEntry)result;
					}
					else if (result instanceof FileShortcut) {
						fileShortcut = (FileShortcut)result;

						fileEntry = DLAppLocalServiceUtil.getFileEntry(fileShortcut.getToFileEntryId());
					}
					else {
						folder = (Folder)result;
					}

					if (fileEntry != null) {

						FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

						String title = DLUtil.getTitleWithExtension(fileEntry);

						JSONObject itemMedatadaJSONObject = ItemSelectorBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);
				%>

						<li class="list-group-item list-group-item-default">
							<div class="list-group-item-field">
								<img src="<%= DLUtil.getThumbnailSrc(fileEntry, themeDisplay) %>" />

								<c:if test="<%= fileShortcut != null %>">
									<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="shortcut" />" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png" />
								</c:if>
							</div>

							<div class="list-group-item-content">
								<div class="text-default">
									<liferay-ui:message key="modified" />
									<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - fileEntry.getModifiedDate().getTime(), true), HtmlUtil.escape(fileEntry.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
								</div>

								<div class="text-primary">
									<a class="item-preview" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(existingFileEntryReturnType)) %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorBrowserReturnTypeUtil.getValue(existingFileEntryReturnType, fileEntry, themeDisplay)) %>" href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" title="<%= HtmlUtil.escapeAttribute(title) %>">
										<%= HtmlUtil.escape(title) %>
									</a>
								</div>

								<div class="status text-default">
									<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(latestFileVersion.getStatus()) %>" />
								</div>
							</div>
						</li>

					<%
					}
					else {
					%>

						<li class="list-group-item list-group-item-default">

							<%
							String folderImage = "folder_empty_document";

							if (PropsValues.DL_FOLDER_ICON_CHECK_COUNT && (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(folder.getRepositoryId(), folder.getFolderId(), WorkflowConstants.STATUS_APPROVED, true) > 0)) {
								folderImage = "folder_full_document";
							}
							%>

							<div class="list-group-item-field">
								<img src="<%= themeDisplay.getPathThemeImages() + "/file_system/large/" + folderImage + ".png" %>" />
							</div>

							<div class="list-group-item-content">
								<div class="text-primary">

									<%
									PortletURL viewFolderURL = PortletURLUtil.clone(searchContainer.getIteratorURL(), liferayPortletResponse);

									viewFolderURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
									%>

									<a href="<%= HtmlUtil.escapeHREF(viewFolderURL.toString()) %>" title="<%= HtmlUtil.escapeAttribute(folder.getName()) %>">
										<%= HtmlUtil.escape(folder.getName()) %>
									</a>
								</div>
							</div>
						</li>

					<%
						}
					}
					%>

				</ul>

				<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
			</c:otherwise>
		</c:choose>

		<liferay-ui:drop-here-info message="drop-files-here" />
	</c:if>
</div>

<aui:script use="liferay-item-selector-browser">
	new Liferay.ItemSelectorBrowser(
		{
			closeCaption: '<%= UnicodeLanguageUtil.get(request, tabName) %>',
			on: {
				selectedItem: function(event) {
					Liferay.Util.getOpener().Liferay.fire('<%= itemSelectedEventName %>', event);
				}
			},
			rootNode: '#<%= randomNamespace %>ItemSelectorContainer'
		}
	);
</aui:script>