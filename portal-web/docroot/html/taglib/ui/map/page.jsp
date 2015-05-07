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

<%@ include file="/html/taglib/init.jsp" %>

<%
String protocol = HttpUtil.getProtocol(request);

JSONArray controlsJSONArray = (JSONArray) request.getAttribute("liferay-ui:map:controlsJSONArray");
boolean geolocation = GetterUtil.getBoolean(request.getAttribute("liferay-ui:map:geolocation"));
double latitude = GetterUtil.getDouble(request.getAttribute("liferay-ui:map:latitude"));
double longitude = GetterUtil.getDouble(request.getAttribute("liferay-ui:map:longitude"));
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:map:name"));
JSONObject pointsJSONObject = (JSONObject)request.getAttribute("liferay-ui:map:pointsJSONObject");
int zoom = GetterUtil.getInteger(request.getAttribute("liferay-ui:map:zoom"));

PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

String mapsAPIProvider = PrefsParamUtil.getString(companyPortletPreferences, request, "mapsAPIProvider", "Google");
String googleMapsAPIKey = PrefsParamUtil.getString(companyPortletPreferences, request, "googleMapsAPIKey", "");

Group group = themeDisplay.getSiteGroup();

if (group.isStagingGroup()) {
	group = group.getLiveGroup();
}

UnicodeProperties groupTypeSettings = new UnicodeProperties();

if (group != null) {
	groupTypeSettings = group.getTypeSettingsProperties();
}

mapsAPIProvider = PropertiesParamUtil.getString(groupTypeSettings, request, "mapsAPIProvider", mapsAPIProvider);
googleMapsAPIKey = PropertiesParamUtil.getString(groupTypeSettings, request, "googleMapsAPIKey", googleMapsAPIKey);

name = namespace + name;
%>

<c:if test='<%= mapsAPIProvider.equals("Google") %>'>
	<liferay-util:html-bottom outputKey="js_maps_google_skip_map_loading">
		<script>
			Liferay.namespace('Maps').onGMapsReady = function(event) {
				Liferay.Maps.gmapsReady = true;

				Liferay.fire('gmapsReady');
			};
		</script>

		<%
		String apiURL = protocol + "://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&callback=Liferay.Maps.onGMapsReady";

		if (Validator.isNotNull(googleMapsAPIKey)) {
			apiURL += "&key=" + googleMapsAPIKey;
		}
		%>

		<script src="<%= apiURL %>" type="text/javascript"></script>
	</liferay-util:html-bottom>
</c:if>

<c:if test='<%= mapsAPIProvider.equals("OpenStreet") %>'>
	<liferay-util:html-top outputKey="js_maps_openstreet_skip_loading">
		<link href="<%= protocol %>://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" rel="stylesheet" />
		<script src="<%= protocol %>://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
	</liferay-util:html-top>
</c:if>

<div class="lfr-map" id="<%= name %>Map"></div>

<aui:script use='<%= "liferay-map-" + StringUtil.toLowerCase(mapsAPIProvider) %>'>
	var MapControls = Liferay.MapBase.CONTROLS;

	var mapConfig = {
		boundingBox: '#<%= name %>Map',

		<c:if test="<%= controlsJSONArray != null %>">
			controls: <%= controlsJSONArray.toString() %>,
		</c:if>

		<c:if test="<%= pointsJSONObject != null %>">
			data: <%= pointsJSONObject.toString() %>,
		</c:if>

		geolocation: <%= geolocation %>

		<c:if test="<%= Validator.isNotNull(latitude) && Validator.isNotNull(longitude) %>">
			, position: {
				location: {
					lat: <%= latitude %>,
					lng: <%= longitude %>
				}
			}
		</c:if>

		<c:if test="<%= zoom != 0 %>">
			, zoom: <%= zoom %>
		</c:if>
	};

	var destroyMap = function(event, map) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			map.destroy();

			Liferay.detach('destroyPortlet', destroyMap);
		}
	};

	var createMap = function() {
		var map = new Liferay['<%= mapsAPIProvider %>Map'](mapConfig).render();

		Liferay.MapBase.register('<%= name %>', map);

		Liferay.on('destroyPortlet', A.rbind(destroyMap, destroyMap, map));
	};

	<c:choose>
		<c:when test='<%= mapsAPIProvider.equals("Google") %>'>
			if (Liferay.Maps.gmapsReady) {
				createMap();
			}
			else {
				Liferay.once('gmapsReady', createMap);
			}
		</c:when>
		<c:otherwise>
			createMap();
		</c:otherwise>
	</c:choose>
</aui:script>