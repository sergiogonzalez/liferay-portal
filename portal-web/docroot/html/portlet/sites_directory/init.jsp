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

<%@ include file="/html/portlet/init.jsp" %>

<%
Map<String, String> selectionOptions = new HashMap<String, String>();

selectionOptions.put("top-level", "absolute,0");
selectionOptions.put("children", "relative,0");
selectionOptions.put("siblings", "relative,1");
selectionOptions.put("parent-level", "relative,2");

PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String displayStyle = PrefsParamUtil.getString(preferences, renderRequest, "displayStyle", "descriptive");
String sitesSelection = PrefsParamUtil.getString(preferences, renderRequest, "sites", "top-level");

String[] sitesSelectionDefinition = StringUtil.split(selectionOptions.get(sitesSelection));

int level = GetterUtil.getInteger(sitesSelectionDefinition[1]);
String levelType = sitesSelectionDefinition[0];
%>

<%@ include file="/html/portlet/sites_directory/init-ext.jsp" %>