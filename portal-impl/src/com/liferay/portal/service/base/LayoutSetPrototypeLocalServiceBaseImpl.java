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
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.LayoutSetPersistence;
import com.liferay.portal.service.persistence.LayoutSetPrototypePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the layout set prototype local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.LayoutSetPrototypeLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.LayoutSetPrototypeLocalServiceImpl
 * @see com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class LayoutSetPrototypeLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements LayoutSetPrototypeLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil} to access the layout set prototype local service.
	 */

	/**
	 * Adds the layout set prototype to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetPrototype the layout set prototype
	 * @return the layout set prototype that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutSetPrototype addLayoutSetPrototype(
		LayoutSetPrototype layoutSetPrototype) {
		layoutSetPrototype.setNew(true);

		return layoutSetPrototypePersistence.update(layoutSetPrototype);
	}

	/**
	 * Creates a new layout set prototype with the primary key. Does not add the layout set prototype to the database.
	 *
	 * @param layoutSetPrototypeId the primary key for the new layout set prototype
	 * @return the new layout set prototype
	 */
	@Override
	public LayoutSetPrototype createLayoutSetPrototype(
		long layoutSetPrototypeId) {
		return layoutSetPrototypePersistence.create(layoutSetPrototypeId);
	}

	/**
	 * Deletes the layout set prototype with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetPrototypeId the primary key of the layout set prototype
	 * @return the layout set prototype that was removed
	 * @throws PortalException if a layout set prototype with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutSetPrototype deleteLayoutSetPrototype(
		long layoutSetPrototypeId) throws PortalException {
		return layoutSetPrototypePersistence.remove(layoutSetPrototypeId);
	}

	/**
	 * Deletes the layout set prototype from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetPrototype the layout set prototype
	 * @return the layout set prototype that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutSetPrototype deleteLayoutSetPrototype(
		LayoutSetPrototype layoutSetPrototype) throws PortalException {
		return layoutSetPrototypePersistence.remove(layoutSetPrototype);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(LayoutSetPrototype.class,
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
		return layoutSetPrototypePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return layoutSetPrototypePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return layoutSetPrototypePersistence.findWithDynamicQuery(dynamicQuery,
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
		return layoutSetPrototypePersistence.countWithDynamicQuery(dynamicQuery);
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
		return layoutSetPrototypePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public LayoutSetPrototype fetchLayoutSetPrototype(long layoutSetPrototypeId) {
		return layoutSetPrototypePersistence.fetchByPrimaryKey(layoutSetPrototypeId);
	}

	/**
	 * Returns the layout set prototype with the matching UUID and company.
	 *
	 * @param uuid the layout set prototype's UUID
	 * @param companyId the primary key of the company
	 * @return the matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	 */
	@Override
	public LayoutSetPrototype fetchLayoutSetPrototypeByUuidAndCompanyId(
		String uuid, long companyId) {
		return layoutSetPrototypePersistence.fetchByUuid_C_First(uuid,
			companyId, null);
	}

	/**
	 * Returns the layout set prototype with the primary key.
	 *
	 * @param layoutSetPrototypeId the primary key of the layout set prototype
	 * @return the layout set prototype
	 * @throws PortalException if a layout set prototype with the primary key could not be found
	 */
	@Override
	public LayoutSetPrototype getLayoutSetPrototype(long layoutSetPrototypeId)
		throws PortalException {
		return layoutSetPrototypePersistence.findByPrimaryKey(layoutSetPrototypeId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(LayoutSetPrototype.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("layoutSetPrototypeId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil.getService());
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(LayoutSetPrototype.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"layoutSetPrototypeId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(LayoutSetPrototype.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("layoutSetPrototypeId");
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

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<LayoutSetPrototype>() {
				@Override
				public void performAction(LayoutSetPrototype layoutSetPrototype)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						layoutSetPrototype);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(LayoutSetPrototype.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return layoutSetPrototypeLocalService.deleteLayoutSetPrototype((LayoutSetPrototype)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return layoutSetPrototypePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the layout set prototype with the matching UUID and company.
	 *
	 * @param uuid the layout set prototype's UUID
	 * @param companyId the primary key of the company
	 * @return the matching layout set prototype
	 * @throws PortalException if a matching layout set prototype could not be found
	 */
	@Override
	public LayoutSetPrototype getLayoutSetPrototypeByUuidAndCompanyId(
		String uuid, long companyId) throws PortalException {
		return layoutSetPrototypePersistence.findByUuid_C_First(uuid,
			companyId, null);
	}

	/**
	 * Returns a range of all the layout set prototypes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of layout set prototypes
	 */
	@Override
	public List<LayoutSetPrototype> getLayoutSetPrototypes(int start, int end) {
		return layoutSetPrototypePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of layout set prototypes.
	 *
	 * @return the number of layout set prototypes
	 */
	@Override
	public int getLayoutSetPrototypesCount() {
		return layoutSetPrototypePersistence.countAll();
	}

	/**
	 * Updates the layout set prototype in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetPrototype the layout set prototype
	 * @return the layout set prototype that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutSetPrototype updateLayoutSetPrototype(
		LayoutSetPrototype layoutSetPrototype) {
		return layoutSetPrototypePersistence.update(layoutSetPrototype);
	}

	/**
	 * Returns the layout set prototype local service.
	 *
	 * @return the layout set prototype local service
	 */
	public LayoutSetPrototypeLocalService getLayoutSetPrototypeLocalService() {
		return layoutSetPrototypeLocalService;
	}

	/**
	 * Sets the layout set prototype local service.
	 *
	 * @param layoutSetPrototypeLocalService the layout set prototype local service
	 */
	public void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {
		this.layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	/**
	 * Returns the layout set prototype persistence.
	 *
	 * @return the layout set prototype persistence
	 */
	public LayoutSetPrototypePersistence getLayoutSetPrototypePersistence() {
		return layoutSetPrototypePersistence;
	}

	/**
	 * Sets the layout set prototype persistence.
	 *
	 * @param layoutSetPrototypePersistence the layout set prototype persistence
	 */
	public void setLayoutSetPrototypePersistence(
		LayoutSetPrototypePersistence layoutSetPrototypePersistence) {
		this.layoutSetPrototypePersistence = layoutSetPrototypePersistence;
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
	 * Returns the layout local service.
	 *
	 * @return the layout local service
	 */
	public com.liferay.portal.service.LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	/**
	 * Sets the layout local service.
	 *
	 * @param layoutLocalService the layout local service
	 */
	public void setLayoutLocalService(
		com.liferay.portal.service.LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	/**
	 * Returns the layout persistence.
	 *
	 * @return the layout persistence
	 */
	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	/**
	 * Sets the layout persistence.
	 *
	 * @param layoutPersistence the layout persistence
	 */
	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	/**
	 * Returns the layout finder.
	 *
	 * @return the layout finder
	 */
	public LayoutFinder getLayoutFinder() {
		return layoutFinder;
	}

	/**
	 * Sets the layout finder.
	 *
	 * @param layoutFinder the layout finder
	 */
	public void setLayoutFinder(LayoutFinder layoutFinder) {
		this.layoutFinder = layoutFinder;
	}

	/**
	 * Returns the layout set local service.
	 *
	 * @return the layout set local service
	 */
	public com.liferay.portal.service.LayoutSetLocalService getLayoutSetLocalService() {
		return layoutSetLocalService;
	}

	/**
	 * Sets the layout set local service.
	 *
	 * @param layoutSetLocalService the layout set local service
	 */
	public void setLayoutSetLocalService(
		com.liferay.portal.service.LayoutSetLocalService layoutSetLocalService) {
		this.layoutSetLocalService = layoutSetLocalService;
	}

	/**
	 * Returns the layout set persistence.
	 *
	 * @return the layout set persistence
	 */
	public LayoutSetPersistence getLayoutSetPersistence() {
		return layoutSetPersistence;
	}

	/**
	 * Sets the layout set persistence.
	 *
	 * @param layoutSetPersistence the layout set persistence
	 */
	public void setLayoutSetPersistence(
		LayoutSetPersistence layoutSetPersistence) {
		this.layoutSetPersistence = layoutSetPersistence;
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
		persistedModelLocalServiceRegistry.register("com.liferay.portal.model.LayoutSetPrototype",
			layoutSetPrototypeLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.model.LayoutSetPrototype");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return LayoutSetPrototypeLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return LayoutSetPrototype.class;
	}

	protected String getModelClassName() {
		return LayoutSetPrototype.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = layoutSetPrototypePersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portal.service.LayoutSetPrototypeLocalService.class)
	protected LayoutSetPrototypeLocalService layoutSetPrototypeLocalService;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.GroupLocalService.class)
	protected com.liferay.portal.service.GroupLocalService groupLocalService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = com.liferay.portal.service.LayoutLocalService.class)
	protected com.liferay.portal.service.LayoutLocalService layoutLocalService;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutFinder.class)
	protected LayoutFinder layoutFinder;
	@BeanReference(type = com.liferay.portal.service.LayoutSetLocalService.class)
	protected com.liferay.portal.service.LayoutSetLocalService layoutSetLocalService;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}