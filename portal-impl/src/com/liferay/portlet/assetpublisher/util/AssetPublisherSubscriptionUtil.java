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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.assetpublisher.service.permission.AssetPublisherPermission;

import java.util.List;

/**
 * @author Roberto Diaz
 */
public class AssetPublisherSubscriptionUtil {

	public static long getPortletPreferencesId(long plid, String instanceId)
		throws Exception {

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil. getPortletPreferences(
				plid, PortletKeys.ASSET_PUBLISHER);

		for (PortletPreferences portletPreferences :portletPreferencesList) {

			if (portletPreferences.getPortletId().equals(instanceId)) {
				return portletPreferences.getPortletPreferencesId();
			}

		}

		return 0;
	}

	public static boolean isSubscribed(
			long companyId, long userId, long plid, String instanceId)
		throws Exception {

		long portletPreferencesId = getPortletPreferencesId(plid, instanceId);

		return SubscriptionLocalServiceUtil.isSubscribed(
				companyId, userId, PortletDisplay.class.getName(),
				portletPreferencesId);
	}

	public static void subscribe(long userId, long groupId, long instanceId)
		throws PortalException, SystemException {

		AssetPublisherPermission.check(
			_getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		SubscriptionLocalServiceUtil.addSubscription(
			userId, groupId, PortletDisplay.class.getName(), instanceId);
	}

	public static void unsubscribe(long userId, long groupId, long instanceId)
		throws PortalException, SystemException {

		AssetPublisherPermission.check(
			_getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		SubscriptionLocalServiceUtil.deleteSubscription(
			userId, PortletDisplay.class.getName(), instanceId);
	}

	private static PermissionChecker _getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException("PermissionChecker not initialized");
		}

		return permissionChecker;
	}

}