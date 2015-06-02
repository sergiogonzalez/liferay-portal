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

package com.liferay.portlet.mvc;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Iván Zaera
 */
public class ActionableMVCPortlet extends MVCPortlet {

	@Override
	public boolean callActionMethod(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		MVCPortletAction mvcPortletAction = getMVCPortletAction(actionRequest);

		if (mvcPortletAction != null) {
			String mvcPath = mvcPortletAction.processAction(
				actionRequest, actionResponse);

			if (Validator.isNotNull(mvcPath)) {
				actionResponse.setRenderParameter("mvcPath", mvcPath);
			}

			return true;
		}

		return super.callActionMethod(actionRequest, actionResponse);
	}

	@Override
	public void hideDefaultErrorMessage(PortletRequest portletRequest) {
		super.hideDefaultErrorMessage(portletRequest);
	}

	@Override
	public void hideDefaultSuccessMessage(PortletRequest portletRequest) {
		super.hideDefaultSuccessMessage(portletRequest);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		MVCPortletAction mvcPortletAction = getMVCPortletAction(renderRequest);

		if (mvcPortletAction != null) {
			String mvcPath = mvcPortletAction.render(
				renderRequest, renderResponse);

			if (Validator.isNotNull(mvcPath)) {
				renderRequest.setAttribute("mvcPath", mvcPath);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		super.sendRedirect(actionRequest, actionResponse);
	}

	public void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String redirect)
		throws IOException {

		if (redirect != null) {
			actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
		}

		super.sendRedirect(actionRequest, actionResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		MVCPortletAction mvcPortletAction = getMVCPortletAction(
			resourceRequest);

		if (mvcPortletAction != null) {
			String mvcPath = mvcPortletAction.serveResource(
				resourceRequest, resourceResponse);

			if (Validator.isNotNull(mvcPath)) {
				resourceRequest.setAttribute("mvcPath", mvcPath);
			}
		}

		super.serveResource(resourceRequest, resourceResponse);
	}

	@Override
	public void writeJSON(
			PortletRequest portletRequest, ActionResponse actionResponse,
			Object json)
		throws IOException {

		super.writeJSON(portletRequest, actionResponse, json);
	}

	protected MVCPortletAction getMVCPortletAction(
		PortletRequest portletRequest) {

		String actionName = ParamUtil.getString(
			portletRequest, "struts_action");

		return _mvcPortletActions.get(actionName);
	}

	@Override
	protected String getPath(PortletRequest portletRequest) {
		String mvcPath = (String)portletRequest.getAttribute("mvcPath");

		if (mvcPath != null) {
			return mvcPath;
		}

		return super.getPath(portletRequest);
	}

	protected void registerMVCPortletAction(
		MVCPortletAction mvcPortletAction, String...actionNames) {

		for (String actionName : actionNames) {
			_mvcPortletActions.put(actionName, mvcPortletAction);
		}
	}

	private final Map<String, MVCPortletAction> _mvcPortletActions =
		new HashMap<>();

}