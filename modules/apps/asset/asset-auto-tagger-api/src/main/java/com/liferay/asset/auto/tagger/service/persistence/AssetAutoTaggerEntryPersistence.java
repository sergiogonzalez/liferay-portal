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

package com.liferay.asset.auto.tagger.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.auto.tagger.exception.NoSuchEntryException;
import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset auto tagger entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.auto.tagger.service.persistence.impl.AssetAutoTaggerEntryPersistenceImpl
 * @see AssetAutoTaggerEntryUtil
 * @generated
 */
@ProviderType
public interface AssetAutoTaggerEntryPersistence extends BasePersistence<AssetAutoTaggerEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetAutoTaggerEntryUtil} to access the asset auto tagger entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset auto tagger entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findByUuid(String uuid);

	/**
	* Returns a range of all the asset auto tagger entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @return the range of matching asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findByUuid(String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the asset auto tagger entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset auto tagger entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator);

	/**
	* Returns the last asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator);

	/**
	* Returns the asset auto tagger entries before and after the current asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param assetAutoTaggerEntryId the primary key of the current asset auto tagger entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset auto tagger entry
	* @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	*/
	public AssetAutoTaggerEntry[] findByUuid_PrevAndNext(
		long assetAutoTaggerEntryId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the asset auto tagger entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of asset auto tagger entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching asset auto tagger entries
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the asset auto tagger entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	* Returns the asset auto tagger entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the asset auto tagger entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the asset auto tagger entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the asset auto tagger entry that was removed
	*/
	public AssetAutoTaggerEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	* Returns the number of asset auto tagger entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching asset auto tagger entries
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the asset auto tagger entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the asset auto tagger entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @return the range of matching asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the asset auto tagger entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset auto tagger entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset auto tagger entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first asset auto tagger entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator);

	/**
	* Returns the last asset auto tagger entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last asset auto tagger entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator);

	/**
	* Returns the asset auto tagger entries before and after the current asset auto tagger entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param assetAutoTaggerEntryId the primary key of the current asset auto tagger entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset auto tagger entry
	* @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	*/
	public AssetAutoTaggerEntry[] findByUuid_C_PrevAndNext(
		long assetAutoTaggerEntryId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the asset auto tagger entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of asset auto tagger entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching asset auto tagger entries
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @return the matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry findByA_A(long assetEntryId, long assetTagId)
		throws NoSuchEntryException;

	/**
	* Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry fetchByA_A(long assetEntryId, long assetTagId);

	/**
	* Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public AssetAutoTaggerEntry fetchByA_A(long assetEntryId, long assetTagId,
		boolean retrieveFromCache);

	/**
	* Removes the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @return the asset auto tagger entry that was removed
	*/
	public AssetAutoTaggerEntry removeByA_A(long assetEntryId, long assetTagId)
		throws NoSuchEntryException;

	/**
	* Returns the number of asset auto tagger entries where assetEntryId = &#63; and assetTagId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @return the number of matching asset auto tagger entries
	*/
	public int countByA_A(long assetEntryId, long assetTagId);

	/**
	* Caches the asset auto tagger entry in the entity cache if it is enabled.
	*
	* @param assetAutoTaggerEntry the asset auto tagger entry
	*/
	public void cacheResult(AssetAutoTaggerEntry assetAutoTaggerEntry);

	/**
	* Caches the asset auto tagger entries in the entity cache if it is enabled.
	*
	* @param assetAutoTaggerEntries the asset auto tagger entries
	*/
	public void cacheResult(
		java.util.List<AssetAutoTaggerEntry> assetAutoTaggerEntries);

	/**
	* Creates a new asset auto tagger entry with the primary key. Does not add the asset auto tagger entry to the database.
	*
	* @param assetAutoTaggerEntryId the primary key for the new asset auto tagger entry
	* @return the new asset auto tagger entry
	*/
	public AssetAutoTaggerEntry create(long assetAutoTaggerEntryId);

	/**
	* Removes the asset auto tagger entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	* @return the asset auto tagger entry that was removed
	* @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	*/
	public AssetAutoTaggerEntry remove(long assetAutoTaggerEntryId)
		throws NoSuchEntryException;

	public AssetAutoTaggerEntry updateImpl(
		AssetAutoTaggerEntry assetAutoTaggerEntry);

	/**
	* Returns the asset auto tagger entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	* @return the asset auto tagger entry
	* @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	*/
	public AssetAutoTaggerEntry findByPrimaryKey(long assetAutoTaggerEntryId)
		throws NoSuchEntryException;

	/**
	* Returns the asset auto tagger entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	* @return the asset auto tagger entry, or <code>null</code> if a asset auto tagger entry with the primary key could not be found
	*/
	public AssetAutoTaggerEntry fetchByPrimaryKey(long assetAutoTaggerEntryId);

	@Override
	public java.util.Map<java.io.Serializable, AssetAutoTaggerEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset auto tagger entries.
	*
	* @return the asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findAll();

	/**
	* Returns a range of all the asset auto tagger entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @return the range of asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the asset auto tagger entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset auto tagger entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset auto tagger entries
	*/
	public java.util.List<AssetAutoTaggerEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset auto tagger entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset auto tagger entries.
	*
	* @return the number of asset auto tagger entries
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}