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

<%@ include file="/admin/common/init.jsp" %>

<%
boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);

	PortletURL portletURL = PortalUtil.getControlPanelPortletURL(request, KBPortletKeys.KNOWLEDGE_BASE_ADMIN, PortletRequest.RENDER_PHASE);

	portletDisplay.setURLBack(portletURL.toString());

	renderResponse.setTitle(LanguageUtil.get(resourceBundle,"error"));
}
%>

<c:if test="<%= !rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE) && !portletTitleBasedNavigation %>">
	<liferay-ui:error-header />
</c:if>

<c:choose>
	<c:when test="<%= rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE) %>">
		<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-selected-article-no-longer-exists" />
	</c:when>
	<c:otherwise>
		<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-article-could-not-be-found" />
	</c:otherwise>
</c:choose>

<liferay-ui:error exception="<%= NoSuchCommentException.class %>" message="the-comment-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchSubscriptionException.class %>" message="the-subscription-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchTemplateException.class %>" message="the-template-could-not-be-found" />

<liferay-ui:error-principal />