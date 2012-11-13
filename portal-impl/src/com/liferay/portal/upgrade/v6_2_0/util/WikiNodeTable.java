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

package com.liferay.portal.upgrade.v6_2_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class WikiNodeTable {

	public static final String TABLE_NAME = "WikiNode";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"nodeId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"lastPostDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final String TABLE_SQL_CREATE = "create table WikiNode (uuid_ VARCHAR(75) null,nodeId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description STRING null,lastPostDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table WikiNode";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_5D6FE3F0 on WikiNode (companyId)",
		"create index IX_B54332D6 on WikiNode (companyId, status)",
		"create index IX_B480A672 on WikiNode (groupId)",
		"create unique index IX_920CD8B1 on WikiNode (groupId, name)",
		"create index IX_23325358 on WikiNode (groupId, status)",
		"create index IX_6C112D7C on WikiNode (uuid_)",
		"create index IX_E0E6D12C on WikiNode (uuid_, companyId)",
		"create unique index IX_7609B2AE on WikiNode (uuid_, groupId)"
	};

}