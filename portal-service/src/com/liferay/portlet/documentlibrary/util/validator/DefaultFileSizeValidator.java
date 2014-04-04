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
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.documentlibrary.FileSizeException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class DefaultFileSizeValidator implements FileSizeValidator {

	@Override
	public void validateSize(String fileName, byte[] bytes)
		throws PortalException, SystemException {

		if ((bytes == null) || !isValidSize(bytes.length)) {
			throw new FileSizeException(fileName);
		}
	}

	@Override
	public void validateSize(String fileName, File file)
		throws PortalException, SystemException {

		if ((file == null) || !isValidSize(file.length())) {
			throw new FileSizeException(fileName);
		}
	}

	@Override
	public void validateSize(String fileName, InputStream is)
		throws PortalException, SystemException {

		try {
			if ((is == null) || !isValidSize(is.available())) {
				throw new FileSizeException(fileName);
			}
		}
		catch (IOException ioe) {
			throw new FileSizeException(ioe.getMessage());
		}
	}

	protected boolean isValidSize(long size) throws SystemException {
		if ((PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0) &&
			(size > PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE))) {

			return false;
		}
		else {
			return true;
		}
	}

}