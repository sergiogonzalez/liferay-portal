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
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

int status = (Integer)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_STATUS);
%>

<div class="kb-article-tools">
	<c:if test="<%= kbGroupServiceConfiguration.sourceURLEnabled() && Validator.isUrl(kbArticle.getSourceURL()) %>">
		<a href="<%= kbArticle.getSourceURL() %>" target="_blank">
			<span class="kb-article-source-url label label-success">
				<liferay-ui:message key="<%= kbGroupServiceConfiguration.sourceURLEditMessageKey() %>" />
			</span>
		</a>
	</c:if>

	<c:if test="<%= enableRSS && (kbArticle.isApproved() || !kbArticle.isFirstVersion()) && !Objects.equals(portletDisplay.getRootPortletId(), KBPortletKeys.KNOWLEDGE_BASE_ADMIN) %>">
		<liferay-portlet:resourceURL id="kbArticleRSS" varImpl="kbArticleRSSURL">
			<portlet:param name="resourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
			<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
		</liferay-portlet:resourceURL>

		<liferay-ui:rss
			delta="<%= rssDelta %>"
			displayStyle="<%= rssDisplayStyle %>"
			feedType="<%= rssFeedType %>"
			resourceURL="<%= kbArticleRSSURL %>"
		/>
	</c:if>
</div>