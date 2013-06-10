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
import com.liferay.util.ContentUtil;
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
			"emailMessageAddedSubject", "emailMessageAddedSubjectPrefix",
			portletPreferences);

		portletPreferences = upgradeSubscriptionSubject(
			"emailMessageUpdatedSubject", "emailMessageUpdatedSubjectPrefix",
			portletPreferences);

		portletPreferences = upgradeSubscriptionBody(
			"emailMessageAddedBody", "emailMessageAddedSignature",
			portletPreferences);

		portletPreferences = upgradeSubscriptionBody(
			"emailMessageUpdatedBody", "emailMessageUpdatedSignature",
			portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected PortletPreferences upgradeSubscriptionBody(
			String bodyName, String signatureName,
			PortletPreferences portletPreferences)
		throws Exception {

		String body = GetterUtil.getString(
			portletPreferences.getValue(bodyName, StringPool.BLANK));

		String signature = GetterUtil.getString(
			portletPreferences.getValue(signatureName, StringPool.BLANK));

		if (Validator.isNull(signature) && Validator.isNull(body)) {
			return portletPreferences;
		}

		if (Validator.isNull(signature)) {
			if (bodyName.startsWith("emailMessageAdded")) {
				signature = ContentUtil.get(
					_RESOURCE_PATH +
						_MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SIGNATURE);
			}
			else {
				signature = ContentUtil.get(
					_RESOURCE_PATH +
						_MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SIGNATURE);
			}
		}

		if (Validator.isNull(body)) {
			if (bodyName.startsWith("emailMessageAdded")) {
				body = ContentUtil.get(
					_RESOURCE_PATH + _MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY);
			}
			else {
				body = ContentUtil.get(
					_RESOURCE_PATH +
						_MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY);
			}
		}

		body = body.concat("\n--\n").concat(signature);

		portletPreferences.setValue(bodyName, body);

		portletPreferences.reset(signatureName);

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
				subject =
					subjectPrefix.trim() + StringPool.SPACE +
						_MESSAGE_BOARDS_EMAIL_MESSAGE_SUBJECT;
			}

			portletPreferences.setValue(subjectName, subject);
		}

		portletPreferences.reset(subjectPrefixName);

		return portletPreferences;
	}

	private static final String _MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY =
		"email_message_added_body.tmpl";
	private static final String _MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SIGNATURE =
		"email_message_added_signature.tmpl";
	private static final String _MESSAGE_BOARDS_EMAIL_MESSAGE_SUBJECT =
		"[$MESSAGE_SUBJECT$]";
	private static final String _MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY =
		"email_message_updated_body.tmpl";
	private static final String
		_MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SIGNATURE =
			"email_message_updated_signature.tmpl";
	private static final String _RESOURCE_PATH =
		"com/liferay/portal/upgrade/v6_2_0/dependencies/messageboards/";

}