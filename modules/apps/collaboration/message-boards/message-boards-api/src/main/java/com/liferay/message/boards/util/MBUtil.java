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

package com.liferay.message.boards.util;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBBan;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import javax.mail.Message;
import javax.servlet.http.HttpServletRequest;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class MBUtil {

	public static final String BB_CODE_EDITOR_WYSIWYG_IMPL_KEY =
		"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
			"edit_message.bb_code.jsp";


	public static long getCategoryId(
		HttpServletRequest request, MBCategory category) {

		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

		if (category != null) {
			categoryId = category.getCategoryId();
		}

		categoryId = ParamUtil.getLong(request, "mbCategoryId", categoryId);

		return categoryId;
	}

	public static long getCategoryId(
		HttpServletRequest request, MBMessage message) {

		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

		if (message != null) {
			categoryId = message.getCategoryId();
		}

		categoryId = ParamUtil.getLong(request, "mbCategoryId", categoryId);

		return categoryId;
	}

	public static long getCategoryId(String messageIdString) {
		String[] parts = _getMessageIdStringParts(messageIdString);

		return GetterUtil.getLong(parts[0]);
	}

	public static Date getUnbanDate(MBBan ban, int expireInterval) {
		Date banDate = ban.getCreateDate();

		Calendar cal = Calendar.getInstance();

		cal.setTime(banDate);

		cal.add(Calendar.DATE, expireInterval);

		return cal.getTime();
	}

	public static String getSubjectForEmail(MBMessage message)
		throws Exception {

		String subject = message.getSubject();

		if (subject.startsWith(MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE)) {
			return subject;
		}
		else {
			return MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE +
			   message.getSubject();
		}
	}

	public static int getMessageIdStringOffset() {
		if (PropsValues.POP_SERVER_SUBDOMAIN.length() == 0) {
			return 1;
		}

		return 0;
	}

	public static String getParentMessageIdString(Message message)
		throws Exception {

		// If the previous block failed, try to get the parent message ID from
		// the "References" header as explained in
		// http://cr.yp.to/immhf/thread.html. Some mail clients such as Yahoo!
		// Mail use the "In-Reply-To" header, so we check that as well.

		String parentHeader = null;

		String[] references = message.getHeader("References");

		if (ArrayUtil.isNotEmpty(references)) {
			String reference = references[0];

			int x = reference.lastIndexOf(
				StringPool.LESS_THAN + MESSAGE_POP_PORTLET_PREFIX);

			if (x > -1) {
				int y = reference.indexOf(StringPool.GREATER_THAN, x);

				parentHeader = reference.substring(x, y + 1);
			}
		}

		if (parentHeader == null) {
			String[] inReplyToHeaders = message.getHeader("In-Reply-To");

			if (ArrayUtil.isNotEmpty(inReplyToHeaders)) {
				parentHeader = inReplyToHeaders[0];
			}
		}

		if (Validator.isNull(parentHeader) ||
			!parentHeader.startsWith(MESSAGE_POP_PORTLET_PREFIX, 1)) {

			parentHeader = _getParentMessageIdFromSubject(message);
		}

		return parentHeader;
	}

	public static long getParentMessageId(Message message) throws Exception {
		long parentMessageId = -1;

		String parentMessageIdString = getParentMessageIdString(message);

		if (parentMessageIdString != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Parent header " + parentMessageIdString);
			}

			parentMessageId = getMessageId(parentMessageIdString);

			if (_log.isDebugEnabled()) {
				_log.debug("Parent message id " + parentMessageId);
			}
		}

		return parentMessageId;
	}

	public static boolean hasMailIdHeader(Message message) throws Exception {
		String[] messageIds = message.getHeader("Message-ID");

		if (messageIds == null) {
			return false;
		}

		for (String messageId : messageIds) {
			if (Validator.isNotNull(PropsValues.POP_SERVER_SUBDOMAIN) &&
				messageId.contains(PropsValues.POP_SERVER_SUBDOMAIN)) {

				return true;
			}
		}

		return false;
	}

	public static String getSubjectWithoutMessageId(Message message)
		throws Exception {

		String subject = message.getSubject();

		String parentMessageId = _getParentMessageIdFromSubject(message);

		if (Validator.isNotNull(parentMessageId)) {
			int pos = subject.indexOf(parentMessageId);

			if (pos != -1) {
				subject = subject.substring(0, pos);
			}
		}

		return subject;
	}

	private static String _getParentMessageIdFromSubject(Message message)
		throws Exception {

		if (message.getSubject() == null) {
			return null;
		}

		String parentMessageId = null;

		String subject = message.getSubject();

		int pos = subject.lastIndexOf(CharPool.LESS_THAN);

		if (pos != -1) {
			parentMessageId = subject.substring(pos);
		}

		return parentMessageId;
	}

	public static final String MESSAGE_POP_PORTLET_PREFIX = "mb_message.";

	public static long getMessageId(String messageIdString) {
		String[] parts = _getMessageIdStringParts(messageIdString);

		return GetterUtil.getLong(parts[1]);
	}

	private static String[] _getMessageIdStringParts(String messageIdString) {
		int start =
			messageIdString.indexOf(MBUtil.MESSAGE_POP_PORTLET_PREFIX) +
			MBUtil.MESSAGE_POP_PORTLET_PREFIX.length();

		int end = messageIdString.indexOf(CharPool.AT);

		return StringUtil.split(
			messageIdString.substring(start, end), CharPool.PERIOD);
	}

	public static boolean isValidMessageFormat(String messageFormat) {
		String editorName = PropsUtil.get(BB_CODE_EDITOR_WYSIWYG_IMPL_KEY);

		if (editorName.equals("bbcode")) {
			editorName = "alloyeditor_bbcode";

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Replacing unsupported BBCode editor with AlloyEditor " +
						"BBCode");
			}
		}

		if (messageFormat.equals("bbcode") &&
			!editorName.equals("alloyeditor_bbcode") &&
			!editorName.equals("ckeditor_bbcode")) {

			return false;
		}

		if (!ArrayUtil.contains(MBMessageConstants.FORMATS, messageFormat)) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(MBUtil.class);

}