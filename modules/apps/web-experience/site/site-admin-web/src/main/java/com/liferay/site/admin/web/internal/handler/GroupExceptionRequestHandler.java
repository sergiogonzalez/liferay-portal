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

package com.liferay.site.admin.web.internal.handler;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.DuplicateGroupException;
import com.liferay.portal.kernel.exception.GroupInheritContentException;
import com.liferay.portal.kernel.exception.GroupKeyException;
import com.liferay.portal.kernel.exception.GroupParentException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.SiteConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = GroupExceptionRequestHandler.class)
public class GroupExceptionRequestHandler {

	public void handlePortalException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			PortalException pe)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (pe instanceof DuplicateGroupException) {
			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getLocale(), "please-enter-a-unique-name"));
		}
		else if (pe instanceof GroupInheritContentException) {
			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"this-site-cannot-inherit-content-from-its-parent-site"));
		}
		else if (pe instanceof GroupKeyException) {
			StringBundler sb = new StringBundler(3);

			sb.append(
				LanguageUtil.format(
					themeDisplay.getLocale(),
					"the-x-cannot-be-x-or-a-reserved-word-such-as-x",
					new String[] {
						SiteConstants.NAME_LABEL,
						SiteConstants.getNameGeneralRestrictions(
							themeDisplay.getLocale()),
						SiteConstants.NAME_RESERVED_WORDS
					}));

			sb.append(StringPool.SPACE);

			sb.append(
				LanguageUtil.format(
					themeDisplay.getLocale(),
					"the-x-cannot-contain-the-following-invalid-characters-x",
					new String[] {
						SiteConstants.NAME_LABEL,
						SiteConstants.NAME_INVALID_CHARACTERS
					}));

			jsonObject.put("error", sb.toString());
		}
		else if (pe instanceof GroupParentException.MustNotBeOwnParent) {
			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"this-site-cannot-inherit-content-from-its-parent-site"));
		}
		else {
			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getLocale(), "an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

}