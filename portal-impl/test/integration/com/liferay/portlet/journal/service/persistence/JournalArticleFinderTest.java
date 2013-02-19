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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class JournalArticleFinderTest {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();

		_ddmStructure = JournalTestUtil.addDDMStructure(_group.getGroupId());

		DDMTemplate ddmTemplate = JournalTestUtil.addDDMTemplate(
			_group.getGroupId(), _ddmStructure.getStructureId());

		_folderA = JournalTestUtil.addFolder(_group.getGroupId(), "Folder A");

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), _folderA.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Article 1</title>", _ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), _folderA.getFolderId(), "Article 2",
			StringPool.BLANK);

		article.setUserId(1234);

		Calendar calendar = new GregorianCalendar();

		calendar.add(Calendar.DATE, -1);

		article.setExpirationDate(calendar.getTime());
		article.setReviewDate(calendar.getTime());

		JournalArticleLocalServiceUtil.updateJournalArticle(article);

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), _folderA.getFolderId(),
			PortalUtil.getClassNameId(JournalStructure.class),
			"<title>Article 3</title>", _ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		_folderB = JournalTestUtil.addFolder(_group.getGroupId(), "Folder B");

		_article = JournalTestUtil.addArticle(
			_group.getGroupId(), _folderB.getFolderId(), "Article 4",
			StringPool.BLANK);

		_article.setDisplayDate(calendar.getTime());

		_folderIds.clear();

		_folderIds.add(_folderA.getFolderId());
		_folderIds.add(_folderB.getFolderId());
	}

	@Test
	public void testCountByC_G_F_C_A_V_T_D_C_T_S_T_D_R() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			3,
			JournalArticleFinderUtil.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null,
				"Article", null, null, null, (String)null, null, null, null,
				null, true, queryDefinition));

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, null,
				null, null, null, _ddmStructure.getStructureKey(), null, null,
				null, null, true, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null,
				"Article", null, null, null, (String)null, null, null, null,
				null, true, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				PortalUtil.getClassNameId(JournalStructure.class), null, null,
				"Article", null, null, null, (String)null, null, null, null,
				null, true, queryDefinition));
	}

	@Test
	public void testCountByG_C_S() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				_ddmStructure.getStructureKey(), queryDefinition));

		Assert.assertEquals(
			2,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, "0",
				queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			0,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				_ddmStructure.getStructureKey(), queryDefinition));

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, "0",
				queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				_ddmStructure.getStructureKey(), queryDefinition));

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, "0",
				queryDefinition));
	}

	@Test
	public void testCountByG_F() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			4,
			JournalArticleFinderUtil.countByG_F(
				_group.getGroupId(), _folderIds, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_F(
				_group.getGroupId(), _folderIds, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			3,
			JournalArticleFinderUtil.countByG_F(
				_group.getGroupId(), _folderIds, queryDefinition));
	}

	@Test
	public void testCountByG_U_C() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			JournalArticleFinderUtil.countByG_U_C(
				_group.getGroupId(), TestPropsValues.getUserId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_U_C(
				_group.getGroupId(), 1234,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			0,
			JournalArticleFinderUtil.countByG_U_C(
				_group.getGroupId(), 1234,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition));
	}

	@Test
	public void testFindByExpirationDate() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<JournalArticle> articles =
			JournalArticleFinderUtil.findByExpirationDate(
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
				queryDefinition);

		Assert.assertEquals(1, articles.size());

		JournalArticle article = articles.get(0);

		Assert.assertEquals(1234, article.getUserId());

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		articles =
			JournalArticleFinderUtil.findByExpirationDate(
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
				queryDefinition);

		Assert.assertEquals(1, articles.size());

		article = articles.get(0);

		Assert.assertEquals(1234, article.getUserId());

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		articles =
			JournalArticleFinderUtil.findByExpirationDate(
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
				queryDefinition);

		Assert.assertEquals(0, articles.size());
	}

	@Test
	public void testFindByR_D() throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.add(Calendar.DATE, -2);

		JournalArticle article = JournalArticleFinderUtil.findByR_D(
			_article.getResourcePrimKey(), new Date());

		Assert.assertNotNull(article);

		Assert.assertEquals(_folderB.getFolderId(), article.getFolderId());
	}

	@Test
	public void testFindByReviewDate() throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.add(Calendar.DATE, -2);

		List<JournalArticle> articles =
			JournalArticleFinderUtil.findByReviewDate(
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
				calendar.getTime());

		Assert.assertEquals(1, articles.size());

		JournalArticle article = articles.get(0);

		Assert.assertEquals(1234, article.getUserId());
	}

	private JournalArticle _article;
	private DDMStructure _ddmStructure;
	private JournalFolder _folderA;
	private JournalFolder _folderB;
	private List<Long> _folderIds = new ArrayList<Long>();
	private Group _group;

}