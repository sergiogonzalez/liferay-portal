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

package com.liferay.portlet.wiki;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.settings.Settings;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ContentUtil;
import com.liferay.util.RSSUtil;

import java.io.IOException;

import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class WikiSettings {

	public WikiSettings(Settings settings, long defaultDisplayStyleGroupId) {
		_settings = settings;
		_defaultDisplayStyleGroupId = defaultDisplayStyleGroupId;
	}

	public String getDisplayStyle() {
		return getValue("displayStyle", StringPool.BLANK);
	}

	public long getDisplayStyleGroupId() {
		String displayStyleGroupId = _settings.getValue(
			"displayStyleGroupId", null);

		return GetterUtil.getLong(
			displayStyleGroupId, _defaultDisplayStyleGroupId);
	}

	public String getEmailFromAddress() {
		return getValue(
			"emailFromAddress", PropsValues.WIKI_EMAIL_FROM_ADDRESS);
	}

	public String getEmailFromName() {
		return getValue("emailFromName", PropsValues.WIKI_EMAIL_FROM_NAME);
	}

	public String getEmailPageAddedBody() {
		String defaultValue =
			ContentUtil.get(
				PropsUtil.get(PropsKeys.WIKI_EMAIL_PAGE_ADDED_BODY));

		return getValue("emailPageAddedBody", defaultValue);
	}

	public String getEmailPageAddedSubject() {
		String defaultValue =
			ContentUtil.get(
				PropsUtil.get(PropsKeys.WIKI_EMAIL_PAGE_ADDED_SUBJECT));

		return getValue("emailPageAddedSubject", defaultValue);
	}

	public String getEmailPageUpdatedBody() {
		String defaultValue =
			ContentUtil.get(
				PropsUtil.get(PropsKeys.WIKI_EMAIL_PAGE_UPDATED_BODY));

		return getValue("emailPageUpdatedBody", defaultValue);
	}

	public String getEmailPageUpdatedSubject() {
		String defaultValue =
			ContentUtil.get(
				PropsUtil.get(PropsKeys.WIKI_EMAIL_PAGE_UPDATED_SUBJECT));

		return getValue("emailPageUpdatedSubject", defaultValue);
	}

	public String[] getHiddenNodes() {
		String hiddenNodes = getValue("hiddenNodes", StringPool.BLANK);

		return StringUtil.split(hiddenNodes);
	}

	public int getRSSDelta() {
		String rssDelta = getValue("rssDelta", null);

		return GetterUtil.getInteger(rssDelta, SearchContainer.DEFAULT_DELTA);
	}

	public String getRSSDisplayStyle() {
		return getValue("rssDisplayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);
	}

	public String getRSSFeedType() {
		return getValue("rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
	}

	public String[] getVisibleNodes() {
		String visibleNodes = getValue("visibleNodes", StringPool.BLANK);

		return StringUtil.split(visibleNodes);
	}

	public boolean isCommentRatingsEnabled() {
		String enableCommentRatings = getValue(
			"enableCommentRatings", Boolean.TRUE.toString());

		return Boolean.valueOf(enableCommentRatings);
	}

	public boolean isCommentsEnabled() {
		if (!PropsValues.WIKI_PAGE_COMMENTS_ENABLED) {
			return false;
		}

		String enableComments = getValue(
			"enableComments", Boolean.TRUE.toString());

		return Boolean.valueOf(enableComments);
	}

	public boolean isEmailPageAddedEnabled() {
		String emailPageAddedEnabled =
			getValue("emailPageAddedEnabled",
			PropsUtil.get(PropsKeys.WIKI_EMAIL_PAGE_ADDED_ENABLED));

		return GetterUtil.getBoolean(emailPageAddedEnabled);
	}

	public boolean isEmailPageUpdatedEnabled() {
		String emailPageUpdatedEnabled =
			getValue("emailPageUpdatedEnabled",
			PropsUtil.get(PropsKeys.WIKI_EMAIL_PAGE_UPDATED_ENABLED));

		return GetterUtil.getBoolean(emailPageUpdatedEnabled);
	}

	public boolean isPageRatingsEnabled() {
		if (!PropsValues.WIKI_PAGE_RATINGS_ENABLED) {
			return false;
		}

		String enablePageRatings = getValue(
			"enablePageRatings", Boolean.TRUE.toString());

		return Boolean.valueOf(enablePageRatings);
	}

	public boolean isRelatedAssetsEnabled() {
		String enableRelatedAssets = getValue(
			"enableRelatedAssets", Boolean.TRUE.toString());

		return Boolean.valueOf(enableRelatedAssets);
	}

	public boolean isRSSEnabled() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		String enableRSS = getValue("enableRss", Boolean.TRUE.toString());

		return Boolean.valueOf(enableRSS);
	}

	public void setHiddenNodes(String[] hiddenNodes) {
		setValue("hiddenNodes", StringUtil.merge(hiddenNodes));
	}

	public void setVisibleNodes(String[] visibleNodes) {
		setValue("visibleNodes", StringUtil.merge(visibleNodes));
	}

	public void store() throws IOException, ValidatorException {
		_settings.store();
	}

	protected String getFallbackKey(String key) {
		String fallbackKey = null;

		if (key.equals("emailFromAddress")) {
			fallbackKey = PropsKeys.ADMIN_EMAIL_FROM_ADDRESS;
		}
		else if (key.equals("emailFromName")) {
			fallbackKey = PropsKeys.ADMIN_EMAIL_FROM_NAME;
		}

		return fallbackKey;
	}

	protected String getValue(String key, String defaultValue) {
		String value = _settings.getValue(key, defaultValue);

		if (Validator.isNotNull(value)) {
			return value;
		}

		String fallbackKey = getFallbackKey(key);

		if (fallbackKey != null) {
			return _settings.getValue(fallbackKey, value);
		}

		return value;
	}

	protected void setValue(String key, String value) {
		_settings.setValue(key, value);
	}

	private long _defaultDisplayStyleGroupId;
	private Settings _settings;

}