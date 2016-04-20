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

package com.liferay.image.editor.web.portlet.action;

import com.liferay.image.editor.capability.ImageEditorCapability;
import com.liferay.image.editor.web.portlet.tracker.ImageEditorCapabilityTracker;
import com.liferay.image.editor.web.constants.ImageEditorPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ImageEditorPortletKeys.IMAGE_EDITOR,
		"mvc.command.name=/", "mvc.command.name=View"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Template template = getTemplate(renderRequest);

		List<Map<String, Object>> toolsContexts = new ArrayList<>();

		List<ImageEditorCapability> imageEditorCapabilities =
			_imageEditorCapabilityTracker.getCapabilities("tool");

		Map<String, Map<String, ImageEditorCapability>> groupedImageEditorCapabilities =
			groupCapabilities(imageEditorCapabilities);

		for (Map<String, ImageEditorCapability> a : groupedImageEditorCapabilities.values()) {
			for (ImageEditorCapability imageEditorCapability : a.values()) {
				Map<String, Object> capabilityProperties =
					_imageEditorCapabilityTracker.getCapabilityProperties(
						imageEditorCapability.getName());

				Map<String, Object> context = new HashMap<>();

				String controls = GetterUtil.getString(
					capabilityProperties.get("com.liferay.image.editor.capability.controls"));
				String icon = GetterUtil.getString(
					capabilityProperties.get("com.liferay.image.editor.capability.icon"));

				context.put("controls", controls);
				context.put("icon", icon);
				context.put(
					"label",
					imageEditorCapability.getLabel(themeDisplay.getLocale()));

				HttpServletRequest httpServletRequest =
					PortalUtil.getHttpServletRequest(renderRequest);

				imageEditorCapability.prepareContext(context, httpServletRequest);

				toolsContexts.add(context);
			}
		}

		template.put("image", "http://localhost:8080/documents/20233/0/214H.jpg/44f148f9-adee-9020-dbc5-61c112d0bc26?t=1460307134146");
		template.put("tools", toolsContexts);

		return "ImageEditor";
	}

	protected Map<String, Map<String, ImageEditorCapability>> groupCapabilities(
		List<ImageEditorCapability> imageEditorCapabilities) {

		Map<String, Map<String, ImageEditorCapability>> groupedCapabilities = new HashMap<>();

		for (ImageEditorCapability imageEditorCapability : imageEditorCapabilities) {
			Map<String, Object> capabilityProperties =
				_imageEditorCapabilityTracker.getCapabilityProperties(
					imageEditorCapability.getName());

			String imageEditorCapabilityCategory = GetterUtil.getString(
				capabilityProperties.get(
					"com.liferay.image.editor.capability.category"));

			groupedCapabilities.putIfAbsent(
				imageEditorCapabilityCategory,
				new HashMap<String, ImageEditorCapability>());

			Map<String, ImageEditorCapability> imageEditorCapabilityMap =
				groupedCapabilities.get(imageEditorCapabilityCategory);

			imageEditorCapabilityMap.put(
				imageEditorCapability.getName(), imageEditorCapability);
		}

		return groupedCapabilities;
	}

	protected Template getTemplate(RenderRequest renderRequest) {
		return (Template)renderRequest.getAttribute(WebKeys.TEMPLATE);
	}

	@Reference
	private ImageEditorCapabilityTracker _imageEditorCapabilityTracker;
}