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

import com.liferay.portal.kernel.flash.FlashMagicBytesUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.ActionContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.MVCPortletAction;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.RenderContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.ResourceContext;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Charles May
 * @author Bruno Farache
 */
@OSGiBeanProperties(
	property = "path=/document_library/get_file", service = StrutsAction.class
)
public class GetFileAction implements MVCPortletAction, StrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			long fileEntryId = ParamUtil.getLong(request, "fileEntryId");

			long folderId = ParamUtil.getLong(request, "folderId");
			String name = ParamUtil.getString(request, "name");
			String title = ParamUtil.getString(request, "title");
			String version = ParamUtil.getString(request, "version");

			long fileShortcutId = ParamUtil.getLong(request, "fileShortcutId");

			String uuid = ParamUtil.getString(request, "uuid");

			String targetExtension = ParamUtil.getString(
				request, "targetExtension");

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(
				request, "groupId", themeDisplay.getScopeGroupId());

			getFile(
				fileEntryId, folderId, name, title, version, fileShortcutId,
				uuid, groupId, targetExtension, request, response);

			return null;
		}
		catch (PrincipalException pe) {
			processPrincipalException(pe, request, response);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	@Override
	public String execute(
			StrutsAction originalStrutsAction, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		return execute(request, response);
	}

	@Override
	public String processAction(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ActionContext actionContext)
		throws PortletException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		try {
			long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

			long folderId = ParamUtil.getLong(actionRequest, "folderId");
			String name = ParamUtil.getString(actionRequest, "name");
			String title = ParamUtil.getString(actionRequest, "title");
			String version = ParamUtil.getString(actionRequest, "version");

			long fileShortcutId = ParamUtil.getLong(
				actionRequest, "fileShortcutId");

			String uuid = ParamUtil.getString(actionRequest, "uuid");

			String targetExtension = ParamUtil.getString(
				actionRequest, "targetExtension");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(
				actionRequest, "groupId", themeDisplay.getScopeGroupId());

			getFile(
				fileEntryId, folderId, name, title, version, fileShortcutId,
				uuid, groupId, targetExtension, request, response);

			return "/html" + ActionConstants.COMMON_NULL;
		}
		catch (NoSuchFileEntryException nsfee) {
			try {
				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND, nsfee, actionRequest,
					actionResponse);
			}
			catch (IOException ioe) {
				throw new PortletException(ioe);
			}
		}
		catch (PrincipalException pe) {
			processPrincipalException(pe, request, response);
		}
		catch (Exception e) {
			try {
				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
			catch (IOException ioe) {
				throw new PortletException(ioe);
			}
		}

		return null;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			RenderContext renderContext)
		throws IOException, PortletException {

		return null;
	}

	@Override
	public String serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			ResourceContext resourceContext)
		throws IOException, PortletException {

		return null;
	}

	protected void getFile(
			long fileEntryId, long folderId, String name, String title,
			String version, long fileShortcutId, String uuid, long groupId,
			String targetExtension, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		if (name.startsWith("DLFE-")) {
			name = name.substring(5);
		}

		name = FileUtil.stripExtension(name);

		FileEntry fileEntry = null;

		if (Validator.isNotNull(uuid) && (groupId > 0)) {
			fileEntry = DLAppServiceUtil.getFileEntryByUuidAndGroupId(
				uuid, groupId);

			folderId = fileEntry.getFolderId();
		}

		if (fileEntryId > 0) {
			fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);
		}
		else if (fileShortcutId <= 0) {
			if (Validator.isNotNull(title)) {
				fileEntry = DLAppServiceUtil.getFileEntry(
					groupId, folderId, title);
			}
			else if (Validator.isNotNull(name)) {
				DLFileEntry dlFileEntry =
					DLFileEntryLocalServiceUtil.fetchFileEntryByName(
						groupId, folderId, name);

				if (dlFileEntry == null) {

					// LPS-30374

					List<DLFileEntry> dlFileEntries =
						DLFileEntryLocalServiceUtil.getFileEntries(
							folderId, name);

					if (!dlFileEntries.isEmpty()) {
						dlFileEntry = dlFileEntries.get(0);
					}
				}

				if (dlFileEntry != null) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)request.getAttribute(
							WebKeys.THEME_DISPLAY);

					PermissionChecker permissionChecker =
						themeDisplay.getPermissionChecker();

					DLFileEntryPermission.check(
						permissionChecker, dlFileEntry, ActionKeys.VIEW);

					fileEntry = new LiferayFileEntry(dlFileEntry);
				}
			}
		}
		else {
			FileShortcut fileShortcut = DLAppServiceUtil.getFileShortcut(
				fileShortcutId);

			fileEntryId = fileShortcut.getToFileEntryId();

			fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);
		}

		if (Validator.isNull(version)) {
			if ((fileEntry != null) &&
				Validator.isNotNull(fileEntry.getVersion())) {

				version = fileEntry.getVersion();
			}
			else {
				throw new NoSuchFileEntryException(
					"{fileEntryId=" + fileEntryId + "}");
			}
		}

		FileVersion fileVersion = fileEntry.getFileVersion(version);

		InputStream is = fileVersion.getContentStream(true);

		String fileName = fileVersion.getTitle();

		String sourceExtension = fileVersion.getExtension();

		if (Validator.isNotNull(sourceExtension) &&
			!fileName.endsWith(StringPool.PERIOD + sourceExtension)) {

			fileName += StringPool.PERIOD + sourceExtension;
		}

		long contentLength = fileVersion.getSize();
		String contentType = fileVersion.getMimeType();

		if (Validator.isNotNull(targetExtension)) {
			String id = DLUtil.getTempFileId(
				fileEntry.getFileEntryId(), version);

			File convertedFile = DocumentConversionUtil.convert(
				id, is, sourceExtension, targetExtension);

			if (convertedFile != null) {
				fileName = FileUtil.stripExtension(fileName).concat(
					StringPool.PERIOD).concat(targetExtension);
				is = new FileInputStream(convertedFile);
				contentLength = convertedFile.length();
				contentType = MimeTypesUtil.getContentType(fileName);
			}
		}

		FlashMagicBytesUtil.Result flashMagicBytesUtilResult =
			FlashMagicBytesUtil.check(is);

		if (flashMagicBytesUtilResult.isFlash()) {
			fileName = FileUtil.stripExtension(fileName) + ".swf";
		}

		is = flashMagicBytesUtilResult.getInputStream();

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentLength, contentType);
	}

	// TODO: PERMITE PEDIR VIA GET

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void processPrincipalException(
			Throwable t, HttpServletRequest request,
			HttpServletResponse response)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		User user = permissionChecker.getUser();

		if ((user != null) && !user.isDefaultUser()) {
			try {
				PortalUtil.sendError(
					HttpServletResponse.SC_UNAUTHORIZED, (Exception)t, request,
					response);
			}
			catch (IOException | ServletException e) {
				throw new PortletException(e);
			}

			return;
		}

		String redirect = PortalUtil.getPathMain() + "/portal/login";

		String currentURL = PortalUtil.getCurrentURL(request);

		redirect = HttpUtil.addParameter(redirect, "redirect", currentURL);

		try {
			response.sendRedirect(redirect);
		}
		catch (IOException ioe) {
			throw new PortletException(ioe);
		}
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}