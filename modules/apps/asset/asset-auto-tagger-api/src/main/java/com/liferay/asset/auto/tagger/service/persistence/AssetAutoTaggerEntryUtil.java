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

import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the asset auto tagger entry service. This utility wraps {@link com.liferay.asset.auto.tagger.service.persistence.impl.AssetAutoTaggerEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntryPersistence
 * @see com.liferay.asset.auto.tagger.service.persistence.impl.AssetAutoTaggerEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetAutoTaggerEntryUtil {
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
	public static void clearCache(AssetAutoTaggerEntry assetAutoTaggerEntry) {
		getPersistence().clearCache(assetAutoTaggerEntry);
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
	public static List<AssetAutoTaggerEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetAutoTaggerEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetAutoTaggerEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetAutoTaggerEntry update(
		AssetAutoTaggerEntry assetAutoTaggerEntry) {
		return getPersistence().update(assetAutoTaggerEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetAutoTaggerEntry update(
		AssetAutoTaggerEntry assetAutoTaggerEntry, ServiceContext serviceContext) {
		return getPersistence().update(assetAutoTaggerEntry, serviceContext);
	}

	/**
	* Returns all the asset auto tagger entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching asset auto tagger entries
	*/
	public static List<AssetAutoTaggerEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<AssetAutoTaggerEntry> findByUuid(String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<AssetAutoTaggerEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<AssetAutoTaggerEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry findByUuid_First(String uuid,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry fetchByUuid_First(String uuid,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry findByUuid_Last(String uuid,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry fetchByUuid_Last(String uuid,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the asset auto tagger entries before and after the current asset auto tagger entry in the ordered set where uuid = &#63;.
	*
	* @param assetAutoTaggerEntryId the primary key of the current asset auto tagger entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset auto tagger entry
	* @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	*/
	public static AssetAutoTaggerEntry[] findByUuid_PrevAndNext(
		long assetAutoTaggerEntryId, String uuid,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(assetAutoTaggerEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the asset auto tagger entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of asset auto tagger entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching asset auto tagger entries
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the asset auto tagger entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset auto tagger entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset auto tagger entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the asset auto tagger entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the asset auto tagger entry that was removed
	*/
	public static AssetAutoTaggerEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of asset auto tagger entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching asset auto tagger entries
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the asset auto tagger entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching asset auto tagger entries
	*/
	public static List<AssetAutoTaggerEntry> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<AssetAutoTaggerEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<AssetAutoTaggerEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<AssetAutoTaggerEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset auto tagger entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first asset auto tagger entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset auto tagger entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset auto tagger entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

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
	public static AssetAutoTaggerEntry[] findByUuid_C_PrevAndNext(
		long assetAutoTaggerEntryId, String uuid, long companyId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(assetAutoTaggerEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the asset auto tagger entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of asset auto tagger entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching asset auto tagger entries
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @return the matching asset auto tagger entry
	* @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry findByA_A(long assetEntryId,
		long assetTagId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence().findByA_A(assetEntryId, assetTagId);
	}

	/**
	* Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry fetchByA_A(long assetEntryId,
		long assetTagId) {
		return getPersistence().fetchByA_A(assetEntryId, assetTagId);
	}

	/**
	* Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	public static AssetAutoTaggerEntry fetchByA_A(long assetEntryId,
		long assetTagId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByA_A(assetEntryId, assetTagId, retrieveFromCache);
	}

	/**
	* Removes the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @return the asset auto tagger entry that was removed
	*/
	public static AssetAutoTaggerEntry removeByA_A(long assetEntryId,
		long assetTagId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence().removeByA_A(assetEntryId, assetTagId);
	}

	/**
	* Returns the number of asset auto tagger entries where assetEntryId = &#63; and assetTagId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param assetTagId the asset tag ID
	* @return the number of matching asset auto tagger entries
	*/
	public static int countByA_A(long assetEntryId, long assetTagId) {
		return getPersistence().countByA_A(assetEntryId, assetTagId);
	}

	/**
	* Caches the asset auto tagger entry in the entity cache if it is enabled.
	*
	* @param assetAutoTaggerEntry the asset auto tagger entry
	*/
	public static void cacheResult(AssetAutoTaggerEntry assetAutoTaggerEntry) {
		getPersistence().cacheResult(assetAutoTaggerEntry);
	}

	/**
	* Caches the asset auto tagger entries in the entity cache if it is enabled.
	*
	* @param assetAutoTaggerEntries the asset auto tagger entries
	*/
	public static void cacheResult(
		List<AssetAutoTaggerEntry> assetAutoTaggerEntries) {
		getPersistence().cacheResult(assetAutoTaggerEntries);
	}

	/**
	* Creates a new asset auto tagger entry with the primary key. Does not add the asset auto tagger entry to the database.
	*
	* @param assetAutoTaggerEntryId the primary key for the new asset auto tagger entry
	* @return the new asset auto tagger entry
	*/
	public static AssetAutoTaggerEntry create(long assetAutoTaggerEntryId) {
		return getPersistence().create(assetAutoTaggerEntryId);
	}

	/**
	* Removes the asset auto tagger entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	* @return the asset auto tagger entry that was removed
	* @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	*/
	public static AssetAutoTaggerEntry remove(long assetAutoTaggerEntryId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence().remove(assetAutoTaggerEntryId);
	}

	public static AssetAutoTaggerEntry updateImpl(
		AssetAutoTaggerEntry assetAutoTaggerEntry) {
		return getPersistence().updateImpl(assetAutoTaggerEntry);
	}

	/**
	* Returns the asset auto tagger entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	* @return the asset auto tagger entry
	* @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	*/
	public static AssetAutoTaggerEntry findByPrimaryKey(
		long assetAutoTaggerEntryId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(assetAutoTaggerEntryId);
	}

	/**
	* Returns the asset auto tagger entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	* @return the asset auto tagger entry, or <code>null</code> if a asset auto tagger entry with the primary key could not be found
	*/
	public static AssetAutoTaggerEntry fetchByPrimaryKey(
		long assetAutoTaggerEntryId) {
		return getPersistence().fetchByPrimaryKey(assetAutoTaggerEntryId);
	}

	public static java.util.Map<java.io.Serializable, AssetAutoTaggerEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset auto tagger entries.
	*
	* @return the asset auto tagger entries
	*/
	public static List<AssetAutoTaggerEntry> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<AssetAutoTaggerEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<AssetAutoTaggerEntry> findAll(int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<AssetAutoTaggerEntry> findAll(int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset auto tagger entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset auto tagger entries.
	*
	* @return the number of asset auto tagger entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static AssetAutoTaggerEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetAutoTaggerEntryPersistence, AssetAutoTaggerEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetAutoTaggerEntryPersistence.class);

		ServiceTracker<AssetAutoTaggerEntryPersistence, AssetAutoTaggerEntryPersistence> serviceTracker =
			new ServiceTracker<AssetAutoTaggerEntryPersistence, AssetAutoTaggerEntryPersistence>(bundle.getBundleContext(),
				AssetAutoTaggerEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}