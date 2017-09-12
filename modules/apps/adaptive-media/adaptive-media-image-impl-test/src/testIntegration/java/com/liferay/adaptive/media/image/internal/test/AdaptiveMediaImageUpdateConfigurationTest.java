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

package com.liferay.adaptive.media.image.internal.test;

import com.liferay.adaptive.media.exception.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class AdaptiveMediaImageUpdateConfigurationTest
	extends BaseAdaptiveMediaImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testSendsAMessageToTheMessageBus() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		List<Message> messages = collectConfigurationMessages(
			() ->
				adaptiveMediaImageConfigurationHelper.
					updateAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1", "two", "twodesc",
						"2", properties));

		Assert.assertEquals(messages.toString(), 1, messages.size());

		Message message = messages.get(0);

		AdaptiveMediaImageConfigurationEntry[] configurationEntries =
			(AdaptiveMediaImageConfigurationEntry[])message.getPayload();

		AdaptiveMediaImageConfigurationEntry oldConfigurationEntry =
			configurationEntries[0];

		AdaptiveMediaImageConfigurationEntry newConfigurationEntry =
			configurationEntries[1];

		Assert.assertEquals(
			configurationEntry.getName(), oldConfigurationEntry.getName());
		Assert.assertEquals(
			configurationEntry.getDescription(),
			oldConfigurationEntry.getDescription());
		Assert.assertEquals(
			configurationEntry.getUUID(), oldConfigurationEntry.getUUID());
		Assert.assertEquals(
			configurationEntry.getProperties(),
			oldConfigurationEntry.getProperties());

		Assert.assertEquals("two", newConfigurationEntry.getName());
		Assert.assertEquals("twodesc", newConfigurationEntry.getDescription());
		Assert.assertEquals("2", newConfigurationEntry.getUUID());
		Assert.assertEquals(properties, newConfigurationEntry.getProperties());
	}

	@Test
	public void testUpdateConfigurationEntryWithBlankDescription()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "desc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", StringPool.BLANK,
				"1", configurationEntry1.getProperties());

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Assert.assertEquals(
			StringPool.BLANK, configurationEntry.getDescription());
	}

	@Test
	public void testUpdateConfigurationEntryWithBlankMaxHeight()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Map<String, String> actualProperties =
			configurationEntry.getProperties();

		Assert.assertEquals("0", actualProperties.get("max-height"));
		Assert.assertEquals("200", actualProperties.get("max-width"));
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithBlankMaxHeightOnly()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test
	public void testUpdateConfigurationEntryWithBlankMaxWidth()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Map<String, String> actualProperties =
			configurationEntry.getProperties();

		Assert.assertEquals("200", actualProperties.get("max-height"));
		Assert.assertEquals("0", actualProperties.get("max-width"));
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithBlankMaxWidthAndBlankMaxHeight()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "");
		properties.put("max-width", "");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithBlankMaxWidthAndZeroMaxHeight()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "");
		properties.put("max-width", "0");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithBlankMaxWidthOnly()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-width", "");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.InvalidNameException.class
	)
	public void testUpdateConfigurationEntryWithBlankName() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "desc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", StringPool.BLANK, "desc",
				"1", configurationEntry1.getProperties());
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.InvalidUuidException.class
	)
	public void testUpdateConfigurationEntryWithBlankUuid() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "desc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "two", "desc",
				StringPool.BLANK, configurationEntry1.getProperties());
	}

	@Test
	public void testUpdateConfigurationEntryWithColonSemicolonDescription()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc:;desc", "1",
				properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Assert.assertEquals("desc:;desc", configurationEntry.getDescription());
	}

	@Test
	public void testUpdateConfigurationEntryWithColonSemicolonName()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one:;one", "desc", "1",
				properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Assert.assertEquals("one:;one", configurationEntry.getName());
	}

	@Test
	public void testUpdateConfigurationEntryWithMaxHeightOnly()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Map<String, String> actualProperties =
			configurationEntry.getProperties();

		Assert.assertEquals("200", actualProperties.get("max-height"));
		Assert.assertEquals("0", actualProperties.get("max-width"));
	}

	@Test
	public void testUpdateConfigurationEntryWithMaxWidthOnly()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Map<String, String> actualProperties =
			configurationEntry.getProperties();

		Assert.assertEquals("0", actualProperties.get("max-height"));
		Assert.assertEquals("200", actualProperties.get("max-width"));
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.InvalidHeightException.class
	)
	public void testUpdateConfigurationEntryWithNegativeNumberMaxHeight()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "-10");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.InvalidWidthException.class
	)
	public void testUpdateConfigurationEntryWithNegativeNumberMaxWidth()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-width", "-10");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.InvalidHeightException.class
	)
	public void testUpdateConfigurationEntryWithNotNumberMaxHeight()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "Invalid");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.InvalidWidthException.class
	)
	public void testUpdateConfigurationEntryWithNotNumberMaxWidth()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-width", "Invalid");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithoutMaxHeightNorMaxWidth()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test
	public void testUpdateConfigurationEntryWithZeroMaxHeight()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "0");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Map<String, String> actualProperties =
			configurationEntry.getProperties();

		Assert.assertEquals("0", actualProperties.get("max-height"));
		Assert.assertEquals("200", actualProperties.get("max-width"));
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithZeroMaxHeightOnly()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "0");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test
	public void testUpdateConfigurationEntryWithZeroMaxWidth()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "0");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		Map<String, String> actualProperties =
			configurationEntry.getProperties();

		Assert.assertEquals("200", actualProperties.get("max-height"));
		Assert.assertEquals("0", actualProperties.get("max-width"));
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithZeroMaxWidthAndBlankMaxHeight()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "0");
		properties.put("max-width", "");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithZeroMaxWidthAndZeroMaxHeight()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "0");
		properties.put("max-width", "0");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testUpdateConfigurationEntryWithZeroMaxWidthOnly()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-width", "0");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one", "desc", "1",
				properties);
	}

	@Test
	public void testUpdateDisabledConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "desc", "1",
					properties);

		adaptiveMediaImageConfigurationHelper.
			disableAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), configurationEntry.getUUID());

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertDisabled(configurationEntryOptional);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one-bis", "desc-bis",
				"1-bis", configurationEntry.getProperties());

		configurationEntryOptional =
			adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1-bis");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		assertDisabled(configurationEntryOptional);

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry =
			configurationEntryOptional.get();

		Assert.assertEquals("one-bis", actualConfigurationEntry.getName());
		Assert.assertEquals(
			"desc-bis", actualConfigurationEntry.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.DuplicateAdaptiveMediaImageConfigurationNameException.class
	)
	public void testUpdateDuplicateConfigurationEntryName() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				properties);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "two", "twodesc", "1",
				configurationEntry1.getProperties());
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.DuplicateAdaptiveMediaImageConfigurationUuidException.class
	)
	public void testUpdateDuplicateConfigurationEntryUuid() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "twodesc", "2",
				properties);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "two-bis", "twodesc", "2",
				configurationEntry1.getProperties());
	}

	@Test
	public void testUpdateFirstConfigurationEntryName() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "two", "twodesc", "2",
					properties);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1", "one-bis", "onedesc-bis",
				"1", configurationEntry1.getProperties());

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals("one-bis", actualConfigurationEntry1.getName());
		Assert.assertEquals(
			"onedesc-bis", actualConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());
		Assert.assertEquals(
			configurationEntry2.getDescription(),
			actualConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test
	public void testUpdateFirstConfigurationEntryProperties() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "two", "twodesc", "2",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "500");
		properties.put("max-width", "800");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), configurationEntry1.getUUID(),
				configurationEntry1.getName(),
				configurationEntry1.getDescription(),
				configurationEntry1.getUUID(), properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());
		Assert.assertEquals(
			configurationEntry1.getDescription(),
			actualConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"500", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"800", actualConfigurationEntry1Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());
		Assert.assertEquals(
			configurationEntry2.getDescription(),
			actualConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test
	public void testUpdateFirstConfigurationEntryUuid() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "two", "twodesc", "2",
					properties);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1",
				configurationEntry1.getName(),
				configurationEntry1.getDescription(), "1-bis",
				configurationEntry1.getProperties());

		Optional<AdaptiveMediaImageConfigurationEntry>
			nonExistantConfigurationEntry1Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(nonExistantConfigurationEntry1Optional.isPresent());

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1-bis");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());
		Assert.assertEquals(
			configurationEntry1.getDescription(),
			actualConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());
		Assert.assertEquals(
			configurationEntry2.getDescription(),
			actualConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.NoSuchAdaptiveMediaImageConfigurationException.class
	)
	public void testUpdateNonExistingConfiguration() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2", "two", "twodesc", "2",
				configurationEntry1.getProperties());
	}

	@Test
	public void testUpdateSecondConfigurationEntryName() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "two", "twodesc", "2",
					properties);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2", "two-bis", "twodesc-bis",
				"2", configurationEntry2.getProperties());

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals("two-bis", actualConfigurationEntry2.getName());
		Assert.assertEquals(
			"twodesc-bis", actualConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());
		Assert.assertEquals(
			configurationEntry1.getDescription(),
			actualConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test
	public void testUpdateSecondConfigurationEntryProperties()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "two", "twodesc", "2",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "500");
		properties.put("max-width", "800");

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), configurationEntry2.getUUID(),
				configurationEntry2.getName(),
				configurationEntry2.getDescription(),
				configurationEntry2.getUUID(), properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());
		Assert.assertEquals(
			configurationEntry2.getDescription(),
			actualConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"500", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"800", actualConfigurationEntry2Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());
		Assert.assertEquals(
			configurationEntry1.getDescription(),
			actualConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test
	public void testUpdateSecondConfigurationEntryUuid() throws Exception {
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper = serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "onedesc", "1",
					properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			adaptiveMediaImageConfigurationHelper.
				addAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "two", "twodesc", "2",
					properties);

		adaptiveMediaImageConfigurationHelper.
			updateAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2",
				configurationEntry2.getName(),
				configurationEntry2.getDescription(), "2-bis",
				configurationEntry2.getProperties());

		Optional<AdaptiveMediaImageConfigurationEntry>
			nonExistantConfigurationEntry2Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(nonExistantConfigurationEntry2Optional.isPresent());

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "2-bis");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());
		Assert.assertEquals(
			configurationEntry2.getDescription(),
			actualConfigurationEntry2.getDescription());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());
		Assert.assertEquals(
			configurationEntry1.getDescription(),
			actualConfigurationEntry1.getDescription());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

}