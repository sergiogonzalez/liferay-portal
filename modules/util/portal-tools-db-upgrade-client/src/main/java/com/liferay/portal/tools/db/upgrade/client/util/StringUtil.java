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

package com.liferay.portal.tools.db.upgrade.client.util;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtil {

	public static String join(Iterable<?> objects, String separator) {
		StringBuilder sb = new StringBuilder();

		int i = 0;

		for (Object object : objects) {
			if (i > 0) {
				sb.append(separator);
			}

			sb.append(object);

			i++;
		}

		return sb.toString();
	}

}