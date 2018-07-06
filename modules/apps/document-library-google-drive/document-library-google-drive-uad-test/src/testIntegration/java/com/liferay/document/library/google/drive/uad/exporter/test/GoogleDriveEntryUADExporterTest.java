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

package com.liferay.document.library.google.drive.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.document.library.google.drive.model.GoogleDriveEntry;
import com.liferay.document.library.google.drive.uad.test.GoogleDriveEntryUADTestHelper;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class GoogleDriveEntryUADExporterTest extends BaseUADExporterTestCase<GoogleDriveEntry> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_googleDriveEntryUADTestHelper.cleanUpDependencies(_googleDriveEntries);
	}

	@Override
	protected GoogleDriveEntry addBaseModel(long userId)
		throws Exception {
		GoogleDriveEntry googleDriveEntry = _googleDriveEntryUADTestHelper.addGoogleDriveEntry(userId);

		_googleDriveEntries.add(googleDriveEntry);

		return googleDriveEntry;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "entryId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<GoogleDriveEntry> _googleDriveEntries = new ArrayList<GoogleDriveEntry>();
	@Inject
	private GoogleDriveEntryUADTestHelper _googleDriveEntryUADTestHelper;
	@Inject(filter = "component.name=*.GoogleDriveEntryUADExporter")
	private UADExporter _uadExporter;
}