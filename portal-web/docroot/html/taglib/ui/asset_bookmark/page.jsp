<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%
long userId = (Long)request.getAttribute("liferay-ui:asset-bookmark:userId");
String className = (String)request.getAttribute("liferay-ui:asset-bookmark:className");
long classPK = (Long)request.getAttribute("liferay-ui:asset-bookmark:classPK");

boolean isBookmarked = AssetBookmarkLocalServiceUtil.isBookmarked(userId, className, classPK);
String starId = portletDisplay.getNamespace() + "favoriteStar" + String.valueOf(classPK);
%>

<a class="aui-rating-element <%= isBookmarked ? "aui-rating-element-on" : StringPool.BLANK %>" href="javascript:;" id="<%= starId %>" title='<%= isBookmarked ? LanguageUtil.get(pageContext, "bookmark-this") : LanguageUtil.get(pageContext, "un-bookmark-this") %>'></a>

<aui:script use="aui-base">
	var favoriteStar = A.one('#<%= starId %>');

	if (favoriteStar) {
		favoriteStar.on(
			'click',
			function(event) {
				if (favoriteStar.hasClass('aui-rating-element-on')) {
					Liferay.Service(
						'/assetbookmark/delete-asset-bookmark',
						{
							userId: <%= userId %>,
							classPK: <%= classPK %>
						}
					);

					favoriteStar.removeClass('aui-rating-element-on');
				}
				else {
					Liferay.Service(
						'/assetbookmark/add-asset-bookmark',
						{
							userId: <%= userId %>,
							className: '<%= className %>',
							classPK: <%= classPK %>
						}
					);

					favoriteStar.addClass('aui-rating-element-on');
				}
			}
		);
	}
</aui:script>