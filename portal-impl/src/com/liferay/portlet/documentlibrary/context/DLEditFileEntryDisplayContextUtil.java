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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DLEditFileEntryDisplayContextUtil {

	public static DLEditFileEntryDisplayContext
	getDLEditFileEntryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				request, response, dlFileEntryType);

		return _chainDLEditFileEntryDisplayContexts(
			request, response, dlFileEntryType, dlEditFileEntryDisplayContext);
	}

	public static DLEditFileEntryDisplayContext
	getDLEditFileEntryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				request, response, fileEntry);

		return _chainDLEditFileEntryDisplayContexts(
			request, response, fileEntry, dlEditFileEntryDisplayContext);
	}

	private static DLEditFileEntryDisplayContext
	_chainDLEditFileEntryDisplayContexts(
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType,
		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext) {

		Collection<DLEditFileEntryDisplayContextFactory>
			dlEditFileEntryDisplayContextFactories =
				_getDLEditFileEntryDisplayContextFactories();

		for (DLEditFileEntryDisplayContextFactory
			dlEditFileEntryDisplayContextFactory :
			dlEditFileEntryDisplayContextFactories) {

			dlEditFileEntryDisplayContext =
				dlEditFileEntryDisplayContextFactory.
					getDLEditFileEntryDisplayContext(
						dlEditFileEntryDisplayContext, request, response,
						dlFileEntryType);
		}

		return dlEditFileEntryDisplayContext;
	}

	private static DLEditFileEntryDisplayContext
	_chainDLEditFileEntryDisplayContexts(
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry,
		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext) {

		Collection<DLEditFileEntryDisplayContextFactory>
			dlEditFileEntryDisplayContextFactories =
				_getDLEditFileEntryDisplayContextFactories();

		for (DLEditFileEntryDisplayContextFactory
			dlEditFileEntryDisplayContextFactory :
			dlEditFileEntryDisplayContextFactories) {

			dlEditFileEntryDisplayContext =
				dlEditFileEntryDisplayContextFactory.
					getDLEditFileEntryDisplayContext(
						dlEditFileEntryDisplayContext, request, response,
						fileEntry);
		}

		return dlEditFileEntryDisplayContext;
	}

	private static Collection<DLEditFileEntryDisplayContextFactory>
	_getDLEditFileEntryDisplayContextFactories() {

		try {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getServices(
				DLEditFileEntryDisplayContextFactory.class, null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}