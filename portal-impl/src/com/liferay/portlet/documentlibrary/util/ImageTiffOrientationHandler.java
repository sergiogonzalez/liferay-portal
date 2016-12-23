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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TIFF;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.WriteOutContentHandler;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * @author Roberto Díaz
 */
public class ImageTiffOrientationHandler {

	public static final String ORIENTATION_VALUE_HORIZONTAL_NORMAL = "1";

	public static final String ORIENTATION_VALUE_MIRROR_HORIZONTAL = "2";

	public static final String
		ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_90_CW = "7";

	public static final String
		ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_270_CW = "5";

	public static final String ORIENTATION_VALUE_MIRROR_VERTICAL = "4";

	public static final String ORIENTATION_VALUE_ROTATE_90_CW = "6";

	public static final String ORIENTATION_VALUE_ROTATE_180 = "3";

	public static final String ORIENTATION_VALUE_ROTATE_270_CW = "8";

	public boolean hasRotation(FileVersion fileVersion) throws PortalException {
		if (Validator.isNull(getOrientationValue(fileVersion))) {
			return false;
		}

		return true;
	}

	public RenderedImage transform(
			FileVersion fileVersion, RenderedImage renderedImage)
		throws PortalException {

		String orientationValue = getOrientationValue(fileVersion);

		if (Validator.isNull(orientationValue) ||
			orientationValue.equals(ORIENTATION_VALUE_HORIZONTAL_NORMAL)) {

			return renderedImage;
		}
		else if (orientationValue.equals(ORIENTATION_VALUE_MIRROR_HORIZONTAL)) {
			return flip(renderedImage);
		}
		else if (orientationValue.equals(
					ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_90_CW)) {

			return flip(rotate(renderedImage, 90));
		}
		else if (orientationValue.equals(
					ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_270_CW)) {

			return flip(rotate(renderedImage, 270));
		}
		else if (orientationValue.equals(ORIENTATION_VALUE_MIRROR_VERTICAL)) {
			return flip(rotate(renderedImage, 180));
		}
		else if (orientationValue.equals(ORIENTATION_VALUE_ROTATE_90_CW)) {
			return rotate(renderedImage, 90);
		}
		else if (orientationValue.equals(ORIENTATION_VALUE_ROTATE_180)) {
			return rotate(renderedImage, 180);
		}
		else if (orientationValue.equals(ORIENTATION_VALUE_ROTATE_270_CW)) {
			return rotate(renderedImage, 270);
		}

		return renderedImage;
	}

	protected RenderedImage flip(RenderedImage renderedImage) {
		BufferedImage image = ImageToolUtil.getBufferedImage(renderedImage);

		int height = image.getHeight();

		AffineTransform affineTransform = AffineTransform.getScaleInstance(
			1.0, -1.0);

		affineTransform.translate(0, -height);

		AffineTransformOp affineTransformOp = new AffineTransformOp(
			affineTransform, null);

		return affineTransformOp.filter(image, null);
	}

	protected String getOrientationValue(FileVersion fileVersion)
		throws PortalException {

		try {
			Metadata metadata = new Metadata();

			ContentHandler contentHandler = new WriteOutContentHandler(
				new DummyWriter());

			Parser parser = new AutoDetectParser();

			parser.parse(
				fileVersion.getContentStream(false), contentHandler, metadata,
				new ParseContext());

			return metadata.get(TIFF.ORIENTATION.getName());
		}
		catch (IOException | SAXException | TikaException e) {
			_log.error(e, e);
		}

		return null;
	}

	protected RenderedImage rotate(RenderedImage renderedImage, int degrees) {
		BufferedImage image = ImageToolUtil.getBufferedImage(renderedImage);

		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage processedImage = new BufferedImage(
			height, width, BufferedImage.TYPE_INT_RGB);

		AffineTransform affineTransform = new AffineTransform();

		affineTransform.translate(height / 2, width / 2);
		affineTransform.rotate(Math.toRadians(degrees));
		affineTransform.translate(width / (-2), height / (-2));

		Graphics2D g = processedImage.createGraphics();

		g.drawImage(image, affineTransform, null);

		g.dispose();

		return processedImage;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageTiffOrientationHandler.class);

}