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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.ResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Sergio González
 */
@OSGiBeanProperties(
	property = {
		"resource.command.name=/document_library/edit_folder",
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY
	},
	service = ResourceCommand.class
)
public class EditFolderResourceCommand extends BaseResourceCommand {

	@Override
	protected void doProcessCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		downloadFolder(resourceRequest, resourceResponse);
	}

	protected void downloadFolder(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(resourceRequest, "repositoryId");
		long folderId = ParamUtil.getLong(resourceRequest, "folderId");

		File file = null;
		InputStream inputStream = null;

		try {
			String zipFileName = LanguageUtil.get(
				themeDisplay.getLocale(), "documents-and-media");

			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				Folder folder = DLAppServiceUtil.getFolder(folderId);

				zipFileName = folder.getName();
			}

			ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

			zipFolder(repositoryId, folderId, StringPool.SLASH, zipWriter);

			file = zipWriter.getFile();

			inputStream = new FileInputStream(file);

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse, zipFileName, inputStream,
				ContentTypes.APPLICATION_ZIP);
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			if (file != null) {
				file.delete();
			}
		}
	}

	protected void zipFolder(
			long repositoryId, long folderId, String path, ZipWriter zipWriter)
		throws Exception {

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				repositoryId, folderId, WorkflowConstants.STATUS_APPROVED,
				false, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Object entry : foldersAndFileEntriesAndFileShortcuts) {
			if (entry instanceof Folder) {
				Folder folder = (Folder)entry;

				zipFolder(
					folder.getRepositoryId(), folder.getFolderId(),
					path.concat(StringPool.SLASH).concat(folder.getName()),
					zipWriter);
			}
			else if (entry instanceof FileEntry) {
				FileEntry fileEntry = (FileEntry)entry;

				zipWriter.addEntry(
					path + StringPool.SLASH +
						HtmlUtil.escapeURL(fileEntry.getTitle()),
					fileEntry.getContentStream());
			}
		}
	}

}