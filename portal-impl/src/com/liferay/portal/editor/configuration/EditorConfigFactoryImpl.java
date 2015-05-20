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
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigFactory;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorConfigFactoryImpl
	extends BaseEditorConfigurationFactoryImpl<EditorConfigContributor>
		implements EditorConfigFactory {

	@Override
	public EditorConfig getEditorConfig(
		String portletName, String editorConfigKey, String editorName,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		List<EditorConfigContributor> editorConfigContributors =
			getEditorConfigContributors(
				portletName, editorConfigKey, editorName);

		return new EditorConfigImpl(
			editorConfigContributors, inputEditorTaglibAttributes, themeDisplay,
			liferayPortletResponse);
	}

	protected List<EditorConfigContributor> getEditorConfigContributors(
		String portletName, String editorConfigKey, String editorName) {

		return super.getContributors(portletName, editorConfigKey, editorName);
	}

	@Override
	protected ServiceTrackerMap<String, List<EditorConfigContributor>>
		getServiceTrackerMap() {

		return _serviceTrackerMap;
	}

	private static final ServiceReferenceMapper<String, EditorConfigContributor>
		_serviceReferenceMapper =
			new BaseEditorConfigurationFactoryImpl.
				EditorServiceReferenceMapper();
	private static final ServiceTrackerMap
		<String, List<EditorConfigContributor>> _serviceTrackerMap =
			ServiceTrackerCollections.multiValueMap(
				EditorConfigContributor.class,
				"(|(editor.config.key=*)(editor.name=*)(javax.portlet.name=*))",
				_serviceReferenceMapper);

	static {
		_serviceTrackerMap.open();
	}

}