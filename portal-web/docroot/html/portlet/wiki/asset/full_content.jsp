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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

PortletURL viewPageURL = new PortletURLImpl(request, PortletKeys.WIKI, plid, PortletRequest.ACTION_PHASE);

viewPageURL.setParameter("struts_action", "/wiki/view");
viewPageURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
viewPageURL.setPortletMode(PortletMode.VIEW);
viewPageURL.setWindowState(WindowState.MAXIMIZED);

PortletURL editPageURL = new PortletURLImpl(request, PortletKeys.WIKI, plid, PortletRequest.ACTION_PHASE);

editPageURL.setParameter("struts_action", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);
editPageURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
editPageURL.setPortletMode(PortletMode.VIEW);
editPageURL.setWindowState(WindowState.MAXIMIZED);

String attachmentURLPrefix = themeDisplay.getPathMain() + "/wiki/get_page_attachment?p_l_id=" + themeDisplay.getPlid() + "&nodeId=" + wikiPage.getNodeId() + "&title=" + HttpUtil.encodeURL(wikiPage.getTitle()) + "&fileName=";

String summary = wikiPage.getContent();

try {
	summary = HtmlUtil.extractText(WikiUtil.convert(wikiPage, viewPageURL, editPageURL, attachmentURLPrefix));
}
catch (Exception e) {
}
%>

<%= summary %>

<liferay-ui:custom-attributes-available className="<%= WikiPage.class.getName() %>">
	<liferay-ui:custom-attribute-list
		className="<%= WikiPage.class.getName() %>"
		classPK="<%= (wikiPage != null) ? wikiPage.getPrimaryKey() : 0 %>"
		editable="<%= false %>"
		label="<%= true %>"
	/>
</liferay-ui:custom-attributes-available>