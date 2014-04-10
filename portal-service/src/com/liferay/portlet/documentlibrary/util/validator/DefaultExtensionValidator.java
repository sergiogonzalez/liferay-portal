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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * @author Adolfo Pérez
 */
public class DefaultExtensionValidator implements FileExtensionValidator {

	@Override
	public void validateExtension(
			String fileName, String fileExtension, String sourceFileName)
		throws PortalException, SystemException {

		String sourceFileExtension = FileUtil.getExtension(sourceFileName);

		boolean checkExtensions = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.DL_FILE_EXTENSIONS_STRICT_CHECK));

		if (Validator.isNotNull(sourceFileName) && checkExtensions &&
			!fileExtension.equals(sourceFileExtension)) {

			throw new SourceFileNameException(sourceFileExtension);
		}

		if (Validator.isNotNull(fileExtension)) {
			int maxLength = ModelHintsUtil.getMaxLength(
				DLFileEntry.class.getName(), "extension");

			if (fileExtension.length() > maxLength) {
				throw new FileExtensionException();
			}
		}
	}

}