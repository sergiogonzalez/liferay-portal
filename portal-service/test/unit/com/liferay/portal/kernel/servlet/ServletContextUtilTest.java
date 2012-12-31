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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.servlet.ServletContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockServletContext;

/**
 * @author Laszlo Csontos
 */
@RunWith(PowerMockRunner.class)
public class ServletContextUtilTest extends PowerMockito {

	@Test
	public void testGetRootURIWithEmptyPath() throws Exception {
		getRootURI(StringPool.BLANK, getURI(StringPool.SLASH));
	}

	@Test(expected = MalformedURLException.class)
	public void testGetRootURIWithInvalidCharacters() throws Exception {
		getRootURI(":?#[]/@", null);
	}

	@Test
	public void testGetRootURIWithReservedCharacters() throws Exception {
		String path =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_" +
				".~";

		getRootURI(path, getURI(path));
	}

	@Test
	public void testGetRootURIWithUnreservedCharacters() throws Exception {
		String path = "/!$&'()*+,;= ";

		getRootURI(path, getURI(path));
	}

	protected void getRootURI(String path, URI uri) throws Exception {
		ServletContext servletContext = getServletContext(path);

		URI rootURI = ServletContextUtil.getRootURI(servletContext);

		Assert.assertEquals(uri, rootURI);
		Assert.assertEquals(
			uri, servletContext.getAttribute(ServletContextUtil.URI_ATTRIBUTE));
	}

	protected ServletContext getServletContext(final String path) {
		return new MockServletContext() {

			@Override
			public URL getResource(String resourcePath)
				throws MalformedURLException {

				URL url = new URL("file:" + path + resourcePath);

				return url;
			}

		};
	}

	protected URI getURI(String path) {
		URI uri = null;

		try {
			uri = new URI("file", path, null);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return uri;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServletContextUtilTest.class);

}