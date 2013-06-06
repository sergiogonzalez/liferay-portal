<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
List<String> alternativeLayoutFriendlyURLs = (List<String>)SessionMessages.get(request, "alternativeLayoutFriendlyUrls");

if ((alternativeLayoutFriendlyURLs != null) && !alternativeLayoutFriendlyURLs.isEmpty()) {
	List<String> alternativeFriendlyURLLinks = new ArrayList<String>();
%>

	<div class="helper-hidden alert alert-info" id="alternativeLayoutFriendlyURLNode">

		<%
		for (String alternativeLayoutFriendlyURL : alternativeLayoutFriendlyURLs) {
			StringBundler sb = new StringBundler(5);

			sb.append("<a href=\"");
			sb.append(alternativeLayoutFriendlyURL);
			sb.append("\">");
			sb.append(alternativeLayoutFriendlyURL);
			sb.append("</a>");

			alternativeFriendlyURLLinks.add(sb.toString());
		}
		%>

		<%= ListUtil.toString(alternativeFriendlyURLLinks, StringPool.BLANK, StringPool.COMMA) %>
	</div>

	<aui:script use="liferay-notice">
		var alternativeLayoutFriendlyURLNode = A.one('#alternativeLayoutFriendlyURLNode')

		var banner = new Liferay.Notice(
			{
				content: alternativeLayoutFriendlyURLNode.html(),
				noticeClass: 'hide',
				toggleText: false,
				useAnimation: true
			}
		);

		banner.show();
	</aui:script>

<%
}
%>