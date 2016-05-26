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

<%@ include file="/admin/init.jsp" %>

<%
long parentResourceClassNameId = ParamUtil.getLong(request, "parentResourceClassNameId", kbFolderClassNameId);
long parentResourcePrimKey = ParamUtil.getLong(request, "parentResourcePrimKey", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

boolean hasAddKBArticlePermission = false;
boolean hasAddKBFolderPermission = false;

if (parentResourceClassNameId == kbFolderClassNameId) {
	hasAddKBArticlePermission = KBFolderPermission.contains(permissionChecker, scopeGroupId, parentResourcePrimKey, KBActionKeys.ADD_KB_ARTICLE);
	hasAddKBFolderPermission = KBFolderPermission.contains(permissionChecker, scopeGroupId, parentResourcePrimKey, KBActionKeys.ADD_KB_FOLDER);
}
else {
	hasAddKBArticlePermission = AdminPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADD_KB_ARTICLE);
}
%>

<liferay-frontend:add-menu>
	<c:if test="<%= hasAddKBArticlePermission %>">
		<liferay-util:include page="/admin/common/add_article_button.jsp" servletContext="<%= application %>" />
	</c:if>

	<c:if test="<%= hasAddKBFolderPermission %>">
		<liferay-util:include page="/admin/common/add_folder_button.jsp" servletContext="<%= application %>" />
	</c:if>

	<c:if test="<%= (parentResourceClassNameId == kbFolderClassNameId) && AdminPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADD_KB_ARTICLE) %>">
		<liferay-util:include page="/admin/import_articles_button.jsp" servletContext="<%= application %>" />
	</c:if>
</liferay-frontend:add-menu>