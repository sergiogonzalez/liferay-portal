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

import com.liferay.document.library.google.drive.exception.NoSuchEntryException;
import com.liferay.document.library.google.drive.model.GoogleDriveEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the google drive entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.document.library.google.drive.service.persistence.impl.GoogleDriveEntryPersistenceImpl
 * @see GoogleDriveEntryUtil
 * @generated
 */
@ProviderType
public interface GoogleDriveEntryPersistence extends BasePersistence<GoogleDriveEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link GoogleDriveEntryUtil} to access the google drive entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the google drive entry where groupId = &#63; and fileEntryId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @return the matching google drive entry
	* @throws NoSuchEntryException if a matching google drive entry could not be found
	*/
	public GoogleDriveEntry findByG_F(long groupId, long fileEntryId)
		throws NoSuchEntryException;

	/**
	* Returns the google drive entry where groupId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @return the matching google drive entry, or <code>null</code> if a matching google drive entry could not be found
	*/
	public GoogleDriveEntry fetchByG_F(long groupId, long fileEntryId);

	/**
	* Returns the google drive entry where groupId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching google drive entry, or <code>null</code> if a matching google drive entry could not be found
	*/
	public GoogleDriveEntry fetchByG_F(long groupId, long fileEntryId,
		boolean retrieveFromCache);

	/**
	* Removes the google drive entry where groupId = &#63; and fileEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @return the google drive entry that was removed
	*/
	public GoogleDriveEntry removeByG_F(long groupId, long fileEntryId)
		throws NoSuchEntryException;

	/**
	* Returns the number of google drive entries where groupId = &#63; and fileEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fileEntryId the file entry ID
	* @return the number of matching google drive entries
	*/
	public int countByG_F(long groupId, long fileEntryId);

	/**
	* Caches the google drive entry in the entity cache if it is enabled.
	*
	* @param googleDriveEntry the google drive entry
	*/
	public void cacheResult(GoogleDriveEntry googleDriveEntry);

	/**
	* Caches the google drive entries in the entity cache if it is enabled.
	*
	* @param googleDriveEntries the google drive entries
	*/
	public void cacheResult(java.util.List<GoogleDriveEntry> googleDriveEntries);

	/**
	* Creates a new google drive entry with the primary key. Does not add the google drive entry to the database.
	*
	* @param entryId the primary key for the new google drive entry
	* @return the new google drive entry
	*/
	public GoogleDriveEntry create(long entryId);

	/**
	* Removes the google drive entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the google drive entry
	* @return the google drive entry that was removed
	* @throws NoSuchEntryException if a google drive entry with the primary key could not be found
	*/
	public GoogleDriveEntry remove(long entryId) throws NoSuchEntryException;

	public GoogleDriveEntry updateImpl(GoogleDriveEntry googleDriveEntry);

	/**
	* Returns the google drive entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the google drive entry
	* @return the google drive entry
	* @throws NoSuchEntryException if a google drive entry with the primary key could not be found
	*/
	public GoogleDriveEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException;

	/**
	* Returns the google drive entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the google drive entry
	* @return the google drive entry, or <code>null</code> if a google drive entry with the primary key could not be found
	*/
	public GoogleDriveEntry fetchByPrimaryKey(long entryId);

	@Override
	public java.util.Map<java.io.Serializable, GoogleDriveEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the google drive entries.
	*
	* @return the google drive entries
	*/
	public java.util.List<GoogleDriveEntry> findAll();

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
	public java.util.List<GoogleDriveEntry> findAll(int start, int end);

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
	public java.util.List<GoogleDriveEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GoogleDriveEntry> orderByComparator);

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
	public java.util.List<GoogleDriveEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GoogleDriveEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the google drive entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of google drive entries.
	*
	* @return the number of google drive entries
	*/
	public int countAll();
}