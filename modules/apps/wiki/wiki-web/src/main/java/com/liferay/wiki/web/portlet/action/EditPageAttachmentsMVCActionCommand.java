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

package com.liferay.wiki.web.portlet.action;

import com.liferay.document.library.web.portlet.action.EditFileEntryMVCActionCommand;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.RequestContentLengthException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.trash.service.TrashEntryService;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.taglib.util.RestoreEntryUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.service.WikiPageService;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/wiki/edit_page_attachment"
	},
	service = MVCActionCommand.class
)
public class EditPageAttachmentsMVCActionCommand
	extends EditFileEntryMVCActionCommand {

	protected void addAttachment(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		int numOfFiles = ParamUtil.getInteger(actionRequest, "numOfFiles");

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>();

		try {
			if (numOfFiles == 0) {
				String fileName = uploadPortletRequest.getFileName("file");
				InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"file");

				if (inputStream != null) {
					ObjectValuePair<String, InputStream> inputStreamOVP =
						new ObjectValuePair<>(fileName, inputStream);

					inputStreamOVPs.add(inputStreamOVP);
				}
			}
			else {
				for (int i = 1; i <= numOfFiles; i++) {
					String fileName = uploadPortletRequest.getFileName(
						"file" + i);
					InputStream inputStream =
						uploadPortletRequest.getFileAsStream("file" + i);

					if (inputStream == null) {
						continue;
					}

					ObjectValuePair<String, InputStream> inputStreamOVP =
						new ObjectValuePair<>(fileName, inputStream);

					inputStreamOVPs.add(inputStreamOVP);
				}
			}

			_wikiPageService.addPageAttachments(nodeId, title, inputStreamOVPs);
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				InputStream inputStream = inputStreamOVP.getValue();

				StreamUtil.cleanUp(inputStream);
			}
		}
	}

	protected void addMultipleFileEntries(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String selectedFileName,
			List<KeyValuePair> validFileNameKVPs,
			List<KeyValuePair> invalidFileNameKVPs)
		throws Exception {

		String originalSelectedFileName = selectedFileName;

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		FileEntry tempFileEntry = null;

		try {
			tempFileEntry = TempFileEntryUtil.getTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				_TEMP_FOLDER_NAME, selectedFileName);

			InputStream inputStream = tempFileEntry.getContentStream();
			String mimeType = tempFileEntry.getMimeType();

			_wikiPageService.addPageAttachment(
				nodeId, title, selectedFileName, inputStream, mimeType);

			validFileNameKVPs.add(
				new KeyValuePair(selectedFileName, originalSelectedFileName));
		}
		catch (Exception e) {
			String errorMessage = getAddMultipleFileEntriesErrorMessage(
				portletConfig, actionRequest, actionResponse, e);

			KeyValuePair invalidFileNameKVP = new KeyValuePair(
				selectedFileName, errorMessage);

			invalidFileNameKVPs.add(invalidFileNameKVP);
		}
		finally {
			if (tempFileEntry != null) {
				TempFileEntryUtil.deleteTempFileEntry(
					tempFileEntry.getFileEntryId());
			}
		}
	}

	protected void addTempAttachment(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String sourceFileName = uploadPortletRequest.getFileName("file");

		InputStream inputStream = null;

		try {
			inputStream = uploadPortletRequest.getFileAsStream("file");

			String mimeType = uploadPortletRequest.getContentType("file");

			_wikiPageService.addTempFileEntry(
				nodeId, _TEMP_FOLDER_NAME, sourceFileName, inputStream,
				mimeType);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void deleteAttachment(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		String attachment = ParamUtil.getString(actionRequest, "fileName");

		TrashedModel trashedModel = null;

		if (moveToTrash) {
			FileEntry fileEntry = _wikiPageService.movePageAttachmentToTrash(
				nodeId, title, attachment);

			if (fileEntry.getModel() instanceof DLFileEntry) {
				trashedModel = (DLFileEntry)fileEntry.getModel();
			}
		}
		else {
			_wikiPageService.deletePageAttachment(nodeId, title, attachment);
		}

		if (moveToTrash && (trashedModel != null)) {
			TrashUtil.addTrashSessionMessages(
				actionRequest, trashedModel, Constants.REMOVE);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	protected void deleteTempAttachment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			_wikiPageService.deleteTempFileEntry(
				nodeId, fileName, _TEMP_FOLDER_NAME);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("deleted", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		PortletConfig portletConfig = getPortletConfig(actionRequest);

		try {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				Throwable cause = uploadException.getCause();

				if (uploadException.isExceededFileSizeLimit()) {
					throw new FileSizeException(cause);
				}

				if (uploadException.isExceededLiferayFileItemSizeLimit()) {
					throw new LiferayFileItemException(cause);
				}

				if (uploadException.isExceededRequestContentLengthLimit()) {
					throw new RequestContentLengthException(cause);
				}

				throw new PortalException(cause);
			}
			else if (cmd.equals(Constants.ADD)) {
				addAttachment(actionRequest);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				addMultipleFileEntries(
					portletConfig, actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				addTempAttachment(actionRequest);
			}
			else if (cmd.equals(Constants.CHECK)) {
				JSONObject jsonObject = RestoreEntryUtil.checkEntry(
					actionRequest);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);

				return;
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteAttachment(actionRequest, false);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				deleteTempAttachment(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.EMPTY_TRASH)) {
				emptyTrash(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteAttachment(actionRequest, true);
			}
			else if (cmd.equals(Constants.RENAME)) {
				restoreRename(actionRequest);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreEntries(actionRequest);
			}
			else if (cmd.equals(Constants.OVERRIDE)) {
				restoreOverride(actionRequest);
			}

			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				actionResponse.setRenderParameter("mvcPath", "/null.jsp");
			}
		}
		catch (NoSuchNodeException | NoSuchPageException |
			   PrincipalException e) {

			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter("mvcPath", "/wiki/error.jsp");
		}
		catch (Exception e) {
			handleUploadException(
				portletConfig, actionRequest, actionResponse, cmd, e);
		}
	}

	protected void emptyTrash(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		_wikiPageService.deleteTrashPageAttachments(nodeId, title);
	}

	@Override
	protected String[] getAllowedFileExtensions(
			PortletConfig portletConfig, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws PortalException {

		return PrefsPropsUtil.getStringArray(
			PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA);
	}

	protected void restoreEntries(ActionRequest actionRequest)
		throws Exception {

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		if (trashEntryId > 0) {
			_trashEntryService.restoreEntry(trashEntryId);

			return;
		}

		long[] restoreEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreEntryId : restoreEntryIds) {
			_trashEntryService.restoreEntry(restoreEntryId);
		}
	}

	protected void restoreOverride(ActionRequest actionRequest)
		throws Exception {

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		long duplicateEntryId = ParamUtil.getLong(
			actionRequest, "duplicateEntryId");

		_trashEntryService.restoreEntry(trashEntryId, duplicateEntryId, null);
	}

	protected void restoreRename(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		String newName = ParamUtil.getString(actionRequest, "newName");

		if (Validator.isNull(newName)) {
			String oldName = ParamUtil.getString(actionRequest, "oldName");

			newName = TrashUtil.getNewName(themeDisplay, null, 0, oldName);
		}

		_trashEntryService.restoreEntry(trashEntryId, 0, newName);
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	private static final String _TEMP_FOLDER_NAME =
		EditPageAttachmentsMVCActionCommand.class.getName();

	private volatile TrashEntryService _trashEntryService;
	private volatile WikiPageService _wikiPageService;

}