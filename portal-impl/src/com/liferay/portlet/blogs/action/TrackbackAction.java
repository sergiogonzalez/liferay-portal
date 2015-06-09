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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.blogs.TrackbackValidationException;
import com.liferay.portlet.blogs.model.BlogsEntry;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 */
public class TrackbackAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected void addTrackback(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected BlogsEntry getBlogsEntry(ActionRequest actionRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected boolean isCommentsEnabled(ActionRequest actionRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected void sendError(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String msg)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected void sendResponse(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String msg, boolean success)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected void sendSuccess(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected void validate(
			ActionRequest actionRequest, String remoteIP, String url)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected void validate(BlogsEntry entry)
		throws TrackbackValidationException {

		throw new UnsupportedOperationException();
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}