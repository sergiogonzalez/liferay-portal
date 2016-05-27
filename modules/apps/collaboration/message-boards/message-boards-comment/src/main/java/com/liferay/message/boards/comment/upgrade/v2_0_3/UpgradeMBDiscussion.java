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

package com.liferay.message.boards.comment.upgrade.v2_0_3;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Adolfo Pérez
 */
public class UpgradeMBDiscussion extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement s = connection.createStatement()) {
			try (ResultSet rs = s.executeQuery(
					"select MBDiscussion.discussionId, MBThread.threadId, " +
						"MBMessage.messageId from MBDiscussion inner join " +
							"MBThread on MBDiscussion.threadId = " +
								"MBThread.threadId inner join MBMessage on " +
									"MBMessage.messageId = " +
										"MBThread.rootMessageId where " +
											"MBThread.messageCount = 1");
				PreparedStatement ps1 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from MBDiscussion where discussionId = ?");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, "delete from MBThread where threadId = ?");
				PreparedStatement ps3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from MBMessage where messageId = ?")) {

				/*
				 * TODO: We also need to delete MBMessage assets. We should also
				 * discuss where this upgrade should be located: here in the
				 * module or in the core MB upgrades.
				 */

				while (rs.next()) {
					ps1.setLong(1, rs.getLong(1));
					ps1.addBatch();

					ps2.setLong(1, rs.getLong(2));
					ps2.addBatch();

					ps3.setLong(1, rs.getLong(3));
					ps3.addBatch();
				}

				ps1.executeBatch();
				ps2.executeBatch();
				ps3.executeBatch();
			}
		}
	}

}