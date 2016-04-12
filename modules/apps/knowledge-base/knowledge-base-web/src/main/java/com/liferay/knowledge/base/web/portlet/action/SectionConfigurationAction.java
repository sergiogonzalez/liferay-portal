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

package com.liferay.knowledge.base.web.portlet.action;

import com.liferay.portal.kernel.portlet.BaseJSPSettingsConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peter Shin
 */
public class SectionConfigurationAction
	extends BaseJSPSettingsConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest request) {
		return "/section/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");

		if (tabs2.equals("general")) {
			updateGeneral(actionRequest);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void updateGeneral(ActionRequest actionRequest) {
		String[] kbArticlesSections = actionRequest.getParameterValues(
			"kbArticlesSections");

		if (ArrayUtil.isEmpty(kbArticlesSections)) {
			SessionErrors.add(actionRequest, "kbArticlesSections");
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			setPreference(
				actionRequest, "kbArticlesSections", kbArticlesSections);
		}
	}

}