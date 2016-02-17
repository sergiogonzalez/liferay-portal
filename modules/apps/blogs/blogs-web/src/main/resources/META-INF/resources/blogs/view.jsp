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

int pendingEntriesCount = BlogsEntryServiceUtil.getGroupUserEntriesCount(scopeGroupId, themeDisplay.getUserId(), BlogsPortletUtil.getPendingStatuses());

request.setAttribute("view.jsp-pendingEntriesCount", pendingEntriesCount);

if ((assetCategoryId != 0) || Validator.isNotNull(assetTagName)) {
	SearchContainerResults<AssetEntry> searchContainerResults = BlogsUtil.getSearchContainerResults(searchContainer);

	searchContainer.setTotal(searchContainerResults.getTotal());

	results = searchContainerResults.getResults();
}
else if (mvcRenderCommandName.equals("/blogs/view_my_pending_entries")) {
	total = pendingEntriesCount;

	searchContainer.setTotal(total);

	results = BlogsEntryServiceUtil.getGroupUserEntries(scopeGroupId, themeDisplay.getUserId(), BlogsPortletUtil.getPendingStatuses(), searchContainer.getStart(), searchContainer.getEnd(), new EntryModifiedDateComparator());

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

<c:if test="<%= pendingEntriesCount > 0 %>">
	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">
			<portlet:renderURL var="viewEntriesURL" />

			<aui:nav-item
				href="<%= viewEntriesURL %>"
				label="entries"
				selected='<%= !mvcRenderCommandName.equals("/blogs/view_my_pending_entries") %>'
			/>

			<portlet:renderURL var="viewMyPendingEntriesURL">
				<portlet:param name="mvcRenderCommandName" value="/blogs/view_my_pending_entries" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= viewMyPendingEntriesURL %>"
				label='<%= LanguageUtil.format(resourceBundle, "my-pending-entries-x", pendingEntriesCount, false) %>'
				selected='<%= mvcRenderCommandName.equals("/blogs/view_my_pending_entries") %>'
			/>
		</aui:nav>
	</aui:nav-bar>
</c:if>

<%@ include file="/blogs/view_entries.jspf" %>