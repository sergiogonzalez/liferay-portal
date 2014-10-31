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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
int abstractLength = (Integer)request.getAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH);

AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute(WebKeys.ASSET_RENDERER);

String viewURL = (String)request.getAttribute(WebKeys.ASSET_PUBLISHER_VIEW_URL);

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
%>

<c:if test="<%= article.isSmallImage() %>">
	<div class="asset-small-image">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(viewURL) %>">
				<a href="<%= viewURL %>">
					<img alt="<%= HtmlUtil.escapeAttribute(article.getTitle()) %>" class="asset-small-image img-thumbnail" src="<%= HtmlUtil.escapeAttribute(article.getArticleImageURL(themeDisplay)) %>" width="150" />
				</a>
			</c:when>
			<c:otherwise>
				<img alt="" class="asset-small-image img-thumbnail" src="<%= HtmlUtil.escapeAttribute(article.getArticleImageURL(themeDisplay)) %>" width="150" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<%= StringUtil.shorten(assetRenderer.getSummary(), abstractLength) %>