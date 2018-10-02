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

package com.liferay.document.library.web.internal.servlet.taglib.ui;

import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileEntryCreationPermissionPolicy;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true, property = "form.navigator.entry.order:Integer=20",
	service = FormNavigatorEntry.class
)
public class SiteDocumentsAndMediaFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<Group> {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_SITES_GENERAL;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES;
	}

	@Override
	public String getKey() {
		return "documents-and-media";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "documents-and-media");
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		Group group = (Group)request.getAttribute("site.group");
		Group liveGroup = (Group)request.getAttribute("site.liveGroup");

		UnicodeProperties typeSettingsProperties = null;

		if (liveGroup != null) {
			typeSettingsProperties = liveGroup.getTypeSettingsProperties();
		}
		else {
			typeSettingsProperties = group.getTypeSettingsProperties();
		}

		boolean directoryIndexingEnabled = PropertiesParamUtil.getBoolean(
			typeSettingsProperties, request, "directoryIndexingEnabled");

		request.setAttribute(
			DLWebKeys.DIRECTORY_INDEXING_ENABLED, directoryIndexingEnabled);

		request.setAttribute(
			DLWebKeys.
				DOCUMENT_LIBRARY_FILE_ENTRY_CREATION_PERMISSION_POLICY_NAME_SET,
			_serviceTrackerMap.keySet());

		String dlFileEntryCreationPermissionPolicyName =
			PropertiesParamUtil.getString(
				typeSettingsProperties, request,
				"dlFileEntryCreationPermissionPolicyName");

		request.setAttribute(
			DLWebKeys.
				DOCUMENT_LIBRARY_FILE_ENTRY_CREATION_PERMISSION_POLICY_NAME,
			dlFileEntryCreationPermissionPolicyName);

		super.include(request, response);
	}

	@Override
	public boolean isVisible(User user, Group group) {
		if ((group == null) || group.isCompany()) {
			return false;
		}

		return true;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
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
	protected String getJspPath() {
		return "/sites_admin/documents_and_media.jsp";
	}

	private ServiceTrackerMap<String, DLFileEntryCreationPermissionPolicy>
		_serviceTrackerMap;

}