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
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Roberto Díaz
 */
public class UpgradeAnnouncements extends UpgradeProcess {

	protected void addResourceAction() {
		PreparedStatement ps = null;

		try {
			long resourceActionId = increment(ResourceAction.class.getName());

			StringBundler sb = new StringBundler(4);

			sb.append("insert into ResourceAction (mvccVersion, ");
			sb.append("resourceActionId, name, actionId, bitwiseValue)");
			sb.append("values (?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setLong(1, 0);
			ps.setLong(2, resourceActionId);
			ps.setString(3, "com.liferay.announcements");
			ps.setString(4, "ADD_ENTRY");
			ps.setLong(5, _NEW_ADD_ENTRY_BITWISE_VALUE);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add resource action", e);
			}
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void addResourcePermission(
			long companyId, String name, int scope, String primKey,
			long primKeyId, long roleId, long ownerId, long actionBitwiseValue)
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
			ps.setInt(11, 0);

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
		addResourceAction();

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
		sb2.append("primKeyId, roleId, ownerId, actionIds from ");
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

					if ((bitwiseValue & actionIds) == 0) {
						continue;
					}

					if (primKey.contains("_LAYOUT_")) {
						long groupId = getLayoutGroupId(primKey);

						String layoutRoleKey = _getKey(
							companyId, scope, String.valueOf(groupId), roleId);

						if (!_groupRoleSet.contains(layoutRoleKey)) {
							addResourcePermission(
								companyId, "com.liferay.announcements", scope,
								String.valueOf(groupId), groupId, roleId,
								ownerId, _NEW_ADD_ENTRY_BITWISE_VALUE);

							_groupRoleSet.add(layoutRoleKey);
						}
					}
					else if (scope == ResourceConstants.SCOPE_COMPANY) {
						String companyRoleKey = _getKey(
							companyId, ResourceConstants.SCOPE_COMPANY, primKey,
							roleId);

						if (!_companyRoleSet.contains(companyRoleKey)) {
							addResourcePermission(
								companyId, "com.liferay.announcements", scope,
								primKey, primKeyId, roleId, ownerId,
								_NEW_ADD_ENTRY_BITWISE_VALUE);

							_companyRoleSet.add(companyRoleKey);
						}
					}
					else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
						String groupTemplateRoleKey = _getKey(
							companyId, ResourceConstants.SCOPE_GROUP_TEMPLATE,
							primKey, roleId);

						if (!_roleSet.contains(groupTemplateRoleKey)) {
							addResourcePermission(
								companyId, "com.liferay.announcements", scope,
								primKey, primKeyId, roleId, ownerId,
								_NEW_ADD_ENTRY_BITWISE_VALUE);

							_roleSet.add(groupTemplateRoleKey);
						}
					}

					updateResourcePermission(
						resourcePermissionId, (actionIds - bitwiseValue));
				}
			}

			deleteResourceAction(resourceActionId);
		}
	}

	private String _getKey(
		long companyId, int scope, String primKey, long roleId) {

		StringBundler sb = new StringBundler(7);

		sb.append(companyId);
		sb.append(StringPool.PERIOD);
		sb.append(scope);
		sb.append(StringPool.PERIOD);
		sb.append(primKey);
		sb.append(StringPool.PERIOD);
		sb.append(roleId);

		return sb.toString();
	}

	private static final int _NEW_ADD_ENTRY_BITWISE_VALUE = 2;

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAnnouncements.class);

	private final Set<String> _companyRoleSet = new HashSet<>();
	private final Set<String> _groupRoleSet = new HashSet<>();
	private final Set<String> _roleSet = new HashSet<>();

}