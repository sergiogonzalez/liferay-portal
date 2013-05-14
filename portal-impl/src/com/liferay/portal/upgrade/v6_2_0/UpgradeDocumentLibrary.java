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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.upgrade.v6_2_0.util.DLFileEntryTypeTable;
import com.liferay.portal.upgrade.v6_2_0.util.DLFileRankTable;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dennis Ju
 * @author Mate Thurzo
 * @author Alexander Chow
 * @author Roberto Díaz
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected static void updateDLFileEntryTypes() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileEntryTypeId, name, description" +
					" from DLFileEntryType");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryTypeId = rs.getLong("fileEntryTypeId");
				String name = rs.getString("name");
				String description = rs.getString("description");

				String fileEntryKey = name.trim().toUpperCase();

				if (fileEntryTypeId ==
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

					name = DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT;

					fileEntryKey = name.trim().toUpperCase();

					updateDLFileEntryType(
						fileEntryTypeId, fileEntryKey, name, StringPool.BLANK);
				}
				else {
					updateDLFileEntryType(
						fileEntryTypeId, fileEntryKey, name, description);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteChecksumDirectory() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select distinct companyId from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");

				try {
					DLStoreUtil.deleteDirectory(companyId, 0, "checksum");
				}
				catch (Exception e) {
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteTempDirectory() {
		try {
			DLStoreUtil.deleteDirectory(0, 0, "liferay_temp/");
		}
		catch (Exception e) {
		}
	}

	@Override
	protected void doUpgrade() throws Exception {

		// DLFileRank

		try {
			runSQL("alter table DLFileRank add userName STRING");

			runSQL("alter table DLFileRank add modifiedDate DATE");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DLFileRankTable.TABLE_NAME, DLFileRankTable.TABLE_COLUMNS,
				DLFileRankTable.TABLE_SQL_CREATE,
				DLFileRankTable.TABLE_SQL_ADD_INDEXES);
		}

		updateFileRanks();

		// Checksum directory

		deleteChecksumDirectory();

		// Temp directory

		deleteTempDirectory();

		// DLFileEntryType

		try {
			runSQL("alter table DLFileEntryType add fileEntryTypeKey STRING");

			runSQL("alter table DLFileEntryType modify name STRING");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DLFileEntryTypeTable.TABLE_NAME,
				DLFileEntryTypeTable.TABLE_COLUMNS,
				DLFileEntryTypeTable.TABLE_SQL_CREATE,
				DLFileEntryTypeTable.TABLE_SQL_ADD_INDEXES);
		}

		updateDLFileEntryTypes();
	}

	protected String getUserName(long userId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select firstName, middleName, lastName from User_ where " +
					"userId = ?");

			ps.setLong(1, userId);

			rs = ps.executeQuery();

			if (rs.next()) {
				String firstName = rs.getString("firstName");
				String middleName = rs.getString("middleName");
				String lastName = rs.getString("lastName");

				FullNameGenerator fullNameGenerator =
					FullNameGeneratorFactory.getInstance();

				return fullNameGenerator.getFullName(
					firstName, middleName, lastName);
			}

			return StringPool.BLANK;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateFileRank(
			long fileRankId, long userId, Timestamp modifiedDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DLFileRank set userName = ?, modifiedDate = ? where " +
					"fileRankId = ?");

			ps.setString(1, getUserName(userId));
			ps.setTimestamp(2, modifiedDate);
			ps.setLong(3, fileRankId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateFileRanks() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileRankId, userId, createDate from DLFileRank");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileRankId = rs.getLong("fileRankId");
				long userId = rs.getLong("userId");
				Timestamp createDate = rs.getTimestamp("createDate");

				updateFileRank(fileRankId, userId, createDate);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static void updateDLFileEntryType(
			long fileEntryTypeId, String fileEntryTypeKey, String name,
			String description)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DLFileEntryType set fileEntryTypeKey = ?, name = ?, " +
					"description = ? where fileEntryTypeId = ?");

			ps.setString(1, fileEntryTypeKey);

			Locale locale = LocaleUtil.getDefault();

			// Localized name

			Map<Locale, String> nameMap = new HashMap<Locale, String>();

			nameMap.put(locale, name);

			name = LocalizationUtil.updateLocalization(
				nameMap, StringPool.BLANK, "Name",
				LocaleUtil.toLanguageId(locale));

			ps.setString(2, name);

			// Localized description

			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

			descriptionMap.put(locale, description);

			description = LocalizationUtil.updateLocalization(
				descriptionMap, StringPool.BLANK, "Description",
				LocaleUtil.toLanguageId(locale));

			ps.setString(3, description);
			ps.setLong(4, fileEntryTypeId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}