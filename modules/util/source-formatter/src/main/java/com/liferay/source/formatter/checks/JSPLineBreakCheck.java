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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPLineBreakCheck extends LineBreakCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			int lineCount = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				checkLineBreaks(line, previousLine, fileName, lineCount);

				previousLine = line;
			}
		}

		content = _fixRedundantLineBreaks(content);

		return fixRedundantCommaInsideArray(content);
	}

	private String _fixRedundantLineBreaks(String content) {
		Matcher matcher = _redundantLineBreakPattern1.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJavaSource(content, matcher.start())) {
				continue;
			}

			int x = matcher.start();

			while (true) {
				x = content.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

				if (x == -1) {
					break;
				}

				String codeBlock = content.substring(matcher.start(), x + 1);

				if (codeBlock.contains("{\n")) {
					break;
				}

				if (getLevel(codeBlock) != 0) {
					continue;
				}

				String codeSingleLine = StringUtil.replace(
					codeBlock, new String[] {StringPool.TAB, ",\n", "\n"},
					new String[] {StringPool.BLANK, ", ", StringPool.BLANK});

				return StringUtil.replaceFirst(
					content, codeBlock, codeSingleLine, matcher.start());
			}
		}

		matcher = _redundantLineBreakPattern2.matcher(content);

		while (matcher.find()) {
			if (JSPSourceUtil.isJavaSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, matcher.group(1), StringPool.SPACE,
					matcher.start());
			}
		}

		return content;
	}

	private final Pattern _redundantLineBreakPattern1 = Pattern.compile(
		"\\(\n");
	private final Pattern _redundantLineBreakPattern2 = Pattern.compile(
		"[|&](\n\\s*)");

}