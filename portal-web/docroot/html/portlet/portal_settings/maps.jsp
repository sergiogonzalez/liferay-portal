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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

boolean isGoogleMapsEnabled = PrefsParamUtil.getBoolean(companyPortletPreferences, request, "settings--googleMapsEnabled--", false);

String googleMapsApiKey = PrefsParamUtil.getString(companyPortletPreferences, request, "googleMapsApiKey", "");
%>

<liferay-ui:error-marker key="errorSection" value="maps" />

<h3><liferay-ui:message key="maps" /></h3>

<p><liferay-ui:message key="select-the-maps-api-provider-to-use-when-displaying-geolocalized-assets" /></p>

<aui:input checked="<%= !isGoogleMapsEnabled %>" helpMessage="use-openstreetmap-as-the-maps-api-provider" id="mapsOpenStreetMapEnabled" label="openstreetmap" name="settings--googleMapsEnabled--" type="radio" value="<%= false %>" />

<aui:input checked="<%= isGoogleMapsEnabled %>" helpMessage="use-google-maps-as-the-maps-api-provider" id="mapsGoogleMapsEnabled" label="google-maps" name="settings--googleMapsEnabled--" type="radio" value="<%= true %>" />

<div class="maps-google-maps-api-key" id="<portlet:namespace />googleMapsApiKey">
	<aui:input helpMessage="set-the-google-maps-api-key-that-will-be-used-for-this-set-of-pages" label="google-maps-api-key" name="settings--googleMapsApiKey--" size="40" type="text" value="<%= googleMapsApiKey %>" />
</div>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />mapsGoogleMapsEnabled', '<portlet:namespace />googleMapsApiKey', '');
	Liferay.Util.toggleRadio('<portlet:namespace />mapsOpenStreetMapEnabled', '', '<portlet:namespace />googleMapsApiKey');
</aui:script>