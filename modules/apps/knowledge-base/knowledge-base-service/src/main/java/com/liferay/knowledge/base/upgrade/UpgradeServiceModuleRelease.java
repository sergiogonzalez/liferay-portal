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

package com.liferay.knowledge.base.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.CharPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adolfo Pérez
 */
class UpgradeServiceModuleRelease extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select buildNumber from Release_ where " +
					"servletContextName = ?")) {

			ps.setString(1, _OLD_SERVLET_CONTEXT_NAME);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String buildNumber = rs.getString("buildNumber");

					_updateRelease(_toSchemaVersion(buildNumber));
				}
			}
		}
	}

	private void _updateRelease(String schemaVersion) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"update Release_ set servletContextName = ?, " +
					"schemaVersion = ? where servletContextName = ?")) {

			ps.setString(1, _NEW_SERVLET_CONTEXT_NAME);
			ps.setString(2, schemaVersion);
			ps.setString(3, _OLD_SERVLET_CONTEXT_NAME);

			ps.execute();
		}
	}

	private String _toSchemaVersion(String buildNumber) {
		StringBuilder sb = new StringBuilder(2 * buildNumber.length());

		for (int i = 0; i < buildNumber.length(); i++) {
			sb.append(buildNumber.charAt(i));
			sb.append(CharPool.PERIOD);
		}

		if (buildNumber.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private static final String _NEW_SERVLET_CONTEXT_NAME =
		"com.liferay.knowledge.base.service";

	private static final String _OLD_SERVLET_CONTEXT_NAME =
		"knowledge-base-portlet";

}