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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.UserTestUtil;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This integration test was made for issue LPS-32967
 *
 * @author Sampsa Sohlman
 */
@ExecutionTestListeners(listeners = {
	MainServletExecutionTestListener.class,
	TransactionalExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DLExportImportToEmptyPortalTest extends DLBaseExportImportTest {

	@Test
	public void testWhenExportedDataDoesNotExist() throws Exception {

		// Prepare From Site

		User userFrom = UserTestUtil.getFirstAdministatorUserForCompany(
			_companyFrom.getCompanyId());

		Layout layoutFrom = LayoutTestUtil.createTestLayoutWithSite(
			_companyFrom.getCompanyId(), userFrom,
			ServiceTestUtil.randomString());

		addFilesWithFileTypesToSite(layoutFrom, true, userFrom);

		// Export

		Date startDate = null;
		Date endDate = null;

		_larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
			layoutFrom.getPlid(), layoutFrom.getGroupId(),
			PortletKeys.DOCUMENT_LIBRARY, getExportParameterMap(), startDate,
			endDate);

		Layout layoutTo = LayoutTestUtil.createTestLayoutWithSite(
			_companyFrom.getCompanyId(), userFrom,
			ServiceTestUtil.randomString());

		// Delete original group

		GroupLocalServiceUtil.deleteGroup(layoutFrom.getGroup());

		User userTo = userFrom;
		Company companyTo = _companyFrom;

		// Verify

		LayoutLocalServiceUtil.importPortletInfo(
			userTo.getUserId(), layoutTo.getPlid(), layoutTo.getGroupId(),
			PortletKeys.DOCUMENT_LIBRARY,
			getImportParameterMap(layoutTo.getPlid(), layoutTo.getGroupId()),
			_larFile);

		Assert.assertTrue("Import was success",true);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLExportImportToEmptyPortalTest.class);

}