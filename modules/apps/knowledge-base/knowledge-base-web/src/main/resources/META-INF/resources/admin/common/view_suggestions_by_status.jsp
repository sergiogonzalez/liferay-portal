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
KBSuggestionListDisplayContext kbSuggestionListDisplayContext = (KBSuggestionListDisplayContext)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_SUGGESTION_LIST_DISPLAY_CONTEXT);

String navItem = kbSuggestionListDisplayContext.getSelectedNavItem();
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">

		<%
		int newKBCommentsCount = kbSuggestionListDisplayContext.getNewKBCommentsCount();

		String newKBCommentsLabel = String.format("%s (%s)", LanguageUtil.get(request, "new"), newKBCommentsCount);
		%>

		<aui:nav-item
			href='<%= kbSuggestionListDisplayContext.getViewSuggestionURL(renderResponse, "viewNewSuggestions") %>'
			label="<%= newKBCommentsLabel %>"
			selected='<%= navItem.equals("viewNewSuggestions") %>'
		/>

		<%
		int inProgressKBCommentsCount = kbSuggestionListDisplayContext.getInProgressKBCommentsCount();

		String inProgressKBCommentsLabel = String.format("%s (%s)", LanguageUtil.get(request, "in-progress"), inProgressKBCommentsCount);
		%>

		<aui:nav-item
			href='<%= kbSuggestionListDisplayContext.getViewSuggestionURL(renderResponse, "viewInProgressSuggestions") %>'
			label="<%= inProgressKBCommentsLabel %>"
			selected='<%= navItem.equals("viewInProgressSuggestions") %>'
		/>

		<%
		int completedKBCommentsCount = kbSuggestionListDisplayContext.getCompletedKBCommentsCount();

		String completedLabel = String.format("%s (%s)", LanguageUtil.get(request, "resolved"), completedKBCommentsCount);
		%>

		<aui:nav-item
			href='<%= kbSuggestionListDisplayContext.getViewSuggestionURL(renderResponse, "viewCompletedSuggestions") %>'
			label="<%= completedLabel %>"
			selected='<%= navItem.equals("viewCompletedSuggestions") %>'
		/>
	</aui:nav>
</aui:nav-bar>

<liferay-portlet:renderURL varImpl="iteratorURL" />

<%
kbSuggestionListDisplayContext.getViewSuggestionURL(iteratorURL, navItem);
%>

<div id="<portlet:namespace />kbArticleCommentsWrapper">
	<liferay-ui:search-container
		emptyResultsMessage="no-suggestion-was-found"
		iteratorURL="<%= iteratorURL %>"
		total="<%= kbSuggestionListDisplayContext.getKBCommentsCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= kbSuggestionListDisplayContext.getKBComments(searchContainer) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.knowledge.base.model.KBComment"
			modelVar="kbComment"
		>

			<%
			request.setAttribute("article_comment.jsp-kb_comment", kbComment);

			KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(kbComment.getClassPK(), WorkflowConstants.STATUS_ANY);

			request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE, kbArticle);
			%>

			<liferay-util:include page="/admin/common/article_comment.jsp" servletContext="<%= application %>" />
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" resultRowSplitter="<%= new KBCommentResultRowSplitter() %>" />
	</liferay-ui:search-container>
</div>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />kbArticleCommentsWrapper').delegate(
		'click',
		function(e) {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				location.href = this.getData('href');
			}
		},
		'.kb-suggestion-actions .kb-suggestion-delete'
	);
</aui:script>