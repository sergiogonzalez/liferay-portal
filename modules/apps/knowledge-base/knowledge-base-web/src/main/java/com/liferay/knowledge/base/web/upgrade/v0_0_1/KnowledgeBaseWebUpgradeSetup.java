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

package com.liferay.knowledge.base.web.upgrade.v0_0_1;

import com.liferay.portal.upgrade.v7_0_0.UpgradeModules;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Roberto Díaz
 */
public class KnowledgeBaseWebUpgradeSetup extends UpgradeModules {

	@Override
	public String[] getBundleSymbolicNames() {
		return new String[] {"com.liferay.knowledge.base.web"};
	}

	@Override
	public String[][] getConvertedLegacyModules() {
		return new String[0][0];
	}

	@Override
	protected void updateExtractedModules() throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"select servletContextName from Release_ where" +
					" servletContextName = ?")) {

			ps.setString(1, getBundleSymbolicNames()[0]);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					super.updateExtractedModules();
				}
			}
		}
	}

}