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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class SocialRelationConfigurationUtil {

	public static SocialRelationConfiguration getCompanySettings(long companyId)
		throws SystemException {

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId, true);

		boolean interactionsEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue(
				"interactionsEnabled", Boolean.TRUE.toString()));

		boolean anyUserEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue(
				"interactionsAnyUser", Boolean.TRUE.toString()));

		boolean sameSitesEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue(
				"interactionsSitesEnabled", Boolean.TRUE.toString()));

		boolean socialRelationTypesEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue(
				"interactionsSocialRelationTypesEnabled",
				Boolean.TRUE.toString()));

		String socialRelationTypesString = companyPortletPreferences.getValue(
			"interactionsSocialRelationTypes", StringPool.BLANK);

		int[] socialRelationTypes =
			GetterUtil.getIntegerValues(
				StringUtil.split(socialRelationTypesString));

		return new SocialRelationConfiguration(
			anyUserEnabled, interactionsEnabled, sameSitesEnabled,
			socialRelationTypesEnabled, socialRelationTypesString,
			socialRelationTypes);
	}

	public static SocialRelationConfiguration getCompanySettings(
			long companyId, HttpServletRequest request)
		throws SystemException {

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId, true);

		boolean interactionsEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request, "interactionsEnabled", true);

		boolean anyUserEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request, "interactionsAnyUser", true);

		boolean sameSitesEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request, "interactionsSitesEnabled",
			true);

		boolean socialRelationTypesEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request,
			"interactionsSocialRelationTypesEnabled", true);

		String socialRelationTypesString = companyPortletPreferences.getValue(
			"interactionsSocialRelationTypes", StringPool.BLANK);

		int[] socialRelationTypes =
			GetterUtil.getIntegerValues(
				StringUtil.split(socialRelationTypesString));

		return new SocialRelationConfiguration(
			anyUserEnabled, interactionsEnabled, sameSitesEnabled,
			socialRelationTypesEnabled, socialRelationTypesString,
			socialRelationTypes);
	}

	public static SocialRelationConfiguration getGroupSettings(
			long companyId, long groupId)
		throws PortalException, SystemException {

		SocialRelationConfiguration companyConfiguration = getCompanySettings(
			companyId);

		if (!companyConfiguration.isInteractionsEnabled()) {
			return companyConfiguration;
		}

		Group liveGroup = getLiveGroup(groupId);

		UnicodeProperties typeSettingsProperties = null;

		if (liveGroup != null) {
			typeSettingsProperties = liveGroup.getTypeSettingsProperties();
		}
		else {
			typeSettingsProperties = new UnicodeProperties();
		}

		boolean interactionsEnabled = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("interactionsEnabled"),
			companyConfiguration.isInteractionsEnabled());

		boolean anyUserEnabled = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("interactionsAnyUser"),
			companyConfiguration.isAnyUserEnabled());

		boolean sameSitesEnabled = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("interactionsSitesEnabled"),
			companyConfiguration.isSameSitesEnabled());

		boolean socialRelationTypesEnabled = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty(
				"interactionsSocialRelationTypesEnabled"),
			companyConfiguration.isSocialRelationTypesEnabled());

		String socialRelationTypesString = GetterUtil.getString(
			typeSettingsProperties.getProperty(
				"interactionsSocialRelationTypes"),
			companyConfiguration.getSocialRelationTypesString());

		int[] socialRelationTypes = GetterUtil.getIntegerValues(
			StringUtil.split(socialRelationTypesString));

		return new SocialRelationConfiguration(
			anyUserEnabled, interactionsEnabled, sameSitesEnabled,
			socialRelationTypesEnabled, socialRelationTypesString,
			socialRelationTypes);
	}

	private static Group getLiveGroup(long groupId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Group liveGroup = group.getLiveGroup();

		if (liveGroup == null) {
			return group;
		}
		else {
			return liveGroup;
		}
	}

}