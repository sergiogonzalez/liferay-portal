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
String tabs1 = ParamUtil.getString(request, "tabs1");

PortletURL documentsAndMediaURL = renderResponse.createRenderURL();
documentsAndMediaURL.setParameter("tabs1", "documents_and_media");
documentsAndMediaURL.setParameter("redirect", currentURL);

PortletURL documentTypesURL = renderResponse.createRenderURL();
documentTypesURL.setParameter("tabs1", "document_types");
documentTypesURL.setParameter("redirect", currentURL);
%>

<aui:nav cssClass="navbar-nav">
	<aui:nav-item
		href="<%= documentsAndMediaURL.toString() %>"
		label="documents-and-media"
		selected='<%= Validator.isNull(tabs1) || tabs1.equals("documents_and_media") %>'
	/>

	<aui:nav-item
		href="<%= documentTypesURL.toString() %>"
		label="document-types"
		selected='<%= tabs1.equals("document_types") %>'
	/>
</aui:nav>