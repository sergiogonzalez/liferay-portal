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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mika Koivisto
 */
public class LiferayTemplateLoader implements TemplateLoader {

	public void closeTemplateSource(Object templateSource) {
		LiferayTemplateSource liferayTemplateSource =
			(LiferayTemplateSource)templateSource;

		liferayTemplateSource.close();
	}

	public Object findTemplateSource(String name) throws IOException {
		FreeMarkerTemplateLoader freeMarkerTemplateLoader =
			_freeMarkerTemplateLoadersMap.get(name);

		if (freeMarkerTemplateLoader != null) {
			Object templateSource = freeMarkerTemplateLoader.findTemplateSource(
				name);

			if (templateSource != null) {
				return new LiferayTemplateSource(
					freeMarkerTemplateLoader, templateSource);
			}
		}

		for (int i = 0; i < _freeMarkerTemplateLoaders.length; i++) {
			freeMarkerTemplateLoader = _freeMarkerTemplateLoaders[i];

			Object templateSource = freeMarkerTemplateLoader.findTemplateSource(
				name);

			if (templateSource != null) {
				_freeMarkerTemplateLoadersMap.put(
					name, freeMarkerTemplateLoader);

				return new LiferayTemplateSource(
					freeMarkerTemplateLoader, templateSource);
			}
		}

		return null;
	}

	public long getLastModified(Object templateSource) {
		LiferayTemplateSource liferayTemplateSource =
			(LiferayTemplateSource)templateSource;

		return liferayTemplateSource.getLastModified();
	}

	public Reader getReader(Object templateSource, String encoding)
		throws IOException {

		LiferayTemplateSource liferayTemplateSource =
			(LiferayTemplateSource)templateSource;

		return liferayTemplateSource.getReader(encoding);
	}

	public void setTemplateLoaders(
		String[] freeMarkerTemplateLoaderClassNames) {

		List<FreeMarkerTemplateLoader> freeMarkerTemplateLoaders =
			new ArrayList<FreeMarkerTemplateLoader>(
				freeMarkerTemplateLoaderClassNames.length);

		for (String freeMarkerTemplateLoaderClassName :
				freeMarkerTemplateLoaderClassNames) {

			try {
				FreeMarkerTemplateLoader freeMarkerTemplateLoader =
					(FreeMarkerTemplateLoader)InstanceFactory.newInstance(
						freeMarkerTemplateLoaderClassName);

				freeMarkerTemplateLoaders.add(freeMarkerTemplateLoader);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		_freeMarkerTemplateLoaders = freeMarkerTemplateLoaders.toArray(
			new FreeMarkerTemplateLoader[freeMarkerTemplateLoaders.size()]);
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayTemplateLoader.class);

	private FreeMarkerTemplateLoader[] _freeMarkerTemplateLoaders;
	private Map<String, FreeMarkerTemplateLoader>
		_freeMarkerTemplateLoadersMap =
			new ConcurrentHashMap<String, FreeMarkerTemplateLoader>();

}