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

<%@ include file="/html/common/init.jsp" %>

<%
String currentURL= (String)request.getAttribute("liferay-ui:restore-entry:currentURL");
%>

<aui:script use="liferay-restore-entry">

	<portlet:actionURL var="checkEntryURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECK %>" />
		<portlet:param name="struts_action" value="/trash/edit_entry" />
	</portlet:actionURL>

	<portlet:renderURL var="restoreEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
		<portlet:param name="struts_action" value="/trash/restore_entry" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	new Liferay.RestoreEntry(
		{
			checkEntryURL: '<portlet:actionURL><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECK %>" /><portlet:param name="struts_action" value="/wiki/restore_page_attachment" /></portlet:actionURL>',
			namespace: '<portlet:namespace />',
			restoreEntryURL: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/wiki/restore_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="restoreEntryURL" value="<%= restoreEntryURL %>" /></portlet:renderURL>',
		}
	);
</aui:script>