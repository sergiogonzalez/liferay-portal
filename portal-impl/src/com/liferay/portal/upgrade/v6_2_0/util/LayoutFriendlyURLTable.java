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

package com.liferay.portal.upgrade.v6_2_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class LayoutFriendlyURLTable {

	public static final String TABLE_NAME = "LayoutFriendlyURL";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"layoutFriendlyURLId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"plid", Types.BIGINT},
		{"privateLayout", Types.BOOLEAN},
		{"friendlyURL", Types.VARCHAR},
		{"languageId", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table LayoutFriendlyURL (uuid_ VARCHAR(75) null,layoutFriendlyURLId LONG not null primary key,groupId LONG,companyId LONG,plid LONG,privateLayout BOOLEAN,friendlyURL VARCHAR(255) null,languageId VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table LayoutFriendlyURL";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_EAB317C8 on LayoutFriendlyURL (companyId)",
		"create index IX_742EF04A on LayoutFriendlyURL (groupId)",
		"create index IX_CA713461 on LayoutFriendlyURL (groupId, privateLayout, friendlyURL)",
		"create unique index IX_A6FC2B28 on LayoutFriendlyURL (groupId, privateLayout, friendlyURL, languageId)",
		"create index IX_83AE56AB on LayoutFriendlyURL (plid)",
		"create unique index IX_C5762E72 on LayoutFriendlyURL (plid, languageId)",
		"create index IX_9F80D54 on LayoutFriendlyURL (uuid_)",
		"create index IX_F4321A54 on LayoutFriendlyURL (uuid_, companyId)",
		"create unique index IX_326525D6 on LayoutFriendlyURL (uuid_, groupId)"
	};

}