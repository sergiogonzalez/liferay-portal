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

<%@ include file="/blogs/init.jsp" %>

<%
long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");
%>

<portlet:actionURL name="/blogs/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

<%
int pageDelta = GetterUtil.getInteger(blogsPortletInstanceConfiguration.pageDelta());

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, pageDelta, currentURLObj, null, null);

searchContainer.setDelta(pageDelta);
searchContainer.setDeltaConfigurable(false);

int total = 0;
List results = null;

int[] statuses = new int[]{WorkflowConstants.STATUS_DRAFT, WorkflowConstants.STATUS_SCHEDULED};

int notPublishedEntriesCount = BlogsEntryServiceUtil.getGroupUserEntriesCount(scopeGroupId, themeDisplay.getUserId(), statuses);

if ((assetCategoryId != 0) || Validator.isNotNull(assetTagName)) {
	SearchContainerResults<AssetEntry> searchContainerResults = BlogsUtil.getSearchContainerResults(searchContainer);

	searchContainer.setTotal(searchContainerResults.getTotal());

	results = searchContainerResults.getResults();
}
else if (mvcRenderCommandName.equals("/blogs/view_not_published_entries")) {
	total = notPublishedEntriesCount;

	searchContainer.setTotal(total);

	results = BlogsEntryServiceUtil.getGroupUserEntries(scopeGroupId, themeDisplay.getUserId(), statuses, searchContainer.getStart(), searchContainer.getEnd(), new EntryModifiedDateComparator());

	searchContainer.setResults(results);
}
else {
	int status = WorkflowConstants.STATUS_APPROVED;

	total = BlogsEntryServiceUtil.getGroupEntriesCount(scopeGroupId, status);

	searchContainer.setTotal(total);

	results = BlogsEntryServiceUtil.getGroupEntries(scopeGroupId, status, searchContainer.getStart(), searchContainer.getEnd());
}

searchContainer.setResults(results);
%>

<c:if test="<%= notPublishedEntriesCount > 0 %>">
	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">
			<aui:nav-item
				href="<%= portletURL %>"
				label="published"
				selected='<%= !mvcRenderCommandName.equals("/blogs/view_not_published_entries") %>'
			/>

			<portlet:renderURL var="viewNotPublishedEntriesURL">
				<portlet:param name="mvcRenderCommandName" value="/blogs/view_not_published_entries" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= viewNotPublishedEntriesURL %>"
				label='<%= LanguageUtil.format(resourceBundle, "not-published-x", notPublishedEntriesCount, false) %>'
				selected='<%= mvcRenderCommandName.equals("/blogs/view_not_published_entries") %>'
			/>
		</aui:nav>
	</aui:nav-bar>
</c:if>

<c:choose>
	<c:when test="<%= total == 0 %>">
		<div class="alert alert-info">
			<liferay-ui:message key="there-are-no-entries" />
		</div>
	</c:when>
	<c:otherwise>
		<%@ include file="/blogs/view_entries.jspf" %>
	</c:otherwise>
</c:choose>