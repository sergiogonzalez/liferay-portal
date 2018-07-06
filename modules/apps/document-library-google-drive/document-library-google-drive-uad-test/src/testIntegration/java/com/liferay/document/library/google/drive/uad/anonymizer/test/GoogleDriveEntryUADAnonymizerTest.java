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

package com.liferay.document.library.google.drive.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.google.drive.model.GoogleDriveEntry;
import com.liferay.document.library.google.drive.service.GoogleDriveEntryLocalService;
import com.liferay.document.library.google.drive.uad.test.GoogleDriveEntryUADTestHelper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class GoogleDriveEntryUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<GoogleDriveEntry> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_googleDriveEntryUADTestHelper.cleanUpDependencies(_googleDriveEntries);
	}

	@Override
	protected GoogleDriveEntry addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected GoogleDriveEntry addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		GoogleDriveEntry googleDriveEntry =
			_googleDriveEntryUADTestHelper.addGoogleDriveEntry(userId);

		if (deleteAfterTestRun) {
			_googleDriveEntries.add(googleDriveEntry);
		}

		return googleDriveEntry;
	}

	@Override
	protected void deleteBaseModels(List<GoogleDriveEntry> baseModels)
		throws Exception {

		_googleDriveEntryUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		GoogleDriveEntry googleDriveEntry =
			_googleDriveEntryLocalService.getGoogleDriveEntry(baseModelPK);

		String userName = googleDriveEntry.getUserName();

		if ((googleDriveEntry.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_googleDriveEntryLocalService.fetchGoogleDriveEntry(baseModelPK) ==
				null) {

			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<GoogleDriveEntry> _googleDriveEntries =
		new ArrayList<>();

	@Inject
	private GoogleDriveEntryLocalService _googleDriveEntryLocalService;

	@Inject
	private GoogleDriveEntryUADTestHelper _googleDriveEntryUADTestHelper;

	@Inject(filter = "component.name=*.GoogleDriveEntryUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

}