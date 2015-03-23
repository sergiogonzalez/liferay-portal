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

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;

/**
 * @author Mohit Soni
 */
public final class ImageUploaderValidatorImpl
	implements ImageUploaderValidator {

	@Override
	public void validateFileSize(long imageId, String imageType, byte[] bytes)
		throws FileSizeException {

		String fileName = getFileName(imageId, imageType);

		if (bytes == null) {
			throw new FileSizeException(fileName);
		}

		validateFileSize(fileName, bytes.length);
	}

	@Override
	public void validateFileSize(String fileName, byte[] bytes)
		throws FileSizeException {

		if (bytes == null) {
			throw new FileSizeException(fileName);
		}

		validateFileSize(fileName, bytes.length);
	}

	@Override
	public void validateFileSize(String fileName, long size)
		throws FileSizeException {

		long maxSize = PrefsPropsUtil.getLong(
			PropsKeys.IMAGE_UPLOADER_MAX_SIZE);

		if ((maxSize > 0) && (size > maxSize)) {
			throw new FileSizeException(fileName);
		}
	}

	protected String getFileName(long imageId, String type) {
		return imageId + StringPool.PERIOD + type;
	}

}