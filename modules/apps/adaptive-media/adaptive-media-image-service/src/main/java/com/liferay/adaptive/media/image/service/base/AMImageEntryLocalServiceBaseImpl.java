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

package com.liferay.adaptive.media.image.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.image.service.persistence.AMImageEntryPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the am image entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.adaptive.media.image.service.impl.AMImageEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.adaptive.media.image.service.impl.AMImageEntryLocalServiceImpl
 * @see com.liferay.adaptive.media.image.service.AMImageEntryLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class AMImageEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements AMImageEntryLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.adaptive.media.image.service.AMImageEntryLocalServiceUtil} to access the am image entry local service.
	 */

	/**
	 * Adds the am image entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param amImageEntry the am image entry
	 * @return the am image entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AMImageEntry addAMImageEntry(AMImageEntry amImageEntry) {
		amImageEntry.setNew(true);

		return amImageEntryPersistence.update(amImageEntry);
	}

	/**
	 * Creates a new am image entry with the primary key. Does not add the am image entry to the database.
	 *
	 * @param amImageEntryId the primary key for the new am image entry
	 * @return the new am image entry
	 */
	@Override
	public AMImageEntry createAMImageEntry(long amImageEntryId) {
		return amImageEntryPersistence.create(amImageEntryId);
	}

	/**
	 * Deletes the am image entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry that was removed
	 * @throws PortalException if a am image entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AMImageEntry deleteAMImageEntry(long amImageEntryId)
		throws PortalException {
		return amImageEntryPersistence.remove(amImageEntryId);
	}

	/**
	 * Deletes the am image entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param amImageEntry the am image entry
	 * @return the am image entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AMImageEntry deleteAMImageEntry(AMImageEntry amImageEntry) {
		return amImageEntryPersistence.remove(amImageEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(AMImageEntry.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return amImageEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.adaptive.media.image.model.impl.AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return amImageEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.adaptive.media.image.model.impl.AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return amImageEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return amImageEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return amImageEntryPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public AMImageEntry fetchAMImageEntry(long amImageEntryId) {
		return amImageEntryPersistence.fetchByPrimaryKey(amImageEntryId);
	}

	/**
	 * Returns the am image entry matching the UUID and group.
	 *
	 * @param uuid the am image entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchAMImageEntryByUuidAndGroupId(String uuid,
		long groupId) {
		return amImageEntryPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the am image entry with the primary key.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry
	 * @throws PortalException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry getAMImageEntry(long amImageEntryId)
		throws PortalException {
		return amImageEntryPersistence.findByPrimaryKey(amImageEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(amImageEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AMImageEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("amImageEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(amImageEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(AMImageEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"amImageEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(amImageEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AMImageEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("amImageEntryId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return amImageEntryLocalService.deleteAMImageEntry((AMImageEntry)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return amImageEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the am image entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the am image entries
	 * @param companyId the primary key of the company
	 * @return the matching am image entries, or an empty list if no matches were found
	 */
	@Override
	public List<AMImageEntry> getAMImageEntriesByUuidAndCompanyId(String uuid,
		long companyId) {
		return amImageEntryPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of am image entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the am image entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching am image entries, or an empty list if no matches were found
	 */
	@Override
	public List<AMImageEntry> getAMImageEntriesByUuidAndCompanyId(String uuid,
		long companyId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {
		return amImageEntryPersistence.findByUuid_C(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	 * Returns the am image entry matching the UUID and group.
	 *
	 * @param uuid the am image entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching am image entry
	 * @throws PortalException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry getAMImageEntryByUuidAndGroupId(String uuid,
		long groupId) throws PortalException {
		return amImageEntryPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.adaptive.media.image.model.impl.AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of am image entries
	 */
	@Override
	public List<AMImageEntry> getAMImageEntries(int start, int end) {
		return amImageEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of am image entries.
	 *
	 * @return the number of am image entries
	 */
	@Override
	public int getAMImageEntriesCount() {
		return amImageEntryPersistence.countAll();
	}

	/**
	 * Updates the am image entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param amImageEntry the am image entry
	 * @return the am image entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AMImageEntry updateAMImageEntry(AMImageEntry amImageEntry) {
		return amImageEntryPersistence.update(amImageEntry);
	}

	/**
	 * Returns the am image entry local service.
	 *
	 * @return the am image entry local service
	 */
	public AMImageEntryLocalService getAMImageEntryLocalService() {
		return amImageEntryLocalService;
	}

	/**
	 * Sets the am image entry local service.
	 *
	 * @param amImageEntryLocalService the am image entry local service
	 */
	public void setAMImageEntryLocalService(
		AMImageEntryLocalService amImageEntryLocalService) {
		this.amImageEntryLocalService = amImageEntryLocalService;
	}

	/**
	 * Returns the am image entry persistence.
	 *
	 * @return the am image entry persistence
	 */
	public AMImageEntryPersistence getAMImageEntryPersistence() {
		return amImageEntryPersistence;
	}

	/**
	 * Sets the am image entry persistence.
	 *
	 * @param amImageEntryPersistence the am image entry persistence
	 */
	public void setAMImageEntryPersistence(
		AMImageEntryPersistence amImageEntryPersistence) {
		this.amImageEntryPersistence = amImageEntryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.adaptive.media.image.model.AMImageEntry",
			amImageEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.adaptive.media.image.model.AMImageEntry");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return AMImageEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return AMImageEntry.class;
	}

	protected String getModelClassName() {
		return AMImageEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = amImageEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = AMImageEntryLocalService.class)
	protected AMImageEntryLocalService amImageEntryLocalService;
	@BeanReference(type = AMImageEntryPersistence.class)
	protected AMImageEntryPersistence amImageEntryPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}