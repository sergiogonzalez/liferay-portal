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

package com.liferay.blogs.web.image.selector;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.blogs.CoverImageSizeException;

import javax.portlet.PortletRequest;

/**
 * @author Sergio González
 * @author Adolfo Pérez
 */
public class EditorImageSelectorUploadHandler
	extends BaseBlogsImageSelectorUploadHandler {

	@Override
	public void validateFile(
			PortletRequest portletRequest, String fileName, String contentType,
			long size)
		throws PortalException {

		if (size > getMaxFileSize(portletRequest)) {
			throw new CoverImageSizeException();
		}

		super.validateFile(portletRequest, fileName, contentType, size);
	}

	@Override
	protected long getMaxFileSize(PortletRequest portletRequest) {
		return PrefsPropsUtil.getLong(
			PropsKeys.BLOGS_IMAGE_ALLOY_EDITOR_MAX_SIZE);
	}

}