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

package com.liferay.message.boards.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBMessageLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageLocalService
 * @generated
 */
@ProviderType
public class MBMessageLocalServiceWrapper implements MBMessageLocalService,
	ServiceWrapper<MBMessageLocalService> {
	public MBMessageLocalServiceWrapper(
		MBMessageLocalService mbMessageLocalService) {
		_mbMessageLocalService = mbMessageLocalService;
	}

	/**
	* Adds the message-boards message to the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message
	* @return the message-boards message that was added
	*/
	@Override
	public com.liferay.message.boards.model.MBMessage addMBMessage(
		com.liferay.message.boards.model.MBMessage mbMessage) {
		return _mbMessageLocalService.addMBMessage(mbMessage);
	}

	/**
	* Creates a new message-boards message with the primary key. Does not add the message-boards message to the database.
	*
	* @param messageId the primary key for the new message-boards message
	* @return the new message-boards message
	*/
	@Override
	public com.liferay.message.boards.model.MBMessage createMBMessage(
		long messageId) {
		return _mbMessageLocalService.createMBMessage(messageId);
	}

	/**
	* Deletes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param messageId the primary key of the message-boards message
	* @return the message-boards message that was removed
	* @throws PortalException if a message-boards message with the primary key could not be found
	*/
	@Override
	public com.liferay.message.boards.model.MBMessage deleteMBMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageLocalService.deleteMBMessage(messageId);
	}

	/**
	* Deletes the message-boards message from the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message
	* @return the message-boards message that was removed
	*/
	@Override
	public com.liferay.message.boards.model.MBMessage deleteMBMessage(
		com.liferay.message.boards.model.MBMessage mbMessage) {
		return _mbMessageLocalService.deleteMBMessage(mbMessage);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mbMessageLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _mbMessageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.message.boards.model.impl.MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _mbMessageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.message.boards.model.impl.MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _mbMessageLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _mbMessageLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _mbMessageLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.message.boards.model.MBMessage fetchMBMessage(
		long messageId) {
		return _mbMessageLocalService.fetchMBMessage(messageId);
	}

	/**
	* Returns the message-boards message matching the UUID and group.
	*
	* @param uuid the message-boards message's UUID
	* @param groupId the primary key of the group
	* @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	@Override
	public com.liferay.message.boards.model.MBMessage fetchMBMessageByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _mbMessageLocalService.fetchMBMessageByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbMessageLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _mbMessageLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _mbMessageLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the message-boards message with the primary key.
	*
	* @param messageId the primary key of the message-boards message
	* @return the message-boards message
	* @throws PortalException if a message-boards message with the primary key could not be found
	*/
	@Override
	public com.liferay.message.boards.model.MBMessage getMBMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageLocalService.getMBMessage(messageId);
	}

	/**
	* Returns the message-boards message matching the UUID and group.
	*
	* @param uuid the message-boards message's UUID
	* @param groupId the primary key of the group
	* @return the matching message-boards message
	* @throws PortalException if a matching message-boards message could not be found
	*/
	@Override
	public com.liferay.message.boards.model.MBMessage getMBMessageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageLocalService.getMBMessageByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the message-boards messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.message.boards.model.impl.MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of message-boards messages
	*/
	@Override
	public java.util.List<com.liferay.message.boards.model.MBMessage> getMBMessages(
		int start, int end) {
		return _mbMessageLocalService.getMBMessages(start, end);
	}

	/**
	* Returns all the message-boards messages matching the UUID and company.
	*
	* @param uuid the UUID of the message-boards messages
	* @param companyId the primary key of the company
	* @return the matching message-boards messages, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.message.boards.model.MBMessage> getMBMessagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _mbMessageLocalService.getMBMessagesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of message-boards messages matching the UUID and company.
	*
	* @param uuid the UUID of the message-boards messages
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching message-boards messages, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.message.boards.model.MBMessage> getMBMessagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.message.boards.model.MBMessage> orderByComparator) {
		return _mbMessageLocalService.getMBMessagesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of message-boards messages.
	*
	* @return the number of message-boards messages
	*/
	@Override
	public int getMBMessagesCount() {
		return _mbMessageLocalService.getMBMessagesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mbMessageLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the message-boards message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message
	* @return the message-boards message that was updated
	*/
	@Override
	public com.liferay.message.boards.model.MBMessage updateMBMessage(
		com.liferay.message.boards.model.MBMessage mbMessage) {
		return _mbMessageLocalService.updateMBMessage(mbMessage);
	}

	@Override
	public MBMessageLocalService getWrappedService() {
		return _mbMessageLocalService;
	}

	@Override
	public void setWrappedService(MBMessageLocalService mbMessageLocalService) {
		_mbMessageLocalService = mbMessageLocalService;
	}

	private MBMessageLocalService _mbMessageLocalService;
}