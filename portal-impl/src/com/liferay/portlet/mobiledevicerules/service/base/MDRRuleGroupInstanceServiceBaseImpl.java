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

package com.liferay.portlet.mobiledevicerules.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.LayoutSetPersistence;
import com.liferay.portal.service.persistence.SystemEventPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceService;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRActionPersistence;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupFinder;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupInstancePersistence;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupPersistence;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the m d r rule group instance remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupInstanceServiceImpl}.
 * </p>
 *
 * @author Edward C. Han
 * @see com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupInstanceServiceImpl
 * @see com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil
 * @generated
 */
public abstract class MDRRuleGroupInstanceServiceBaseImpl
	extends BaseServiceImpl implements MDRRuleGroupInstanceService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil} to access the m d r rule group instance remote service.
	 */

	/**
	 * Returns the m d r rule group instance local service.
	 *
	 * @return the m d r rule group instance local service
	 */
	public com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalService getMDRRuleGroupInstanceLocalService() {
		return mdrRuleGroupInstanceLocalService;
	}

	/**
	 * Sets the m d r rule group instance local service.
	 *
	 * @param mdrRuleGroupInstanceLocalService the m d r rule group instance local service
	 */
	public void setMDRRuleGroupInstanceLocalService(
		com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {
		this.mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	/**
	 * Returns the m d r rule group instance remote service.
	 *
	 * @return the m d r rule group instance remote service
	 */
	public com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceService getMDRRuleGroupInstanceService() {
		return mdrRuleGroupInstanceService;
	}

	/**
	 * Sets the m d r rule group instance remote service.
	 *
	 * @param mdrRuleGroupInstanceService the m d r rule group instance remote service
	 */
	public void setMDRRuleGroupInstanceService(
		com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {
		this.mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	/**
	 * Returns the m d r rule group instance persistence.
	 *
	 * @return the m d r rule group instance persistence
	 */
	public MDRRuleGroupInstancePersistence getMDRRuleGroupInstancePersistence() {
		return mdrRuleGroupInstancePersistence;
	}

	/**
	 * Sets the m d r rule group instance persistence.
	 *
	 * @param mdrRuleGroupInstancePersistence the m d r rule group instance persistence
	 */
	public void setMDRRuleGroupInstancePersistence(
		MDRRuleGroupInstancePersistence mdrRuleGroupInstancePersistence) {
		this.mdrRuleGroupInstancePersistence = mdrRuleGroupInstancePersistence;
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

	/**
	 * Returns the m d r action local service.
	 *
	 * @return the m d r action local service
	 */
	public com.liferay.portlet.mobiledevicerules.service.MDRActionLocalService getMDRActionLocalService() {
		return mdrActionLocalService;
	}

	/**
	 * Sets the m d r action local service.
	 *
	 * @param mdrActionLocalService the m d r action local service
	 */
	public void setMDRActionLocalService(
		com.liferay.portlet.mobiledevicerules.service.MDRActionLocalService mdrActionLocalService) {
		this.mdrActionLocalService = mdrActionLocalService;
	}

	/**
	 * Returns the m d r action remote service.
	 *
	 * @return the m d r action remote service
	 */
	public com.liferay.portlet.mobiledevicerules.service.MDRActionService getMDRActionService() {
		return mdrActionService;
	}

	/**
	 * Sets the m d r action remote service.
	 *
	 * @param mdrActionService the m d r action remote service
	 */
	public void setMDRActionService(
		com.liferay.portlet.mobiledevicerules.service.MDRActionService mdrActionService) {
		this.mdrActionService = mdrActionService;
	}

	/**
	 * Returns the m d r action persistence.
	 *
	 * @return the m d r action persistence
	 */
	public MDRActionPersistence getMDRActionPersistence() {
		return mdrActionPersistence;
	}

	/**
	 * Sets the m d r action persistence.
	 *
	 * @param mdrActionPersistence the m d r action persistence
	 */
	public void setMDRActionPersistence(
		MDRActionPersistence mdrActionPersistence) {
		this.mdrActionPersistence = mdrActionPersistence;
	}

	/**
	 * Returns the m d r rule group local service.
	 *
	 * @return the m d r rule group local service
	 */
	public com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalService getMDRRuleGroupLocalService() {
		return mdrRuleGroupLocalService;
	}

	/**
	 * Sets the m d r rule group local service.
	 *
	 * @param mdrRuleGroupLocalService the m d r rule group local service
	 */
	public void setMDRRuleGroupLocalService(
		com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalService mdrRuleGroupLocalService) {
		this.mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	/**
	 * Returns the m d r rule group remote service.
	 *
	 * @return the m d r rule group remote service
	 */
	public com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupService getMDRRuleGroupService() {
		return mdrRuleGroupService;
	}

	/**
	 * Sets the m d r rule group remote service.
	 *
	 * @param mdrRuleGroupService the m d r rule group remote service
	 */
	public void setMDRRuleGroupService(
		com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupService mdrRuleGroupService) {
		this.mdrRuleGroupService = mdrRuleGroupService;
	}

	/**
	 * Returns the m d r rule group persistence.
	 *
	 * @return the m d r rule group persistence
	 */
	public MDRRuleGroupPersistence getMDRRuleGroupPersistence() {
		return mdrRuleGroupPersistence;
	}

	/**
	 * Sets the m d r rule group persistence.
	 *
	 * @param mdrRuleGroupPersistence the m d r rule group persistence
	 */
	public void setMDRRuleGroupPersistence(
		MDRRuleGroupPersistence mdrRuleGroupPersistence) {
		this.mdrRuleGroupPersistence = mdrRuleGroupPersistence;
	}

	/**
	 * Returns the m d r rule group finder.
	 *
	 * @return the m d r rule group finder
	 */
	public MDRRuleGroupFinder getMDRRuleGroupFinder() {
		return mdrRuleGroupFinder;
	}

	/**
	 * Sets the m d r rule group finder.
	 *
	 * @param mdrRuleGroupFinder the m d r rule group finder
	 */
	public void setMDRRuleGroupFinder(MDRRuleGroupFinder mdrRuleGroupFinder) {
		this.mdrRuleGroupFinder = mdrRuleGroupFinder;
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

	protected Class<?> getModelClass() {
		return MDRRuleGroupInstance.class;
	}

	protected String getModelClassName() {
		return MDRRuleGroupInstance.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = mdrRuleGroupInstancePersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalService.class)
	protected com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService;
	@BeanReference(type = com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceService.class)
	protected com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceService mdrRuleGroupInstanceService;
	@BeanReference(type = MDRRuleGroupInstancePersistence.class)
	protected MDRRuleGroupInstancePersistence mdrRuleGroupInstancePersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameService.class)
	protected com.liferay.portal.service.ClassNameService classNameService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutLocalService.class)
	protected com.liferay.portal.service.LayoutLocalService layoutLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutService.class)
	protected com.liferay.portal.service.LayoutService layoutService;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutFinder.class)
	protected LayoutFinder layoutFinder;
	@BeanReference(type = com.liferay.portal.service.LayoutSetLocalService.class)
	protected com.liferay.portal.service.LayoutSetLocalService layoutSetLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutSetService.class)
	protected com.liferay.portal.service.LayoutSetService layoutSetService;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
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
	@BeanReference(type = com.liferay.portlet.mobiledevicerules.service.MDRActionLocalService.class)
	protected com.liferay.portlet.mobiledevicerules.service.MDRActionLocalService mdrActionLocalService;
	@BeanReference(type = com.liferay.portlet.mobiledevicerules.service.MDRActionService.class)
	protected com.liferay.portlet.mobiledevicerules.service.MDRActionService mdrActionService;
	@BeanReference(type = MDRActionPersistence.class)
	protected MDRActionPersistence mdrActionPersistence;
	@BeanReference(type = com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalService.class)
	protected com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalService mdrRuleGroupLocalService;
	@BeanReference(type = com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupService.class)
	protected com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupService mdrRuleGroupService;
	@BeanReference(type = MDRRuleGroupPersistence.class)
	protected MDRRuleGroupPersistence mdrRuleGroupPersistence;
	@BeanReference(type = MDRRuleGroupFinder.class)
	protected MDRRuleGroupFinder mdrRuleGroupFinder;
	private String _beanIdentifier;
}