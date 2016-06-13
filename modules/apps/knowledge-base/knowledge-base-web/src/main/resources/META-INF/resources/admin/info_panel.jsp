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
long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long parentResourceClassNameId = ParamUtil.getLong(request, "parentResourceClassNameId", kbFolderClassNameId);
long parentResourcePrimKey = ParamUtil.getLong(request, "parentResourcePrimKey", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

List<KBFolder> kbFolders = (List<KBFolder>)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_FOLDERS);
List<KBArticle> kbArticles = (List<KBArticle>)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLES);

if (ListUtil.isEmpty(kbFolders) && ListUtil.isEmpty(kbArticles)) {
	if (parentResourceClassNameId == kbFolderClassNameId) {
		kbFolders = new ArrayList<KBFolder>();

		if (parentResourcePrimKey != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			kbFolders.add(KBFolderServiceUtil.getKBFolder(parentResourcePrimKey));
		}
		else {
			kbFolders.add(null);
		}
	}
	else {
		kbArticles = new ArrayList<KBArticle>();

		kbArticles.add(KBArticleServiceUtil.getLatestKBArticle(parentResourcePrimKey, WorkflowConstants.STATUS_ANY));
	}
}
%>

<c:choose>
	<c:when test="<%= (ListUtil.isEmpty(kbArticles) && ListUtil.isNotEmpty(kbFolders) && (kbFolders.size() == 1)) %>">

		<%
		KBFolder kbFolder = kbFolders.get(0);

		request.setAttribute("info_panel.jsp-kbFolder", kbFolder);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/admin/folder_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= (kbFolder != null) ? HtmlUtil.escape(kbFolder.getName()) : LanguageUtil.get(request, "home") %></h4>

			<div>
				<liferay-ui:message key="folder" />
			</div>
		</div>

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="num-of-items" /></h5>

			<%
			long folderId = KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			if (kbFolder != null) {
				folderId = kbFolder.getKbFolderId();
			}
			%>

			<p>
				<%= KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(scopeGroupId, folderId, WorkflowConstants.STATUS_APPROVED) %>
			</p>

			<c:if test="<%= kbFolder != null %>">
				<h5><liferay-ui:message key="created" /></h5>

				<p>
					<%= HtmlUtil.escape(kbFolder.getUserName()) %>
				</p>
			</c:if>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isEmpty(kbFolders) && ListUtil.isNotEmpty(kbArticles) && (kbArticles.size() == 1) %>">

		<%
		KBArticle kbArticle = kbArticles.get(0);

		request.setAttribute("info_panel.jsp-kbArticle", kbArticle);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/admin/subscribe.jsp" servletContext="<%= application %>" />
				</li>

				<li>
					<liferay-util:include page="/admin/article_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(kbArticle.getTitle()) %></h4>

			<div>
				<liferay-ui:message key="entry" />
			</div>
		</div>

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="created" /></h5>

			<p>
				<%= HtmlUtil.escape(kbArticle.getUserName()) %>
			</p>

			<c:if test="<%= Validator.isNotNull(kbArticle.getDescription()) %>">
				<h5><liferay-ui:message key="description" /></h5>

				<p>
					<%= HtmlUtil.escape(kbArticle.getDescription()) %>
				</p>
			</c:if>

			<div class="lfr-asset-categories">
				<liferay-ui:asset-categories-summary
					className="<%= KBArticle.class.getName() %>"
					classPK="<%= kbArticle.getClassPK() %>"
				/>
			</div>

			<div class="lfr-asset-tags">
				<liferay-ui:asset-tags-summary
					className="<%= KBArticle.class.getName() %>"
					classPK="<%= kbArticle.getClassPK() %>"
					message="tags"
				/>
			</div>

			<liferay-ui:ratings
				className="<%= KBArticle.class.getName() %>"
				classPK="<%= kbArticle.getClassPK() %>"
			/>

			<liferay-ui:custom-attributes-available className="<%= KBArticle.class.getName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= KBArticle.class.getName() %>"
					classPK="<%= kbArticle.getClassPK() %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= kbFolders.size() + kbArticles.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= kbFolders.size() + kbArticles.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:otherwise>
</c:choose>