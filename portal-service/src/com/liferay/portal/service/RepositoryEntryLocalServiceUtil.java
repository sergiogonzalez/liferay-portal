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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the repository entry local service. This utility wraps {@link com.liferay.portal.service.impl.RepositoryEntryLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntryLocalService
 * @see com.liferay.portal.service.base.RepositoryEntryLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.RepositoryEntryLocalServiceImpl
 * @generated
 */
public class RepositoryEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.RepositoryEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the repository entry to the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntry the repository entry
	* @return the repository entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry addRepositoryEntry(
		com.liferay.portal.model.RepositoryEntry repositoryEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addRepositoryEntry(repositoryEntry);
	}

	/**
	* Creates a new repository entry with the primary key. Does not add the repository entry to the database.
	*
	* @param repositoryEntryId the primary key for the new repository entry
	* @return the new repository entry
	*/
	public static com.liferay.portal.model.RepositoryEntry createRepositoryEntry(
		long repositoryEntryId) {
		return getService().createRepositoryEntry(repositoryEntryId);
	}

	/**
	* Deletes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry that was removed
	* @throws PortalException if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry deleteRepositoryEntry(
		long repositoryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteRepositoryEntry(repositoryEntryId);
	}

	/**
	* Deletes the repository entry from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntry the repository entry
	* @return the repository entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry deleteRepositoryEntry(
		com.liferay.portal.model.RepositoryEntry repositoryEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteRepositoryEntry(repositoryEntry);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.RepositoryEntry fetchRepositoryEntry(
		long repositoryEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchRepositoryEntry(repositoryEntryId);
	}

	/**
	* Returns the repository entry with the primary key.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry
	* @throws PortalException if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry getRepositoryEntry(
		long repositoryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRepositoryEntry(repositoryEntryId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the repository entry with the UUID in the group.
	*
	* @param uuid the UUID of repository entry
	* @param groupId the group id of the repository entry
	* @return the repository entry
	* @throws PortalException if a repository entry with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry getRepositoryEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRepositoryEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.RepositoryEntry> getRepositoryEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRepositoryEntries(start, end);
	}

	/**
	* Returns the number of repository entries.
	*
	* @return the number of repository entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getRepositoryEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRepositoryEntriesCount();
	}

	/**
	* Updates the repository entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntry the repository entry
	* @return the repository entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry updateRepositoryEntry(
		com.liferay.portal.model.RepositoryEntry repositoryEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRepositoryEntry(repositoryEntry);
	}

	/**
	* Updates the repository entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntry the repository entry
	* @param merge whether to merge the repository entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the repository entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RepositoryEntry updateRepositoryEntry(
		com.liferay.portal.model.RepositoryEntry repositoryEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRepositoryEntry(repositoryEntry, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static RepositoryEntryLocalService getService() {
		if (_service == null) {
			_service = (RepositoryEntryLocalService)PortalBeanLocatorUtil.locate(RepositoryEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(RepositoryEntryLocalServiceUtil.class,
				"_service");
			MethodCache.remove(RepositoryEntryLocalService.class);
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(RepositoryEntryLocalService service) {
	}

	private static RepositoryEntryLocalService _service;
}