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

package com.liferay.wiki.lar;

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BasePortletDataHandlerTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.util.test.WikiTestUtil;

import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@Override
	protected void addStagedModels() throws Exception {
		WikiNode node = WikiTestUtil.addNode(stagingGroup.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(stagingGroup.getGroupId());

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		return new WikiPortletDataHandler();
	}

	@Override
	protected String getPortletId() {
		return WikiPortletKeys.WIKI;
	}

}