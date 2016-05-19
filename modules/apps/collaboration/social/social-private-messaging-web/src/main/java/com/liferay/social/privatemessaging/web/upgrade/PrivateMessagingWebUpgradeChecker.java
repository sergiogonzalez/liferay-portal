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

package com.liferay.social.privatemessaging.web.upgrade;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = Object.class)
public class PrivateMessagingWebUpgradeChecker {

	@Activate
	public void activate() throws PortalException {
		Release release = _releaseLocalService.fetchRelease(
			"com.liferay.private.messaging.web");

		if (release == null) {
			return;
		}

		long oldPortletIdCount = _getPortletCount(
			"1_WAR_privatemessagingportlet");

		long newPortletIdCount = _getPortletCount(
			"com_liferay_social_privatemessaging_web_portlet_" +
				"PrivateMessagingPortlet");

		if ((oldPortletIdCount > 0) || (newPortletIdCount == 0)) {
			release.setSchemaVersion("0.0.1");

			_releaseLocalService.updateRelease(release);
		}
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	private long _getPortletCount(String... portletIds) {
		DynamicQuery dynamicQuery =
			_portletPreferencesLocalService.dynamicQuery();

		Criterion criterion = RestrictionsFactoryUtil.in(
			"portletId", portletIds);

		dynamicQuery.add(criterion);

		dynamicQuery.setProjection(ProjectionFactoryUtil.count("portletId"));

		return _portletPreferencesLocalService.dynamicQueryCount(dynamicQuery);
	}

	private PortletPreferencesLocalService _portletPreferencesLocalService;
	private ReleaseLocalService _releaseLocalService;

}