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

package com.liferay.portlet.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalFolder;

/**
 * The persistence interface for the journal folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderPersistenceImpl
 * @see JournalFolderUtil
 * @generated
 */
@ProviderType
public interface JournalFolderPersistence extends BasePersistence<JournalFolder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalFolderUtil} to access the journal folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the journal folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the journal folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where uuid = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] findByUuid_PrevAndNext(
		long folderId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of journal folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal folders
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByUUID_G(
		java.lang.String uuid, long groupId);

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache);

	/**
	* Removes the journal folder where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal folder that was removed
	*/
	public com.liferay.portlet.journal.model.JournalFolder removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the number of journal folders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal folders
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the journal folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the journal folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] findByUuid_C_PrevAndNext(
		long folderId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of journal folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching journal folders
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the journal folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the journal folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] findByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] filterFindByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of journal folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal folders
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal folders that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the journal folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the journal folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where companyId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] findByCompanyId_PrevAndNext(
		long folderId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of journal folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching journal folders
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P(
		long groupId, long parentFolderId);

	/**
	* Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByG_P_First(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_P_First(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByG_P_Last(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_P_Last(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] findByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P(
		long groupId, long parentFolderId);

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] filterFindByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	*/
	public void removeByG_P(long groupId, long parentFolderId);

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching journal folders
	*/
	public int countByG_P(long groupId, long parentFolderId);

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching journal folders that the user has permission to view
	*/
	public int filterCountByG_P(long groupId, long parentFolderId);

	/**
	* Returns the journal folder where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_N(
		long groupId, java.lang.String name);

	/**
	* Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_N(
		long groupId, java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the journal folder where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the journal folder that was removed
	*/
	public com.liferay.portlet.journal.model.JournalFolder removeByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the number of journal folders where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching journal folders
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByC_NotS(
		long companyId, int status);

	/**
	* Returns a range of all the journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByC_NotS(
		long companyId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByC_NotS(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByC_NotS_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByC_NotS_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByC_NotS_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByC_NotS_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] findByC_NotS_PrevAndNext(
		long folderId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_NotS(long companyId, int status);

	/**
	* Returns the number of journal folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public int countByC_NotS(long companyId, int status);

	/**
	* Returns all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByF_C_P_NotS_First(
		long folderId, long companyId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByF_C_P_NotS_First(
		long folderId, long companyId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByF_C_P_NotS_Last(
		long folderId, long companyId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByF_C_P_NotS_Last(
		long folderId, long companyId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status);

	/**
	* Returns the number of journal folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public int countByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status);

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name);

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the journal folder that was removed
	*/
	public com.liferay.portlet.journal.model.JournalFolder removeByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the number of matching journal folders
	*/
	public int countByG_P_N(long groupId, long parentFolderId,
		java.lang.String name);

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P_S(
		long groupId, long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByG_P_S_First(
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_P_S_First(
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByG_P_S_Last(
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_P_S_Last(
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] findByG_P_S_PrevAndNext(
		long folderId, long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P_S(
		long groupId, long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] filterFindByG_P_S_PrevAndNext(
		long folderId, long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByG_P_S(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public int countByG_P_S(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders that the user has permission to view
	*/
	public int filterCountByG_P_S(long groupId, long parentFolderId, int status);

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByG_P_NotS_First(
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_P_NotS_First(
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByG_P_NotS_Last(
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByG_P_NotS_Last(
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] findByG_P_NotS_PrevAndNext(
		long folderId, long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P_NotS(
		long groupId, long parentFolderId, int status);

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder[] filterFindByG_P_NotS_PrevAndNext(
		long folderId, long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByG_P_NotS(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders
	*/
	public int countByG_P_NotS(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching journal folders that the user has permission to view
	*/
	public int filterCountByG_P_NotS(long groupId, long parentFolderId,
		int status);

	/**
	* Caches the journal folder in the entity cache if it is enabled.
	*
	* @param journalFolder the journal folder
	*/
	public void cacheResult(
		com.liferay.portlet.journal.model.JournalFolder journalFolder);

	/**
	* Caches the journal folders in the entity cache if it is enabled.
	*
	* @param journalFolders the journal folders
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalFolder> journalFolders);

	/**
	* Creates a new journal folder with the primary key. Does not add the journal folder to the database.
	*
	* @param folderId the primary key for the new journal folder
	* @return the new journal folder
	*/
	public com.liferay.portlet.journal.model.JournalFolder create(long folderId);

	/**
	* Removes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder that was removed
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder remove(long folderId)
		throws com.liferay.portlet.journal.NoSuchFolderException;

	public com.liferay.portlet.journal.model.JournalFolder updateImpl(
		com.liferay.portlet.journal.model.JournalFolder journalFolder);

	/**
	* Returns the journal folder with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder findByPrimaryKey(
		long folderId) throws com.liferay.portlet.journal.NoSuchFolderException;

	/**
	* Returns the journal folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder, or <code>null</code> if a journal folder with the primary key could not be found
	*/
	public com.liferay.portlet.journal.model.JournalFolder fetchByPrimaryKey(
		long folderId);

	/**
	* Returns all the journal folders.
	*
	* @return the journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findAll();

	/**
	* Returns a range of all the journal folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the journal folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal folders
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the journal folders from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of journal folders.
	*
	* @return the number of journal folders
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of d d m structures associated with the journal folder.
	*
	* @param pk the primary key of the journal folder
	* @return long[] of the primaryKeys of d d m structures associated with the journal folder
	*/
	public long[] getDDMStructurePrimaryKeys(long pk);

	/**
	* Returns all the d d m structures associated with the journal folder.
	*
	* @param pk the primary key of the journal folder
	* @return the d d m structures associated with the journal folder
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk);

	/**
	* Returns a range of all the d d m structures associated with the journal folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the journal folder
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of d d m structures associated with the journal folder
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures associated with the journal folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the journal folder
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structures associated with the journal folder
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the number of d d m structures associated with the journal folder.
	*
	* @param pk the primary key of the journal folder
	* @return the number of d d m structures associated with the journal folder
	*/
	public int getDDMStructuresSize(long pk);

	/**
	* Returns <code>true</code> if the d d m structure is associated with the journal folder.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructurePK the primary key of the d d m structure
	* @return <code>true</code> if the d d m structure is associated with the journal folder; <code>false</code> otherwise
	*/
	public boolean containsDDMStructure(long pk, long ddmStructurePK);

	/**
	* Returns <code>true</code> if the journal folder has any d d m structures associated with it.
	*
	* @param pk the primary key of the journal folder to check for associations with d d m structures
	* @return <code>true</code> if the journal folder has any d d m structures associated with it; <code>false</code> otherwise
	*/
	public boolean containsDDMStructures(long pk);

	/**
	* Adds an association between the journal folder and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructurePK the primary key of the d d m structure
	*/
	public void addDDMStructure(long pk, long ddmStructurePK);

	/**
	* Adds an association between the journal folder and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructure the d d m structure
	*/
	public void addDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure);

	/**
	* Adds an association between the journal folder and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructurePKs the primary keys of the d d m structures
	*/
	public void addDDMStructures(long pk, long[] ddmStructurePKs);

	/**
	* Adds an association between the journal folder and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructures the d d m structures
	*/
	public void addDDMStructures(long pk,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures);

	/**
	* Clears all associations between the journal folder and its d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder to clear the associated d d m structures from
	*/
	public void clearDDMStructures(long pk);

	/**
	* Removes the association between the journal folder and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructurePK the primary key of the d d m structure
	*/
	public void removeDDMStructure(long pk, long ddmStructurePK);

	/**
	* Removes the association between the journal folder and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructure the d d m structure
	*/
	public void removeDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure);

	/**
	* Removes the association between the journal folder and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructurePKs the primary keys of the d d m structures
	*/
	public void removeDDMStructures(long pk, long[] ddmStructurePKs);

	/**
	* Removes the association between the journal folder and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructures the d d m structures
	*/
	public void removeDDMStructures(long pk,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures);

	/**
	* Sets the d d m structures associated with the journal folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructurePKs the primary keys of the d d m structures to be associated with the journal folder
	*/
	public void setDDMStructures(long pk, long[] ddmStructurePKs);

	/**
	* Sets the d d m structures associated with the journal folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the journal folder
	* @param ddmStructures the d d m structures to be associated with the journal folder
	*/
	public void setDDMStructures(long pk,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures);
}