/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Miguel Pastor
 */
public class UpgradeDLSync extends UpgradeProcess {

	protected void updateDlSync() throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(10);

			sb.append("SELECT DLFileEntry.fileEntryId as id, ");
			sb.append("DLFileEntry.groupId as groupId, DLFileEntry.companyId");
			sb.append(" as companyId, DLFileEntry.createDate as createDate, ");
			sb.append("DLFileEntry.folderId as parentFolderId, \"file\" as ");
			sb.append("type from DLFileEntry UNION ALL");
			sb.append("SELECT DLFolder.folderId as id, DLFolder.groupId as ");
			sb.append("groupId, DLFolder.companyId as companyId, ");
			sb.append("DLFolder.createDate as createDate, ");
			sb.append("DLFolder.parentFolderId as parentFolderId, ");
			sb.append("\"folder\" as type  from DLFolder");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long syncId = increment();
				long id = rs.getLong("id");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				Date createDate = rs.getDate("creationDate");
				long parentFolderId = rs.getLong("parentFolderId");
				String type = rs.getString("type");

				runSQL(
					"insert into DLSync (syncId, companyId, createDate, " +
						"modifiedDate, fileId, repositoryId, parentFolderId," +
							" event, type_) values (" + syncId + ", " +
								companyId + ", " + createDate + ", " +
									createDate + ", " + id + ", " + groupId +
										", " + parentFolderId + ", " +
											"\"add\", " + type + ")");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateDlSync();
	}

	public long increment() throws SystemException {
		DB db = DBFactoryUtil.getDB();

		return db.increment();
	}

}