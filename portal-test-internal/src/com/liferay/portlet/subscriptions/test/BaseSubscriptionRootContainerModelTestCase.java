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

package com.liferay.portlet.subscriptions.test;

import com.liferay.portal.kernel.test.util.MailServiceTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public abstract class BaseSubscriptionRootContainerModelTestCase
	extends BaseSubscriptionTestCase {

	@Test
	public void
			testSubscriptionRootContainerModelWhenAddingBaseModelInContainerModel()
		throws Exception {

		addSubscriptionContainerModel(
				user.getUserId(),
				PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			contextUser.getUserId(),
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(contextUser.getUserId(), containerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionRootContainerModelWhenAddingBaseModelInRootContainerModel()
		throws Exception {

		addSubscriptionContainerModel(
				user.getUserId(),
				PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(
			contextUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionRootContainerModelWhenAddingBaseModelInSubcontainerModel()
		throws Exception {

		addSubscriptionContainerModel(
				user.getUserId(),
				PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			contextUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(
			contextUser.getUserId(), containerModelId);

		addBaseModel(contextUser.getUserId(), subcontainerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionRootContainerModelWhenUpdatingBaseModelInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			contextUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(
			contextUser.getUserId(), containerModelId);

		addSubscriptionContainerModel(
				user.getUserId(),
				PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(contextUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionRootContainerModelWhenUpdatingBaseModelInRootContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(
			contextUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(
				user.getUserId(),
				PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(contextUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void
			testSubscriptionRootContainerModelWhenUpdatingBaseModelInSubcontainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			contextUser.getUserId(), PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(
			contextUser.getUserId(), containerModelId);

		long baseModelId = addBaseModel(
			contextUser.getUserId(), subcontainerModelId);

		addSubscriptionContainerModel(
			user.getUserId(),
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(contextUser.getUserId(), baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testAuthorSubscriptionsWhenAddingBaseModelInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			contextUser.getUserId(),
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(
				contextUser.getUserId(),
				PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(contextUser.getUserId(), containerModelId);

		if (isAuthorSubscriptionEnabled()){
			Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
		} else {
			Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
		}
	}

	@Test
	public void testAuthorSubscriptionsWhenUpdatingBaseModelInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			contextUser.getUserId(),
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(
			contextUser.getUserId(), containerModelId);

		addSubscriptionContainerModel(
			contextUser.getUserId(),
			PARENT_CONTAINER_MODEL_ID_DEFAULT);

		updateBaseModel(contextUser.getUserId(), baseModelId);

		if (isAuthorSubscriptionEnabled()){
			Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
		} else {
			Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
		}
	}

	protected boolean isAuthorSubscriptionEnabled() {
		return false;
	}

	protected abstract void addSubscriptionContainerModel(
			long userId, long containerModelId)
		throws Exception;

}