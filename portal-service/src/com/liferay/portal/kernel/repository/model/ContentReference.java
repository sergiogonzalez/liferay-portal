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

package com.liferay.portal.kernel.repository.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class ContentReference {

	public static final ContentReference fromBytes(
		String sourceFileName, String extension, String mimeType,
		byte[] bytes) {

		return fromInputStream(
			sourceFileName, extension, mimeType,
			new ByteArrayInputStream(bytes), bytes.length);
	}

	public static final ContentReference fromFile(
		String sourceFileName, String extension, String mimeType, File file) {

		return new ContentReference(
			sourceFileName, extension, mimeType, file, null, 0);
	}

	public static final ContentReference fromInputStream(
		String sourceFileName, String extension, String mimeType,
		InputStream is, long size) {

		return new ContentReference(
			sourceFileName, extension, mimeType, null, is, size);
	}

	public String getExtension() {
		return _extension;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public long getSize() {
		if (_inputStream != null) {
			return _size;
		}

		if (_file != null) {
			return _file.length();
		}

		return 0;
	}

	public String getSourceFileName() {
		return _sourceFileName;
	}

	protected ContentReference(
		String sourceFileName, String extension, String mimeType, File file,
		InputStream is, long size) {

		_sourceFileName = sourceFileName;
		_extension = extension;
		_mimeType = mimeType;
		_file = file;
		_inputStream = is;
		_size = size;
	}

	private final String _extension;
	private final File _file;
	private final InputStream _inputStream;
	private final String _mimeType;
	private final long _size;
	private final String _sourceFileName;

}