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

package com.liferay.portlet.journal.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link JournalFolderLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFolderLocalService
 * @generated
 */
public class JournalFolderLocalServiceWrapper
	implements JournalFolderLocalService,
		ServiceWrapper<JournalFolderLocalService> {
	public JournalFolderLocalServiceWrapper(
		JournalFolderLocalService journalFolderLocalService) {
		_journalFolderLocalService = journalFolderLocalService;
	}

	/**
	* Adds the journal folder to the database. Also notifies the appropriate model listeners.
	*
	* @param journalFolder the journal folder
	* @return the journal folder that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalFolder addJournalFolder(
		com.liferay.portlet.journal.model.JournalFolder journalFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.addJournalFolder(journalFolder);
	}

	/**
	* Creates a new journal folder with the primary key. Does not add the journal folder to the database.
	*
	* @param folderId the primary key for the new journal folder
	* @return the new journal folder
	*/
	public com.liferay.portlet.journal.model.JournalFolder createJournalFolder(
		long folderId) {
		return _journalFolderLocalService.createJournalFolder(folderId);
	}

	/**
	* Deletes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the journal folder
	* @throws PortalException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteJournalFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFolderLocalService.deleteJournalFolder(folderId);
	}

	/**
	* Deletes the journal folder from the database. Also notifies the appropriate model listeners.
	*
	* @param journalFolder the journal folder
	* @throws SystemException if a system exception occurred
	*/
	public void deleteJournalFolder(
		com.liferay.portlet.journal.model.JournalFolder journalFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalFolderLocalService.deleteJournalFolder(journalFolder);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.journal.model.JournalFolder fetchJournalFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.fetchJournalFolder(folderId);
	}

	/**
	* Returns the journal folder with the primary key.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder
	* @throws PortalException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalFolder getJournalFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getJournalFolder(folderId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the journal folder with the UUID in the group.
	*
	* @param uuid the UUID of journal folder
	* @param groupId the group id of the journal folder
	* @return the journal folder
	* @throws PortalException if a journal folder with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalFolder getJournalFolderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getJournalFolderByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the journal folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of journal folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getJournalFolders(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getJournalFolders(start, end);
	}

	/**
	* Returns the number of journal folders.
	*
	* @return the number of journal folders
	* @throws SystemException if a system exception occurred
	*/
	public int getJournalFoldersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getJournalFoldersCount();
	}

	/**
	* Updates the journal folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalFolder the journal folder
	* @return the journal folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalFolder updateJournalFolder(
		com.liferay.portlet.journal.model.JournalFolder journalFolder)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.updateJournalFolder(journalFolder);
	}

	/**
	* Updates the journal folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalFolder the journal folder
	* @param merge whether to merge the journal folder with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the journal folder that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalFolder updateJournalFolder(
		com.liferay.portlet.journal.model.JournalFolder journalFolder,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.updateJournalFolder(journalFolder,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _journalFolderLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_journalFolderLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.journal.model.JournalFolder addFolder(
		long userId, long groupId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.addFolder(userId, groupId,
			parentFolderId, name, description, serviceContext);
	}

	public void deleteFolder(
		com.liferay.portlet.journal.model.JournalFolder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFolderLocalService.deleteFolder(folder);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFolderLocalService.deleteFolder(folderId);
	}

	public void deleteFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFolderLocalService.deleteFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getCompanyFolders(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getCompanyFolders(companyId, start,
			end);
	}

	public int getCompanyFoldersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getCompanyFoldersCount(companyId);
	}

	public com.liferay.portlet.journal.model.JournalFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getFolder(folderId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getFolders(groupId, parentFolderId,
			start, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.getFoldersCount(groupId,
			parentFolderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalFolderLocalService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public com.liferay.portlet.journal.model.JournalFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderLocalService.updateFolder(folderId,
			parentFolderId, name, description, mergeWithParentFolder,
			serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public JournalFolderLocalService getWrappedJournalFolderLocalService() {
		return _journalFolderLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {
		_journalFolderLocalService = journalFolderLocalService;
	}

	public JournalFolderLocalService getWrappedService() {
		return _journalFolderLocalService;
	}

	public void setWrappedService(
		JournalFolderLocalService journalFolderLocalService) {
		_journalFolderLocalService = journalFolderLocalService;
	}

	private JournalFolderLocalService _journalFolderLocalService;
}