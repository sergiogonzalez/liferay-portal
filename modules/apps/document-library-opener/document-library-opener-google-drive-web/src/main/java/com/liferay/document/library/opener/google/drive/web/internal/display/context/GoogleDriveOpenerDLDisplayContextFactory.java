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

package com.liferay.document.library.opener.google.drive.web.internal.display.context;

import com.liferay.document.library.display.context.BaseDLDisplayContextFactory;
import com.liferay.document.library.display.context.DLDisplayContextFactory;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.opener.google.drive.service.GoogleDriveManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = DLDisplayContextFactory.class)
public class GoogleDriveOpenerDLDisplayContextFactory
	extends BaseDLDisplayContextFactory {

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileShortcut fileShortcut) {

		try {
			return getDLViewFileVersionDisplayContext(
				parentDLViewFileVersionDisplayContext, request, response,
				fileShortcut.getFileVersion());
		}
		catch (PortalException pe) {
			throw new SystemException(
				StringBundler.concat(
					"Unable to build ",
					GoogleDriveOpenerDLViewFileVersionDisplayContext.class.
						getSimpleName(),
					" for shortcut ", fileShortcut.getPrimaryKey()),
				pe);
		}
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLViewFileVersionDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		return new GoogleDriveOpenerDLViewFileVersionDisplayContext(
			parentDLViewFileVersionDisplayContext, request, response,
			fileVersion, key -> _translateKey(request, key),
			_googleDriveManager);
	}

	private String _translateKey(HttpServletRequest request, String key) {
		return _language.get(
			_resourceBundleLoader.loadResourceBundle(
				_portal.getLocale(request)),
			key);
	}

	@Reference
	private GoogleDriveManager _googleDriveManager;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.opener.google.drive.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}