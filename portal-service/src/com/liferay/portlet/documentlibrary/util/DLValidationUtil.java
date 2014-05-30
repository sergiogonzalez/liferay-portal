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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class DLValidationUtil {

	public static DLValidation getDLValidation() {
		PortalRuntimePermission.checkGetBeanProperty(DLValidationUtil.class);

		return _dlValidation;
	}

	public static boolean isValidName(String name) {
		return getDLValidation().isValidName(name);
	}

	public static final void validateDirectoryName(String directoryName)
		throws FolderNameException {

		getDLValidation().validateDirectoryName(directoryName);
	}

	public static void validateFileExtension(String fileName)
		throws FileExtensionException, SystemException {

		getDLValidation().validateFileExtension(fileName);
	}

	public static void validateFileName(String fileName)
		throws FileNameException {

		getDLValidation().validateFileName(fileName);
	}

	public static void validateFileSize(String fileName, byte[] bytes)
		throws FileSizeException, SystemException {

		getDLValidation().validateFileSize(fileName, bytes);
	}

	public static void validateFileSize(String fileName, File file)
		throws FileSizeException, SystemException {

		getDLValidation().validateFileSize(fileName, file);
	}

	public static void validateFileSize(String fileName, InputStream is)
		throws FileSizeException, SystemException {

		getDLValidation().validateFileSize(fileName, is);
	}

	public static void validateSourceFileExtension(
			String fileExtension, String sourceFileName)
		throws SourceFileNameException {

		getDLValidation().validateSourceFileExtension(
			fileExtension, sourceFileName);
	}

	public static void validateVersionLabel(String versionLabel)
		throws InvalidFileVersionException {

		getDLValidation().validateVersionLabel(versionLabel);
	}

	public void setDLValidation(DLValidation dlValidation) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_dlValidation = dlValidation;
	}

	private static DLValidation _dlValidation;

}