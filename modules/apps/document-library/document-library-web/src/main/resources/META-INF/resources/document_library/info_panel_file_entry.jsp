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
FileEntry fileEntry = (FileEntry)request.getAttribute("info_panel.jsp-fileEntry");
FileVersion fileVersion = (FileVersion)request.getAttribute("info_panel.jsp-fileVersion");
%>

<div class="sidebar-header">
	<ul class="sidebar-header-actions">
		<li>
			<liferay-util:include page="/document_library/file_entry_action.jsp" servletContext="<%= application %>" />
		</li>
	</ul>

	<h4><%= HtmlUtil.escape(fileEntry.getTitle()) %></h4>
</div>

<%
String thumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay);

DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileVersion);

String tabsNames = "details";

if (dlViewFileVersionDisplayContext.isVersionInfoVisible()) {
	tabsNames += ",versions";
}

long assetClassPK = 0;

if (!fileVersion.isApproved() && !fileVersion.getVersion().equals(DLFileEntryConstants.VERSION_DEFAULT) && !fileEntry.isInTrash()) {
	assetClassPK = fileVersion.getFileVersionId();
}
else {
	assetClassPK = fileEntry.getFileEntryId();
}
%>

<liferay-ui:tabs
	cssClass="navbar-no-collapse"
	names="<%= tabsNames %>"
	refresh="<%= false %>"
	type="dropdown"
