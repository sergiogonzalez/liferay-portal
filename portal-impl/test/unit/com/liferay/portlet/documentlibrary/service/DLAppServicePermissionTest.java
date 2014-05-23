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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.RepositoryService;
import com.liferay.portlet.documentlibrary.service.impl.DLAppServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adolfo Pérez
 */
@RunWith(PowerMockRunner.class)
public class DLAppServicePermissionTest extends PowerMockito {

	@Before
	public void setUp() {
		_dlAppHelperLocalService = mock(DLAppHelperLocalService.class);
		_repositoryService = mock(RepositoryService.class);
		_repository = mock(Repository.class);

		DLAppServiceImpl dlAppService = new DLAppServiceImpl();
		dlAppService.setDLAppHelperLocalService(_dlAppHelperLocalService);
		dlAppService.setRepositoryService(_repositoryService);

		_dlAppService = dlAppService;
	}

	@Test
	public void testWhenDeletingAFileEntryFailsHelperActionsAreNotExecuted()
		throws PortalException, SystemException {

		when(
			_repositoryService.getRepositoryImpl(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(_repository);

		doThrow(
			new PrincipalException()
		).when(
			_repository
		).deleteFileEntry(Matchers.anyLong());

		try {
			_dlAppService.deleteFileEntry(42L);
			Assert.fail();
		}
		catch (PrincipalException pe) {

			// expected exception

		}

		Mockito.verify(
			_dlAppHelperLocalService, Mockito.never()
		).deleteFileEntry(Matchers.any(FileEntry.class));
	}

	@Test
	public void testWhenDeletingAFolderFailsHelperActionsAreNotExecuted()
		throws PortalException, SystemException {

		when(
			_repositoryService.getRepositoryImpl(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(_repository);

		doThrow(
			new PrincipalException()
		).when(
			_repository
		).deleteFolder(Matchers.anyLong());

		try {
			_dlAppService.deleteFolder(42L);
			Assert.fail();
		}
		catch (PrincipalException pe) {

			// expected exception

		}

		Mockito.verify(
			_dlAppHelperLocalService, Mockito.never()
		).deleteFolder(Matchers.any(Folder.class));
	}

	private DLAppHelperLocalService _dlAppHelperLocalService;
	private DLAppService _dlAppService;
	private Repository _repository;
	private RepositoryService _repositoryService;

}