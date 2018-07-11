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

package com.liferay.document.library.google.drive.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.google.drive.model.GoogleDriveEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the google drive entry service. This utility wraps {@link com.liferay.document.library.google.drive.service.persistence.impl.GoogleDriveEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GoogleDriveEntryPersistence
 * @see com.liferay.document.library.google.drive.service.persistence.impl.GoogleDriveEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class GoogleDriveEntryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(GoogleDriveEntry googleDriveEntry) {
		getPersistence().clearCache(googleDriveEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<GoogleDriveEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<GoogleDriveEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<GoogleDriveEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<GoogleDriveEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static GoogleDriveEntry update(GoogleDriveEntry googleDriveEntry) {
		return getPersistence().update(googleDriveEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static GoogleDriveEntry update(GoogleDriveEntry googleDriveEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(googleDriveEntry, serviceContext);
	}

	/**
	* Returns the google drive entry where groupId = &#63; and fileEntryId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @return the matching google drive entry
	* @throws NoSuchEntryException if a matching google drive entry could not be found
	*/
	public static GoogleDriveEntry findByG_F(long groupId, long fileEntryId)
		throws com.liferay.document.library.google.drive.exception.NoSuchEntryException {
		return getPersistence().findByG_F(groupId, fileEntryId);
	}

	/**
	* Returns the google drive entry where groupId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @return the matching google drive entry, or <code>null</code> if a matching google drive entry could not be found
	*/
	public static GoogleDriveEntry fetchByG_F(long groupId, long fileEntryId) {
		return getPersistence().fetchByG_F(groupId, fileEntryId);
	}

	/**
	* Returns the google drive entry where groupId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching google drive entry, or <code>null</code> if a matching google drive entry could not be found
	*/
	public static GoogleDriveEntry fetchByG_F(long groupId, long fileEntryId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_F(groupId, fileEntryId, retrieveFromCache);
	}

	/**
	* Removes the google drive entry where groupId = &#63; and fileEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @return the google drive entry that was removed
	*/
	public static GoogleDriveEntry removeByG_F(long groupId, long fileEntryId)
		throws com.liferay.document.library.google.drive.exception.NoSuchEntryException {
		return getPersistence().removeByG_F(groupId, fileEntryId);
	}

	/**
	* Returns the number of google drive entries where groupId = &#63; and fileEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @return the number of matching google drive entries
	*/
	public static int countByG_F(long groupId, long fileEntryId) {
		return getPersistence().countByG_F(groupId, fileEntryId);
	}

	/**
	* Caches the google drive entry in the entity cache if it is enabled.
	*
	* @param googleDriveEntry the google drive entry
	*/
	public static void cacheResult(GoogleDriveEntry googleDriveEntry) {
		getPersistence().cacheResult(googleDriveEntry);
	}

	/**
	* Caches the google drive entries in the entity cache if it is enabled.
	*
	* @param googleDriveEntries the google drive entries
	*/
	public static void cacheResult(List<GoogleDriveEntry> googleDriveEntries) {
		getPersistence().cacheResult(googleDriveEntries);
	}

	/**
	* Creates a new google drive entry with the primary key. Does not add the google drive entry to the database.
	*
	* @param entryId the primary key for the new google drive entry
	* @return the new google drive entry
	*/
	public static GoogleDriveEntry create(long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the google drive entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the google drive entry
	* @return the google drive entry that was removed
	* @throws NoSuchEntryException if a google drive entry with the primary key could not be found
	*/
	public static GoogleDriveEntry remove(long entryId)
		throws com.liferay.document.library.google.drive.exception.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static GoogleDriveEntry updateImpl(GoogleDriveEntry googleDriveEntry) {
		return getPersistence().updateImpl(googleDriveEntry);
	}

	/**
	* Returns the google drive entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the google drive entry
	* @return the google drive entry
	* @throws NoSuchEntryException if a google drive entry with the primary key could not be found
	*/
	public static GoogleDriveEntry findByPrimaryKey(long entryId)
		throws com.liferay.document.library.google.drive.exception.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Returns the google drive entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the google drive entry
	* @return the google drive entry, or <code>null</code> if a google drive entry with the primary key could not be found
	*/
	public static GoogleDriveEntry fetchByPrimaryKey(long entryId) {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.Map<java.io.Serializable, GoogleDriveEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the google drive entries.
	*
	* @return the google drive entries
	*/
	public static List<GoogleDriveEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the google drive entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GoogleDriveEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of google drive entries
	* @param end the upper bound of the range of google drive entries (not inclusive)
	* @return the range of google drive entries
	*/
	public static List<GoogleDriveEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the google drive entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GoogleDriveEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of google drive entries
	* @param end the upper bound of the range of google drive entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of google drive entries
	*/
	public static List<GoogleDriveEntry> findAll(int start, int end,
		OrderByComparator<GoogleDriveEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the google drive entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GoogleDriveEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of google drive entries
	* @param end the upper bound of the range of google drive entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of google drive entries
	*/
	public static List<GoogleDriveEntry> findAll(int start, int end,
		OrderByComparator<GoogleDriveEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the google drive entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of google drive entries.
	*
	* @return the number of google drive entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static GoogleDriveEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<GoogleDriveEntryPersistence, GoogleDriveEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(GoogleDriveEntryPersistence.class);

		ServiceTracker<GoogleDriveEntryPersistence, GoogleDriveEntryPersistence> serviceTracker =
			new ServiceTracker<GoogleDriveEntryPersistence, GoogleDriveEntryPersistence>(bundle.getBundleContext(),
				GoogleDriveEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}