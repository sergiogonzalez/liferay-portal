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

package com.liferay.modern.site.building.fragment.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
import com.liferay.modern.site.building.fragment.service.MSBFragmentCollectionLocalService;
import com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentCollectionPersistence;
import com.liferay.modern.site.building.fragment.service.persistence.MSBFragmentEntryPersistence;

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
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the msb fragment collection local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionLocalServiceImpl
 * @see com.liferay.modern.site.building.fragment.service.MSBFragmentCollectionLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class MSBFragmentCollectionLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements MSBFragmentCollectionLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.modern.site.building.fragment.service.MSBFragmentCollectionLocalServiceUtil} to access the msb fragment collection local service.
	 */

	/**
	 * Adds the msb fragment collection to the database. Also notifies the appropriate model listeners.
	 *
	 * @param msbFragmentCollection the msb fragment collection
	 * @return the msb fragment collection that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public MSBFragmentCollection addMSBFragmentCollection(
		MSBFragmentCollection msbFragmentCollection) {
		msbFragmentCollection.setNew(true);

		return msbFragmentCollectionPersistence.update(msbFragmentCollection);
	}

	/**
	 * Creates a new msb fragment collection with the primary key. Does not add the msb fragment collection to the database.
	 *
	 * @param msbFragmentCollectionId the primary key for the new msb fragment collection
	 * @return the new msb fragment collection
	 */
	@Override
	public MSBFragmentCollection createMSBFragmentCollection(
		long msbFragmentCollectionId) {
		return msbFragmentCollectionPersistence.create(msbFragmentCollectionId);
	}

	/**
	 * Deletes the msb fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param msbFragmentCollectionId the primary key of the msb fragment collection
	 * @return the msb fragment collection that was removed
	 * @throws PortalException if a msb fragment collection with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public MSBFragmentCollection deleteMSBFragmentCollection(
		long msbFragmentCollectionId) throws PortalException {
		return msbFragmentCollectionPersistence.remove(msbFragmentCollectionId);
	}

	/**
	 * Deletes the msb fragment collection from the database. Also notifies the appropriate model listeners.
	 *
	 * @param msbFragmentCollection the msb fragment collection
	 * @return the msb fragment collection that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public MSBFragmentCollection deleteMSBFragmentCollection(
		MSBFragmentCollection msbFragmentCollection) throws PortalException {
		return msbFragmentCollectionPersistence.remove(msbFragmentCollection);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(MSBFragmentCollection.class,
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
		return msbFragmentCollectionPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return msbFragmentCollectionPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return msbFragmentCollectionPersistence.findWithDynamicQuery(dynamicQuery,
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
		return msbFragmentCollectionPersistence.countWithDynamicQuery(dynamicQuery);
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
		return msbFragmentCollectionPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public MSBFragmentCollection fetchMSBFragmentCollection(
		long msbFragmentCollectionId) {
		return msbFragmentCollectionPersistence.fetchByPrimaryKey(msbFragmentCollectionId);
	}

	/**
	 * Returns the msb fragment collection with the primary key.
	 *
	 * @param msbFragmentCollectionId the primary key of the msb fragment collection
	 * @return the msb fragment collection
	 * @throws PortalException if a msb fragment collection with the primary key could not be found
	 */
	@Override
	public MSBFragmentCollection getMSBFragmentCollection(
		long msbFragmentCollectionId) throws PortalException {
		return msbFragmentCollectionPersistence.findByPrimaryKey(msbFragmentCollectionId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(msbFragmentCollectionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(MSBFragmentCollection.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"msbFragmentCollectionId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(msbFragmentCollectionLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(MSBFragmentCollection.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"msbFragmentCollectionId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(msbFragmentCollectionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(MSBFragmentCollection.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"msbFragmentCollectionId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return msbFragmentCollectionLocalService.deleteMSBFragmentCollection((MSBFragmentCollection)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return msbFragmentCollectionPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the msb fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of msb fragment collections
	 * @param end the upper bound of the range of msb fragment collections (not inclusive)
	 * @return the range of msb fragment collections
	 */
	@Override
	public List<MSBFragmentCollection> getMSBFragmentCollections(int start,
		int end) {
		return msbFragmentCollectionPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of msb fragment collections.
	 *
	 * @return the number of msb fragment collections
	 */
	@Override
	public int getMSBFragmentCollectionsCount() {
		return msbFragmentCollectionPersistence.countAll();
	}

	/**
	 * Updates the msb fragment collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param msbFragmentCollection the msb fragment collection
	 * @return the msb fragment collection that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public MSBFragmentCollection updateMSBFragmentCollection(
		MSBFragmentCollection msbFragmentCollection) {
		return msbFragmentCollectionPersistence.update(msbFragmentCollection);
	}

	/**
	 * Returns the msb fragment collection local service.
	 *
	 * @return the msb fragment collection local service
	 */
	public MSBFragmentCollectionLocalService getMSBFragmentCollectionLocalService() {
		return msbFragmentCollectionLocalService;
	}

	/**
	 * Sets the msb fragment collection local service.
	 *
	 * @param msbFragmentCollectionLocalService the msb fragment collection local service
	 */
	public void setMSBFragmentCollectionLocalService(
		MSBFragmentCollectionLocalService msbFragmentCollectionLocalService) {
		this.msbFragmentCollectionLocalService = msbFragmentCollectionLocalService;
	}

	/**
	 * Returns the msb fragment collection persistence.
	 *
	 * @return the msb fragment collection persistence
	 */
	public MSBFragmentCollectionPersistence getMSBFragmentCollectionPersistence() {
		return msbFragmentCollectionPersistence;
	}

	/**
	 * Sets the msb fragment collection persistence.
	 *
	 * @param msbFragmentCollectionPersistence the msb fragment collection persistence
	 */
	public void setMSBFragmentCollectionPersistence(
		MSBFragmentCollectionPersistence msbFragmentCollectionPersistence) {
		this.msbFragmentCollectionPersistence = msbFragmentCollectionPersistence;
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

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the msb fragment entry local service.
	 *
	 * @return the msb fragment entry local service
	 */
	public com.liferay.modern.site.building.fragment.service.MSBFragmentEntryLocalService getMSBFragmentEntryLocalService() {
		return msbFragmentEntryLocalService;
	}

	/**
	 * Sets the msb fragment entry local service.
	 *
	 * @param msbFragmentEntryLocalService the msb fragment entry local service
	 */
	public void setMSBFragmentEntryLocalService(
		com.liferay.modern.site.building.fragment.service.MSBFragmentEntryLocalService msbFragmentEntryLocalService) {
		this.msbFragmentEntryLocalService = msbFragmentEntryLocalService;
	}

	/**
	 * Returns the msb fragment entry persistence.
	 *
	 * @return the msb fragment entry persistence
	 */
	public MSBFragmentEntryPersistence getMSBFragmentEntryPersistence() {
		return msbFragmentEntryPersistence;
	}

	/**
	 * Sets the msb fragment entry persistence.
	 *
	 * @param msbFragmentEntryPersistence the msb fragment entry persistence
	 */
	public void setMSBFragmentEntryPersistence(
		MSBFragmentEntryPersistence msbFragmentEntryPersistence) {
		this.msbFragmentEntryPersistence = msbFragmentEntryPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.modern.site.building.fragment.model.MSBFragmentCollection",
			msbFragmentCollectionLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.modern.site.building.fragment.model.MSBFragmentCollection");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return MSBFragmentCollectionLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return MSBFragmentCollection.class;
	}

	protected String getModelClassName() {
		return MSBFragmentCollection.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = msbFragmentCollectionPersistence.getDataSource();

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

	@BeanReference(type = MSBFragmentCollectionLocalService.class)
	protected MSBFragmentCollectionLocalService msbFragmentCollectionLocalService;
	@BeanReference(type = MSBFragmentCollectionPersistence.class)
	protected MSBFragmentCollectionPersistence msbFragmentCollectionPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = com.liferay.modern.site.building.fragment.service.MSBFragmentEntryLocalService.class)
	protected com.liferay.modern.site.building.fragment.service.MSBFragmentEntryLocalService msbFragmentEntryLocalService;
	@BeanReference(type = MSBFragmentEntryPersistence.class)
	protected MSBFragmentEntryPersistence msbFragmentEntryPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}