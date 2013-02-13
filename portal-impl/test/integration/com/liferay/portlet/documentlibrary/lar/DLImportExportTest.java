/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.events.AddDefaultDocumentLibraryStructuresAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatamapping.lar.DDMPortletDataHandler;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sampsa Sohlman
 */
@ExecutionTestListeners(listeners = {
	MainServletExecutionTestListener.class,
	TransactionalExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DLImportExportTest {

	@Before
	public void setUp() throws Exception {
		Map<java.lang.String, java.lang.String[]> _parameterMap =
			new HashMap<String, String[]>();

		SimpleAction simpleAction =
			new AddDefaultDocumentLibraryStructuresAction();

		String random = ServiceTestUtil.randomString();

		String from = "from".concat(ServiceTestUtil.randomString());
		String to = "to".concat(ServiceTestUtil.randomString());

		_companyFrom =
			CompanyLocalServiceUtil.addCompany(
				from.concat(".id"), from.concat(".com"), from.concat(".mx"),
				null, false, 100, true);

		_companyTo =
			CompanyLocalServiceUtil.addCompany(
				to.concat(".id"), to.concat(".com"), to.concat(".mx"), null,
				false, 100, true);

		simpleAction.run(new String[] {
			String.valueOf(_companyFrom.getCompanyId()),
			String.valueOf(_companyTo.getCompanyId()),
		});

	}

	@After
	public void tearDown() throws Exception {
		if ((_larFile != null) && _larFile.exists()) {
			FileUtil.delete(_larFile);
		}

		_companyFrom = null;
		_companyTo = null;
	}

	@Test
	public void testFileEntriTypesAtGlobalDifferentCompany() throws Exception {
		testImportExport(true, true);
	}

	@Test
	public void testFileEntriTypesAtGlobalSameCompany() throws Exception {
		testImportExport(true, false);
	}

	@Test
	public void testFileEntriTypesAtLocalDifferentCompany() throws Exception {
		testImportExport(false, true);
	}

	@Test
	public void testFileEntriTypesAtLocalSameCompany() throws Exception {
		testImportExport(false, false);
	}

	protected DDMStructure addDDMStructure(
			User user, long groupId, String structureKey,
			String structureFileString, ServiceContext serviceContext)
		throws Exception {

		return DDMStructureLocalServiceUtil.addStructure(
			user.getUserId(), groupId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			PortalUtil.getClassNameId(DDLRecord.class), structureKey,
			getDefaultLocaleMap("en_US"), null, structureFileString,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT,
			serviceContext);
	}

	protected DLFileEntryType addDLFileEntryType(
			long groupId, String fileEnryTypeName, User user,
			ServiceContext serviceContext, long... ddmStructureIds)
		throws Exception {

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				user.getUserId(), groupId, fileEnryTypeName, StringPool.BLANK,
				ddmStructureIds, serviceContext);

		return dlFileEntryType;
	}

	protected FileEntry addFileEntryToFolder(
			long parentFolderId, long fileEntryTypeId, long groupId,
			long userId, String name, byte[] bytes,
			ObjectValuePair<String, String>... values)
		throws Exception {

		String title = name.concat(" ").concat("title");
		String description = name.concat(" ").concat("description");

		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(
				fileEntryTypeId);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		if (!ddmStructures.isEmpty()) {

			// For start this supports only 1 strucutre per file type

			DDMStructure ddmStructure = ddmStructures.get(0);
			for (ObjectValuePair<String, String> value : values) {
				setDDMFieldValueToServiceToContext(
					serviceContext, ddmStructure, value.getKey(),
					value.getValue());
			}
		}

		return DLAppTestUtil.addFileEntry(
			groupId, parentFolderId, name, title, description, bytes,
			serviceContext);
	}

	protected DLFileEntryType addFileEntryType(
			long fileTypeGroupId, User user, String structureKey, String xsd)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(fileTypeGroupId);
		serviceContext.setUserId(user.getUserId());
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		DDMStructure ddmStructure =
			addDDMStructure(
				user, fileTypeGroupId, structureKey, xsd, serviceContext);

		return addDLFileEntryType(
			fileTypeGroupId, "MyFileTypeEntry", user, serviceContext,
			ddmStructure.getStructureId());
	}

	@SuppressWarnings("unchecked")
	protected void addFilesWithFileTypesToSite(
			Layout layout, boolean globalFileEntryType, User user)
		throws Exception {

		long fileEntryTypeGroupId;

		if (globalFileEntryType) {
			fileEntryTypeGroupId =
				GroupLocalServiceUtil.getCompanyGroup(
					layout.getCompanyId()).getGroupId();
			_log.debug(
				String.format(" fileEntryTypeGroupId=%d is global",
				fileEntryTypeGroupId));
		}
		else {
			fileEntryTypeGroupId = layout.getGroupId();
			_log.debug(
				String.format(" fileEntryTypeGroupId=%d is local",
				fileEntryTypeGroupId));
		}

		DLFileEntryType fileEntryType =
			addFileEntryType(
				fileEntryTypeGroupId, user, "contract fields",
				readStructure("dependencies/ddm-structure-1.xml"));

		long rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		Folder folder = DLAppTestUtil.addFolder(
			layout.getGroupId(), rootFolderId, "First");

		addFileEntryToFolder(
			folder.getFolderId(), fileEntryType.getFileEntryTypeId(),
			layout.getGroupId(), user.getUserId(), "ö-tiedosto.txt",
			readFile("dependencies/file-scandinavic-chars-ISO-8859-1.bin"),
			new ObjectValuePair<String, String>("superHero", "super"));

		addFileEntryToFolder(
			folder.getFolderId(), fileEntryType.getFileEntryTypeId(),
			layout.getGroupId(), user.getUserId(), "alphabeth-file.txt",
			readFile("dependencies/file-alphabeth.txt"),
			new ObjectValuePair<String, String>("superHero", "spider"));

		addFileEntryToFolder(
			rootFolderId, fileEntryType.getFileEntryTypeId(),
			layout.getGroupId(), user.getUserId(), "special-chars-file.txt",
			readFile("dependencies/file-special-chars-utf-8.bin"),
			new ObjectValuePair<String, String>("superHero", "bat"));
	}

	protected Group addGroup(long parentGroupId, User user, String name)
		throws Exception {

		Group group =
			GroupLocalServiceUtil.fetchGroup(
				TestPropsValues.getCompanyId(), name);

		if (group != null) {
			return group;
		}

		String description = "This is a test group.";
		int type = GroupConstants.TYPE_SITE_OPEN;
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);
		boolean site = true;
		boolean active = true;

		Group parentGroup = GroupLocalServiceUtil.getGroup(parentGroupId);

		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(TestPropsValues.getCompanyId());
		serviceContext.setUserId(user.getUserId());

		return GroupLocalServiceUtil.addGroup(
			user.getUserId(), parentGroupId, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, description, type,
			friendlyURL, site, active, serviceContext);
	}

	protected Layout addLayout(
			Group group, String name, boolean privateLayout, String friendlyURL,
			String layouteTemplateId)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		Layout layout =
			LayoutLocalServiceUtil.addLayout(
				group.getCreatorUserId(), group.getGroupId(), privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name,
				StringPool.BLANK, StringPool.BLANK,
				LayoutConstants.TYPE_PORTLET, false, friendlyURL,
				serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(0, layouteTemplateId, false);

		return layout;
	}

	protected Layout createTestLayoutWithSite(
			long companyId, User user, String siteName)
		throws Exception {

		Group parentGroup = GroupLocalServiceUtil.getCompanyGroup(companyId);
		Group group = addGroup(parentGroup.getGroupId(), user, siteName);
		return addLayout(group, "Home", false, "/home", "2_columns_ii");

	}

	protected Map<Locale, String> getDefaultLocaleMap(String defaultValue) {
		Map<Locale, String> map = new HashMap<Locale, String>();
		map.put(LocaleUtil.getDefault(), defaultValue);
		return map;
	}

	protected Map<java.lang.String, java.lang.String[]> getExportParameterMap()
		throws Exception {

		Map<java.lang.String, java.lang.String[]> map =
			new HashMap<String, String[]>();

		setAddParameterToMap(map, PortletDataHandlerKeys.CATEGORIES, true);
		setAddParameterToMap(map, PortletDataHandlerKeys.PERMISSIONS, true);
		setAddParameterToMap(map, PortletDataHandlerKeys.PORTLET_DATA, true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE,
			PortletDataHandlerKeys.PORTLET_METADATA_ALL), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE, "categories"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE, "comments"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE, "ratings"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE, "tags"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE,
			PortletDataHandlerKeys.PORTLET_METADATA_ALL), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "previews-and-thumbnails"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "ranks"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "shortcuts"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "categories"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "comments"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "ratings"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "tags"), true);

		return map;
	}

	protected User getFirstAdministatorUserForCompany(long companyId)
		throws Exception {

		Role role =
			RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.ADMINISTRATOR);

		List<User> users = UserLocalServiceUtil.getRoleUsers(
			role.getRoleId(), 0, 2);

		return users.get(0);
	}

	protected Map<java.lang.String, java.lang.String[]> getImportParameterMap(
		long groupId, long plid) {

		Map<java.lang.String, java.lang.String[]> map =
			new HashMap<String, String[]>();

		setAddParameterToMap(map, PortletDataHandlerKeys.CATEGORIES, false);

		setAddParameterToMap(
			map, PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);

		setAddParameterToMap(
			map, PortletDataHandlerKeys.DELETE_PORTLET_DATA, false);

		setAddParameterToMap(map, PortletDataHandlerKeys.PERMISSIONS, false);

		setAddParameterToMap(map, PortletDataHandlerKeys.PORTLET_DATA, true);

		setAddParameterToMap(
			map, PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, false);

		setAddParameterToMap(
			map, PortletDataHandlerKeys.USER_ID_STRATEGY,
			UserIdStrategy.CURRENT_USER_ID);

		// No option to write these

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE,
			PortletDataHandlerKeys.PORTLET_METADATA_ALL), "true");

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE, "categories"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE, "comments"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE, "ratings"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DDMPortletDataHandler.NAMESPACE, "tags"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE,
			PortletDataHandlerKeys.PORTLET_METADATA_ALL), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "categories"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "comments"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "ratings"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "tags"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "previews-and-thumbnails"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "ranks"), true);

		setAddParameterToMap(
			map,
			PortletDataHandlerControl.getNamespacedControlName(
			DLPortletDataHandler.NAMESPACE, "shortcuts"), true);

		return map;
	}

	protected byte[] readFile(String fileWithClasspath) throws IOException {
		return FileUtil.getBytes(getClass().getResourceAsStream(
			fileWithClasspath));
	}

	protected String readStructure(String structureFileWithClasspath)
		throws IOException {

		byte[] fileBytes =
			FileUtil.getBytes(getClass().getResourceAsStream(
				structureFileWithClasspath));

		return new String(fileBytes, "UTF-8");
	}

	protected void setAddParameterToMap(
		Map<java.lang.String, java.lang.String[]> map, String key,
		boolean value) {

		setAddParameterToMap(map, key, String.valueOf(value));
	}

	protected void setAddParameterToMap(
		Map<java.lang.String, java.lang.String[]> map, String key,
		String value) {

		map.put(key, new String[] {
			value
		});
	}

	protected void setDDMFieldValueToServiceToContext(
			ServiceContext sc, DDMStructure ddmStructure, String fieldName,
			String value)
		throws PortalException, SystemException {

		if (!ddmStructure.getFieldNames().contains(fieldName)) {
			throw new IllegalArgumentException(
				String.format("Structure id:%d does not contain fieldName %s",
				ddmStructure.getStructureId(), fieldName));
		}

		sc.setAttribute(
			String.format("%d%s", ddmStructure.getStructureId(), fieldName),
			value);
	}

	protected void testImportExport(
			boolean isGlobal, boolean isTargetDifferentCompany)
		throws Exception {

		// Prepare From Site

		User userFrom = getFirstAdministatorUserForCompany(
			_companyFrom.getCompanyId());

		Layout layoutFrom =
			createTestLayoutWithSite(
				_companyFrom.getCompanyId(), userFrom,
				ServiceTestUtil.randomString());

		addFilesWithFileTypesToSite(layoutFrom, isGlobal, userFrom);

		// Export

		Date startDate = null;
		Date endDate = null;

		_larFile =
			LayoutLocalServiceUtil.exportPortletInfoAsFile(
				layoutFrom.getPlid(), layoutFrom.getGroupId(),
				PortletKeys.DOCUMENT_LIBRARY, getExportParameterMap(),
				startDate, endDate);

		User userTo;
		Company companyTo;
		Group compareGroup = layoutFrom.getGroup();

		if (isTargetDifferentCompany) {
			companyTo = _companyTo;
			userTo = getFirstAdministatorUserForCompany(
				_companyTo.getCompanyId());
		}
		else {
			companyTo = _companyFrom;
			userTo = userFrom;
		}

		// Prepare to Site

		Layout layoutTo = createTestLayoutWithSite(
			companyTo.getCompanyId(), userTo, ServiceTestUtil.randomString());

		if (isGlobal && !isTargetDifferentCompany) {
			compareGroup = GroupLocalServiceUtil.getCompanyGroup(
				_companyFrom.getCompanyId());
		}
		else {
			compareGroup = layoutTo.getGroup();
		}

		// Import

		LayoutLocalServiceUtil.importPortletInfo(
			userTo.getUserId(), layoutTo.getPlid(), layoutTo.getGroupId(),
			PortletKeys.DOCUMENT_LIBRARY,
			getImportParameterMap(layoutTo.getPlid(), layoutTo.getGroupId()),
			_larFile);

		// Verify

		DLAppTestUtil.assertDlFoldersOfSites(
			layoutFrom.getGroupId(), layoutTo.getGroupId(),
			compareGroup.getGroupId());
	}

	private static Log _log = LogFactoryUtil.getLog(DLImportExportTest.class);

	private Company _companyFrom;
	private Company _companyTo;
	private int _counter = 0;
	private File _larFile;

}