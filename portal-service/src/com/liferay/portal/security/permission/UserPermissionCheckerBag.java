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

package com.liferay.portal.security.permission;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;

import java.io.Serializable;

import java.util.List;
import java.util.Set;

/**
 * @author László Csontos
 */
@ProviderType
public interface UserPermissionCheckerBag extends Serializable {

	public List<Group> getGroups();

	public Set<Role> getRoles();

	public Set<Group> getUserGroups();

	public long getUserId();

	public Set<Group> getUserOrgGroups();

	public List<Organization> getUserOrgs();

	public List<Group> getUserUserGroupGroups();

	public boolean hasRole(Role role);

}