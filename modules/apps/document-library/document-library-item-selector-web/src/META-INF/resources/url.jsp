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
DLItemSelectorViewDisplayContext dlItemSelectorViewDisplayContext = (DLItemSelectorViewDisplayContext)request.getAttribute(DLItemSelectorView.DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

String itemSelectedEventName = dlItemSelectorViewDisplayContext.getItemSelectedEventName();

String tabName = dlItemSelectorViewDisplayContext.getTitle(locale);

ResourceBundle resourceBundle = ResourceBundle.getBundle("content/Language", locale);
%>

<aui:row cssClass="lfr-item-viewer" id="itemSelectorUrlContainer">
	<aui:col width="60" cssClass="col-md-offset-2">
		<h4><%= LanguageUtil.get(resourceBundle, "enter-url") %></h4>
		<p><%= LanguageUtil.get(resourceBundle, "enter-url-that-contains-the-image-you-want-to-add") %></p>
		<div class="col-md-12">
			<aui:input wrapperCssClass="col-md-10" label="" name="urlInput" placeholder="http://" />
			<aui:button cssClass="btn-primary" name="previewBtn" value="<%= LanguageUtil.get(resourceBundle, "enter") %>"/>
		</div>
		<em><%= LanguageUtil.format(resourceBundle, "for-example-x", "http://www.liferay.com/liferay.png", false) %></em>
	</aui:col>
</aui:row>

<aui:script use="liferay-item-selector-url">
	new Liferay.ItemSelectorUrl(
		{
			closeCaption: '<%= tabName %>',
			namespace: '<portlet:namespace/>',
			on: {
				selectedItem: function(event) {
					Liferay.Util.getOpener().Liferay.fire('<%= itemSelectedEventName %>', event);
				}
			},
			rootNode: '#itemSelectorUrlContainer'
		}
	);
</aui:script>