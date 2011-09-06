/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.support.tomcat.connector;

import org.apache.catalina.connector.Request;

/**
 * @author Minhchau Dang
 */
public class PortalRequest extends Request {

	/*@Override
	protected void configureSessionCookie(Cookie cookie) {
		super.configureSessionCookie(cookie);

		String host = getServerName();

		String domain = _getDomain(host);

		if (domain != null) {
			cookie.setDomain(domain);
		}
	}

	private String _getDomain(String host) {

		// See LEP-4602 and LEP-4645.

		if (host == null) {
			return null;
		}

		int x = host.lastIndexOf(StringPool.PERIOD);

		if (x <= 0) {
			return null;
		}

		int y = host.lastIndexOf(StringPool.PERIOD, x - 1);

		if (y <= 0) {
			return StringPool.PERIOD + host;
		}

		int z = host.lastIndexOf(StringPool.PERIOD, y - 1);

		String domain = null;

		if (z <= 0) {
			domain = host.substring(y);
		}
		else {
			domain = host.substring(z);
		}

		return domain;
	}*/

}