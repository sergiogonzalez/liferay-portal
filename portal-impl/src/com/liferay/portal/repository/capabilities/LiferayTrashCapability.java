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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.event.TrashRepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModel;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.capabilities.util.DLAppServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFileVersionServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFolderServiceAdapter;
import com.liferay.portal.repository.capabilities.util.RepositoryServiceAdapter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WorkflowInstanceLinkLocalService;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService;
import com.liferay.portlet.documentlibrary.util.DLAppHelperThreadLocal;
import com.liferay.portlet.documentlibrary.util.comparator.DLFileVersionVersionComparator;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalService;
import com.liferay.portlet.trash.service.TrashVersionLocalService;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class LiferayTrashCapability
	implements RepositoryEventAware, TrashCapability {

	public LiferayTrashCapability(
		DLAppHelperLocalService dlAppHelperLocalService,
		DLAppServiceAdapter dlAppServiceAdapter,
		DLFileEntryServiceAdapter dlFileEntryServiceAdapter,
		DLFileRankLocalService dlFileRankLocalService,
		DLFileShortcutLocalService dlFileShortcutLocalService,
		DLFileVersionServiceAdapter dlFileVersionServiceAdapter,
		DLFolderServiceAdapter dlFolderServiceAdapter,
		RepositoryServiceAdapter repositoryServiceAdapter,
		TrashEntryLocalService trashEntryLocalService,
		TrashVersionLocalService trashVersionLocalService,
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {

		_dlAppHelperLocalService = dlAppHelperLocalService;
		_dlAppServiceAdapter = dlAppServiceAdapter;
		_dlFileEntryServiceAdapter = dlFileEntryServiceAdapter;
		_dlFileRankLocalService = dlFileRankLocalService;
		_dlFileShortcutLocalService = dlFileShortcutLocalService;
		_dlFileVersionServiceAdapter = dlFileVersionServiceAdapter;
		_dlFolderServiceAdapter = dlFolderServiceAdapter;
		_repositoryServiceAdapter = repositoryServiceAdapter;
		_trashEntryLocalService = trashEntryLocalService;
		_trashVersionLocalService = trashVersionLocalService;
		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		deleteTrashEntry(fileEntry);

		_dlAppServiceAdapter.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deleteFolder(Folder folder) throws PortalException {
		List<DLFileEntry> dlFileEntries =
			_dlFileEntryServiceAdapter.getGroupFileEntries(
				folder.getGroupId(), 0, folder.getRepositoryId(),
				folder.getFolderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			_dlAppHelperLocalService.deleteFileEntry(fileEntry);

			deleteTrashEntry(fileEntry);
		}

		_dlAppHelperLocalService.deleteFolder(folder);

		deleteTrashEntry(folder);

		_dlFolderServiceAdapter.deleteFolder(folder.getFolderId(), false);
	}

	@Override
	public boolean isInTrash(Folder folder) {
		DLFolder dlFolder = (DLFolder)folder.getModel();

		return dlFolder.isInTrash();
	}

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, Folder newFolder,
			ServiceContext serviceContext)
		throws PortalException {

		long newFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (newFolder != null) {
			newFolderId = newFolder.getFolderId();
		}

		return _dlAppHelperLocalService.moveFileEntryFromTrash(
			userId, fileEntry, newFolderId, serviceContext);
	}

	@Override
	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		boolean hasLock = _dlFileEntryServiceAdapter.hasFileEntryLock(
			userId, fileEntry.getFileEntryId());

		if (!hasLock) {
			_dlFileEntryServiceAdapter.lockFileEntry(
				userId, fileEntry.getFileEntryId());
		}

		try {
			if (fileEntry.isCheckedOut()) {
				_dlFileEntryServiceAdapter.cancelCheckOut(
					userId, fileEntry.getFileEntryId());
			}

			return doMoveFileEntryToTrash(userId, fileEntry);
		}
		finally {
			if (!hasLock) {
				_dlFileEntryServiceAdapter.unlockFileEntry(
					fileEntry.getFileEntryId());
			}
		}
	}

	@Override
	public FileShortcut moveFileShortcutFromTrash(
			long userId, FileShortcut fileShortcut, Folder newFolder,
			ServiceContext serviceContext)
		throws PortalException {

		long newFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (newFolder != null) {
			newFolderId = newFolder.getFolderId();
		}

		return _dlAppHelperLocalService.moveFileShortcutFromTrash(
			userId, fileShortcut, newFolderId, serviceContext);
	}

	@Override
	public FileShortcut moveFileShortcutToTrash(
			long userId, FileShortcut fileShortcut)
		throws PortalException {

		return _dlAppHelperLocalService.moveFileShortcutToTrash(
			userId, fileShortcut);
	}

	@Override
	public Folder moveFolderFromTrash(
			long userId, Folder folder, Folder destinationFolder,
			ServiceContext serviceContext)
		throws PortalException {

		long destinationFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (destinationFolder != null) {
			destinationFolderId = destinationFolder.getFolderId();
		}

		return _dlAppHelperLocalService.moveFolderFromTrash(
			userId, folder, destinationFolderId, serviceContext);
	}

	@Override
	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException {

		return _dlAppHelperLocalService.moveFolderToTrash(userId, folder);
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, FileEntry.class,
			new DeleteFileEntryRepositoryEventListener());
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, Folder.class,
			new DeleteFolderRepositoryEventListener());
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, LocalRepository.class,
			new DeleteLocalRepositoryEventListener());
	}

	@Override
	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		_dlAppHelperLocalService.restoreFileEntryFromTrash(userId, fileEntry);
	}

	@Override
	public void restoreFileShortcutFromTrash(
			long userId, FileShortcut fileShortcut)
		throws PortalException {

		_dlAppHelperLocalService.restoreFileShortcutFromTrash(
			userId, fileShortcut);
	}

	@Override
	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException {

		_dlAppHelperLocalService.restoreFolderFromTrash(userId, folder);
	}

	protected void deleteRepositoryTrashEntries(
		long repositoryId, String className) {

		List<TrashEntry> trashEntries = _trashEntryLocalService.getEntries(
			repositoryId, className);

		for (TrashEntry trashEntry : trashEntries) {
			_trashEntryLocalService.deleteTrashEntry(trashEntry);
		}
	}

	protected void deleteTrashEntries(long repositoryId)
		throws PortalException {

		Repository repository = _repositoryServiceAdapter.fetchRepository(
			repositoryId);

		if (repository == null) {
			deleteRepositoryTrashEntries(
				repositoryId, DLFileEntry.class.getName());
			deleteRepositoryTrashEntries(
				repositoryId, DLFolder.class.getName());
		}
		else {
			deleteTrashEntries(
				repository.getGroupId(), repository.getDlFolderId());
		}
	}

	protected void deleteTrashEntries(long groupId, long dlFolderId)
		throws PortalException {

		QueryDefinition<Object> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			_dlFolderServiceAdapter.getFoldersAndFileEntriesAndFileShortcuts(
				groupId, dlFolderId, null, true, queryDefinition);

		for (Object folderFileEntryOrFileShortcut :
				foldersAndFileEntriesAndFileShortcuts) {

			if (folderFileEntryOrFileShortcut instanceof DLFileEntry) {
				deleteTrashEntry((DLFileEntry)folderFileEntryOrFileShortcut);
			}
			else if (folderFileEntryOrFileShortcut instanceof DLFolder) {
				DLFolder dlFolder = (DLFolder)folderFileEntryOrFileShortcut;

				deleteTrashEntries(
					dlFolder.getGroupId(), dlFolder.getFolderId());

				deleteTrashEntry(dlFolder);
			}
		}
	}

	protected void deleteTrashEntry(DLFileEntry dlFileEntry) {
		if (!dlFileEntry.isInTrash()) {
			return;
		}

		if (dlFileEntry.isInTrashExplicitly()) {
			_trashEntryLocalService.deleteEntry(
				DLFileEntryConstants.getClassName(),
				dlFileEntry.getFileEntryId());
		}
		else {
			List<DLFileVersion> dlFileVersions = dlFileEntry.getFileVersions(
				WorkflowConstants.STATUS_ANY);

			for (DLFileVersion dlFileVersion : dlFileVersions) {
				_trashVersionLocalService.deleteTrashVersion(
					DLFileVersion.class.getName(),
					dlFileVersion.getFileVersionId());
			}
		}
	}

	protected void deleteTrashEntry(DLFolder dlFolder) {
		if (!dlFolder.isInTrash()) {
			return;
		}

		if (dlFolder.isInTrashExplicitly()) {
			_trashEntryLocalService.deleteEntry(
				DLFolderConstants.getClassName(), dlFolder.getFolderId());
		}
		else {
			_trashVersionLocalService.deleteTrashVersion(
				DLFolderConstants.getClassName(), dlFolder.getFolderId());
		}
	}

	protected void deleteTrashEntry(FileEntry fileEntry) {
		deleteTrashEntry((DLFileEntry)fileEntry.getModel());
	}

	protected void deleteTrashEntry(Folder folder) {
		deleteTrashEntry((DLFolder)folder.getModel());
	}

	protected FileEntry doMoveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		// File versions

		List<DLFileVersion> dlFileVersions =
			_dlFileVersionServiceAdapter.getFileVersions(
				fileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);

		dlFileVersions = ListUtil.sort(
			dlFileVersions, new DLFileVersionVersionComparator());

		List<ObjectValuePair<Long, Integer>> dlFileVersionStatusOVPs =
			new ArrayList<>();

		if ((dlFileVersions != null) && !dlFileVersions.isEmpty()) {
			dlFileVersionStatusOVPs = getDlFileVersionStatuses(dlFileVersions);
		}

		FileVersion fileVersion = fileEntry.getFileVersion();

		int oldStatus = fileVersion.getStatus();

		_dlFileEntryServiceAdapter.updateStatus(
			userId, fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_IN_TRASH, new ServiceContext(),
			new HashMap<String, Serializable>());

		if (DLAppHelperThreadLocal.isEnabled()) {

			// File shortcut

			_dlFileShortcutLocalService.disableFileShortcuts(
				fileEntry.getFileEntryId());

			// File rank

			_dlFileRankLocalService.disableFileRanks(
				fileEntry.getFileEntryId());

			// Sync

			triggerRepositoryEvent(
				fileEntry.getRepositoryId(),
				TrashRepositoryEventType.EntryTrashed.class, FileEntry.class,
				fileEntry);
		}

		// Trash

		DLFileVersion oldDLFileVersion = (DLFileVersion)fileVersion.getModel();

		int oldDLFileVersionStatus = oldDLFileVersion.getStatus();

		for (DLFileVersion curDLFileVersion : dlFileVersions) {
			curDLFileVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

			_dlFileVersionServiceAdapter.update(curDLFileVersion);
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("fileName", dlFileEntry.getFileName());
		typeSettingsProperties.put("title", dlFileEntry.getTitle());

		TrashEntry trashEntry = _trashEntryLocalService.addTrashEntry(
			userId, dlFileEntry.getGroupId(),
			DLFileEntryConstants.getClassName(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getUuid(), dlFileEntry.getClassName(),
			oldDLFileVersionStatus, dlFileVersionStatusOVPs,
			typeSettingsProperties);

		String trashTitle = TrashUtil.getTrashTitle(trashEntry.getEntryId());

		dlFileEntry.setFileName(trashTitle);
		dlFileEntry.setTitle(trashTitle);

		_dlFileEntryServiceAdapter.update(dlFileEntry);

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return fileEntry;
		}

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put(
			"title", TrashUtil.getOriginalTitle(fileEntry.getTitle()));

		SocialActivityManagerUtil.addActivity(
			userId, fileEntry, SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		// Workflow

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				fileVersion.getCompanyId(), fileVersion.getGroupId(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId());
		}

		return fileEntry;
	}

	protected List<ObjectValuePair<Long, Integer>> getDlFileVersionStatuses(
		List<DLFileVersion> dlFileVersions) {

		List<ObjectValuePair<Long, Integer>> dlFileVersionStatusOVPs =
			new ArrayList<>(dlFileVersions.size());

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			int status = dlFileVersion.getStatus();

			if (status == WorkflowConstants.STATUS_PENDING) {
				status = WorkflowConstants.STATUS_DRAFT;
			}

			ObjectValuePair<Long, Integer> dlFileVersionStatusOVP =
				new ObjectValuePair<>(dlFileVersion.getFileVersionId(), status);

			dlFileVersionStatusOVPs.add(dlFileVersionStatusOVP);
		}

		return dlFileVersionStatusOVPs;
	}

	protected <T extends RepositoryModel<T>> void triggerRepositoryEvent(
			long repositoryId,
			Class<? extends RepositoryEventType> repositoryEventType,
			Class<T> modelClass, T target)
		throws PortalException {

		com.liferay.portal.kernel.repository.Repository repository =
			RepositoryProviderUtil.getRepository(repositoryId);

		if (repository.isCapabilityProvided(
				RepositoryEventTriggerCapability.class)) {

			RepositoryEventTriggerCapability repositoryEventTriggerCapability =
				repository.getCapability(
					RepositoryEventTriggerCapability.class);

			repositoryEventTriggerCapability.trigger(
				repositoryEventType, modelClass, target);
		}
	}

	private final DLAppHelperLocalService _dlAppHelperLocalService;
	private final DLAppServiceAdapter _dlAppServiceAdapter;
	private final DLFileEntryServiceAdapter _dlFileEntryServiceAdapter;
	private final DLFileRankLocalService _dlFileRankLocalService;
	private final DLFileShortcutLocalService _dlFileShortcutLocalService;
	private final DLFileVersionServiceAdapter _dlFileVersionServiceAdapter;
	private final DLFolderServiceAdapter _dlFolderServiceAdapter;
	private final RepositoryServiceAdapter _repositoryServiceAdapter;
	private final TrashEntryLocalService _trashEntryLocalService;
	private final TrashVersionLocalService _trashVersionLocalService;
	private final WorkflowInstanceLinkLocalService
		_workflowInstanceLinkLocalService;

	private class DeleteFileEntryRepositoryEventListener
		implements RepositoryEventListener
			<RepositoryEventType.Delete, FileEntry> {

		@Override
		public void execute(FileEntry fileEntry) {
			LiferayTrashCapability.this.deleteTrashEntry(fileEntry);
		}

	}

	private class DeleteFolderRepositoryEventListener
		implements RepositoryEventListener<RepositoryEventType.Delete, Folder> {

		@Override
		public void execute(Folder folder) {
			LiferayTrashCapability.this.deleteTrashEntry(folder);
		}

	}

	private class DeleteLocalRepositoryEventListener
		implements RepositoryEventListener
			<RepositoryEventType.Delete, LocalRepository> {

		@Override
		public void execute(LocalRepository localRepository)
			throws PortalException {

			LiferayTrashCapability.this.deleteTrashEntries(
				localRepository.getRepositoryId());
		}

	}

}