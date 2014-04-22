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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link ContactLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ContactLocalService
 * @generated
 */
@ProviderType
public class ContactLocalServiceWrapper implements ContactLocalService,
	ServiceWrapper<ContactLocalService> {
	public ContactLocalServiceWrapper(ContactLocalService contactLocalService) {
		_contactLocalService = contactLocalService;
	}

	/**
	* Adds the contact to the database. Also notifies the appropriate model listeners.
	*
	* @param contact the contact
	* @return the contact that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Contact addContact(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.addContact(contact);
	}

	/**
	* Creates a new contact with the primary key. Does not add the contact to the database.
	*
	* @param contactId the primary key for the new contact
	* @return the new contact
	*/
	@Override
	public com.liferay.portal.model.Contact createContact(long contactId) {
		return _contactLocalService.createContact(contactId);
	}

	/**
	* Deletes the contact with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contactId the primary key of the contact
	* @return the contact that was removed
	* @throws PortalException if a contact with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Contact deleteContact(long contactId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.deleteContact(contactId);
	}

	/**
	* Deletes the contact from the database. Also notifies the appropriate model listeners.
	*
	* @param contact the contact
	* @return the contact that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Contact deleteContact(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.deleteContact(contact);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _contactLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ContactModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ContactModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.Contact fetchContact(long contactId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.fetchContact(contactId);
	}

	/**
	* Returns the contact with the primary key.
	*
	* @param contactId the primary key of the contact
	* @return the contact
	* @throws PortalException if a contact with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Contact getContact(long contactId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.getContact(contactId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the contacts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ContactModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of contacts
	* @param end the upper bound of the range of contacts (not inclusive)
	* @return the range of contacts
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Contact> getContacts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.getContacts(start, end);
	}

	/**
	* Returns the number of contacts.
	*
	* @return the number of contacts
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getContactsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.getContactsCount();
	}

	/**
	* Updates the contact in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param contact the contact
	* @return the contact that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Contact updateContact(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.updateContact(contact);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _contactLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_contactLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.Contact addContact(long userId,
		java.lang.String className, long classPK,
		java.lang.String emailAddress, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.addContact(userId, className, classPK,
			emailAddress, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn,
			facebookSn, icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn,
			ymSn, jobTitle);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Contact> getContacts(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.getContacts(classNameId, classPK, start,
			end, orderByComparator);
	}

	@Override
	public int getContactsCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.getContactsCount(classNameId, classPK);
	}

	@Override
	public com.liferay.portal.model.Contact updateContact(long contactId,
		java.lang.String emailAddress, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _contactLocalService.updateContact(contactId, emailAddress,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
			icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
			jobTitle);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ContactLocalService getWrappedContactLocalService() {
		return _contactLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedContactLocalService(
		ContactLocalService contactLocalService) {
		_contactLocalService = contactLocalService;
	}

	@Override
	public ContactLocalService getWrappedService() {
		return _contactLocalService;
	}

	@Override
	public void setWrappedService(ContactLocalService contactLocalService) {
		_contactLocalService = contactLocalService;
	}

	private ContactLocalService _contactLocalService;
}