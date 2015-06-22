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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link UserGroupGroupRoleLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRoleLocalService
 * @generated
 */
@ProviderType
public class UserGroupGroupRoleLocalServiceWrapper
	implements UserGroupGroupRoleLocalService,
		ServiceWrapper<UserGroupGroupRoleLocalService> {
	public UserGroupGroupRoleLocalServiceWrapper(
		UserGroupGroupRoleLocalService userGroupGroupRoleLocalService) {
		_userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
	}

	/**
	* Adds the user group group role to the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRole the user group group role
	* @return the user group group role that was added
	*/
	@Override
	public com.liferay.portal.model.UserGroupGroupRole addUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole) {
		return _userGroupGroupRoleLocalService.addUserGroupGroupRole(userGroupGroupRole);
	}

	@Override
	public void addUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds) {
		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(userGroupId,
			groupId, roleIds);
	}

	@Override
	public void addUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId) {
		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(userGroupIds,
			groupId, roleId);
	}

	/**
	* Creates a new user group group role with the primary key. Does not add the user group group role to the database.
	*
	* @param userGroupGroupRolePK the primary key for the new user group group role
	* @return the new user group group role
	*/
	@Override
	public com.liferay.portal.model.UserGroupGroupRole createUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK) {
		return _userGroupGroupRoleLocalService.createUserGroupGroupRole(userGroupGroupRolePK);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupGroupRoleLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the user group group role from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRole the user group group role
	* @return the user group group role that was removed
	*/
	@Override
	public com.liferay.portal.model.UserGroupGroupRole deleteUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole) {
		return _userGroupGroupRoleLocalService.deleteUserGroupGroupRole(userGroupGroupRole);
	}

	/**
	* Deletes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role
	* @return the user group group role that was removed
	* @throws PortalException if a user group group role with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.UserGroupGroupRole deleteUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupGroupRoleLocalService.deleteUserGroupGroupRole(userGroupGroupRolePK);
	}

	@Override
	public void deleteUserGroupGroupRoles(long userGroupId, long groupId,
		long[] roleIds) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(userGroupId,
			groupId, roleIds);
	}

	@Override
	public void deleteUserGroupGroupRoles(long userGroupId, long[] groupIds) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(userGroupId,
			groupIds);
	}

	@Override
	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(userGroupIds,
			groupId);
	}

	@Override
	public void deleteUserGroupGroupRoles(long[] userGroupIds, long groupId,
		long roleId) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRoles(userGroupIds,
			groupId, roleId);
	}

	@Override
	public void deleteUserGroupGroupRolesByGroupId(long groupId) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByGroupId(groupId);
	}

	@Override
	public void deleteUserGroupGroupRolesByRoleId(long roleId) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByRoleId(roleId);
	}

	@Override
	public void deleteUserGroupGroupRolesByUserGroupId(long userGroupId) {
		_userGroupGroupRoleLocalService.deleteUserGroupGroupRolesByUserGroupId(userGroupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _userGroupGroupRoleLocalService.dynamicQuery();
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
		return _userGroupGroupRoleLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _userGroupGroupRoleLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _userGroupGroupRoleLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _userGroupGroupRoleLocalService.dynamicQueryCount(dynamicQuery);
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
		return _userGroupGroupRoleLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.UserGroupGroupRole fetchUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK) {
		return _userGroupGroupRoleLocalService.fetchUserGroupGroupRole(userGroupGroupRolePK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _userGroupGroupRoleLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _userGroupGroupRoleLocalService.getBeanIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupGroupRoleLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the user group group role with the primary key.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role
	* @return the user group group role
	* @throws PortalException if a user group group role with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.UserGroupGroupRole getUserGroupGroupRole(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRole(userGroupGroupRolePK);
	}

	/**
	* Returns a range of all the user group group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @return the range of user group group roles
	*/
	@Override
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		int start, int end) {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRoles(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId) {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRoles(userGroupId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRoles(
		long userGroupId, long groupId) {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRoles(userGroupId,
			groupId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRolesByGroupAndRole(
		long groupId, long roleId) {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRolesByGroupAndRole(groupId,
			roleId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> getUserGroupGroupRolesByUser(
		long userId) {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRolesByUser(userId);
	}

	/**
	* Returns the number of user group group roles.
	*
	* @return the number of user group group roles
	*/
	@Override
	public int getUserGroupGroupRolesCount() {
		return _userGroupGroupRoleLocalService.getUserGroupGroupRolesCount();
	}

	@Override
	public boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		long roleId) {
		return _userGroupGroupRoleLocalService.hasUserGroupGroupRole(userGroupId,
			groupId, roleId);
	}

	@Override
	public boolean hasUserGroupGroupRole(long userGroupId, long groupId,
		java.lang.String roleName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userGroupGroupRoleLocalService.hasUserGroupGroupRole(userGroupId,
			groupId, roleName);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_userGroupGroupRoleLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the user group group role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRole the user group group role
	* @return the user group group role that was updated
	*/
	@Override
	public com.liferay.portal.model.UserGroupGroupRole updateUserGroupGroupRole(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole) {
		return _userGroupGroupRoleLocalService.updateUserGroupGroupRole(userGroupGroupRole);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public UserGroupGroupRoleLocalService getWrappedUserGroupGroupRoleLocalService() {
		return _userGroupGroupRoleLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedUserGroupGroupRoleLocalService(
		UserGroupGroupRoleLocalService userGroupGroupRoleLocalService) {
		_userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
	}

	@Override
	public UserGroupGroupRoleLocalService getWrappedService() {
		return _userGroupGroupRoleLocalService;
	}

	@Override
	public void setWrappedService(
		UserGroupGroupRoleLocalService userGroupGroupRoleLocalService) {
		_userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
	}

	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;
}