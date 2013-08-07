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

package com.liferay.portal.search;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalIndexerTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	@Transactional
	public void testAddArticleApprove() throws Exception {
		addArticle(true);
	}

	@Test
	@Transactional
	public void testAddArticleNotApprove() throws Exception {
		addArticle(false);
	}

	@Test
	@Transactional
	public void testCopyArticle() throws Exception {
		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords("Architectural");

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		int initialBaseModelsSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		JournalArticleLocalServiceUtil.copyArticle(
			TestPropsValues.getUserId(), _group.getGroupId(),
			article.getArticleId(), StringPool.BLANK, true,
			article.getVersion());

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchCount(_group.getGroupId(), searchContext));
	}

	@Test
	@Transactional
	public void testDeleteAllArticleVersion() throws Exception {
		allArticleVersions(true);
	}

	@Test
	@Transactional
	public void testDeleteArticles() throws Exception {
		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords("Architectural");

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		int initialBaseModelsSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalArticle article1 = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		JournalTestUtil.updateArticle(
			article1, article1.getTitle(), "Architectural Approach",
			serviceContext);

		JournalArticle article2 = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Apple Architectural Tablet", true);

		Assert.assertEquals(
			initialBaseModelsSearchCount  + 2,
			searchCount(_group.getGroupId(), searchContext));

		JournalTestUtil.updateArticle(
			article2, article2.getTitle(), "Architectural Tablet",
			serviceContext);

		JournalArticleLocalServiceUtil.deleteArticles(_group.getGroupId());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchCount(_group.getGroupId(), searchContext));
	}

	@Test
	@Transactional
	public void testDeleteArticleVersion() throws Exception {
		articleVersion(true);
	}

	@Test
	@Transactional
	public void testExpireAllArticleVersions() throws Exception {
		allArticleVersions(false);
	}

	@Test
	@Transactional
	public void testExpireArticleVersion() throws Exception {
		articleVersion(false);
	}

	@Test
	@Transactional
	public void testIndexVersions() throws Exception {
		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		int initialBaseModelsSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		String content = "Liferay Architectural Approach";

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title", content, true);

		Assert.assertEquals(
			initialBaseModelsSearchCount  + 1,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		article = JournalTestUtil.updateArticle(
			article, article.getTitle(), content, serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		JournalTestUtil.updateArticle(
			article, article.getTitle(), content, serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 3,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));
	}

	@Test
	@Transactional
	public void testIndexVersionsDelete() throws Exception {
		indexVersionsDelete(false);
	}

	@Test
	@Transactional
	public void testIndexVersionsDeleteAll() throws Exception {
		indexVersionsDelete(true);
	}

	@Test
	@Transactional
	public void testIndexVersionsExpire() throws Exception {
		indexVersionsExpire(false);
	}

	@Test
	@Transactional
	public void testIndexVersionsExpireAll() throws Exception {
		indexVersionsExpire(true);
}

	@Test
	@Transactional
	public void testMoveArticle() throws Exception {
		moveArticle(false);
	}

	@Test
	@Transactional
	public void testMoveArticleFromTrash() throws Exception {
		moveArticle(true);
	}

	@Test
	@Transactional
	public void testMoveArticleToTrashAndRestore() throws Exception {
		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords("Architectural");

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		int initialBaseModelsSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchCount(_group.getGroupId(), searchContext));

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchCount(
				_group.getGroupId(), true, WorkflowConstants.STATUS_IN_TRASH,
				searchContext));

		JournalArticleLocalServiceUtil.restoreArticleFromTrash(
			TestPropsValues.getUserId(), article);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchCount(
				_group.getGroupId(), true, WorkflowConstants.STATUS_IN_TRASH,
				searchContext));
	}

	@Test
	@Transactional
	public void testRemoveArticleLocale() throws Exception {
		SearchContext searchContext1 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Arquitectura");
		searchContext1.setLocale(LocaleUtil.SPAIN);

		SearchContext searchContext2 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Architectural");
		searchContext2.setLocale(LocaleUtil.SPAIN);

		int initialBaseModelsSearchCountKeywords1 = searchCount(
			_group.getGroupId(), searchContext1);
		int initialBaseModelsSearchCountKeywords2 = searchCount(
			_group.getGroupId(), searchContext2);

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(LocaleUtil.US, "Title");
		titleMap.put(LocaleUtil.GERMANY, "Titel");
		titleMap.put(LocaleUtil.SPAIN, "Titulo");

		Map<Locale, String> contentMap = new HashMap<Locale, String>();

		contentMap.put(LocaleUtil.US, "Liferay Architectural Approach");
		contentMap.put(LocaleUtil.GERMANY, "Liferay Architektur Ansatz");
		contentMap.put(LocaleUtil.SPAIN, "Liferay Arquitectura Aproximacion");

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), titleMap, titleMap, contentMap, true);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		JournalArticleLocalServiceUtil.removeArticleLocale(
			_group.getGroupId(), article.getArticleId(), article.getVersion(),
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN));

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1,
			searchCount(_group.getGroupId(), searchContext1));

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords2 + 1,
			searchCount(_group.getGroupId(), searchContext2));
	}

	@Test
	@Transactional
	public void testUpdateArticleAndApprove() throws Exception {
		updateArticle(true);
	}

	@Test
	@Transactional
	public void testUpdateArticleAndDraft() throws Exception {
		updateArticle(false);
	}

	@Test
	@Transactional
	public void testUpdateArticleTranslation() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		SearchContext searchContext1 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Arquitectura");
		searchContext1.setLocale(LocaleUtil.SPAIN);

		SearchContext searchContext2 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Apple");
		searchContext2.setLocale(LocaleUtil.SPAIN);

		int initialBaseModelsSearchCountKeywords1 = searchCount(
			_group.getGroupId(), searchContext1);
		int initialBaseModelsSearchCountKeywords2 = searchCount(
			_group.getGroupId(), searchContext2);

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(LocaleUtil.US, "Title");
		titleMap.put(LocaleUtil.GERMANY, "Titel");
		titleMap.put(LocaleUtil.SPAIN, "Titulo");

		Map<Locale, String> contentMap = new HashMap<Locale, String>();

		contentMap.put(LocaleUtil.US, "Liferay Architectural Approach");
		contentMap.put(LocaleUtil.GERMANY, "Liferay Architektur Ansatz");
		contentMap.put(LocaleUtil.SPAIN, "Liferay Arquitectura Aproximacion");

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), titleMap, titleMap, contentMap, true);

		User user = UserTestUtil.addUser(_group.getGroupId(), LocaleUtil.SPAIN);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		contentMap.put(LocaleUtil.SPAIN, "Apple manzana tablet");

		String content = JournalTestUtil.createLocalizedContent(
			contentMap, LocaleUtil.getDefault());

		article = JournalArticleLocalServiceUtil.updateArticleTranslation(
			_group.getGroupId(), article.getArticleId(), article.getVersion(),
			LocaleUtil.SPAIN, article.getTitle(LocaleUtil.SPAIN),
			article.getDescription(LocaleUtil.SPAIN), content, null,
			serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords2,
			searchCount(_group.getGroupId(), searchContext2));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		JournalArticleLocalServiceUtil.updateArticle(
			user.getUserId(), article.getGroupId(), article.getFolderId(),
			article.getArticleId(), article.getVersion(), article.getContent(),
			serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords2 + 1,
			searchCount(_group.getGroupId(), searchContext2));
	}

	@Test
	@Transactional
	public void testUpdateBasicContent() throws Exception {
		updateContent(true);
	}

	@Test
	@Transactional
	public void testUpdateStructuredContent() throws Exception {
		updateContent(false);
	}

	protected void addArticle(boolean approve) throws Exception {
		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords("Architectural");

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		int initialBaseModelsSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", approve);

		if (approve) {
			Assert.assertEquals(
				initialBaseModelsSearchCount + 1,
				searchCount(_group.getGroupId(), searchContext));
		}
		else {
			Assert.assertEquals(
				initialBaseModelsSearchCount,
				searchCount(_group.getGroupId(), searchContext));
		}
	}

	protected JournalArticle addJournalWithDDMStructure(
			long folderId, String keywords, ServiceContext serviceContext)
		throws Exception {

		String xsd = DDMStructureTestUtil.getSampleStructureXSD("name");

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			serviceContext.getScopeGroupId(), JournalArticle.class.getName(),
			xsd);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			serviceContext.getScopeGroupId(), ddmStructure.getStructureId());

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			"name", keywords);

		return JournalTestUtil.addArticleWithXMLContent(
			folderId, content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), serviceContext);
	}

	protected void allArticleVersions(boolean delete) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		SearchContext searchContext1 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Architectural");

		SearchContext searchContext2 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Apple");

		int initialBaseModelsSearchCountKeywords1 = searchCount(
			_group.getGroupId(), searchContext1);
		int initialBaseModelsSearchCountKeywords2 = searchCount(
			_group.getGroupId(), searchContext2);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		article = JournalTestUtil.updateArticle(
			article, article.getTitle(), "Apple tablet", serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords2 + 1,
			searchCount(_group.getGroupId(), searchContext2));

		if (delete) {
			JournalArticleLocalServiceUtil.deleteArticle(
				_group.getGroupId(), article.getArticleId(), serviceContext);
		}
		else {
			JournalArticleLocalServiceUtil.expireArticle(
				TestPropsValues.getUserId(), _group.getGroupId(),
				article.getArticleId(), article.getUrlTitle(), serviceContext);
		}

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords2,
			searchCount(_group.getGroupId(), searchContext2));

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1,
			searchCount(_group.getGroupId(), searchContext1));
	}

	protected void articleVersion(boolean delete) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		SearchContext searchContext1 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Architectural");

		SearchContext searchContext2 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Apple");

		int initialBaseModelsSearchCountKeywords1 = searchCount(
			_group.getGroupId(), searchContext1);
		int initialBaseModelsSearchCountKeywords2 = searchCount(
			_group.getGroupId(), searchContext2);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		article = JournalTestUtil.updateArticle(
			article, article.getTitle(), "Apple tablet", serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1,
			searchCount(_group.getGroupId(), searchContext1));

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords2 + 1,
			searchCount(_group.getGroupId(), searchContext2));

		if (delete) {
			JournalArticleLocalServiceUtil.deleteArticle(article);
		}
		else {
			JournalArticleLocalServiceUtil.expireArticle(
				TestPropsValues.getUserId(), _group.getGroupId(),
				article.getArticleId(), article.getVersion(),
				article.getUrlTitle(), serviceContext);
		}

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords2,
			searchCount(_group.getGroupId(), searchContext2));

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1 + 1,
			searchCount(_group.getGroupId(), searchContext1));
	}

	protected void indexVersionsDelete(boolean deleteAll) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		int initialBaseModelsSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		String content = "Liferay Architectural Approach";

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title", content, true);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		article = JournalTestUtil.updateArticle(
			article, article.getTitle(), content, serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));

		if (deleteAll) {
			JournalArticleLocalServiceUtil.deleteArticle(
				_group.getGroupId(), article.getArticleId(), serviceContext);

			Assert.assertEquals(
				initialBaseModelsSearchCount,
				searchCount(
					_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
					searchContext));
		}
		else {
			JournalArticleLocalServiceUtil.deleteArticle(article);

			Assert.assertEquals(
				initialBaseModelsSearchCount + 1,
				searchCount(
					_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
					searchContext));
		}
	}

	protected void indexVersionsExpire(boolean expireAll) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		int initialBaseModelsSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		String content = "Liferay Architectural Approach";

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title", content, true);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		article = JournalTestUtil.updateArticle(
			article, article.getTitle(), content, serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));

		if (expireAll) {
			JournalArticleLocalServiceUtil.expireArticle(
				TestPropsValues.getUserId(), _group.getGroupId(),
				article.getArticleId(), article.getUrlTitle(), serviceContext);
		}
		else {
			JournalArticleLocalServiceUtil.expireArticle(
				TestPropsValues.getUserId(), _group.getGroupId(),
				article.getArticleId(), article.getVersion(),
				article.getUrlTitle(), serviceContext);
		}

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));
	}

	protected void moveArticle(boolean moveToTrash) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		JournalFolder newFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		SearchContext searchContext1 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Architectural");
		searchContext1.setFolderIds(new long[]{folder.getFolderId()});

		SearchContext searchContext2 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Architectural");
		searchContext2.setFolderIds(new long[]{newFolder.getFolderId()});

		int initialBaseModelsSearchCountFolders1 = searchCount(
			_group.getGroupId(), searchContext1);
		int initialBaseModelsSearchCountFolders2 = searchCount(
			_group.getGroupId(), searchContext2);

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialBaseModelsSearchCountFolders1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		if (moveToTrash) {
			JournalArticleLocalServiceUtil.moveArticleToTrash(
				TestPropsValues.getUserId(), article);

			Assert.assertEquals(
				initialBaseModelsSearchCountFolders1,
				searchCount(_group.getGroupId(), searchContext1));

			JournalArticleLocalServiceUtil.moveArticleFromTrash(
				TestPropsValues.getUserId(), _group.getGroupId(), article,
				newFolder.getFolderId(), serviceContext);
		}
		else {
			JournalArticleLocalServiceUtil.moveArticle(
				_group.getGroupId(), article.getArticleId(),
				newFolder.getFolderId());
		}

		Assert.assertEquals(
			initialBaseModelsSearchCountFolders1,
			searchCount(_group.getGroupId(), searchContext1));

		Assert.assertEquals(
			initialBaseModelsSearchCountFolders2 + 1,
			searchCount(_group.getGroupId(), searchContext2));
	}

	protected int searchCount(
			long groupId, boolean head, int status, SearchContext searchContext)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		searchContext.setAttribute("head", head);
		searchContext.setAttribute("status", status);
		searchContext.setGroupIds(new long[] {groupId});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	protected int searchCount(long groupId, SearchContext searchContext)
		throws Exception {

		return searchCount(
			groupId, true, WorkflowConstants.STATUS_APPROVED, searchContext);
	}

	protected void updateArticle(boolean approve) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		SearchContext searchContext1 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Architectural");

		SearchContext searchContext2 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Apple");

		int initialBaseModelsSearchCountKeywords1 = searchCount(
			_group.getGroupId(), searchContext1);
		int initialBaseModelsSearchCountKeywords2 = searchCount(
			_group.getGroupId(), searchContext2);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		if (!approve) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		JournalTestUtil.updateArticle(
			article, article.getTitle(), "Apple tablet", serviceContext);

		if (approve) {
			Assert.assertEquals(
				initialBaseModelsSearchCountKeywords1,
				searchCount(_group.getGroupId(), searchContext1));

			Assert.assertEquals(
				initialBaseModelsSearchCountKeywords2 + 1,
				searchCount(_group.getGroupId(), searchContext2));
		}
		else {
			Assert.assertEquals(
				initialBaseModelsSearchCountKeywords1 + 1,
				searchCount(_group.getGroupId(), searchContext1));

			Assert.assertEquals(
				initialBaseModelsSearchCountKeywords2,
				searchCount(_group.getGroupId(), searchContext2));
		}
	}

	protected void updateContent(boolean basicContent) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		SearchContext searchContext1 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Architectural");

		SearchContext searchContext2 = ServiceTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Liferay");

		int initialBaseModelsSearchCountKeywords1 = searchCount(
			_group.getGroupId(), searchContext1);
		int initialBaseModelsSearchCountKeywords2 = searchCount(
			_group.getGroupId(), searchContext2);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		String content = "Liferay Architectural Approach";

		JournalArticle article = null;

		if (basicContent) {
			article = JournalTestUtil.addArticleWithWorkflow(
				_group.getGroupId(), folder.getFolderId(), "title", content,
				true);
		}
		else {
			article = addJournalWithDDMStructure(
				folder.getFolderId(), content, serviceContext);
		}

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		if (basicContent) {
			content = JournalTestUtil.createLocalizedContent(
				"Architectural Approach", LocaleUtil.getDefault());
		}
		else {
			content = DDMStructureTestUtil.getSampleStructuredContent(
				"name", "Architectural Approach");
		}

		JournalArticleLocalServiceUtil.updateContent(
			_group.getGroupId(), article.getArticleId(), article.getVersion(),
			content);

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		Assert.assertEquals(
			initialBaseModelsSearchCountKeywords2,
			searchCount(_group.getGroupId(), searchContext2));
	}

	private Group _group;

}