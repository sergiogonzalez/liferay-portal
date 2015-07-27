<%@ page
		import="com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion" %>
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

<%@ include file="/init.jsp" %>

<%
	String randomNamespace = PortalUtil.generateRandomKey(request, "item_selector_upload_page") + StringPool.UNDERLINE;

	ItemSelectorUploadViewDisplayContext itemSelectorUploadViewDisplayContext = (ItemSelectorUploadViewDisplayContext)request.getAttribute(ItemSelectorUploadView.ITEM_SELECTOR_UPLOAD_VIEW_DISPLAY_CONTEXT);

	String uploadURL = itemSelectorUploadViewDisplayContext.getUploadURL();

	String repositoryName = itemSelectorUploadViewDisplayContext.getUploadRepositoryName();

	ItemSelectorReturnType returnType = itemSelectorUploadViewDisplayContext.getUploadItemReturnType();
%>

<div class="lfr-item-viewer" id="<%= randomNamespace %>ItemSelectorUploadContainer">
	<div class="drop-enabled drop-zone upload-view" data-returntype="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(returnType)) %>" data-uploadurl="<%= uploadURL %>">
		<div id="<%= randomNamespace %>UploadDescription">
			<p>
				<strong><liferay-ui:message arguments="<%= repositoryName %>" key="drag-and-drop-to-upload-to-x-or" /></strong>
			</p>

			<p>
				<label class="btn btn-default" for="<%= randomNamespace %>InputFile"><liferay-ui:message key="select-file" /></label>

				<input class="hide" id="<%= randomNamespace %>InputFile" type="file" />
			</p>
		</div>
	</div>

	<liferay-ui:drop-here-info message="drop-files-here" />
</div>

<aui:script use="liferay-item-selector-browser">
	new Liferay.ItemSelectorBrowser(
		{
			closeCaption: '<%= itemSelectorUploadViewDisplayContext.getTitle(locale) %>',
			on: {
				selectedItem: function(event) {
					Liferay.Util.getOpener().Liferay.fire('<%= itemSelectorUploadViewDisplayContext.getItemSelectedEventName() %>', event);
				}
			},
			rootNode: '#<%= randomNamespace %>ItemSelectorUploadContainer'
		}
	);
</aui:script>