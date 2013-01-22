/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.assetpublisher.util.AssetPublisherSubscriptionUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Roberto Diaz
 */
public class EditSusbcriptionAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.SUBSCRIBE)) {
			subscribeAssetPublisher(actionRequest);
		}
		else if (cmd.equals(Constants.UNSUBSCRIBE)) {
			unsubscribeAssetPublisher(actionRequest);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	private void subscribeAssetPublisher(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long portletPreferencesId =
			AssetPublisherSubscriptionUtil.getPortletPreferencesId(
				themeDisplay.getPlid(),
				themeDisplay.getPortletDisplay().getInstanceId());

		AssetPublisherSubscriptionUtil.subscribe(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
			portletPreferencesId);

	}

	private void unsubscribeAssetPublisher(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long portletPreferencesId =
			AssetPublisherSubscriptionUtil.getPortletPreferencesId(
				themeDisplay.getPlid(),
				themeDisplay.getPortletDisplay().getInstanceId());

		AssetPublisherSubscriptionUtil.unsubscribe(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
			portletPreferencesId);
	}
}