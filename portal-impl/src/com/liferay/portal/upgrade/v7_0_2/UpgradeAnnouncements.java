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

package com.liferay.portal.upgrade.v7_0_2;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roberto Díaz
 */
public class UpgradeAnnouncements extends UpgradeProcess {

	protected void addResourcePermission(
			long companyId, String name, int scope, String primKey,
			long primKeyId, long roleId, long ownerId, long actionBitwiseValue,
			int viewActionId)
		throws Exception {

		PreparedStatement ps = null;

		try {
			long resourcePermissionId = increment(
				ResourcePermission.class.getName());

			StringBundler sb = new StringBundler(4);

			sb.append("insert into ResourcePermission (mvccVersion, ");
			sb.append("resourcePermissionId, companyId, name, scope, ");
			sb.append("primKey, primKeyId, roleId, ownerId, actionIds, ");
			sb.append("viewActionId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setLong(1, 0);
			ps.setLong(2, resourcePermissionId);
			ps.setLong(3, companyId);
			ps.setString(4, name);
			ps.setInt(5, scope);
			ps.setString(6, primKey);
			ps.setLong(7, primKeyId);
			ps.setLong(8, roleId);
			ps.setLong(9, ownerId);
			ps.setLong(10, actionBitwiseValue);
			ps.setInt(11, viewActionId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add resource permission " + name, e);
			}
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void deleteResourceAction(long resourceActionId)
		throws SQLException {

 		PreparedStatement ps = connection.prepareStatement(
			"delete from ResourceAction where resourceActionId = ?");

		ps.setLong(1, resourceActionId);

		ps.executeUpdate();
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeAlertsResourcePermission();
		upgradeAnnouncementsResourcePermission();
	}

	protected long getLayoutGroupId(String primKey) throws Exception {
		String layoutId = StringUtil.split(primKey, StringPool.UNDERLINE)[0];

		PreparedStatement ps = connection.prepareStatement(
			"select groupId from Layout where plid = " + layoutId);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return rs.getLong("groupId");
		}

		return 0;
	}

	protected void updateResourcePermission(
			long resourcePermissionId, long bitwiseValue)
		throws Exception {

		PreparedStatement ps = connection.prepareStatement(
			"update ResourcePermission set actionIds = ? where " +
				"resourcePermissionId = ?");

		ps.setLong(1, bitwiseValue);
		ps.setLong(2, resourcePermissionId);

		ps.executeUpdate();
	}

	protected void upgradeAlertsResourcePermission() throws Exception {
		upgradeResourcePermission(
			"com_liferay_announcements_web_portlet_AlertsPortlet");
	}

	protected void upgradeAnnouncementsResourcePermission() throws Exception {
		upgradeResourcePermission(
			"com_liferay_announcements_web_portlet_AnnouncementsPortlet");
	}

	protected void upgradeResourcePermission(String name) throws Exception {
		StringBundler sb1 = new StringBundler(4);

		sb1.append("select resourceActionId, bitwiseValue from ");
		sb1.append("ResourceAction where actionId = 'ADD_ENTRY' and name = '");
		sb1.append(name);
		sb1.append("'");

		StringBundler sb2 = new StringBundler(5);

		sb2.append("select resourcePermissionId, companyId, scope, primKey, ");
		sb2.append("primKeyId, roleId, ownerId, actionIds, viewActionId from ");
		sb2.append("ResourcePermission where name = '");
		sb2.append(name);
		sb2.append("'");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());

			ResultSet rs1 = ps1.executeQuery()) {

			long resourceActionId = 0;
			long bitwiseValue = 0;

			if (rs1.next()) {
				resourceActionId = rs1.getLong("resourceActionId");
				bitwiseValue = rs1.getLong("bitwiseValue");
			}
			else {
				return;
			}

			try (PreparedStatement ps2 = connection.prepareStatement(
					sb2.toString());

				ResultSet rs = ps2.executeQuery()) {

				while (rs.next()) {
					long resourcePermissionId = rs.getLong(
						"resourcePermissionId");
					long companyId = rs.getLong("companyId");
					int scope = rs.getInt("scope");
					String primKey = rs.getString("primKey");
					long primKeyId = rs.getLong("primKeyId");
					long roleId = rs.getLong("roleId");
					long ownerId = rs.getLong("ownerId");
					long actionIds = rs.getLong("actionIds");
					int viewActionId = rs.getInt("viewActionId");

					if ((bitwiseValue & actionIds) == 0) {
						continue;
					}

					if (primKey.contains("_LAYOUT_")) {
						long groupId = getLayoutGroupId(primKey);

						String groupRole = groupId + StringPool.COMMA + roleId;

						if (!_groupRoleList.contains(groupRole)) {
							addResourcePermission(
								companyId, "com.liferay.announcements", scope,
								String.valueOf(groupId), groupId, roleId,
								ownerId, 2, viewActionId);

							_groupRoleList.add(groupRole);
						}
					}
					else if (scope == ResourceConstants.SCOPE_COMPANY) {
						String companyRole =
							companyId + StringPool.COMMA + roleId;

						if (!_companyRoleList.contains(companyRole)) {
							addResourcePermission(
								companyId, "com.liferay.announcements", scope,
								primKey, primKeyId, roleId, ownerId, 2,
								viewActionId);

							_companyRoleList.add(companyRole);
						}
					}
					else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
						if (!_roleList.contains(roleId)) {
							addResourcePermission(
								companyId, "com.liferay.announcements", scope,
								primKey, primKeyId, roleId, ownerId, 2,
								viewActionId);

							_roleList.add(roleId);
						}
					}

					updateResourcePermission(
						resourcePermissionId, (actionIds - bitwiseValue));
				}
			}

			deleteResourceAction(resourceActionId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAnnouncements.class);

	private final List<String> _companyRoleList = new ArrayList<>();
	private final List<String> _groupRoleList = new ArrayList<>();
	private final List<Long> _roleList = new ArrayList<>();

}