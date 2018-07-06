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

package com.liferay.document.library.google.drive.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for GoogleDriveEntry. This utility wraps
 * {@link com.liferay.document.library.google.drive.service.impl.GoogleDriveEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see GoogleDriveEntryLocalService
 * @see com.liferay.document.library.google.drive.service.base.GoogleDriveEntryLocalServiceBaseImpl
 * @see com.liferay.document.library.google.drive.service.impl.GoogleDriveEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class GoogleDriveEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.document.library.google.drive.service.impl.GoogleDriveEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the google drive entry to the database. Also notifies the appropriate model listeners.
	*
	* @param googleDriveEntry the google drive entry
	* @return the google drive entry that was added
	*/
	public static com.liferay.document.library.google.drive.model.GoogleDriveEntry addGoogleDriveEntry(
		com.liferay.document.library.google.drive.model.GoogleDriveEntry googleDriveEntry) {
		return getService().addGoogleDriveEntry(googleDriveEntry);
	}

	/**
	* Creates a new google drive entry with the primary key. Does not add the google drive entry to the database.
	*
	* @param entryId the primary key for the new google drive entry
	* @return the new google drive entry
	*/
	public static com.liferay.document.library.google.drive.model.GoogleDriveEntry createGoogleDriveEntry(
		long entryId) {
		return getService().createGoogleDriveEntry(entryId);
	}

	/**
	* Deletes the google drive entry from the database. Also notifies the appropriate model listeners.
	*
	* @param googleDriveEntry the google drive entry
	* @return the google drive entry that was removed
	*/
	public static com.liferay.document.library.google.drive.model.GoogleDriveEntry deleteGoogleDriveEntry(
		com.liferay.document.library.google.drive.model.GoogleDriveEntry googleDriveEntry) {
		return getService().deleteGoogleDriveEntry(googleDriveEntry);
	}

	/**
	* Deletes the google drive entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the google drive entry
	* @return the google drive entry that was removed
	* @throws PortalException if a google drive entry with the primary key could not be found
	*/
	public static com.liferay.document.library.google.drive.model.GoogleDriveEntry deleteGoogleDriveEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteGoogleDriveEntry(entryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.google.drive.model.impl.GoogleDriveEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.google.drive.model.impl.GoogleDriveEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.document.library.google.drive.model.GoogleDriveEntry fetchGoogleDriveEntry(
		long entryId) {
		return getService().fetchGoogleDriveEntry(entryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the google drive entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.google.drive.model.impl.GoogleDriveEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of google drive entries
	* @param end the upper bound of the range of google drive entries (not inclusive)
	* @return the range of google drive entries
	*/
	public static java.util.List<com.liferay.document.library.google.drive.model.GoogleDriveEntry> getGoogleDriveEntries(
		int start, int end) {
		return getService().getGoogleDriveEntries(start, end);
	}

	/**
	* Returns the number of google drive entries.
	*
	* @return the number of google drive entries
	*/
	public static int getGoogleDriveEntriesCount() {
		return getService().getGoogleDriveEntriesCount();
	}

	/**
	* Returns the google drive entry with the primary key.
	*
	* @param entryId the primary key of the google drive entry
	* @return the google drive entry
	* @throws PortalException if a google drive entry with the primary key could not be found
	*/
	public static com.liferay.document.library.google.drive.model.GoogleDriveEntry getGoogleDriveEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGoogleDriveEntry(entryId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the google drive entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param googleDriveEntry the google drive entry
	* @return the google drive entry that was updated
	*/
	public static com.liferay.document.library.google.drive.model.GoogleDriveEntry updateGoogleDriveEntry(
		com.liferay.document.library.google.drive.model.GoogleDriveEntry googleDriveEntry) {
		return getService().updateGoogleDriveEntry(googleDriveEntry);
	}

	public static GoogleDriveEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<GoogleDriveEntryLocalService, GoogleDriveEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(GoogleDriveEntryLocalService.class);

		ServiceTracker<GoogleDriveEntryLocalService, GoogleDriveEntryLocalService> serviceTracker =
			new ServiceTracker<GoogleDriveEntryLocalService, GoogleDriveEntryLocalService>(bundle.getBundleContext(),
				GoogleDriveEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}