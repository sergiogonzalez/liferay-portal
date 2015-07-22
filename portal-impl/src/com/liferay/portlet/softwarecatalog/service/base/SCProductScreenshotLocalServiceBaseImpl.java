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

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the s c product screenshot local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.softwarecatalog.service.impl.SCProductScreenshotLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCProductScreenshotLocalServiceImpl
 * @see com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class SCProductScreenshotLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements SCProductScreenshotLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil} to access the s c product screenshot local service.
	 */

	/**
	 * Adds the s c product screenshot to the database. Also notifies the appropriate model listeners.
	 *
	 * @param scProductScreenshot the s c product screenshot
	 * @return the s c product screenshot that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SCProductScreenshot addSCProductScreenshot(
		SCProductScreenshot scProductScreenshot) {
		scProductScreenshot.setNew(true);

		return scProductScreenshotPersistence.update(scProductScreenshot);
	}

	/**
	 * Creates a new s c product screenshot with the primary key. Does not add the s c product screenshot to the database.
	 *
	 * @param productScreenshotId the primary key for the new s c product screenshot
	 * @return the new s c product screenshot
	 */
	@Override
	public SCProductScreenshot createSCProductScreenshot(
		long productScreenshotId) {
		return scProductScreenshotPersistence.create(productScreenshotId);
	}

	/**
	 * Deletes the s c product screenshot with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productScreenshotId the primary key of the s c product screenshot
	 * @return the s c product screenshot that was removed
	 * @throws PortalException if a s c product screenshot with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SCProductScreenshot deleteSCProductScreenshot(
		long productScreenshotId) throws PortalException {
		return scProductScreenshotPersistence.remove(productScreenshotId);
	}

	/**
	 * Deletes the s c product screenshot from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scProductScreenshot the s c product screenshot
	 * @return the s c product screenshot that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SCProductScreenshot deleteSCProductScreenshot(
		SCProductScreenshot scProductScreenshot) {
		return scProductScreenshotPersistence.remove(scProductScreenshot);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
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
		return scProductScreenshotPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return scProductScreenshotPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return scProductScreenshotPersistence.findWithDynamicQuery(dynamicQuery,
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
		return scProductScreenshotPersistence.countWithDynamicQuery(dynamicQuery);
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
		return scProductScreenshotPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public SCProductScreenshot fetchSCProductScreenshot(
		long productScreenshotId) {
		return scProductScreenshotPersistence.fetchByPrimaryKey(productScreenshotId);
	}

	/**
	 * Returns the s c product screenshot with the primary key.
	 *
	 * @param productScreenshotId the primary key of the s c product screenshot
	 * @return the s c product screenshot
	 * @throws PortalException if a s c product screenshot with the primary key could not be found
	 */
	@Override
	public SCProductScreenshot getSCProductScreenshot(long productScreenshotId)
		throws PortalException {
		return scProductScreenshotPersistence.findByPrimaryKey(productScreenshotId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(SCProductScreenshot.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("productScreenshotId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(SCProductScreenshot.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("productScreenshotId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return scProductScreenshotLocalService.deleteSCProductScreenshot((SCProductScreenshot)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return scProductScreenshotPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the s c product screenshots.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product screenshots
	 * @param end the upper bound of the range of s c product screenshots (not inclusive)
	 * @return the range of s c product screenshots
	 */
	@Override
	public List<SCProductScreenshot> getSCProductScreenshots(int start, int end) {
		return scProductScreenshotPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of s c product screenshots.
	 *
	 * @return the number of s c product screenshots
	 */
	@Override
	public int getSCProductScreenshotsCount() {
		return scProductScreenshotPersistence.countAll();
	}

	/**
	 * Updates the s c product screenshot in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param scProductScreenshot the s c product screenshot
	 * @return the s c product screenshot that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SCProductScreenshot updateSCProductScreenshot(
		SCProductScreenshot scProductScreenshot) {
		return scProductScreenshotPersistence.update(scProductScreenshot);
	}

	/**
	 * Returns the s c product screenshot local service.
	 *
	 * @return the s c product screenshot local service
	 */
	public SCProductScreenshotLocalService getSCProductScreenshotLocalService() {
		return scProductScreenshotLocalService;
	}

	/**
	 * Sets the s c product screenshot local service.
	 *
	 * @param scProductScreenshotLocalService the s c product screenshot local service
	 */
	public void setSCProductScreenshotLocalService(
		SCProductScreenshotLocalService scProductScreenshotLocalService) {
		this.scProductScreenshotLocalService = scProductScreenshotLocalService;
	}

	/**
	 * Returns the s c product screenshot persistence.
	 *
	 * @return the s c product screenshot persistence
	 */
	public SCProductScreenshotPersistence getSCProductScreenshotPersistence() {
		return scProductScreenshotPersistence;
	}

	/**
	 * Sets the s c product screenshot persistence.
	 *
	 * @param scProductScreenshotPersistence the s c product screenshot persistence
	 */
	public void setSCProductScreenshotPersistence(
		SCProductScreenshotPersistence scProductScreenshotPersistence) {
		this.scProductScreenshotPersistence = scProductScreenshotPersistence;
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
	 * Returns the image local service.
	 *
	 * @return the image local service
	 */
	public com.liferay.portal.service.ImageLocalService getImageLocalService() {
		return imageLocalService;
	}

	/**
	 * Sets the image local service.
	 *
	 * @param imageLocalService the image local service
	 */
	public void setImageLocalService(
		com.liferay.portal.service.ImageLocalService imageLocalService) {
		this.imageLocalService = imageLocalService;
	}

	/**
	 * Returns the image remote service.
	 *
	 * @return the image remote service
	 */
	public com.liferay.portal.service.ImageService getImageService() {
		return imageService;
	}

	/**
	 * Sets the image remote service.
	 *
	 * @param imageService the image remote service
	 */
	public void setImageService(
		com.liferay.portal.service.ImageService imageService) {
		this.imageService = imageService;
	}

	/**
	 * Returns the image persistence.
	 *
	 * @return the image persistence
	 */
	public ImagePersistence getImagePersistence() {
		return imagePersistence;
	}

	/**
	 * Sets the image persistence.
	 *
	 * @param imagePersistence the image persistence
	 */
	public void setImagePersistence(ImagePersistence imagePersistence) {
		this.imagePersistence = imagePersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.softwarecatalog.model.SCProductScreenshot",
			scProductScreenshotLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.softwarecatalog.model.SCProductScreenshot");
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
		return SCProductScreenshot.class;
	}

	protected String getModelClassName() {
		return SCProductScreenshot.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = scProductScreenshotPersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService.class)
	protected SCProductScreenshotLocalService scProductScreenshotLocalService;
	@BeanReference(type = SCProductScreenshotPersistence.class)
	protected SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ImageLocalService.class)
	protected com.liferay.portal.service.ImageLocalService imageLocalService;
	@BeanReference(type = com.liferay.portal.service.ImageService.class)
	protected com.liferay.portal.service.ImageService imageService;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}