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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.documentlibrary.FileExtensionException;

/**
 * @author Adolfo Pérez
 */
public class DefaultFileNameAndExtensionValidator implements FileNameValidator {

	public DefaultFileNameAndExtensionValidator() {
		this(DLValidatorUtil.getDefaultFileNameValidator());
	}

	public DefaultFileNameAndExtensionValidator(
		FileNameValidator fileNameValidator) {

		_fileNameValidator = fileNameValidator;
	}

	@Override
	public void validate(String fileName)
		throws PortalException, SystemException {

		FileNameValidator validator = getFileNameValidator();

		validator.validate(fileName);

		boolean validFileExtension = false;

		String[] fileExtensions = PropsUtil.getArray(
			PropsKeys.DL_FILE_EXTENSIONS);

		for (String fileExtension : fileExtensions) {
			if (StringPool.STAR.equals(fileExtension) ||
				StringUtil.endsWith(fileName, fileExtension)) {

				validFileExtension = true;

				break;
			}
		}

		if (!validFileExtension) {
			throw new FileExtensionException(fileName);
		}
	}

	protected FileNameValidator getFileNameValidator() {
		return _fileNameValidator;
	}

	private final FileNameValidator _fileNameValidator;

}