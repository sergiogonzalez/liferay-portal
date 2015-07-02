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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "discussion_full_content") + StringPool.UNDERLINE;

MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);
Comment comment = CommentManagerUtil.fetchComment(message.getMessageId());

Comment parentComment = CommentManagerUtil.fetchComment(message.getParentMessageId());

WorkflowableComment workflowableComment = null;

boolean approved = true;

if (comment instanceof WorkflowableComment) {
	workflowableComment = (WorkflowableComment)comment;

	approved = workflowableComment.getStatus() == WorkflowConstants.STATUS_APPROVED;
}
%>

<table class="lfr-grid lfr-table">
<tr>
	<td colspan="2" id="<%= randomNamespace %>messageScroll<%= comment.getCommentId() %>">
		<a name="<%= randomNamespace %>message_<%= comment.getCommentId() %>"></a>
	</td>
</tr>
<tr>
	<td class="lfr-center lfr-top">
		<liferay-ui:user-display
			displayStyle="2"
			userId="<%= comment.getUserId() %>"
			userName="<%= HtmlUtil.escape(comment.getUserName()) %>"
		/>
	</td>
	<td class="lfr-top stretch">
		<c:if test="<%= (workflowableComment != null) && !approved %>">
			<aui:model-context bean="<%= comment %>" model="<%= comment.getModelClass() %>" />

			<div>
				<aui:workflow-status model="<%= CommentConstants.getDiscussionClass() %>" status="<%= workflowableComment.getStatus() %>" />
			</div>
		</c:if>

		<div>
			<%= HtmlUtil.escape(comment.getBody()) %>
		</div>

		<br />

		<div>
			<c:if test="<%= parentComment.isRoot() %>">
				<%= LanguageUtil.format(request, "posted-on-x", dateFormatDateTime.format(comment.getModifiedDate()), false) %>
			</c:if>
		</div>
	</td>
</tr>
</table>

<c:if test="<%= (parentComment != null) && !parentComment.isRoot() %>">
	<h3><liferay-ui:message key="replying-to" />:</h3>

	<%
	request.setAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE, MBMessageLocalServiceUtil.fetchMBMessage(parentComment.getCommentId()));
	%>

	<liferay-util:include page="/html/portlet/message_boards/asset/discussion_full_content.jsp" />
</c:if>