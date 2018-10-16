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

package com.liferay.segments.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SegmentsEntryLocalService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryLocalService
 * @generated
 */
@ProviderType
public class SegmentsEntryLocalServiceWrapper
	implements SegmentsEntryLocalService,
		ServiceWrapper<SegmentsEntryLocalService> {
	public SegmentsEntryLocalServiceWrapper(
		SegmentsEntryLocalService segmentsEntryLocalService) {
		_segmentsEntryLocalService = segmentsEntryLocalService;
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry addSegmentsEntry(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, boolean active,
		String criteria, String key, String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.addSegmentsEntry(nameMap,
			descriptionMap, active, criteria, key, type, serviceContext);
	}

	/**
	* Adds the segments entry to the database. Also notifies the appropriate model listeners.
	*
	* @param segmentsEntry the segments entry
	* @return the segments entry that was added
	*/
	@Override
	public com.liferay.segments.model.SegmentsEntry addSegmentsEntry(
		com.liferay.segments.model.SegmentsEntry segmentsEntry) {
		return _segmentsEntryLocalService.addSegmentsEntry(segmentsEntry);
	}

	/**
	* Creates a new segments entry with the primary key. Does not add the segments entry to the database.
	*
	* @param segmentsEntryId the primary key for the new segments entry
	* @return the new segments entry
	*/
	@Override
	public com.liferay.segments.model.SegmentsEntry createSegmentsEntry(
		long segmentsEntryId) {
		return _segmentsEntryLocalService.createSegmentsEntry(segmentsEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteSegmentsEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_segmentsEntryLocalService.deleteSegmentsEntries(groupId);
	}

	/**
	* Deletes the segments entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param segmentsEntryId the primary key of the segments entry
	* @return the segments entry that was removed
	* @throws PortalException if a segments entry with the primary key could not be found
	*/
	@Override
	public com.liferay.segments.model.SegmentsEntry deleteSegmentsEntry(
		long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntryId);
	}

	/**
	* Deletes the segments entry from the database. Also notifies the appropriate model listeners.
	*
	* @param segmentsEntry the segments entry
	* @return the segments entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.segments.model.SegmentsEntry deleteSegmentsEntry(
		com.liferay.segments.model.SegmentsEntry segmentsEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _segmentsEntryLocalService.dynamicQuery();
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
		return _segmentsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.segments.model.impl.SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _segmentsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.segments.model.impl.SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _segmentsEntryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _segmentsEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _segmentsEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry fetchSegmentsEntry(
		long segmentsEntryId) {
		return _segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry fetchSegmentsEntry(
		long groupId, String key) {
		return _segmentsEntryLocalService.fetchSegmentsEntry(groupId, key);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _segmentsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _segmentsEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the segments entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.segments.model.impl.SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of segments entries
	*/
	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntry> getSegmentsEntries(
		int start, int end) {
		return _segmentsEntryLocalService.getSegmentsEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntry> getSegmentsEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.segments.model.SegmentsEntry> orderByComparator) {
		return _segmentsEntryLocalService.getSegmentsEntries(groupId, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntry> getSegmentsEntries(
		long groupId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.segments.model.SegmentsEntry> orderByComparator) {
		return _segmentsEntryLocalService.getSegmentsEntries(groupId, type,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntry> getSegmentsEntries(
		String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.segments.model.SegmentsEntry> orderByComparator) {
		return _segmentsEntryLocalService.getSegmentsEntries(type, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of segments entries.
	*
	* @return the number of segments entries
	*/
	@Override
	public int getSegmentsEntriesCount() {
		return _segmentsEntryLocalService.getSegmentsEntriesCount();
	}

	@Override
	public int getSegmentsEntriesCount(long groupId) {
		return _segmentsEntryLocalService.getSegmentsEntriesCount(groupId);
	}

	/**
	* Returns the segments entry with the primary key.
	*
	* @param segmentsEntryId the primary key of the segments entry
	* @return the segments entry
	* @throws PortalException if a segments entry with the primary key could not be found
	*/
	@Override
	public com.liferay.segments.model.SegmentsEntry getSegmentsEntry(
		long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry getSegmentsEntry(
		long groupId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.getSegmentsEntry(groupId, key);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.segments.model.SegmentsEntry> searchSegmentsEntries(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.searchSegmentsEntries(companyId,
			groupId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.segments.model.SegmentsEntry> searchSegmentsEntries(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.searchSegmentsEntries(searchContext);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry updateSegmentsEntry(
		long segmentsEntryId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, boolean active,
		String criteria, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryLocalService.updateSegmentsEntry(segmentsEntryId,
			nameMap, descriptionMap, active, criteria, key, serviceContext);
	}

	/**
	* Updates the segments entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param segmentsEntry the segments entry
	* @return the segments entry that was updated
	*/
	@Override
	public com.liferay.segments.model.SegmentsEntry updateSegmentsEntry(
		com.liferay.segments.model.SegmentsEntry segmentsEntry) {
		return _segmentsEntryLocalService.updateSegmentsEntry(segmentsEntry);
	}

	@Override
	public SegmentsEntryLocalService getWrappedService() {
		return _segmentsEntryLocalService;
	}

	@Override
	public void setWrappedService(
		SegmentsEntryLocalService segmentsEntryLocalService) {
		_segmentsEntryLocalService = segmentsEntryLocalService;
	}

	private SegmentsEntryLocalService _segmentsEntryLocalService;
}