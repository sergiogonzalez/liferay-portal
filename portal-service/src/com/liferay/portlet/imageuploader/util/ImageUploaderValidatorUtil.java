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

package com.liferay.portlet.imageuploader.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.documentlibrary.FileSizeException;

/**
 * @author Mohit Soni
 */
public class ImageUploaderValidatorUtil {

	public static ImageUploaderValidator getImageUploaderValidator() {
		PortalRuntimePermission.checkGetBeanProperty(
			ImageUploaderValidatorUtil.class);

		return _imageUploaderValidator;
	}

	public static void validateFileSize(
			long imageId, String imageType, byte[] bytes)
		throws FileSizeException {

		getImageUploaderValidator().validateFileSize(imageId, imageType, bytes);
	}

	public static void validateFileSize(String fileName, byte[] bytes)
		throws FileSizeException {

		getImageUploaderValidator().validateFileSize(fileName, bytes);
	}

	public static void validateFileSize(String fileName, long size)
		throws FileSizeException {

		getImageUploaderValidator().validateFileSize(fileName, size);
	}

	public void setImageUploaderValidator(
		ImageUploaderValidator imageUploaderValidator) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_imageUploaderValidator = imageUploaderValidator;
	}

	private static ImageUploaderValidator _imageUploaderValidator;

}