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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chema Balsas
 * @author Roberto Díaz
 */
public class MapTag extends IncludeTag {

	public JSONObject getControlsJSONObject(HttpServletRequest request) {
		if (Validator.isNotNull(_controlsJSONObject)) {
			return _controlsJSONObject;
		}

		if (!_geolocation) {
			return null;
		}

		if (BrowserSnifferUtil.isMobile(request)) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			jsonArray.put("MapControls.HOME");
			jsonArray.put("MapControls.SEARCH");

			jsonObject.put("controls", jsonArray);

			return jsonObject;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put("MapControls.HOME");
		jsonArray.put("MapControls.PAN");
		jsonArray.put("MapControls.SEARCH");
		jsonArray.put("MapControls.TYPE");
		jsonArray.put("MapControls.ZOOM");

		jsonObject.put("controls", jsonArray);

		return jsonObject;
	}

	public void setApiKey(String apiKey) {
		_apiKey = apiKey;
	}

	public void setControlsJSONObject(JSONObject controlsJSONObject) {
		_controlsJSONObject = controlsJSONObject;
	}

	public void setControlsJSONObject(String controlsJSONObjectString) {
		_controlsJSONObject = createJSONObject(controlsJSONObjectString);
	}

	public void setGeolocation(boolean geolocation) {
		_geolocation = geolocation;
	}

	public void setLatitude(double latitude) {
		_latitude = latitude;
	}

	public void setLongitude(double longitude) {
		_longitude = longitude;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPointsJSONObject(JSONObject pointsJSONObject) {
		_pointsJSONObject = pointsJSONObject;
	}

	public void setPointsJSONObject(String pointsJSONObjectString) {
		_pointsJSONObject = createJSONObject(pointsJSONObjectString);
	}

	public void setProvider(String provider) {
		_provider = provider;
	}

	public void setZoom(int zoom) {
		_zoom = zoom;
	}

	@Override
	protected void cleanUp() {
		_apiKey = null;
		_controlsJSONObject = null;
		_geolocation = false;
		_latitude = 0;
		_longitude = 0;
		_name = null;
		_pointsJSONObject = null;
		_provider = null;
		_zoom = 0;
	}

	protected JSONObject createJSONObject(String json) {
		try {
			return JSONFactoryUtil.createJSONObject(json);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to create JSON", jsone);
			}

			return null;
		}
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:map:apiKey", _apiKey);
		request.setAttribute(
			"liferay-ui:map:controlsJSONObject",
			getControlsJSONObject(request));
		request.setAttribute("liferay-ui:map:geolocation", _geolocation);
		request.setAttribute("liferay-ui:map:latitude", _latitude);
		request.setAttribute("liferay-ui:map:longitude", _longitude);
		request.setAttribute("liferay-ui:map:name", _name);
		request.setAttribute(
			"liferay-ui:map:pointsJSONObject", _pointsJSONObject);
		request.setAttribute("liferay-ui:map:provider", _provider);
		request.setAttribute("liferay-ui:map:zoom", _zoom);
	}

	private static final String _PAGE = "/html/taglib/ui/map/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(MapTag.class);

	private String _apiKey;
	private JSONObject _controlsJSONObject;
	private boolean _geolocation;
	private double _latitude;
	private double _longitude;
	private String _name;
	private JSONObject _pointsJSONObject;
	private String _provider;
	private int _zoom;

}