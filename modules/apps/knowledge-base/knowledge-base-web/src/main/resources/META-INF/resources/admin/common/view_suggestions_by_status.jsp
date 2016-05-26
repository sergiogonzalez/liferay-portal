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

String mvcPath = ParamUtil.getString(request, "mvcPath");

SearchContainer kbCommentsSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, "no-suggestions-were-found");

String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
String orderByType = ParamUtil.getString(renderRequest, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "pages-order-by-col", orderByCol);
	portalPreferences.setValue(KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "pages-order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "pages-order-by-col", "modifiedDate");
	orderByType = portalPreferences.getValue(KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "pages-order-by-type", "desc");
}

request.setAttribute("view_suggestions_by_status.jsp-orderByCol", orderByCol);
request.setAttribute("view_suggestions_by_status.jsp-orderByType", orderByType);

kbCommentsSearchContainer.setOrderByCol(orderByCol);
kbCommentsSearchContainer.setOrderByType(orderByType);

if (mvcPath.equals("/admin/view_suggestions.jsp")) {
	kbCommentsSearchContainer.setRowChecker(new KBCommentsChecker(liferayPortletRequest, liferayPortletResponse));
}

List<KBComment> kbComments = kbSuggestionListDisplayContext.getKBComments(kbCommentsSearchContainer);
%>

<c:if test='<%= mvcPath.equals("/admin/view_suggestions.jsp") %>'>
	<liferay-frontend:management-bar
		disabled="<%= kbComments.isEmpty() %>"
		includeCheckBox="<%= true %>"
		searchContainerId="kbComments"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"descriptive"} %>'
				portletURL="<%= currentURLObj %>"
				selectedDisplayStyle="descriptive"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-util:include page="/admin/common/suggestions_filters.jsp" servletContext="<%= application %>" />
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteKBComments" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<%
kbSuggestionListDisplayContext.getViewSuggestionURL(currentURLObj);
%>

<liferay-portlet:actionURL name="deleteKBComments" varImpl="deleteKBComments">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:actionURL>

<aui:form action="<%= deleteKBComments %>" name="fm">
	<liferay-ui:search-container
		id="kbComments"
		searchContainer="<%= kbCommentsSearchContainer %>"
		total="<%= kbSuggestionListDisplayContext.getKBCommentsCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= kbComments %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.knowledge.base.model.KBComment"
			keyProperty="kbCommentId"
			modelVar="kbComment"
		>
			<liferay-ui:search-container-column-user
				cssClass="user-icon-lg"
				showDetails="<%= false %>"
				userId="<%= kbComment.getUserId() %>"
			/>

			<liferay-ui:search-container-column-text colspan="<%= 2 %>">

				<%
				Date modifiedDate = kbComment.getModifiedDate();

				String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
				%>

				<h5 class="text-default">
					<liferay-ui:message arguments="<%= new String[] {kbComment.getUserName(), modifiedDateDescription} %>" key="x-suggested-x-ago" />
				</h5>

				<h4>
					<%= StringUtil.shorten(HtmlUtil.replaceNewLine(HtmlUtil.escape(kbComment.getContent())), 100) %>
				</h4>

				<h5 class="text-default">

					<%
					KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(kbComment.getClassPK(), WorkflowConstants.STATUS_ANY);

					request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE, kbArticle);

					KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse, templatePath);

					PortletURL viewKBArticleURL = kbArticleURLHelper.createViewWithRedirectURL(kbArticle, currentURL);
					%>

					<c:if test="<%= kbSuggestionListDisplayContext.isShowKBArticleTitle() %>">
						<a href="<%= viewKBArticleURL.toString() %>"><%= HtmlUtil.escape(kbArticle.getTitle()) %></a>
					</c:if>
				</h5>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				path="/admin/common/suggestion_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" resultRowSplitter="<%= new KBCommentResultRowSplitter(kbSuggestionListDisplayContext, resourceBundle) %>" />
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteKBComments').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>