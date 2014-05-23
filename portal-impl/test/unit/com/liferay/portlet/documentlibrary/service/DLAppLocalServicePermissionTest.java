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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portlet.documentlibrary.service.impl.DLAppLocalServiceImpl;

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
public class DLAppLocalServicePermissionTest extends PowerMockito {

	@Before
	public void setUp() {
		_dlAppHelperLocalService = mock(DLAppHelperLocalService.class);
		_repositoryLocalService = mock(RepositoryLocalService.class);
		_localRepository = mock(LocalRepository.class);

		DLAppLocalServiceImpl dlAppLocalService = new DLAppLocalServiceImpl();
		dlAppLocalService.setDLAppHelperLocalService(_dlAppHelperLocalService);
		dlAppLocalService.setRepositoryLocalService(_repositoryLocalService);

		_dlAppLocalService = dlAppLocalService;
	}

	@Test
	public void testWhenDeletingAFileEntryFailsHelperActionsAreNotExecuted()
		throws PortalException, SystemException {

		when(
			_repositoryLocalService.getLocalRepositoryImpl(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(_localRepository);

		doThrow(
			new PrincipalException()
		).when(
			_localRepository
		).deleteFileEntry(Matchers.anyLong());

		try {
			_dlAppLocalService.deleteFileEntry(42L);
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
			_repositoryLocalService.getLocalRepositoryImpl(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(_localRepository);

		doThrow(
			new PrincipalException()
		).when(
			_localRepository
		).deleteFolder(Matchers.anyLong());

		try {
			_dlAppLocalService.deleteFolder(42L);
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
	private DLAppLocalService _dlAppLocalService;
	private LocalRepository _localRepository;
	private RepositoryLocalService _repositoryLocalService;

}