>
	<liferay-ui:section>
		<div class="sidebar-body">
			<div style="margin-bottom:1.5rem;">
				<c:if test="<%= dlViewFileVersionDisplayContext.isVersionInfoVisible() %>">
					<clay:label
						label='<%= LanguageUtil.get(request, "version") + StringPool.SPACE + fileVersion.getVersion() %>'
						style="info"
					/>
				</c:if>

				<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

				<aui:workflow-status model="<%= DLFileEntry.class %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fileVersion.getStatus() %>" />
			</div>

			<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
				<div class="aspect-ratio aspect-ratio-16-to-9 sidebar-panel">
					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="thumbnail" />" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= DLUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay) %>" />

					<div class="sticker sticker-bottom-left sticker-danger"><%= HtmlUtil.escape(StringUtil.toUpperCase(fileEntry.getExtension())) %></div>
				</div>
			</c:if>

			<div class="autofit-row widget-metadata">
				<div class="autofit-col inline-item-before">

					<%
					User owner = UserLocalServiceUtil.fetchUser(fileEntry.getUserId());

					String ownerURL = StringPool.BLANK;

					if ((owner != null) && !owner.isDefaultUser()) {
						ownerURL = owner.getDisplayURL(themeDisplay);
					}
					%>

					<liferay-ui:user-portrait
						user="<%= owner %>"
					/>
				</div>

				<div class="autofit-col autofit-col-expand">
					<div class="autofit-row">
						<div class="autofit-col autofit-col-expand">
							<a class="username" href="<%= ownerURL %>"><%= owner.getFullName() %></a>

							<div>
								<small>
									<liferay-ui:message key="owner" />
								</small>
							</div>
						</div>
					</div>
				</div>
			</div>

			<c:if test="<%= dlViewFileVersionDisplayContext.isDownloadLinkVisible() %>">
				<div class="sidebar-block">
					<aui:button-row>
						<clay:link
							buttonStyle="primary"
							elementClasses='<%= "btn-sm" %>'
							href="<%= DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>"
							label='<%= LanguageUtil.get(resourceBundle, "download") %>'
							title='<%= LanguageUtil.get(resourceBundle, "download") %>'
						/>

						<span class="conversions">

							<%
							String[] conversions = new String[0];

							if (PropsValues.DL_FILE_ENTRY_CONVERSIONS_ENABLED && DocumentConversionUtil.isEnabled()) {
								conversions = DocumentConversionUtil.getConversions(fileVersion.getExtension());
							}
							%>

							<%
							for (int i = 0; i < conversions.length; i++) {
								String conversion = conversions[i];
							%>

								<liferay-ui:icon
									iconCssClass="<%= DLUtil.getFileIconCssClass(conversion) %>"
									label="<%= true %>"
									message="<%= StringUtil.toUpperCase(conversion) %>"
									method="get"
									url='<%= DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, "&targetExtension=" + conversion) %>'
								/>

							<%
							}
							%>

						</span>
					</aui:button-row>

					<div class="sidebar-block">
						<aui:input name="url" type="resource" value="<%= DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, true) %>" />

						<c:if test="<%= portletDisplay.isWebDAVEnabled() && fileEntry.isSupportsSocial() %>">

							<%
							String webDavHelpMessage = null;

							if (BrowserSnifferUtil.isWindows(request)) {
								webDavHelpMessage = LanguageUtil.format(request, "webdav-windows-help", new Object[] {"https://support.microsoft.com/en-us/kb/892211", "https://dev.liferay.com/discover/portal/-/knowledge_base/7-0/publishing-files#desktop-access-to-documents-and-media"}, false);
							}
							else {
								webDavHelpMessage = LanguageUtil.format(request, "webdav-help", "https://dev.liferay.com/discover/portal/-/knowledge_base/7-0/publishing-files#desktop-access-to-documents-and-media", false);
							}
							%>

							<aui:input helpMessage="<%= webDavHelpMessage %>" name="webDavURL" type="resource" value="<%= DLUtil.getWebDavURL(themeDisplay, fileEntry.getFolder(), fileEntry) %>" />
						</c:if>
					</div>
				</div>
			</c:if>

			<dl class="sidebar-dl sidebar-section">
				<c:if test="<%= fileVersion.getModel() instanceof DLFileVersion %>">

					<%
					DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

					DLFileEntryType dlFileEntryType = dlFileVersion.getDLFileEntryType();
					%>

					<dt class="sidebar-dt">
						<liferay-ui:message key="document-type" />
					</dt>
					<dd class="sidebar-dd">
						<%= HtmlUtil.escape(dlFileEntryType.getName(locale)) %>
					</dd>
				</c:if>

				<c:if test="<%= Validator.isNotNull(fileVersion.getExtension()) %>">
					<dt class="sidebar-dt">
						<liferay-ui:message key="extension" />
					</dt>
					<dd class="sidebar-dd">
						<%= HtmlUtil.escape(fileVersion.getExtension()) %>
					</dd>
				</c:if>

				<dt class="sidebar-dt">
					<liferay-ui:message key="size" />
				</dt>
				<dd class="sidebar-dd">
					<%= HtmlUtil.escape(TextFormatter.formatStorageSize(fileEntry.getSize(), locale)) %>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="modified" />
				</dt>
				<dd class="sidebar-dd">
					<liferay-ui:message arguments="<%= new Object[] {dateFormatDateTime.format(fileVersion.getModifiedDate()), HtmlUtil.escape(fileVersion.getStatusByUserName())} %>" key="x-by-x" translateArguments="<%= false %>" />
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="created" />
				</dt>
				<dd class="sidebar-dd">
					<liferay-ui:message arguments="<%= new Object[] {dateFormatDateTime.format(fileVersion.getCreateDate()), HtmlUtil.escape(fileVersion.getUserName())} %>" key="x-by-x" translateArguments="<%= false %>" />
				</dd>

				<liferay-asset:asset-tags-available
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= assetClassPK %>"
				>
					<dt class="sidebar-dt">
						<liferay-ui:message key="tags" />
					</dt>
					<dd class="sidebar-dd">
						<liferay-asset:asset-tags-summary
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= assetClassPK %>"
						/>
					</dd>
				</liferay-asset:asset-tags-available>

				<dt class="sidebar-dt">
					<liferay-ui:message key="ratings" />
				</dt>
				<dd class="sidebar-dd">
					<liferay-ui:ratings
						className="<%= DLFileEntryConstants.getClassName() %>"
						classPK="<%= fileEntry.getFileEntryId() %>"
						inTrash="<%= fileEntry.isInTrash() %>"
					/>
				</dd>

				<c:if test="<%= Validator.isNotNull(fileEntry.getDescription()) %>">
					<dt class="sidebar-dt">
						<liferay-ui:message key="description" />
					</dt>
					<dd class="sidebar-dd">
						<%= HtmlUtil.escape(fileEntry.getDescription()) %>
					</dd>
				</c:if>

				<liferay-asset:asset-categories-available
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= assetClassPK %>"
				>
					<dt class="sidebar-dt">
						<liferay-ui:message key="categories" />
					</dt>
					<dd class="sidebar-dd">
						<liferay-asset:asset-categories-summary
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= assetClassPK %>"
							displayStyle="simple-category"
						/>
					</dd>
				</liferay-asset:asset-categories-available>
			</dl>

			<%
			AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(DLFileEntryConstants.getClassName(), assetClassPK);
			%>

			<c:if test="<%= (layoutAssetEntry != null) && dlPortletInstanceSettings.isEnableRelatedAssets() && fileEntry.isSupportsSocial() %>">
				<liferay-asset:asset-links
					assetEntryId="<%= layoutAssetEntry.getEntryId() %>"
				/>
			</c:if>

			<liferay-ui:panel-container
				cssClass="metadata-panel-container"
				extended="<%= true %>"
				markupView="lexicon"
				persistState="<%= true %>"
			>
				<c:if test="<%= dlViewFileVersionDisplayContext.getDDMStructuresCount() > 0 %>">

					<%
					try {
						List<DDMStructure> ddmStructures = dlViewFileVersionDisplayContext.getDDMStructures();

						for (DDMStructure ddmStructure : ddmStructures) {
							DDMFormValues ddmFormValues = null;

							List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<DDMFormFieldValue>();

							try {
								ddmFormValues = dlViewFileVersionDisplayContext.getDDMFormValues(ddmStructure);

								ddmFormFieldValues = ddmFormValues.getDDMFormFieldValues();
							}
							catch (Exception e) {
							}
					%>

							<c:if test="<%= !ddmFormFieldValues.isEmpty() %>">
								<liferay-ui:panel
									collapsible="<%= true %>"
									cssClass="metadata"
									extended="<%= true %>"
									id='<%= "documentLibraryMetadataPanel" + StringPool.UNDERLINE + ddmStructure.getStructureId() %>'
									markupView="lexicon"
									persistState="<%= true %>"
									title="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
								>
									<liferay-ddm:html
										classNameId="<%= PortalUtil.getClassNameId(com.liferay.dynamic.data.mapping.model.DDMStructure.class) %>"
										classPK="<%= ddmStructure.getPrimaryKey() %>"
										ddmFormValues="<%= ddmFormValues %>"
										fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
										readOnly="<%= true %>"
										requestedLocale="<%= locale %>"
										showEmptyFieldLabel="<%= false %>"
									/>
								</liferay-ui:panel>
							</c:if>

					<%
						}
					}
					catch (Exception e) {
					}
					%>

				</c:if>

				<liferay-expando:custom-attributes-available
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= fileVersion.getFileVersionId() %>"
					editable="<%= false %>"
				>
					<liferay-ui:panel
						collapsible="<%= true %>"
						cssClass="lfr-custom-fields"
						id="documentLibraryCustomFieldsPanel"
						markupView="lexicon"
						persistState="<%= true %>"
						title="custom-fields"
					>
						<liferay-expando:custom-attribute-list
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileVersion.getFileVersionId() %>"
							editable="<%= false %>"
							label="<%= true %>"
						/>
					</liferay-ui:panel>
				</liferay-expando:custom-attributes-available>

				<%
				try {
					List<DDMStructure> ddmStructures = DDMStructureManagerUtil.getClassStructures(company.getCompanyId(), PortalUtil.getClassNameId(RawMetadataProcessor.class), DDMStructureManager.STRUCTURE_COMPARATOR_STRUCTURE_KEY);

					for (DDMStructure ddmStructure : ddmStructures) {
						DDMFormValues ddmFormValues = null;

						try {
							DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(ddmStructure.getStructureId(), fileVersion.getFileVersionId());

							ddmFormValues = dlViewFileVersionDisplayContext.getDDMFormValues(fileEntryMetadata.getDDMStorageId());
						}
						catch (Exception e) {
						}

						if (ddmFormValues != null) {
							String name = "metadata." + ddmStructure.getStructureKey();
				%>

							<liferay-ui:panel
								collapsible="<%= true %>"
								cssClass="lfr-asset-metadata"
								id='<%= "documentLibraryMetadataPanel" + StringPool.UNDERLINE + ddmStructure.getStructureId() %>'
								markupView="lexicon"
								persistState="<%= true %>"
								title="<%= name %>"
							>
								<liferay-ddm:html
									classNameId="<%= PortalUtil.getClassNameId(com.liferay.dynamic.data.mapping.model.DDMStructure.class) %>"
									classPK="<%= ddmStructure.getPrimaryKey() %>"
									ddmFormValues="<%= ddmFormValues %>"
									fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
									readOnly="<%= true %>"
									requestedLocale="<%= ddmFormValues.getDefaultLocale() %>"
									showEmptyFieldLabel="<%= false %>"
								/>
							</liferay-ui:panel>

				<%
						}
					}
				}
				catch (Exception e) {
				}
				%>

			</liferay-ui:panel-container>
		</div>
	</liferay-ui:section>

	<c:if test="<%= dlViewFileVersionDisplayContext.isVersionInfoVisible() %>">
		<liferay-ui:section>
			<div class="sidebar-body">

				<%
				request.setAttribute("info_panel.jsp-fileEntry", fileEntry);
				%>

				<liferay-util:include page="/document_library/file_entry_history.jsp" servletContext="<%= application %>" />
			</div>
		</liferay-ui:section>
	</c:if>
</liferay-ui:tabs>