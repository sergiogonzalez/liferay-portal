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

package com.liferay.portlet.documentlibrary.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.service.DLTrashLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.service.BaseLocalServiceImpl;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the d l trash local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.documentlibrary.service.impl.DLTrashLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.documentlibrary.service.impl.DLTrashLocalServiceImpl
 * @see com.liferay.document.library.kernel.service.DLTrashLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class DLTrashLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements DLTrashLocalService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.document.library.kernel.service.DLTrashLocalServiceUtil} to access the d l trash local service.
	 */

	/**
	 * Returns the d l trash local service.
	 *
	 * @return the d l trash local service
	 */
	public DLTrashLocalService getDLTrashLocalService() {
		return dlTrashLocalService;
	}

	/**
	 * Sets the d l trash local service.
	 *
	 * @param dlTrashLocalService the d l trash local service
	 */
	public void setDLTrashLocalService(DLTrashLocalService dlTrashLocalService) {
		this.dlTrashLocalService = dlTrashLocalService;
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
	 * Returns the d l app helper local service.
	 *
	 * @return the d l app helper local service
	 */
	public com.liferay.document.library.kernel.service.DLAppHelperLocalService getDLAppHelperLocalService() {
		return dlAppHelperLocalService;
	}

	/**
	 * Sets the d l app helper local service.
	 *
	 * @param dlAppHelperLocalService the d l app helper local service
	 */
	public void setDLAppHelperLocalService(
		com.liferay.document.library.kernel.service.DLAppHelperLocalService dlAppHelperLocalService) {
		this.dlAppHelperLocalService = dlAppHelperLocalService;
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
		return DLTrashLocalService.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = InfrastructureUtil.getDataSource();

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

	@BeanReference(type = com.liferay.document.library.kernel.service.DLTrashLocalService.class)
	protected DLTrashLocalService dlTrashLocalService;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.document.library.kernel.service.DLAppHelperLocalService.class)
	protected com.liferay.document.library.kernel.service.DLAppHelperLocalService dlAppHelperLocalService;
}