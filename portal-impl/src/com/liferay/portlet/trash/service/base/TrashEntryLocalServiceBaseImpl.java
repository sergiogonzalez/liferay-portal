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

package com.liferay.portlet.trash.service.base;

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
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.SystemEventPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalService;
import com.liferay.portlet.trash.service.persistence.TrashEntryPersistence;
import com.liferay.portlet.trash.service.persistence.TrashVersionPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the trash entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl
 * @see com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil
 * @generated
 */
public abstract class TrashEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements TrashEntryLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil} to access the trash entry local service.
	 */

	/**
	 * Adds the trash entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntry the trash entry
	 * @return the trash entry that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public TrashEntry addTrashEntry(TrashEntry trashEntry)
		throws SystemException {
		trashEntry.setNew(true);

		return trashEntryPersistence.update(trashEntry);
	}

	/**
	 * Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	 *
	 * @param entryId the primary key for the new trash entry
	 * @return the new trash entry
	 */
	@Override
	public TrashEntry createTrashEntry(long entryId) {
		return trashEntryPersistence.create(entryId);
	}

	/**
	 * Deletes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the trash entry
	 * @return the trash entry that was removed
	 * @throws PortalException if a trash entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public TrashEntry deleteTrashEntry(long entryId)
		throws PortalException, SystemException {
		return trashEntryPersistence.remove(entryId);
	}

	/**
	 * Deletes the trash entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntry the trash entry
	 * @return the trash entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public TrashEntry deleteTrashEntry(TrashEntry trashEntry)
		throws SystemException {
		return trashEntryPersistence.remove(trashEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(TrashEntry.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return trashEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return trashEntryPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return trashEntryPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return trashEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) throws SystemException {
		return trashEntryPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public TrashEntry fetchTrashEntry(long entryId) throws SystemException {
		return trashEntryPersistence.fetchByPrimaryKey(entryId);
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param entryId the primary key of the trash entry
	 * @return the trash entry
	 * @throws PortalException if a trash entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TrashEntry getTrashEntry(long entryId)
		throws PortalException, SystemException {
		return trashEntryPersistence.findByPrimaryKey(entryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery()
		throws SystemException {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(TrashEntry.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("entryId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery)
		throws SystemException {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(TrashEntry.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("entryId");
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return trashEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @return the range of trash entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<TrashEntry> getTrashEntries(int start, int end)
		throws SystemException {
		return trashEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of trash entries.
	 *
	 * @return the number of trash entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getTrashEntriesCount() throws SystemException {
		return trashEntryPersistence.countAll();
	}

	/**
	 * Updates the trash entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntry the trash entry
	 * @return the trash entry that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public TrashEntry updateTrashEntry(TrashEntry trashEntry)
		throws SystemException {
		return trashEntryPersistence.update(trashEntry);
	}

	/**
	 * Returns the trash entry local service.
	 *
	 * @return the trash entry local service
	 */
	public com.liferay.portlet.trash.service.TrashEntryLocalService getTrashEntryLocalService() {
		return trashEntryLocalService;
	}

	/**
	 * Sets the trash entry local service.
	 *
	 * @param trashEntryLocalService the trash entry local service
	 */
	public void setTrashEntryLocalService(
		com.liferay.portlet.trash.service.TrashEntryLocalService trashEntryLocalService) {
		this.trashEntryLocalService = trashEntryLocalService;
	}

	/**
	 * Returns the trash entry remote service.
	 *
	 * @return the trash entry remote service
	 */
	public com.liferay.portlet.trash.service.TrashEntryService getTrashEntryService() {
		return trashEntryService;
	}

	/**
	 * Sets the trash entry remote service.
	 *
	 * @param trashEntryService the trash entry remote service
	 */
	public void setTrashEntryService(
		com.liferay.portlet.trash.service.TrashEntryService trashEntryService) {
		this.trashEntryService = trashEntryService;
	}

	/**
	 * Returns the trash entry persistence.
	 *
	 * @return the trash entry persistence
	 */
	public TrashEntryPersistence getTrashEntryPersistence() {
		return trashEntryPersistence;
	}

	/**
	 * Sets the trash entry persistence.
	 *
	 * @param trashEntryPersistence the trash entry persistence
	 */
	public void setTrashEntryPersistence(
		TrashEntryPersistence trashEntryPersistence) {
		this.trashEntryPersistence = trashEntryPersistence;
	}

	/**
	 * Returns the trash version local service.
	 *
	 * @return the trash version local service
	 */
	public com.liferay.portlet.trash.service.TrashVersionLocalService getTrashVersionLocalService() {
		return trashVersionLocalService;
	}

	/**
	 * Sets the trash version local service.
	 *
	 * @param trashVersionLocalService the trash version local service
	 */
	public void setTrashVersionLocalService(
		com.liferay.portlet.trash.service.TrashVersionLocalService trashVersionLocalService) {
		this.trashVersionLocalService = trashVersionLocalService;
	}

	/**
	 * Returns the trash version persistence.
	 *
	 * @return the trash version persistence
	 */
	public TrashVersionPersistence getTrashVersionPersistence() {
		return trashVersionPersistence;
	}

	/**
	 * Sets the trash version persistence.
	 *
	 * @param trashVersionPersistence the trash version persistence
	 */
	public void setTrashVersionPersistence(
		TrashVersionPersistence trashVersionPersistence) {
		this.trashVersionPersistence = trashVersionPersistence;
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
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.service.GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.service.GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public com.liferay.portal.service.GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(
		com.liferay.portal.service.GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the group finder.
	 *
	 * @return the group finder
	 */
	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	/**
	 * Sets the group finder.
	 *
	 * @param groupFinder the group finder
	 */
	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the system event local service.
	 *
	 * @return the system event local service
	 */
	public com.liferay.portal.service.SystemEventLocalService getSystemEventLocalService() {
		return systemEventLocalService;
	}

	/**
	 * Sets the system event local service.
	 *
	 * @param systemEventLocalService the system event local service
	 */
	public void setSystemEventLocalService(
		com.liferay.portal.service.SystemEventLocalService systemEventLocalService) {
		this.systemEventLocalService = systemEventLocalService;
	}

	/**
	 * Returns the system event persistence.
	 *
	 * @return the system event persistence
	 */
	public SystemEventPersistence getSystemEventPersistence() {
		return systemEventPersistence;
	}

	/**
	 * Sets the system event persistence.
	 *
	 * @param systemEventPersistence the system event persistence
	 */
	public void setSystemEventPersistence(
		SystemEventPersistence systemEventPersistence) {
		this.systemEventPersistence = systemEventPersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
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
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.trash.model.TrashEntry",
			trashEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.trash.model.TrashEntry");
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
		return TrashEntry.class;
	}

	protected String getModelClassName() {
		return TrashEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = trashEntryPersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portlet.trash.service.TrashEntryLocalService.class)
	protected com.liferay.portlet.trash.service.TrashEntryLocalService trashEntryLocalService;
	@BeanReference(type = com.liferay.portlet.trash.service.TrashEntryService.class)
	protected com.liferay.portlet.trash.service.TrashEntryService trashEntryService;
	@BeanReference(type = TrashEntryPersistence.class)
	protected TrashEntryPersistence trashEntryPersistence;
	@BeanReference(type = com.liferay.portlet.trash.service.TrashVersionLocalService.class)
	protected com.liferay.portlet.trash.service.TrashVersionLocalService trashVersionLocalService;
	@BeanReference(type = TrashVersionPersistence.class)
	protected TrashVersionPersistence trashVersionPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameService.class)
	protected com.liferay.portal.service.ClassNameService classNameService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.service.GroupLocalService.class)
	protected com.liferay.portal.service.GroupLocalService groupLocalService;
	@BeanReference(type = com.liferay.portal.service.GroupService.class)
	protected com.liferay.portal.service.GroupService groupService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.SystemEventLocalService.class)
	protected com.liferay.portal.service.SystemEventLocalService systemEventLocalService;
	@BeanReference(type = SystemEventPersistence.class)
	protected SystemEventPersistence systemEventPersistence;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}