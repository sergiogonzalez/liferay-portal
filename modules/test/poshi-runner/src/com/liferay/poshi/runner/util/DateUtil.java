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

package com.liferay.poshi.runner.util;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class DateUtil {

	public static String getCurrentDate(String pattern, Locale locale) {
		return getDate(new Date(), pattern, locale);
	}

	public static String getDate(Date date, String pattern, Locale locale) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			pattern, locale);

		return simpleDateFormat.format(date);
	}

}