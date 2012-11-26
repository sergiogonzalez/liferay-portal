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

String overrideLabelMessage= (String)request.getAttribute("liferay-ui:restore-entry:overrideLabelMessage");
String renameLabelMessage= (String)request.getAttribute("liferay-ui:restore-entry:renameLabelMessage");

if (overrideLabelMessage==null || overrideLabelMessage.length()==0){
	overrideLabelMessage= "overwrite-the-existing-entry-with-the-one-from-the-recycle-bin";
}
if (renameLabelMessage==null || renameLabelMessage.length()==0){
	renameLabelMessage= "keep-both-entries-and-rename-the-entry-from-the-recycle-bin-as";
}
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
	renameLabelMessage: '<%= renameLabelMessage %>',
	overrideLabelMessage: '<%= overrideLabelMessage %>'
	}
	);
</aui:script>