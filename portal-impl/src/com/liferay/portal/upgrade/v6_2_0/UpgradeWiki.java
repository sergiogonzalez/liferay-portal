/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v6_2_0.util.WikiNodeTable;

import java.sql.SQLException;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeWiki extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type WikiNode name varchar(255) null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				WikiNodeTable.TABLE_NAME, WikiNodeTable.TABLE_COLUMNS,
				WikiNodeTable.TABLE_SQL_CREATE,
				WikiNodeTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}