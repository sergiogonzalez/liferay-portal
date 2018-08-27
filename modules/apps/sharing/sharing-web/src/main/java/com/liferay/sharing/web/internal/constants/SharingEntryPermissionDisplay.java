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

package com.liferay.sharing.web.internal.constants;

import com.liferay.sharing.constants.SharingEntryActionKey;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio González
 */
public class SharingEntryPermissionDisplay {

	public static List<SharingEntryPermissionDisplay>
		getSharingEntryPermissionDisplays(
			List<SharingEntryActionKey> sharingEntryActionKeys) {

		List<SharingEntryPermissionDisplay> sharingEntryPermissionDisplays =
			new ArrayList<>();

		if (sharingEntryActionKeys.contains(
			SharingEntryActionKey.ADD_DISCUSSION) &&
			sharingEntryActionKeys.contains(SharingEntryActionKey.UPDATE) &&
			sharingEntryActionKeys.contains(SharingEntryActionKey.VIEW)) {

			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.UPDATE.getActionId(),
					SharingEntryPermissionDisplayActionKey.UPDATE.getTitleKey(),
					SharingEntryPermissionDisplayActionKey.UPDATE.getDescriptionKey(),
					true));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.UPDATE.getActionId(),
					SharingEntryPermissionDisplayActionKey.UPDATE.getTitleKey(),
					SharingEntryPermissionDisplayActionKey.UPDATE.getDescriptionKey(),
					false));
		}

		if (sharingEntryActionKeys.contains(
				SharingEntryActionKey.ADD_DISCUSSION) &&
			sharingEntryActionKeys.contains(SharingEntryActionKey.VIEW)) {

			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.COMMENTS.getActionId(),
					SharingEntryPermissionDisplayActionKey.COMMENTS.getTitleKey(),
					SharingEntryPermissionDisplayActionKey.COMMENTS.getDescriptionKey(),
					true));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.COMMENTS.getActionId(),
					SharingEntryPermissionDisplayActionKey.COMMENTS.getTitleKey(),
					SharingEntryPermissionDisplayActionKey.COMMENTS.getDescriptionKey(),
					false));
		}

		if (sharingEntryActionKeys.contains(SharingEntryActionKey.VIEW)) {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.VIEW.getActionId(),
					SharingEntryPermissionDisplayActionKey.VIEW.getTitleKey(),
					SharingEntryPermissionDisplayActionKey.VIEW.getDescriptionKey(),
					true));
		}
		else {
			sharingEntryPermissionDisplays.add(
				new SharingEntryPermissionDisplay(
					SharingEntryPermissionDisplayActionKey.VIEW.getActionId(),
					SharingEntryPermissionDisplayActionKey.VIEW.getTitleKey(),
					SharingEntryPermissionDisplayActionKey.VIEW.getDescriptionKey(),
					false));
		}

		return sharingEntryPermissionDisplays;
	}

	public SharingEntryPermissionDisplay(
		String sharingEntryPermissionDisplayActionKeyActionId,
		String titleKey, String descriptionKey,
		boolean enabled) {

		_sharingEntryPermissionDisplayActionKeyActionId =
			sharingEntryPermissionDisplayActionKeyActionId;
		_titleKey = titleKey;
		_descriptionKey = descriptionKey;
		_enabled = enabled;
	}

	public String getSharingEntryPermissionDisplayActionKeyActionId() {
		return _sharingEntryPermissionDisplayActionKeyActionId;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	private final boolean _enabled;
	private final String _sharingEntryPermissionDisplayActionKeyActionId;
	private final String _titleKey;
	private final String _descriptionKey;

	public String getTitleKey() {
		return _titleKey;
	}

	public String getDescriptionKey() {
		return _descriptionKey;
	}
}