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

package com.liferay.dynamic.data.lists.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordFinder;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordPersistence;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetFinder;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetPersistence;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetVersionPersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

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
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.WorkflowDefinitionLinkPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the ddl record set local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordSetLocalServiceImpl
 * @see com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class DDLRecordSetLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements DDLRecordSetLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil} to access the ddl record set local service.
	 */

	/**
	 * Adds the ddl record set to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordSet the ddl record set
	 * @return the ddl record set that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecordSet addDDLRecordSet(DDLRecordSet ddlRecordSet) {
		ddlRecordSet.setNew(true);

		return ddlRecordSetPersistence.update(ddlRecordSet);
	}

	/**
	 * Creates a new ddl record set with the primary key. Does not add the ddl record set to the database.
	 *
	 * @param recordSetId the primary key for the new ddl record set
	 * @return the new ddl record set
	 */
	@Override
	public DDLRecordSet createDDLRecordSet(long recordSetId) {
		return ddlRecordSetPersistence.create(recordSetId);
	}

	/**
	 * Deletes the ddl record set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordSetId the primary key of the ddl record set
	 * @return the ddl record set that was removed
	 * @throws PortalException if a ddl record set with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDLRecordSet deleteDDLRecordSet(long recordSetId)
		throws PortalException {
		return ddlRecordSetPersistence.remove(recordSetId);
	}

	/**
	 * Deletes the ddl record set from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordSet the ddl record set
	 * @return the ddl record set that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDLRecordSet deleteDDLRecordSet(DDLRecordSet ddlRecordSet) {
		return ddlRecordSetPersistence.remove(ddlRecordSet);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
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
		return ddlRecordSetPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddlRecordSetPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddlRecordSetPersistence.findWithDynamicQuery(dynamicQuery,
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
		return ddlRecordSetPersistence.countWithDynamicQuery(dynamicQuery);
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
		return ddlRecordSetPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public DDLRecordSet fetchDDLRecordSet(long recordSetId) {
		return ddlRecordSetPersistence.fetchByPrimaryKey(recordSetId);
	}

	/**
	 * Returns the ddl record set matching the UUID and group.
	 *
	 * @param uuid the ddl record set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	@Override
	public DDLRecordSet fetchDDLRecordSetByUuidAndGroupId(String uuid,
		long groupId) {
		return ddlRecordSetPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddl record set with the primary key.
	 *
	 * @param recordSetId the primary key of the ddl record set
	 * @return the ddl record set
	 * @throws PortalException if a ddl record set with the primary key could not be found
	 */
	@Override
	public DDLRecordSet getDDLRecordSet(long recordSetId)
		throws PortalException {
		return ddlRecordSetPersistence.findByPrimaryKey(recordSetId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(ddlRecordSetLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDLRecordSet.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("recordSetId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(ddlRecordSetLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(DDLRecordSet.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("recordSetId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(ddlRecordSetLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDLRecordSet.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("recordSetId");
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

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<DDLRecordSet>() {
				@Override
				public void performAction(DDLRecordSet ddlRecordSet)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						ddlRecordSet);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(DDLRecordSet.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return ddlRecordSetLocalService.deleteDDLRecordSet((DDLRecordSet)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return ddlRecordSetPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the ddl record sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddl record sets
	 * @param companyId the primary key of the company
	 * @return the matching ddl record sets, or an empty list if no matches were found
	 */
	@Override
	public List<DDLRecordSet> getDDLRecordSetsByUuidAndCompanyId(String uuid,
		long companyId) {
		return ddlRecordSetPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of ddl record sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddl record sets
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddl record sets, or an empty list if no matches were found
	 */
	@Override
	public List<DDLRecordSet> getDDLRecordSetsByUuidAndCompanyId(String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator) {
		return ddlRecordSetPersistence.findByUuid_C(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	 * Returns the ddl record set matching the UUID and group.
	 *
	 * @param uuid the ddl record set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddl record set
	 * @throws PortalException if a matching ddl record set could not be found
	 */
	@Override
	public DDLRecordSet getDDLRecordSetByUuidAndGroupId(String uuid,
		long groupId) throws PortalException {
		return ddlRecordSetPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the ddl record sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of ddl record sets
	 */
	@Override
	public List<DDLRecordSet> getDDLRecordSets(int start, int end) {
		return ddlRecordSetPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of ddl record sets.
	 *
	 * @return the number of ddl record sets
	 */
	@Override
	public int getDDLRecordSetsCount() {
		return ddlRecordSetPersistence.countAll();
	}

	/**
	 * Updates the ddl record set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordSet the ddl record set
	 * @return the ddl record set that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecordSet updateDDLRecordSet(DDLRecordSet ddlRecordSet) {
		return ddlRecordSetPersistence.update(ddlRecordSet);
	}

	/**
	 * Returns the ddl record set local service.
	 *
	 * @return the ddl record set local service
	 */
	public DDLRecordSetLocalService getDDLRecordSetLocalService() {
		return ddlRecordSetLocalService;
	}

	/**
	 * Sets the ddl record set local service.
	 *
	 * @param ddlRecordSetLocalService the ddl record set local service
	 */
	public void setDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {
		this.ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	/**
	 * Returns the ddl record set persistence.
	 *
	 * @return the ddl record set persistence
	 */
	public DDLRecordSetPersistence getDDLRecordSetPersistence() {
		return ddlRecordSetPersistence;
	}

	/**
	 * Sets the ddl record set persistence.
	 *
	 * @param ddlRecordSetPersistence the ddl record set persistence
	 */
	public void setDDLRecordSetPersistence(
		DDLRecordSetPersistence ddlRecordSetPersistence) {
		this.ddlRecordSetPersistence = ddlRecordSetPersistence;
	}

	/**
	 * Returns the ddl record set finder.
	 *
	 * @return the ddl record set finder
	 */
	public DDLRecordSetFinder getDDLRecordSetFinder() {
		return ddlRecordSetFinder;
	}

	/**
	 * Sets the ddl record set finder.
	 *
	 * @param ddlRecordSetFinder the ddl record set finder
	 */
	public void setDDLRecordSetFinder(DDLRecordSetFinder ddlRecordSetFinder) {
		this.ddlRecordSetFinder = ddlRecordSetFinder;
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
	 * Returns the ddl record local service.
	 *
	 * @return the ddl record local service
	 */
	public com.liferay.dynamic.data.lists.service.DDLRecordLocalService getDDLRecordLocalService() {
		return ddlRecordLocalService;
	}

	/**
	 * Sets the ddl record local service.
	 *
	 * @param ddlRecordLocalService the ddl record local service
	 */
	public void setDDLRecordLocalService(
		com.liferay.dynamic.data.lists.service.DDLRecordLocalService ddlRecordLocalService) {
		this.ddlRecordLocalService = ddlRecordLocalService;
	}

	/**
	 * Returns the ddl record persistence.
	 *
	 * @return the ddl record persistence
	 */
	public DDLRecordPersistence getDDLRecordPersistence() {
		return ddlRecordPersistence;
	}

	/**
	 * Sets the ddl record persistence.
	 *
	 * @param ddlRecordPersistence the ddl record persistence
	 */
	public void setDDLRecordPersistence(
		DDLRecordPersistence ddlRecordPersistence) {
		this.ddlRecordPersistence = ddlRecordPersistence;
	}

	/**
	 * Returns the ddl record finder.
	 *
	 * @return the ddl record finder
	 */
	public DDLRecordFinder getDDLRecordFinder() {
		return ddlRecordFinder;
	}

	/**
	 * Sets the ddl record finder.
	 *
	 * @param ddlRecordFinder the ddl record finder
	 */
	public void setDDLRecordFinder(DDLRecordFinder ddlRecordFinder) {
		this.ddlRecordFinder = ddlRecordFinder;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
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
	 * Returns the workflow definition link local service.
	 *
	 * @return the workflow definition link local service
	 */
	public com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService getWorkflowDefinitionLinkLocalService() {
		return workflowDefinitionLinkLocalService;
	}

	/**
	 * Sets the workflow definition link local service.
	 *
	 * @param workflowDefinitionLinkLocalService the workflow definition link local service
	 */
	public void setWorkflowDefinitionLinkLocalService(
		com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {
		this.workflowDefinitionLinkLocalService = workflowDefinitionLinkLocalService;
	}

	/**
	 * Returns the workflow definition link persistence.
	 *
	 * @return the workflow definition link persistence
	 */
	public WorkflowDefinitionLinkPersistence getWorkflowDefinitionLinkPersistence() {
		return workflowDefinitionLinkPersistence;
	}

	/**
	 * Sets the workflow definition link persistence.
	 *
	 * @param workflowDefinitionLinkPersistence the workflow definition link persistence
	 */
	public void setWorkflowDefinitionLinkPersistence(
		WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence) {
		this.workflowDefinitionLinkPersistence = workflowDefinitionLinkPersistence;
	}

	/**
	 * Returns the ddl record set version local service.
	 *
	 * @return the ddl record set version local service
	 */
	public com.liferay.dynamic.data.lists.service.DDLRecordSetVersionLocalService getDDLRecordSetVersionLocalService() {
		return ddlRecordSetVersionLocalService;
	}

	/**
	 * Sets the ddl record set version local service.
	 *
	 * @param ddlRecordSetVersionLocalService the ddl record set version local service
	 */
	public void setDDLRecordSetVersionLocalService(
		com.liferay.dynamic.data.lists.service.DDLRecordSetVersionLocalService ddlRecordSetVersionLocalService) {
		this.ddlRecordSetVersionLocalService = ddlRecordSetVersionLocalService;
	}

	/**
	 * Returns the ddl record set version persistence.
	 *
	 * @return the ddl record set version persistence
	 */
	public DDLRecordSetVersionPersistence getDDLRecordSetVersionPersistence() {
		return ddlRecordSetVersionPersistence;
	}

	/**
	 * Sets the ddl record set version persistence.
	 *
	 * @param ddlRecordSetVersionPersistence the ddl record set version persistence
	 */
	public void setDDLRecordSetVersionPersistence(
		DDLRecordSetVersionPersistence ddlRecordSetVersionPersistence) {
		this.ddlRecordSetVersionPersistence = ddlRecordSetVersionPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.dynamic.data.lists.model.DDLRecordSet",
			ddlRecordSetLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.dynamic.data.lists.model.DDLRecordSet");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DDLRecordSetLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DDLRecordSet.class;
	}

	protected String getModelClassName() {
		return DDLRecordSet.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddlRecordSetPersistence.getDataSource();

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

	@BeanReference(type = DDLRecordSetLocalService.class)
	protected DDLRecordSetLocalService ddlRecordSetLocalService;
	@BeanReference(type = DDLRecordSetPersistence.class)
	protected DDLRecordSetPersistence ddlRecordSetPersistence;
	@BeanReference(type = DDLRecordSetFinder.class)
	protected DDLRecordSetFinder ddlRecordSetFinder;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.dynamic.data.lists.service.DDLRecordLocalService.class)
	protected com.liferay.dynamic.data.lists.service.DDLRecordLocalService ddlRecordLocalService;
	@BeanReference(type = DDLRecordPersistence.class)
	protected DDLRecordPersistence ddlRecordPersistence;
	@BeanReference(type = DDLRecordFinder.class)
	protected DDLRecordFinder ddlRecordFinder;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService.class)
	protected com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService;
	@ServiceReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = com.liferay.dynamic.data.lists.service.DDLRecordSetVersionLocalService.class)
	protected com.liferay.dynamic.data.lists.service.DDLRecordSetVersionLocalService ddlRecordSetVersionLocalService;
	@BeanReference(type = DDLRecordSetVersionPersistence.class)
	protected DDLRecordSetVersionPersistence ddlRecordSetVersionPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}