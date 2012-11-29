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

String duplicationCheckURLAction= (String)request.getAttribute("liferay-ui:restore-entry:duplicationCheckURLAction");
String overrideLabelMessage= (String)request.getAttribute("liferay-ui:restore-entry:overrideLabelMessage");
String renameLabelMessage= (String)request.getAttribute("liferay-ui:restore-entry:renameLabelMessage");
String restoreURLAction= (String)request.getAttribute("liferay-ui:restore-entry:restoreURLAction");

if (duplicationCheckURLAction== null || duplicationCheckURLAction.length()==0){
	duplicationCheckURLAction= "/trash/edit_entry";
}
if (overrideLabelMessage==null || overrideLabelMessage.length()==0){
	overrideLabelMessage= "overwrite-the-existing-entry-with-the-one-from-the-recycle-bin";
}
if (renameLabelMessage==null || renameLabelMessage.length()==0){
	renameLabelMessage= "keep-both-entries-and-rename-the-entry-from-the-recycle-bin-as";
}
if (restoreURLAction== null || restoreURLAction.length()==0){
	restoreURLAction= "/trash/restore_entry";
}
%>

<aui:script use="liferay-restore-entry">

	<portlet:actionURL var="restoreURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECK %>" />
		<portlet:param name="struts_action" value="<%= restoreURLAction %>" />
	</portlet:actionURL>

	<portlet:renderURL var="duplicationCheckURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
		<portlet:param name="struts_action" value="<%= duplicationCheckURLAction %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="restoreURLAction" value="<%= restoreURLAction %>" />
	</portlet:renderURL>

	new Liferay.RestoreEntry(
		{
			duplicationCheckURL: '<%= duplicationCheckURL %>',
			restoreURL: '<%= restoreURL %>',
			renameLabelMessage: '<%= renameLabelMessage %>',
			namespace: '<portlet:namespace />',
			overrideLabelMessage: '<%= overrideLabelMessage %>'
		}
	);
</aui:script>