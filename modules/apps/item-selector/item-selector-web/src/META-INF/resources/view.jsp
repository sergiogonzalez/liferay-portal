<%@ page import="java.util.List" %>

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
LocalizedItemSelectorRendering localizedItemSelectorRendering = LocalizedItemSelectorRendering.get(liferayPortletRequest);

ResourceBundle resourceBundle = ResourceBundle.getBundle("content/Language", locale);
%>

<liferay-ui:error exception="<%= NoSuchItemSelectorViewException.class %>" message='<%= LanguageUtil.get(resourceBundle, "selection-is-not-available") %>' />

<c:if test="<%= SessionErrors.contains(renderRequest, NoSuchItemSelectorViewException.class.getName()) %>">

	<%
	List<String> titles = localizedItemSelectorRendering.getTitles();
	%>

	<liferay-ui:tabs names="<%= StringUtil.merge(titles) %>" refresh="<%= false %>" type="pills" value="<%= localizedItemSelectorRendering.getSelectedTab() %>">

		<%
		for (String title : titles) {
			ItemSelectorViewRenderer itemSelectorViewRenderer = localizedItemSelectorRendering.getItemSelectorViewRenderer(title);
		%>

			<liferay-ui:section>
				<div>

					<%
					itemSelectorViewRenderer.renderHTML(pageContext);
					%>

				</div>
			</liferay-ui:section>

		<%
		}
		%>

	</liferay-ui:tabs>
</c:if>