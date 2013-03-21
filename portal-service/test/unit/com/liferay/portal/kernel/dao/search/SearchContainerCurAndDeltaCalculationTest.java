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
import org.junit.Before;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class SearchContainerCurAndDeltaCalculationTest {

	@Before
	public void setUp() {
		_stubSearchContainer = new StubSearchContainer(5, 30);
	}

	@After
	public void tearDown() {
		_stubSearchContainer = null;
	}

	@Test
	public void testCalculateCur() throws Exception {
		_stubSearchContainer.setCur(6);
		_stubSearchContainer.setTotal(7);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(2, _stubSearchContainer.getCur());
	}

	@Test
	public void testCalculateCur2() throws Exception {
		_stubSearchContainer.setCur(6);
		_stubSearchContainer.setTotal(17);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(4, _stubSearchContainer.getCur());
	}

	@Test
	public void testCalculateCur3() throws Exception {
		_stubSearchContainer.setCur(StubSearchContainer.DEFAULT_CUR);
		_stubSearchContainer.setTotal(0);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(
			StubSearchContainer.DEFAULT_CUR, _stubSearchContainer.getCur());
	}

	@Test
	public void testCalculateCur4() throws Exception {
		_stubSearchContainer.setCur(StubSearchContainer.DEFAULT_CUR);
		_stubSearchContainer.setTotal(100);

		_stubSearchContainer.calculateCur();

		Assert.assertEquals(
			StubSearchContainer.DEFAULT_CUR, _stubSearchContainer.getCur());
	}

	@Test
	public void testCalculateStartAndEnd() throws Exception {
		_stubSearchContainer.setCur(6);
		_stubSearchContainer.setTotal(7);

		_stubSearchContainer.calculateCur();
		_stubSearchContainer.calculateStartAndEnd();

		Assert.assertEquals(5, _stubSearchContainer.getStart());
	}

	@Test
	public void testCalculateStartAndEnd2() throws Exception {
		_stubSearchContainer.setCur(6);
		_stubSearchContainer.setTotal(17);

		_stubSearchContainer.calculateCur();
		_stubSearchContainer.calculateStartAndEnd();

		Assert.assertEquals(15, _stubSearchContainer.getStart());
	}

	@Test
	public void testCalculateStartAndEnd3() throws Exception {
		_stubSearchContainer.setCur(StubSearchContainer.DEFAULT_CUR);
		_stubSearchContainer.setTotal(0);

		_stubSearchContainer.calculateCur();
		_stubSearchContainer.calculateStartAndEnd();

		Assert.assertEquals(0, _stubSearchContainer.getStart());
	}

	@Test
	public void testCalculateStartAndEnd4() throws Exception {
		_stubSearchContainer.setCur(StubSearchContainer.DEFAULT_CUR);
		_stubSearchContainer.setTotal(100);

		_stubSearchContainer.calculateCur();
		_stubSearchContainer.calculateStartAndEnd();

		Assert.assertEquals(0, _stubSearchContainer.getStart());
	}

	private StubSearchContainer _stubSearchContainer;

	private class StubSearchContainer {

		public StubSearchContainer(int delta, int start) {
			_delta = delta;
			_start = start;
		}

		public int getCur() {
			return _cur;
		}

		public int getStart() {
			return _start;
		}

		public void setEnd(int end) {
			_end = end;
		}

		public void setCur(int cur) {
			_cur = cur;
		}

		public void setTotal(int total) {
			_total = total;
		}

		protected void calculateCur() {
			if (((_cur - 1) * _delta) >= _total) {
				_cur = (_total /_delta) + 1;
			}
		}

		protected void calculateStartAndEnd() {
			_start = (_cur - 1) * _delta;
			_end = _start + _delta;

			if (_end > _total) {
				_end = _total;
			}
		}

		public static final int DEFAULT_CUR = 1;

		private int _cur = 0;
		private int _delta = 0;
		private int _end = 0;
		private int _start = 0;
		private int _total = 0;

	}

}