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

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.message.boards.kernel.model.MBMailingList;
import com.liferay.message.boards.kernel.service.MBMailingListLocalService;
import com.liferay.message.boards.kernel.service.persistence.MBMailingListPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
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
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the message boards mailing list local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.messageboards.service.impl.MBMailingListLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.messageboards.service.impl.MBMailingListLocalServiceImpl
 * @see com.liferay.message.boards.kernel.service.MBMailingListLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class MBMailingListLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements MBMailingListLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.message.boards.kernel.service.MBMailingListLocalServiceUtil} to access the message boards mailing list local service.
	 */

	/**
	 * Adds the message boards mailing list to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMailingList the message boards mailing list
	 * @return the message boards mailing list that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public MBMailingList addMBMailingList(MBMailingList mbMailingList) {
		mbMailingList.setNew(true);

		return mbMailingListPersistence.update(mbMailingList);
	}

	/**
	 * Creates a new message boards mailing list with the primary key. Does not add the message boards mailing list to the database.
	 *
	 * @param mailingListId the primary key for the new message boards mailing list
	 * @return the new message boards mailing list
	 */
	@Override
	public MBMailingList createMBMailingList(long mailingListId) {
		return mbMailingListPersistence.create(mailingListId);
	}

	/**
	 * Deletes the message boards mailing list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mailingListId the primary key of the message boards mailing list
	 * @return the message boards mailing list that was removed
	 * @throws PortalException if a message boards mailing list with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public MBMailingList deleteMBMailingList(long mailingListId)
		throws PortalException {
		return mbMailingListPersistence.remove(mailingListId);
	}

	/**
	 * Deletes the message boards mailing list from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMailingList the message boards mailing list
	 * @return the message boards mailing list that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public MBMailingList deleteMBMailingList(MBMailingList mbMailingList) {
		return mbMailingListPersistence.remove(mbMailingList);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(MBMailingList.class,
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
		return mbMailingListPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return mbMailingListPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return mbMailingListPersistence.findWithDynamicQuery(dynamicQuery,
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
		return mbMailingListPersistence.countWithDynamicQuery(dynamicQuery);
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
		return mbMailingListPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public MBMailingList fetchMBMailingList(long mailingListId) {
		return mbMailingListPersistence.fetchByPrimaryKey(mailingListId);
	}

	/**
	 * Returns the message boards mailing list matching the UUID and group.
	 *
	 * @param uuid the message boards mailing list's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	 */
	@Override
	public MBMailingList fetchMBMailingListByUuidAndGroupId(String uuid,
		long groupId) {
		return mbMailingListPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the message boards mailing list with the primary key.
	 *
	 * @param mailingListId the primary key of the message boards mailing list
	 * @return the message boards mailing list
	 * @throws PortalException if a message boards mailing list with the primary key could not be found
	 */
	@Override
	public MBMailingList getMBMailingList(long mailingListId)
		throws PortalException {
		return mbMailingListPersistence.findByPrimaryKey(mailingListId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.message.boards.kernel.service.MBMailingListLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(MBMailingList.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("mailingListId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(com.liferay.message.boards.kernel.service.MBMailingListLocalServiceUtil.getService());
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(MBMailingList.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"mailingListId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.message.boards.kernel.service.MBMailingListLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(MBMailingList.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("mailingListId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
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

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<MBMailingList>() {
				@Override
				public void performAction(MBMailingList mbMailingList)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						mbMailingList);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(MBMailingList.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return mbMailingListLocalService.deleteMBMailingList((MBMailingList)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return mbMailingListPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the message boards mailing lists matching the UUID and company.
	 *
	 * @param uuid the UUID of the message boards mailing lists
	 * @param companyId the primary key of the company
	 * @return the matching message boards mailing lists, or an empty list if no matches were found
	 */
	@Override
	public List<MBMailingList> getMBMailingListsByUuidAndCompanyId(
		String uuid, long companyId) {
		return mbMailingListPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of message boards mailing lists matching the UUID and company.
	 *
	 * @param uuid the UUID of the message boards mailing lists
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching message boards mailing lists, or an empty list if no matches were found
	 */
	@Override
	public List<MBMailingList> getMBMailingListsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBMailingList> orderByComparator) {
		return mbMailingListPersistence.findByUuid_C(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	 * Returns the message boards mailing list matching the UUID and group.
	 *
	 * @param uuid the message boards mailing list's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards mailing list
	 * @throws PortalException if a matching message boards mailing list could not be found
	 */
	@Override
	public MBMailingList getMBMailingListByUuidAndGroupId(String uuid,
		long groupId) throws PortalException {
		return mbMailingListPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the message boards mailing lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMailingListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards mailing lists
	 * @param end the upper bound of the range of message boards mailing lists (not inclusive)
	 * @return the range of message boards mailing lists
	 */
	@Override
	public List<MBMailingList> getMBMailingLists(int start, int end) {
		return mbMailingListPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of message boards mailing lists.
	 *
	 * @return the number of message boards mailing lists
	 */
	@Override
	public int getMBMailingListsCount() {
		return mbMailingListPersistence.countAll();
	}

	/**
	 * Updates the message boards mailing list in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbMailingList the message boards mailing list
	 * @return the message boards mailing list that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public MBMailingList updateMBMailingList(MBMailingList mbMailingList) {
		return mbMailingListPersistence.update(mbMailingList);
	}

	/**
	 * Returns the message boards mailing list local service.
	 *
	 * @return the message boards mailing list local service
	 */
	public MBMailingListLocalService getMBMailingListLocalService() {
		return mbMailingListLocalService;
	}

	/**
	 * Sets the message boards mailing list local service.
	 *
	 * @param mbMailingListLocalService the message boards mailing list local service
	 */
	public void setMBMailingListLocalService(
		MBMailingListLocalService mbMailingListLocalService) {
		this.mbMailingListLocalService = mbMailingListLocalService;
	}

	/**
	 * Returns the message boards mailing list persistence.
	 *
	 * @return the message boards mailing list persistence
	 */
	public MBMailingListPersistence getMBMailingListPersistence() {
		return mbMailingListPersistence;
	}

	/**
	 * Sets the message boards mailing list persistence.
	 *
	 * @param mbMailingListPersistence the message boards mailing list persistence
	 */
	public void setMBMailingListPersistence(
		MBMailingListPersistence mbMailingListPersistence) {
		this.mbMailingListPersistence = mbMailingListPersistence;
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
		persistedModelLocalServiceRegistry.register("com.liferay.message.boards.kernel.model.MBMailingList",
			mbMailingListLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.message.boards.kernel.model.MBMailingList");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return MBMailingListLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return MBMailingList.class;
	}

	protected String getModelClassName() {
		return MBMailingList.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = mbMailingListPersistence.getDataSource();

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

	@BeanReference(type = com.liferay.message.boards.kernel.service.MBMailingListLocalService.class)
	protected MBMailingListLocalService mbMailingListLocalService;
	@BeanReference(type = MBMailingListPersistence.class)
	protected MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}