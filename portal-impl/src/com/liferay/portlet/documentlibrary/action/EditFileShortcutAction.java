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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portlet.documentlibrary.FileShortcutPermissionException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcutConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.mvc.ActionableMVCPortlet;
import com.liferay.portlet.mvc.MVCPortletAction;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Levente Hudák
 */
public class EditFileShortcutAction implements MVCPortletAction {

	public EditFileShortcutAction(ActionableMVCPortlet actionableMVCPortlet) {
		_actionableMVCPortlet = actionableMVCPortlet;
	}

	@Override
	public String processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFileShortcut(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFileShortcut(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteFileShortcut(actionRequest, true);
			}

			_actionableMVCPortlet.sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileShortcutException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				return "/html/portlet/document_library/error.jsp";
			}
			else if (e instanceof FileShortcutPermissionException ||
					 e instanceof NoSuchFileEntryException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw new PortletException(e);
			}
		}

		return null;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			ActionUtil.getFileShortcut(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileShortcutException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/html/portlet/document_library/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/html/portlet/document_library/edit_file_shortcut.jsp";
	}

	@Override
	public String serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		return null;
	}

	protected void deleteFileShortcut(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long fileShortcutId = ParamUtil.getLong(
			actionRequest, "fileShortcutId");

		if (moveToTrash) {
			FileShortcut fileShortcut =
				DLAppServiceUtil.moveFileShortcutToTrash(fileShortcutId);

			if (fileShortcut.getModel() instanceof TrashedModel) {
				TrashUtil.addTrashSessionMessages(
					actionRequest, (TrashedModel)fileShortcut.getModel());
			}

			_actionableMVCPortlet.hideDefaultSuccessMessage(actionRequest);
		}
		else {
			DLAppServiceUtil.deleteFileShortcut(fileShortcutId);
		}
	}

	protected void updateFileShortcut(ActionRequest actionRequest)
		throws Exception {

		long fileShortcutId = ParamUtil.getLong(
			actionRequest, "fileShortcutId");

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");
		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		long toFileEntryId = ParamUtil.getLong(actionRequest, "toFileEntryId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileShortcutConstants.getClassName(), actionRequest);

		if (fileShortcutId <= 0) {

			// Add file shortcut

			DLAppServiceUtil.addFileShortcut(
				repositoryId, folderId, toFileEntryId, serviceContext);
		}
		else {

			// Update file shortcut

			DLAppServiceUtil.updateFileShortcut(
				fileShortcutId, folderId, toFileEntryId, serviceContext);
		}
	}

	private final ActionableMVCPortlet _actionableMVCPortlet;

}