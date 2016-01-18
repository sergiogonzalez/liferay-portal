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

<%@ include file="/wiki/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SocialActivity socialActivity = null;

if (row == null) {
	socialActivity = (SocialActivity)request.getAttribute("info_panel.jsp-socialActivity");
}
else {
	socialActivity = (SocialActivity)row.getObject();
}

JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(HtmlUtil.unescape(socialActivity.getExtraData()));

FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(extraDataJSONObject.getLong("fileEntryId"));

WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= fileEntry.isInTrash() && (socialActivity.getType() == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) && WikiNodePermissionChecker.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_ATTACHMENT) %>">

		<%
		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), fileEntry.getFileEntryId());
		%>

		<portlet:actionURL name="/wiki/restore_page_attachment" var="restoreEntryURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="trashEntryId" value="<%= String.valueOf(trashEntry.getEntryId()) %>" />
		</portlet:actionURL>

		<%
		String taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "checkEntry', {trashEntryId: " + trashEntry.getEntryId() + ", uri: '" + restoreEntryURL.toString() + "'});";
		%>

		<liferay-ui:icon
			message="restore-attachment"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= !fileEntry.isInTrash() && ((socialActivity.getType() == SocialActivityConstants.TYPE_ADD_ATTACHMENT) || (socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH)) && WikiPagePermissionChecker.contains(permissionChecker, wikiPage.getNodeId(), wikiPage.getTitle(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="/wiki/edit_page_attachment" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
			<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
			<portlet:param name="fileName" value="<%= fileEntry.getTitle() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			message='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "remove-attachment" : "delete-attachment" %>'
			trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>