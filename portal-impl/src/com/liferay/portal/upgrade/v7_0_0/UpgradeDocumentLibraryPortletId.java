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

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.util.UpgradePortletId;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cristina González
 */
@SuppressWarnings("deprecation")
public class UpgradeDocumentLibraryPortletId extends UpgradePortletId {

	protected void deleteDuplicateResourceActions() throws SQLException {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select actionId from ResourceAction where name = '" +
					_PORTLET_ID_DOCUMENT_LIBRARY + "'");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				try (PreparedStatement ps2 = connection.prepareStatement(
						"delete from ResourceAction where name = ? and " +
							"actionId = ?")) {

					ps2.setString(1, _PORTLET_ID_DL_DISPLAY);
					ps2.setString(2, rs.getString("actionId"));

					ps2.execute();
				}
			}
		}
	}

	protected void deleteDuplicateResourcePermissions() throws SQLException {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select companyId, scope, primKey from ResourcePermission " +
					"where name = '" + _PORTLET_ID_DOCUMENT_LIBRARY + "'");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				try (PreparedStatement ps2 = connection.prepareStatement(
						"delete from ResourcePermission where companyId = ? " +
							"and name = ? and scope = ? and primKey = ?")) {

					ps2.setLong(1, rs.getLong("companyId"));
					ps2.setString(2, _PORTLET_ID_DL_DISPLAY);
					ps2.setInt(3, rs.getInt("scope"));
					ps2.setString(4, rs.getString("primKey"));

					ps2.execute();
				}
			}
		}
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {_PORTLET_ID_DL_DISPLAY, _PORTLET_ID_DOCUMENT_LIBRARY}
		};
	}

	protected void updateDuplicatePortletPreferences() throws SQLException {
		StringBundler sb = new StringBundler(6);

		sb.append("select portletPreferencesId, plid, portletId from ");
		sb.append("portletPreferences where portletId = '");
		sb.append(_PORTLET_ID_DL_DISPLAY);
		sb.append("' or portletId = '");
		sb.append(_PORTLET_ID_DOCUMENT_LIBRARY);
		sb.append("'");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			ResultSet rs1 = ps1.executeQuery();
			PreparedStatement ps2 = connection.prepareStatement(
				"select typeSettings from layout where plid = ?");
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update layout set typeSettings = ? where plid = ?");
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update portletpreferences set portletId = ?" +
						"where portletPreferencesId = ?")) {

			Map<String, String> plidsEncountered = new HashMap();

			while (rs1.next()) {
				String oldPortletId = rs1.getString("portletId");
				String plid = rs1.getString("plid");

				sb = new StringBundler(3);

				sb.append(oldPortletId);
				sb.append(_INSTANCE_SEPARATOR);
				sb.append(StringUtil.randomString(12));

				ps2.setString(1, plid);

				ResultSet rs2 = ps2.executeQuery();

				if (rs2.next()) {
					String typeSettings = null;

					if (plidsEncountered.containsKey(plid)) {
						typeSettings = plidsEncountered.get(plid);
					}
					else {
						typeSettings = rs2.getString("typeSettings");
					}

					typeSettings = StringUtil.replace(
						typeSettings, oldPortletId, sb.toString());

					plidsEncountered.put(plid, typeSettings);

					ps3.setString(1, typeSettings);

					ps3.setString(2, plid);

					ps3.addBatch();
				}

				ps4.setString(1, sb.toString());
				ps4.setInt(2, rs1.getInt("portletPreferencesId"));

				ps4.addBatch();
			}

			ps3.executeBatch();
			ps4.executeBatch();
		}
	}

	protected void updateDuplicateResourcePermissions() throws SQLException {
		StringBundler sb = new StringBundler(6);

		sb.append("select resourcePermissionId, name, primKey from ");
		sb.append("resourcePermission where name = '");
		sb.append(_PORTLET_ID_DL_DISPLAY);
		sb.append("' OR name = '");
		sb.append(_PORTLET_ID_DOCUMENT_LIBRARY);
		sb.append("'");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update resourcePermission set primKey = ?" +
						"where resourcePermissionId = ?")) {

			Map<String, String> primKeysEncountered = new HashMap<>();

			while (rs.next()) {
				String oldPrimKey = rs.getString("primKey");

				if (oldPrimKey.contains(PortletConstants.LAYOUT_SEPARATOR)) {
					sb = new StringBundler(3);

					sb.append(oldPrimKey);
					sb.append(_INSTANCE_SEPARATOR);

					if (primKeysEncountered.containsKey(oldPrimKey)) {
						sb.append(primKeysEncountered.get(oldPrimKey));
					}
					else {
						String instanceId = StringUtil.randomString(12);

						primKeysEncountered.put(oldPrimKey, instanceId);

						sb.append(instanceId);
					}

					String newPrimKey = sb.toString();

					ps2.setString(1, newPrimKey);

					ps2.setInt(2, rs.getInt("resourcePermissionId"));

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
	}

	@Override
	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		try {
			runSQL(
				"delete from Portlet where portletId = '" +
					_PORTLET_ID_DL_DISPLAY + "'");

			deleteDuplicateResourceActions();
			deleteDuplicateResourcePermissions();

			updateDuplicatePortletPreferences();
			updateDuplicateResourcePermissions();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		super.updatePortlet(oldRootPortletId, newRootPortletId);
	}

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

	private static final String _PORTLET_ID_DL_DISPLAY = "110";

	private static final String _PORTLET_ID_DOCUMENT_LIBRARY = "20";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibraryPortletId.class);

}