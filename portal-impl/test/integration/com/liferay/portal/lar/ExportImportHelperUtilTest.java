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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class ExportImportHelperUtilTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_liveGroup = GroupTestUtil.addGroup();
		_stagingGroup = GroupTestUtil.addGroup();

		_fileEntry = DLAppTestUtil.addFileEntry(
			_stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString() + ".txt",
			ServiceTestUtil.randomString(), true);

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)_fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		dlFileEntry.setLargeImageId(dlFileEntry.getFileEntryId());

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		_portletDataContextExport =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				_stagingGroup.getCompanyId(), _stagingGroup.getGroupId(),
				new HashMap<String, String[]>(),
				new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
				testReaderWriter);

		Element rootElement = SAXReaderUtil.createElement("root");

		_portletDataContextExport.setExportDataRootElement(rootElement);

		_portletDataContextImport =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_stagingGroup.getCompanyId(), _stagingGroup.getGroupId(),
				new HashMap<String, String[]>(),
				new CurrentUserIdStrategy(TestPropsValues.getUser()),
				testReaderWriter);

		_portletDataContextImport.setImportDataRootElement(rootElement);
		_portletDataContextImport.setSourceGroupId(_stagingGroup.getGroupId());

		rootElement.addElement("entry");

		_referrerStagedModel = JournalTestUtil.addArticle(
			_stagingGroup.getGroupId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString());
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_liveGroup);
		GroupLocalServiceUtil.deleteGroup(_stagingGroup);
	}

	@Test
	public void testExportDLReferences() throws Exception {
		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		String content = replaceParameters(
			getContent("dl_references.txt"), _fileEntry);

		List<String> urls = getURLs(content);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel,
			rootElement.element("entry"), content, true);

		for (String url : urls) {
			Assert.assertFalse(content.contains(url));
		}

		TestReaderWriter testReaderWriter =
			(TestReaderWriter)_portletDataContextExport.getZipWriter();

		List<String> entries = testReaderWriter.getEntries();

		Assert.assertEquals(entries.size(), 1);

		List<String> binaryEntries = testReaderWriter.getBinaryEntries();

		Assert.assertEquals(binaryEntries.size(), entries.size());

		for (String entry : testReaderWriter.getEntries()) {
			Assert.assertTrue(
				content.contains("[$dl-reference=" + entry + "$]"));
		};
	}

	@Test
	public void testExportLayoutReferencesWithContext() throws Exception {
		PortalImpl portalImpl = spy(new PortalImpl());

		when(
			portalImpl.getPathContext()
		).thenReturn("/de");

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portalImpl);

		_OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

		setFinalStaticField(
			PropsValues.class.getField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			"/en");

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			"/en/");

		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel,
			rootElement.element("entry"), content, true);

		Assert.assertFalse(
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING));
		Assert.assertFalse(
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING));
		Assert.assertFalse(content.contains(_stagingGroup.getFriendlyURL()));
		Assert.assertFalse(content.contains(PortalUtil.getPathContext()));
		Assert.assertFalse(content.contains("/en/en"));

		setFinalStaticField(
			PropsValues.class.getDeclaredField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			_OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
				StringPool.SLASH);

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testExportLayoutReferencesWithoutContext() throws Exception {
		_OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

		setFinalStaticField(
			PropsValues.class.getField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			"/en");

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"), "/en/");

		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel,
			rootElement.element("entry"), content, true);

		Assert.assertFalse(
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING));
		Assert.assertFalse(
			content.contains(
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING));
		Assert.assertFalse(content.contains(_stagingGroup.getFriendlyURL()));
		Assert.assertFalse(content.contains("/en/en"));

		setFinalStaticField(
			PropsValues.class.getDeclaredField(
				"LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING"),
			_OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);

		setFinalStaticField(
			ExportImportHelperImpl.class.getDeclaredField(
				"_PRIVATE_USER_SERVLET_MAPPING"),
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING +
				StringPool.SLASH);
	}

	@Test
	public void testExportLinksToLayouts() throws Exception {
		Layout publicLayout = LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), ServiceTestUtil.randomString(), false);
		Layout privateLayout = LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), ServiceTestUtil.randomString(), true);

		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		String content = replaceParameters(
			getContent("layout_links.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel,
			rootElement.element("entry"), content, true);

		StringBundler sb = new StringBundler(5);

		sb.append("[1@private-group@");
		sb.append(privateLayout.getUuid());
		sb.append(StringPool.AT);
		sb.append(privateLayout.getFriendlyURL());
		sb.append(StringPool.CLOSE_BRACKET);

		Assert.assertTrue(content.contains(sb.toString()));

		sb.setIndex(0);

		sb.append("[1@public@");
		sb.append(publicLayout.getUuid());
		sb.append(StringPool.AT);
		sb.append(publicLayout.getFriendlyURL());
		sb.append(StringPool.CLOSE_BRACKET);

		Assert.assertTrue(content.contains(sb.toString()));
	}

	@Test
	public void testImportDLReferences() throws Exception {
		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		Element entryElement = rootElement.element("entry");

		String content = replaceParameters(
			getContent("dl_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, entryElement,
			content, true);
		content = ExportImportHelperUtil.replaceImportContentReferences(
			_portletDataContextImport, entryElement, content, true);

		Assert.assertFalse(content.contains("[$dl-reference="));
	}

	@Test
	public void testImportLayoutReferences() throws Exception {
		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		Element entryElement = rootElement.element("entry");

		String content = replaceParameters(
			getContent("layout_references.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, entryElement,
			content, true);
		content = ExportImportHelperUtil.replaceImportContentReferences(
			_portletDataContextExport, entryElement, content, true);

		Assert.assertFalse(
			content.contains("@data_handler_group_friendly_url@"));
		Assert.assertFalse(content.contains("@data_handler_path_context@"));
		Assert.assertFalse(
			content.contains("@data_handler_private_group_servlet_mapping@"));
		Assert.assertFalse(
			content.contains("@data_handler_private_user_servlet_mapping@"));
		Assert.assertFalse(
			content.contains("@data_handler_public_servlet_mapping@"));
	}

	@Test
	public void testImportLinksToLayouts() throws Exception {
		LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), ServiceTestUtil.randomString(), false);
		LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), ServiceTestUtil.randomString(), true);

		Element rootElement =
			_portletDataContextExport.getExportDataRootElement();

		Element entryElement = rootElement.element("entry");

		String content = replaceParameters(
			getContent("layout_links.txt"), _fileEntry);

		content = ExportImportHelperUtil.replaceExportContentReferences(
			_portletDataContextExport, _referrerStagedModel, entryElement,
			content, true);

		String importedContent =
			ExportImportHelperUtil.replaceExportContentReferences(
				_portletDataContextExport, _referrerStagedModel, entryElement,
				content, true);

		Assert.assertEquals(importedContent, content);
	}

	@Test
	public void testValidateMissingReferences() throws Exception {
		String xml = replaceParameters(
			getContent("missing_references.txt"), _fileEntry);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		zipWriter.addEntry("/manifest.xml", xml);

		MissingReferences missingReferences =
			ExportImportHelperUtil.validateMissingReferences(
				TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
				new HashMap<String, String[]>(), zipWriter.getFile());

		Map<String, MissingReference> dependencyMissingReferences =
			missingReferences.getDependencyMissingReferences();

		Map<String, MissingReference> weakMissingReferences =
			missingReferences.getWeakMissingReferences();

		Assert.assertEquals(2, dependencyMissingReferences.size());
		Assert.assertEquals(1, weakMissingReferences.size());

		FileUtil.delete(zipWriter.getFile());
	}

	protected String getContent(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		Scanner scanner = new Scanner(inputStream);

		scanner.useDelimiter("\\Z");

		return scanner.next();
	}

	protected List<String> getURLs(String content) {
		Pattern pattern = Pattern.compile(
			"(?:href=|\\{|\\[)(.*?)(?:>|\\}|\\]|Link\\]\\])");

		Matcher matcher = pattern.matcher(content);

		List<String> urls = new ArrayList<String>();

		while (matcher.find()) {
			String url = matcher.group(1);

			urls.add(url);
		}

		return urls;
	}

	protected String replaceParameters(String content, FileEntry fileEntry) {
		return StringUtil.replace(
			content,
			new String[] {
				"[$GROUP_FRIENDLY_URL$]", "[$GROUP_ID$]", "[$IMAGE_ID$]",
				"[$PATH_CONTEXT$]", "[$PATH_FRIENDLY_URL_PRIVATE_GROUP$]",
				"[$PATH_FRIENDLY_URL_PRIVATE_USER$]",
				"[$PATH_FRIENDLY_URL_PUBLIC$]", "[$TITLE$]", "[$UUID$]"
			},
			new String[] {
				_stagingGroup.getFriendlyURL(),
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getFileEntryId()),
				PortalUtil.getPathContext(),
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
				fileEntry.getTitle(), fileEntry.getUuid()
			});
	}

	protected void setFinalStaticField(Field field, Object newValue)
		throws Exception {

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);
	}

	private static String _OLD_LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private FileEntry _fileEntry;
	private Group _liveGroup;
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private StagedModel _referrerStagedModel;
	private Group _stagingGroup;

}