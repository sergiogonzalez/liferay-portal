/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.lar;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lar.BasePortletExportImportTestCase;
import com.liferay.portal.lar.PortletImporter;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class WikiExportImportTest extends BasePortletExportImportTestCase {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		super.setUp();
	}

	@Test
	public void testExportImportBasicWikiPage() throws Exception {
		exportImportWikiPage();

		WikiPage importedWikiPage =
			WikiPageLocalServiceUtil.getWikiPageByUuidAndGroupId(
				_exportedPageUuid, _importedGroup.getGroupId());

		long importedWikiPageGroupId = importedWikiPage.getGroupId();

		Assert.assertNotEquals(_group.getGroupId(), importedWikiPageGroupId);
	}

	protected Map<String, String[]> getBaseParameterMap(long groupId, long plid)
		throws Exception {

		Map<String, String[]> parameterMap = new HashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_METADATA_ALL,
			new String[] {Boolean.TRUE.toString()});

		parameterMap.put("doAsGroupId", new String[] {String.valueOf(groupId)});
		parameterMap.put("groupId", new String[] {String.valueOf(groupId)});
		parameterMap.put(
			"permissionsAssignedToRoles",
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put("plid", new String[] {String.valueOf(plid)});
		parameterMap.put("portletResource", new String[] {PortletKeys.WIKI});

		return parameterMap;
	}

	protected Map<String, String[]> getExportParameterMap(
			long groupId, long plid)
		throws Exception {

		Map<String, String[]> parameterMap = getBaseParameterMap(groupId, plid);

		parameterMap.put(Constants.CMD, new String[] {Constants.EXPORT});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				PortletKeys.WIKI,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	protected Map<String, String[]> getImportParameterMap(
			long groupId, long plid)
		throws Exception {

		Map<String, String[]> parameterMap = getBaseParameterMap(groupId, plid);

		parameterMap.put(Constants.CMD, new String[] {Constants.IMPORT});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	private void exportImportWikiPage() throws Exception {
		long userId= TestPropsValues.getUserId();

		WikiNode node = WikiTestUtil.addNode(
			userId, _group.getGroupId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(50));

		WikiPage page = WikiTestUtil.addPage(
			userId, _group.getGroupId(), node.getNodeId(),
			ServiceTestUtil.randomString(), true);

		_exportedPageUuid = page.getUuid();

		Map<String, String[]> parameterMap = getExportParameterMap(
			_group.getGroupId(), _layout.getPlid());

		_larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
			_layout.getPlid(), _group.getGroupId(), PortletKeys.WIKI,
			parameterMap, null, null);

		_importedGroup = GroupTestUtil.addGroup();

		_importedLayout = LayoutTestUtil.addLayout(
			_importedGroup.getGroupId(), ServiceTestUtil.randomString());

		PortletImporter portletImporter = new PortletImporter();

		parameterMap = getImportParameterMap(
			_importedGroup.getGroupId(), _importedLayout.getPlid());

		portletImporter.importPortletInfo(
			TestPropsValues.getUserId(), _importedLayout.getPlid(),
			_importedGroup.getGroupId(), PortletKeys.WIKI, parameterMap,
			_larFile);
	}

	private String _exportedPageUuid;
	private File _larFile;

}