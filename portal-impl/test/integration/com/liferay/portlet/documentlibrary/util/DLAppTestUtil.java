/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.InputStream;

import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;

import org.junit.Assert;

/**
 * This class contains helper methods to create integration tests for
 * Document and media library
 *
 * @author Alexander Chow
 * @author Sampsa Sohlman
 */
public abstract class DLAppTestUtil {

	public static DLFileRank addDLFileRank(long groupId, long fileEntryId)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setCompanyId(group.getCompanyId());

		return DLAppLocalServiceUtil.addFileRank(
			groupId, TestPropsValues.getCompanyId(),
			TestPropsValues.getUserId(), fileEntryId, serviceContext);
	}

	public static DLFileShortcut addDLFileShortcut(
			FileEntry fileEntry, long groupId, long folderId)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setCompanyId(group.getCompanyId());

		return DLAppServiceUtil.addFileShortcut(
			groupId, folderId, fileEntry.getFileEntryId(), serviceContext);
	}

	public static DLFileShortcut addDLFileShortcut(
			long groupId, FileEntry fileEntry)
		throws Exception {

		return addDLFileShortcut(fileEntry, groupId, fileEntry.getFolderId());
	}

	public static FileEntry addFileEntry(
			long groupId, long parentFolderId, boolean rootFolder,
			String fileName)
		throws Exception {

		return addFileEntry(
			groupId, parentFolderId, rootFolder, fileName, fileName);
	}

	public static FileEntry addFileEntry(
			long groupId, long parentFolderId, boolean rootFolder,
			String sourceFileName, String title)
		throws Exception {

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (!rootFolder) {
			folderId = parentFolderId;
		}

		return addFileEntry(groupId, folderId, sourceFileName, title);
	}

	public static FileEntry addFileEntry(
			long userId, long groupId, long folderId, String sourceFileName,
			String mimeType, String title, byte[] bytes, int workflowAction)
		throws Exception {

		if ((bytes == null) && Validator.isNotNull(sourceFileName)) {
			bytes = _CONTENT.getBytes();
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setWorkflowAction(workflowAction);

		return DLAppLocalServiceUtil.addFileEntry(
			userId, groupId, folderId, sourceFileName, mimeType, title,
			StringPool.BLANK, StringPool.BLANK, bytes, serviceContext);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName)
		throws Exception {

		return addFileEntry(groupId, folderId, sourceFileName, sourceFileName);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title)
		throws Exception {

		return addFileEntry(
			groupId, folderId, sourceFileName, title, null,
			WorkflowConstants.ACTION_PUBLISH);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title,
			boolean approved)
		throws Exception {

		int workflowAction = WorkflowConstants.ACTION_SAVE_DRAFT;

		if (approved) {
			workflowAction = WorkflowConstants.ACTION_PUBLISH;
		}

		return addFileEntry(
			groupId, folderId, sourceFileName, title, null, workflowAction);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title,
			byte[] bytes)
		throws Exception {

		return addFileEntry(
			groupId, folderId, sourceFileName, title, bytes,
			WorkflowConstants.ACTION_PUBLISH);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title,
			byte[] bytes, int workflowAction)
		throws Exception {

		return addFileEntry(
			groupId, folderId, sourceFileName, ContentTypes.TEXT_PLAIN, title,
			bytes, workflowAction);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String mimeType,
			String title)
		throws Exception {

		return addFileEntry(
			groupId, folderId, sourceFileName, mimeType, title, null,
			WorkflowConstants.ACTION_PUBLISH);

	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String mimeType,
			String title, byte[] bytes, int workflowAction)
		throws Exception {

		return addFileEntry(
			TestPropsValues.getUserId(), groupId, folderId, sourceFileName,
			mimeType, title, bytes, workflowAction);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title,
			String description, byte[] bytes, ServiceContext serviceContext)
		throws Exception {

		String changeLog = StringPool.BLANK;

		if ((bytes == null) && Validator.isNotNull(sourceFileName)) {
			bytes = _CONTENT.getBytes();
		}

		return DLAppLocalServiceUtil.addFileEntry(
			serviceContext.getUserId(), groupId, folderId, sourceFileName,
			ContentTypes.TEXT_PLAIN, title, description, changeLog, bytes,
			serviceContext);

	}

	public static Folder addFolder(
			long groupId, Folder parentFolder, String name)
		throws Exception {

		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (parentFolder!=null) {
			parentFolderId = parentFolder.getFolderId();
		}

		return addFolder(groupId, parentFolderId, name);
	}

	public static Folder addFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		return addFolder(groupId, parentFolderId, name, false);
	}

	public static Folder addFolder(
			long groupId, long parentFolderId, String name,
			boolean deleteExisting)
		throws Exception {

		String description = StringPool.BLANK;

		ServiceContext serviceContext = new ServiceContext();

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setCompanyId(group.getCompanyId());

		if (deleteExisting) {
			try {
				DLAppServiceUtil.deleteFolder(groupId, parentFolderId, name);
			}
			catch (NoSuchFolderException nsfe) {
			}
		}

		return DLAppServiceUtil.addFolder(
			groupId, parentFolderId, name, description, serviceContext);
	}

	/**
	 * Create DL folder to root level
	 *
	 * @param groupId
	 * @param name name of the folder
	 * @return Folder created folder
	 * @throws Exception
	 */
	public static Folder addFolderToRoot(long groupId, String name)
		throws Exception {

		return addFolder(groupId, null, name);
	}

	/**
	 * Compare two sourceFileEntries including DDM data
	 *
	 * @param sourceFileEntry
	 * @param targetFileEntry
	 * @param expectedFileEntryTypeGroupId expected FileEntryType groupId
	 *  if -1 then Assert is not done
	 * @throws Exception
	 */
	public static void assertDLFileEntries(
			DLFileEntry sourceFileEntry, DLFileEntry targetFileEntry,
			long expectedFileEntryTypeGroupId)
		throws Exception {

		Assert.assertNotNull(
			String.format("Verifying that FileEntry %s is found",
			sourceFileEntry.getUuid()), targetFileEntry);

		Assert.assertEquals(
			"Verify title", sourceFileEntry.getTitle(),
			targetFileEntry.getTitle());

		Assert.assertEquals(
			"Verify description", sourceFileEntry.getDescription(),
			targetFileEntry.getDescription());

		Assert.assertEquals(
			"Verify extension", sourceFileEntry.getExtension(),
			targetFileEntry.getExtension());

		assertInputStreams(
			String.format("Verifying that FileEntry %s content is equal",
			sourceFileEntry.getUuid()), sourceFileEntry.getContentStream(),
			targetFileEntry.getContentStream());

		DLFileEntryType dlFileEntryType = null;

		dlFileEntryType = DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(
			targetFileEntry.getFileEntryTypeId());

		// With -1 you can disable this check

		if (expectedFileEntryTypeGroupId!=-1) {
			Assert.assertEquals(
				"Verifying the DLFileEntryType groupId",
				dlFileEntryType.getGroupId(), expectedFileEntryTypeGroupId);
		}

		Map<String, Fields> sourceFieldsMap = sourceFileEntry.getFieldsMap(
			sourceFileEntry.getFileVersion().getFileVersionId());

		Map<String, Fields> targetFieldsMap = targetFileEntry.getFieldsMap(
			targetFileEntry.getFileVersion().getFileVersionId());

		for (Entry<String, Fields> sourceEntry : sourceFieldsMap.entrySet()) {

			Fields sourceFields = sourceEntry.getValue();
			Fields targetFields = targetFieldsMap.get(sourceEntry.getKey());

			for (String name : sourceFields.getNames()) {

				for (Locale locale : sourceFields.getAvailableLocales()) {

					Field sourceField = sourceFields.get(name);
					Field targetField = targetFields.get(name);

					Assert.assertNotNull(
						String.format("Verifying that target field '%s' exists",
						name), targetField);

					if (targetField!=null) {

						Assert.assertEquals(
							String.format(
							"Verifying field '%s' are same with locale %s",
							name, locale.toString()),
							sourceField.getValue(locale),
							targetField.getValue(locale));
					}
				}
			}
		}
	}

	/**
	 * Compare DL folder and files recursively
	 *
	 * <i>Support is not complete yet.</i>
	 *
	 * @param sourceGroupId
	 * @param targetGroupId
	 * @param sourceParentFolderId
	 * @param targetParentFolderId
	 * @param fileEntryTypeGroupId FileEntryType has to be at this group
	 * @throws Exception
	 */
	public static void assertDLFolders(
			long sourceGroupId, long targetGroupId, long sourceParentFolderId,
			long targetParentFolderId, long expectedFileEntryTypeGroupId)
		throws Exception {

		List<DLFileEntry> fileEntriesSource = DLFileEntryLocalServiceUtil.
			getFileEntries(sourceGroupId, sourceParentFolderId);

		for (DLFileEntry sourceFileEntry : fileEntriesSource) {

			DLFileEntry targetFileEntry = DLFileEntryLocalServiceUtil.
				getDLFileEntryByUuidAndGroupId(sourceFileEntry.getUuid(),
				targetGroupId);

			assertDLFileEntries(
				sourceFileEntry, targetFileEntry, expectedFileEntryTypeGroupId);
		}

		List<DLFolder> foldersSource = DLFolderLocalServiceUtil.getFolders(
			sourceGroupId, sourceParentFolderId);

		for (DLFolder sourceFolder : foldersSource) {
			DLFolder targetFolder = DLFolderLocalServiceUtil.
				getDLFolderByUuidAndGroupId(sourceFolder.getUuid(),
				targetGroupId);
			Assert.assertNotNull(
				String.format("Verifying that Folder %s is found",
				sourceFolder.getUuid()), targetFolder);

			assertDLFolders(
				sourceGroupId, targetGroupId, sourceFolder.getFolderId(),
				targetFolder.getFolderId(), expectedFileEntryTypeGroupId);
		}
	}

	/**
	 * Compare site's DL folder structure
	 *
	 * <i>Support is not complete yet.</i>
	 *
	 * @param sourceGroupId
	 * @param targetGroupId
	 * @param sourceParentFolderId
	 * @param targetParentFolderId
	 * @throws Exception
	 */
	public static void assertDlFoldersOfSites(
			long sourceGroupId, long targetGroupId,
			long expectedFileEntryTypeGroupId)
		throws Exception {

		_log.debug(
			String.format("sourceGroupId=%d, targetGroupid, %d %d",
			sourceGroupId, targetGroupId, expectedFileEntryTypeGroupId));

		assertDLFolders(
			sourceGroupId, targetGroupId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			expectedFileEntryTypeGroupId);
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String sourceFileName, String title)
		throws Exception {

		return updateFileEntry(
			groupId, fileEntryId, sourceFileName, title, false);
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String sourceFileName, String title,
			boolean majorVersion)
		throws Exception {

		return updateFileEntry(
			groupId, fileEntryId, sourceFileName, ContentTypes.TEXT_PLAIN,
			title, majorVersion);
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String sourceFileName,
			String mimeType, String title, boolean majorVersion)
		throws Exception {

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		byte[] bytes = null;

		if (Validator.isNotNull(sourceFileName)) {
			String newContent = _CONTENT + "\n" + System.currentTimeMillis();

			bytes = newContent.getBytes();
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setCompanyId(group.getCompanyId());

		return DLAppServiceUtil.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, bytes, serviceContext);
	}

	/**
	 * Print site's DL folder structure to lot on debug level
	 *
	 * @param groupId Site's group id where folder should be printed out.
	 * @throws Exception
	 */
	public void debugFolderStructure(long groupId) throws Exception {
		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler();
			debugFolderStructure(
				sb, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, 0);
		}
	}

	protected static void assertInputStreams(
			String message, InputStream sourceStream, InputStream targetStream)
		throws Exception {

		byte[] source = new byte[1024];
		byte[] target = new byte[1024];

		int sourceResult = sourceStream.read(source);
		int targetResult = targetStream.read(target);

		while (sourceResult!=-1 ||
			 targetResult!=-1) {
			Assert.assertArrayEquals(message, source, target);
			sourceResult = sourceStream.read(source);
			targetResult = targetStream.read(target);
		}
	}

	protected static void debugFolderStructure(
			StringBundler sb, long groupId, long parentFolderId, int depth)
		throws Exception {

		List<DLFileEntry> listTo = DLFileEntryLocalServiceUtil.getFileEntries(
			groupId, parentFolderId);

		if (!_log.isDebugEnabled()) {
			return;
		}

		for (DLFileEntry dlFileEntry : listTo) {
			sb.append(
				String.format("[%s] %s.%s %s ( %s ) - File", intent(depth),
				dlFileEntry.getName(), dlFileEntry.getTitle(),
				dlFileEntry.getExtension(), dlFileEntry.getDescription()));

			sb.append(StringPool.RETURN_NEW_LINE);
		}

		List<DLFolder> listFolderTo = DLFolderLocalServiceUtil.getFolders(
			groupId, parentFolderId);

		for (DLFolder dlFolder : listFolderTo) {
			sb.append(
				String.format("%s%s - Folder", intent(depth),
				dlFolder.getName()));

			sb.append(StringPool.RETURN_NEW_LINE);

			debugFolderStructure(
				sb, groupId, dlFolder.getFolderId(), depth + 2);
		}
	}

	protected static String intent(int amount) {
		if (amount==0) {
			return "";
		}

		StringBundler sb = new StringBundler();

		for (int i=0; i<=amount; i++) {
			sb.append(' ');
		}

		return sb.toString();
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	private static Log _log = LogFactoryUtil.getLog(DLAppTestUtil.class);

}