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

<%@ include file="/html/taglib/ui/discussion/init.jsp" %>

<%
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

CommentTreeNodeDisplay commentTreeNodeDisplay =(CommentTreeNodeDisplay)request.getAttribute(DiscussionWebKeys.COMMENT_TREE_NODE_DISPLAY);

Comment message = commentTreeNodeDisplay.getComment();
%>

<tr>
	<td class="table-cell" style="padding-left: <%= commentTreeNodeDisplay.getDepth() * 10 %>px; width: 90%">
		<c:if test="<%= !message.isRoot() %>">
			<c:choose>
				<c:when test="<%= !commentTreeNodeDisplay.isLastNode() %>">
					<img alt="" src="<%= themeDisplay.getPathThemeImages() %>/message_boards/t.png" />
				</c:when>
				<c:otherwise>
					<img alt="" src="<%= themeDisplay.getPathThemeImages() %>/message_boards/l.png" />
				</c:otherwise>
			</c:choose>
		</c:if>

		<%
		String rowHREF = "#" + renderResponse.getNamespace() + "message_" + message.getCommentId();
		%>

		<a href="<%= rowHREF %>"><%= HtmlUtil.escape(StringUtil.shorten(message.getBody(), 50, StringPool.TRIPLE_PERIOD)) %></a>
	</td>
	<td class="table-cell"></td>
	<td class="table-cell" nowrap="nowrap">
		<a href="<%= rowHREF %>">

		<c:choose>
			<c:when test="<%= message.isAnonymous() %>">
				<c:choose>
					<c:when test="<%= Validator.isNull(message.getUserName()) %>">
						<liferay-ui:message key="anonymous" />
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(message.getUserName()) %>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<%= HtmlUtil.escape(message.getUserNameNonAnonymous()) %>
			</c:otherwise>
		</c:choose>

		</a>
	</td>
	<td class="table-cell"></td>
	<td class="table-cell" nowrap="nowrap">
		<a href="<%= rowHREF %>">
		<%= dateFormatDateTime.format(message.getModifiedDate()) %>
		</a>
	</td>
</tr>

<%
for (CommentTreeNodeDisplay childCommentTreeNodeDisplay : commentTreeNodeDisplay.getChildren()) {
	request.setAttribute(DiscussionWebKeys.COMMENT_TREE_NODE_DISPLAY, childCommentTreeNodeDisplay);
%>

	<liferay-util:include page="/html/taglib/ui/discussion/view_message_thread.jsp" />

<%
}
%>