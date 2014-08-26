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

package com.liferay.portal.parsers.bbcode;

import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ThemeConstants;

/**
 * @author André de Oliveira
 * @author Roberto Díaz
 * @author Sergio González
 */
public class BBCodeUtil {

	public static String getBBCodeHTML(String msgBody, String pathThemeImages) {
		return StringUtil.replace(
			BBCodeTranslatorUtil.getHTML(msgBody),
			ThemeConstants.TOKEN_THEME_IMAGES_PATH +
				BBCodeConstants.EMOTICONS_PATH,
			pathThemeImages + BBCodeConstants.EMOTICONS_PATH);
	}

}