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
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import java.util.Date;

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
public class DLExportImportFileEntryTypeTest extends DLBaseExportImportTest {

	@Test
	public void testWithGlobalTypeAndDifferentCompany() throws Exception {
		testImportExport(true, true);
	}

	@Test
	public void testWithGlobalTypeAndSameCompany() throws Exception {
		testImportExport(true, false);
	}

	@Test
	public void testWithLocalTypeAndDifferentCompany() throws Exception {
		testImportExport(false, true);
	}

	@Test
	public void testWithLocalTypeAndSameCompany() throws Exception {
		testImportExport(false, false);
	}

	protected void testImportExport(
			boolean isGlobal, boolean isTargetDifferentCompany)
		throws Exception {

		// Prepare "From" Site

		User userFrom = UserTestUtil.getFirstAdministatorUserForCompany(
			_companyFrom.getCompanyId());

		Layout layoutFrom =
			LayoutTestUtil.createTestLayoutWithSite(
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

		if (isTargetDifferentCompany) {
			companyTo = _companyTo;
			userTo = UserTestUtil.getFirstAdministatorUserForCompany(
				_companyTo.getCompanyId());
		}
		else {
			companyTo = _companyFrom;
			userTo = userFrom;
		}

		// Prepare "To" Site

		Layout layoutTo = LayoutTestUtil.createTestLayoutWithSite(
			companyTo.getCompanyId(), userTo, ServiceTestUtil.randomString());

		long expectedFileEntryTypeGroupId = -1;

		if (isGlobal && !isTargetDifferentCompany) {
			expectedFileEntryTypeGroupId =
				GroupLocalServiceUtil.getCompanyGroup(
					_companyFrom.getCompanyId()).getGroupId();
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
			expectedFileEntryTypeGroupId);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLExportImportFileEntryTypeTest.class);

}