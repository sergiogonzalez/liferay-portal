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
KBArticle kbArticle = (KBArticle)request.getAttribute("article_icons.jsp-kb_article");

int status = (Integer)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_STATUS);

resourcePrimKey = ParamUtil.getLong(request, "resourcePrimKey");
%>

<c:if test="<%= (AdminPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADD_KB_ARTICLE) && rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) || (DisplayPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADD_KB_ARTICLE) && DisplayPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADMINISTRATOR) && rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) || ((!rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY) || DisplayPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADMINISTRATOR)) && KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE)) || (kbArticle.isRoot() && KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.PERMISSIONS)) || KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.MOVE_KB_ARTICLE) || KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.DELETE) %>">
	<div class="kb-article-icons text-right">
		<liferay-ui:icon-menu cssClass="right" direction="down" extended="<%= false %>" triggerCssClass="btn">
			<c:if test="<%= KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.DELETE) %>">
				<liferay-portlet:renderURL var="homeURL">
					<portlet:param name="mvcPath" value='<%= templatePath + "view.jsp" %>' />
				</liferay-portlet:renderURL>

				<liferay-portlet:actionURL name="deleteKBArticle" var="deleteURL">
					<portlet:param name="mvcPath" value='<%= templatePath + "view_article.jsp" %>' />
					<portlet:param name="redirect" value="<%= (kbArticle.getResourcePrimKey() == resourcePrimKey) ? homeURL : currentURL %>" />
					<portlet:param name="resourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
					<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
					<portlet:param name="status" value="<%= String.valueOf(status) %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon-delete
					label="<%= true %>"
					url="<%= deleteURL %>"
				/>
			</c:if>
		</liferay-ui:icon-menu>
	</div>
</c:if>