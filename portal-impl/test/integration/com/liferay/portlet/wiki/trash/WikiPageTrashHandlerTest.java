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

package com.liferay.portlet.wiki.trash;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;
import com.liferay.portlet.wiki.asset.WikiPageAssetRenderer;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiPageTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Override
	public void testTrashAndDeleteDraft() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashAndRestoreDraft() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		String pageTitle = getSearchKeywords().concat(
			ServiceTestUtil.randomString(255 - getSearchKeywords().length()));

		WikiPage page = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(),
			(Long)parentBaseModel.getPrimaryKeyObj(), pageTitle,
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			true, serviceContext);

		WikiPageLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), page.getResourcePrimKey(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		return page;
	}

	@Override
	protected Long getAssetClassPK(ClassedModel classedModel) {
		return WikiPageAssetRenderer.getClassPK((WikiPage)classedModel);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return WikiPageLocalServiceUtil.getPageByPageId(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return WikiPage.class;
	}

	@Override
	protected String getBaseModelName(ClassedModel classedModel) {
		WikiPage page = (WikiPage)classedModel;

		return page.getTitle();
	}

	@Override
	protected int getBaseModelsNotInTrashCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return WikiPageLocalServiceUtil.getPagesCount(
			(Long)parentBaseModel.getPrimaryKeyObj(), true,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_APPROVED);

		return WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(75),
			ServiceTestUtil.randomString(), serviceContext);
	}

	@Override
	protected Class<?> getParentBaseModelClass() {
		return WikiNode.class;
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		WikiPage page = (WikiPage)baseModel;

		return page.getTitle();
	}

	@Override
	protected long getTrashEntryClassPK(ClassedModel classedModel) {
		WikiPage page = (WikiPage)classedModel;

		return page.getResourcePrimKey();
	}

	@Override
	protected boolean isBaseModelMoveableFromTrash() {
		return false;
	}

	@Override
	protected boolean isInTrashFolder(ClassedModel classedModel)
		throws Exception {

		WikiPage page = (WikiPage)classedModel;

		return page.isInTrashFolder();
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPageByPageId(primaryKey);

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle());
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		WikiNodeLocalServiceUtil.moveNodeToTrash(
			TestPropsValues.getUserId(), primaryKey);
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		WikiPage page = WikiPageLocalServiceUtil.getPageByPageId(primaryKey);

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return WikiPageLocalServiceUtil.updatePage(
			TestPropsValues.getUserId(), page.getNodeId(), getSearchKeywords(),
			page.getVersion(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), false, page.getFormat(),
			page.getParentTitle(), page.getRedirectTitle(), serviceContext);
	}

}