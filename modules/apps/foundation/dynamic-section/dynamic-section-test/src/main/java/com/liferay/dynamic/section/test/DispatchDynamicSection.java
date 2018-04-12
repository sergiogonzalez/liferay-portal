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

package com.liferay.dynamic.section.test;

import com.liferay.dynamic.section.taglib.DynamicSection;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.ByteArrayOutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(
	immediate = true,
	property = {Constants.SERVICE_RANKING + ":Integer=1", "name=testDI"}
)
public class DispatchDynamicSection implements DynamicSection {

	@Override
	public StringBundler modify(StringBundler sb, PageContext pageContext) {
		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/META-INF/resources/test.jsp");

		String result = null;

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			HttpServletResponse httpServletResponse = new PipingServletResponse(
				(HttpServletResponse)pageContext.getResponse(), outputStream);

			requestDispatcher.include(
				pageContext.getRequest(), httpServletResponse);

			result = new String(outputStream.toByteArray());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return new StringBundler(result);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.dynamic.section.test)"
	)
	private ServletContext _servletContext;

}