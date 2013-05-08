/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARFileSizeException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.LayoutPrototypeException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.action.EditFileEntryAction;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.layoutsadmin.util.ExportImportUtil;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.File;
import java.io.InputStream;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class ImportLayoutsAction extends EditFileEntryAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD_TEMP)) {
				addTempFileEntry(actionRequest, actionResponse);

				validateImportLayoutsFile(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				deleteTempFileEntry(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.IMPORT)) {
				importLayouts(actionRequest, actionResponse);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				handleUploadException(
					portletConfig, actionRequest, actionResponse, cmd, e);
			}
			else {
				if ((e instanceof LARFileException) ||
					(e instanceof LARFileSizeException) ||
					(e instanceof LARTypeException)) {

					SessionErrors.add(actionRequest, e.getClass());
				}
				else if ((e instanceof LayoutPrototypeException) ||
						 (e instanceof LocaleException)) {

					SessionErrors.add(actionRequest, e.getClass(), e);
				}
				else {
					_log.error(e, e);

					SessionErrors.add(
						actionRequest, LayoutImportException.class.getName());
				}
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.export_layouts"));
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/portlet/layouts_admin/export_import_resources.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void addTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		checkExceededSizeLimit(uploadPortletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		// Delete existing fileEntries

		deleteTempFileEntry(themeDisplay.getScopeGroupId());

		InputStream inputStream = null;

		try {
			String sourceFileName = uploadPortletRequest.getFileName("file");

			inputStream = uploadPortletRequest.getFileAsStream("file");

			String contentType = uploadPortletRequest.getContentType("file");

			LayoutServiceUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), sourceFileName,
				ExportImportUtil.TEMP_FOLDER_NAME, inputStream, contentType);
		}
		catch (Exception e) {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if ((uploadException != null) &&
				(uploadException.getCause()
					instanceof FileUploadBase.IOFileUploadException)) {

				// Cancelled a temporary upload

			}
			else if ((uploadException != null) &&
					 uploadException.isExceededSizeLimit()) {

				throw new FileSizeException(uploadException.getCause());
			}
			else {
				throw e;
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void checkExceededSizeLimit(HttpServletRequest request)
		throws PortalException {

		UploadException uploadException = (UploadException)request.getAttribute(
			WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			if (uploadException.isExceededSizeLimit()) {
				throw new LARFileSizeException(uploadException.getCause());
			}

			throw new PortalException(uploadException.getCause());
		}
	}

	protected void deleteTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			String fileName = ParamUtil.getString(actionRequest, "fileName");

			LayoutServiceUtil.deleteTempFileEntry(
				themeDisplay.getScopeGroupId(), fileName,
				ExportImportUtil.TEMP_FOLDER_NAME);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = LanguageUtil.get(
				themeDisplay.getLocale(),
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("deleted", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected void deleteTempFileEntry(long groupId)
		throws PortalException, SystemException {

		String[] tempFileEntryNames = LayoutServiceUtil.getTempFileEntryNames(
			groupId, ExportImportUtil.TEMP_FOLDER_NAME);

		for (String tempFileEntryName : tempFileEntryNames) {
			LayoutServiceUtil.deleteTempFileEntry(
				groupId, tempFileEntryName, ExportImportUtil.TEMP_FOLDER_NAME);
		}
	}

	protected void handleUploadException(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String cmd, Exception e)
		throws Exception {

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		response.setContentType(ContentTypes.TEXT_HTML);
		response.setStatus(HttpServletResponse.SC_OK);

		int errorType = 0;
		String errorMessage = StringPool.BLANK;

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (e instanceof DuplicateFileException ||
			e instanceof FileExtensionException ||
			e instanceof FileNameException ||
			e instanceof LARFileException ||
			e instanceof LARFileSizeException ||
			e instanceof LARTypeException ||
			e instanceof LayoutPrototypeException ||
			e instanceof LocaleException ||
			e instanceof FileSizeException) {

			if (e instanceof DuplicateFileException) {
				errorType =
					ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;

				errorMessage = LanguageUtil.get(
					themeDisplay.getLocale(),
					"please-enter-a-unique-document-name");
			}
			else if (e instanceof FileExtensionException) {
				errorType =
					ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;

				errorMessage = LanguageUtil.format(
					themeDisplay.getLocale(),
					"document-names-must-end-with-one-of-the-following-" +
						"extensions",
					StringUtil.merge(
						getAllowedFileExtensions(
							portletConfig, actionRequest, actionResponse)));
			}
			else if (e instanceof FileNameException) {
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;

				errorMessage = LanguageUtil.get(
					themeDisplay.getLocale(),
					"please-enter-a-file-with-a-valid-file-name");
			}
			else if (e instanceof FileSizeException ||
					 e instanceof LARFileSizeException) {

				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;

				long fileMaxSize = PrefsPropsUtil.getLong(
					PropsKeys.DL_FILE_MAX_SIZE);

				if (fileMaxSize == 0) {
					fileMaxSize = PrefsPropsUtil.getLong(
						PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
				}

				fileMaxSize /= 1024;

				errorMessage = LanguageUtil.format(
					themeDisplay.getLocale(),
					"please-enter-a-file-with-a-valid-file-size-no-larger" +
						"-than-x", fileMaxSize);
			}
			else if (e instanceof LARTypeException) {
				LARTypeException lte = (LARTypeException)e;

				errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;

				errorMessage = LanguageUtil.format(
					themeDisplay.getLocale(),
					"please-import-a-lar-file-of-the-correct-type" +
						"-x-is-not-valid",
					lte.getMessage());

			}
			else if (e instanceof LARFileException) {
				errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;

				errorMessage = LanguageUtil.get(
					themeDisplay.getLocale(),
					"please-specify-a-lar-file-to-import");
			}
			else if (e instanceof LocaleException) {
				LocaleException le = (LocaleException)e;

				errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;

				errorMessage = LanguageUtil.format(
					themeDisplay.getLocale(),
					"the-available-languages-in-the-lar-file-x-do" +
						"-not-match-the-portal's-available-languages-x",
					new String[] {
						StringUtil.merge(
							le.getSourceAvailableLocales(),
							StringPool.COMMA_AND_SPACE),
						StringUtil.merge(
							le.getTargetAvailableLocales(),
							StringPool.COMMA_AND_SPACE)});
			}
			else if (e instanceof LayoutPrototypeException) {
				LayoutPrototypeException lpe = (LayoutPrototypeException)e;

				errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;

				List<Tuple> missingLayoutPrototypes =
					lpe.getMissingLayoutPrototypes();

				StringBundler sb = new StringBundler(
					3 + (10 * missingLayoutPrototypes.size()));

				sb.append(
					LanguageUtil.get(
						themeDisplay.getLocale(),
						"the-lar-file-could-not-be-imported-because-it-" +
							"requires-page-templates-or-site-templates-" +
							"that-could-not-be-found.-please-import-the-" +
							"following-templates-manually"));

				sb.append("<ul>");

				for (Tuple missingLayoutPrototype : missingLayoutPrototypes) {
					String layoutPrototypeClassName =
						(String)missingLayoutPrototype.getObject(0);
					String layoutPrototypeUuid =
						(String)missingLayoutPrototype.getObject(1);
					String layoutPrototypeName =
						(String)missingLayoutPrototype.getObject(2);

					sb.append("<li>");
					sb.append(
						ResourceActionsUtil.getModelResource(
							themeDisplay.getLocale(),
							layoutPrototypeClassName));
					sb.append(StringPool.COLON);
					sb.append(StringPool.SPACE);
					sb.append("<strong>");
					sb.append(layoutPrototypeName);
					sb.append("</strong>");
					sb.append(StringPool.OPEN_PARENTHESIS);
					sb.append(layoutPrototypeUuid);
					sb.append(StringPool.CLOSE_PARENTHESIS);

					sb.append("</li>");
				}

				sb.append("</ul>");

				errorMessage = sb.toString();
			}
		}
		else {
			errorType = ServletResponseConstants.SC_FILE_CUSTOM_EXCEPTION;
		}

		deleteTempFileEntry(themeDisplay.getScopeGroupId());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("status", errorType);
		jsonObject.put("message", errorMessage);

		writeJSON(actionRequest, actionResponse, jsonObject);

		ServletResponseUtil.write(response, String.valueOf(errorType));
	}

	protected void importLayouts(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");

		FileEntry fileEntry = ExportImportUtil.getTempFileEntry(
			groupId, themeDisplay.getUserId());

		File file = DLFileEntryLocalServiceUtil.getFile(
			themeDisplay.getUserId(), fileEntry.getFileEntryId(),
			fileEntry.getVersion(), false);

		if ((file == null) || !file.exists()) {
			throw new LARFileException("Import file does not exist");
		}

		boolean successfulRename = false;

		File newFile = null;

		try {
			String newFilePath = StringUtil.replace(
				file.getPath(), file.getName(), fileEntry.getTitle());

			newFile = new File(newFilePath);

			successfulRename = file.renameTo(newFile);

			if (!successfulRename) {
				newFile = FileUtil.createTempFile(fileEntry.getExtension());

				FileUtil.copyFile(file, newFile);
			}

			LayoutServiceUtil.importLayouts(
				groupId, privateLayout, actionRequest.getParameterMap(),
				newFile);

			addSuccessMessage(actionRequest, actionResponse);
		}
		finally {
			if (successfulRename) {
				newFile.renameTo(file);
			}
			else {
				FileUtil.delete(newFile);
			}
		}
	}

	protected void validateImportLayoutsFile(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");

		FileEntry fileEntry = ExportImportUtil.getTempFileEntry(
			groupId, themeDisplay.getUserId());

		File file = DLFileEntryLocalServiceUtil.getFile(
			themeDisplay.getUserId(), fileEntry.getFileEntryId(),
			fileEntry.getVersion(), false);

		if ((file == null) || !file.exists()) {
			throw new LARFileException("Import file does not exist");
		}

		boolean successfulRename = false;

		File newFile = null;

		try {
			String newFilePath = StringUtil.replace(
				file.getPath(), file.getName(), fileEntry.getTitle());

			newFile = new File(newFilePath);

			successfulRename = file.renameTo(newFile);

			if (!successfulRename) {
				newFile = FileUtil.createTempFile(fileEntry.getExtension());

				FileUtil.copyFile(file, newFile);
			}

			LayoutServiceUtil.validateImportLayoutsFile(
				groupId, privateLayout, actionRequest.getParameterMap(),
				newFile);
		}
		finally {
			if (successfulRename) {
				newFile.renameTo(file);
			}
			else {
				FileUtil.delete(newFile);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ImportLayoutsAction.class);

}