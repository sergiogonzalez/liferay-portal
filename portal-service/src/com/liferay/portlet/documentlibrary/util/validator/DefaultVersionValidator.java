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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.util.DLUtil;

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

		if (!DLUtil.isValidVersion(versionLabel)) {
			throw new InvalidFileVersionException();
		}
	}

}