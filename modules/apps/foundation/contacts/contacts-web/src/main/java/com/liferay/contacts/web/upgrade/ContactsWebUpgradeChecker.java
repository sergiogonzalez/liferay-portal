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

package com.liferay.contacts.web.upgrade;

import com.liferay.contacts.web.constants.ContactsPortletKeys;
import com.liferay.portal.upgrade.util.BaseWebUpgradeChecker;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = Object.class)
public class ContactsWebUpgradeChecker extends BaseWebUpgradeChecker {

	@Override
	protected String getBundleSymbolicName() {
		return "com.liferay.contacts.web";
	}

	@Override
	protected String[] getNewPortletIds() {
		return new String[] {
			ContactsPortletKeys.CONTACTS_CENTER, ContactsPortletKeys.MEMBERS,
			ContactsPortletKeys.MY_CONTACTS, ContactsPortletKeys.PROFILE
		};
	}

	@Override
	protected String[] getOldPortletIds() {
		return new String[] {
			"1_WAR_contactsportlet", "2_WAR_contactsportlet",
			"3_WAR_contactsportlet", "4_WAR_contactsportlet"
		};
	}

}