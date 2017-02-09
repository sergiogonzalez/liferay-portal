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

package com.liferay.message.boards.web.internal.upload.format.handlers;

import com.liferay.message.boards.web.internal.util.MBAttachmentFileEntryReference;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alejandro Tardín
 */
@RunWith(MockitoJUnitRunner.class)
public class MBMessageHTMLFormatUploadHandlerTest {

	@Before
	public void setUp() {
		_handler.setPortletFileRepository(_portletFileRepository);
	}

	@Test
	public void testDoesNotReplaceImageReferencesWithoutDataImageIdAttribute() {
		List<MBAttachmentFileEntryReference> fileEntryReferences =
			new ArrayList<>();

		FileEntry fileEntry = Mockito.mock(FileEntry.class);
		long tempFileId = 1;
		String originalContent = "<img src=\"http://random\"/>";

		Mockito.doReturn("http://final").when(_portletFileRepository).
			getPortletFileEntryURL(
				Mockito.isNull(ThemeDisplay.class), Mockito.eq(fileEntry),
				Mockito.eq(StringPool.BLANK));

		fileEntryReferences.add(
			new MBAttachmentFileEntryReference(tempFileId, fileEntry));

		String finalContent = _handler.replaceImageReferences(
			originalContent, fileEntryReferences);

		Assert.assertEquals(originalContent, finalContent);
	}

	@Test
	public void testReplaceASingleImageReference() {
		List<MBAttachmentFileEntryReference> fileEntryReferences =
			new ArrayList<>();

		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		String temporalURL = "http://temporal";
		String finalURL = "http://final";
		long tempFileId = 1;

		Mockito.doReturn(finalURL).when(_portletFileRepository).
			getPortletFileEntryURL(
				Mockito.isNull(ThemeDisplay.class), Mockito.eq(fileEntry),
				Mockito.eq(StringPool.BLANK));

		fileEntryReferences.add(
			new MBAttachmentFileEntryReference(tempFileId, fileEntry));

		String finalContent = _handler.replaceImageReferences(
			String.format(
				"<img data-image-id=\"%d\" src=\"%s\"/>", tempFileId,
				temporalURL),
			fileEntryReferences);

		Assert.assertEquals("<img src=\"" + finalURL + "\" />", finalContent);
	}

	@Test
	public void testReplaceSeveralImageReferences() {
		List<MBAttachmentFileEntryReference> fileEntryReferences =
			new ArrayList<>();

		StringBuilder originalContent = new StringBuilder();
		StringBuilder expectedContent = new StringBuilder();

		for (int tempFileId = 0; tempFileId < 3; tempFileId++) {
			String temporalURL = "http://temporal-" + tempFileId;
			String finalURL = "http://final-" + tempFileId;
			FileEntry fileEntry = Mockito.mock(FileEntry.class);

			Mockito.doReturn(finalURL).when(_portletFileRepository).
				getPortletFileEntryURL(
					Mockito.isNull(ThemeDisplay.class), Mockito.eq(fileEntry),
					Mockito.eq(StringPool.BLANK));

			fileEntryReferences.add(
				new MBAttachmentFileEntryReference(tempFileId, fileEntry));

			originalContent.append(
				String.format(
					"<img data-image-id=\"%d\" src=\"%s\"/>", tempFileId,
					temporalURL));

			expectedContent.append("<img src=\"" + finalURL + "\" />");
		}

		String finalContent = _handler.replaceImageReferences(
			originalContent.toString(), fileEntryReferences);

		Assert.assertEquals(expectedContent.toString(), finalContent);
	}

	private final MBMessageHTMLFormatUploadHandler _handler =
		new MBMessageHTMLFormatUploadHandler();

	@Mock
	private PortletFileRepository _portletFileRepository;

}