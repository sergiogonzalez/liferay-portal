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

package com.liferay.announcements.web.internal.upgrade;

import com.liferay.announcements.web.internal.upgrade.v1_0_2.UpgradePermission;
import com.liferay.portal.kernel.upgrade.BaseReplacePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeWebModuleRelease;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AnnouncementsWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		BaseUpgradeWebModuleRelease upgradeWebModuleRelease =
			new BaseUpgradeWebModuleRelease() {

				@Override
				protected String getBundleSymbolicName() {
					return "com.liferay.announcements.web";
				}

				@Override
				protected String[] getPortletIds() {
					return new String[] {
						"1_WAR_soannouncementsportlet", "83", "84",
						PortletKeys.ANNOUNCEMENTS
					};
				}

			};

		try {
			upgradeWebModuleRelease.upgrade();
		}
		catch (UpgradeException ue) {
			throw new RuntimeException(ue);
		}

		registry.register(
			"com.liferay.announcements.web", "0.0.0", "1.0.3",
			new DummyUpgradeStep());

		UpgradeStep upgradePortletId = new BaseReplacePortletId() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] {
					new String[] {
						"1_WAR_soannouncementsportlet",
						PortletKeys.ANNOUNCEMENTS
					},
					new String[] {"83", PortletKeys.ALERTS},
					new String[] {"84", PortletKeys.ANNOUNCEMENTS}
				};
			}

		};

		registry.register(
			"com.liferay.announcements.web", "0.0.1", "1.0.1",
			upgradePortletId);

		// See LPS-65946

		registry.register(
			"com.liferay.announcements.web", "1.0.0", "1.0.1",
			upgradePortletId);

		registry.register(
			"com.liferay.announcements.web", "1.0.1", "1.0.2",
			new UpgradePermission(true));

		// See LPS-69656

		registry.register(
			"com.liferay.announcements.web", "1.0.2", "1.0.3", upgradePortletId,
			new UpgradePermission(true));
	}

}