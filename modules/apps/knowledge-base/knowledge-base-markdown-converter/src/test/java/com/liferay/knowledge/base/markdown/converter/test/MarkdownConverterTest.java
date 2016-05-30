/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.knowledge.base.markdown.converter.test;

import static org.junit.Assert.assertNotSame;

import com.liferay.knowledge.base.markdown.converter.factory.MarkdownConverterFactoryUtil;

import org.junit.Test;

/**
 * @author Andy Wu
 */
public class MarkdownConverterTest {

	@Test
	public void testLiferayToHtmlSerializer() throws Exception {

		String markdownString = "### The liferay-ui:logo-selector Tag "
				+ "Requires Parameter Changes [](id=the-liferay-uilogo-selector"
				+ "-tag-requires-parameter-changes)";

		String html = MarkdownConverterFactoryUtil.create().
				convert(markdownString);

		int find = html.indexOf("id=\"the-liferay-uilogo-selector-tag-"
				+ "requires-parameter-changes\"");

		assertNotSame(-1, find);
	}
}
