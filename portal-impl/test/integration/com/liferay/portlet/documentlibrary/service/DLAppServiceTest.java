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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLAppServiceTest extends BaseDLAppTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false,
			"Test DLAppService.txt");

		_userIds = new long[ServiceTestUtil.THREAD_COUNT];

		for (int i = 0; i < ServiceTestUtil.THREAD_COUNT; i++) {
			User user = UserTestUtil.addUser(
				"DLAppServiceTest" + (i + 1), group.getGroupId());

			_userIds[i] = user.getUserId();
		}
	}

	@After
	@Override
	public void tearDown() throws Exception {
		if (_fileEntry != null) {
			DLAppServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());
		}

		super.tearDown();

		for (int i = 0; i < ServiceTestUtil.THREAD_COUNT; i++) {
			UserLocalServiceUtil.deleteUser(_userIds[i]);
		}
	}

	@Test
	public void testAddFileEntriesConcurrently() throws Exception {
		DoAsUserThread[] doAsUserThreads = new DoAsUserThread[_userIds.length];

		_fileEntryIds = new long[_userIds.length];

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < doAsUserThreads.length; j++) {
				if (i == 0) {
					doAsUserThreads[j] = new AddFileEntryThread(_userIds[j], j);
				}
				else {
					doAsUserThreads[j] = new GetFileEntryThread(_userIds[j], j);
				}
			}

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				doAsUserThread.start();
			}

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				doAsUserThread.join();
			}

			int successCount = 0;

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				if (doAsUserThread.isSuccess()) {
					successCount++;
				}
			}

			String message =
				"Only " + successCount + " out of " + _userIds.length;

			if (i == 0) {
				message += " threads added file entries successfully";
			}
			else {
				message += " threads retrieved file entries successfully";
			}

			Assert.assertTrue(message, successCount == _userIds.length);
		}
	}

	@Test
	public void testAddFileEntryWithDuplicateName() throws Exception {
		addFileEntry(false);

		try {
			addFileEntry(false);

			Assert.fail("Able to add two files of the same name");
		}
		catch (DuplicateFileException dfe) {
		}

		try {
			addFileEntry(true);

			DLAppServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());
		}
		catch (DuplicateFileException dfe) {
			Assert.fail(
				"Unable to add two files of the same name in different " +
					"folders");
		}

		_fileEntry = null;
	}

	@Test
	public void testAddFileEntryWithInvalidMimeType() throws Exception {
		long folderId = parentFolder.getFolderId();
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		try {
			String name = "InvalidMime.txt";
			byte[] bytes = CONTENT.getBytes();

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, name,
				ContentTypes.APPLICATION_OCTET_STREAM, name, description,
				changeLog, bytes, serviceContext);

			Assert.assertEquals(
				ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());

			name = "InvalidMime";

			fileEntry = DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, null, name, description,
				changeLog, true, bytes, serviceContext);

			Assert.assertEquals(
				ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());
		}
		catch (Exception e) {
			Assert.fail(
				"Unable to add file with invalid mime type " +
					StackTraceUtil.getStackTrace(e));
		}
	}

	@Test
	public void testAddNullFileEntry() throws Exception {
		long folderId = parentFolder.getFolderId();
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		try {
			String name = "Bytes-null.txt";
			byte[] bytes = null;

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, name, ContentTypes.TEXT_PLAIN,
				name, description, changeLog, bytes, serviceContext);

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
				description, changeLog, true, bytes, serviceContext);

			String newName = "Bytes-changed.txt";

			bytes = CONTENT.getBytes();

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), newName, ContentTypes.TEXT_PLAIN,
				newName, description, changeLog, true, bytes, serviceContext);
		}
		catch (Exception e) {
			Assert.fail(
				"Unable to pass null byte[] " +
					StackTraceUtil.getStackTrace(e));
		}

		try {
			String name = "File-null.txt";
			File file = null;

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, name, ContentTypes.TEXT_PLAIN,
				name, description, changeLog, file, serviceContext);

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
				description, changeLog, true, file, serviceContext);

			try {
				String newName = "File-changed.txt";

				file = FileUtil.createTempFile(CONTENT.getBytes());

				DLAppServiceUtil.updateFileEntry(
					fileEntry.getFileEntryId(), newName,
					ContentTypes.TEXT_PLAIN, newName, description, changeLog,
					true, file, serviceContext);
			}
			finally {
				FileUtil.delete(file);
			}
		}
		catch (Exception e) {
			Assert.fail(
				"Unable to pass null File " + StackTraceUtil.getStackTrace(e));
		}

		try {
			String name = "IS-null.txt";
			InputStream is = null;

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, name, ContentTypes.TEXT_PLAIN,
				name, description, changeLog, is, 0, serviceContext);

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
				description, changeLog, true, is, 0, serviceContext);

			try {
				String newName = "IS-changed.txt";

				is = new ByteArrayInputStream(CONTENT.getBytes());

				DLAppServiceUtil.updateFileEntry(
					fileEntry.getFileEntryId(), newName,
					ContentTypes.TEXT_PLAIN, newName, description, changeLog,
					true, is, 0, serviceContext);
			}
			finally {
				if (is != null) {
					is.close();
				}
			}
		}
		catch (Exception e) {
			Assert.fail(
				"Unable to pass null InputStream " +
					StackTraceUtil.getStackTrace(e));
		}
	}

	@Test
	public void testAssetTags() throws Exception {
		long folderId = parentFolder.getFolderId();
		String name = "TestTags.txt";
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;
		byte[] bytes = CONTENT.getBytes();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		String[] assetTagNames = new String[] {"hello", "world"};

		serviceContext.setAssetTagNames(assetTagNames);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			group.getGroupId(), folderId, name, ContentTypes.TEXT_PLAIN, name,
			description, changeLog, bytes, serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		AssertUtils.assertEqualsSorted(assetTagNames, assetEntry.getTagNames());

		_fileEntry = fileEntry;

		search(_fileEntry, false, "hello", true);
		search(_fileEntry, false, "world", true);
		search(_fileEntry, false, "liferay", false);

		assetTagNames = new String[] {"hello", "world", "liferay"};

		serviceContext.setAssetTagNames(assetTagNames);

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
			description, changeLog, false, bytes, serviceContext);

		assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		AssertUtils.assertEqualsSorted(assetTagNames, assetEntry.getTagNames());

		_fileEntry = fileEntry;

		search(_fileEntry, false, "hello", true);
		search(_fileEntry, false, "world", true);
		search(_fileEntry, false, "liferay", true);

		DLAppServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());

		_fileEntry = null;
	}

	@Test
	public void testGetFileEntryWithoutFolderPermission() throws Exception {
		User user = UserTestUtil.addUser();

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder Permissions", true);

		DLAppTestUtil.addFileEntry(
			group.getGroupId(), folder.getFolderId(), false,
			"TestFileEntry.txt");

		deleteGuestPermission(folder);
		deletePowerUserPermission(folder);

		ServiceTestUtil.setUser(user);

		try {
			DLAppServiceUtil.getFileEntry(
				group.getGroupId(), folder.getFolderId(), "TestFileEntry.txt");

			Assert.fail("User is able to get file entry");
		}
		catch (PrincipalException pe) {
		}

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		UserLocalServiceUtil.deleteUser(user);

		DLAppServiceUtil.deleteFolder(folder.getFolderId());
	}

	@Test
	public void testGetFileEntryWithoutRootFolderPermission() throws Exception {
		User user = UserTestUtil.addUser();

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test SubFolder", true);

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			true, "TestFileEntry.txt");

		FileEntry subFileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), folder.getFolderId(), false,
			"TestSubFolderFileEntry.txt");

		ServiceTestUtil.removeResourcePermission(
			RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(group.getGroupId()),
			ActionKeys.VIEW);

		ServiceTestUtil.setUser(user);

		try {
			DLAppServiceUtil.getFileEntry(
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"TestFileEntry.txt");

			Assert.fail("User is able to get file entry");
		}
		catch (PrincipalException pe) {
		}

		try {
			DLAppServiceUtil.getFileEntry(
				group.getGroupId(), folder.getFolderId(),
				"TestSubFolderFileEntry.txt");

			Assert.fail("User is able to get file entry in subfolder");
		}
		catch (PrincipalException pe) {
		}

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		UserLocalServiceUtil.deleteUser(user);

		DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

		DLAppServiceUtil.deleteFileEntry(subFileEntry.getFileEntryId());

		DLAppServiceUtil.deleteFolder(folder.getFolderId());

		ServiceTestUtil.addResourcePermission(
			RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(group.getGroupId()),
			ActionKeys.VIEW);
	}

	@Test
	public void testGetFolder() throws Exception {
		User user = UserTestUtil.addUser();

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder Permissions", true);

		long folderId = folder.getFolderId();

		ServiceTestUtil.setUser(user);

		try {
			DLAppServiceUtil.getFolder(folderId);
		}
		catch (PrincipalException pe) {
			Assert.fail("User is unable to get folder");
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			DLPermission.check(
				permissionChecker, group.getGroupId(), ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			Assert.fail("User is unable to get root folder");
		}

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		DLAppServiceUtil.deleteFolder(folder.getFolderId());
	}

	@Test
	public void testGetFolderWithoutPermissions() throws Exception {
		User user = UserTestUtil.addUser();

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder Permissions", true);

		deleteGuestPermission(folder);
		deletePowerUserPermission(folder);

		ServiceTestUtil.setUser(user);

		try {
			DLAppServiceUtil.getFolder(folder.getFolderId());

			Assert.fail("User is able to get folder");
		}
		catch (PrincipalException pe) {
		}
		catch (NoSuchRepositoryEntryException nsree) {
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		ServiceTestUtil.removeResourcePermission(
			RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(group.getGroupId()),
			ActionKeys.VIEW);

		try {
			DLPermission.check(
				permissionChecker, group.getGroupId(), ActionKeys.VIEW);

			Assert.fail("User is able to get root folder");
		}
		catch (PrincipalException pe) {
		}

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		DLAppServiceUtil.deleteFolder(folder.getFolderId());

		UserLocalServiceUtil.deleteUser(user.getUserId());

		ServiceTestUtil.addResourcePermission(
			RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(group.getGroupId()),
			ActionKeys.VIEW);
	}

	@Test
	public void testSearchFileInRootFolder() throws Exception {
		searchFile(true);
	}

	@Test
	public void testSearchFileInSubFolder() throws Exception {
		searchFile(false);
	}

	@Test
	public void testVersionLabel() throws Exception {
		String fileName = "TestVersion.txt";

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, fileName);

		Assert.assertEquals(
			"Version label incorrect after add", "1.0", fileEntry.getVersion());

		fileEntry = updateFileEntry(
			fileEntry.getFileEntryId(), fileName, false);

		Assert.assertEquals(
			"Version label incorrect after minor update", "1.1",
			fileEntry.getVersion());

		fileEntry = updateFileEntry(fileEntry.getFileEntryId(), fileName, true);

		Assert.assertEquals(
			"Version label incorrect after major update", "2.0",
			fileEntry.getVersion());
	}

	protected FileEntry addFileEntry(boolean rootFolder) throws Exception {
		return DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), rootFolder,
			"Title.txt");
	}

	protected void deleteGuestPermission(Folder folder) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			group.getCompanyId(), RoleConstants.GUEST);

		deletePermission(folder, role);
	}

	protected void deletePermission(Folder folder, Role role) throws Exception {
		if (ResourceBlockLocalServiceUtil.isSupported(
				DLFolder.class.getName())) {

			ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
				group.getCompanyId(), group.getGroupId(),
				DLFolder.class.getName(), folder.getFolderId(),
				role.getRoleId(), new ArrayList<String>());
		}
		else {
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				group.getCompanyId(), DLFolder.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(folder.getFolderId()), role.getRoleId(),
				new String[0]);
		}
	}

	protected void deletePowerUserPermission(Folder folder) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			group.getCompanyId(), RoleConstants.POWER_USER);

		deletePermission(folder, role);
	}

	protected void search(
			FileEntry fileEntry, boolean rootFolder, String keywords,
			boolean assertTrue)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute("paginationType", "regular");
		searchContext.setCompanyId(fileEntry.getCompanyId());
		searchContext.setFolderIds(new long[] {fileEntry.getFolderId()});
		searchContext.setGroupIds(new long[] {fileEntry.getRepositoryId()});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Indexer indexer = IndexerRegistryUtil.getIndexer(
			DLFileEntryConstants.getClassName());

		Hits hits = indexer.search(searchContext);

		List<Document> documents = hits.toList();

		boolean found = false;

		for (Document document : documents) {
			long fileEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			if (fileEntryId == fileEntry.getFileEntryId()) {
				found = true;

				break;
			}
		}

		String message = "Search engine could not find ";

		if (rootFolder) {
			message += "root file entry by " + keywords;
		}
		else {
			message += "file entry by " + keywords;
		}

		message += " using query " + hits.getQuery();

		if (assertTrue) {
			Assert.assertTrue(message, found);
		}
		else {
			Assert.assertFalse(message, found);
		}
	}

	protected void searchFile(boolean rootFolder) throws Exception {
		FileEntry fileEntry = addFileEntry(rootFolder);

		search(fileEntry, rootFolder, "title", true);
		search(fileEntry, rootFolder, "content", true);

		DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	protected FileEntry updateFileEntry(
			long fileEntryId, String fileName, boolean majorVersion)
		throws Exception {

		return DLAppTestUtil.updateFileEntry(
			group.getGroupId(), fileEntryId, fileName, fileName, majorVersion);
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceTest.class);

	private FileEntry _fileEntry;
	private long[] _fileEntryIds;
	private long[] _userIds;

	private class AddFileEntryThread extends DoAsUserThread {

		public AddFileEntryThread(long userId, int index) {
			super(userId);

			_index = index;
		}

		@Override
		public boolean isSuccess() {
			return _success;
		}

		@Override
		protected void doRun() throws Exception {
			try {
				FileEntry fileEntry = DLAppTestUtil.addFileEntry(
					group.getGroupId(), parentFolder.getFolderId(), false,
					"Test-" + _index + ".txt");

				_fileEntryIds[_index] = fileEntry.getFileEntryId();

				if (_log.isDebugEnabled()) {
					_log.debug("Added file " + _index);
				}

				_success = true;
			}
			catch (Exception e) {
				_log.error("Unable to add file " + _index, e);
			}
		}

		private int _index;
		private boolean _success;

	}

	private class GetFileEntryThread extends DoAsUserThread {

		public GetFileEntryThread(long userId, int index) {
			super(userId);

			_index = index;
		}

		@Override
		public boolean isSuccess() {
			return _success;
		}

		@Override
		protected void doRun() throws Exception {
			try {
				FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
					_fileEntryIds[_index]);

				InputStream is = fileEntry.getContentStream();

				String content = StringUtil.read(is);

				if (CONTENT.equals(content)) {
					if (_log.isDebugEnabled()) {
						_log.debug("Retrieved file " + _index);
					}

					_success = true;
				}
			}
			catch (Exception e) {
				_log.error("Unable to add file " + _index, e);
			}
		}

		private int _index;
		private boolean _success;

	}

}