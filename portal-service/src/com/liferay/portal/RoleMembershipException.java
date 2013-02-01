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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;

import java.util.List;

/**
 * @author Roberto Díaz
 */
public class RoleMembershipException extends PortalException {

	public RoleMembershipException(
		int type, List<Role> errorRoles, List<User> errorUsers) {
		_errorUsers = errorUsers;
		_errorRoles = errorRoles;
		_type = type;
	}

	public List<Role> getErrorRoles() {
		return _errorRoles;
	}

	public List<User> getErrorUsers() {
		return _errorUsers;
	}

	public int getType() {
		return _type;
	}

	private List<Role> _errorRoles;
	private List<User> _errorUsers;
	private int _type;

}