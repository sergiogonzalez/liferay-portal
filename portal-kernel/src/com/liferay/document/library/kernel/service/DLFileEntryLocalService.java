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

package com.liferay.document.library.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;

import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service interface for DLFileEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLFileEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileEntryLocalServiceUtil} to access the document library file entry local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the document library file entry to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @return the document library file entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DLFileEntry addDLFileEntry(DLFileEntry dlFileEntry);

	public DLFileEntry addFileEntry(long userId, long groupId,
		long repositoryId, long folderId, String sourceFileName,
		String mimeType, String title, String description, String changeLog,
		long fileEntryTypeId, Map<String, DDMFormValues> ddmFormValuesMap,
		File file, InputStream is, long size, ServiceContext serviceContext)
		throws PortalException;

	public DLFileVersion cancelCheckOut(long userId, long fileEntryId)
		throws PortalException;

	public void checkInFileEntry(long userId, long fileEntryId,
		boolean majorVersion, String changeLog, ServiceContext serviceContext)
		throws PortalException;

	public void checkInFileEntry(long userId, long fileEntryId,
		String lockUuid, ServiceContext serviceContext)
		throws PortalException;

	public DLFileEntry checkOutFileEntry(long userId, long fileEntryId,
		long fileEntryTypeId, ServiceContext serviceContext)
		throws PortalException;

	public DLFileEntry checkOutFileEntry(long userId, long fileEntryId,
		long fileEntryTypeId, String owner, long expirationTime,
		ServiceContext serviceContext) throws PortalException;

	public DLFileEntry checkOutFileEntry(long userId, long fileEntryId,
		ServiceContext serviceContext) throws PortalException;

	public DLFileEntry checkOutFileEntry(long userId, long fileEntryId,
		String owner, long expirationTime, ServiceContext serviceContext)
		throws PortalException;

	public void convertExtraSettings(String[] keys) throws PortalException;

	public DLFileEntry copyFileEntry(long userId, long groupId,
		long repositoryId, long fileEntryId, long destFolderId,
		ServiceContext serviceContext) throws PortalException;

	public void copyFileEntryMetadata(long companyId, long fileEntryTypeId,
		long fileEntryId, long fromFileVersionId, long toFileVersionId,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new document library file entry with the primary key. Does not add the document library file entry to the database.
	*
	* @param fileEntryId the primary key for the new document library file entry
	* @return the new document library file entry
	*/
	@Transactional(enabled = false)
	public DLFileEntry createDLFileEntry(long fileEntryId);

	/**
	* Deletes the document library file entry from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @return the document library file entry that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DLFileEntry deleteDLFileEntry(DLFileEntry dlFileEntry);

	/**
	* Deletes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryId the primary key of the document library file entry
	* @return the document library file entry that was removed
	* @throws PortalException if a document library file entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DLFileEntry deleteDLFileEntry(long fileEntryId)
		throws PortalException;

	public void deleteFileEntries(long groupId, long folderId)
		throws PortalException;

	public void deleteFileEntries(long groupId, long folderId,
		boolean includeTrashedEntries) throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public DLFileEntry deleteFileEntry(DLFileEntry dlFileEntry)
		throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	public DLFileEntry deleteFileEntry(long fileEntryId)
		throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	public DLFileEntry deleteFileEntry(long userId, long fileEntryId)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public DLFileEntry deleteFileVersion(long userId, long fileEntryId,
		String version) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public void deleteRepositoryFileEntries(long repositoryId)
		throws PortalException;

	public void deleteRepositoryFileEntries(long repositoryId, long folderId)
		throws PortalException;

	public void deleteRepositoryFileEntries(long repositoryId, long folderId,
		boolean includeTrashedEntries) throws PortalException;

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry fetchDLFileEntry(long fileEntryId);

	/**
	* Returns the document library file entry matching the UUID and group.
	*
	* @param uuid the document library file entry's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry fetchDLFileEntryByUuidAndGroupId(String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry fetchFileEntry(long groupId, long folderId, String title);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry fetchFileEntry(String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry fetchFileEntryByAnyImageId(long imageId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry fetchFileEntryByFileName(long groupId, long folderId,
		String fileName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry fetchFileEntryByName(long groupId, long folderId,
		String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getDDMStructureFileEntries(long groupId,
		long[] ddmStructureIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getDDMStructureFileEntries(long[] ddmStructureIds);

	/**
	* Returns a range of all the document library file entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of document library file entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getDLFileEntries(int start, int end);

	/**
	* Returns all the document library file entries matching the UUID and company.
	*
	* @param uuid the UUID of the document library file entries
	* @param companyId the primary key of the company
	* @return the matching document library file entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getDLFileEntriesByUuidAndCompanyId(String uuid,
		long companyId);

	/**
	* Returns a range of document library file entries matching the UUID and company.
	*
	* @param uuid the UUID of the document library file entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching document library file entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getDLFileEntriesByUuidAndCompanyId(String uuid,
		long companyId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator);

	/**
	* Returns the number of document library file entries.
	*
	* @return the number of document library file entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLFileEntriesCount();

	/**
	* Returns the document library file entry with the primary key.
	*
	* @param fileEntryId the primary key of the document library file entry
	* @return the document library file entry
	* @throws PortalException if a document library file entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry getDLFileEntry(long fileEntryId)
		throws PortalException;

	/**
	* Returns the document library file entry matching the UUID and group.
	*
	* @param uuid the document library file entry's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file entry
	* @throws PortalException if a matching document library file entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry getDLFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getExtraSettingsFileEntries(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getExtraSettingsFileEntriesCount();

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link #getFile(long,
	String, boolean)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public File getFile(long userId, long fileEntryId, String version,
		boolean incrementCounter) throws PortalException;

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link #getFile(long,
	String, boolean, int)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public File getFile(long userId, long fileEntryId, String version,
		boolean incrementCounter, int increment) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public File getFile(long fileEntryId, String version,
		boolean incrementCounter) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public File getFile(long fileEntryId, String version,
		boolean incrementCounter, int increment) throws PortalException;

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#getFileAsStream(long, String)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InputStream getFileAsStream(long userId, long fileEntryId,
		String version) throws PortalException;

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#getFileAsStream(long, String, boolean)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InputStream getFileAsStream(long userId, long fileEntryId,
		String version, boolean incrementCounter) throws PortalException;

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#getFileAsStream(long, String, boolean, int)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InputStream getFileAsStream(long userId, long fileEntryId,
		String version, boolean incrementCounter, int increment)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InputStream getFileAsStream(long fileEntryId, String version)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InputStream getFileAsStream(long fileEntryId, String version,
		boolean incrementCounter) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InputStream getFileAsStream(long fileEntryId, String version,
		boolean incrementCounter, int increment) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long folderId,
		int status, int start, int end, OrderByComparator<DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long folderId,
		int start, int end, OrderByComparator<DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long userId,
		List<Long> repositoryIds, List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) throws Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long groupId, long userId,
		List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) throws Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getFileEntries(long folderId, String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount();

	/**
	* @deprecated As of Wilberforce (7.0.x), with no direct replacement
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, DateRange dateRange,
		long repositoryId, QueryDefinition<DLFileEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long userId,
		List<Long> repositoryIds, List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) throws Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesCount(long groupId, long userId,
		List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) throws Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry getFileEntry(long fileEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry getFileEntryByName(long groupId, long folderId,
		String name) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getGroupFileEntries(long groupId, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getGroupFileEntries(long groupId, int start,
		int end, OrderByComparator<DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getGroupFileEntries(long groupId, long userId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getGroupFileEntries(long groupId, long userId,
		int start, int end, OrderByComparator<DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getGroupFileEntries(long groupId, long userId,
		long rootFolderId, int start, int end,
		OrderByComparator<DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getGroupFileEntries(long groupId, long userId,
		long repositoryId, long rootFolderId, int start, int end,
		OrderByComparator<DLFileEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupFileEntriesCount(long groupId, long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @deprecated As of Judson (7.1.x), with no direct replacement
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getMisversionedFileEntries();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getNoAssetFileEntries();

	/**
	* @deprecated As of Judson (7.1.x), with no direct replacement
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getOrphanedFileEntries();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DLFileEntry> getRepositoryFileEntries(long repositoryId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRepositoryFileEntriesCount(long repositoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getUniqueTitle(long groupId, long folderId, long fileEntryId,
		String title, String extension) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasExtraSettings();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasFileEntryLock(long userId, long fileEntryId)
		throws PortalException;

	@BufferedIncrement(configuration = "DLFileEntry", incrementClass = NumberIncrement.class)
	public void incrementViewCounter(DLFileEntry dlFileEntry, int increment);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isFileEntryCheckedOut(long fileEntryId)
		throws PortalException;

	/**
	* @deprecated As of Judson (7.1.x), with no direct replacement
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isKeepFileVersionLabel(long fileEntryId,
		boolean majorVersion, ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#isKeepFileVersionLabel(long, boolean, ServiceContext)}
	*/
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isKeepFileVersionLabel(long fileEntryId,
		ServiceContext serviceContext) throws PortalException;

	public Lock lockFileEntry(long userId, long fileEntryId)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public DLFileEntry moveFileEntry(long userId, long fileEntryId,
		long newFolderId, ServiceContext serviceContext)
		throws PortalException;

	public void rebuildTree(long companyId) throws PortalException;

	public void revertFileEntry(long userId, long fileEntryId, String version,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(long groupId, long userId, long creatorUserId,
		int status, int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(long groupId, long userId, long creatorUserId,
		long folderId, String[] mimeTypes, int status, int start, int end)
		throws PortalException;

	public void setTreePaths(long folderId, String treePath, boolean reindex)
		throws PortalException;

	public void unlockFileEntry(long fileEntryId);

	/**
	* Updates the document library file entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntry the document library file entry
	* @return the document library file entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DLFileEntry updateDLFileEntry(DLFileEntry dlFileEntry);

	public DLFileEntry updateFileEntry(long userId, long fileEntryId,
		String sourceFileName, String mimeType, String title,
		String description, String changeLog, boolean majorVersion,
		long fileEntryTypeId, Map<String, DDMFormValues> ddmFormValuesMap,
		File file, InputStream is, long size, ServiceContext serviceContext)
		throws PortalException;

	public DLFileEntry updateFileEntryType(long userId, long fileEntryId,
		long fileEntryTypeId, ServiceContext serviceContext)
		throws PortalException;

	public void updateSmallImage(long smallImageId, long largeImageId)
		throws PortalException;

	public DLFileEntry updateStatus(long userId, long fileVersionId,
		int status, ServiceContext serviceContext,
		Map<String, Serializable> workflowContext) throws PortalException;

	public void validateFile(long groupId, long folderId, long fileEntryId,
		String fileName, String title) throws PortalException;

	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException;

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException;
}