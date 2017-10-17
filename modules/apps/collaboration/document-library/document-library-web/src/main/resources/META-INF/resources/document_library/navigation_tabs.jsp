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
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName", "/document_library/view");

PortletURL viewEntriesURL = renderResponse.createRenderURL();
viewEntriesURL.setParameter("mvcRenderCommandName", "/document_library/view");
viewEntriesURL.setParameter("redirect", currentURL);

PortletURL viewFileEntryTypesURL = renderResponse.createRenderURL();
viewFileEntryTypesURL.setParameter("mvcRenderCommandName", "/document_library/view_file_entry_types");
viewFileEntryTypesURL.setParameter("redirect", currentURL);
%>

<aui:nav cssClass="navbar-nav">
	<aui:nav-item
		href="<%= viewEntriesURL.toString() %>"
		label="documents-and-media"
		selected='<%= mvcRenderCommandName.equals("/document_library/view") %>'
	/>

	<aui:nav-item
		href="<%= viewFileEntryTypesURL.toString() %>"
		label="document-types"
		selected='<%= mvcRenderCommandName.equals("/document_library/view_file_entry_types") %>'
	/>
</aui:nav>