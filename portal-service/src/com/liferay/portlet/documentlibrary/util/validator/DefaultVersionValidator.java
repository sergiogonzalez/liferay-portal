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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;

/**
 * @author Adolfo Pérez
 */
public class DefaultVersionValidator implements VersionValidator {

	@Override
	public void validateVersion(String versionLabel)
		throws PortalException, SystemException {

		if (Validator.isNull(versionLabel)) {
			return;
		}

		if (!_isValidVersion(versionLabel)) {
			throw new InvalidFileVersionException();
		}
	}

	private boolean _isValidVersion(String version) {
		if (version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {
			return true;
		}

		String[] versionParts = StringUtil.split(version, StringPool.PERIOD);

		if (versionParts.length != 2) {
			return false;
		}

		if (Validator.isNumber(versionParts[0]) &&
			Validator.isNumber(versionParts[1])) {

			return true;
		}

		return false;
	}

}