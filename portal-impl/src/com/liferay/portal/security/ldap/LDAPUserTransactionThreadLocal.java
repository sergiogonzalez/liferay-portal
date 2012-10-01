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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.util.InitialThreadLocal;

/**
 * @author Edward Han
 */
public class LDAPUserTransactionThreadLocal {

	public static boolean isOriginatesFromLDAP() {
		return _originatesFromLDAP.get();
	}

	public static void setOriginatesFromLDAP(boolean originatesFromLDAP) {
		_originatesFromLDAP.set(originatesFromLDAP);
	}

	private static ThreadLocal<Boolean> _originatesFromLDAP =
		new InitialThreadLocal<Boolean>(
			LDAPUserTransactionThreadLocal.class + "._originatesFromLDAP",
			false);

}