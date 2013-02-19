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

package com.liferay.httpservice.ws.json;

import com.liferay.portal.kernel.jsonwebservice.BaseJSONWebServiceConfigurator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.net.URL;

import java.util.Collection;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class JSONWebServiceConfiguratorImpl
	extends BaseJSONWebServiceConfigurator {

	@Override
	public void configure() {
		ServletContext servletContext = getServletContext();

		Bundle bundle = (Bundle)servletContext.getAttribute(
			WebKeys.OSGI_BUNDLE);

		String path = StringPool.SLASH;

		URL url = bundle.getEntry("WEB-INF/service.xml");

		if (url != null) {
			try {
				Document document = SAXReaderUtil.read(url.openStream());

				Element rootElement = document.getRootElement();

				String packagePath = rootElement.attributeValue("package-path");

				if (Validator.isNotNull(packagePath)) {
					path += StringUtil.replace(
						packagePath, StringPool.PERIOD, StringPool.SLASH);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		Collection<String> classPaths = bundleWiring.listResources(
			path, "*.class", BundleWiring.LISTRESOURCES_RECURSE);

		registerClasses(classPaths);
	}

	protected void registerClasses(Collection<String> classPaths) {
		if ((classPaths == null) || classPaths.isEmpty()) {
			return;
		}

		ClassLoader classLoader = getClassLoader();

		for (String classPath : classPaths) {
			URL url = classLoader.getResource(classPath);

			String className = StringUtil.replace(
				url.getPath(), new String[] {StringPool.SLASH, ".class"},
				new String[] {StringPool.PERIOD, StringPool.BLANK});

			if (className.startsWith(StringPool.PERIOD)) {
				className = className.substring(1);
			}

			try {
				registerClass(className, url.openStream());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceConfiguratorImpl.class);

}