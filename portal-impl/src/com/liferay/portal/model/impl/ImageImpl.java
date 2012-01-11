/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageImpl extends ImageBaseImpl {

	public ImageImpl() {
	}

	public byte[] getTextObj() {
		if (_textObj != null) {
			return _textObj;
		}

		long imageId = getImageId();

		try {
			DLFileEntry dlFileEntry =
				DLFileEntryLocalServiceUtil.fetchFileEntryByAnyImageId(imageId);

			InputStream is = null;

			if ((dlFileEntry != null) &&
				(dlFileEntry.getLargeImageId() == imageId)) {

				is = DLStoreUtil.getFileAsStream(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName());
			}
			else {
				is = DLStoreUtil.getFileAsStream(
					_DEFAULT_COMPANY_ID, _DEFAULT_REPOSITORY_ID,
					getFileName());
			}

			byte[] bytes = FileUtil.getBytes(is);

			_textObj = bytes;
		}
		catch (Exception e) {
			_log.error("Error reading image " + imageId, e);
		}

		return _textObj;
	}

	public void setTextObj(byte[] textObj) {
		_textObj = textObj;

		super.setText(Base64.objectToString(textObj));
	}

	protected String getFileName() {
		return getImageId() + StringPool.PERIOD + getType();
	}

	private byte[] _textObj;

	private final long _DEFAULT_COMPANY_ID = 0;

	private final long _DEFAULT_REPOSITORY_ID = 0;

	private static Log _log = LogFactoryUtil.getLog(ImageImpl.class);

}