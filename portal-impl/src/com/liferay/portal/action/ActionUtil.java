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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class ActionUtil {

	public static String getRootPortletId(Portlet portlet) {

		// Workaround for portlet.getRootPortletId() because that does not
		// return the proper root portlet ID for OpenSocial and WSRP portlets

		Portlet rootPortlet = portlet.getRootPortlet();

		return rootPortlet.getPortletId();
	}

	public static void populatePortletJSONObject(
			HttpServletRequest request, StringServletResponse stringResponse,
			Portlet portlet, JSONObject jsonObject)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		jsonObject.put("refresh", !portlet.isAjaxable());
		jsonObject.put("portletHTML", stringResponse.getString().trim());

		Set<String> footerCssSet = new LinkedHashSet<String>();
		Set<String> footerJavaScriptSet = new LinkedHashSet<String>();
		Set<String> headerCssSet = new LinkedHashSet<String>();
		Set<String> headerJavaScriptSet = new LinkedHashSet<String>();

		boolean portletOnLayout = false;

		String rootPortletId = getRootPortletId(portlet);
		String portletId = portlet.getPortletId();

		for (Portlet layoutPortlet : layoutTypePortlet.getAllPortlets()) {

			// Check to see if an instance of this portlet is already in the
			// layout, but ignore the portlet that was just added

			String layoutPortletRootPortletId = getRootPortletId(layoutPortlet);

			if (rootPortletId.equals(layoutPortletRootPortletId) &&
				!portletId.equals(layoutPortlet.getPortletId())) {

				portletOnLayout = true;

				break;
			}
		}

		PortletApp portletApp = portlet.getPortletApp();

		if (!portletOnLayout && portlet.isAjaxable()) {
			Portlet rootPortlet = portlet.getRootPortlet();

			for (String footerPortalCss : portlet.getFooterPortalCss()) {
				if (!HttpUtil.hasProtocol(footerPortalCss)) {
					footerPortalCss =
						PortalUtil.getPathContext() + footerPortalCss;

					footerPortalCss = PortalUtil.getStaticResourceURL(
						request, footerPortalCss, rootPortlet.getTimestamp());
				}

				footerCssSet.add(footerPortalCss);
			}

			for (String footerPortalJavaScript :
					portlet.getFooterPortalJavaScript()) {

				if (!HttpUtil.hasProtocol(footerPortalJavaScript)) {
					footerPortalJavaScript =
						PortalUtil.getPathContext() + footerPortalJavaScript;

					footerPortalJavaScript = PortalUtil.getStaticResourceURL(
						request, footerPortalJavaScript,
						rootPortlet.getTimestamp());
				}

				footerJavaScriptSet.add(footerPortalJavaScript);
			}

			for (String footerPortletCss : portlet.getFooterPortletCss()) {
				if (!HttpUtil.hasProtocol(footerPortletCss)) {
					footerPortletCss =
						portletApp.getContextPath() + footerPortletCss;

					footerPortletCss = PortalUtil.getStaticResourceURL(
						request, footerPortletCss, rootPortlet.getTimestamp());
				}

				footerCssSet.add(footerPortletCss);
			}

			for (String footerPortletJavaScript :
					portlet.getFooterPortletJavaScript()) {

				if (!HttpUtil.hasProtocol(footerPortletJavaScript)) {
					footerPortletJavaScript =
						portletApp.getContextPath() + footerPortletJavaScript;

					footerPortletJavaScript = PortalUtil.getStaticResourceURL(
						request, footerPortletJavaScript,
						rootPortlet.getTimestamp());
				}

				footerJavaScriptSet.add(footerPortletJavaScript);
			}

			for (String headerPortalCss : portlet.getHeaderPortalCss()) {
				if (!HttpUtil.hasProtocol(headerPortalCss)) {
					headerPortalCss =
						PortalUtil.getPathContext() + headerPortalCss;

					headerPortalCss = PortalUtil.getStaticResourceURL(
						request, headerPortalCss, rootPortlet.getTimestamp());
				}

				headerCssSet.add(headerPortalCss);
			}

			for (String headerPortalJavaScript :
					portlet.getHeaderPortalJavaScript()) {

				if (!HttpUtil.hasProtocol(headerPortalJavaScript)) {
					headerPortalJavaScript =
						PortalUtil.getPathContext() + headerPortalJavaScript;

					headerPortalJavaScript = PortalUtil.getStaticResourceURL(
						request, headerPortalJavaScript,
						rootPortlet.getTimestamp());
				}

				headerJavaScriptSet.add(headerPortalJavaScript);
			}

			for (String headerPortletCss : portlet.getHeaderPortletCss()) {
				if (!HttpUtil.hasProtocol(headerPortletCss)) {
					headerPortletCss =
						portletApp.getContextPath() + headerPortletCss;

					headerPortletCss = PortalUtil.getStaticResourceURL(
						request, headerPortletCss, rootPortlet.getTimestamp());
				}

				headerCssSet.add(headerPortletCss);
			}

			for (String headerPortletJavaScript :
					portlet.getHeaderPortletJavaScript()) {

				if (!HttpUtil.hasProtocol(headerPortletJavaScript)) {
					headerPortletJavaScript =
						portletApp.getContextPath() + headerPortletJavaScript;

					headerPortletJavaScript = PortalUtil.getStaticResourceURL(
						request, headerPortletJavaScript,
						rootPortlet.getTimestamp());
				}

				headerJavaScriptSet.add(headerPortletJavaScript);
			}
		}

		String footerCssPaths = JSONFactoryUtil.serialize(
			footerCssSet.toArray(new String[footerCssSet.size()]));

		jsonObject.put(
			"footerCssPaths", JSONFactoryUtil.createJSONArray(footerCssPaths));

		String footerJavaScriptPaths = JSONFactoryUtil.serialize(
			footerJavaScriptSet.toArray(
				new String[footerJavaScriptSet.size()]));

		jsonObject.put(
			"footerJavaScriptPaths",
			JSONFactoryUtil.createJSONArray(footerJavaScriptPaths));

		String headerCssPaths = JSONFactoryUtil.serialize(
			headerCssSet.toArray(new String[headerCssSet.size()]));

		jsonObject.put(
			"headerCssPaths", JSONFactoryUtil.createJSONArray(headerCssPaths));

		String headerJavaScriptPaths = JSONFactoryUtil.serialize(
			headerJavaScriptSet.toArray(
				new String[headerJavaScriptSet.size()]));

		jsonObject.put(
			"headerJavaScriptPaths",
			JSONFactoryUtil.createJSONArray(headerJavaScriptPaths));
	}

}