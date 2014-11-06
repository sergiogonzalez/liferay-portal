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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.util.RepositoryWrapper;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class LiferayCheckInRepositoryWrapper extends RepositoryWrapper {

	public LiferayCheckInRepositoryWrapper(Repository repository) {
		super(repository);
	}

	@Override
	public void revertFileEntry(
			final long fileEntryId, final String version,
			final ServiceContext serviceContext)
		throws PortalException {

		FileEntryOperation revertOperation = new FileEntryOperation() {

			@Override
			public void execute() throws PortalException {
				LiferayCheckInRepositoryWrapper.super.revertFileEntry(
					fileEntryId, version, serviceContext);
			}

		};

		boolean majorVersion = true;
		String changeLog = LanguageUtil.format(
			serviceContext.getLocale(), "reverted-to-x", version, false);

		doAutoCheckIn(
			_getUserId(), fileEntryId, majorVersion, changeLog, serviceContext,
			revertOperation);
	}

	@Override
	public FileEntry updateFileEntry(
			final long fileEntryId, final String sourceFileName,
			final String mimeType, final String title, final String description,
			final String changeLog, final boolean majorVersion, final File file,
			final ServiceContext serviceContext)
		throws PortalException {

		FileEntryOperation updateOperation = new FileEntryOperation() {

			@Override
			public void execute() throws PortalException {
				LiferayCheckInRepositoryWrapper.super.updateFileEntry(
					fileEntryId, sourceFileName, mimeType, title, description,
					changeLog, majorVersion, file, serviceContext);
			}

		};

		return doAutoCheckIn(
			_getUserId(), fileEntryId, majorVersion, changeLog, serviceContext,
			updateOperation);
	}

	@Override
	public FileEntry updateFileEntry(
			final long fileEntryId, final String sourceFileName,
			final String mimeType, final String title, final String description,
			final String changeLog, final boolean majorVersion,
			final InputStream is, final long size,
			final ServiceContext serviceContext)
		throws PortalException {

		FileEntryOperation updateOperation = new FileEntryOperation() {

			@Override
			public void execute() throws PortalException {
				LiferayCheckInRepositoryWrapper.super.updateFileEntry(
					fileEntryId, sourceFileName, mimeType, title, description,
					changeLog, majorVersion, is, size, serviceContext);
			}

		};

		return doAutoCheckIn(
			_getUserId(), fileEntryId, majorVersion, changeLog, serviceContext,
			updateOperation);
	}

	protected FileEntry doAutoCheckIn(
			long userId, long fileEntryId, boolean majorVersion,
			String changeLog, ServiceContext serviceContext,
			FileEntryOperation fileEntryOperation)
		throws PortalException {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntryId);

		boolean checkedOut = dlFileEntry.isCheckedOut();

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.getLatestFileVersion(
				fileEntryId, !checkedOut);

		boolean autoCheckIn = !checkedOut && dlFileVersion.isApproved();

		if (autoCheckIn) {
			dlFileEntry = DLFileEntryLocalServiceUtil.checkOutFileEntry(
				userId, fileEntryId, serviceContext);
		}
		else if (!checkedOut) {
			DLFileEntryLocalServiceUtil.lockFileEntry(userId, fileEntryId);
		}

		if (!DLFileEntryLocalServiceUtil.hasFileEntryLock(
				userId, fileEntryId)) {

			DLFileEntryLocalServiceUtil.lockFileEntry(userId, fileEntryId);
		}

		if (checkedOut || autoCheckIn) {
			dlFileVersion = DLFileVersionLocalServiceUtil.getLatestFileVersion(
				fileEntryId, false);
		}

		try {
			fileEntryOperation.execute();

			// Folder

			if (!checkedOut &&
				(dlFileEntry.getFolderId() !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

				dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
					fileEntryId);

				DLFolderLocalServiceUtil.updateLastPostDate(
					dlFileEntry.getFolderId(),
					serviceContext.getModifiedDate(
						dlFileEntry.getModifiedDate()));
			}

			if (autoCheckIn) {
				if (ExportImportThreadLocal.isImportInProcess()) {
					DLFileEntryLocalServiceUtil.checkInFileEntry(
						userId, fileEntryId, majorVersion, changeLog,
						serviceContext);
				}
				else {
					DLFileEntryServiceUtil.checkInFileEntry(
						fileEntryId, majorVersion, changeLog, serviceContext);
				}
			}
			else if (!checkedOut &&
					 (serviceContext.getWorkflowAction() ==
						WorkflowConstants.ACTION_PUBLISH)) {

				String syncEvent = DLSyncConstants.EVENT_UPDATE;

				if (dlFileVersion.getVersion().equals(
						DLFileEntryConstants.VERSION_DEFAULT)) {

					syncEvent = DLSyncConstants.EVENT_ADD;
				}

				DLUtil.startWorkflowInstance(
					userId, dlFileVersion, syncEvent, serviceContext);
			}
		}
		catch (PortalException|SystemException pe) {
			if (autoCheckIn) {
				try {
					if (ExportImportThreadLocal.isImportInProcess()) {
						DLFileEntryLocalServiceUtil.cancelCheckOut(
							userId, fileEntryId);
					}
					else {
						DLFileEntryServiceUtil.cancelCheckOut(fileEntryId);
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			throw pe;
		}
		finally {
			if (!autoCheckIn && !checkedOut) {
				DLFileEntryLocalServiceUtil.unlockFileEntry(fileEntryId);
			}
		}

		return new LiferayFileEntry(
			DLFileEntryLocalServiceUtil.getDLFileEntry(fileEntryId));
	}

	/**
	 * See {@link com.liferay.portal.service.BaseServiceImpl#getUserId()}
	 */
	private long _getUserId() throws PrincipalException {
		String name = PrincipalThreadLocal.getName();

		if (name == null) {
			throw new PrincipalException();
		}

		if (Validator.isNull(name)) {
			throw new PrincipalException("Principal is null");
		}
		else {
			for (int i = 0; i < BaseServiceImpl.ANONYMOUS_NAMES.length; i++) {
				if (StringUtil.equalsIgnoreCase(
						name, BaseServiceImpl.ANONYMOUS_NAMES[i])) {

					throw new PrincipalException(
						"Principal cannot be " +
							BaseServiceImpl.ANONYMOUS_NAMES[i]);
				}
			}
		}

		return GetterUtil.getLong(name);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayCheckInRepositoryWrapper.class);

	private interface FileEntryOperation {

		public void execute() throws PortalException;

	}

}