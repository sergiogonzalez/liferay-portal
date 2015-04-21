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

<aui:script use="liferay-item-viewer">
	var viewer = new A.LiferayItemViewer(
		{
			btnCloseCaption:'<%= tabName %>',
			links: '#<%= idPrefix %>ItemSelectorContainer a.item-preview',
		}
	).render('#<%= idPrefix %>ItemViewerPreview');
</aui:script>