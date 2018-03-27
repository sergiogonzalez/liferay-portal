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

package com.liferay.dynamic.section.taglib.internal.util;

import com.liferay.dynamic.section.taglib.DynamicSection;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class DynamicSectionUtil {

	public static StringBundler modify(
		String name, PageContext pageContext, StringBundler sb) {

		for (DynamicSection dynamicSections : _getDynamicSections(name)) {
			sb = dynamicSections.modify(sb, pageContext);
		}

		return sb;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private static List<DynamicSection> _getDynamicSections(String name) {
		BundleContext bundleContext = _bundleContext;

		List<DynamicSection> dynamicSections = new ArrayList<>();

		try {
			StringBundler sb = new StringBundler(5);

			sb.append("(&(objectClass=");
			sb.append(DynamicSection.class.getName());
			sb.append(")(name=");
			sb.append(name);
			sb.append("))");

			List<ServiceReference<DynamicSection>> serviceReferences =
				new ArrayList<>(
					bundleContext.getServiceReferences(
						DynamicSection.class, sb.toString()));

			Collections.sort(serviceReferences);

			serviceReferences.forEach(
				servicereference -> {
					dynamicSections.add(
						bundleContext.getService(servicereference));
				});
		}
		catch (InvalidSyntaxException ise) {
			_log.error("Invalid service filter {name=" + name + "}", ise);
		}

		return dynamicSections;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicSectionUtil.class);

	private static BundleContext _bundleContext;

}