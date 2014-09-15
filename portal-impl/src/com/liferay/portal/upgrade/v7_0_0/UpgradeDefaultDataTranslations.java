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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class UpgradeDefaultDataTranslations extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select companyId, groupId from Group_ where classNameId = ?");

			long classNameId = PortalUtil.getClassNameId(Company.class);

			ps.setLong(1, classNameId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong(1);
				long groupId = rs.getLong(2);

				upgradeTranslations(companyId, groupId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected DLFileEntryTypeData getDLFileEntryTypeData(
			long groupId, String dlFileEntryTypeKey)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileEntryTypeId, name, description from " +
					"DLFileEntryType where groupId = ? and fileEntryTypeKey " +
					"= ?");

			ps.setLong(1, groupId);
			ps.setString(2, dlFileEntryTypeKey);

			rs = ps.executeQuery();

			if (!rs.next()) {
				return null;
			}

			long fileEntryTypeId = rs.getLong(1);
			String name = rs.getString(2);
			String description = rs.getString(3);

			if (rs.next()) {
				throw new IllegalStateException(
					String.format(
						"Found at least two rows for groupId = %s and " +
							"fileEntryTypeKey = %s on table DLFileEntryType; " +
							"expected 1 row",
						groupId, dlFileEntryTypeKey));
			}

			DLFileEntryTypeData dlFileEntryTypeData = new DLFileEntryTypeData(
				fileEntryTypeId, name, description);

			return dlFileEntryTypeData;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeDLFileEntryType(
			DLFileEntryTypeData dlFileEntryTypeData, Map<Locale,
			String> nameMap, Map<Locale, String> descriptionMap,
			Locale defaultLocale)
		throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DLFileEntryType set name = ?, description = ? where " +
					"fileEntryTypeId = ?");

			String languageId = LanguageUtil.getLanguageId(defaultLocale);

			String name = LocalizationUtil.updateLocalization(
				nameMap, dlFileEntryTypeData.getName(), "Name", languageId);

			String description = LocalizationUtil.updateLocalization(
				descriptionMap, dlFileEntryTypeData.getDescription(),
				"Description", languageId);

			ps.setString(1, name);
			ps.setString(2, description);
			ps.setLong(3, dlFileEntryTypeData.getDlFileEntryTypeId());

			int rowCount = ps.executeUpdate();

			if (rowCount != 1) {
				throw new IllegalStateException(
					String.format(
						"Updated %s rows with fileEntryTypeId = %s in table" +
							"DLFileEntryTypeId; expected 1 row",
						rowCount, dlFileEntryTypeData.getDlFileEntryTypeId()));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void upgradeGroupDLFileEntryTypes(
			long companyId, long groupId, String nameLanguageKey,
			String dlFileEntryTypeKey)
		throws SQLException {

		DLFileEntryTypeData dlFileEntryTypeData = getDLFileEntryTypeData(
			groupId, dlFileEntryTypeKey);

		if (dlFileEntryTypeData == null) {
			return;
		}

		Map<Locale, String> descriptionMap =
			dlFileEntryTypeData.getDescriptionMap();
		Map<Locale, String> nameMap = dlFileEntryTypeData.getNameMap();

		boolean needsUpdate = false;

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			UpgradeProcessUtil.getDefaultLanguageId(companyId));
		String defaultValue = LanguageUtil.get(defaultLocale, nameLanguageKey);

		for (Locale locale : LanguageUtil.getSupportedLocales()) {
			String localizedValue = LanguageUtil.get(locale, nameLanguageKey);

			if (!locale.equals(defaultLocale) &&
				localizedValue.equals(defaultValue)) {

				continue;
			}

			String description = descriptionMap.get(locale);

			if (description == null) {
				descriptionMap.put(locale, localizedValue);

				needsUpdate = true;
			}

			String name = nameMap.get(locale);

			if (name == null) {
				nameMap.put(locale, localizedValue);

				needsUpdate = true;
			}
		}

		if (needsUpdate) {
			upgradeDLFileEntryType(
				dlFileEntryTypeData, nameMap, descriptionMap, defaultLocale);
		}
	}

	protected void upgradeTranslations(long companyId, long groupId)
		throws PortalException {

		try {
			for (Map.Entry<String, String> nameAndKey :
					_DEFAULT_FILE_ENTRY_TYPE_MAP.entrySet()) {

				upgradeGroupDLFileEntryTypes(
					companyId, groupId, nameAndKey.getKey(),
					nameAndKey.getValue());
			}
		}
		catch (SQLException sqle) {
			throw new UpgradeException(sqle);
		}
	}

	protected class DLFileEntryTypeData {

		public DLFileEntryTypeData(
			long dlFileEntryTypeId, String name, String description) {

			_description = description;
			_dlFileEntryTypeId = dlFileEntryTypeId;
			_name = name;
		}

		public String getDescription() {
			return _description;
		}

		public Map<Locale, String> getDescriptionMap() {
			return LocalizationUtil.getLocalizationMap(_description);
		}

		public long getDlFileEntryTypeId() {
			return _dlFileEntryTypeId;
		}

		public String getName() {
			return _name;
		}

		public Map<Locale, String> getNameMap() {
			return LocalizationUtil.getLocalizationMap(_name);
		}

		private String _description;
		private long _dlFileEntryTypeId;
		private String _name;

	}

	private static Map<String, String> _DEFAULT_FILE_ENTRY_TYPE_MAP;

	static {
		_DEFAULT_FILE_ENTRY_TYPE_MAP = new HashMap<String, String>();

		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_CONTRACT,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT);
		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_MARKETING_BANNER,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_MARKETING_BANNER);
		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_ONLINE_TRAINING,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_ONLINE_TRAINING);
		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_SALES_PRESENTATION,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_SALES_PRESENTATION);
	}

}