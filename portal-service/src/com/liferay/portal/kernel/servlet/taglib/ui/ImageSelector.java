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

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;

import java.awt.image.RenderedImage;

import java.io.IOException;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public class ImageSelector {

	public ImageSelector(long imageId) {
		_imageId = imageId;

		_imageCropRegion = null;
		_imageURL = null;
	}

	public ImageSelector(long imageId, String imageCropRegion) {
		_imageId = imageId;
		_imageCropRegion = imageCropRegion;

		_imageURL = null;
	}

	public ImageSelector(
		long imageId, String imageURL, String imageCropRegion) {

		_imageId = imageId;
		_imageURL = imageURL;
		_imageCropRegion = imageCropRegion;
	}

	public ImageSelector(String imageURL, String imageCropRegion) {
		_imageURL = imageURL;
		_imageCropRegion = imageCropRegion;

		_imageId = 0;
	}

	public byte[] getImageBytes() throws IOException, PortalException {
		if (_imageId == 0) {
			return null;
		}

		byte[] bytes = null;

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			_imageId);

		if (Validator.isNotNull(_imageCropRegion)) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				_imageCropRegion);

			int height = jsonObject.getInt("height");
			int width = jsonObject.getInt("width");
			int x = jsonObject.getInt("x");
			int y = jsonObject.getInt("y");

			if ((x > 0) || (y > 0) || (width > 0) || (height > 0)) {
				ImageBag imageBag = ImageToolUtil.read(
					fileEntry.getContentStream());

				RenderedImage renderedImage = imageBag.getRenderedImage();

				renderedImage = ImageToolUtil.crop(
					renderedImage, height, width, x, y);

				bytes = ImageToolUtil.getBytes(
					renderedImage, imageBag.getType());
			}
		}

		if (bytes == null) {
			bytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		return bytes;
	}

	public String getImageCropRegion() {
		return _imageCropRegion;
	}

	public long getImageId() {
		return _imageId;
	}

	public String getImageURL() {
		return _imageURL;
	}

	public String getMimeType() throws PortalException {
		if (_imageId == 0) {
			return null;
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			_imageId);

		return fileEntry.getMimeType();
	}

	public String getTitle() throws PortalException {
		if (_imageId == 0) {
			return null;
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			_imageId);

		return fileEntry.getTitle();
	}

	public boolean isRemoveSmallImage() {
		if ((_imageId == 0) && Validator.isNull(_imageURL)) {
			return true;
		}

		return false;
	}

	private final String _imageCropRegion;
	private final long _imageId;
	private final String _imageURL;

}