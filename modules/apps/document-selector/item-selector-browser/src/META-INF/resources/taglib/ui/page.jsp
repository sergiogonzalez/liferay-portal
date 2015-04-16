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

<div class="item-selector-browser-container style-<%= displayStyle %>" id="<%= idPrefix %>ItemSelectorContainer">

	<c:choose>
		<c:when test='<%= !displayStyle.equals("list") %>'>

			<%
			for (Object result : itemSearchContainer.getResults()) {
				FileEntry fileEntry = (FileEntry)result;

				String imageURL = DLUtil.getImagePreviewURL(fileEntry, themeDisplay);
				String imageTitle = DLUtil.getTitleWithExtension(fileEntry);

				FileVersion latestFileVersion = fileEntry.getLatestFileVersion();
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

<div class="lfr-image-viewer" id="<%= idPrefix %>ImageViewerPreview"></div>

<aui:script use="liferay-image-viewer">
	var viewer = new A.LiferayImageViewer(
		{
			btnCloseCaption:'<%= tabName %>',
			captionFromTitle: true,
			centered: true,
			circular: true,
			height: '75%',
			infoTemplate: '{current} of {total}',
			links: '#<%= idPrefix %>ItemSelectorContainer a.image-preview',
			playing: false,
			preloadAllImages: false,
			preloadNeighborImages: true,
			showPlayer: false,
			zIndex: 1
		}
	).render('#<%= idPrefix %>ImageViewerPreview');
</aui:script>