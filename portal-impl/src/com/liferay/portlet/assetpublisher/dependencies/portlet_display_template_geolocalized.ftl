<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />
<#assign liferay_aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />

<#assign companyPrefs = prefsPropsUtil.getPreferences(companyId) />

<#assign isGoogleMapsEnabled = getterUtil.getBoolean(companyPrefs.getValue("googleMapsEnabled", "false")) />
<#assign apiKey = companyPrefs.getValue("googleMapsApiKey", "") />

<#assign group = themeDisplay.getScopeGroup() />
<#assign typeSettings = group.getParentLiveGroupTypeSettingsProperties() />

<#if typeSettings?keys?seq_contains("googleMapsEnabled")>
	<#assign isGoogleMapsEnabled = getterUtil.getBoolean(group.getLiveParentTypeSettingsProperty("googleMapsEnabled")) />
</#if>

<#if typeSettings?keys?seq_contains("googleMapsApiKey")>
	<#assign apiKey = group.getLiveParentTypeSettingsProperty("googleMapsApiKey") />
</#if>

<#assign defaultLatitude = -3.6833 />
<#assign defaultLongitude = 40.40 />

<#if themeDisplay.isSecure()>
	<#assign uriScheme = "https" />
<#else>
	<#assign uriScheme = "http" />
</#if>

<#assign maxWidth = 500 />
<#assign minHeight = "400px" />
<#assign minWidth = "400px" />

<#assign namespace = renderResponse.getNamespace() />

<#assign showEditURL = paramUtil.getBoolean(renderRequest, "showEditURL", true) />

<#assign images = {
	"com.liferay.portlet.documentlibrary.model.DLFileEntry": "${uriScheme}://maps.google.com/mapfiles/ms/icons/green-dot.png",
	"com.liferay.portlet.dynamicdatalists.model.DDLRecord": "${uriScheme}://maps.google.com/mapfiles/ms/icons/red-dot.png",
	"com.liferay.portlet.journal.model.JournalArticle": "${uriScheme}://maps.google.com/mapfiles/ms/icons/blue-dot.png",
	"default": "${uriScheme}://maps.google.com/mapfiles/ms/icons/yellow-dot.png"
} />

<#assign jsonArray = jsonFactoryUtil.createJSONArray() />

<#list entries as entry>
	<#assign assetRenderer = entry.getAssetRenderer() />

	<#assign ddmReader = assetRenderer.getDDMFieldReader() />

	<#assign fields = ddmReader.getFields("geolocation") />

	<#list fields.iterator() as field>
		<#assign jsonObject = jsonFactoryUtil.createJSONObject(field.getValue()) />

		<@liferay.silently jsonObject.put("title", assetRenderer.getTitle(locale)) />

		<#assign entryAbstract>
			<@getAbstract asset = entry />
		</#assign>

		<@liferay.silently jsonObject.put("abstract", entryAbstract) />

		<#if isGoogleMapsEnabled>
			<#if images?keys?seq_contains(entry.getClassName())>
				<@liferay.silently jsonObject.put("icon", images[entry.getClassName()]) />
			<#else>
				<@liferay.silently jsonObject.put("icon", images["default"]) />
			</#if>
		</#if>

		<@liferay.silently jsonArray.put(jsonObject) />
	</#list>
</#list>

<#if isGoogleMapsEnabled >
	<style type="text/css">
		.${namespace}asset-entry-abstract {
			min-width: ${minWidth};
		}

		.${namespace}asset-entry-abstract .asset-entry-abstract-image {
			float: left;
		}

		.${namespace}asset-entry-abstract .asset-entry-abstract-image img {
			display: block;
			margin-right: 2em;
		}

		.${namespace}asset-entry-abstract .taglib-icon {
			float: right;
		}

		.${namespace}map-canvas {
			min-height: ${minHeight};
		}

		.${namespace}map-canvas img {
			max-width: none;
		}
	</style>
<#else>
	<style type="text/css">
		.${namespace}asset-entry-abstract {
			min-width: ${minWidth};
			overflow: auto;
		}

		.${namespace}asset-entry-abstract .asset-entry-abstract-image {
			float: left;
			margin-right: 2em;
		}

		.${namespace}asset-entry-abstract .asset-entry-abstract-image img {
			display: block;
		}

		.${namespace}asset-entry-abstract .taglib-icon {
			float: right;
		}

		.${namespace}map-canvas {
			min-height: ${minHeight};
		}
	</style>
</#if>

<div class="${namespace}map-canvas" id="${namespace}mapCanvas"></div>

<#if isGoogleMapsEnabled>
	<#if apiKey = "">
		<script src="${uriScheme}://maps.googleapis.com/maps/api/js?sensor=true" type="text/javascript"></script>
	<#else>
		<script src="${uriScheme}://maps.googleapis.com/maps/api/js?key=${apiKey}&sensor=true" type="text/javascript"></script>
	</#if>
