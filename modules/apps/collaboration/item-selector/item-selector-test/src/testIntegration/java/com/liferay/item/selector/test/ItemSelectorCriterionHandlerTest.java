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

package com.liferay.item.selector.test;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class ItemSelectorCriterionHandlerTest {

	@Before
	public void setUp() throws Exception {
		_bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new Hashtable<>();

		_bundleContext.registerService(
			TestItemSelectorCriterionHandler.class,
			new TestItemSelectorCriterionHandler(), properties);

		_serviceReference = _bundleContext.getServiceReference(
			TestItemSelectorCriterionHandler.class);

		_testItemSelectorCriterionHandler = _bundleContext.getService(
			_serviceReference);

		_testItemSelectorCriterionHandler.activate(_bundleContext);
	}

	@After
	public void tearDown() throws BundleException {
		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testItemSelectorViewsAreAddedWhenNoViewKeys() {
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration1 = registerItemSelectorView(
				new TestItemSelectorView1(), 100, null);
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration2 = registerItemSelectorView(
				new TestItemSelectorView2(), 200, null);
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration3 = registerItemSelectorView(
				new TestItemSelectorView3(), 50, null);

		try {
			ItemSelectorCriterion testItemSelectorCriterion =
				getTestItemSelectorCriterion();

			List<ItemSelectorView<TestItemSelectorCriterion>>
				itemSelectorViews =
					_testItemSelectorCriterionHandler.getItemSelectorViews(
						testItemSelectorCriterion);

			Assert.assertEquals(3, itemSelectorViews.size());
			Assert.assertTrue(
				itemSelectorViews.get(0) instanceof TestItemSelectorView3);
			Assert.assertTrue(
				itemSelectorViews.get(1) instanceof TestItemSelectorView1);
			Assert.assertTrue(
				itemSelectorViews.get(2) instanceof TestItemSelectorView2);
		}
		finally {
			itemSelectorViewServiceRegistration1.unregister();
			itemSelectorViewServiceRegistration2.unregister();
			itemSelectorViewServiceRegistration3.unregister();
		}
	}

	@Test
	public void testItemSelectorViewsAreAddedWhenViewKeysAreDifferent() {
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration1 = registerItemSelectorView(
				new TestItemSelectorView1(), 200, "view1");
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration2 = registerItemSelectorView(
				new TestItemSelectorView2(), 100, "view2");

		try {
			ItemSelectorCriterion testItemSelectorCriterion =
				getTestItemSelectorCriterion();

			List<ItemSelectorView<TestItemSelectorCriterion>>
				itemSelectorViews =
					_testItemSelectorCriterionHandler.getItemSelectorViews(
						testItemSelectorCriterion);

			Assert.assertEquals(2, itemSelectorViews.size());
			Assert.assertTrue(
				itemSelectorViews.get(0) instanceof TestItemSelectorView2);
			Assert.assertTrue(
				itemSelectorViews.get(1) instanceof TestItemSelectorView1);
		}
		finally {
			itemSelectorViewServiceRegistration1.unregister();
			itemSelectorViewServiceRegistration2.unregister();
		}
	}

	@Test
	public void testItemSelectorViewsAreOverridenWhenViewKeysAreTheSame() {
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration1 = registerItemSelectorView(
				new TestItemSelectorView1(), 100, "view");
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration2 = registerItemSelectorView(
				new TestItemSelectorView2(), 200, "view");

		try {
			ItemSelectorCriterion testItemSelectorCriterion =
				getTestItemSelectorCriterion();

			List<ItemSelectorView<TestItemSelectorCriterion>>
				itemSelectorViews =
					_testItemSelectorCriterionHandler.getItemSelectorViews(
						testItemSelectorCriterion);

			Assert.assertEquals(1, itemSelectorViews.size());
			Assert.assertTrue(
				itemSelectorViews.get(0) instanceof TestItemSelectorView1);
		}
		finally {
			itemSelectorViewServiceRegistration1.unregister();
			itemSelectorViewServiceRegistration2.unregister();
		}
	}

	@Test
	public void testItemSelectorWithOverridenViewsAndUniqueViews() {
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration1 = registerItemSelectorView(
				new TestItemSelectorView1(), 100, "view1");
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration2 = registerItemSelectorView(
				new TestItemSelectorView2(), 200, "view2");
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration3 = registerItemSelectorView(
				new TestItemSelectorView3(), 50, "view3");
		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration1Bis = registerItemSelectorView(
				new TestItemSelectorView4(), 200, "view1");

		try {
			ItemSelectorCriterion testItemSelectorCriterion =
				getTestItemSelectorCriterion();

			List<ItemSelectorView<TestItemSelectorCriterion>>
				itemSelectorViews =
					_testItemSelectorCriterionHandler.getItemSelectorViews(
						testItemSelectorCriterion);

			Assert.assertEquals(3, itemSelectorViews.size());
			Assert.assertTrue(
				itemSelectorViews.get(0) instanceof TestItemSelectorView3);
			Assert.assertTrue(
				itemSelectorViews.get(1) instanceof TestItemSelectorView1);
			Assert.assertTrue(
				itemSelectorViews.get(2) instanceof TestItemSelectorView2);
		}
		finally {
			itemSelectorViewServiceRegistration1.unregister();
			itemSelectorViewServiceRegistration2.unregister();
			itemSelectorViewServiceRegistration3.unregister();
			itemSelectorViewServiceRegistration1Bis.unregister();
		}
	}

	@ArquillianResource
	public Bundle bundle;

	protected ItemSelectorCriterion getTestItemSelectorCriterion() {
		ItemSelectorCriterion testItemSelectorCriterion =
			new TestItemSelectorCriterion();

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			new ArrayList<>();

		itemSelectorReturnTypes.add(new TestItemSelectorReturnType());

		testItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			itemSelectorReturnTypes);

		return testItemSelectorCriterion;
	}

	protected ServiceRegistration<ItemSelectorView> registerItemSelectorView(
		ItemSelectorView itemSelectorView, int itemSelectorViewOrder,
		String itemSelectorViewKey) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("item.selector.view.order", itemSelectorViewOrder);

		if (Validator.isNotNull(itemSelectorViewKey)) {
			properties.put("item.selector.view.key", itemSelectorViewKey);
		}

		return _bundleContext.registerService(
			ItemSelectorView.class, itemSelectorView, properties);
	}

	private BundleContext _bundleContext;
	private ServiceReference<TestItemSelectorCriterionHandler>
		_serviceReference;
	private TestItemSelectorCriterionHandler _testItemSelectorCriterionHandler;

	private static class TestItemSelectorReturnType
		implements ItemSelectorReturnType {
	}

	private static class TestItemSelectorView1
		extends BaseTestItemSelectorView {

		public String getTitle(Locale locale) {
			return "Test 1";
		}

	}

	private static class TestItemSelectorView2
		extends BaseTestItemSelectorView {

		public String getTitle(Locale locale) {
			return "Test 2";
		}

	}

	private static class TestItemSelectorView3
		extends BaseTestItemSelectorView {

		public String getTitle(Locale locale) {
			return "Test 3";
		}

	}

	private static class TestItemSelectorView4
		extends BaseTestItemSelectorView {

		public String getTitle(Locale locale) {
			return "Test 4";
		}

	}

	private abstract static class BaseTestItemSelectorView
		implements ItemSelectorView<TestItemSelectorCriterion> {

		public Class<TestItemSelectorCriterion>
			getItemSelectorCriterionClass() {

			return TestItemSelectorCriterion.class;
		}

		public List<ItemSelectorReturnType>
			getSupportedItemSelectorReturnTypes() {

			return _supportedItemSelectorReturnTypes;
		}

		public boolean isShowSearch() {
			return false;
		}

		public boolean isVisible(ThemeDisplay themeDisplay) {
			return false;
		}

		public void renderHTML(
				ServletRequest servletRequest, ServletResponse servletResponse,
				TestItemSelectorCriterion itemSelectorCriterion,
				PortletURL portletURL, String itemSelectedEventName,
				boolean search)
			throws IOException, ServletException {
		}

		private static final List<ItemSelectorReturnType>
			_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
				ListUtil.fromArray(
					new ItemSelectorReturnType[] {
						new TestItemSelectorReturnType()
					}));

	}

}