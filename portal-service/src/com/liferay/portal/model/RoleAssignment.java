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

package com.liferay.portal.model;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jorge Ferrer
 */
public class RoleAssignment {

	public RoleAssignment(User user, Role role) {
		_user = user;
		_role = role;
	}

	public RoleAssignment(UserGroupRole userGroupRole) throws PortalException {
		_user = userGroupRole.getUser();
		_role = userGroupRole.getRole();
		_group = userGroupRole.getGroup();
	}

	public void addOrganizationAssignment(Organization organization) {
		_delegateOrganizations.add(organization);
	}

	public void addUserGroupAssignment(UserGroup userGroup) {
		_delegateUserGroups.add(userGroup);
	}

	public boolean equals(Object obj) {
		RoleAssignment ra = (RoleAssignment)obj;

		if (ra.getUser().equals(getUser()) && (ra.getRoleId() == getRoleId())) {
			return true;
		}

		return false;
	}

	public Set<Organization> getDelegateOrganizations() {
		return _delegateOrganizations;
	}

	public Set<UserGroup> getDelegateUserGroups() {
		return _delegateUserGroups;
	}

	public Set<String> getDelegateNames() {
		Set<String> delegateNames = new TreeSet<String>();

		for (Organization organization : _delegateOrganizations) {
			delegateNames.add(
				BeanPropertiesUtil.getString(organization, "name"));
		}

		for (UserGroup userGroup : _delegateUserGroups) {
			delegateNames.add(
				BeanPropertiesUtil.getString(userGroup, "name"));
		}

		return delegateNames;
	}

	public Group getGroup() {
		return _group;
	}

	public Role getRole() {
		return _role;
	}

	public long getRoleId() {
		return _role.getRoleId();
	}

	public User getUser() {
		return _user;
	}

	public boolean isDirectAssignment() {
		return _directAssignment;
	}

	public void setDirectAssignment(boolean directAssignment) {
		_directAssignment = directAssignment;
	}

	private Set<Organization> _delegateOrganizations = new HashSet();
	private Set<UserGroup> _delegateUserGroups = new HashSet();
	private boolean _directAssignment = false;
	private Group _group;
	private Role _role;
	private User _user;

}