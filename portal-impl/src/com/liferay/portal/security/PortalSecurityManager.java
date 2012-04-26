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

import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

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
	 * Or, even better, called inside filter, on one place.
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
	public void accept(Method method) {
		Boolean isRemote = REMOTE_FLAG.get();

		if (isRemote == null || !isRemote.booleanValue()) {
			System.out.println("---> local call, not auth for: " + method);
			return;
		}

		System.out.println("---> remote call, check method: " + method);

		SecureMethodData secureMethodData = _lookupSecureData(method);

		Authentication requiredAuthentication =
			secureMethodData.getAuthentication();

		if (requiredAuthentication == Authentication.PRIVATE) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker == null || !permissionChecker.isSignedIn()) {
				throw new SecurityException(
					"Access denied, user not authenticated.");
			}
		}

		REMOTE_FLAG.set(Boolean.FALSE);
	}

	/**
	 * Lookups for method secure data.
	 */
	private SecureMethodData _lookupSecureData(Method method) {
		SecureMethodData secureMethodData = _secureMethodDataCache.get(method);

		if (secureMethodData != null) {
			return secureMethodData;
		}

		secureMethodData = new SecureMethodData();

		Secure secure = method.getAnnotation(Secure.class);

		if (secure != null) {
			secureMethodData.setAuthentication(secure.authentication());
		}

		_secureMethodDataCache.put(method, secureMethodData);

		return secureMethodData;
	}

	private static class SecureMethodData {

		public Authentication getAuthentication() {
			return _authentication;
		}

		public void setAuthentication(Authentication authentication) {
			this._authentication = authentication;
		}

		private Authentication _authentication = Authentication.PRIVATE;
	}

	private static final ThreadLocal<Boolean> REMOTE_FLAG
		= new ThreadLocal<Boolean>() {

		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	private static PortalSecurityManager _instance;

	/**
	 * Cache for varoius method data, read from annotation/configuration files.
	 */
	private Map<Method, SecureMethodData> _secureMethodDataCache =
		new HashMap<Method, SecureMethodData>();

}