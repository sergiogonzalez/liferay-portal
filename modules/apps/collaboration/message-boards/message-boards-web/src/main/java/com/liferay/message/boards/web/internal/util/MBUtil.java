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

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.constants.MBThreadConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.message.boards.settings.MBGroupServiceSettings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class MBUtil {

	public static String[] getThreadPriority(
		MBGroupServiceSettings mbGroupServiceSettings, String languageId,
		double value)
		throws Exception {

		String[] priorities = mbGroupServiceSettings.getPriorities(languageId);

		String[] priorityPair = _findThreadPriority(value, priorities);

		if (priorityPair == null) {
			String defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getSiteDefault());

			priorities = mbGroupServiceSettings.getPriorities(
				defaultLanguageId);

			priorityPair = _findThreadPriority(value, priorities);
		}

		return priorityPair;
	}

	private static String[] _findThreadPriority(
		double value, String[] priorities) {

		for (int i = 0; i < priorities.length; i++) {
			String[] priority = StringUtil.split(
				priorities[i], StringPool.PIPE);

			try {
				String priorityName = priority[0];
				String priorityImage = priority[1];
				double priorityValue = GetterUtil.getDouble(priority[2]);

				if (value == priorityValue) {
					return new String[] {priorityName, priorityImage};
				}
			}
			catch (Exception e) {
				_log.error("Unable to determine thread priority", e);
			}
		}

		return null;
	}

	public static String getBBCodeSplitThreadBody(HttpServletRequest request) {
		StringBundler sb = new StringBundler(5);

		sb.append("[url=");
		sb.append(MBThreadConstants.NEW_THREAD_URL);
		sb.append("]");
		sb.append(MBThreadConstants.NEW_THREAD_URL);
		sb.append("[/url]");

		return LanguageUtil.format(
			request, "the-new-thread-can-be-found-at-x", sb.toString(), false);
	}

	public static String getHtmlSplitThreadBody(HttpServletRequest request) {
		StringBundler sb = new StringBundler(5);

		sb.append("<a href=");
		sb.append(MBThreadConstants.NEW_THREAD_URL);
		sb.append(">");
		sb.append(MBThreadConstants.NEW_THREAD_URL);
		sb.append("</a>");

		return LanguageUtil.format(
			request, "the-new-thread-can-be-found-at-x", sb.toString(), false);
	}

	public static String getHtmlQuoteBody(
		HttpServletRequest request, MBMessage parentMessage) {

		String parentAuthor = null;

		if (parentMessage.isAnonymous()) {
			parentAuthor = LanguageUtil.get(request, "anonymous");
		}
		else {
			parentAuthor = HtmlUtil.escape(
				PortalUtil.getUserName(parentMessage));
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<blockquote><div class=\"quote-title\">");
		sb.append(parentAuthor);
		sb.append(": </div><div class=\"quote\"><div class=\"quote-content\">");
		sb.append(parentMessage.getBody(false));
		sb.append("</div></blockquote><br /><br /><br />");

		return sb.toString();
	}

	public static String getBBCodeQuoteBody(
		HttpServletRequest request, MBMessage parentMessage) {

		String parentAuthor = null;

		if (parentMessage.isAnonymous()) {
			parentAuthor = LanguageUtil.get(request, "anonymous");
		}
		else {
			parentAuthor = HtmlUtil.escape(
				PortalUtil.getUserName(parentMessage));
		}

		StringBundler sb = new StringBundler(5);

		sb.append("[quote=");
		sb.append(
			StringUtil.replace(
				parentAuthor, new String[] {"[", "]", "(", ")"},
				new String[] {"&#91;", "&#93;", "&#40;", "&#41;"}));
		sb.append("]\n");
		sb.append(parentMessage.getBody(false));
		sb.append("[/quote]\n\n\n");

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(MBUtil.class);

}