<#else>
	<link href="${uriScheme}://cdn.leafletjs.com/leaflet-0.7.2/leaflet.css" rel="stylesheet" />

	<script src="${uriScheme}://cdn.leafletjs.com/leaflet-0.7.2/leaflet.js"></script>
</#if>

<#if isGoogleMapsEnabled>
	<@liferay_aui.script>
	(function() {
		var putMarkers = function(map) {
			var points = ${jsonArray};

			var len = points.length;

			if (len == 0) {
				return null;
			}

			var bounds = new google.maps.LatLngBounds();

			for (var i = 0; i < len; i++) {
				var point = points[i];

				var marker = new google.maps.Marker(
					{
						icon: point['icon'],
						map: map,
						position: new google.maps.LatLng(point['latitude'], point['longitude']),
						title: point['title']
					}
				);

				bounds.extend(marker.position);

				(function(marker) {
					var infoWindow = new google.maps.InfoWindow(
						{
							content: point['abstract'] || point['title']
						}
					);

					google.maps.event.addListener(
						marker, 'click',
						function() {
							infoWindow.open(map, marker);
						}
					);
				})(marker);
			}

			return bounds;
		};

		var drawMap = function(mapOptions) {
			var map = new google.maps.Map(document.getElementById('${namespace}mapCanvas'), mapOptions);

			var bounds = putMarkers(map);

			if (bounds) {
				map.fitBounds(bounds);
				map.panToBounds(bounds);
			}
		};

		var drawDefaultMap = function() {
			drawMap(
				{
					center: new google.maps.LatLng(${defaultLatitude}, ${defaultLongitude}),
					zoom: 8
				}
			);
		};

		Liferay.Util.getGeolocation(
			function(latitude, longitude) {
				drawMap(
					{
						center: new google.maps.LatLng(latitude, longitude),
						zoom: 8
					}
				);
			},
			drawDefaultMap
		);
	})();
	</@liferay_aui.script>
<#else>
	<@liferay_aui.script>
	(function() {
		var putMarkers = function(map) {
			L.tileLayer(
				'${uriScheme}://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
				{
					attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
				}
			).addTo(map);

			var bounds = L.latLngBounds([]);
			var points = ${jsonArray};

			var len = points.length;

			for (var i = 0; i < len; i++) {
				var point = points[i];

				var latLng = L.latLng(point['latitude'], point['longitude']);

				L.marker(latLng).addTo(map).bindPopup(
					point['abstract'],
					{
						maxWidth: ${maxWidth}
					}
				);

				bounds.extend(latLng);
			}

			return bounds;
		};

		var drawMap = function(lat, lng) {
			var map = L.map('${namespace}mapCanvas').setView([lat, lng], 8);

			map.fitBounds(putMarkers(map));
		};

		var drawDefaultMap = function() {
			drawMap(${defaultLatitude}, ${defaultLongitude});
		};

		Liferay.Util.getGeolocation(
			function(latitude, longitude) {
				drawMap(latitude, longitude);
			},
			drawDefaultMap
		);
	})();
	</@liferay_aui.script>
</#if>

<#macro getAbstract asset>
	<#assign assetRenderer = asset.getAssetRenderer() />

	<#assign redirectURL = renderResponse.createLiferayPortletURL(themeDisplay.getPlid(), themeDisplay.getPortletDisplay().getId(), "RENDER_PHASE", false) />

	${redirectURL.setParameter("struts_action", "/asset_publisher/add_asset_redirect")}

	<div class="${namespace}asset-entry-abstract">
		<#assign editPortletURL = assetRenderer.getURLEdit(renderRequest, renderResponse, windowStateFactory.getWindowState("POP_UP"), redirectURL) />

		<#assign taglibEditURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "editAsset', title: '" + htmlUtil.escapeJS(languageUtil.format(locale, "edit-x", htmlUtil.escape(assetRenderer.getTitle(locale)), false)) + "', uri:'" + htmlUtil.escapeJS(editPortletURL.toString()) + "'});" />

		<#if showEditURL && assetRenderer.hasEditPermission(permissionChecker)>
			<@liferay_ui.icon
				image = "edit"
				label = true
				message = "edit"
				url = taglibEditURL
			/>
		</#if>

		<div class="asset-entry-abstract-image">
			<img src="${assetRenderer.getThumbnailPath(renderRequest)}" />
		</div>

		<#assign assetURL = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, asset) />

		<div class="asset-entry-abstract-content">
			<h3><a href="${assetURL}">${assetRenderer.getTitle(locale)}</a></h3>

			<div>
				${assetRenderer.getSummary(renderRequest, renderResponse)}
			</div>
		</div>

		<div class="asset-entry-abstract-footer">
			<a href="${assetURL}">${languageUtil.get(locale, "read-more")} &raquo;</a>
		</div>
	</div>
</#macro>