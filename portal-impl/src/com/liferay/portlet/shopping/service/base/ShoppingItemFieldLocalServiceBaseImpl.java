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

package com.liferay.portlet.shopping.service.base;

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
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the shopping item field local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.shopping.service.impl.ShoppingItemFieldLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.shopping.service.impl.ShoppingItemFieldLocalServiceImpl
 * @see com.liferay.portlet.shopping.service.ShoppingItemFieldLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class ShoppingItemFieldLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements ShoppingItemFieldLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.shopping.service.ShoppingItemFieldLocalServiceUtil} to access the shopping item field local service.
	 */

	/**
	 * Adds the shopping item field to the database. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingItemField the shopping item field
	 * @return the shopping item field that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ShoppingItemField addShoppingItemField(
		ShoppingItemField shoppingItemField) {
		shoppingItemField.setNew(true);

		return shoppingItemFieldPersistence.update(shoppingItemField);
	}

	/**
	 * Creates a new shopping item field with the primary key. Does not add the shopping item field to the database.
	 *
	 * @param itemFieldId the primary key for the new shopping item field
	 * @return the new shopping item field
	 */
	@Override
	public ShoppingItemField createShoppingItemField(long itemFieldId) {
		return shoppingItemFieldPersistence.create(itemFieldId);
	}

	/**
	 * Deletes the shopping item field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param itemFieldId the primary key of the shopping item field
	 * @return the shopping item field that was removed
	 * @throws PortalException if a shopping item field with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ShoppingItemField deleteShoppingItemField(long itemFieldId)
		throws PortalException {
		return shoppingItemFieldPersistence.remove(itemFieldId);
	}

	/**
	 * Deletes the shopping item field from the database. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingItemField the shopping item field
	 * @return the shopping item field that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ShoppingItemField deleteShoppingItemField(
		ShoppingItemField shoppingItemField) {
		return shoppingItemFieldPersistence.remove(shoppingItemField);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(ShoppingItemField.class,
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
		return shoppingItemFieldPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return shoppingItemFieldPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return shoppingItemFieldPersistence.findWithDynamicQuery(dynamicQuery,
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
		return shoppingItemFieldPersistence.countWithDynamicQuery(dynamicQuery);
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
		return shoppingItemFieldPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public ShoppingItemField fetchShoppingItemField(long itemFieldId) {
		return shoppingItemFieldPersistence.fetchByPrimaryKey(itemFieldId);
	}

	/**
	 * Returns the shopping item field with the primary key.
	 *
	 * @param itemFieldId the primary key of the shopping item field
	 * @return the shopping item field
	 * @throws PortalException if a shopping item field with the primary key could not be found
	 */
	@Override
	public ShoppingItemField getShoppingItemField(long itemFieldId)
		throws PortalException {
		return shoppingItemFieldPersistence.findByPrimaryKey(itemFieldId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.shopping.service.ShoppingItemFieldLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(ShoppingItemField.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("itemFieldId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.shopping.service.ShoppingItemFieldLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(ShoppingItemField.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("itemFieldId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return shoppingItemFieldLocalService.deleteShoppingItemField((ShoppingItemField)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return shoppingItemFieldPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the shopping item fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @return the range of shopping item fields
	 */
	@Override
	public List<ShoppingItemField> getShoppingItemFields(int start, int end) {
		return shoppingItemFieldPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of shopping item fields.
	 *
	 * @return the number of shopping item fields
	 */
	@Override
	public int getShoppingItemFieldsCount() {
		return shoppingItemFieldPersistence.countAll();
	}

	/**
	 * Updates the shopping item field in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingItemField the shopping item field
	 * @return the shopping item field that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ShoppingItemField updateShoppingItemField(
		ShoppingItemField shoppingItemField) {
		return shoppingItemFieldPersistence.update(shoppingItemField);
	}

	/**
	 * Returns the shopping item field local service.
	 *
	 * @return the shopping item field local service
	 */
	public com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService getShoppingItemFieldLocalService() {
		return shoppingItemFieldLocalService;
	}

	/**
	 * Sets the shopping item field local service.
	 *
	 * @param shoppingItemFieldLocalService the shopping item field local service
	 */
	public void setShoppingItemFieldLocalService(
		com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService shoppingItemFieldLocalService) {
		this.shoppingItemFieldLocalService = shoppingItemFieldLocalService;
	}

	/**
	 * Returns the shopping item field persistence.
	 *
	 * @return the shopping item field persistence
	 */
	public ShoppingItemFieldPersistence getShoppingItemFieldPersistence() {
		return shoppingItemFieldPersistence;
	}

	/**
	 * Sets the shopping item field persistence.
	 *
	 * @param shoppingItemFieldPersistence the shopping item field persistence
	 */
	public void setShoppingItemFieldPersistence(
		ShoppingItemFieldPersistence shoppingItemFieldPersistence) {
		this.shoppingItemFieldPersistence = shoppingItemFieldPersistence;
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

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.shopping.model.ShoppingItemField",
			shoppingItemFieldLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.shopping.model.ShoppingItemField");
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
		return ShoppingItemField.class;
	}

	protected String getModelClassName() {
		return ShoppingItemField.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = shoppingItemFieldPersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService.class)
	protected com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService shoppingItemFieldLocalService;
	@BeanReference(type = ShoppingItemFieldPersistence.class)
	protected ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}