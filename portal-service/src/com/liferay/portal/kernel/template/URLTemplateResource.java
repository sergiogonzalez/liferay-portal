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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Tina Tian
 */
public class URLTemplateResource implements TemplateResource {

	public URLTemplateResource(String templateId, URL templateURL) {
		if (templateId == null) {
			throw new IllegalArgumentException("Template ID is null");
		}

		if (templateURL == null) {
			throw new IllegalArgumentException("Template URL is null");
		}

		_templateId = templateId;
		_templateURL = templateURL;
	}

	public long getLastModified() {
		URLConnection urlConnection = null;

		try {
			urlConnection = _templateURL.openConnection();

			if (urlConnection instanceof JarURLConnection) {
				JarURLConnection jarURLConnection =
					(JarURLConnection)urlConnection;

				URL url = jarURLConnection.getJarFileURL();

				String protocol = url.getProtocol();

				if (protocol.equals("file")) {
					return new File(url.getFile()).lastModified();
				}
				else {
					urlConnection = url.openConnection();
				}
			}

			return urlConnection.getLastModified();
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to get last modified time for template " + _templateId,
				ioe);

			return 0;
		}
		finally {
			if (urlConnection != null) {
				try {
					urlConnection.getInputStream().close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	public Reader getReader() throws IOException {
		if (_templateURL == null) {
			return null;
		}

		URLConnection urlConnection = _templateURL.openConnection();

		return new InputStreamReader(
			urlConnection.getInputStream(), DEFAUT_ENCODING);
	}

	public String getTemplateId() {
		return _templateId;
	}

	private static Log _log = LogFactoryUtil.getLog(URLTemplateResource.class);

	private String _templateId;
	private URL _templateURL;

}