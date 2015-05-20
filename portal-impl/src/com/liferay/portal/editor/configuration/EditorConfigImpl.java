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

package com.liferay.portal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfig;
import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.editor.configuration.EditorConfigTransformer;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorConfigImpl implements EditorConfig {

	public EditorConfigImpl(
		JSONObject configJSONObject, EditorOptions editorOptions,
		EditorConfigTransformer editorConfigTransformer,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		if (editorConfigTransformer != null) {
			editorConfigTransformer.transform(
				editorOptions, inputEditorTaglibAttributes, themeDisplay,
				liferayPortletResponse, configJSONObject);
		}

		_configJSONObject = configJSONObject;
		_editorOptions = editorOptions;
	}

	@Override
	public JSONObject getConfigJSONObject() {
		return _configJSONObject;
	}

	@Override
	public Map<String, Object> getData() {
		Map<String, Object> data = new HashMap<>();

		data.put("editorConfig", _configJSONObject);
		data.put("editorOptions", _editorOptions);

		return data;
	}

	@Override
	public EditorOptions getEditorOptions() {
		return _editorOptions;
	}

	private final JSONObject _configJSONObject;
	private final EditorOptions _editorOptions;

}