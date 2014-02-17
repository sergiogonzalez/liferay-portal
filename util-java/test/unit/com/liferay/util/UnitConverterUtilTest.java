/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class UnitConverterUtilTest {

	@Test
	public void testConvertFromBitsToGigaBytes() throws Exception {
		BigDecimal bits = new BigDecimal(9589934592L);

		Assert.assertEquals(
			"1,12", UnitConverterUtil.convertFromBitsToGigaBytes(
				bits, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.12", UnitConverterUtil.convertFromBitsToGigaBytes(
				bits, LocaleUtil.US));

		DecimalFormat df = new DecimalFormat("#.#####");

		Assert.assertEquals(
			"1,11642", UnitConverterUtil.convertFromBitsToGigaBytes(
				bits, df, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.11642", UnitConverterUtil.convertFromBitsToGigaBytes(
				bits, df, LocaleUtil.US));

		Assert.assertEquals(
			"1.1164153",
			UnitConverterUtil.convertFromBitsToGigaBytes(
				bits, null, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.1164153",
			UnitConverterUtil.convertFromBitsToGigaBytes(
				bits, null, LocaleUtil.US));
	}

	@Test
	public void testConvertFromBitsToKiloBits() throws Exception {
		BigDecimal bits = new BigDecimal(1500);

		Assert.assertEquals(
			"1,46", UnitConverterUtil.convertFromBitsToKiloBits(
				bits, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.46", UnitConverterUtil.convertFromBitsToKiloBits(
				bits, LocaleUtil.US));

		DecimalFormat df = new DecimalFormat("#.#####");

		Assert.assertEquals(
			"1,46484", UnitConverterUtil.convertFromBitsToKiloBits(
				bits, df, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.46484", UnitConverterUtil.convertFromBitsToKiloBits(
				bits, df, LocaleUtil.US));

		Assert.assertEquals(
			"1.4648438",
			UnitConverterUtil.convertFromBitsToKiloBits(
				bits, null, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.4648438",
			UnitConverterUtil.convertFromBitsToKiloBits(
				bits, null, LocaleUtil.US));
	}

	@Test
	public void testConvertFromBitsToKiloBytes() throws Exception {
		BigDecimal bits = new BigDecimal(9176);

		Assert.assertEquals(
			"1,12", UnitConverterUtil.convertFromBitsToKiloBytes(
				bits, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.12", UnitConverterUtil.convertFromBitsToKiloBytes(
				bits, LocaleUtil.US));

		DecimalFormat df = new DecimalFormat("#.#####");

		org.junit.Assert.assertEquals(
			"1,12012", UnitConverterUtil.convertFromBitsToKiloBytes(
				bits, df, LocaleUtil.SPAIN));

		org.junit.Assert.assertEquals(
			"1.12012", UnitConverterUtil.convertFromBitsToKiloBytes(
				bits, df, LocaleUtil.US));

		Assert.assertEquals(
			"1.1201172",
			UnitConverterUtil.convertFromBitsToKiloBytes(
				bits, null, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.1201172",
			UnitConverterUtil.convertFromBitsToKiloBytes(
				bits, null, LocaleUtil.US));
	}

	@Test
	public void testConvertFromBitsToMegaBytes() throws Exception {
		BigDecimal bits = new BigDecimal(9395300);

		Assert.assertEquals(
			"1,12", UnitConverterUtil.convertFromBitsToMegaBytes(
				bits, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.12", UnitConverterUtil.convertFromBitsToMegaBytes(
				bits, LocaleUtil.US));

		DecimalFormat df = new DecimalFormat("#.#####");

		Assert.assertEquals(
			"1,12001",
			UnitConverterUtil.convertFromBitsToMegaBytes(
				bits, df, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.12001",
			UnitConverterUtil.convertFromBitsToMegaBytes(
				bits, df, LocaleUtil.US));

		Assert.assertEquals(
			"1.120007",
			UnitConverterUtil.convertFromBitsToMegaBytes(
				bits, null, LocaleUtil.SPAIN));

		Assert.assertEquals(
			"1.120007",
			UnitConverterUtil.convertFromBitsToMegaBytes(
				bits, null, LocaleUtil.US));
	}

}