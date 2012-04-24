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

package com.liferay.portal.security;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class PortalSecurityManager {

	/**
	 * Temporary singleton.
	 */
	public static PortalSecurityManager getInstance() {
		if (_instance == null) {
			_instance = new PortalSecurityManager();
		}
		return _instance;
	}

	/**
	 * Marks thread to be remote. Needed to be called by any API
	 * to turn on the access check.
	 */
	public void setRemoteAccess() {
		REMOTE_FLAG.set(Boolean.TRUE);
	}

	/**
	 * Checks is some method is allowed to be run.
	 * Throws {@link SecurityException} if access is denied.
	 * If method is checked, current thread will be marked as
	 * local, to prevent further authentication.
	 */
	public void checkAccess(Method method) {
		Boolean isRemote = REMOTE_FLAG.get();

		if (isRemote == null || !isRemote.booleanValue()) {
			System.out.println("---> local call, not auth for: " + method);
			return;
		}

		System.out.println("---> remote call, check method: " + method);

		REMOTE_FLAG.set(Boolean.FALSE);
	}

	private static PortalSecurityManager _instance;

	private static final ThreadLocal<Boolean> REMOTE_FLAG
		= new ThreadLocal<Boolean>() {

		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
         }
	};

}