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
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;

import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class DefaultVersionValidatorTest {

	@Test
	public void testAValidVersionNumberSucceeds()
		throws PortalException, SystemException {

		_versionValidator.validateVersion("1.2");
	}

	@Test
	public void testValidatingNullSucceeds()
		throws PortalException, SystemException {

		_versionValidator.validateVersion(null);
	}

	@Test
	public void testValidatingPrivateWorkingCopySucceeds()
		throws PortalException, SystemException {

		_versionValidator.validateVersion(
			DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);
	}

	@Test(expected = InvalidFileVersionException.class)
	public void testVersionMusBeANumber()
		throws PortalException, SystemException {

		_versionValidator.validateVersion("xyz");
	}

	@Test(expected = InvalidFileVersionException.class)
	public void testVersionMustHaveAtLeastOneDecimal()
		throws PortalException, SystemException {

		_versionValidator.validateVersion("42");
	}

	@Test(expected = InvalidFileVersionException.class)
	public void testVersionMustHaveAtMostOneDot()
		throws PortalException, SystemException {

		_versionValidator.validateVersion("1.2.3");
	}

	private final VersionValidator _versionValidator =
		new DefaultVersionValidator();

}