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

package com.liferay.portlet.documentlibrary.util.validator;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseNameValidator implements NameValidator {

	protected boolean isValidFileName(String fileName) {
		if (Validator.isNull(fileName)) {
			return false;
		}

		String[] dlCharBlacklist = PropsUtil.getArray(
			PropsKeys.DL_CHAR_BLACKLIST);

		for (String blacklistChar : dlCharBlacklist) {
			if (fileName.contains(blacklistChar)) {
				return false;
			}
		}

		String[] dlCharLastBlacklist = PropsUtil.getArray(
			PropsKeys.DL_CHAR_LAST_BLACKLIST);

		for (String blacklistLastChar : dlCharLastBlacklist) {
			if (blacklistLastChar.startsWith(_UNICODE_PREFIX)) {
				blacklistLastChar = UnicodeFormatter.parseString(
					blacklistLastChar);
			}

			if (fileName.endsWith(blacklistLastChar)) {
				return false;
			}
		}

		String nameWithoutExtension = fileName;

		if (fileName.contains(StringPool.PERIOD)) {
			int index = fileName.lastIndexOf(StringPool.PERIOD);

			nameWithoutExtension = fileName.substring(0, index);
		}

		String[] dlNameBlacklist = PropsUtil.getArray(
			PropsKeys.DL_NAME_BLACKLIST);

		for (String blacklistName : dlNameBlacklist) {
			if (StringUtil.equalsIgnoreCase(
					nameWithoutExtension, blacklistName)) {

				return false;
			}
		}

		return true;
	}

	private static final String _UNICODE_PREFIX = "\\u";

}