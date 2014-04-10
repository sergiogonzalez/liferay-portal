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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DirectoryNameException;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLConfig;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.validator.DLValidatorUtil;
import com.liferay.portlet.documentlibrary.util.validator.DirectoryNameValidator;
import com.liferay.portlet.documentlibrary.util.validator.FileExtensionValidator;
import com.liferay.portlet.documentlibrary.util.validator.FileNameValidator;
import com.liferay.portlet.documentlibrary.util.validator.FileSizeValidator;
import com.liferay.portlet.documentlibrary.util.validator.VersionValidator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Edward Han
 */
public class DLStoreImpl implements DLStore {

	@Override
	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		if (!isValidName(dirName) || dirName.equals("/")) {
			throw new DirectoryNameException(dirName);
		}

		store.addDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, byte[] bytes)
		throws PortalException, SystemException {

		addFile(
			companyId, repositoryId, fileName, validateFileExtension, bytes,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, byte[] bytes, DLConfig dlConfig)
		throws PortalException, SystemException {

		validate(fileName, validateFileExtension, bytes, dlConfig);

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(bytes);
		}

		store.addFile(companyId, repositoryId, fileName, bytes, dlConfig);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, File file)
		throws PortalException, SystemException {

		addFile(
			companyId, repositoryId, fileName, validateFileExtension, file,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, File file, DLConfig dlConfig)
		throws PortalException, SystemException {

		validate(fileName, validateFileExtension, file, dlConfig);

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(file);
		}

		store.addFile(companyId, repositoryId, fileName, file, dlConfig);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		addFile(
			companyId, repositoryId, fileName, validateFileExtension, is,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, InputStream is, DLConfig dlConfig)
		throws PortalException, SystemException {

		if (is instanceof ByteArrayFileInputStream) {
			ByteArrayFileInputStream byteArrayFileInputStream =
				(ByteArrayFileInputStream)is;

			File file = byteArrayFileInputStream.getFile();

			addFile(
				companyId, repositoryId, fileName, validateFileExtension, file,
				dlConfig);

			return;
		}

		validate(fileName, validateFileExtension, is, dlConfig);

		if (!PropsValues.DL_STORE_ANTIVIRUS_ENABLED ||
			!AntivirusScannerUtil.isActive()) {

			store.addFile(companyId, repositoryId, fileName, is, dlConfig);
		}
		else {
			File tempFile = null;

			try {
				if (is.markSupported()) {
					is.mark(is.available() + 1);

					AntivirusScannerUtil.scan(is);

					is.reset();

					store.addFile(
						companyId, repositoryId, fileName, is, dlConfig);
				}
				else {
					tempFile = FileUtil.createTempFile();

					FileUtil.write(tempFile, is);

					AntivirusScannerUtil.scan(tempFile);

					store.addFile(
						companyId, repositoryId, fileName, tempFile, dlConfig);
				}
			}
			catch (IOException ioe) {
				throw new SystemException(
					"Unable to scan file " + fileName, ioe);
			}
			finally {
				if (tempFile != null) {
					tempFile.delete();
				}
			}
		}
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException, SystemException {

		addFile(
			companyId, repositoryId, fileName, bytes,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		addFile(companyId, repositoryId, fileName, true, bytes, dlConfig);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException, SystemException {

		addFile(
			companyId, repositoryId, fileName, file,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		addFile(companyId, repositoryId, fileName, true, file, dlConfig);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException {

		addFile(
			companyId, repositoryId, fileName, is,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		addFile(companyId, repositoryId, fileName, true, is, dlConfig);
	}

	@Override
	public void checkRoot(long companyId) throws SystemException {
		store.checkRoot(companyId);
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		store.copyFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

	@Override
	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		store.deleteDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		validate(fileName, false, DLConfig.getLiberalDLConfig());

		store.deleteFile(companyId, repositoryId, fileName);
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		validate(fileName, false, versionLabel, DLConfig.getLiberalDLConfig());

		store.deleteFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		validate(fileName, false, DLConfig.getLiberalDLConfig());

		return store.getFile(companyId, repositoryId, fileName);
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		validate(fileName, false, versionLabel, DLConfig.getLiberalDLConfig());

		return store.getFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		validate(fileName, false, DLConfig.getLiberalDLConfig());

		return store.getFileAsBytes(companyId, repositoryId, fileName);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		validate(fileName, false, versionLabel, DLConfig.getLiberalDLConfig());

		return store.getFileAsBytes(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		validate(fileName, false, DLConfig.getLiberalDLConfig());

		return store.getFileAsStream(companyId, repositoryId, fileName);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		validate(fileName, false, versionLabel, DLConfig.getLiberalDLConfig());

		return store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		if (!isValidName(dirName)) {
			throw new DirectoryNameException(dirName);
		}

		return store.getFileNames(companyId, repositoryId, dirName);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		validate(fileName, false, DLConfig.getLiberalDLConfig());

		return store.getFileSize(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		if (!isValidName(dirName)) {
			throw new DirectoryNameException(dirName);
		}

		return store.hasDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		validate(fileName, false, DLConfig.getLiberalDLConfig());

		return store.hasFile(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		validate(fileName, false, versionLabel, DLConfig.getLiberalDLConfig());

		return store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public boolean isValidName(String name)
		throws PortalException, SystemException {

		return DLUtil.isValidFileName(name);
	}

	@Override
	public void move(String srcDir, String destDir) throws SystemException {
		store.move(srcDir, destDir);
	}

	public Hits search(
			long companyId, long userId, String portletId, long groupId,
			long[] repositoryIds, String keywords, int start, int end)
		throws SystemException {

		try {
			SearchContext searchContext = new SearchContext();

			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setEntryClassNames(
				new String[] {DLFileEntryConstants.getClassName()});
			searchContext.setGroupIds(new long[] {groupId});

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				DLFileEntryConstants.getClassName());

			searchContext.setSearchEngineId(indexer.getSearchEngineId());

			searchContext.setStart(start);
			searchContext.setUserId(userId);

			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			contextQuery.addRequiredTerm(Field.PORTLET_ID, portletId);

			if (groupId > 0) {
				Group group = groupLocalService.getGroup(groupId);

				if (group.isLayout()) {
					contextQuery.addRequiredTerm(Field.SCOPE_GROUP_ID, groupId);

					groupId = group.getParentGroupId();
				}

				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if (ArrayUtil.isNotEmpty(repositoryIds)) {
				BooleanQuery repositoryIdsQuery =
					BooleanQueryFactoryUtil.create(searchContext);

				for (long repositoryId : repositoryIds) {
					try {
						if (userId > 0) {
							PermissionChecker permissionChecker =
								PermissionThreadLocal.getPermissionChecker();

							DLFolderPermission.check(
								permissionChecker, groupId, repositoryId,
								ActionKeys.VIEW);
						}

						if (repositoryId ==
								DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

							repositoryId = groupId;
						}

						TermQuery termQuery = TermQueryFactoryUtil.create(
							searchContext, "repositoryId", repositoryId);

						repositoryIdsQuery.add(
							termQuery, BooleanClauseOccur.SHOULD);
					}
					catch (Exception e) {
					}
				}

				contextQuery.add(repositoryIdsQuery, BooleanClauseOccur.MUST);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			searchQuery.addTerms(_KEYWORDS_FIELDS, keywords);

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			List<BooleanClause> clauses = searchQuery.clauses();

			if (!clauses.isEmpty()) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(searchContext, fullQuery);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException {

		updateFile(
			companyId, repositoryId, newRepositoryId, fileName,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName, DLConfig dlConfig)
		throws PortalException, SystemException {

		store.updateFile(
			companyId, repositoryId, newRepositoryId, fileName, dlConfig);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException {

		updateFile(
			companyId, repositoryId, fileName, newFileName,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, File file)
		throws PortalException, SystemException {

		updateFile(
			companyId, repositoryId, fileName, fileExtension,
			validateFileExtension, versionLabel, sourceFileName, file,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, File file,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			file, versionLabel, dlConfig);

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(file);
		}

		store.updateFile(
			companyId, repositoryId, fileName, versionLabel, file, dlConfig);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, InputStream is)
		throws PortalException, SystemException {

		updateFile(
			companyId, repositoryId, fileName, fileExtension,
			validateFileExtension, versionLabel, sourceFileName, is,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, InputStream is,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		if (is instanceof ByteArrayFileInputStream) {
			ByteArrayFileInputStream byteArrayFileInputStream =
				(ByteArrayFileInputStream)is;

			File file = byteArrayFileInputStream.getFile();

			updateFile(
				companyId, repositoryId, fileName, fileExtension,
				validateFileExtension, versionLabel, sourceFileName, file,
				dlConfig);

			return;
		}

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension, is,
			versionLabel, dlConfig);

		if (!PropsValues.DL_STORE_ANTIVIRUS_ENABLED ||
			!AntivirusScannerUtil.isActive()) {

			store.updateFile(
				companyId, repositoryId, fileName, versionLabel, is, dlConfig);
		}
		else {
			File tempFile = null;

			try {
				if (is.markSupported()) {
					is.mark(is.available() + 1);

					AntivirusScannerUtil.scan(is);

					is.reset();

					store.updateFile(
						companyId, repositoryId, fileName, versionLabel, is,
						dlConfig);
				}
				else {
					tempFile = FileUtil.createTempFile();

					FileUtil.write(tempFile, is);

					AntivirusScannerUtil.scan(tempFile);

					store.updateFile(
						companyId, repositoryId, fileName, versionLabel,
						tempFile, dlConfig);
				}
			}
			catch (IOException ioe) {
				throw new SystemException(
					"Unable to scan file " + fileName, ioe);
			}
			finally {
				if (tempFile != null) {
					tempFile.delete();
				}
			}
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName, DLConfig dlConfig)
		throws PortalException, SystemException {

		store.updateFile(
			companyId, repositoryId, fileName, newFileName, dlConfig);
	}

	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		store.updateFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void validate(String fileName, boolean validateFileExtension)
		throws PortalException, SystemException {

		validate(
			fileName, validateFileExtension, DLConfig.getLiberalDLConfig());
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void validate(
			String fileName, boolean validateFileExtension, byte[] bytes)
		throws PortalException, SystemException {

		validate(
			fileName, validateFileExtension, bytes,
			DLConfig.getLiberalDLConfig());
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException, SystemException {

		validate(
			fileName, validateFileExtension, file,
			DLConfig.getLiberalDLConfig());
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void validate(
			String fileName, boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		validate(
			fileName, validateFileExtension, is, DLConfig.getLiberalDLConfig());
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file)
		throws PortalException, SystemException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			file, DLConfig.getLiberalDLConfig());
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension, is,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void validateDirectoryName(String directoryName)
		throws PortalException, SystemException {

		DirectoryNameValidator validator =
			DLValidatorUtil.getDefaultDirectoryNameValidator();

		validator.validate(directoryName);
	}

	protected void validate(
			String fileName, boolean validateFileExtension, byte[] bytes,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		validate(fileName, validateFileExtension, dlConfig);

		FileSizeValidator validator = dlConfig.getFileSizeValidator();

		validator.validateSize(fileName, bytes);
	}

	protected void validate(
			String fileName, boolean validateFileExtension, DLConfig dlConfig)
		throws PortalException, SystemException {

		FileNameValidator validator = dlConfig.getFileNameValidator(
			validateFileExtension);

		validator.validate(fileName);
	}

	protected void validate(
			String fileName, boolean validateFileExtension, File file,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		validate(fileName, validateFileExtension, dlConfig);

		FileSizeValidator validator = dlConfig.getFileSizeValidator();

		validator.validateSize(fileName, file);
	}

	protected void validate(
			String fileName, boolean validateFileExtension, InputStream is,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		validate(fileName, validateFileExtension, dlConfig);

		FileSizeValidator validator = dlConfig.getFileSizeValidator();

		validator.validateSize(fileName, is);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	protected void validate(
			String fileName, boolean validateFileExtension, String versionLabel)
		throws PortalException, SystemException {

		validate(
			fileName, validateFileExtension, versionLabel,
			DLConfig.getLiberalDLConfig());
	}

	protected void validate(
			String fileName, boolean validateFileExtension, String versionLabel,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		VersionValidator validator = dlConfig.getVersionValidator();

		validator.validateVersion(versionLabel);

		validate(fileName, validateFileExtension, dlConfig);
	}

	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file, DLConfig dlConfig)
		throws PortalException, SystemException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			StringPool.BLANK, dlConfig);

		FileSizeValidator fileSizeValidator = dlConfig.getFileSizeValidator();

		fileSizeValidator.validateSize(fileName, file);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file, String versionLabel)
		throws PortalException, SystemException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			file, versionLabel, DLConfig.getLiberalDLConfig());
	}

	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file, String versionLabel,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		VersionValidator validator = dlConfig.getVersionValidator();

		validator.validateVersion(versionLabel);

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			file, dlConfig);
	}

	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is, DLConfig dlConfig)
		throws PortalException, SystemException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			StringPool.BLANK, dlConfig);

		FileSizeValidator fileSizeValidator = dlConfig.getFileSizeValidator();

		fileSizeValidator.validateSize(fileName, is);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is, String versionLabel)
		throws PortalException, SystemException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension, is,
			versionLabel, DLConfig.getLiberalDLConfig());
	}

	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is, String versionLabel,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		VersionValidator validator = dlConfig.getVersionValidator();

		validator.validateVersion(versionLabel);

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension, is,
			dlConfig);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, String versionLabel)
		throws PortalException, SystemException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			versionLabel, DLConfig.getLiberalDLConfig());
	}

	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, String versionLabel,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		FileExtensionValidator validator = dlConfig.getFileExtensionValidator();

		validator.validateExtension(fileName, fileExtension, sourceFileName);

		validate(fileName, validateFileExtension, versionLabel, dlConfig);
	}

	@BeanReference(type = GroupLocalService.class)
	protected GroupLocalService groupLocalService;

	@BeanReference(type = Store.class)
	protected Store store;

	private static final String[] _KEYWORDS_FIELDS = {
		Field.ASSET_TAG_NAMES, Field.CONTENT, Field.PROPERTIES
	};

	private static final String _UNICODE_PREFIX = "\\u";

}