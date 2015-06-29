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

package com.liferay.blogs.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.editor.configuration.EditorOptionsContributor;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLBuilder;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"editor.config.key=contentEditor", "javax.portlet.name=33",
		"javax.portlet.name=161"
	},
	service = EditorOptionsContributor.class
)
public class BlogsContentEditorOptionsContributor
	implements EditorOptionsContributor {

	@Override
	public void populateEditorOptions(
		EditorOptions editorOptions,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay, PortletURLBuilder portletURLBuilder) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (Validator.isNull(portletDisplay.getId())) {
			return;
		}

		PortletURL portletURL = portletURLBuilder.createActionURL(
			portletDisplay.getId());

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/blogs/upload_editor_image");

		editorOptions.setUploadURL(portletURL.toString());
	}

}