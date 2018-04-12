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

import javax.servlet.jsp.PageContext;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Tambara
 */
@Component(
	immediate = true,
	property = {Constants.SERVICE_RANKING + ":Integer=2", "name=testDI"}
)
public class TextDynamicSection implements DynamicSection {

	public StringBundler modify(StringBundler sb, PageContext pageContext) {
		StringBundler stringBundler = new StringBundler(3);

		stringBundler.append("Header Text <br/>");
		stringBundler.append(sb.toString());
		stringBundler.append("<br /> Footer Text");

		return stringBundler;
	}

}