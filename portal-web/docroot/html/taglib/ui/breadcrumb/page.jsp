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

<%@ include file="/html/taglib/ui/breadcrumb/init.jsp" %>

<div id="<portlet:namespace />breadcrumbs-defaultScreen">
	<h1 class="hide-accessible"><liferay-ui:message key="breadcrumbs" /></h1>

	<c:if test="<%= !breadcrumbEntries.isEmpty() %>">
		<liferay-util:include page='<%= "/html/taglib/ui/breadcrumb/display_style_" + displayStyle + ".jsp" %>' />
	</c:if>
</div>