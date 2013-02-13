/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

		generateTestData();

		for (String screenName : _usersSingleDouble.keySet()) {
			User user = ServiceTestUtil.addUser(
				screenName, _group.getGroupId());

			user.setFirstName(screenName.replaceFirst("user", ""));
			user.setLastName("Smith");
			user.setStatus(WorkflowConstants.STATUS_APPROVED);

			UserLocalServiceUtil.updateUser(user);
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
	public void testSearchResultsCount2() throws Exception {
		checkNumberOfSearchResults(buildSearchContext("Smith"), 6);
	}

	@Test
	public void testSearchResultsCount3() throws Exception {
		checkNumberOfSearchResults(
			buildSearchContext("sixth second first fourth fifth third"), 6);
	}

	@Test
	public void testSearchSortedByMultiDouble1() throws Exception {
		String[] expected =
			new String[] {"firstuser", "thirduser", "fifthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"third sixth fifth first", expected, MULTI_DOUBLE,
			Sort.DOUBLE_TYPE);

		checkSortedSearchResultsOrder(
			"sixth fifth first third", expected, MULTI_DOUBLE,
			Sort.DOUBLE_TYPE);

		checkSortedSearchResultsOrder(
			"fifth first third sixth", expected, MULTI_DOUBLE,
			Sort.DOUBLE_TYPE);

		checkSortedSearchResultsOrder(
			"first third sixth fifth", expected, MULTI_DOUBLE,
			Sort.DOUBLE_TYPE);
	}

	@Test
	public void testSearchSortedByMultiDouble2() throws Exception {
		String[] expected =
			new String[] {"firstuser", "thirduser", "fifthuser", "seconduser",
						"fourthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"Smith", expected, MULTI_DOUBLE, Sort.DOUBLE_TYPE);
	}

	@Test
	public void testSearchSortedByMultiFloat() throws Exception {
		String[] expected =
			new String[] {"firstuser", "seconduser", "fourthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"sixth second first fourth", expected, MULTI_FLOAT,
			Sort.FLOAT_TYPE);

		checkSortedSearchResultsOrder(
			"second first fourth sixth", expected, MULTI_FLOAT,
			Sort.FLOAT_TYPE);

		checkSortedSearchResultsOrder(
			"first fourth sixth second", expected, MULTI_FLOAT,
			Sort.FLOAT_TYPE);

		checkSortedSearchResultsOrder(
			"fourth sixth second first", expected, MULTI_FLOAT,
			Sort.FLOAT_TYPE);
	}

	@Test
	public void testSearchSortedByMultiInteger1() throws Exception {
		String[] expected =
			new String[] {"fourthuser", "fifthuser", "sixthuser", "firstuser",
						"seconduser", "thirduser"};

		checkSortedSearchResultsOrder(
			"Smith", expected, MULTI_INT, Sort.INT_TYPE);
	}

	@Test
	public void testSearchSortedByMultiInteger2() throws Exception {
		String[] expected =
			new String[] {"fourthuser", "fifthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"fourth fifth sixth", expected, MULTI_INT, Sort.INT_TYPE);

		checkSortedSearchResultsOrder(
			"fifth sixth fourth", expected, MULTI_INT, Sort.INT_TYPE);

		checkSortedSearchResultsOrder(
			"sixth fourth fifth", expected, MULTI_INT, Sort.INT_TYPE);
	}

	@Test
	public void testSearchSortedByMultiLong1() throws Exception {
		String[] expected1 =
			new String[] {"firstuser", "seconduser", "thirduser", "fourthuser",
				"fifthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"Smith", expected1, MULTI_LONG, Sort.LONG_TYPE);
	}

	@Test
	public void testSearchSortedByMultiLong2() throws Exception {
		String[] expected1 =
			new String[] {"firstuser", "seconduser", "thirduser", "fourthuser",
				"fifthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"sixth second first fourth fifth third", expected1, MULTI_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"second first fourth fifth third sixth", expected1, MULTI_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"first fourth fifth third sixth second", expected1, MULTI_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"fourth fifth third sixth second first", expected1, MULTI_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"fifth third sixth second first fourth", expected1, MULTI_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"third sixth second first fourth fifth", expected1, MULTI_LONG,
			Sort.LONG_TYPE);
	}

	@Test
	public void testSearchSortedBySingleDouble1() throws Exception {
		String[] expected =
			new String[] {"firstuser", "thirduser", "fifthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"third sixth fifth first", expected, SINGLE_DOUBLE,
			Sort.DOUBLE_TYPE);

		checkSortedSearchResultsOrder(
			"sixth fifth first third", expected, SINGLE_DOUBLE,
			Sort.DOUBLE_TYPE);

		checkSortedSearchResultsOrder(
			"fifth first third sixth", expected, SINGLE_DOUBLE,
			Sort.DOUBLE_TYPE);

		checkSortedSearchResultsOrder(
			"first third sixth fifth", expected, SINGLE_DOUBLE,
			Sort.DOUBLE_TYPE);
	}

	@Test
	public void testSearchSortedBySingleDouble2() throws Exception {
		String[] expected =
			new String[] {"firstuser", "thirduser", "fifthuser", "seconduser",
						"fourthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"Smith", expected, SINGLE_DOUBLE, Sort.DOUBLE_TYPE);
	}

	@Test
	public void testSearchSortedBySingleFloat() throws Exception {
		String[] expected =
			new String[] {"firstuser", "seconduser", "fourthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"sixth second first fourth", expected, SINGLE_FLOAT,
			Sort.FLOAT_TYPE);

		checkSortedSearchResultsOrder(
			"second first fourth sixth", expected, SINGLE_FLOAT,
			Sort.FLOAT_TYPE);

		checkSortedSearchResultsOrder(
			"first fourth sixth second", expected, SINGLE_FLOAT,
			Sort.FLOAT_TYPE);

		checkSortedSearchResultsOrder(
			"fourth sixth second first", expected, SINGLE_FLOAT,
			Sort.FLOAT_TYPE);
	}

	@Test
	public void testSearchSortedBySingleInteger1() throws Exception {
		String[] expected =
			new String[] {"fourthuser", "fifthuser", "sixthuser", "firstuser",
						"seconduser", "thirduser"};

		checkSortedSearchResultsOrder(
			"Smith", expected, SINGLE_INT, Sort.INT_TYPE);

	}

	@Test
	public void testSearchSortedBySingleInteger2() throws Exception {
		String[] expected =
			new String[] {"fourthuser", "fifthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"fourth fifth sixth", expected, SINGLE_INT, Sort.INT_TYPE);

		checkSortedSearchResultsOrder(
			"fifth sixth fourth", expected, SINGLE_INT, Sort.INT_TYPE);

		checkSortedSearchResultsOrder(
			"sixth fourth fifth", expected, SINGLE_INT, Sort.INT_TYPE);
	}

	@Test
	public void testSearchSortedBySingleLong1() throws Exception {
		String[] expected1 =
			new String[] {"firstuser", "seconduser", "thirduser", "fourthuser",
				"fifthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"Smith", expected1, SINGLE_LONG, Sort.LONG_TYPE);
	}

	@Test
	public void testSearchSortedBySingleLong2() throws Exception {
		String[] expected1 =
			new String[] {"firstuser", "seconduser", "thirduser", "fourthuser",
				"fifthuser", "sixthuser"};

		checkSortedSearchResultsOrder(
			"sixth second first fourth fifth third", expected1, SINGLE_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"second first fourth fifth third sixth", expected1, SINGLE_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"first fourth fifth third sixth second", expected1, SINGLE_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"fourth fifth third sixth second first", expected1, SINGLE_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"fifth third sixth second first fourth", expected1, SINGLE_LONG,
			Sort.LONG_TYPE);

		checkSortedSearchResultsOrder(
			"third sixth second first fourth fifth", expected1, SINGLE_LONG,
			Sort.LONG_TYPE);
	}

	protected SearchContext buildSearchContext(String keywords)
		throws Exception {
		SearchContext searchContext = ServiceTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[]{});
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

	protected void checkSearchResultsData(Hits results) {
		for (Document doc : results.getDocs()) {
			String screenName = doc.get("screenName");

			Assert.assertEquals(
				_usersSingleDouble.get(screenName),
				Double.valueOf(doc.get(SINGLE_DOUBLE)).doubleValue(), 0);

			Assert.assertEquals(
				_usersSingleLong.get(screenName),
				Long.valueOf(doc.get(SINGLE_LONG)).longValue(), 0);

			Assert.assertEquals(
				_usersSingleFloat.get(screenName),
				Float.valueOf(doc.get(SINGLE_FLOAT)).floatValue(), 0);

			Assert.assertEquals(
				_usersSingleInteger.get(screenName),
				Integer.valueOf(doc.get(SINGLE_INT)).intValue(), 0);

			Assert.assertArrayEquals(
				_usersMultiDouble.get(screenName).toArray(new Double[]{}),
				getMultiDouble(doc));

			Assert.assertArrayEquals(
				_usersMultiLong.get(screenName).toArray(new Long[]{}),
				getMultiLong(doc));

			Assert.assertArrayEquals(
				_usersMultiFloat.get(screenName).toArray(new Float[]{}),
				getMultiFloat(doc));

			Assert.assertArrayEquals(
				_usersMultiInteger.get(screenName).toArray(new Integer[]{}),
				getMultiInteger(doc));
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

	protected void checkSortedSearchResultsOrder(
		String keywords, String[] expectedAsc, String field, int sort)
			throws Exception {

		String[] expectedDsc = reverse(expectedAsc);

		SearchContext searchContext = buildSearchContext(keywords);

		checkSortedSearchResultsOrder(
			searchContext, SortFactoryUtil.create(field, sort, false),
			expectedAsc);

		checkSortedSearchResultsOrder(
			searchContext, SortFactoryUtil.create(field, sort, true),
			expectedDsc);
	}

	protected void generateTestData() {
		generateTestItem(
			"firstuser", 0.0000000000001, new Long(Integer.MIN_VALUE - 9L));

		generateTestItem(
			"seconduser",0.0000020000002, new Long(Integer.MIN_VALUE - 8L));

		generateTestItem(
			"thirduser",0.0000000000003, new Long(Integer.MIN_VALUE - 7L));

		generateTestItem("fourthuser",0.0000040000004, Integer.MIN_VALUE + 7);

		generateTestItem("fifthuser",0.0000000000005, Integer.MIN_VALUE + 8);

		generateTestItem("sixthuser",0.0000060000006, Integer.MIN_VALUE + 9);
	}

	protected void generateTestItem(
		String screenName, double number1, long number2) {

		_usersSingleDouble.put(screenName, number1);

		_usersSingleFloat.put(screenName, new Float((float)number1));

		_usersSingleInteger.put(screenName, Long.valueOf(number2).intValue());

		_usersSingleLong.put(screenName, number2);

		List<Double> doubleList = new ArrayList<Double>();

		List<Float> floatList = new ArrayList<Float>();

		List<Integer> intList = new ArrayList<Integer>();

		List<Long> longList = new ArrayList<Long>();

		double doublePow = number1;

		long longSum;

		for (int i=1; i<5; i++) {
			doublePow *= number1;
			doubleList.add(doublePow);
			floatList.add(new Float((float)doublePow));
			longSum = number2 + i;
			longList.add(longSum);
			intList.add(Long.valueOf(longSum).intValue());
		}

		_usersMultiDouble.put(screenName, doubleList);

		_usersMultiFloat.put(screenName, floatList);

		_usersMultiInteger.put(screenName, intList);

		_usersMultiLong.put(screenName, longList);
	}

	protected Double[] getMultiDouble(Document doc) {
		List<Double> multiDouble = new ArrayList<Double>();
		for (String item : doc.getValues(MULTI_DOUBLE)) {
			multiDouble.add(Double.valueOf(item));
		}

		return multiDouble.toArray(new Double[]{});
	}

	protected Float[] getMultiFloat(Document doc) {
		List<Float> multiFloat = new ArrayList<Float>();
		for (String item : doc.getValues(MULTI_FLOAT)) {
			multiFloat.add(Float.valueOf(item));
		}

		return multiFloat.toArray(new Float[]{});
	}

	protected Integer[] getMultiInteger(Document doc) {
		List<Integer> multiInt = new ArrayList<Integer>();
		for (String item : doc.getValues(MULTI_INT)) {
			multiInt.add(Integer.valueOf(item));
		}

		return multiInt.toArray(new Integer[]{});
	}

	protected Long[] getMultiLong(Document doc) {
		List<Long> multiLong = new ArrayList<Long>();
		for (String item : doc.getValues(MULTI_LONG)) {
			multiLong.add(Long.valueOf(item));
		}

		return multiLong.toArray(new Long[]{});
	}

	protected String[] reverse(String[] array) {
		String[] reversed = Arrays.copyOf(array, array.length);
		Collections.reverse(Arrays.asList(reversed));
		return reversed;
	}

	protected Group _group;

	protected Indexer _indexer;

	protected Map<String, List<Double>> _usersMultiDouble =
		new HashMap<String, List<Double>>();

	protected Map<String, List<Float>> _usersMultiFloat =
			new HashMap<String, List<Float>>();

	protected Map<String, List<Integer>> _usersMultiInteger =
			new HashMap<String, List<Integer>>();

	protected Map<String, List<Long>> _usersMultiLong =
		new HashMap<String, List<Long>>();

	protected Map<String, Double> _usersSingleDouble =
		new HashMap<String, Double>();
	protected Map<String, Float> _usersSingleFloat =
		new HashMap<String, Float>();
	protected Map<String, Integer> _usersSingleInteger =
		new HashMap<String, Integer>();
	protected Map<String, Long> _usersSingleLong = new HashMap<String, Long>();

	protected class ExtendedUserIndexerPostProcessor extends
		BaseIndexerPostProcessor {

		@Override
		public void postProcessDocument(Document document, Object obj)
			throws Exception {

			String screenName = document.get("screenName");

			document.addNumber(
				SINGLE_DOUBLE, _usersSingleDouble.get(screenName));

			document.addNumber(SINGLE_FLOAT, _usersSingleFloat.get(screenName));

			document.addNumber(SINGLE_INT, _usersSingleInteger.get(screenName));

			document.addNumber(SINGLE_LONG, _usersSingleLong.get(screenName));

			document.addNumber(
				MULTI_DOUBLE,
				_usersMultiDouble.get(screenName).toArray(new Double[]{}));

			document.addNumber(
				MULTI_FLOAT,
				_usersMultiFloat.get(screenName).toArray(new Float[]{}));

			document.addNumber(
				MULTI_INT,
				_usersMultiInteger.get(screenName).toArray(new Integer[]{}));

			document.addNumber(
				MULTI_LONG,
				_usersMultiLong.get(screenName).toArray(new Long[]{}));
		}
	}

}