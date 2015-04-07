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

package com.liferay.item.selector.numbers;

import com.liferay.document.selector.ItemSelectorView;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Iván Zaera
 */
public class BaseNumberItemSelectorView
	implements ItemSelectorView<NumberItemSelectorCriterion> {

	public BaseNumberItemSelectorView(String cssStyle) {
		_cssStyle = cssStyle;
	}

	@Override
	public Class<NumberItemSelectorCriterion> getItemSelectorCriterionClass() {
		return NumberItemSelectorCriterion.class;
	}

	@Override
	public String getTitle(Locale locale) {
		Class<?> clazz = getClass();

		return LanguageUtil.get(locale, clazz.getSimpleName() + ".title");
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			NumberItemSelectorCriterion itemSelectorCriterion,
			PortletURL portletURL, String itemSelectedCallback)
		throws IOException {

		StringBundler sb = new StringBundler();

		Class<?> desiredReturnType =
			itemSelectorCriterion.getDesiredReturnTypes().iterator().next();

		sb.append("<script>");
		sb.append("function select(i) {");
		sb.append("  ");
		sb.append(itemSelectedCallback);
		sb.append("('");
		sb.append(desiredReturnType.getName());
		sb.append("',''+i)");
		sb.append("}");
		sb.append("</script>");

		for (int i = itemSelectorCriterion.getMinValue();
				i<= itemSelectorCriterion.getMaxValue(); i++) {

			sb.append("<a style='font-size: 20pt; border: 1px solid black; ");
			sb.append("padding: 4px; ");
			sb.append(_cssStyle);
			sb.append("' ");
			sb.append("href='javascript:select(");
			sb.append(i);
			sb.append(")'>");
			sb.append(i);
			sb.append("</a>&nbsp;");
		}

		PrintWriter writer = response.getWriter();

		writer.print(sb.toString());
	}

	private final String _cssStyle;

}