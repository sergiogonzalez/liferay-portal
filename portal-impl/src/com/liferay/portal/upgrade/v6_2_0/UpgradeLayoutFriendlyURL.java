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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.upgrade.v6_2_0.util.LayoutFriendlyURLTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Sergio González
 */
public class UpgradeLayoutFriendlyURL extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			updateLayoutFriendlyURLs();
		}
		catch (SQLException sqle) {
			upgradeTable(
				LayoutFriendlyURLTable.TABLE_NAME,
				LayoutFriendlyURLTable.TABLE_COLUMNS,
				LayoutFriendlyURLTable.TABLE_SQL_CREATE,
				LayoutFriendlyURLTable.TABLE_SQL_ADD_INDEXES);
		}
	}

	protected void updateLayoutFriendlyURL(
			long groupId, long companyId, long plid, boolean privateLayout,
			String friendlyURL)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into LayoutFriendlyURL (uuid_, ");
			sb.append("layoutFriendlyURLId, groupId, companyId, plid, ");
			sb.append("privateLayout, friendlyURL, languageId) values (?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?)");

			ps = con.prepareStatement(sb.toString());

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, increment());
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, plid);
			ps.setBoolean(6, privateLayout);
			ps.setString(7, friendlyURL);
			ps.setString(8, LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateLayoutFriendlyURLs() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId, companyId, plid, privateLayout, friendlyURL " +
					"from Layout where friendlyURL != '/manage'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long plid = rs.getLong("plid");
				boolean privateLayout = rs.getBoolean("privateLayout");
				String friendlyURL = rs.getString("friendlyURL");

				updateLayoutFriendlyURL(
					groupId, companyId, plid, privateLayout, friendlyURL);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}