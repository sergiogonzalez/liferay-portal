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

package com.liferay.document.library.google.drive.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.google.drive.constants.GoogleDriveConstants;
import com.liferay.document.library.google.drive.model.GoogleDriveFileReference;
import com.liferay.document.library.google.drive.service.GoogleDriveManager;
import com.liferay.document.library.google.drive.web.internal.constants.GoogleDriveWebConstants;
import com.liferay.document.library.google.drive.web.internal.constants.GoogleDriveWebKeys;
import com.liferay.document.library.google.drive.web.internal.util.OAuth2Helper;
import com.liferay.document.library.google.drive.web.internal.util.State;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/edit_in_google_docs"
	},
	service = MVCActionCommand.class
)
public class EditInGoogleDriveMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

			// Fail early if file doesn't exist or the user haven't got the
			// required permissions.

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			if (_googleDriveManager.hasValidCredential(
					_portal.getUserId(actionRequest))) {

				Optional<GoogleDriveFileReference> driveFileReferenceOptional =
					GoogleDriveFileReference.captureGoogleDriveFileReference(
						() -> _executeCommand(actionRequest, fileEntry));

				driveFileReferenceOptional.ifPresent(
					driveFileReference -> actionRequest.setAttribute(
						GoogleDriveWebKeys.GOOGLE_DRIVE_FILE_REFERENCE,
						driveFileReference));
			}
			else {
				_performAuthorizationFlow(actionRequest, actionResponse);
			}
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());
		}
	}

	private void _executeCommand(
			PortletRequest portletRequest, FileEntry fileEntry)
		throws PortalException {

		String cmd = ParamUtil.getString(portletRequest, Constants.CMD);

		if (cmd.equals(GoogleDriveWebConstants.GOOGLE_DRIVE_CANCEL_CHECKOUT)) {
			_dlAppService.cancelCheckOut(fileEntry.getFileEntryId());
		}
		else if (cmd.equals(GoogleDriveWebConstants.GOOGLE_DRIVE_CHECKIN)) {
			_dlAppService.checkInFileEntry(
				fileEntry.getFileEntryId(),
				ParamUtil.getBoolean(portletRequest, "majorVersion"),
				ParamUtil.getString(portletRequest, "changeLog"),
				ServiceContextFactory.getInstance(portletRequest));
		}
		else if (cmd.equals(GoogleDriveWebConstants.GOOGLE_DRIVE_CHECKOUT)) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				portletRequest);

			serviceContext.setAttribute(
				GoogleDriveConstants.CHECK_OUT_IN_GOOGLE_DRIVE, Boolean.TRUE);

			_dlAppService.checkOutFileEntry(
				fileEntry.getFileEntryId(), serviceContext);
		}
		else if (cmd.equals(GoogleDriveWebConstants.GOOGLE_DRIVE_EDIT) &&
				 _googleDriveManager.isGoogleDriveFile(fileEntry)) {

				GoogleDriveFileReference.setCurrentGoogleDriveFileReference(
					_googleDriveManager.requestEditAccess(
						fileEntry,
						ServiceContextFactory.getInstance(portletRequest)));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private String _getFailureURL(PortletRequest portletRequest)
		throws PortalException {

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			_portal.getControlPanelPlid(portletRequest),
			PortletRequest.RENDER_PHASE);

		return liferayPortletURL.toString();
	}

	private String _getSuccessURL(PortletRequest portletRequest) {
		return _portal.getCurrentCompleteURL(
			_portal.getHttpServletRequest(portletRequest));
	}

	private void _performAuthorizationFlow(
			PortletRequest portletRequest, ActionResponse actionResponse)
		throws IOException, PortalException {

		String state = StringUtil.randomString(5);

		State.save(
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(portletRequest)),
			_portal.getUserId(portletRequest), _getSuccessURL(portletRequest),
			_getFailureURL(portletRequest), state);

		actionResponse.sendRedirect(
			_googleDriveManager.getAuthorizationURL(
				state, _oAuth2Helper.getRedirectUri(portletRequest)));
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private GoogleDriveManager _googleDriveManager;

	@Reference
	private OAuth2Helper _oAuth2Helper;

	@Reference
	private Portal _portal;

}