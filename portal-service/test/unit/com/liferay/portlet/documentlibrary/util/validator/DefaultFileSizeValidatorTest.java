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

package com.liferay.portlet.documentlibrary.util.validator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;

import java.io.File;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.internal.stubbing.answers.Returns;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adolfo Pérez
 */
@PrepareForTest({PropsUtil.class})
@RunWith(PowerMockRunner.class)
public class DefaultFileSizeValidatorTest extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(PropsUtil.class, new Returns(Long.toString(MAX_FILE_SIZE)));
	}

	@Test(expected = FileSizeException.class)
	public void testFileSizeGreaterThanMaxFails()
		throws PortalException, SystemException {

		_fileSizeValidator.validateSize(
			"fileName", new byte[MAX_FILE_SIZE + 1]);
	}

	@Test
	public void testFileSizeLessThanOrEqualToMaxSucceeds()
		throws PortalException, SystemException {

		_fileSizeValidator.validateSize("fileName", new byte[MAX_FILE_SIZE]);
	}

	@Test(expected = FileSizeException.class)
	public void testNullBytesFails() throws PortalException, SystemException {
		_fileSizeValidator.validateSize("fileName", (byte[])null);
	}

	@Test(expected = FileSizeException.class)
	public void testNullFileFails() throws PortalException, SystemException {
		_fileSizeValidator.validateSize("fileName", (File)null);
	}

	@Test(expected = FileSizeException.class)
	public void testNullInputStreamFails()
		throws PortalException, SystemException {

		_fileSizeValidator.validateSize("fileName", (InputStream)null);
	}

	@Test
	public void testWithUnlimitedMaxSizeSucceeds()
		throws PortalException, SystemException {

		when(
			PropsUtil.get(PropsKeys.DL_FILE_MAX_SIZE)
		).thenReturn(
			"0"
		);

		_fileSizeValidator.validateSize("fileName", new byte[MAX_FILE_SIZE]);
	}

	private static final int MAX_FILE_SIZE = 100;

	private final FileSizeValidator _fileSizeValidator =
		new DefaultFileSizeValidator();

}