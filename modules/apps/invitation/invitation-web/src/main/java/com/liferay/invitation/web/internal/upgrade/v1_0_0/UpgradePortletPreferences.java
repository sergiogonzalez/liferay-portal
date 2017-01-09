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

package com.liferay.invitation.web.internal.upgrade.v1_0_0;

import com.liferay.invitation.web.internal.constants.InvitationPortletKeys;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletPreferences;

/**
 * @author Adolfo Pérez
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {InvitationPortletKeys.INVITATION};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String emailMaxRecipients = portletPreferences.getValue(
			"emailMaxRecipients", null);

		if (Validator.isNull(emailMaxRecipients)) {
			portletPreferences.setValue("emailMaxRecipients", "20");
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}