/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionService;
import com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService;
import com.liferay.portlet.softwarecatalog.service.SCLicenseService;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryService;
import com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionService;
import com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * The base implementation of the s c license local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.softwarecatalog.service.impl.SCLicenseLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCLicenseLocalServiceImpl
 * @see com.liferay.portlet.softwarecatalog.service.SCLicenseLocalServiceUtil
 * @generated
 */
public abstract class SCLicenseLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements SCLicenseLocalService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.softwarecatalog.service.SCLicenseLocalServiceUtil} to access the s c license local service.
	 */

	/**
	 * Adds the s c license to the database. Also notifies the appropriate model listeners.
	 *
	 * @param scLicense the s c license
	 * @return the s c license that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public SCLicense addSCLicense(SCLicense scLicense)
		throws SystemException {
		scLicense.setNew(true);

		return scLicensePersistence.update(scLicense);
	}

	/**
	 * Creates a new s c license with the primary key. Does not add the s c license to the database.
	 *
	 * @param licenseId the primary key for the new s c license
	 * @return the new s c license
	 */
	public SCLicense createSCLicense(long licenseId) {
		return scLicensePersistence.create(licenseId);
	}

	/**
	 * Deletes the s c license with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param licenseId the primary key of the s c license
	 * @return the s c license that was removed
	 * @throws PortalException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public SCLicense deleteSCLicense(long licenseId)
		throws PortalException, SystemException {
		return scLicensePersistence.remove(licenseId);
	}

	/**
	 * Deletes the s c license from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scLicense the s c license
	 * @return the s c license that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public SCLicense deleteSCLicense(SCLicense scLicense)
		throws SystemException {
		return scLicensePersistence.remove(scLicense);
	}

	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(SCLicense.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return scLicensePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return scLicensePersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return scLicensePersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return scLicensePersistence.countWithDynamicQuery(dynamicQuery);
	}

	public SCLicense fetchSCLicense(long licenseId) throws SystemException {
		return scLicensePersistence.fetchByPrimaryKey(licenseId);
	}

	/**
	 * Returns the s c license with the primary key.
	 *
	 * @param licenseId the primary key of the s c license
	 * @return the s c license
	 * @throws PortalException if a s c license with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCLicense getSCLicense(long licenseId)
		throws PortalException, SystemException {
		return scLicensePersistence.findByPrimaryKey(licenseId);
	}

	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return scLicensePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the s c licenses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c licenses
	 * @param end the upper bound of the range of s c licenses (not inclusive)
	 * @return the range of s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCLicense> getSCLicenses(int start, int end)
		throws SystemException {
		return scLicensePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of s c licenses.
	 *
	 * @return the number of s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	public int getSCLicensesCount() throws SystemException {
		return scLicensePersistence.countAll();
	}

	/**
	 * Updates the s c license in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param scLicense the s c license
	 * @return the s c license that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public SCLicense updateSCLicense(SCLicense scLicense)
		throws SystemException {
		return scLicensePersistence.update(scLicense);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCProductEntrySCLicense(long productEntryId, long licenseId)
		throws SystemException {
		scProductEntryPersistence.addSCLicense(productEntryId, licenseId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCProductEntrySCLicense(long productEntryId,
		SCLicense scLicense) throws SystemException {
		scProductEntryPersistence.addSCLicense(productEntryId, scLicense);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds) throws SystemException {
		scProductEntryPersistence.addSCLicenses(productEntryId, licenseIds);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCProductEntrySCLicenses(long productEntryId,
		List<SCLicense> SCLicenses) throws SystemException {
		scProductEntryPersistence.addSCLicenses(productEntryId, SCLicenses);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void clearSCProductEntrySCLicenses(long productEntryId)
		throws SystemException {
		scProductEntryPersistence.clearSCLicenses(productEntryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteSCProductEntrySCLicense(long productEntryId,
		long licenseId) throws SystemException {
		scProductEntryPersistence.removeSCLicense(productEntryId, licenseId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteSCProductEntrySCLicense(long productEntryId,
		SCLicense scLicense) throws SystemException {
		scProductEntryPersistence.removeSCLicense(productEntryId, scLicense);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds) throws SystemException {
		scProductEntryPersistence.removeSCLicenses(productEntryId, licenseIds);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteSCProductEntrySCLicenses(long productEntryId,
		List<SCLicense> SCLicenses) throws SystemException {
		scProductEntryPersistence.removeSCLicenses(productEntryId, SCLicenses);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCLicense> getSCProductEntrySCLicenses(long productEntryId)
		throws SystemException {
		return scProductEntryPersistence.getSCLicenses(productEntryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCLicense> getSCProductEntrySCLicenses(long productEntryId,
		int start, int end) throws SystemException {
		return scProductEntryPersistence.getSCLicenses(productEntryId, start,
			end);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCLicense> getSCProductEntrySCLicenses(long productEntryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return scProductEntryPersistence.getSCLicenses(productEntryId, start,
			end, orderByComparator);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public int getSCProductEntrySCLicensesCount(long productEntryId)
		throws SystemException {
		return scProductEntryPersistence.getSCLicensesSize(productEntryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasSCProductEntrySCLicense(long productEntryId,
		long licenseId) throws SystemException {
		return scProductEntryPersistence.containsSCLicense(productEntryId,
			licenseId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasSCProductEntrySCLicenses(long productEntryId)
		throws SystemException {
		return scProductEntryPersistence.containsSCLicenses(productEntryId);
	}

	/**
	 * @throws SystemException if a system exception occurred
	 */
	public void setSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds) throws SystemException {
		scProductEntryPersistence.setSCLicenses(productEntryId, licenseIds);
	}

	/**
	 * Returns the s c framework version local service.
	 *
	 * @return the s c framework version local service
	 */
	public SCFrameworkVersionLocalService getSCFrameworkVersionLocalService() {
		return scFrameworkVersionLocalService;
	}

	/**
	 * Sets the s c framework version local service.
	 *
	 * @param scFrameworkVersionLocalService the s c framework version local service
	 */
	public void setSCFrameworkVersionLocalService(
		SCFrameworkVersionLocalService scFrameworkVersionLocalService) {
		this.scFrameworkVersionLocalService = scFrameworkVersionLocalService;
	}

	/**
	 * Returns the s c framework version remote service.
	 *
	 * @return the s c framework version remote service
	 */
	public SCFrameworkVersionService getSCFrameworkVersionService() {
		return scFrameworkVersionService;
	}

	/**
	 * Sets the s c framework version remote service.
	 *
	 * @param scFrameworkVersionService the s c framework version remote service
	 */
	public void setSCFrameworkVersionService(
		SCFrameworkVersionService scFrameworkVersionService) {
		this.scFrameworkVersionService = scFrameworkVersionService;
	}

	/**
	 * Returns the s c framework version persistence.
	 *
	 * @return the s c framework version persistence
	 */
	public SCFrameworkVersionPersistence getSCFrameworkVersionPersistence() {
		return scFrameworkVersionPersistence;
	}

	/**
	 * Sets the s c framework version persistence.
	 *
	 * @param scFrameworkVersionPersistence the s c framework version persistence
	 */
	public void setSCFrameworkVersionPersistence(
		SCFrameworkVersionPersistence scFrameworkVersionPersistence) {
		this.scFrameworkVersionPersistence = scFrameworkVersionPersistence;
	}

	/**
	 * Returns the s c license local service.
	 *
	 * @return the s c license local service
	 */
	public SCLicenseLocalService getSCLicenseLocalService() {
		return scLicenseLocalService;
	}

	/**
	 * Sets the s c license local service.
	 *
	 * @param scLicenseLocalService the s c license local service
	 */
	public void setSCLicenseLocalService(
		SCLicenseLocalService scLicenseLocalService) {
		this.scLicenseLocalService = scLicenseLocalService;
	}

	/**
	 * Returns the s c license remote service.
	 *
	 * @return the s c license remote service
	 */
	public SCLicenseService getSCLicenseService() {
		return scLicenseService;
	}

	/**
	 * Sets the s c license remote service.
	 *
	 * @param scLicenseService the s c license remote service
	 */
	public void setSCLicenseService(SCLicenseService scLicenseService) {
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
	 * Returns the s c product entry local service.
	 *
	 * @return the s c product entry local service
	 */
	public SCProductEntryLocalService getSCProductEntryLocalService() {
		return scProductEntryLocalService;
	}

	/**
	 * Sets the s c product entry local service.
	 *
	 * @param scProductEntryLocalService the s c product entry local service
	 */
	public void setSCProductEntryLocalService(
		SCProductEntryLocalService scProductEntryLocalService) {
		this.scProductEntryLocalService = scProductEntryLocalService;
	}

	/**
	 * Returns the s c product entry remote service.
	 *
	 * @return the s c product entry remote service
	 */
	public SCProductEntryService getSCProductEntryService() {
		return scProductEntryService;
	}

	/**
	 * Sets the s c product entry remote service.
	 *
	 * @param scProductEntryService the s c product entry remote service
	 */
	public void setSCProductEntryService(
		SCProductEntryService scProductEntryService) {
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
	 * Returns the s c product version local service.
	 *
	 * @return the s c product version local service
	 */
	public SCProductVersionLocalService getSCProductVersionLocalService() {
		return scProductVersionLocalService;
	}

	/**
	 * Sets the s c product version local service.
	 *
	 * @param scProductVersionLocalService the s c product version local service
	 */
	public void setSCProductVersionLocalService(
		SCProductVersionLocalService scProductVersionLocalService) {
		this.scProductVersionLocalService = scProductVersionLocalService;
	}

	/**
	 * Returns the s c product version remote service.
	 *
	 * @return the s c product version remote service
	 */
	public SCProductVersionService getSCProductVersionService() {
		return scProductVersionService;
	}

	/**
	 * Sets the s c product version remote service.
	 *
	 * @param scProductVersionService the s c product version remote service
	 */
	public void setSCProductVersionService(
		SCProductVersionService scProductVersionService) {
		this.scProductVersionService = scProductVersionService;
	}

	/**
	 * Returns the s c product version persistence.
	 *
	 * @return the s c product version persistence
	 */
	public SCProductVersionPersistence getSCProductVersionPersistence() {
		return scProductVersionPersistence;
	}

	/**
	 * Sets the s c product version persistence.
	 *
	 * @param scProductVersionPersistence the s c product version persistence
	 */
	public void setSCProductVersionPersistence(
		SCProductVersionPersistence scProductVersionPersistence) {
		this.scProductVersionPersistence = scProductVersionPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
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

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.softwarecatalog.model.SCLicense",
			scLicenseLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.softwarecatalog.model.SCLicense");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
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
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = scLicensePersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = SCFrameworkVersionLocalService.class)
	protected SCFrameworkVersionLocalService scFrameworkVersionLocalService;
	@BeanReference(type = SCFrameworkVersionService.class)
	protected SCFrameworkVersionService scFrameworkVersionService;
	@BeanReference(type = SCFrameworkVersionPersistence.class)
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(type = SCLicenseLocalService.class)
	protected SCLicenseLocalService scLicenseLocalService;
	@BeanReference(type = SCLicenseService.class)
	protected SCLicenseService scLicenseService;
	@BeanReference(type = SCLicensePersistence.class)
	protected SCLicensePersistence scLicensePersistence;
	@BeanReference(type = SCProductEntryLocalService.class)
	protected SCProductEntryLocalService scProductEntryLocalService;
	@BeanReference(type = SCProductEntryService.class)
	protected SCProductEntryService scProductEntryService;
	@BeanReference(type = SCProductEntryPersistence.class)
	protected SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(type = SCProductScreenshotLocalService.class)
	protected SCProductScreenshotLocalService scProductScreenshotLocalService;
	@BeanReference(type = SCProductScreenshotPersistence.class)
	protected SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(type = SCProductVersionLocalService.class)
	protected SCProductVersionLocalService scProductVersionLocalService;
	@BeanReference(type = SCProductVersionService.class)
	protected SCProductVersionService scProductVersionService;
	@BeanReference(type = SCProductVersionPersistence.class)
	protected SCProductVersionPersistence scProductVersionPersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}