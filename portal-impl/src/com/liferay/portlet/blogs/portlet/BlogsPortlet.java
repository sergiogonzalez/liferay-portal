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

package com.liferay.portlet.blogs.portlet;

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContextFunction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.blogs.TrackbackValidationException;
import com.liferay.portlet.blogs.action.ActionUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.blogs.trackback.Trackback;
import com.liferay.portlet.blogs.trackback.TrackbackImpl;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class BlogsPortlet extends BaseBlogsPortlet {

	public void addTrackback(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			BlogsEntry entry = getBlogsEntry(resourceRequest);

			validate(entry);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				resourceRequest);

			HttpServletRequest originalRequest =
				PortalUtil.getOriginalServletRequest(request);

			String excerpt = ParamUtil.getString(originalRequest, "excerpt");
			String url = ParamUtil.getString(originalRequest, "url");
			String blogName = ParamUtil.getString(originalRequest, "blog_name");
			String title = ParamUtil.getString(originalRequest, "title");

			validate(resourceRequest, request.getRemoteAddr(), url);

			_trackback.addTrackback(
				entry, themeDisplay, excerpt, url, blogName, title,
				new ServiceContextFunction(resourceRequest));

			sendSuccess(resourceRequest, resourceResponse);
		}
		catch (TrackbackValidationException tve) {
			sendError(resourceRequest, resourceResponse, tve.getMessage());
		}
	}

	public void subscribe(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		BlogsEntryServiceUtil.subscribe(themeDisplay.getScopeGroupId());
	}

	public void unsubscribe(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		BlogsEntryServiceUtil.unsubscribe(themeDisplay.getScopeGroupId());
	}

	protected BlogsEntry getBlogsEntry(ResourceRequest resourceRequest)
		throws Exception {

		try {
			ActionUtil.getEntry(resourceRequest);
		}
		catch (PrincipalException pe) {
			throw new TrackbackValidationException(
				"Blog entry must have guest view permissions to enable " +
					"trackbacks");
		}

		return (BlogsEntry)resourceRequest.getAttribute(WebKeys.BLOGS_ENTRY);
	}

	protected boolean isCommentsEnabled(ResourceRequest resourceRequest)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getExistingPortletSetup(
				resourceRequest);

		if (portletPreferences == null) {
			portletPreferences = resourceRequest.getPreferences();
		}

		return GetterUtil.getBoolean(
			portletPreferences.getValue("enableComments", null), true);
	}

	protected void sendError(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			String msg)
		throws Exception {

		sendResponse(
			resourceRequest, resourceResponse, "<error>1</error><message>", msg,
			"</message>");
	}

	protected void sendResponse(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			String... contents)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<response>");

		for (String content : contents) {
			sb.append(content);
		}

		sb.append("</response>");

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			resourceResponse);

		ServletResponseUtil.sendFile(
			request, response, null, sb.toString().getBytes(StringPool.UTF8),
			ContentTypes.TEXT_XML_UTF8);
	}

	protected void sendSuccess(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		sendResponse(resourceRequest, resourceResponse, "<error>0</error>");
	}

	protected void validate(BlogsEntry entry)
		throws TrackbackValidationException {

		if (!entry.isAllowTrackbacks()) {
			throw new TrackbackValidationException(
				"Trackbacks are not enabled");
		}
	}

	protected void validate(
			ResourceRequest resourceRequest, String remoteIP, String url)
		throws Exception {

		if (!isCommentsEnabled(resourceRequest)) {
			throw new TrackbackValidationException("Comments are disabled");
		}

		if (Validator.isNull(url)) {
			throw new TrackbackValidationException(
				"Trackback requires a valid permanent URL");
		}

		String trackbackIP = HttpUtil.getIpAddress(url);

		if (!remoteIP.equals(trackbackIP)) {
			throw new TrackbackValidationException(
				"Remote IP does not match the trackback URL's IP");
		}
	}

	private final Trackback _trackback = new TrackbackImpl();

}