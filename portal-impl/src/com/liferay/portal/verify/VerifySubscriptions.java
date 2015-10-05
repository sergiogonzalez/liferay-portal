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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Istvan Andras Dezsi
 */
public class VerifySubscriptions extends VerifyProcess {

	protected void deleteOrphanedSubscriptions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			long classNameId = PortalUtil.getClassNameId(
				PortletPreferences.class.getName());

			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("select subscriptionId from Subscription where ");
			sb.append("classNameId = ");
			sb.append(classNameId);
			sb.append(" and classPK not in (select portletPreferencesId from ");
			sb.append("PortletPreferences)");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long subscriptionId = rs.getLong("subscriptionId");

				SubscriptionLocalServiceUtil.deleteSubscription(subscriptionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteOrphanedSubscriptions();
	}

}