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

package com.liferay.workflow.task.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

/**
 * @author Leonardo Barros
 */
public abstract class WorkflowTaskBaseActionCommand implements ActionCommand {

	@Override
	public boolean processCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			doProcessCommand(actionRequest, actionResponse);

			setRedirectAttribute(actionRequest);

			return SessionErrors.isEmpty(actionRequest);
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected abstract void doProcessCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception;

	protected void setRedirectAttribute(ActionRequest actionRequest) {
		String redirect = ParamUtil.getString(actionRequest, "redirect");

		String closeRedirect = ParamUtil.getString(
			actionRequest, "closeRedirect");

		if (Validator.isNotNull(closeRedirect)) {
			redirect = HttpUtil.setParameter(
				redirect, "closeRedirect", closeRedirect);

			SessionMessages.add(
				actionRequest, PortalUtil.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT, closeRedirect);
		}

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

}