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

import java.math.BigDecimal;

import java.text.DecimalFormat;

import org.junit.Test;

import org.testng.Assert;

/**
 * @author Roberto Díaz
 */
public class UnitConverterUtilTest {

	@Test
	public void testConvertFromBitsToGigaBytes() throws Exception {
		BigDecimal bits = new BigDecimal(9589934592L);

		Assert.assertEquals(
			"1,12", UnitConverterUtil.convertFromBitsToGigaBytes(bits));
	}

	@Test
	public void testConvertFromBitsToGigaBytesWithCustomDecimalFormat()
		throws Exception {

		BigDecimal bits = new BigDecimal(9589934592L);

		DecimalFormat df = new DecimalFormat("#.#####");

		Assert.assertEquals(
			"1,11642", UnitConverterUtil.convertFromBitsToGigaBytes(bits, df));
	}

	@Test
	public void testConvertFromBitsToGigaBytesWithNullDecimalFormat()
		throws Exception {

		BigDecimal bits = new BigDecimal(9589934592L);

		Assert.assertEquals(
			"1.1164153",
			UnitConverterUtil.convertFromBitsToGigaBytes(bits, null));
	}

	@Test
	public void testConvertFromBitsToKiloBits() throws Exception {
		BigDecimal bits = new BigDecimal(1500);

		Assert.assertEquals(
			"1,46", UnitConverterUtil.convertFromBitsToKiloBits(bits));
	}

	@Test
	public void testConvertFromBitsToKiloBitsWithCustomDecimalFormat()
		throws Exception {

		BigDecimal bits = new BigDecimal(1500);

		DecimalFormat df = new DecimalFormat("#.#####");

		Assert.assertEquals(
			"1,46484", UnitConverterUtil.convertFromBitsToKiloBits(bits, df));
	}

	@Test
	public void testConvertFromBitsToKiloBitsWithNullDecimalFormat()
		throws Exception {

		BigDecimal bits = new BigDecimal(1500);

		Assert.assertEquals(
			"1.4648438",
			UnitConverterUtil.convertFromBitsToKiloBits(bits, null));
	}

	@Test
	public void testConvertFromBitsToKiloBytes() throws Exception {
		BigDecimal bits = new BigDecimal(9176);

		Assert.assertEquals(
			"1,12", UnitConverterUtil.convertFromBitsToKiloBytes(bits));
	}

	@Test
	public void testConvertFromBitsToKiloBytesWithCustomDecimalFormat()
		throws Exception {

		BigDecimal bits = new BigDecimal(9176);

		DecimalFormat df = new DecimalFormat("#.#####");

		org.junit.Assert.assertEquals(
			"1,12012", UnitConverterUtil.convertFromBitsToKiloBytes(bits, df));
	}

	@Test
	public void testConvertFromBitsToKiloBytesWithNullDecimalFormat()
		throws Exception {

		BigDecimal bits = new BigDecimal(9176);

		Assert.assertEquals(
			"1.1201172",
			UnitConverterUtil.convertFromBitsToKiloBytes(bits, null));
	}

	@Test
	public void testConvertFromBitsToMegaBytes() throws Exception {
		BigDecimal bits = new BigDecimal(9395300);

		Assert.assertEquals(
			"1,12", UnitConverterUtil.convertFromBitsToMegaBytes(bits));
	}

	@Test
	public void testConvertFromBitsToMegaBytesWithCustomDecimalFormat()
		throws Exception {

		BigDecimal bits = new BigDecimal(9395300);

		DecimalFormat df = new DecimalFormat("#.#####");

		Assert.assertEquals(
			"1,12001",
			UnitConverterUtil.convertFromBitsToMegaBytes(bits, df));
	}

	@Test
	public void testConvertFromBitsToMegaBytesWithNullDecimalFormat()
		throws Exception {

		BigDecimal bits = new BigDecimal(9395300);

		Assert.assertEquals(
			"1.120007",
			UnitConverterUtil.convertFromBitsToMegaBytes(bits, null));
	}

}