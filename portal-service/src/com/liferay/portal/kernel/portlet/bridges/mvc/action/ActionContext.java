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

package com.liferay.portal.kernel.portlet.bridges.mvc.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

/**
 * @author Iván Zaera
 */
public interface ActionContext {

	public void hideDefaultErrorMessage(ActionRequest actionRequest);

	public void hideDefaultSuccessMessage(ActionRequest actionRequest);

	public void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException;

	public void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String redirect)
		throws PortletException;

	public void writeJSON(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Object object)
		throws PortletException;

}