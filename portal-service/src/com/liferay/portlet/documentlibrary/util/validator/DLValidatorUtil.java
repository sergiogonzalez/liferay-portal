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

/**
 * @author Adolfo Pérez
 */
public class DLValidatorUtil {

	public static DirectoryNameValidator getDefaultDirectoryNameValidator() {
		return _directoryNameValidator;
	}

	public static FileExtensionValidator getDefaultFileExtensionValidator() {
		return _fileExtensionValidator;
	}

	public static FileNameValidator getDefaultFileNameAndExtensionValidator() {
		return _fileNameAndExtensionValidator;
	}

	public static FileNameValidator getDefaultFileNameValidator() {
		return _fileNameValidator;
	}

	public static FileNameValidator getDefaultFileNameValidator(
		boolean validateExtension) {

		if (validateExtension) {
			return getDefaultFileNameValidator();
		}
		else {
			return getDefaultFileNameAndExtensionValidator();
		}
	}

	public static FileSizeValidator getDefaultFileSizeValidator() {
		return _fileSizeValidator;
	}

	public static VersionValidator getDefaultVersionValidator() {
		return _versionValidator;
	}

	private static final DirectoryNameValidator _directoryNameValidator =
		new DefaultDirectoryNameValidator();
	private static final FileExtensionValidator _fileExtensionValidator =
		new DefaultExtensionValidator();
	private static final FileNameValidator _fileNameAndExtensionValidator =
		new DefaultFileNameAndExtensionValidator();
	private static final FileNameValidator _fileNameValidator =
		new DefaultFileNameValidator();
	private static final FileSizeValidator _fileSizeValidator =
		new DefaultFileSizeValidator();
	private static final VersionValidator _versionValidator =
		new DefaultVersionValidator();

}