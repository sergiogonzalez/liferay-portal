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

package com.liferay.portlet.softwarecatalog.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.service.SCLicenseService;
import com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the s c license remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.softwarecatalog.service.impl.SCLicenseServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCLicenseServiceImpl
 * @see com.liferay.portlet.softwarecatalog.service.SCLicenseServiceUtil
 * @generated
 */
public abstract class SCLicenseServiceBaseImpl extends BaseServiceImpl
	implements SCLicenseService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.softwarecatalog.service.SCLicenseServiceUtil} to access the s c license remote service.
	 */

	/**
	 * Returns the s c license local service.
	 *
	 * @return the s c license local service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService getSCLicenseLocalService() {
		return scLicenseLocalService;
	}

	/**
	 * Sets the s c license local service.
	 *
	 * @param scLicenseLocalService the s c license local service
	 */
	public void setSCLicenseLocalService(
		com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService scLicenseLocalService) {
		this.scLicenseLocalService = scLicenseLocalService;
	}

	/**
	 * Returns the s c license remote service.
	 *
	 * @return the s c license remote service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCLicenseService getSCLicenseService() {
		return scLicenseService;
	}

	/**
	 * Sets the s c license remote service.
	 *
	 * @param scLicenseService the s c license remote service
	 */
	public void setSCLicenseService(
		com.liferay.portlet.softwarecatalog.service.SCLicenseService scLicenseService) {
		this.scLicenseService = scLicenseService;
	}

	/**
	 * Returns the s c license persistence.
	 *
	 * @return the s c license persistence
	 */
	public SCLicensePersistence getSCLicensePersistence() {
		return scLicensePersistence;
	}

	/**
	 * Sets the s c license persistence.
	 *
	 * @param scLicensePersistence the s c license persistence
	 */
	public void setSCLicensePersistence(
		SCLicensePersistence scLicensePersistence) {
		this.scLicensePersistence = scLicensePersistence;
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
	 * Returns the s c product entry local service.
	 *
	 * @return the s c product entry local service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService getSCProductEntryLocalService() {
		return scProductEntryLocalService;
	}

	/**
	 * Sets the s c product entry local service.
	 *
	 * @param scProductEntryLocalService the s c product entry local service
	 */
	public void setSCProductEntryLocalService(
		com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService scProductEntryLocalService) {
		this.scProductEntryLocalService = scProductEntryLocalService;
	}

	/**
	 * Returns the s c product entry remote service.
	 *
	 * @return the s c product entry remote service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCProductEntryService getSCProductEntryService() {
		return scProductEntryService;
	}

	/**
	 * Sets the s c product entry remote service.
	 *
	 * @param scProductEntryService the s c product entry remote service
	 */
	public void setSCProductEntryService(
		com.liferay.portlet.softwarecatalog.service.SCProductEntryService scProductEntryService) {
		this.scProductEntryService = scProductEntryService;
	}

	/**
	 * Returns the s c product entry persistence.
	 *
	 * @return the s c product entry persistence
	 */
	public SCProductEntryPersistence getSCProductEntryPersistence() {
		return scProductEntryPersistence;
	}

	/**
	 * Sets the s c product entry persistence.
	 *
	 * @param scProductEntryPersistence the s c product entry persistence
	 */
	public void setSCProductEntryPersistence(
		SCProductEntryPersistence scProductEntryPersistence) {
		this.scProductEntryPersistence = scProductEntryPersistence;
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
		return SCLicense.class;
	}

	protected String getModelClassName() {
		return SCLicense.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = scLicensePersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService scLicenseLocalService;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCLicenseService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCLicenseService scLicenseService;
	@BeanReference(type = SCLicensePersistence.class)
	protected SCLicensePersistence scLicensePersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService scProductEntryLocalService;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductEntryService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCProductEntryService scProductEntryService;
	@BeanReference(type = SCProductEntryPersistence.class)
	protected SCProductEntryPersistence scProductEntryPersistence;
	private String _beanIdentifier;
}