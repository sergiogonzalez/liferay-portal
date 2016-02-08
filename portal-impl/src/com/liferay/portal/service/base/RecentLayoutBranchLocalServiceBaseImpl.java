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

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.RecentLayoutBranch;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.RecentLayoutBranchLocalService;
import com.liferay.portal.service.persistence.LayoutBranchPersistence;
import com.liferay.portal.service.persistence.RecentLayoutBranchPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the recent layout branch local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.RecentLayoutBranchLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.RecentLayoutBranchLocalServiceImpl
 * @see com.liferay.portal.service.RecentLayoutBranchLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class RecentLayoutBranchLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements RecentLayoutBranchLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.service.RecentLayoutBranchLocalServiceUtil} to access the recent layout branch local service.
	 */

	/**
	 * Adds the recent layout branch to the database. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutBranch the recent layout branch
	 * @return the recent layout branch that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RecentLayoutBranch addRecentLayoutBranch(
		RecentLayoutBranch recentLayoutBranch) {
		recentLayoutBranch.setNew(true);

		return recentLayoutBranchPersistence.update(recentLayoutBranch);
	}

	/**
	 * Creates a new recent layout branch with the primary key. Does not add the recent layout branch to the database.
	 *
	 * @param recentLayoutBranchId the primary key for the new recent layout branch
	 * @return the new recent layout branch
	 */
	@Override
	public RecentLayoutBranch createRecentLayoutBranch(
		long recentLayoutBranchId) {
		return recentLayoutBranchPersistence.create(recentLayoutBranchId);
	}

	/**
	 * Deletes the recent layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutBranchId the primary key of the recent layout branch
	 * @return the recent layout branch that was removed
	 * @throws PortalException if a recent layout branch with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public RecentLayoutBranch deleteRecentLayoutBranch(
		long recentLayoutBranchId) throws PortalException {
		return recentLayoutBranchPersistence.remove(recentLayoutBranchId);
	}

	/**
	 * Deletes the recent layout branch from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutBranch the recent layout branch
	 * @return the recent layout branch that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public RecentLayoutBranch deleteRecentLayoutBranch(
		RecentLayoutBranch recentLayoutBranch) {
		return recentLayoutBranchPersistence.remove(recentLayoutBranch);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(RecentLayoutBranch.class,
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
		return recentLayoutBranchPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return recentLayoutBranchPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return recentLayoutBranchPersistence.findWithDynamicQuery(dynamicQuery,
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
		return recentLayoutBranchPersistence.countWithDynamicQuery(dynamicQuery);
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
		return recentLayoutBranchPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public RecentLayoutBranch fetchRecentLayoutBranch(long recentLayoutBranchId) {
		return recentLayoutBranchPersistence.fetchByPrimaryKey(recentLayoutBranchId);
	}

	/**
	 * Returns the recent layout branch with the primary key.
	 *
	 * @param recentLayoutBranchId the primary key of the recent layout branch
	 * @return the recent layout branch
	 * @throws PortalException if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch getRecentLayoutBranch(long recentLayoutBranchId)
		throws PortalException {
		return recentLayoutBranchPersistence.findByPrimaryKey(recentLayoutBranchId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.RecentLayoutBranchLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(RecentLayoutBranch.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("recentLayoutBranchId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.RecentLayoutBranchLocalServiceUtil.getService());
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(RecentLayoutBranch.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"recentLayoutBranchId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.RecentLayoutBranchLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(RecentLayoutBranch.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("recentLayoutBranchId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return recentLayoutBranchLocalService.deleteRecentLayoutBranch((RecentLayoutBranch)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return recentLayoutBranchPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the recent layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @return the range of recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> getRecentLayoutBranchs(int start, int end) {
		return recentLayoutBranchPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of recent layout branchs.
	 *
	 * @return the number of recent layout branchs
	 */
	@Override
	public int getRecentLayoutBranchsCount() {
		return recentLayoutBranchPersistence.countAll();
	}

	/**
	 * Updates the recent layout branch in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutBranch the recent layout branch
	 * @return the recent layout branch that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RecentLayoutBranch updateRecentLayoutBranch(
		RecentLayoutBranch recentLayoutBranch) {
		return recentLayoutBranchPersistence.update(recentLayoutBranch);
	}

	/**
	 * Returns the recent layout branch local service.
	 *
	 * @return the recent layout branch local service
	 */
	public RecentLayoutBranchLocalService getRecentLayoutBranchLocalService() {
		return recentLayoutBranchLocalService;
	}

	/**
	 * Sets the recent layout branch local service.
	 *
	 * @param recentLayoutBranchLocalService the recent layout branch local service
	 */
	public void setRecentLayoutBranchLocalService(
		RecentLayoutBranchLocalService recentLayoutBranchLocalService) {
		this.recentLayoutBranchLocalService = recentLayoutBranchLocalService;
	}

	/**
	 * Returns the recent layout branch persistence.
	 *
	 * @return the recent layout branch persistence
	 */
	public RecentLayoutBranchPersistence getRecentLayoutBranchPersistence() {
		return recentLayoutBranchPersistence;
	}

	/**
	 * Sets the recent layout branch persistence.
	 *
	 * @param recentLayoutBranchPersistence the recent layout branch persistence
	 */
	public void setRecentLayoutBranchPersistence(
		RecentLayoutBranchPersistence recentLayoutBranchPersistence) {
		this.recentLayoutBranchPersistence = recentLayoutBranchPersistence;
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
	 * Returns the layout branch local service.
	 *
	 * @return the layout branch local service
	 */
	public com.liferay.portal.service.LayoutBranchLocalService getLayoutBranchLocalService() {
		return layoutBranchLocalService;
	}

	/**
	 * Sets the layout branch local service.
	 *
	 * @param layoutBranchLocalService the layout branch local service
	 */
	public void setLayoutBranchLocalService(
		com.liferay.portal.service.LayoutBranchLocalService layoutBranchLocalService) {
		this.layoutBranchLocalService = layoutBranchLocalService;
	}

	/**
	 * Returns the layout branch persistence.
	 *
	 * @return the layout branch persistence
	 */
	public LayoutBranchPersistence getLayoutBranchPersistence() {
		return layoutBranchPersistence;
	}

	/**
	 * Sets the layout branch persistence.
	 *
	 * @param layoutBranchPersistence the layout branch persistence
	 */
	public void setLayoutBranchPersistence(
		LayoutBranchPersistence layoutBranchPersistence) {
		this.layoutBranchPersistence = layoutBranchPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portal.model.RecentLayoutBranch",
			recentLayoutBranchLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.model.RecentLayoutBranch");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return RecentLayoutBranchLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return RecentLayoutBranch.class;
	}

	protected String getModelClassName() {
		return RecentLayoutBranch.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = recentLayoutBranchPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

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

	@BeanReference(type = com.liferay.portal.service.RecentLayoutBranchLocalService.class)
	protected RecentLayoutBranchLocalService recentLayoutBranchLocalService;
	@BeanReference(type = RecentLayoutBranchPersistence.class)
	protected RecentLayoutBranchPersistence recentLayoutBranchPersistence;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutBranchLocalService.class)
	protected com.liferay.portal.service.LayoutBranchLocalService layoutBranchLocalService;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}