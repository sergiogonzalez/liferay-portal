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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.StagingLocalService;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.LayoutRevisionPersistence;
import com.liferay.portal.service.persistence.LayoutSetBranchPersistence;
import com.liferay.portal.service.persistence.LayoutSetPersistence;
import com.liferay.portal.util.PortalUtil;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the staging local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.StagingLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.StagingLocalServiceImpl
 * @see com.liferay.portal.service.StagingLocalServiceUtil
 * @generated
 */
public abstract class StagingLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements StagingLocalService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.service.StagingLocalServiceUtil} to access the staging local service.
	 */

	/**
	 * Returns the staging local service.
	 *
	 * @return the staging local service
	 */
	public com.liferay.portal.service.StagingLocalService getStagingLocalService() {
		return stagingLocalService;
	}

	/**
	 * Sets the staging local service.
	 *
	 * @param stagingLocalService the staging local service
	 */
	public void setStagingLocalService(
		com.liferay.portal.service.StagingLocalService stagingLocalService) {
		this.stagingLocalService = stagingLocalService;
	}

	/**
	 * Returns the staging remote service.
	 *
	 * @return the staging remote service
	 */
	public com.liferay.portal.service.StagingService getStagingService() {
		return stagingService;
	}

	/**
	 * Sets the staging remote service.
	 *
	 * @param stagingService the staging remote service
	 */
	public void setStagingService(
		com.liferay.portal.service.StagingService stagingService) {
		this.stagingService = stagingService;
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
	 * Returns the layout remote service.
	 *
	 * @return the layout remote service
	 */
	public com.liferay.portal.service.LayoutService getLayoutService() {
		return layoutService;
	}

	/**
	 * Sets the layout remote service.
	 *
	 * @param layoutService the layout remote service
	 */
	public void setLayoutService(
		com.liferay.portal.service.LayoutService layoutService) {
		this.layoutService = layoutService;
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
	 * Returns the layout revision local service.
	 *
	 * @return the layout revision local service
	 */
	public com.liferay.portal.service.LayoutRevisionLocalService getLayoutRevisionLocalService() {
		return layoutRevisionLocalService;
	}

	/**
	 * Sets the layout revision local service.
	 *
	 * @param layoutRevisionLocalService the layout revision local service
	 */
	public void setLayoutRevisionLocalService(
		com.liferay.portal.service.LayoutRevisionLocalService layoutRevisionLocalService) {
		this.layoutRevisionLocalService = layoutRevisionLocalService;
	}

	/**
	 * Returns the layout revision remote service.
	 *
	 * @return the layout revision remote service
	 */
	public com.liferay.portal.service.LayoutRevisionService getLayoutRevisionService() {
		return layoutRevisionService;
	}

	/**
	 * Sets the layout revision remote service.
	 *
	 * @param layoutRevisionService the layout revision remote service
	 */
	public void setLayoutRevisionService(
		com.liferay.portal.service.LayoutRevisionService layoutRevisionService) {
		this.layoutRevisionService = layoutRevisionService;
	}

	/**
	 * Returns the layout revision persistence.
	 *
	 * @return the layout revision persistence
	 */
	public LayoutRevisionPersistence getLayoutRevisionPersistence() {
		return layoutRevisionPersistence;
	}

	/**
	 * Sets the layout revision persistence.
	 *
	 * @param layoutRevisionPersistence the layout revision persistence
	 */
	public void setLayoutRevisionPersistence(
		LayoutRevisionPersistence layoutRevisionPersistence) {
		this.layoutRevisionPersistence = layoutRevisionPersistence;
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
	 * Returns the layout set remote service.
	 *
	 * @return the layout set remote service
	 */
	public com.liferay.portal.service.LayoutSetService getLayoutSetService() {
		return layoutSetService;
	}

	/**
	 * Sets the layout set remote service.
	 *
	 * @param layoutSetService the layout set remote service
	 */
	public void setLayoutSetService(
		com.liferay.portal.service.LayoutSetService layoutSetService) {
		this.layoutSetService = layoutSetService;
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
	 * Returns the layout set branch local service.
	 *
	 * @return the layout set branch local service
	 */
	public com.liferay.portal.service.LayoutSetBranchLocalService getLayoutSetBranchLocalService() {
		return layoutSetBranchLocalService;
	}

	/**
	 * Sets the layout set branch local service.
	 *
	 * @param layoutSetBranchLocalService the layout set branch local service
	 */
	public void setLayoutSetBranchLocalService(
		com.liferay.portal.service.LayoutSetBranchLocalService layoutSetBranchLocalService) {
		this.layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	/**
	 * Returns the layout set branch remote service.
	 *
	 * @return the layout set branch remote service
	 */
	public com.liferay.portal.service.LayoutSetBranchService getLayoutSetBranchService() {
		return layoutSetBranchService;
	}

	/**
	 * Sets the layout set branch remote service.
	 *
	 * @param layoutSetBranchService the layout set branch remote service
	 */
	public void setLayoutSetBranchService(
		com.liferay.portal.service.LayoutSetBranchService layoutSetBranchService) {
		this.layoutSetBranchService = layoutSetBranchService;
	}

	/**
	 * Returns the layout set branch persistence.
	 *
	 * @return the layout set branch persistence
	 */
	public LayoutSetBranchPersistence getLayoutSetBranchPersistence() {
		return layoutSetBranchPersistence;
	}

	/**
	 * Sets the layout set branch persistence.
	 *
	 * @param layoutSetBranchPersistence the layout set branch persistence
	 */
	public void setLayoutSetBranchPersistence(
		LayoutSetBranchPersistence layoutSetBranchPersistence) {
		this.layoutSetBranchPersistence = layoutSetBranchPersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
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

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = InfrastructureUtil.getDataSource();

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

	@BeanReference(type = com.liferay.portal.service.StagingLocalService.class)
	protected com.liferay.portal.service.StagingLocalService stagingLocalService;
	@BeanReference(type = com.liferay.portal.service.StagingService.class)
	protected com.liferay.portal.service.StagingService stagingService;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.GroupLocalService.class)
	protected com.liferay.portal.service.GroupLocalService groupLocalService;
	@BeanReference(type = com.liferay.portal.service.GroupService.class)
	protected com.liferay.portal.service.GroupService groupService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = com.liferay.portal.service.LayoutLocalService.class)
	protected com.liferay.portal.service.LayoutLocalService layoutLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutService.class)
	protected com.liferay.portal.service.LayoutService layoutService;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutFinder.class)
	protected LayoutFinder layoutFinder;
	@BeanReference(type = com.liferay.portal.service.LayoutRevisionLocalService.class)
	protected com.liferay.portal.service.LayoutRevisionLocalService layoutRevisionLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutRevisionService.class)
	protected com.liferay.portal.service.LayoutRevisionService layoutRevisionService;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutSetLocalService.class)
	protected com.liferay.portal.service.LayoutSetLocalService layoutSetLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutSetService.class)
	protected com.liferay.portal.service.LayoutSetService layoutSetService;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutSetBranchLocalService.class)
	protected com.liferay.portal.service.LayoutSetBranchLocalService layoutSetBranchLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutSetBranchService.class)
	protected com.liferay.portal.service.LayoutSetBranchService layoutSetBranchService;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	private String _beanIdentifier;
}