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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.portal.events.AddDefaultDocumentLibraryStructuresAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.BasePermissionTestCase;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileEntryTypePermissionTest extends BasePermissionTestCase {

	@Test
	public void testGetFolderFileEntryTypeViewPermissions() throws Exception {
		addPortletModelViewPermission(_contractDLFileEntryType.getPrimaryKey());

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeServiceUtil.getFolderFileEntryTypes(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					group.getGroupId()),
				_folder.getFolderId(), true);

		Assert.assertEquals(5, dlFileEntryTypes.size());

		removeResourceModelViewPermission(
			_contractDLFileEntryType.getPrimaryKey());

		dlFileEntryTypes = DLFileEntryTypeServiceUtil.getFolderFileEntryTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(group.getGroupId()),
			_folder.getFolderId(), true);

		Assert.assertEquals(4, dlFileEntryTypes.size());

		addPortletModelViewPermission(_contractDLFileEntryType.getPrimaryKey());

		DLAppLocalServiceUtil.updateFolder(
			_folder.getFolderId(), _folder.getParentFolderId(),
			_folder.getName(), _folder.getDescription(),
			_getFolderServiceContext(_contractDLFileEntryType));

		dlFileEntryTypes = DLFileEntryTypeServiceUtil.getFolderFileEntryTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(group.getGroupId()),
			_folder.getFolderId(), true);

		Assert.assertEquals(1, dlFileEntryTypes.size());

		dlFileEntryTypes = DLFileEntryTypeServiceUtil.getFolderFileEntryTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(group.getGroupId()),
			_subfolder.getFolderId(), true);

		Assert.assertEquals(1, dlFileEntryTypes.size());

		removeResourceModelViewPermission(
			_contractDLFileEntryType.getPrimaryKey());

		dlFileEntryTypes = DLFileEntryTypeServiceUtil.getFolderFileEntryTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(group.getGroupId()),
			_folder.getFolderId(), true);

		Assert.assertEquals(0, dlFileEntryTypes.size());

		dlFileEntryTypes = DLFileEntryTypeServiceUtil.getFolderFileEntryTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(group.getGroupId()),
			_subfolder.getFolderId(), true);

		Assert.assertEquals(0, dlFileEntryTypes.size());
	}

	protected void addPortletModelViewPermission(long primKey)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			group.getCompanyId(), getResourceName(),
			ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(primKey),
			role.getRoleId(), new String[]{ActionKeys.VIEW});
	}

	@Override
	protected void doSetUp() throws Exception {
		SimpleAction simpleAction =
			new AddDefaultDocumentLibraryStructuresAction();

		String companyIdString = String.valueOf(TestPropsValues.getCompanyId());

		simpleAction.run(new String[] {companyIdString});

		_folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A", "",
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		_subfolder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), group.getGroupId(),
			_folder.getFolderId(), "SubFolder AA", "",
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					group.getGroupId()));

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			String fileEntryTypeKey = dlFileEntryType.getFileEntryTypeKey();

			if (fileEntryTypeKey.equals(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT)) {

				_contractDLFileEntryType = dlFileEntryType;
			}
		}
	}

	@Override
	protected String getResourceName() {
		return "com.liferay.portlet.documentlibrary.model.DLFileEntryType";
	}

	protected void removeResourceModelViewPermission(long primKey)
		throws Exception {

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, getResourceName(),
			ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(primKey),
			ActionKeys.VIEW);
	}

	private ServiceContext _getFolderServiceContext(
			DLFileEntryType dlFileEntryType)
		throws PortalException {

		long primaryKey = dlFileEntryType.getPrimaryKey();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute("defaultFileEntryTypeId", primaryKey);
		serviceContext.setAttribute(
			"dlFileEntryTypesSearchContainerPrimaryKeys",
			String.valueOf(primaryKey));
		serviceContext.setAttribute("overrideFileEntryTypes", true);

		return serviceContext;
	}

	private DLFileEntryType _contractDLFileEntryType;
	private Folder _folder;
	private Folder _subfolder;

}