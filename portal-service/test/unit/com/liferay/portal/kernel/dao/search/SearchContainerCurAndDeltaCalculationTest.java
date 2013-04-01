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

package com.liferay.portal.kernel.dao.search;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class SearchContainerCurAndDeltaCalculationTest {

	@After
	public void tearDown() {
		_stubSearchContainer = null;
	}

	@Test
	public void testCalculateCurUsingBigValues() {
		int cur = 341;
		int delta = 20;
		int start = 0;
		int total = 1001;

		_stubSearchContainer = new StubSearchContainer(
			cur, delta, start, total);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(51, _stubSearchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalBiggerThanCurLessOneMultiplyByDelta() {
		int cur = 35;
		int delta = 20;
		int start = 0;
		int total = 999;

		_stubSearchContainer = new StubSearchContainer(
			cur, delta, start, total);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(35, _stubSearchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalEqualsDelta() {
		int cur = 2;
		int delta = 5;
		int start = 0;
		int total = 5;

		_stubSearchContainer = new StubSearchContainer(
			cur, delta, start, total);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(1, _stubSearchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalEqualsZero() {
		int cur = 2;
		int delta = 5;
		int start = 0;
		int total = 0;

		_stubSearchContainer = new StubSearchContainer(
			cur, delta, start, total);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(1, _stubSearchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalLessThanDelta() {
		int cur = 2;
		int delta = 5;
		int start = 0;
		int total = 1;

		_stubSearchContainer = new StubSearchContainer(
			cur, delta, start, total);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(1, _stubSearchContainer.getCur());
	}

	@Test
	public void testCalculateCurWhenTotalMoreThanDelta() {
		int cur = 5;
		int delta = 5;
		int start = 0;
		int total = 16;

		_stubSearchContainer = new StubSearchContainer(
			cur, delta, start, total);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(4, _stubSearchContainer.getCur());
	}

	private StubSearchContainer _stubSearchContainer;

	private class StubSearchContainer {

		public StubSearchContainer(int cur, int delta, int start, int total) {
			_cur = cur;
			_delta = delta;
			_start = start;
			_total = total;
		}

		public int getCur() {
			return _cur;
		}

		public int getStart() {
			return _start;
		}

		protected void calculateCur() {
			if (_total == 0) {
				_cur = DEFAULT_CUR;
				return;
			}

			if (((_cur - 1) * _delta) >= _total) {
				if ((_total % _delta) == 0) {
					_cur = (_total / _delta);
				}
				else {
					_cur = (_total / _delta) + 1;
				}
			}
		}

		public static final int DEFAULT_CUR = 1;

		private int _cur = 0;
		private int _delta = 0;
		private int _start = 0;
		private int _total = 0;

	}

}