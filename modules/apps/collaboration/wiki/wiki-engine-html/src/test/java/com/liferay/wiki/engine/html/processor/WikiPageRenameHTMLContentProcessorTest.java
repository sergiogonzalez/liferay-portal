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

package com.liferay.wiki.engine.html.processor;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class WikiPageRenameHTMLContentProcessorTest {

	@Test
	public void testProcessContentImage() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME&fileName=image.jpeg\">";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME", "FINAL_NAME", 0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME&fileName=image.jpeg\">",
			content);
	}

	@Test
	public void testProcessContentImageDoNotChangeOtherImages() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME1&fileName=image.jpeg\"/> " +
					"<img src=\"wiki/get_page_attachment?p_l_id=1234\"" +
						"&title=ORIGINAL_NAME2&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME1", "FINAL_NAME1", 0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME1&fileName=image.jpeg\"/> " +
					"<img src=\"wiki/get_page_attachment?p_l_id=1234\"" +
					"&title=ORIGINAL_NAME2&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithComplexTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=Complex.,() original title&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "Complex.,() original title", "Complex.,() final title",
			0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=Complex.,() final title&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithCurlyBracketsInTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title={ORIGINAL_NAME}&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "{ORIGINAL_NAME}", "{FINAL_NAME}", 0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title={FINAL_NAME}&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithNumbersInTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME123456&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME123456", "FINAL_NAME123456", 0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME123456&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithParenthesisInTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=(ORIGINAL_NAME)&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "(ORIGINAL_NAME)", "(FINAL_NAME)", 0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=(FINAL_NAME)&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithSpaceInTitle() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL NAME PAGE&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL NAME PAGE", "FINAL NAME PAGE", 0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL NAME PAGE&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentImageWithSrcAsFirstParameterDoNotChange() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?" +
				"title=ORIGINAL_NAME&fileName=image.jpeg\">";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME", "FINAL_NAME", 0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?" +
				"title=ORIGINAL_NAME&fileName=image.jpeg\">",
			content);
	}

	@Test
	public void testProcessContentImageWithSrcAsLastParameterDoNotChange() {
		String content =
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME\">";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME", "FINAL_NAME", 0);

		Assert.assertEquals(
			"This is a test <img src=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME\">",
			content);
	}

	@Test
	public void testProcessContentLink() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME", "FINAL_NAME", 0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkDoNotChangeOtherLinks() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME1&fileName=image.jpeg\"/> " +
					"<a href=\"wiki/get_page_attachment?p_l_id=1234\"" +
						"&title=ORIGINAL_NAME2&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME1", "FINAL_NAME1", 0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME1&fileName=image.jpeg\"/> " +
					"<a href=\"wiki/get_page_attachment?p_l_id=1234\"" +
					"&title=ORIGINAL_NAME2&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithComplexTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=Complex.,() original title&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "Complex.,() original title", "Complex.,() final title",
			0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=Complex.,() final title&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithCurlyBracketsInTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title={ORIGINAL_NAME}&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "{ORIGINAL_NAME}", "{FINAL_NAME}", 0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title={FINAL_NAME}&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithHrefAsFirstParameterDoNotChange() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?" +
				"title=ORIGINAL_NAME&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME", "FINAL_NAME", 0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?" +
				"title=ORIGINAL_NAME&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithHrefAsLastParameterDoNotChange() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME", "FINAL_NAME", 0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithNumbersInTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL_NAME123456&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL_NAME123456", "FINAL_NAME123456", 0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL_NAME123456&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithParenthesisInTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=(ORIGINAL_NAME)&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "(ORIGINAL_NAME)", "(FINAL_NAME)", 0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=(FINAL_NAME)&fileName=image.jpeg\"/>",
			content);
	}

	@Test
	public void testProcessContentLinkWithSpaceInTitle() {
		String content =
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=ORIGINAL NAME PAGE&fileName=image.jpeg\"/>";

		content = _wikiPageRenameHTMLContentProcessor.processContent(
			content, "ORIGINAL NAME PAGE", "FINAL NAME PAGE", 0);

		Assert.assertEquals(
			"This is a test <a href=\"wiki/get_page_attachment?p_l_id=1234" +
				"&title=FINAL NAME PAGE&fileName=image.jpeg\"/>",
			content);
	}

	private final WikiPageRenameHTMLContentProcessor
		_wikiPageRenameHTMLContentProcessor =
			new WikiPageRenameHTMLContentProcessorStub();

	private class WikiPageRenameHTMLContentProcessorStub
		extends WikiPageRenameHTMLContentProcessor {

		public WikiPageRenameHTMLContentProcessorStub() {
			activate();
		}

	}

}