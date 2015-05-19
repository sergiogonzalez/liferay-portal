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

<%@ include file="/init.jsp" %>

<%
String displayStyle = ParamUtil.getString(request, "displayStyle");

PortletURL portletURL = (PortletURL)request.getAttribute(DLItemSelectorView.PORTLET_URL);

SearchContainer documentsSearchContainer = new SearchContainer(renderRequest, null, null, "curDocuments", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

DLItemSelectorCriterion dlItemSelectorCriterion = (DLItemSelectorCriterion)request.getAttribute(DLItemSelectorView.DL_ITEM_SELECTOR_CRITERION);

long repositoryId = dlItemSelectorCriterion.getRepositoryId();
long folderId = dlItemSelectorCriterion.getFolderId();
String[] mimeTypes = dlItemSelectorCriterion.getMimeTypes();

documentsSearchContainer.setTotal(DLAppServiceUtil.getFileEntriesCount(repositoryId, folderId, mimeTypes));
documentsSearchContainer.setResults(DLAppServiceUtil.getFileEntries(repositoryId, folderId, mimeTypes, documentsSearchContainer.getStart(), documentsSearchContainer.getEnd(), documentsSearchContainer.getOrderByComparator()));
%>

<liferay-ui:item-selector-browser
	displayStyle="<%= displayStyle %>"
	idPrefix="<%= dlItemSelectorCriterion.getType() %>"
	searchContainer="<%= documentsSearchContainer %>"
	tabName="<%= dlItemSelectorCriterion.getType() %>"
/>