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

package com.liferay.wiki.service.upgrade;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.upgrade.util.UpgradePortletSettings;
import com.liferay.portal.util.PortletKeys;
import com.liferay.wiki.WikiPortletInstanceSettings;
import com.liferay.wiki.WikiSettings;
import com.liferay.wiki.util.WikiConstants;

import java.util.Arrays;

import com.liferay.wiki.constants.WikiPortletKeys;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true, service = WikiServiceUpgrade.class
)
public class WikiServiceUpgrade {

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		UpgradeWiki upgradeWiki = new UpgradeWiki();

		UpgradePortletSettings upgradePortletSettings =
			new UpgradePortletSettings() {

				@Override
				protected void upgradePortlets() throws Exception {
					upgradeMainPortlet(
						WikiPortletKeys.WIKI, WikiConstants.SERVICE_NAME,
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
						WikiPortletInstanceSettings.ALL_KEYS,
						WikiSettings.ALL_KEYS);

					upgradeDisplayPortlet(
						WikiPortletKeys.WIKI_DISPLAY,
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
						WikiSettings.ALL_KEYS);
				}

			};

		_releaseLocalService.updateRelease(
			"com.liferay.wiki.service",
			Arrays.asList(upgradeWiki, upgradePortletSettings),
			1, 0, false);
	}

	private ReleaseLocalService _releaseLocalService;

}