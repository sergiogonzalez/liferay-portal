/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.settings.BaseServiceSettings;
import com.liferay.portal.settings.FallbackKeys;
import com.liferay.portal.settings.Settings;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez Álvarez
 */
public class DLSettings extends BaseServiceSettings {

	public DLSettings(Settings settings) {
		super(settings, _fallbackKeys);
	}

	public long getDefaultFolderId() {
		return typedSettings.getLongValue(
			"rootFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public Map<Locale, String> getEmailFileEntryAddedBodyMap() {
		return typedSettings.getLocalizedValue("emailFileEntryAddedBody");
	}

	public boolean getEmailFileEntryAddedEnabled() {
		return typedSettings.getBooleanValue("emailFileEntryAddedEnabled");
	}

	public Map<Locale, String> getEmailFileEntryAddedSubjectMap() {
		return typedSettings.getLocalizedValue("emailFileEntryAddedSubject");
	}

	public boolean getEmailFileEntryAnyEventEnabled() {
		return getEmailFileEntryAddedEnabled() ||
			getEmailFileEntryUpdatedEnabled();
	}

	public Map<Locale, String> getEmailFileEntryUpdatedBodyMap() {
		return typedSettings.getLocalizedValue("emailFileEntryUpdatedBody");
	}

	public boolean getEmailFileEntryUpdatedEnabled() {
		return typedSettings.getBooleanValue("emailFileEntryUpdatedEnabled");
	}

	public Map<Locale, String> getEmailFileEntryUpdatedSubjectMap() {
		return typedSettings.getLocalizedValue("emailFileEntryUpdatedSubject");
	}

	public String getEmailFromAddress() {
		return typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return typedSettings.getValue("emailFromName");
	}

	public String[] getMediaGalleryMimeTypes() {
		return typedSettings.getValues(
			"mimeTypes", _defaultMediaGalleryMimeTypes);
	}


	private static FallbackKeys _fallbackKeys = new FallbackKeys();
	private static String[] _defaultMediaGalleryMimeTypes;

	static {
		_fallbackKeys.add(
			"emailFileEntryAddedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_BODY);
		_fallbackKeys.add(
			"emailFileEntryAddedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_ENABLED);
		_fallbackKeys.add(
			"emailFileEntryAddedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT);
		_fallbackKeys.add(
			"emailFileEntryUpdatedBody",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_BODY);
		_fallbackKeys.add(
			"emailFileEntryUpdatedEnabled",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_ENABLED);
		_fallbackKeys.add(
			"emailFileEntryUpdatedSubject",
			PropsKeys.DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT);
		_fallbackKeys.add(
			"emailFromAddress", PropsKeys.DL_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		_fallbackKeys.add(
			"emailFromName", PropsKeys.DL_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);

		Set<String> allMimeTypes = DLUtil.getAllMediaGalleryMimeTypes();

		_defaultMediaGalleryMimeTypes = allMimeTypes.toArray(
			new String[allMimeTypes.size()]);
	}

}