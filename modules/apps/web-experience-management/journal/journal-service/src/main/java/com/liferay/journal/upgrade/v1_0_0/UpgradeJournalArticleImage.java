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

package com.liferay.journal.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Inácio Nery
 */
public class UpgradeJournalArticleImage extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateJournalArticleImages();
	}

	protected void updateJournalArticle(long articleImageId, String elName)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update JournalArticleImage set elName = ? where " +
					"articleImageId = ?");

			ps.setString(1, elName);
			ps.setLong(2, articleImageId);

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void updateJournalArticleImages() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select articleImageId, elName from JournalArticleImage");

			rs = ps.executeQuery();

			while (rs.next()) {
				long articleImageId = rs.getLong(1);
				String elName = rs.getString(2);

				int lastIndexOf = elName.lastIndexOf(StringPool.UNDERLINE);

				if (lastIndexOf > 0) {
					elName = elName.substring(0, lastIndexOf);
				}

				updateJournalArticle(articleImageId, elName);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

}