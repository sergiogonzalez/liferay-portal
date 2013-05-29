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
 * @author Roberto Díaz
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

		String rssFormat = GetterUtil.getString(
			portletPreferences.getValue("rssFormat", null));

		if (Validator.isNotNull(rssFormat)) {
			String rssFeedType = RSSUtil.getFeedType(
				RSSUtil.getFormatType(rssFormat),
				RSSUtil.getFormatVersion(rssFormat));

			portletPreferences.setValue("rssFeedType", rssFeedType);
		}

		portletPreferences.reset("rssFormat");

		portletPreferences = upgradeSubscriptionSubject(
			"mailMessageAddedSubject", "mailMessageAddedSubjectPrefix",
			portletPreferences);

		portletPreferences = upgradeSubscriptionSubject(
			"mailMessageUpdatedSubject", "mailMessageUpdatedSubjectPrefix",
			portletPreferences);

		// Email Subscription Body

		portletPreferences = upgradeSubscriptionBody(
			"mailMessageAddedBody", "mailMessageAddedSignature",
			portletPreferences);

		portletPreferences = upgradeSubscriptionBody(
			"mailMessageUpdatedBody", "mailMessageUpdatedSignature",
			portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected PortletPreferences upgradeSubscriptionBody(
			String valueKey, String oldValueKey,
			PortletPreferences portletPreferences)
		throws Exception {

		String bodyValue = GetterUtil.getString(
			portletPreferences.getValue(valueKey, StringPool.BLANK));

		String signatureValue = GetterUtil.getString(
			portletPreferences.getValue(oldValueKey, StringPool.BLANK));

		if (Validator.isNull(signatureValue) && Validator.isNull(bodyValue)) {
			return portletPreferences;
		}

		if (Validator.isNull(signatureValue)) {
			signatureValue =
				"[$COMPANY_NAME$] Message Boards [$MESSAGE_URL$]" +
					"[$MAILING_LIST_ADDRESS$][$PORTAL_URL$]";
		}
		else if (Validator.isNull(bodyValue)) {
			bodyValue = "[$MESSAGE_BODY$]";
		}

		bodyValue = bodyValue.concat("\n--\n" + signatureValue);

		portletPreferences.setValue(valueKey, bodyValue);

		portletPreferences.reset(oldValueKey);

		return portletPreferences;
	}

	protected PortletPreferences upgradeSubscriptionSubject(
			String subjectName, String subjectPrefixName,
			PortletPreferences portletPreferences)
		throws Exception {

		String subjectPrefix = GetterUtil.getString(
			portletPreferences.getValue(subjectPrefixName, StringPool.BLANK));

		if (Validator.isNotNull(subjectPrefix)) {
			String subject = subjectPrefix;

			if (!subjectPrefix.contains("[$MESSAGE_SUBJECT$]")) {
				subject = subject.concat(" [$MESSAGE_SUBJECT$]");
			}

			portletPreferences.setValue(subjectName, subject);
		}

		portletPreferences.reset(subjectPrefixName);

		return portletPreferences;
	}

}