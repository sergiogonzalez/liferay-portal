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

<%@ include file="/taglib/ui/init.jsp" %>

<%
String displayStyle = GetterUtil.getString(request.getAttribute("item-selector:view-entries:displayStyle"), "icon");
String idPrefix = GetterUtil.getString(request.getAttribute("item-selector:view-entries:idPrefix"));
SearchContainer itemSearchContainer = (SearchContainer)request.getAttribute("item-selector:view-entries:itemSearchContainer");
String tabName = GetterUtil.getString(request.getAttribute("item-selector:view-entries:tabName"));
%>

<liferay-util:html-top outputKey="item-selector-browser-taglib">
	<link href="/o/item-selector-browser/css/browser.css" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="item-selector-browser-container style-<%= displayStyle %>" id="<%= idPrefix %>ItemSelectorContainer">

	<%
		String dropContainerClass = displayStyle.equals("icon") ? "drop-zone preview-content" : "drop-zone";
		Integer dropContainerWidth = displayStyle.equals("icon") ? 25 : 100;
	%>
	<aui:col cssClass="<%= dropContainerClass %>" width="<%= dropContainerWidth %>">
		<a class="browse-image btn btn-primary" href="javascript:;" id="<%= idPrefix %>SelectFile"><liferay-ui:message key="select-file" />
		</a>

		<input type="file" style="visibility: hidden; width: 0; height: 0" id="<%= idPrefix %>InputFile"/>

		<a id="<%= idPrefix %>Image" href=""></a>

		<div class="hide image-info">
			<dl>
				<dt><liferay-ui:message key="format" /></dt>
				<dd id="imageExtension"></dd>

				<dt><liferay-ui:message key="size" /></dt>
				<dd id="imageSize"></dd>

				<dt><liferay-ui:message key="name" /></dt>
				<dd id="imageName"></dd>
			</dl>
		</div>

		<p>Upload an image by dropping it right here or by pressing this plus icon</p>
	</aui:col>

	<div class="drop-here-info">
		<div class="drop-here-indicator">
			<div class="drop-icons">
				<span aria-hidden="true" class="glyphicon glyphicon-picture"></span>
				<span aria-hidden="true" class="glyphicon glyphicon-picture"></span>
				<span aria-hidden="true" class="glyphicon glyphicon-picture"></span>
			</div>

			<div class="drop-text">
				<liferay-ui:message key="drop-files-here" />
			</div>
		</div>
	</div>

	<c:choose>
		<c:when test='<%= !displayStyle.equals("list") %>'>

			<%
			for (Object result : itemSearchContainer.getResults()) {
				FileEntry fileEntry = (FileEntry)result;
				FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

				String imagePreviewURL = DLUtil.getImagePreviewURL(fileEntry, themeDisplay);
				String imageURL = DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK);
				String imageTitle = DLUtil.getTitleWithExtension(fileEntry);
			%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("icon") %>'>
						<%@ include file="/META-INF/resources/taglib/ui/icon_view.jspf" %>
					</c:when>
					<c:otherwise>
						<%@ include file="/META-INF/resources/taglib/ui/descriptive_view.jspf" %>
					</c:otherwise>
				</c:choose>

			<%
			}
			%>

			<liferay-ui:search-paginator searchContainer="<%= itemSearchContainer %>" />

		</c:when>
		<c:otherwise>
			<%@ include file="/META-INF/resources/taglib/ui/list_view.jspf" %>
		</c:otherwise>
	</c:choose>
</div>

<div class="lfr-item-viewer" id="<%= idPrefix %>ItemViewerPreview"></div>
<div class="lfr-item-viewer" id="<%= idPrefix %>UploadImagePreview"></div>

<aui:script use="aui-component,liferay-item-viewer,liferay-item-browser">
	var viewer = new A.LiferayItemViewer(
		{
			btnCloseCaption:'<%= tabName %>',
			links: '#<%= idPrefix %>ItemSelectorContainer a.item-preview',
		}
	).render('#<%= idPrefix %>ItemViewerPreview');

	var itemBrowser = new Liferay.ItemBrowser({
		browseImageId: 'SelectFile',
		inputFileId: 'InputFile',
		itemViewerCloseCaption: '<%= tabName %>',
		itemViewerContainer: 'UploadImagePreview',
		linkId: 'Image',
		namespace: '<%= idPrefix %>',
		rootNode: '#<%= idPrefix %>ItemSelectorContainer'
	});

</aui:script>