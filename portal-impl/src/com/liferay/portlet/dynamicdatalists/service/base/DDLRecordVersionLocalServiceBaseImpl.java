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

package com.liferay.portlet.dynamicdatalists.service.base;

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalService;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordVersionPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the d d l record version local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordVersionLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordVersionLocalServiceImpl
 * @see com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class DDLRecordVersionLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements DDLRecordVersionLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalServiceUtil} to access the d d l record version local service.
	 */

	/**
	 * Adds the d d l record version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordVersion the d d l record version
	 * @return the d d l record version that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecordVersion addDDLRecordVersion(
		DDLRecordVersion ddlRecordVersion) {
		ddlRecordVersion.setNew(true);

		return ddlRecordVersionPersistence.update(ddlRecordVersion);
	}

	/**
	 * Creates a new d d l record version with the primary key. Does not add the d d l record version to the database.
	 *
	 * @param recordVersionId the primary key for the new d d l record version
	 * @return the new d d l record version
	 */
	@Override
	public DDLRecordVersion createDDLRecordVersion(long recordVersionId) {
		return ddlRecordVersionPersistence.create(recordVersionId);
	}

	/**
	 * Deletes the d d l record version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordVersionId the primary key of the d d l record version
	 * @return the d d l record version that was removed
	 * @throws PortalException if a d d l record version with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDLRecordVersion deleteDDLRecordVersion(long recordVersionId)
		throws PortalException {
		return ddlRecordVersionPersistence.remove(recordVersionId);
	}

	/**
	 * Deletes the d d l record version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordVersion the d d l record version
	 * @return the d d l record version that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDLRecordVersion deleteDDLRecordVersion(
		DDLRecordVersion ddlRecordVersion) {
		return ddlRecordVersionPersistence.remove(ddlRecordVersion);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
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
		return ddlRecordVersionPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddlRecordVersionPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddlRecordVersionPersistence.findWithDynamicQuery(dynamicQuery,
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
		return ddlRecordVersionPersistence.countWithDynamicQuery(dynamicQuery);
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
		return ddlRecordVersionPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public DDLRecordVersion fetchDDLRecordVersion(long recordVersionId) {
		return ddlRecordVersionPersistence.fetchByPrimaryKey(recordVersionId);
	}

	/**
	 * Returns the d d l record version with the primary key.
	 *
	 * @param recordVersionId the primary key of the d d l record version
	 * @return the d d l record version
	 * @throws PortalException if a d d l record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion getDDLRecordVersion(long recordVersionId)
		throws PortalException {
		return ddlRecordVersionPersistence.findByPrimaryKey(recordVersionId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(DDLRecordVersion.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("recordVersionId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(DDLRecordVersion.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("recordVersionId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return ddlRecordVersionLocalService.deleteDDLRecordVersion((DDLRecordVersion)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return ddlRecordVersionPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the d d l record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d l record versions
	 * @param end the upper bound of the range of d d l record versions (not inclusive)
	 * @return the range of d d l record versions
	 */
	@Override
	public List<DDLRecordVersion> getDDLRecordVersions(int start, int end) {
		return ddlRecordVersionPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of d d l record versions.
	 *
	 * @return the number of d d l record versions
	 */
	@Override
	public int getDDLRecordVersionsCount() {
		return ddlRecordVersionPersistence.countAll();
	}

	/**
	 * Updates the d d l record version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordVersion the d d l record version
	 * @return the d d l record version that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecordVersion updateDDLRecordVersion(
		DDLRecordVersion ddlRecordVersion) {
		return ddlRecordVersionPersistence.update(ddlRecordVersion);
	}

	/**
	 * Returns the d d l record version local service.
	 *
	 * @return the d d l record version local service
	 */
	public com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalService getDDLRecordVersionLocalService() {
		return ddlRecordVersionLocalService;
	}

	/**
	 * Sets the d d l record version local service.
	 *
	 * @param ddlRecordVersionLocalService the d d l record version local service
	 */
	public void setDDLRecordVersionLocalService(
		com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalService ddlRecordVersionLocalService) {
		this.ddlRecordVersionLocalService = ddlRecordVersionLocalService;
	}

	/**
	 * Returns the d d l record version remote service.
	 *
	 * @return the d d l record version remote service
	 */
	public com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionService getDDLRecordVersionService() {
		return ddlRecordVersionService;
	}

	/**
	 * Sets the d d l record version remote service.
	 *
	 * @param ddlRecordVersionService the d d l record version remote service
	 */
	public void setDDLRecordVersionService(
		com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionService ddlRecordVersionService) {
		this.ddlRecordVersionService = ddlRecordVersionService;
	}

	/**
	 * Returns the d d l record version persistence.
	 *
	 * @return the d d l record version persistence
	 */
	public DDLRecordVersionPersistence getDDLRecordVersionPersistence() {
		return ddlRecordVersionPersistence;
	}

	/**
	 * Sets the d d l record version persistence.
	 *
	 * @param ddlRecordVersionPersistence the d d l record version persistence
	 */
	public void setDDLRecordVersionPersistence(
		DDLRecordVersionPersistence ddlRecordVersionPersistence) {
		this.ddlRecordVersionPersistence = ddlRecordVersionPersistence;
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

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion",
			ddlRecordVersionLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion");
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
		return DDLRecordVersion.class;
	}

	protected String getModelClassName() {
		return DDLRecordVersion.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddlRecordVersionPersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalService.class)
	protected com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalService ddlRecordVersionLocalService;
	@BeanReference(type = com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionService.class)
	protected com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionService ddlRecordVersionService;
	@BeanReference(type = DDLRecordVersionPersistence.class)
	protected DDLRecordVersionPersistence ddlRecordVersionPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}