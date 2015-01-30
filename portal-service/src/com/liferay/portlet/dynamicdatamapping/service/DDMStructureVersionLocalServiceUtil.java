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

package com.liferay.portlet.dynamicdatamapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for DDMStructureVersion. This utility wraps
 * {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureVersionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionLocalService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureVersionLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureVersionLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDMStructureVersionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureVersionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m structure version to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the d d m structure version
	* @return the d d m structure version that was added
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion addDDMStructureVersion(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion ddmStructureVersion) {
		return getService().addDDMStructureVersion(ddmStructureVersion);
	}

	/**
	* Creates a new d d m structure version with the primary key. Does not add the d d m structure version to the database.
	*
	* @param structureVersionId the primary key for the new d d m structure version
	* @return the new d d m structure version
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion createDDMStructureVersion(
		long structureVersionId) {
		return getService().createDDMStructureVersion(structureVersionId);
	}

	/**
	* Deletes the d d m structure version from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the d d m structure version
	* @return the d d m structure version that was removed
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion deleteDDMStructureVersion(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion ddmStructureVersion) {
		return getService().deleteDDMStructureVersion(ddmStructureVersion);
	}

	/**
	* Deletes the d d m structure version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureVersionId the primary key of the d d m structure version
	* @return the d d m structure version that was removed
	* @throws PortalException if a d d m structure version with the primary key could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion deleteDDMStructureVersion(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDMStructureVersion(structureVersionId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion fetchDDMStructureVersion(
		long structureVersionId) {
		return getService().fetchDDMStructureVersion(structureVersionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Returns the d d m structure version with the primary key.
	*
	* @param structureVersionId the primary key of the d d m structure version
	* @return the d d m structure version
	* @throws PortalException if a d d m structure version with the primary key could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion getDDMStructureVersion(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMStructureVersion(structureVersionId);
	}

	/**
	* Returns a range of all the d d m structure versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @return the range of d d m structure versions
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion> getDDMStructureVersions(
		int start, int end) {
		return getService().getDDMStructureVersions(start, end);
	}

	/**
	* Returns the number of d d m structure versions.
	*
	* @return the number of d d m structure versions
	*/
	public static int getDDMStructureVersionsCount() {
		return getService().getDDMStructureVersionsCount();
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion getLatestStructureVersion(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestStructureVersion(structureId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion getStructureVersion(
		long structureId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStructureVersion(structureId, version);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion getStructureVersion(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStructureVersion(structureVersionId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion> getStructureVersions(
		long structureId) {
		return getService().getStructureVersions(structureId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion> getStructureVersions(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion> orderByComparator) {
		return getService()
				   .getStructureVersions(structureId, start, end,
			orderByComparator);
	}

	public static int getStructureVersionsCount(long structureId) {
		return getService().getStructureVersionsCount(structureId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the d d m structure version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the d d m structure version
	* @return the d d m structure version that was updated
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion updateDDMStructureVersion(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion ddmStructureVersion) {
		return getService().updateDDMStructureVersion(ddmStructureVersion);
	}

	public static DDMStructureVersionLocalService getService() {
		if (_service == null) {
			_service = (DDMStructureVersionLocalService)PortalBeanLocatorUtil.locate(DDMStructureVersionLocalService.class.getName());

			ReferenceRegistry.registerReference(DDMStructureVersionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDMStructureVersionLocalService service) {
	}

	private static DDMStructureVersionLocalService _service;
}