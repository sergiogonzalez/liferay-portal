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

package com.liferay.asset.auto.tagger.service.base;

import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryService;
import com.liferay.asset.auto.tagger.service.persistence.AssetAutoTaggerEntryPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the asset auto tagger entry remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.asset.auto.tagger.service.impl.AssetAutoTaggerEntryServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.auto.tagger.service.impl.AssetAutoTaggerEntryServiceImpl
 * @see com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryServiceUtil
 * @generated
 */
public abstract class AssetAutoTaggerEntryServiceBaseImpl
	extends BaseServiceImpl implements AssetAutoTaggerEntryService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryServiceUtil} to access the asset auto tagger entry remote service.
	 */

	/**
	 * Returns the asset auto tagger entry local service.
	 *
	 * @return the asset auto tagger entry local service
	 */
	public com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService getAssetAutoTaggerEntryLocalService() {
		return assetAutoTaggerEntryLocalService;
	}

	/**
	 * Sets the asset auto tagger entry local service.
	 *
	 * @param assetAutoTaggerEntryLocalService the asset auto tagger entry local service
	 */
	public void setAssetAutoTaggerEntryLocalService(
		com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService assetAutoTaggerEntryLocalService) {
		this.assetAutoTaggerEntryLocalService = assetAutoTaggerEntryLocalService;
	}

	/**
	 * Returns the asset auto tagger entry remote service.
	 *
	 * @return the asset auto tagger entry remote service
	 */
	public AssetAutoTaggerEntryService getAssetAutoTaggerEntryService() {
		return assetAutoTaggerEntryService;
	}

	/**
	 * Sets the asset auto tagger entry remote service.
	 *
	 * @param assetAutoTaggerEntryService the asset auto tagger entry remote service
	 */
	public void setAssetAutoTaggerEntryService(
		AssetAutoTaggerEntryService assetAutoTaggerEntryService) {
		this.assetAutoTaggerEntryService = assetAutoTaggerEntryService;
	}

	/**
	 * Returns the asset auto tagger entry persistence.
	 *
	 * @return the asset auto tagger entry persistence
	 */
	public AssetAutoTaggerEntryPersistence getAssetAutoTaggerEntryPersistence() {
		return assetAutoTaggerEntryPersistence;
	}

	/**
	 * Sets the asset auto tagger entry persistence.
	 *
	 * @param assetAutoTaggerEntryPersistence the asset auto tagger entry persistence
	 */
	public void setAssetAutoTaggerEntryPersistence(
		AssetAutoTaggerEntryPersistence assetAutoTaggerEntryPersistence) {
		this.assetAutoTaggerEntryPersistence = assetAutoTaggerEntryPersistence;
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
		return AssetAutoTaggerEntryService.class.getName();
	}

	protected Class<?> getModelClass() {
		return AssetAutoTaggerEntry.class;
	}

	protected String getModelClassName() {
		return AssetAutoTaggerEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = assetAutoTaggerEntryPersistence.getDataSource();

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

	@BeanReference(type = com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService.class)
	protected com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService assetAutoTaggerEntryLocalService;
	@BeanReference(type = AssetAutoTaggerEntryService.class)
	protected AssetAutoTaggerEntryService assetAutoTaggerEntryService;
	@BeanReference(type = AssetAutoTaggerEntryPersistence.class)
	protected AssetAutoTaggerEntryPersistence assetAutoTaggerEntryPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
}