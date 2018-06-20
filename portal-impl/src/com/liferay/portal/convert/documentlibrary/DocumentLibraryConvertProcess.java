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

package com.liferay.portal.convert.documentlibrary;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.document.library.kernel.util.comparator.FileVersionVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.convert.BaseConvertProcess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.InputStream;

import java.util.Collection;
import java.util.List;

/**
 * @author Minhchau Dang
 * @author Alexander Chow
 * @author László Csontos
 */
public class DocumentLibraryConvertProcess
	extends BaseConvertProcess implements DLStoreConverter {

	@Override
	public String getConfigurationErrorMessage() {
		return "there-are-no-stores-configured";
	}

	@Override
	public String getDescription() {
		return "migrate-documents-from-one-repository-to-another";
	}

	@Override
	public String getParameterDescription() {
		return "please-select-a-new-repository-hook";
	}

	@Override
	public String[] getParameterNames() {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore();

		if (store == null) {
			return null;
		}

		String[] storeTypes = storeFactory.getStoreTypes();

		StringBundler sb = new StringBundler(storeTypes.length * 2 + 2);

		sb.append(PropsKeys.DL_STORE_IMPL);
		sb.append(StringPool.EQUAL);

		for (String storeType : storeTypes) {
			Class<?> clazz = store.getClass();

			if (!storeType.equals(clazz.getName())) {
				sb.append(storeType);
				sb.append(StringPool.SEMICOLON);
			}
		}

		return new String[] {
			sb.toString(), "delete-files-from-previous-repository=checkbox"
		};
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void migrateDLFileEntry(
		long companyId, long repositoryId, FileEntry fileEntry) {

		Object model = fileEntry.getModel();

		if (!(model instanceof DLFileEntry)) {
			throw new IllegalArgumentException(
				"Unsupported file entry model " + model.getClass());
		}

		String fileName = ((DLFileEntry)model).getName();

		for (FileVersion fileVersion : getFileVersions(fileEntry)) {
			String versionNumber = fileVersion.getVersion();

			migrateFile(companyId, repositoryId, fileName, versionNumber);
		}
	}

	@Override
	public void validate() {
	}

	@Override
	protected void doConvert() throws Exception {
		migratePortlets();

		StoreFactory storeFactory = StoreFactory.getInstance();

		String targetStoreClassName = getTargetStoreClassName();

		migrateDLStoreConvertProcesses(
			storeFactory.getStore(),
			storeFactory.getStore(targetStoreClassName));

		storeFactory.setStore(targetStoreClassName);

		MaintenanceUtil.appendStatus(
			StringBundler.concat(
				"Please set ", PropsKeys.DL_STORE_IMPL,
				" in your portal-ext.properties to use ",
				targetStoreClassName));

		PropsValues.DL_STORE_IMPL = targetStoreClassName;
	}

	protected List<FileVersion> getFileVersions(FileEntry fileEntry) {
		List<FileVersion> fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		return ListUtil.sort(
			fileVersions, new FileVersionVersionComparator(true));
	}

	protected String getSourceStoreClassName() {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store sourceStore = storeFactory.getStore();

		Class<?> clazz = sourceStore.getClass();

		return clazz.getName();
	}

	protected String getTargetStoreClassName() {
		String[] values = getParameterValues();

		return values[0];
	}

	protected boolean isDeleteFilesFromSourceStore() {
		String[] values = getParameterValues();

		return GetterUtil.getBoolean(values[1]);
	}

	protected void migrateDL() throws PortalException {
		int count = DLFileEntryLocalServiceUtil.getFileEntriesCount();

		MaintenanceUtil.appendStatus(
			"Migrating " + count + " documents and media files");

		ActionableDynamicQuery actionableDynamicQuery =
			DLFileVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(DLFileVersion dlFileVersion) -> {
				DLFileEntry dlFileEntry = dlFileVersion.getFileEntry();

				long dataRepositoryId = DLFolderConstants.getDataRepositoryId(
					dlFileVersion.getRepositoryId(),
					dlFileVersion.getFolderId());

				migrateFile(
					dlFileVersion.getCompanyId(), dataRepositoryId,
					dlFileEntry.getName(), dlFileVersion.getVersion());
			});

		actionableDynamicQuery.performActions();
	}

	protected void migrateDLStoreConvertProcesses(
			Store sourceStore, Store targetStore)
		throws PortalException {

		Collection<DLStoreConvertProcess> dlStoreConvertProcesses =
			_getDLStoreConvertProcesses();

		for (DLStoreConvertProcess dlStoreConvertProcess :
				dlStoreConvertProcesses) {

			dlStoreConvertProcess.migrate(sourceStore, targetStore);
		}
	}

	protected void migrateFile(
		long companyId, long repositoryId, String fileName,
		String versionNumber) {

		try {
			InputStream is = _sourceStore.getFileAsStream(
				companyId, repositoryId, fileName, versionNumber);

			if (versionNumber.equals(Store.VERSION_DEFAULT)) {
				_targetStore.addFile(companyId, repositoryId, fileName, is);
			}
			else {
				_targetStore.updateFile(
					companyId, repositoryId, fileName, versionNumber, is);
			}

			if (isDeleteFilesFromSourceStore()) {
				_sourceStore.deleteFile(
					companyId, repositoryId, fileName, versionNumber);
			}
		}
		catch (Exception e) {
			_log.error("Migration failed for " + fileName, e);
		}
	}

	protected void migrateGeneratedFiles(String path) throws Exception {
		MaintenanceUtil.appendStatus("Migrating files from " + path);

		ActionableDynamicQuery actionableDynamicQuery =
			CompanyLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Company company) -> {
				long companyId = company.getCompanyId();

				String[] fileNames = _sourceStore.getFileNames(
					companyId, DLPreviewableProcessor.REPOSITORY_ID, path);

				for (String fileName : fileNames) {

					// See LPS-70788

					String actualFileName = StringUtil.replace(
						fileName, StringPool.DOUBLE_SLASH, StringPool.SLASH);

					migrateFile(
						companyId, DLPreviewableProcessor.REPOSITORY_ID,
						actualFileName, Store.VERSION_DEFAULT);
				}
			});

		actionableDynamicQuery.performActions();
	}

	protected void migrateImages() throws PortalException {
		int count = ImageLocalServiceUtil.getImagesCount();

		MaintenanceUtil.appendStatus("Migrating " + count + " images");

		ActionableDynamicQuery actionableDynamicQuery =
			ImageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Image image) -> {
				String fileName =
					image.getImageId() + StringPool.PERIOD + image.getType();

				migrateFile(0, 0, fileName, Store.VERSION_DEFAULT);
			});

		actionableDynamicQuery.performActions();
	}

	protected void migratePortlets() throws Exception {
		migrateImages();
		migrateDL();
		migrateGeneratedFiles(DLPreviewableProcessor.THUMBNAIL_PATH);
		migrateGeneratedFiles(DLPreviewableProcessor.PREVIEW_PATH);
	}

	private Collection<DLStoreConvertProcess> _getDLStoreConvertProcesses() {
		try {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getServices(DLStoreConvertProcess.class, null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryConvertProcess.class);

	private Store _sourceStore;
	private Store _targetStore;

}