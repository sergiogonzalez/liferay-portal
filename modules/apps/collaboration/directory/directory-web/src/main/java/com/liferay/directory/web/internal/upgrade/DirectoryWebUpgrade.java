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

package com.liferay.directory.web.internal.upgrade;

import com.liferay.directory.web.internal.constants.DirectoryPortletKeys;
import com.liferay.directory.web.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeWebModuleRelease;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class DirectoryWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		BaseUpgradeWebModuleRelease baseUpgradeWebModuleRelease =
			new BaseUpgradeWebModuleRelease() {

				@Override
				protected String getBundleSymbolicName() {
					return "com.liferay.directory.web";
				}

				@Override
				protected String[] getPortletIds() {
					return new String[] {
						"11", "186", "187", "188",
						DirectoryPortletKeys.DIRECTORY,
						DirectoryPortletKeys.FRIENDS_DIRECTORY,
						DirectoryPortletKeys.SITE_MEMBERS_DIRECTORY,
						DirectoryPortletKeys.MY_SITES_DIRECTORY
					};
				}

			};

		try {
			baseUpgradeWebModuleRelease.upgrade();
		}
		catch (UpgradeException ue) {
			throw new RuntimeException(ue);
		}

		registry.register(
			"com.liferay.directory.web", "0.0.0", "1.0.1",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.directory.web", "0.0.1", "1.0.1",
			new UpgradePortletId());

		// See LPS-65946

		registry.register(
			"com.liferay.directory.web", "1.0.0", "1.0.1",
			new UpgradePortletId());
	}

}