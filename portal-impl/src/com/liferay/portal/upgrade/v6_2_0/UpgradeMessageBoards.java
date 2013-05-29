/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.RSSUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Eduardo Garcia
 * @author Daniel Kocsis
 */
public class UpgradeMessageBoards extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"19"};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		// RSS

		String rssFormat = GetterUtil.getString(
			portletPreferences.getValue("rssFormat", null));

		if (Validator.isNotNull(rssFormat)) {
			String rssFeedType = RSSUtil.getFeedType(
				RSSUtil.getFormatType(rssFormat),
				RSSUtil.getFormatVersion(rssFormat));

			portletPreferences.setValue("rssFeedType", rssFeedType);
		}

		portletPreferences.reset("rssFormat");

		// Email Subscription Subject

		portletPreferences = upgradeSubscriptionSubject(
			"mailMessageAddedSubject", "mailMessageAddedSubjectPrefix",
			portletPreferences);

		portletPreferences = upgradeSubscriptionSubject(
			"mailMessageUpdatedSubject", "mailMessageUpdatedSubjectPrefix",
			portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected PortletPreferences upgradeSubscriptionSubject(
			String valueKey, String oldValueKey,
			PortletPreferences portletPreferences)
		throws Exception {

		String subjectPrefixValue = GetterUtil.getString(
			portletPreferences.getValue(oldValueKey, StringPool.BLANK));

		if (Validator.isNotNull(subjectPrefixValue)) {
			String subjectValue = subjectPrefixValue;

			if (!subjectPrefixValue.contains("[$MESSAGE_SUBJECT$]")) {
				subjectValue = subjectValue.concat(" [$MESSAGE_SUBJECT$]");
			}

			portletPreferences.setValue(valueKey, subjectValue);
		}

		portletPreferences.reset(oldValueKey);

		return portletPreferences;
	}

}