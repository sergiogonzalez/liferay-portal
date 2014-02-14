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

/**
 * @author Roberto Díaz
 */
public class UnitConverterUtil {

	public static final long BITS_IN_BYTE = 8L;

	public static final long BITS_IN_KILO = 1024L;

	public static String convertFromBitsToGigaBytes(BigDecimal bits) {
		return convertFromBitsToGigaBytes(bits, _defaultDecimalFormat);
	}

	public static String convertFromBitsToGigaBytes(
		BigDecimal bits, DecimalFormat decimalFormat) {

		BigDecimal gigaBytes = bits.divide(
			new BigDecimal(
				BITS_IN_BYTE * BITS_IN_KILO * BITS_IN_KILO * BITS_IN_KILO));

		if (decimalFormat != null) {
			return NumberFormatUtil.format(
				decimalFormat, gigaBytes.floatValue(), 0L);
		}

		return String.valueOf(gigaBytes.floatValue());
	}

	public static String convertFromBitsToKiloBits(BigDecimal bits) {
		return convertFromBitsToKiloBits(bits, _defaultDecimalFormat);
	}

	public static String convertFromBitsToKiloBits(
			BigDecimal bits, DecimalFormat decimalFormat) {

		BigDecimal kiloBits = bits.divide(new BigDecimal(BITS_IN_KILO));

		if (decimalFormat != null) {
			return NumberFormatUtil.format(
				decimalFormat, kiloBits.floatValue(), 0L);
		}

		return String.valueOf(kiloBits.floatValue());
	}

	public static String convertFromBitsToKiloBytes(BigDecimal bits) {
		return convertFromBitsToKiloBytes(bits, _defaultDecimalFormat);
	}

	public static String convertFromBitsToKiloBytes(
		BigDecimal bits, DecimalFormat decimalFormat) {

		BigDecimal kiloBytes = bits.divide(
			new BigDecimal(BITS_IN_BYTE * BITS_IN_KILO));

		if (decimalFormat != null) {
			return NumberFormatUtil.format(
				decimalFormat, kiloBytes.floatValue(), 0L);
		}

		return String.valueOf(kiloBytes.floatValue());
	}

	public static String convertFromBitsToMegaBytes(BigDecimal bits) {
		return convertFromBitsToMegaBytes(bits, _defaultDecimalFormat);
	}

	public static String convertFromBitsToMegaBytes(
		BigDecimal bits, DecimalFormat decimalFormat) {

		BigDecimal megaBytes = bits.divide(
			new BigDecimal(BITS_IN_BYTE * BITS_IN_KILO * BITS_IN_KILO));

		if (decimalFormat != null) {
			return NumberFormatUtil.format(
			decimalFormat, megaBytes.floatValue(), 0L);
		}

		return String.valueOf(megaBytes.floatValue());
	}

	private static DecimalFormat _defaultDecimalFormat;

	static {
		_defaultDecimalFormat = new DecimalFormat("#.##");
	}

}