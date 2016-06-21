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

package com.liferay.wiki.engine.html.translator;

import com.liferay.wiki.translator.BaseWikiTitleChangeTranslator;
import com.liferay.wiki.translator.WikiTitleChangeTranslator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Roberto Díaz
 * @author Daniel Sanz
 */
@Component(
	immediate = true, property = "wiki.format.name=html",
	service = WikiTitleChangeTranslator.class
)
public class WikiTitleChangeHTMLTranslator
	extends BaseWikiTitleChangeTranslator {

	@Activate
	@Modified
	public void activate() {
		regexps.put(
			"(<img [^s]*src=\"[^g]+get_page_attachment\\?[^t]+title=)" +
				"@old_title@&",
			"$1@new_title@&");

		regexps.put(
			"(<a [^h]*href=\"[^g]+get_page_attachment\\?[^t]+title=)" +
				"@old_title@&",
			"$1@new_title@&");
	}

}