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
String bulletStyle = ((String)request.getAttribute("liferay-ui:sites-directory:bulletStyle")).toLowerCase();
String displayStyle = (String)request.getAttribute("liferay-ui:sites-directory:displayStyle");

String headerType = null;
String includedGroups = null;
boolean nestedChildren = true;
int rootGroupLevel = 0;
String rootGroupType = null;

String[] displayStyleDefinition = _getDisplayStyleDefinition(displayStyle);

if ((displayStyleDefinition != null) && (displayStyleDefinition.length != 0)) {
	headerType = displayStyleDefinition[0];
	includedGroups = displayStyleDefinition[3];

	if (displayStyleDefinition.length > 4) {
		nestedChildren = GetterUtil.getBoolean(displayStyleDefinition[4]);
	}

	rootGroupLevel = GetterUtil.getInteger(displayStyleDefinition[2]);
	rootGroupType = displayStyleDefinition[1];
}
else {
	headerType = (String)request.getAttribute("liferay-ui:sites-directory:headerType");
	includedGroups = (String)request.getAttribute("liferay-ui:sites-directory:includedGroups");
	nestedChildren = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:sites-directory:nestedChildren"));
	rootGroupLevel = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:sites-directory:rootGroupLevel"));
	rootGroupType = (String)request.getAttribute("liferay-ui:sites-directory:rootGroupType");
}

Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);
%>

<%!
private String[] _getDisplayStyleDefinition(String displayStyle) {
	return PropsUtil.getArray("sites.directory.display.style", new Filter(displayStyle));
}
%>