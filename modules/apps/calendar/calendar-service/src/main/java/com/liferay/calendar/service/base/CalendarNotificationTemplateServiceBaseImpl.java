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

package com.liferay.calendar.service.base;

import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.service.CalendarNotificationTemplateService;
import com.liferay.calendar.service.persistence.CalendarBookingFinder;
import com.liferay.calendar.service.persistence.CalendarBookingPersistence;
import com.liferay.calendar.service.persistence.CalendarFinder;
import com.liferay.calendar.service.persistence.CalendarNotificationTemplatePersistence;
import com.liferay.calendar.service.persistence.CalendarPersistence;
import com.liferay.calendar.service.persistence.CalendarResourceFinder;
import com.liferay.calendar.service.persistence.CalendarResourcePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.spring.extender.service.ServiceReference;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the calendar notification template remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.calendar.service.impl.CalendarNotificationTemplateServiceImpl}.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see com.liferay.calendar.service.impl.CalendarNotificationTemplateServiceImpl
 * @see com.liferay.calendar.service.CalendarNotificationTemplateServiceUtil
 * @generated
 */
public abstract class CalendarNotificationTemplateServiceBaseImpl
	extends BaseServiceImpl implements CalendarNotificationTemplateService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.calendar.service.CalendarNotificationTemplateServiceUtil} to access the calendar notification template remote service.
	 */

	/**
	 * Returns the calendar local service.
	 *
	 * @return the calendar local service
	 */
	public com.liferay.calendar.service.CalendarLocalService getCalendarLocalService() {
		return calendarLocalService;
	}

	/**
	 * Sets the calendar local service.
	 *
	 * @param calendarLocalService the calendar local service
	 */
	public void setCalendarLocalService(
		com.liferay.calendar.service.CalendarLocalService calendarLocalService) {
		this.calendarLocalService = calendarLocalService;
	}

	/**
	 * Returns the calendar remote service.
	 *
	 * @return the calendar remote service
	 */
	public com.liferay.calendar.service.CalendarService getCalendarService() {
		return calendarService;
	}

	/**
	 * Sets the calendar remote service.
	 *
	 * @param calendarService the calendar remote service
	 */
	public void setCalendarService(
		com.liferay.calendar.service.CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	/**
	 * Returns the calendar persistence.
	 *
	 * @return the calendar persistence
	 */
	public CalendarPersistence getCalendarPersistence() {
		return calendarPersistence;
	}

	/**
	 * Sets the calendar persistence.
	 *
	 * @param calendarPersistence the calendar persistence
	 */
	public void setCalendarPersistence(CalendarPersistence calendarPersistence) {
		this.calendarPersistence = calendarPersistence;
	}

	/**
	 * Returns the calendar finder.
	 *
	 * @return the calendar finder
	 */
	public CalendarFinder getCalendarFinder() {
		return calendarFinder;
	}

	/**
	 * Sets the calendar finder.
	 *
	 * @param calendarFinder the calendar finder
	 */
	public void setCalendarFinder(CalendarFinder calendarFinder) {
		this.calendarFinder = calendarFinder;
	}

	/**
	 * Returns the calendar booking local service.
	 *
	 * @return the calendar booking local service
	 */
	public com.liferay.calendar.service.CalendarBookingLocalService getCalendarBookingLocalService() {
		return calendarBookingLocalService;
	}

	/**
	 * Sets the calendar booking local service.
	 *
	 * @param calendarBookingLocalService the calendar booking local service
	 */
	public void setCalendarBookingLocalService(
		com.liferay.calendar.service.CalendarBookingLocalService calendarBookingLocalService) {
		this.calendarBookingLocalService = calendarBookingLocalService;
	}

	/**
	 * Returns the calendar booking remote service.
	 *
	 * @return the calendar booking remote service
	 */
	public com.liferay.calendar.service.CalendarBookingService getCalendarBookingService() {
		return calendarBookingService;
	}

	/**
	 * Sets the calendar booking remote service.
	 *
	 * @param calendarBookingService the calendar booking remote service
	 */
	public void setCalendarBookingService(
		com.liferay.calendar.service.CalendarBookingService calendarBookingService) {
		this.calendarBookingService = calendarBookingService;
	}

	/**
	 * Returns the calendar booking persistence.
	 *
	 * @return the calendar booking persistence
	 */
	public CalendarBookingPersistence getCalendarBookingPersistence() {
		return calendarBookingPersistence;
	}

	/**
	 * Sets the calendar booking persistence.
	 *
	 * @param calendarBookingPersistence the calendar booking persistence
	 */
	public void setCalendarBookingPersistence(
		CalendarBookingPersistence calendarBookingPersistence) {
		this.calendarBookingPersistence = calendarBookingPersistence;
	}

	/**
	 * Returns the calendar booking finder.
	 *
	 * @return the calendar booking finder
	 */
	public CalendarBookingFinder getCalendarBookingFinder() {
		return calendarBookingFinder;
	}

	/**
	 * Sets the calendar booking finder.
	 *
	 * @param calendarBookingFinder the calendar booking finder
	 */
	public void setCalendarBookingFinder(
		CalendarBookingFinder calendarBookingFinder) {
		this.calendarBookingFinder = calendarBookingFinder;
	}

	/**
	 * Returns the calendar notification template local service.
	 *
	 * @return the calendar notification template local service
	 */
	public com.liferay.calendar.service.CalendarNotificationTemplateLocalService getCalendarNotificationTemplateLocalService() {
		return calendarNotificationTemplateLocalService;
	}

	/**
	 * Sets the calendar notification template local service.
	 *
	 * @param calendarNotificationTemplateLocalService the calendar notification template local service
	 */
	public void setCalendarNotificationTemplateLocalService(
		com.liferay.calendar.service.CalendarNotificationTemplateLocalService calendarNotificationTemplateLocalService) {
		this.calendarNotificationTemplateLocalService = calendarNotificationTemplateLocalService;
	}

	/**
	 * Returns the calendar notification template remote service.
	 *
	 * @return the calendar notification template remote service
	 */
	public CalendarNotificationTemplateService getCalendarNotificationTemplateService() {
		return calendarNotificationTemplateService;
	}

	/**
	 * Sets the calendar notification template remote service.
	 *
	 * @param calendarNotificationTemplateService the calendar notification template remote service
	 */
	public void setCalendarNotificationTemplateService(
		CalendarNotificationTemplateService calendarNotificationTemplateService) {
		this.calendarNotificationTemplateService = calendarNotificationTemplateService;
	}

	/**
	 * Returns the calendar notification template persistence.
	 *
	 * @return the calendar notification template persistence
	 */
	public CalendarNotificationTemplatePersistence getCalendarNotificationTemplatePersistence() {
		return calendarNotificationTemplatePersistence;
	}

	/**
	 * Sets the calendar notification template persistence.
	 *
	 * @param calendarNotificationTemplatePersistence the calendar notification template persistence
	 */
	public void setCalendarNotificationTemplatePersistence(
		CalendarNotificationTemplatePersistence calendarNotificationTemplatePersistence) {
		this.calendarNotificationTemplatePersistence = calendarNotificationTemplatePersistence;
	}

	/**
	 * Returns the calendar resource local service.
	 *
	 * @return the calendar resource local service
	 */
	public com.liferay.calendar.service.CalendarResourceLocalService getCalendarResourceLocalService() {
		return calendarResourceLocalService;
	}

	/**
	 * Sets the calendar resource local service.
	 *
	 * @param calendarResourceLocalService the calendar resource local service
	 */
	public void setCalendarResourceLocalService(
		com.liferay.calendar.service.CalendarResourceLocalService calendarResourceLocalService) {
		this.calendarResourceLocalService = calendarResourceLocalService;
	}

	/**
	 * Returns the calendar resource remote service.
	 *
	 * @return the calendar resource remote service
	 */
	public com.liferay.calendar.service.CalendarResourceService getCalendarResourceService() {
		return calendarResourceService;
	}

	/**
	 * Sets the calendar resource remote service.
	 *
	 * @param calendarResourceService the calendar resource remote service
	 */
	public void setCalendarResourceService(
		com.liferay.calendar.service.CalendarResourceService calendarResourceService) {
		this.calendarResourceService = calendarResourceService;
	}

	/**
	 * Returns the calendar resource persistence.
	 *
	 * @return the calendar resource persistence
	 */
	public CalendarResourcePersistence getCalendarResourcePersistence() {
		return calendarResourcePersistence;
	}

	/**
	 * Sets the calendar resource persistence.
	 *
	 * @param calendarResourcePersistence the calendar resource persistence
	 */
	public void setCalendarResourcePersistence(
		CalendarResourcePersistence calendarResourcePersistence) {
		this.calendarResourcePersistence = calendarResourcePersistence;
	}

	/**
	 * Returns the calendar resource finder.
	 *
	 * @return the calendar resource finder
	 */
	public CalendarResourceFinder getCalendarResourceFinder() {
		return calendarResourceFinder;
	}

	/**
	 * Sets the calendar resource finder.
	 *
	 * @param calendarResourceFinder the calendar resource finder
	 */
	public void setCalendarResourceFinder(
		CalendarResourceFinder calendarResourceFinder) {
		this.calendarResourceFinder = calendarResourceFinder;
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

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CalendarNotificationTemplateService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CalendarNotificationTemplate.class;
	}

	protected String getModelClassName() {
		return CalendarNotificationTemplate.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = calendarNotificationTemplatePersistence.getDataSource();

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

	@BeanReference(type = com.liferay.calendar.service.CalendarLocalService.class)
	protected com.liferay.calendar.service.CalendarLocalService calendarLocalService;
	@BeanReference(type = com.liferay.calendar.service.CalendarService.class)
	protected com.liferay.calendar.service.CalendarService calendarService;
	@BeanReference(type = CalendarPersistence.class)
	protected CalendarPersistence calendarPersistence;
	@BeanReference(type = CalendarFinder.class)
	protected CalendarFinder calendarFinder;
	@BeanReference(type = com.liferay.calendar.service.CalendarBookingLocalService.class)
	protected com.liferay.calendar.service.CalendarBookingLocalService calendarBookingLocalService;
	@BeanReference(type = com.liferay.calendar.service.CalendarBookingService.class)
	protected com.liferay.calendar.service.CalendarBookingService calendarBookingService;
	@BeanReference(type = CalendarBookingPersistence.class)
	protected CalendarBookingPersistence calendarBookingPersistence;
	@BeanReference(type = CalendarBookingFinder.class)
	protected CalendarBookingFinder calendarBookingFinder;
	@BeanReference(type = com.liferay.calendar.service.CalendarNotificationTemplateLocalService.class)
	protected com.liferay.calendar.service.CalendarNotificationTemplateLocalService calendarNotificationTemplateLocalService;
	@BeanReference(type = com.liferay.calendar.service.CalendarNotificationTemplateService.class)
	protected CalendarNotificationTemplateService calendarNotificationTemplateService;
	@BeanReference(type = CalendarNotificationTemplatePersistence.class)
	protected CalendarNotificationTemplatePersistence calendarNotificationTemplatePersistence;
	@BeanReference(type = com.liferay.calendar.service.CalendarResourceLocalService.class)
	protected com.liferay.calendar.service.CalendarResourceLocalService calendarResourceLocalService;
	@BeanReference(type = com.liferay.calendar.service.CalendarResourceService.class)
	protected com.liferay.calendar.service.CalendarResourceService calendarResourceService;
	@BeanReference(type = CalendarResourcePersistence.class)
	protected CalendarResourcePersistence calendarResourcePersistence;
	@BeanReference(type = CalendarResourceFinder.class)
	protected CalendarResourceFinder calendarResourceFinder;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = com.liferay.portal.service.ClassNameService.class)
	protected com.liferay.portal.service.ClassNameService classNameService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@ServiceReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
}