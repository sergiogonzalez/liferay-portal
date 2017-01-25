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

package com.liferay.portal.kernel.servlet.filters.compoundsessionid;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-18587.
 * </p>
 *
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class CompoundSessionIdSplitterUtil {

	public static String getSessionIdDelimiter() {
		return _SESSION_ID_DELIMITER;
	}

	public static boolean hasSessionDelimiter() {
		if (_SESSION_ID_DELIMITER == null) {
			return false;
		}

		return true;
	}

	public static String parseSessionId(String sessionId) {
		if (_SESSION_ID_DELIMITER == null) {
			return sessionId;
		}

		int pos = sessionId.indexOf(_SESSION_ID_DELIMITER);

		if (pos == -1) {
			return sessionId;
		}

		return sessionId.substring(0, pos);
	}

	private static final String _SESSION_ID_DELIMITER;

	static {
		String sessionIdDelimiter = PropsUtil.get(
			PropsKeys.SESSION_ID_DELIMITER);

		if (Validator.isNull(sessionIdDelimiter)) {
			sessionIdDelimiter = PropsUtil.get(
				"session.id." + ServerDetector.getServerId() + ".delimiter");
		}

		if (Validator.isNotNull(sessionIdDelimiter)) {
			_SESSION_ID_DELIMITER = sessionIdDelimiter;
		}
		else {
			_SESSION_ID_DELIMITER = null;
		}
	}

}