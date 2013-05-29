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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Roberto Díaz
 */
public class UpgradeWiki extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"36"};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		portletPreferences = upgradeSubscriptionSubject(
			"mailPageAddedSubject", "mailPageAddedSubjectPrefix",
			portletPreferences);

		portletPreferences = upgradeSubscriptionSubject(
			"mailPageUpdatedSubject", "mailPageUpdatedSubjectPrefix",
			portletPreferences);

		portletPreferences = upgradeSubscriptionBody(
			"mailPageAddedBody", "mailPageAddedSignature", portletPreferences);

		portletPreferences = upgradeSubscriptionBody(
			"mailPageUpdatedBody", "mailPageUpdatedSignature",
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
			StringBundler sb = new StringBundler(8);

			sb.append(
				"<div style=\"background: #eee; border-top: 1px solid #ccc; " +
					"color: #999; margin: 5px; padding: 5px; " +
					"text-align: center;\">\n");

			if (bodyName.startsWith("mailPageAdded")) {
				sb.append(
					"\t<a href=\"[$PAGE_URL$]\">View Page ([$PAGE_TITLE$])\n");
			}
			else {
				sb.append(
					"\t<a href=\"[$PAGE_URL$]\">View Page " +
						"([$PAGE_TITLE$])</a> | <a href=\"[$DIFFS_URL$]\">" +
						"View Changes</a>\n");
			}

			sb.append("\n");
			sb.append("\t<p>\n");
			sb.append("\t\t[$COMPANY_NAME$] Wiki<br />\n");
			sb.append("\t\t[$PORTAL_URL$]\n");
			sb.append("\t</p>\n");
			sb.append("</div>");

			signature = sb.toString();
		}

		if (Validator.isNull(body)) {
			StringBundler sb = new StringBundler(6);

			sb.append(
				"<div style=\"background: #dff4ff; " +
					"border: 1px solid #a7cedf; color: #34404f; " +
					"margin: 5px; padding: 5px;\">\n");
			sb.append(
				"\tBy <strong>[$PAGE_USER_NAME$]</strong> "+
					"([$PAGE_DATE_UPDATE$])<br />\n");

			if (bodyName.startsWith("mailPageAdded")) {
				sb.append("\tPage Title: [$PAGE_TITLE$]\n");
				sb.append("</div>\n");
				sb.append("\n");
				sb.append("[$PAGE_CONTENT$]");
			}
			else {
				sb.append("\tSummary: [$PAGE_SUMMARY$]\n");
				sb.append("</div>\n");
				sb.append("\n");
				sb.append("[$PAGE_DIFFS$]");
			}

			body = sb.toString();
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

			if (!subjectPrefix.contains("[$PAGE_TITLE$]")) {
				subject = subject.concat(" [$PAGE_TITLE$]");
			}

			portletPreferences.setValue(subjectName, subject);
		}

		portletPreferences.reset(subjectPrefixName);

		return portletPreferences;
	}

}