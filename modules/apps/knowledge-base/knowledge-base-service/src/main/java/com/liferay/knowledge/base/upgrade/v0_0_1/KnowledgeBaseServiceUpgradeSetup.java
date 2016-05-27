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

package com.liferay.knowledge.base.upgrade.v0_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.upgrade.v7_0_0.UpgradeModules;
import com.liferay.portal.upgrade.v7_0_0.UpgradeRelease;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Roberto Díaz
 */
public class KnowledgeBaseServiceUpgradeSetup {

	public void upgrade() throws UpgradeException {
		UpgradeModules upgradeModules = new UpgradeModules() {

			@Override
			public String[] getBundleSymbolicNames() {
				return new String[0];
			}

			@Override
			public String[][] getConvertedLegacyModules() {
				return new String[][] {
					{
						"knowledge-base-portlet",
						"com.liferay.knowledge.base.service", "KB"
					}
				};
			}

			@Override
			protected void updateConvertedLegacyModules()
				throws IOException, SQLException {

				try (PreparedStatement ps = connection.prepareStatement(
						"select servletContextName from Release_ where " +
							"servletContextName = ?")) {

					String newServletContextName =
						getConvertedLegacyModules()[0][1];

					ps.setString(1, newServletContextName);

					try (ResultSet rs = ps.executeQuery()) {
						if (!rs.next()) {
							super.updateConvertedLegacyModules();
						}
					}
				}
			}

		};

		upgradeModules.upgrade();

		UpgradeRelease upgradeRelease = new UpgradeRelease() {

			@Override
			protected void upgradeSchemaVersion() throws Exception {
				try (LoggingTimer loggingTimer = new LoggingTimer();
					PreparedStatement ps = connection.prepareStatement(
						"select distinct buildNumber from Release_ where " +
							"schemaVersion is null and servletContextName = " +
								"?")) {

					ps.setString(1, _SERVLET_CONTEXT_NAME);

					try (ResultSet rs = ps.executeQuery()) {
						if (rs.next()) {
							String buildNumber = rs.getString("buildNumber");

							String schemaVersion = toSchemaVersion(buildNumber);

							StringBuilder sb = new StringBuilder(8);

							sb.append("update Release_ set schemaVersion = '");
							sb.append(schemaVersion);
							sb.append("' where buildNumber = ");
							sb.append(buildNumber);
							sb.append(" and schemaVersion is null and ");
							sb.append("servletContextName = '");
							sb.append(_SERVLET_CONTEXT_NAME);
							sb.append(CharPool.APOSTROPHE);

							runSQL(sb.toString());
						}
					}
				}
			}

		};

		upgradeRelease.upgrade();
	}

	private static final String _SERVLET_CONTEXT_NAME =
		"com.liferay.knowledge.base.service";

}