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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.comment.Comment" %><%@
page import="com.liferay.portal.kernel.comment.context.CommentSectionDisplayContext" %><%@
page import="com.liferay.portal.kernel.comment.context.CommentTreeDisplayContext" %><%@
page import="com.liferay.portlet.messageboards.comment.context.MBCommentSectionDisplayContext" %><%@
page import="com.liferay.portlet.messageboards.comment.context.MBCommentTreeDisplayContext" %><%@
page import="com.liferay.portlet.messageboards.comment.context.util.DiscussionRequestHelper" %><%@
page import="com.liferay.portlet.messageboards.comment.context.util.DiscussionTaglibHelper" %><%@
page import="com.liferay.portlet.messageboards.model.MBDiscussion" %><%@
page import="com.liferay.portlet.messageboards.model.MBMessageDisplay" %><%@
page import="com.liferay.portlet.messageboards.model.MBTreeWalker" %><%@
page import="com.liferay.portlet.messageboards.util.comparator.MessageThreadComparator" %><%@
page import="com.liferay.portlet.ratings.model.RatingsEntry" %><%@
page import="com.liferay.portlet.ratings.model.RatingsStats" %><%@
page import="com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil" %><%@
page import="com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil" %>

<portlet:defineObjects />