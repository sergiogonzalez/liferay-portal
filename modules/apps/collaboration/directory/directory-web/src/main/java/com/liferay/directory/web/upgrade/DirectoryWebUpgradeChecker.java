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

package com.liferay.directory.web.upgrade;

import com.liferay.directory.web.constants.DirectoryPortletKeys;
import com.liferay.portal.upgrade.util.BaseWebUpgradeChecker;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = Object.class)
public class DirectoryWebUpgradeChecker extends BaseWebUpgradeChecker {

	@Override
	protected String getBundleSymbolicName() {
		return "com.liferay.directory.web";
	}

	@Override
	protected String[] getNewPortletIds() {
		return new String[] {
			DirectoryPortletKeys.DIRECTORY,
			DirectoryPortletKeys.FRIENDS_DIRECTORY,
			DirectoryPortletKeys.SITE_MEMBERS_DIRECTORY,
			DirectoryPortletKeys.MY_SITES_DIRECTORY
		};
	}

	@Override
	protected String[] getOldPortletIds() {
		return new String[] {"11", "186", "187", "188"};
	}

}