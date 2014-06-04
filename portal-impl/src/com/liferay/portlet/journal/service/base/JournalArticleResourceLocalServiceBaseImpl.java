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

package com.liferay.portlet.journal.service.base;

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

import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalService;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the journal article resource local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.journal.service.impl.JournalArticleResourceLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.journal.service.impl.JournalArticleResourceLocalServiceImpl
 * @see com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil
 * @generated
 */
public abstract class JournalArticleResourceLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements JournalArticleResourceLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil} to access the journal article resource local service.
	 */

	/**
	 * Adds the journal article resource to the database. Also notifies the appropriate model listeners.
	 *
	 * @param journalArticleResource the journal article resource
	 * @return the journal article resource that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticleResource addJournalArticleResource(
		JournalArticleResource journalArticleResource) {
		journalArticleResource.setNew(true);

		return journalArticleResourcePersistence.update(journalArticleResource);
	}

	/**
	 * Creates a new journal article resource with the primary key. Does not add the journal article resource to the database.
	 *
	 * @param resourcePrimKey the primary key for the new journal article resource
	 * @return the new journal article resource
	 */
	@Override
	public JournalArticleResource createJournalArticleResource(
		long resourcePrimKey) {
		return journalArticleResourcePersistence.create(resourcePrimKey);
	}

	/**
	 * Deletes the journal article resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourcePrimKey the primary key of the journal article resource
	 * @return the journal article resource that was removed
	 * @throws PortalException if a journal article resource with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public JournalArticleResource deleteJournalArticleResource(
		long resourcePrimKey) throws PortalException {
		return journalArticleResourcePersistence.remove(resourcePrimKey);
	}

	/**
	 * Deletes the journal article resource from the database. Also notifies the appropriate model listeners.
	 *
	 * @param journalArticleResource the journal article resource
	 * @return the journal article resource that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public JournalArticleResource deleteJournalArticleResource(
		JournalArticleResource journalArticleResource) {
		return journalArticleResourcePersistence.remove(journalArticleResource);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(JournalArticleResource.class,
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
		return journalArticleResourcePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return journalArticleResourcePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return journalArticleResourcePersistence.findWithDynamicQuery(dynamicQuery,
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
		return journalArticleResourcePersistence.countWithDynamicQuery(dynamicQuery);
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
		return journalArticleResourcePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public JournalArticleResource fetchJournalArticleResource(
		long resourcePrimKey) {
		return journalArticleResourcePersistence.fetchByPrimaryKey(resourcePrimKey);
	}

	/**
	 * Returns the journal article resource matching the UUID and group.
	 *
	 * @param uuid the journal article resource's UUID
	 * @param groupId the primary key of the group
	 * @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource fetchJournalArticleResourceByUuidAndGroupId(
		String uuid, long groupId) {
		return journalArticleResourcePersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the journal article resource with the primary key.
	 *
	 * @param resourcePrimKey the primary key of the journal article resource
	 * @return the journal article resource
	 * @throws PortalException if a journal article resource with the primary key could not be found
	 */
	@Override
	public JournalArticleResource getJournalArticleResource(
		long resourcePrimKey) throws PortalException {
		return journalArticleResourcePersistence.findByPrimaryKey(resourcePrimKey);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(JournalArticleResource.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("resourcePrimKey");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(JournalArticleResource.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("resourcePrimKey");
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return journalArticleResourcePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the journal article resource matching the UUID and group.
	 *
	 * @param uuid the journal article resource's UUID
	 * @param groupId the primary key of the group
	 * @return the matching journal article resource
	 * @throws PortalException if a matching journal article resource could not be found
	 */
	@Override
	public JournalArticleResource getJournalArticleResourceByUuidAndGroupId(
		String uuid, long groupId) throws PortalException {
		return journalArticleResourcePersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the journal article resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @return the range of journal article resources
	 */
	@Override
	public List<JournalArticleResource> getJournalArticleResources(int start,
		int end) {
		return journalArticleResourcePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of journal article resources.
	 *
	 * @return the number of journal article resources
	 */
	@Override
	public int getJournalArticleResourcesCount() {
		return journalArticleResourcePersistence.countAll();
	}

	/**
	 * Updates the journal article resource in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param journalArticleResource the journal article resource
	 * @return the journal article resource that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticleResource updateJournalArticleResource(
		JournalArticleResource journalArticleResource) {
		return journalArticleResourcePersistence.update(journalArticleResource);
	}

	/**
	 * Returns the journal article resource local service.
	 *
	 * @return the journal article resource local service
	 */
	public com.liferay.portlet.journal.service.JournalArticleResourceLocalService getJournalArticleResourceLocalService() {
		return journalArticleResourceLocalService;
	}

	/**
	 * Sets the journal article resource local service.
	 *
	 * @param journalArticleResourceLocalService the journal article resource local service
	 */
	public void setJournalArticleResourceLocalService(
		com.liferay.portlet.journal.service.JournalArticleResourceLocalService journalArticleResourceLocalService) {
		this.journalArticleResourceLocalService = journalArticleResourceLocalService;
	}

	/**
	 * Returns the journal article resource persistence.
	 *
	 * @return the journal article resource persistence
	 */
	public JournalArticleResourcePersistence getJournalArticleResourcePersistence() {
		return journalArticleResourcePersistence;
	}

	/**
	 * Sets the journal article resource persistence.
	 *
	 * @param journalArticleResourcePersistence the journal article resource persistence
	 */
	public void setJournalArticleResourcePersistence(
		JournalArticleResourcePersistence journalArticleResourcePersistence) {
		this.journalArticleResourcePersistence = journalArticleResourcePersistence;
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
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.journal.model.JournalArticleResource",
			journalArticleResourceLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.journal.model.JournalArticleResource");
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
		return JournalArticleResource.class;
	}

	protected String getModelClassName() {
		return JournalArticleResource.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = journalArticleResourcePersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portlet.journal.service.JournalArticleResourceLocalService.class)
	protected com.liferay.portlet.journal.service.JournalArticleResourceLocalService journalArticleResourceLocalService;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}