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

package com.liferay.portlet.messageboards.service.base;

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
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalService;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFlagPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the message boards thread flag local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.messageboards.service.impl.MBThreadFlagLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.messageboards.service.impl.MBThreadFlagLocalServiceImpl
 * @see com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil
 * @generated
 */
public abstract class MBThreadFlagLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements MBThreadFlagLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil} to access the message boards thread flag local service.
	 */

	/**
	 * Adds the message boards thread flag to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbThreadFlag the message boards thread flag
	 * @return the message boards thread flag that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public MBThreadFlag addMBThreadFlag(MBThreadFlag mbThreadFlag)
		throws SystemException {
		mbThreadFlag.setNew(true);

		return mbThreadFlagPersistence.update(mbThreadFlag);
	}

	/**
	 * Creates a new message boards thread flag with the primary key. Does not add the message boards thread flag to the database.
	 *
	 * @param threadFlagId the primary key for the new message boards thread flag
	 * @return the new message boards thread flag
	 */
	@Override
	public MBThreadFlag createMBThreadFlag(long threadFlagId) {
		return mbThreadFlagPersistence.create(threadFlagId);
	}

	/**
	 * Deletes the message boards thread flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag that was removed
	 * @throws PortalException if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public MBThreadFlag deleteMBThreadFlag(long threadFlagId)
		throws PortalException, SystemException {
		return mbThreadFlagPersistence.remove(threadFlagId);
	}

	/**
	 * Deletes the message boards thread flag from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbThreadFlag the message boards thread flag
	 * @return the message boards thread flag that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public MBThreadFlag deleteMBThreadFlag(MBThreadFlag mbThreadFlag)
		throws SystemException {
		return mbThreadFlagPersistence.remove(mbThreadFlag);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
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
		return mbThreadFlagPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return mbThreadFlagPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return mbThreadFlagPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return mbThreadFlagPersistence.countWithDynamicQuery(dynamicQuery);
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
		return mbThreadFlagPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public MBThreadFlag fetchMBThreadFlag(long threadFlagId)
		throws SystemException {
		return mbThreadFlagPersistence.fetchByPrimaryKey(threadFlagId);
	}

	/**
	 * Returns the message boards thread flag with the matching UUID and company.
	 *
	 * @param uuid the message boards thread flag's UUID
	 * @param  companyId the primary key of the company
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBThreadFlag fetchMBThreadFlagByUuidAndCompanyId(String uuid,
		long companyId) throws SystemException {
		return mbThreadFlagPersistence.fetchByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns the message boards thread flag matching the UUID and group.
	 *
	 * @param uuid the message boards thread flag's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBThreadFlag fetchMBThreadFlagByUuidAndGroupId(String uuid,
		long groupId) throws SystemException {
		return mbThreadFlagPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the message boards thread flag with the primary key.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag
	 * @throws PortalException if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBThreadFlag getMBThreadFlag(long threadFlagId)
		throws PortalException, SystemException {
		return mbThreadFlagPersistence.findByPrimaryKey(threadFlagId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery()
		throws SystemException {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(MBThreadFlag.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("threadFlagId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery)
		throws SystemException {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(MBThreadFlag.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("threadFlagId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) throws SystemException {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount()
					throws PortalException, SystemException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType.toString(),
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType.toString(),
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				@SuppressWarnings("unused")
				public void performAction(Object object)
					throws PortalException, SystemException {
					MBThreadFlag stagedModel = (MBThreadFlag)object;

					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						stagedModel);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(MBThreadFlag.class.getName())));

		return exportActionableDynamicQuery;
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return mbThreadFlagPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the message boards thread flag with the matching UUID and company.
	 *
	 * @param uuid the message boards thread flag's UUID
	 * @param  companyId the primary key of the company
	 * @return the matching message boards thread flag
	 * @throws PortalException if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBThreadFlag getMBThreadFlagByUuidAndCompanyId(String uuid,
		long companyId) throws PortalException, SystemException {
		return mbThreadFlagPersistence.findByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns the message boards thread flag matching the UUID and group.
	 *
	 * @param uuid the message boards thread flag's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards thread flag
	 * @throws PortalException if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBThreadFlag getMBThreadFlagByUuidAndGroupId(String uuid,
		long groupId) throws PortalException, SystemException {
		return mbThreadFlagPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the message boards thread flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<MBThreadFlag> getMBThreadFlags(int start, int end)
		throws SystemException {
		return mbThreadFlagPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of message boards thread flags.
	 *
	 * @return the number of message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getMBThreadFlagsCount() throws SystemException {
		return mbThreadFlagPersistence.countAll();
	}

	/**
	 * Updates the message boards thread flag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbThreadFlag the message boards thread flag
	 * @return the message boards thread flag that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public MBThreadFlag updateMBThreadFlag(MBThreadFlag mbThreadFlag)
		throws SystemException {
		return mbThreadFlagPersistence.update(mbThreadFlag);
	}

	/**
	 * Returns the message boards thread flag local service.
	 *
	 * @return the message boards thread flag local service
	 */
	public com.liferay.portlet.messageboards.service.MBThreadFlagLocalService getMBThreadFlagLocalService() {
		return mbThreadFlagLocalService;
	}

	/**
	 * Sets the message boards thread flag local service.
	 *
	 * @param mbThreadFlagLocalService the message boards thread flag local service
	 */
	public void setMBThreadFlagLocalService(
		com.liferay.portlet.messageboards.service.MBThreadFlagLocalService mbThreadFlagLocalService) {
		this.mbThreadFlagLocalService = mbThreadFlagLocalService;
	}

	/**
	 * Returns the message boards thread flag persistence.
	 *
	 * @return the message boards thread flag persistence
	 */
	public MBThreadFlagPersistence getMBThreadFlagPersistence() {
		return mbThreadFlagPersistence;
	}

	/**
	 * Sets the message boards thread flag persistence.
	 *
	 * @param mbThreadFlagPersistence the message boards thread flag persistence
	 */
	public void setMBThreadFlagPersistence(
		MBThreadFlagPersistence mbThreadFlagPersistence) {
		this.mbThreadFlagPersistence = mbThreadFlagPersistence;
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
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.messageboards.model.MBThreadFlag",
			mbThreadFlagLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.messageboards.model.MBThreadFlag");
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
		return MBThreadFlag.class;
	}

	protected String getModelClassName() {
		return MBThreadFlag.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = mbThreadFlagPersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portlet.messageboards.service.MBThreadFlagLocalService.class)
	protected com.liferay.portlet.messageboards.service.MBThreadFlagLocalService mbThreadFlagLocalService;
	@BeanReference(type = MBThreadFlagPersistence.class)
	protected MBThreadFlagPersistence mbThreadFlagPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
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