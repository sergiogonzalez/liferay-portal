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
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.lar.DDMPortletDataHandler;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

/**
 * @author Sampsa Sohlman
 */
public class DLBaseExportImportTest {

	@Before
	public void setUp() throws Exception {
		Map<String, String[]> _parameterMap = new HashMap<String, String[]>();

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
			DLAppTestUtil.addFileEntryType(
				fileEntryTypeGroupId, user, "contract fields",
				readStructure("dependencies/ddm-structure-1.xml"));

		long rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		Folder folder = DLAppTestUtil.addFolder(
			layout.getGroupId(), rootFolderId, "First");

		DLAppTestUtil.addFileEntryWithDDMValues(
			folder.getFolderId(), fileEntryType.getFileEntryTypeId(),
			layout.getGroupId(), user.getUserId(), "ö-tiedosto.txt",
			readFile("dependencies/file-scandinavic-chars-ISO-8859-1.bin"),
			new ObjectValuePair<String, String>("superHero", "super"));

		DLAppTestUtil.addFileEntryWithDDMValues(
			folder.getFolderId(), fileEntryType.getFileEntryTypeId(),
			layout.getGroupId(), user.getUserId(), "alphabeth-file.txt",
			readFile("dependencies/file-alphabeth.txt"),
			new ObjectValuePair<String, String>("superHero", "spider"));

		DLAppTestUtil.addFileEntryWithDDMValues(
			rootFolderId, fileEntryType.getFileEntryTypeId(),
			layout.getGroupId(), user.getUserId(), "special-chars-file.txt",
			readFile("dependencies/file-special-chars-utf-8.bin"),
			new ObjectValuePair<String, String>("superHero", "bat"));
	}

	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();

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

	protected Map<String, String[]> getImportParameterMap(
		long groupId, long plid) {

		Map<String, String[]> map = new HashMap<String, String[]>();

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
			FileUtil.getBytes(
				getClass().getResourceAsStream(structureFileWithClasspath));

		return new String(fileBytes, "UTF-8");
	}

	protected Map<String, String[]> setAddParameterToMap(
		Map<String, String[]> map, String key, boolean value) {

		setAddParameterToMap(map, key, String.valueOf(value));
		return map;
	}

	protected Map<String, String[]> setAddParameterToMap(
		Map<String, String[]> map, String key, String value) {

		map.put(key, new String[] {
			value
		});

		return map;
	}

	protected Company _companyFrom;
	protected Company _companyTo;
	protected File _larFile;

	private static Log _log = LogFactoryUtil.getLog(
		DLBaseExportImportTest.class);

}