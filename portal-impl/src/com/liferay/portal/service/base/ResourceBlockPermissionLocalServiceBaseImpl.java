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

package com.liferay.portal.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.ResourceBlockPermissionLocalService;
import com.liferay.portal.service.persistence.ResourceBlockFinder;
import com.liferay.portal.service.persistence.ResourceBlockPermissionPersistence;
import com.liferay.portal.service.persistence.ResourceBlockPersistence;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the resource block permission local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.ResourceBlockPermissionLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.ResourceBlockPermissionLocalServiceImpl
 * @see com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil
 * @generated
 */
public abstract class ResourceBlockPermissionLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements ResourceBlockPermissionLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil} to access the resource block permission local service.
	 */

	/**
	 * Adds the resource block permission to the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermission the resource block permission
	 * @return the resource block permission that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ResourceBlockPermission addResourceBlockPermission(
		ResourceBlockPermission resourceBlockPermission) {
		resourceBlockPermission.setNew(true);

		return resourceBlockPermissionPersistence.update(resourceBlockPermission);
	}

	/**
	 * Creates a new resource block permission with the primary key. Does not add the resource block permission to the database.
	 *
	 * @param resourceBlockPermissionId the primary key for the new resource block permission
	 * @return the new resource block permission
	 */
	@Override
	public ResourceBlockPermission createResourceBlockPermission(
		long resourceBlockPermissionId) {
		return resourceBlockPermissionPersistence.create(resourceBlockPermissionId);
	}

	/**
	 * Deletes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission that was removed
	 * @throws PortalException if a resource block permission with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ResourceBlockPermission deleteResourceBlockPermission(
		long resourceBlockPermissionId) throws PortalException {
		return resourceBlockPermissionPersistence.remove(resourceBlockPermissionId);
	}

	/**
	 * Deletes the resource block permission from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermission the resource block permission
	 * @return the resource block permission that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ResourceBlockPermission deleteResourceBlockPermission(
		ResourceBlockPermission resourceBlockPermission) {
		return resourceBlockPermissionPersistence.remove(resourceBlockPermission);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(ResourceBlockPermission.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery) {
		return resourceBlockPermissionPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end) {
		return resourceBlockPermissionPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return resourceBlockPermissionPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return resourceBlockPermissionPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows that match the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return resourceBlockPermissionPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public ResourceBlockPermission fetchResourceBlockPermission(
		long resourceBlockPermissionId) {
		return resourceBlockPermissionPersistence.fetchByPrimaryKey(resourceBlockPermissionId);
	}

	/**
	 * Returns the resource block permission with the primary key.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission
	 * @throws PortalException if a resource block permission with the primary key could not be found
	 */
	@Override
	public ResourceBlockPermission getResourceBlockPermission(
		long resourceBlockPermissionId) throws PortalException {
		return resourceBlockPermissionPersistence.findByPrimaryKey(resourceBlockPermissionId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(ResourceBlockPermission.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"resourceBlockPermissionId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(ResourceBlockPermission.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"resourceBlockPermissionId");
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return resourceBlockPermissionPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the resource block permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @return the range of resource block permissions
	 */
	@Override
	public List<ResourceBlockPermission> getResourceBlockPermissions(
		int start, int end) {
		return resourceBlockPermissionPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of resource block permissions.
	 *
	 * @return the number of resource block permissions
	 */
	@Override
	public int getResourceBlockPermissionsCount() {
		return resourceBlockPermissionPersistence.countAll();
	}

	/**
	 * Updates the resource block permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermission the resource block permission
	 * @return the resource block permission that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ResourceBlockPermission updateResourceBlockPermission(
		ResourceBlockPermission resourceBlockPermission) {
		return resourceBlockPermissionPersistence.update(resourceBlockPermission);
	}

	/**
	 * Returns the resource block permission local service.
	 *
	 * @return the resource block permission local service
	 */
	public com.liferay.portal.service.ResourceBlockPermissionLocalService getResourceBlockPermissionLocalService() {
		return resourceBlockPermissionLocalService;
	}

	/**
	 * Sets the resource block permission local service.
	 *
	 * @param resourceBlockPermissionLocalService the resource block permission local service
	 */
	public void setResourceBlockPermissionLocalService(
		com.liferay.portal.service.ResourceBlockPermissionLocalService resourceBlockPermissionLocalService) {
		this.resourceBlockPermissionLocalService = resourceBlockPermissionLocalService;
	}

	/**
	 * Returns the resource block permission persistence.
	 *
	 * @return the resource block permission persistence
	 */
	public ResourceBlockPermissionPersistence getResourceBlockPermissionPersistence() {
		return resourceBlockPermissionPersistence;
	}

	/**
	 * Sets the resource block permission persistence.
	 *
	 * @param resourceBlockPermissionPersistence the resource block permission persistence
	 */
	public void setResourceBlockPermissionPersistence(
		ResourceBlockPermissionPersistence resourceBlockPermissionPersistence) {
		this.resourceBlockPermissionPersistence = resourceBlockPermissionPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource block local service.
	 *
	 * @return the resource block local service
	 */
	public com.liferay.portal.service.ResourceBlockLocalService getResourceBlockLocalService() {
		return resourceBlockLocalService;
	}

	/**
	 * Sets the resource block local service.
	 *
	 * @param resourceBlockLocalService the resource block local service
	 */
	public void setResourceBlockLocalService(
		com.liferay.portal.service.ResourceBlockLocalService resourceBlockLocalService) {
		this.resourceBlockLocalService = resourceBlockLocalService;
	}

	/**
	 * Returns the resource block remote service.
	 *
	 * @return the resource block remote service
	 */
	public com.liferay.portal.service.ResourceBlockService getResourceBlockService() {
		return resourceBlockService;
	}

	/**
	 * Sets the resource block remote service.
	 *
	 * @param resourceBlockService the resource block remote service
	 */
	public void setResourceBlockService(
		com.liferay.portal.service.ResourceBlockService resourceBlockService) {
		this.resourceBlockService = resourceBlockService;
	}

	/**
	 * Returns the resource block persistence.
	 *
	 * @return the resource block persistence
	 */
	public ResourceBlockPersistence getResourceBlockPersistence() {
		return resourceBlockPersistence;
	}

	/**
	 * Sets the resource block persistence.
	 *
	 * @param resourceBlockPersistence the resource block persistence
	 */
	public void setResourceBlockPersistence(
		ResourceBlockPersistence resourceBlockPersistence) {
		this.resourceBlockPersistence = resourceBlockPersistence;
	}

	/**
	 * Returns the resource block finder.
	 *
	 * @return the resource block finder
	 */
	public ResourceBlockFinder getResourceBlockFinder() {
		return resourceBlockFinder;
	}

	/**
	 * Sets the resource block finder.
	 *
	 * @param resourceBlockFinder the resource block finder
	 */
	public void setResourceBlockFinder(ResourceBlockFinder resourceBlockFinder) {
		this.resourceBlockFinder = resourceBlockFinder;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portal.model.ResourceBlockPermission",
			resourceBlockPermissionLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.model.ResourceBlockPermission");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return ResourceBlockPermission.class;
	}

	protected String getModelClassName() {
		return ResourceBlockPermission.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = resourceBlockPermissionPersistence.getDataSource();

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.portal.service.ResourceBlockPermissionLocalService.class)
	protected com.liferay.portal.service.ResourceBlockPermissionLocalService resourceBlockPermissionLocalService;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceBlockLocalService.class)
	protected com.liferay.portal.service.ResourceBlockLocalService resourceBlockLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceBlockService.class)
	protected com.liferay.portal.service.ResourceBlockService resourceBlockService;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockFinder.class)
	protected ResourceBlockFinder resourceBlockFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}