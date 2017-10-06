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

package com.liferay.document.library.content.internal.command;

import com.liferay.document.library.kernel.exception.NoSuchContentException;
import com.liferay.document.library.kernel.model.DLContent;
import com.liferay.document.library.kernel.service.DLContentLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {"osgi.command.function=check", "osgi.command.scope=dlcontent"},
	service = DLContentServiceOSGiCommands.class
)
public class DLContentServiceOSGiCommands {

	public void check() throws NoSuchContentException {
		DLContent dlContent1 = _getRandomDlContent();

		DLContent dlContent2 = DLContentLocalServiceUtil.fetchDLContent(
			dlContent1.getContentId());

		DLContent dlContent3 = _getRandomDlContent();

		List<DLContent> dlContents = Arrays.asList(
			dlContent1, dlContent2, dlContent3);

		for (DLContent dlContentA : dlContents) {
			for (DLContent dlContentB : dlContents) {
				System.out.println(dlContentA.equals(dlContentB));
			}
		}
	}

	private DLContent _getRandomDlContent() {
		return DLContentLocalServiceUtil.addContent(
			20109L, 0, StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString().getBytes());
	}

}