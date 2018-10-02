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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileEntryCreationPermissionPolicy;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Iván Zaera
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_file_entry"
	},
	service = MVCRenderCommand.class
)
public class EditFileEntryMVCRenderCommand
	extends GetFileEntryMVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		String dlFileEntryCreationPermissionPolicyName = GetterUtil.getString(
			group.getLiveParentTypeSettingsProperty(
				"dlFileEntryCreationPermissionPolicyName"));

		DLFileEntryCreationPermissionPolicy
			dlFileEntryCreationPermissionPolicy = null;

		if (Validator.isNotNull(dlFileEntryCreationPermissionPolicyName)) {
			dlFileEntryCreationPermissionPolicy = _serviceTrackerMap.getService(
				dlFileEntryCreationPermissionPolicyName);
		}

		renderRequest.setAttribute(
			DLFileEntryCreationPermissionPolicy.class.getName(),
			dlFileEntryCreationPermissionPolicy);

		return super.render(renderRequest, renderResponse);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DLFileEntryCreationPermissionPolicy.class,
			"file.entry.creation.permission.policy.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	protected String getPath() {
		return "/document_library/edit_file_entry.jsp";
	}

	private ServiceTrackerMap<String, DLFileEntryCreationPermissionPolicy>
		_serviceTrackerMap;

}