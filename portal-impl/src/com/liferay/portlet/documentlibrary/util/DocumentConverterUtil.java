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

import com.liferay.document.library.kernel.util.DocumentConverter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Mauro Mariuzzo
 */
public class DocumentConverterUtil {

	public static DocumentConverter getConverter(
		String sourceExtension, String targetExtension) {

		for (Object service : _instance._serviceTracker.getServices()) {
			DocumentConverter converter = (DocumentConverter)service;

			if (converter.canConvert(sourceExtension, targetExtension)) {
				return converter;
			}
		}

		return null;
	}

	public static boolean hasConverter(
		String sourceExtension, String targetExtension) {

		DocumentConverter converter = getConverter(
			sourceExtension, targetExtension);

		if (converter != null) {
			return true;
		}

		return false;
	}

	public static boolean hasRegisteredConverters() {
		return !_instance._serviceTracker.isEmpty();
	}

	private DocumentConverterUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(DocumentConverter.class);

		_serviceTracker.open();
	}

	private static final DocumentConverterUtil _instance =
		new DocumentConverterUtil();

	private final ServiceTracker<DocumentConverter, DocumentConverter>
		_serviceTracker;

}