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

package com.liferay.asset.auto.tagger.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetAutoTaggerEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntryLocalService
 * @generated
 */
@ProviderType
public class AssetAutoTaggerEntryLocalServiceWrapper
	implements AssetAutoTaggerEntryLocalService,
		ServiceWrapper<AssetAutoTaggerEntryLocalService> {
	public AssetAutoTaggerEntryLocalServiceWrapper(
		AssetAutoTaggerEntryLocalService assetAutoTaggerEntryLocalService) {
		_assetAutoTaggerEntryLocalService = assetAutoTaggerEntryLocalService;
	}

	/**
	* Adds the asset auto tagger entry to the database. Also notifies the appropriate model listeners.
	*
	* @param assetAutoTaggerEntry the asset auto tagger entry
	* @return the asset auto tagger entry that was added
	*/
	@Override
	public com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry addAssetAutoTaggerEntry(
		com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry assetAutoTaggerEntry) {
		return _assetAutoTaggerEntryLocalService.addAssetAutoTaggerEntry(assetAutoTaggerEntry);
	}

	/**
	* Creates a new asset auto tagger entry with the primary key. Does not add the asset auto tagger entry to the database.
	*
	* @param assetAutoTaggerEntryId the primary key for the new asset auto tagger entry
	* @return the new asset auto tagger entry
	*/
	@Override
	public com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry createAssetAutoTaggerEntry(
		long assetAutoTaggerEntryId) {
		return _assetAutoTaggerEntryLocalService.createAssetAutoTaggerEntry(assetAutoTaggerEntryId);
	}

	/**
	* Deletes the asset auto tagger entry from the database. Also notifies the appropriate model listeners.
	*
	* @param assetAutoTaggerEntry the asset auto tagger entry
	* @return the asset auto tagger entry that was removed
	*/
	@Override
	public com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry deleteAssetAutoTaggerEntry(
		com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry assetAutoTaggerEntry) {
		return _assetAutoTaggerEntryLocalService.deleteAssetAutoTaggerEntry(assetAutoTaggerEntry);
	}

	/**
	* Deletes the asset auto tagger entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	* @return the asset auto tagger entry that was removed
	* @throws PortalException if a asset auto tagger entry with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry deleteAssetAutoTaggerEntry(
		long assetAutoTaggerEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetAutoTaggerEntryLocalService.deleteAssetAutoTaggerEntry(assetAutoTaggerEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetAutoTaggerEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetAutoTaggerEntryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _assetAutoTaggerEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _assetAutoTaggerEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _assetAutoTaggerEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _assetAutoTaggerEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _assetAutoTaggerEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry fetchAssetAutoTaggerEntry(
		long assetAutoTaggerEntryId) {
		return _assetAutoTaggerEntryLocalService.fetchAssetAutoTaggerEntry(assetAutoTaggerEntryId);
	}

	/**
	* Returns the asset auto tagger entry matching the UUID and group.
	*
	* @param uuid the asset auto tagger entry's UUID
	* @param groupId the primary key of the group
	* @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	*/
	@Override
	public com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry fetchAssetAutoTaggerEntryByUuidAndGroupId(
		String uuid, long groupId) {
		return _assetAutoTaggerEntryLocalService.fetchAssetAutoTaggerEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _assetAutoTaggerEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the asset auto tagger entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @return the range of asset auto tagger entries
	*/
	@Override
	public java.util.List<com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry> getAssetAutoTaggerEntries(
		int start, int end) {
		return _assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntries(start,
			end);
	}

	/**
	* Returns all the asset auto tagger entries matching the UUID and company.
	*
	* @param uuid the UUID of the asset auto tagger entries
	* @param companyId the primary key of the company
	* @return the matching asset auto tagger entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry> getAssetAutoTaggerEntriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return _assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of asset auto tagger entries matching the UUID and company.
	*
	* @param uuid the UUID of the asset auto tagger entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of asset auto tagger entries
	* @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching asset auto tagger entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry> getAssetAutoTaggerEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry> orderByComparator) {
		return _assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset auto tagger entries.
	*
	* @return the number of asset auto tagger entries
	*/
	@Override
	public int getAssetAutoTaggerEntriesCount() {
		return _assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntriesCount();
	}

	/**
	* Returns the asset auto tagger entry with the primary key.
	*
	* @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	* @return the asset auto tagger entry
	* @throws PortalException if a asset auto tagger entry with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry getAssetAutoTaggerEntry(
		long assetAutoTaggerEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntry(assetAutoTaggerEntryId);
	}

	/**
	* Returns the asset auto tagger entry matching the UUID and group.
	*
	* @param uuid the asset auto tagger entry's UUID
	* @param groupId the primary key of the group
	* @return the matching asset auto tagger entry
	* @throws PortalException if a matching asset auto tagger entry could not be found
	*/
	@Override
	public com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry getAssetAutoTaggerEntryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _assetAutoTaggerEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _assetAutoTaggerEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetAutoTaggerEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetAutoTaggerEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the asset auto tagger entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetAutoTaggerEntry the asset auto tagger entry
	* @return the asset auto tagger entry that was updated
	*/
	@Override
	public com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry updateAssetAutoTaggerEntry(
		com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry assetAutoTaggerEntry) {
		return _assetAutoTaggerEntryLocalService.updateAssetAutoTaggerEntry(assetAutoTaggerEntry);
	}

	@Override
	public AssetAutoTaggerEntryLocalService getWrappedService() {
		return _assetAutoTaggerEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AssetAutoTaggerEntryLocalService assetAutoTaggerEntryLocalService) {
		_assetAutoTaggerEntryLocalService = assetAutoTaggerEntryLocalService;
	}

	private AssetAutoTaggerEntryLocalService _assetAutoTaggerEntryLocalService;
}