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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Adolfo Pérez
 */
public class UpgradeSocialActivitySets extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		int count = getSocialActivitySetsCount();

		if (count > 0) {
			return;
		}

		insertSocialActivitySets();

		updateSocialActivityForeignKeys();
	}

	protected int getSocialActivitySetsCount() throws Exception {
		try (Statement s = connection.createStatement()) {
			String query = "SELECT COUNT(activitySetId) FROM SocialActivitySet";

			try (ResultSet rs = s.executeQuery(query)) {
				if (rs.next()) {
					return rs.getInt(1);
				}

				return 0;
			}
		}
	}

	protected void insertSocialActivitySets() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("INSERT INTO SocialActivitySet ");
		sb.append("SELECT activityId AS activitySetId, groupId, companyId, ");
		sb.append("userId, createDate, createDate AS modifiedDate, ");
		sb.append("classNameId, classPK, type_, extraData, ");
		sb.append("1 as activityCount FROM SocialActivity ");
		sb.append("WHERE mirrorActivityId = 0");

		runSQL(sb.toString());
	}

	protected void updateSocialActivityForeignKeys() throws Exception {
		try (Statement s = connection.createStatement()) {
			s.execute("UPDATE SocialActivity SET activitySetId = activityId");
		}
	}

}