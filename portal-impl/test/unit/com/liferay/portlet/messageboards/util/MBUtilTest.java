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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author John Zhao
 */
public class MBUtilTest {

	@Test
	public void testGetCategoryId() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "POP_SERVER_SUBDOMAIN", StringPool.BLANK);

		Assert.assertEquals(
			10640,
			MBUtil.getCategoryId(
				"<mb_message.10640.20646.1425017183884@gmail.com>"));

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "POP_SERVER_SUBDOMAIN", "events");

		Assert.assertEquals(
			10640,
			MBUtil.getCategoryId(
				"mb_message.10640.20646.1425017183884@event.gmail.com"));
	}

	@Test
	public void testGetMessageId() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "POP_SERVER_SUBDOMAIN", StringPool.BLANK);

		Assert.assertEquals(
			20646,
			MBUtil.getMessageId(
				"<mb_message.10640.20646.1425017183884@gmail.com>"));

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "POP_SERVER_SUBDOMAIN", "events");

		Assert.assertEquals(
			20646,
			MBUtil.getMessageId(
				"mb_message.10640.20646.1425017183884@event.gmail.com"));
	}

}