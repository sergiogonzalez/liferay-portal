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

package com.liferay.portal.security.membershippolicy;

/**
 * @author Roberto Díaz
 */
public class MembershipPolicyKeys {

	public static final String MEMBERSHIP_POLICY_TYPE = "membershipPolicyType";

	// ORGANIZATION MEMBERSHIP POLICY KEYS

	public static final int NO_ORGANIZATION_MEMBERSHIP_POLICY = 0;

	// SITE MEMBERSHIP POLICY KEYS

	public static final int ONLY_ALLOW_PARENT_SITE_MEMBERSHIPS = 2;

	public static final int INHERIT_PARENT_SITE_MEMBERSHIPS = 1;

	public static final int INDEPENDENT_SITE_MEMBERSHIPS = 0;

	// ROLE MEMBERSHIP POLICY KEYS

	public static final int NO_ROLE_MEMBERSHIP_POLICY = 0;

	// USER GROUP MEMBERSHIP POLICY KEYS

	public static final int NO_USER_GROUP_MEMBERSHIP_POLICY = 0;



}
