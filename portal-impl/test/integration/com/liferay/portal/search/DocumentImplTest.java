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

package com.liferay.portal.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.usersadmin.util.UserIndexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <a href="DocumentImplTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Daniel Sanz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DocumentImplTest {

	public static final String MULTI_DOUBLE = "md";

	public static final String MULTI_FLOAT = "mf";

	public static final String MULTI_INT = "mi";

	public static final String MULTI_LONG = "ml";

	public static final String SINGLE_DOUBLE = "sd";

	public static final String SINGLE_FLOAT = "sf";

	public static final String SINGLE_INT = "si";

	public static final String SINGLE_LONG = "sl";

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup("testSearch");

		_indexer = IndexerRegistryUtil.getIndexer(UserIndexer.class);

		_indexer.registerIndexerPostProcessor(
			new ExtendedUserIndexerPostProcessor());

		_users.put("firstuser", new NumbersHolder(
			0.0000000000001, new Long(Integer.MIN_VALUE - 9L)));

		_users.put("seconduser",new NumbersHolder(
			0.0000020000002, new Long(Integer.MIN_VALUE - 8L)));

		_users.put("thirduser", new NumbersHolder(
			0.0000000000003, new Long(Integer.MIN_VALUE - 7L)));

		_users.put("fourthuser",new NumbersHolder(
			0.0000040000004, Integer.MIN_VALUE + 7));

		_users.put("fifthuser", new NumbersHolder(
			0.0000000000005, Integer.MIN_VALUE + 8));

		_users.put("sixthuser", new NumbersHolder(
			0.0000060000006, Integer.MIN_VALUE + 9));

		for (String screenName : _users.keySet()) {
			User user = ServiceTestUtil.addUser(
				screenName, _group.getGroupId());

			user.setFirstName(screenName.replaceFirst("user", ""));
			user.setLastName("Smith");
			user.setStatus(WorkflowConstants.STATUS_APPROVED);

			UserLocalServiceUtil.updateUser(user);

			_indexer.reindex(user);
		}
	}

	@Test
	public void testSearchResultsCount() throws Exception {
		checkNumberOfSearchResults(buildSearchContext("first"), 1);
		checkNumberOfSearchResults(buildSearchContext("second"), 1);
		checkNumberOfSearchResults(buildSearchContext("third"), 1);
		checkNumberOfSearchResults(buildSearchContext("fourth"), 1);
		checkNumberOfSearchResults(buildSearchContext("fifth"), 1);
		checkNumberOfSearchResults(buildSearchContext("sixth"), 1);
	}

	@Test
	public void testSearchSortedByDouble() throws Exception {
		String[] expected1 =
			new String[] {"firstuser", "thirduser", "fifthuser", "seconduser",
				"fourthuser", "sixthuser"};

		checkSearchesSortedByDouble("Smith", expected1);

		String[] expected2 =
			new String[] {"firstuser", "thirduser", "fifthuser", "sixthuser"};

		checkSearchesSortedByDouble("third sixth fifth first", expected2);
		checkSearchesSortedByDouble("sixth fifth first third", expected2);
		checkSearchesSortedByDouble("fifth first third sixth", expected2);
		checkSearchesSortedByDouble("first third sixth fifth", expected2);
	}

	@Test
	public void testSearchSortedByFloat() throws Exception {
		String[] expected1 =
			new String[] {"firstuser", "seconduser", "fourthuser", "sixthuser"};

		checkSearchesSortedByFloat("sixth second first fourth", expected1);
		checkSearchesSortedByFloat("second first fourth sixth", expected1);
		checkSearchesSortedByFloat("first fourth sixth second", expected1);
		checkSearchesSortedByFloat("fourth sixth second first", expected1);
	}

	@Test
	public void testSearchSortedByInteger() throws Exception {
		String[] expected1 =
			new String[] {"fourthuser", "fifthuser", "sixthuser", "firstuser",
				"seconduser", "thirduser"};

		checkSearchesSortedByInteger("Smith", expected1);

		String[] expected2 =
			new String[] {"fourthuser", "fifthuser", "sixthuser"};

		checkSearchesSortedByInteger("fourth fifth sixth", expected2);
		checkSearchesSortedByInteger("fifth sixth fourth", expected2);
		checkSearchesSortedByInteger("sixth fourth fifth", expected2);
	}

	@Test
	public void testSearchSortedByLong() throws Exception {
		String[] expected1 =
			new String[] {"firstuser", "seconduser", "thirduser", "fourthuser",
				"fifthuser", "sixthuser"};

		checkSearchesSortedByLong("Smith", expected1);
		checkSearchesSortedByLong(
			"sixth second first fourth fifth third", expected1);
		checkSearchesSortedByLong(
			"second first fourth fifth third sixth", expected1);
		checkSearchesSortedByLong(
			"first fourth fifth third sixth second", expected1);
		checkSearchesSortedByLong(
			"fourth fifth third sixth second first", expected1);
		checkSearchesSortedByLong(
			"fifth third sixth second first fourth", expected1);
		checkSearchesSortedByLong(
			"third sixth second first fourth fifth", expected1);
	}

	protected SearchContext buildSearchContext(String keywords)
		throws Exception {
		SearchContext searchContext = ServiceTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[] {});
		searchContext.setKeywords(keywords);
		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);

		return searchContext;
	}

	protected void checkNumberOfSearchResults(Hits results, long expectedHits)
		throws Exception {

		if (expectedHits >= 0) {
			Assert.assertEquals(results.getLength(), expectedHits);
		}
	}

	protected void checkNumberOfSearchResults(
			SearchContext searchContext, long expectedHits)
		throws Exception {

		checkNumberOfSearchResults(
			_indexer.search(searchContext), expectedHits);
	}

	protected void checkSearchesSortedByDouble(
		String keywords, String[] expectedAsc) throws Exception {

		String[] expectedDsc = reverse(expectedAsc);

		SearchContext searchContext = buildSearchContext(keywords);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(SINGLE_DOUBLE, Sort.DOUBLE_TYPE, false),
			expectedAsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(SINGLE_DOUBLE, Sort.DOUBLE_TYPE, true),
			expectedDsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(MULTI_DOUBLE, Sort.DOUBLE_TYPE, false),
			expectedAsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(MULTI_DOUBLE, Sort.DOUBLE_TYPE, true),
			expectedDsc);
	}

	protected void checkSearchesSortedByFloat(
		String keywords, String[] expectedAsc) throws Exception {

		String[] expectedDsc = reverse(expectedAsc);

		SearchContext searchContext = buildSearchContext(keywords);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(SINGLE_FLOAT, Sort.FLOAT_TYPE, false),
			expectedAsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(SINGLE_FLOAT, Sort.FLOAT_TYPE, true),
			expectedDsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(MULTI_FLOAT, Sort.FLOAT_TYPE, false),
			expectedAsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(MULTI_FLOAT, Sort.FLOAT_TYPE, true),
			expectedDsc);
	}

	protected void checkSearchesSortedByInteger(
		String keywords, String[] expectedAsc) throws Exception {

		String[] expectedDsc = reverse(expectedAsc);

		SearchContext searchContext = buildSearchContext(keywords);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(SINGLE_INT, Sort.INT_TYPE, false),
			expectedAsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(SINGLE_INT, Sort.INT_TYPE, true),
			expectedDsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(MULTI_INT, Sort.INT_TYPE, false),
			expectedAsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(MULTI_INT, Sort.INT_TYPE, true),
			expectedDsc);
	}

	protected void checkSearchesSortedByLong(
		String keywords, String[] expectedAsc) throws Exception {

		String[] expectedDsc = reverse(expectedAsc);

		SearchContext searchContext = buildSearchContext(keywords);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(SINGLE_LONG, Sort.LONG_TYPE, false),
			expectedAsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(SINGLE_LONG, Sort.LONG_TYPE, true),
			expectedDsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(MULTI_LONG, Sort.LONG_TYPE, false),
			expectedAsc);

		checkSortedSearchResultsOrder(
			searchContext,
			SortFactoryUtil.create(MULTI_LONG, Sort.LONG_TYPE, true),
			expectedDsc);
	}

	protected void checkSearchResultsData(Hits results) {
		for (Document doc : results.getDocs()) {
			NumbersHolder orig = _users.get(doc.get("screenName"));
			NumbersHolder indexed = new NumbersHolder(doc);

			Assert.assertEquals(
				orig.getSingleDouble(), indexed.getSingleDouble(), 0);
			Assert.assertEquals(
				orig.getSingleLong(), indexed.getSingleLong(), 0);
			Assert.assertEquals(
				orig.getSingleFloat(), indexed.getSingleFloat(), 0);
			Assert.assertEquals(
				orig.getSingleInteger(), indexed.getSingleInteger(), 0);
			Assert.assertArrayEquals(
				orig.getMultiDouble(), indexed.getMultiDouble());
			Assert.assertArrayEquals(
				orig.getMultiLong(), indexed.getMultiLong());
			Assert.assertArrayEquals(
				orig.getMultiFloat(), indexed.getMultiFloat());
			Assert.assertArrayEquals(
				orig.getMultiInteger(), indexed.getMultiInteger());
		}
	}

	protected void checkSortedSearchResultsOrder(
			SearchContext searchContext, Sort sort, String[] expected)
		throws Exception {

		Query query = _indexer.getFullQuery(searchContext);

		Hits results = SearchEngineUtil.search(
			searchContext.getSearchEngineId(), searchContext.getCompanyId(),
			query, sort, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		checkNumberOfSearchResults(results, expected.length);

		for (int i = 0; i < expected.length; i++) {
			Assert.assertEquals(results.doc(i).get("screenName"), expected[i]);
		}

		checkSearchResultsData(results);
	}

	protected String[] reverse(String[] array) {
		String[] reversed = Arrays.copyOf(array, array.length);

		Collections.reverse(Arrays.asList(reversed));

		return reversed;
	}

	private Group _group;

	private Indexer _indexer;

	private Map<String, NumbersHolder> _users =
		new HashMap<String, NumbersHolder>();

	private class ExtendedUserIndexerPostProcessor
		extends BaseIndexerPostProcessor {

		@Override
		public void postProcessDocument(Document document, Object obj)
			throws Exception {

			NumbersHolder numbersHolder = _users.get(
				document.get("screenName"));

			document.addNumber(SINGLE_DOUBLE, numbersHolder.getSingleDouble());
			document.addNumber(SINGLE_FLOAT, numbersHolder.getSingleFloat());
			document.addNumber(SINGLE_INT, numbersHolder.getSingleInteger());
			document.addNumber(SINGLE_LONG, numbersHolder.getSingleLong());
			document.addNumber(MULTI_DOUBLE, numbersHolder.getMultiDouble());
			document.addNumber(MULTI_FLOAT, numbersHolder.getMultiFloat());
			document.addNumber(MULTI_INT, numbersHolder.getMultiInteger());
			document.addNumber(MULTI_LONG, numbersHolder.getMultiLong());
		}
	}

	private class NumbersHolder {

		private double _singleDouble;
		private float _singleFloat;
		private int _singleInt;
		private long _singleLong;
		private List<Double> _multiDouble;
		private List<Float> _multiFloat;
		private List<Integer> _multiInt;
		private List<Long> _multiLong;

		public NumbersHolder(double number1, long number2) {
			_singleDouble = number1;
			_singleFloat = _convertFloat(_singleDouble);
			_singleLong = number2;
			_singleInt = _convertInteger(_singleLong);

			_multiDouble = new ArrayList<Double>();
			_multiLong = new ArrayList<Long>();
			_multiFloat = new ArrayList<Float>();
			_multiInt = new ArrayList<Integer>();

			long longSum = 0L;

			double doublePow = _singleDouble;

			for (int i=1; i<5; i++) {
				doublePow *= _singleDouble;
				_multiDouble.add(doublePow);
				_multiFloat.add(_convertFloat(doublePow));
				longSum = _singleLong + i;
				_multiLong.add(longSum);
				_multiInt.add(_convertInteger(longSum));
			}
		}

		public NumbersHolder(Document doc) {
			_singleDouble = Double.valueOf(
				doc.get(SINGLE_DOUBLE)).doubleValue();
			_singleLong = Long.valueOf(doc.get(SINGLE_LONG)).longValue();
			_singleFloat = Float.valueOf(doc.get(SINGLE_FLOAT)).floatValue();
			_singleInt = Integer.valueOf(doc.get(SINGLE_INT)).intValue();

			_multiDouble = _parseMultiDouble(doc.getValues(MULTI_DOUBLE));
			_multiLong = _parseMultiLong(doc.getValues(MULTI_LONG));
			_multiFloat = _parseMultiFloat(doc.getValues(MULTI_FLOAT));
			_multiInt = _parseMultiInteger(doc.getValues(MULTI_INT));
		}

		public Double[] getMultiDouble() {
			return _multiDouble.toArray(new Double[] {});
		}

		public Float[] getMultiFloat() {
			return _multiFloat.toArray(new Float[] {});
		}

		public Integer[] getMultiInteger() {
			return _multiInt.toArray(new Integer[] {});
		}

		public Long[] getMultiLong() {
			return _multiLong.toArray(new Long[] {});
		}

		public double getSingleDouble() {
			return _singleDouble;
		}

		public float getSingleFloat() {
			return _singleFloat;
		}

		public int getSingleInteger() {
			return _singleInt;
		}

		public long getSingleLong() {
			return _singleLong;
		}

		private Float _convertFloat(double data) {
			return new Float((float)data);
		}

		private Integer _convertInteger(long data) {
			return Long.valueOf(data).intValue();
		}

		private List<Double> _parseMultiDouble(String[] data) {
			List<Double> multiDouble = new ArrayList<Double>();

			for (String item : data) {
				multiDouble.add(Double.valueOf(item));
			}

			return multiDouble;
		}

		private List<Float> _parseMultiFloat(String[] data) {
			List<Float> multiFloat = new ArrayList<Float>();

			for (String item : data) {
				multiFloat.add(Float.valueOf(item));
			}

			return multiFloat;
		}

		private List<Integer> _parseMultiInteger(String[] data) {
			List<Integer> multiInt = new ArrayList<Integer>();

			for (String item : data) {
				multiInt.add(Integer.valueOf(item));
			}

			return multiInt;
		}

		private List<Long> _parseMultiLong(String[] data) {
			List<Long> multiLong = new ArrayList<Long>();

			for (String item : data) {
				multiLong.add(Long.valueOf(item));
			}

			return multiLong;
		}
	}

}