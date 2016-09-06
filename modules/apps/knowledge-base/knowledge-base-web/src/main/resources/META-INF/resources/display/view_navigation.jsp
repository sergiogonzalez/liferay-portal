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

<%@ include file="/display/init.jsp" %>

<%
KBNavigationDisplayContext kbNavigationDisplayContext = (KBNavigationDisplayContext)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_NAVIGATION_DISPLAY_CONTEXT);

List<Long> ancestorResourcePrimaryKeys = kbNavigationDisplayContext.getAncestorResourcePrimaryKeys();

long rootResourcePrimKey = kbNavigationDisplayContext.getRootResourcePrimKey();

String pageTitle = kbNavigationDisplayContext.getPageTitle();

if (Validator.isNotNull(pageTitle)) {
	PortalUtil.setPageTitle(pageTitle, request);
}

KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse, templatePath);
%>

<div class="kbarticle-navigation">
	<c:if test="<%= resourceClassNameId == kbFolderClassNameId %>">
		<liferay-util:include page="/display/content_root_selector.jsp" servletContext="<%= application %>" />
	</c:if>

	<%
	request.setAttribute("ancestorResourcePrimaryKeys", ancestorResourcePrimaryKeys);
	request.setAttribute("kbArticleURLHelper", kbArticleURLHelper);
	request.setAttribute("level", 0);
	request.setAttribute("parentResourcePrimKey", rootResourcePrimKey);
	%>

	<liferay-util:include page="/display/view_child_articles.jsp" servletContext="<%= application %>" />
</div>