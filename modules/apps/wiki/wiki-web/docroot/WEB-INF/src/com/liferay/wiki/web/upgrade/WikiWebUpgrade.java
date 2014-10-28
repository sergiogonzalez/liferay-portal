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

package com.liferay.wiki.web.upgrade;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.upgrade.util.UpgradePortletId;
import com.liferay.portal.upgrade.util.UpgradePortletSettings;
import com.liferay.portal.util.PortletKeys;
import com.liferay.wiki.configuration.WikiSettings;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.util.WikiConstants;
import com.liferay.wiki.web.configuration.WikiPortletInstanceSettings;

import java.util.Arrays;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true, service = WikiWebUpgrade.class
)
public class WikiWebUpgrade {

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		UpgradePortletId upgradePortletId = new UpgradePortletId() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] {
					new String[] {"36", "36_WAR_pollsweb"},
					new String[] {"154", "154_WAR_pollsweb"},
					new String[] {"54", "54_WAR_pollsweb"}
				};
			}

		};

		UpgradePortletSettings upgradePortletSettings =
			new UpgradePortletSettings() {

				@Override
				protected void upgradePortlets() throws Exception {
					upgradeMainPortlet(
						WikiPortletKeys.WIKI, WikiConstants.SERVICE_NAME,
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
						WikiPortletInstanceSettings.ALL_KEYS,
						WikiSettings.ALL_KEYS);
				}

			};

		_releaseLocalService.updateRelease(
			"com.liferay.wiki.web",
			Arrays.asList(upgradePortletId, upgradePortletSettings), 1, 0,
			false);
	}

	private ReleaseLocalService _releaseLocalService;

}