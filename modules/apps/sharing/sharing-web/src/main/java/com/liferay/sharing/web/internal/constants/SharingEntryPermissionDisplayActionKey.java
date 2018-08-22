package com.liferay.sharing.web.internal.constants;

import com.liferay.sharing.constants.SharingEntryActionKey;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum SharingEntryPermissionDisplayActionKey {

		COMMENTS(
			"COMMENTS", "comments",
			"collaborators-can-comment-on-the-document",
			SharingEntryActionKey.ADD_DISCUSSION, SharingEntryActionKey.VIEW),
		UPDATE(
			"UPDATE", "edition",
			"collaborators-can-view-comment-update-the-document",
			SharingEntryActionKey.ADD_DISCUSSION, SharingEntryActionKey.UPDATE,
			SharingEntryActionKey.VIEW),
		VIEW(
			"VIEW", "view", "collaborators-can-only-view-the-document",
			SharingEntryActionKey.VIEW);

	private final String _descriptionKey;
	private final String _titleKey;

	public static SharingEntryPermissionDisplayActionKey parseFromActionId(
			String actionId) {

			if (Objects.equals(COMMENTS.getActionId(), actionId)) {
				return COMMENTS;
			}
			else if (Objects.equals(UPDATE.getActionId(), actionId)) {
				return UPDATE;
			}
			else if (Objects.equals(VIEW.getActionId(), actionId)) {
				return VIEW;
			}

			throw new IllegalArgumentException("Invalid action id " + actionId);
		}

		public String getActionId() {
			return _actionId;
		}

		public List<SharingEntryActionKey> getSharingEntryActionKeys() {
			return _sharingEntryActionKeys;
		}

		private SharingEntryPermissionDisplayActionKey(
			String actionId, String titleKey, String descriptionKey,
			SharingEntryActionKey... sharingEntryActionKeys) {

			_actionId = actionId;
			_titleKey = titleKey;
			_descriptionKey = descriptionKey;
			_sharingEntryActionKeys = Arrays.asList(sharingEntryActionKeys);
		}

		private final String _actionId;
		private final List<SharingEntryActionKey> _sharingEntryActionKeys;

	public String getDescriptionKey() {
		return _descriptionKey;
	}

	public String getTitleKey() {
		return _titleKey;
	}
}
