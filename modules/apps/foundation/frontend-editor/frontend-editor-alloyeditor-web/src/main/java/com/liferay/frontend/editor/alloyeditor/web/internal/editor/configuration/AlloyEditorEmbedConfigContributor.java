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

package com.liferay.frontend.editor.alloyeditor.web.internal.editor.configuration;

import com.liferay.frontend.editor.api.embed.EditorEmbedProvider;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Chema Balsas
 */
@Component(
	property = "editor.name=alloyeditor",
	service = EditorConfigContributor.class
)
public class AlloyEditorEmbedConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put("embedProviders", getEmbedProvidersJSONArray());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, EditorEmbedProvider.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	protected JSONObject getEmbedProviderJSONObject(
		EditorEmbedProvider editorEmbedProvider) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("id", editorEmbedProvider.getId());

		JSONArray schemasJSONArray = JSONFactoryUtil.createJSONArray();

		jsonObject.put("schemas", schemasJSONArray);

		String[] schemas = editorEmbedProvider.getSchemas();

		for (String schema : schemas) {
			schemasJSONArray.put(schema);
		}

		jsonObject.put("tpl", editorEmbedProvider.getTpl());

		return jsonObject;
	}

	protected JSONArray getEmbedProvidersJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		_serviceTrackerList.forEach(this::getEmbedProviderJSONObject);

		return jsonArray;
	}

	private ServiceTrackerList<EditorEmbedProvider, EditorEmbedProvider>
		_serviceTrackerList;

}