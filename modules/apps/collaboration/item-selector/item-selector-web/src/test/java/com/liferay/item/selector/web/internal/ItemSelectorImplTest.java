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

package com.liferay.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorRendering;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.item.selector.constants.ItemSelectorPortletKeys;
import com.liferay.item.selector.web.internal.util.ItemSelectorCriterionSerializerImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PortalImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 * @author Roberto Díaz
 */
public class ItemSelectorImplTest extends PowerMockito {

	@Before
	public void setUp() {
		_flickrItemSelectorCriterion = new FlickrItemSelectorCriterion();

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(_testURLItemSelectorReturnType);

		_flickrItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		_itemSelectorImpl = new ItemSelectorImpl();

		_stubItemSelectorCriterionSerializer.addItemSelectorReturnType(
			_testFileEntryItemSelectorReturnType);
		_stubItemSelectorCriterionSerializer.addItemSelectorReturnType(
			_testStringItemSelectorReturnType);
		_stubItemSelectorCriterionSerializer.addItemSelectorReturnType(
			_testURLItemSelectorReturnType);

		_itemSelectorImpl.setItemSelectorCriterionSerializer(
			_stubItemSelectorCriterionSerializer);

		_mediaItemSelectorCriterion = new MediaItemSelectorCriterion();

		_mediaItemSelectorCriterion.setFileExtension("jpg");
		_mediaItemSelectorCriterion.setMaxSize(2048);

		desiredItemSelectorReturnTypes = new ArrayList<>();

		desiredItemSelectorReturnTypes.add(
			new TestFileEntryItemSelectorReturnType());
		desiredItemSelectorReturnTypes.add(_testURLItemSelectorReturnType);

		_mediaItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testGetItemSelectedEventName() {
		String itemSelectorURL = getItemSelectorURL(
			"testItemSelectedEventName", _mediaItemSelectorCriterion,
			_flickrItemSelectorCriterion);

		setUpItemSelectionCriterionHandlers();

		Assert.assertEquals(
			"testItemSelectedEventName",
			_itemSelectorImpl.getItemSelectedEventName(itemSelectorURL));
	}

	@Test
	public void testGetItemSelectorCriteriaFromItemSelectorURL() {
		String itemSelectorURL = getItemSelectorURL(
			StringUtil.randomString(), _mediaItemSelectorCriterion,
			_flickrItemSelectorCriterion);

		setUpItemSelectionCriterionHandlers();

		List<ItemSelectorCriterion> itemSelectorCriteria =
			_itemSelectorImpl.getItemSelectorCriteria(itemSelectorURL);

		Assert.assertEquals(2, itemSelectorCriteria.size());

		MediaItemSelectorCriterion mediaItemSelectorCriterion =
			(MediaItemSelectorCriterion)itemSelectorCriteria.get(0);

		Assert.assertEquals(
			"jpg", mediaItemSelectorCriterion.getFileExtension());
		Assert.assertEquals(2048, mediaItemSelectorCriterion.getMaxSize());

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			mediaItemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		Assert.assertEquals(2, desiredItemSelectorReturnTypes.size());
		Assert.assertTrue(
			desiredItemSelectorReturnTypes.get(0) instanceof
				TestFileEntryItemSelectorReturnType);
		Assert.assertTrue(
			desiredItemSelectorReturnTypes.get(1) instanceof
				TestURLItemSelectorReturnType);
		Assert.assertTrue(
			itemSelectorCriteria.get(1) instanceof FlickrItemSelectorCriterion);
	}

	@Test
	public void testGetItemSelectorParameters() {
		Map<String, String[]> parameters =
			_itemSelectorImpl.getItemSelectorParameters(
				"itemSelectedEventName", _mediaItemSelectorCriterion,
				_flickrItemSelectorCriterion);

		Assert.assertEquals(
			"itemSelectedEventName",
			parameters.get(
				ItemSelectorImpl.PARAMETER_ITEM_SELECTED_EVENT_NAME)[0]);
		Assert.assertEquals(
			MediaItemSelectorCriterion.class.getName() + "," +
				FlickrItemSelectorCriterion.class.getName(),
			parameters.get(ItemSelectorImpl.PARAMETER_CRITERIA)[0]);
		Assert.assertNull(parameters.get("0_desiredItemSelectorReturnTypes"));
		Assert.assertNotNull(parameters.get("0_json")[0]);
		Assert.assertNotNull(parameters.get("1_json")[0]);
		Assert.assertEquals(4, parameters.size());
	}

	@Test
	public void testGetItemSelectorRendering() {
		setUpItemSelectionCriterionHandlers();

		ItemSelectorRendering itemSelectorRendering =
			getItemSelectorRendering();

		Assert.assertEquals(
			"itemSelectedEventName",
			itemSelectorRendering.getItemSelectedEventName());

		List<ItemSelectorViewRenderer> itemSelectorViewRenderers =
			itemSelectorRendering.getItemSelectorViewRenderers();

		ItemSelectorViewRenderer mediaItemSelectorViewRenderer =
			itemSelectorViewRenderers.get(0);

		MediaItemSelectorCriterion mediaItemSelectorCriterion =
			(MediaItemSelectorCriterion)
				mediaItemSelectorViewRenderer.getItemSelectorCriterion();

		Assert.assertEquals(
			_mediaItemSelectorCriterion.getFileExtension(),
			mediaItemSelectorCriterion.getFileExtension());
		Assert.assertEquals(
			_mediaItemSelectorCriterion.getMaxSize(),
			mediaItemSelectorCriterion.getMaxSize());
		Assert.assertTrue(
			(ItemSelectorView<?>)
				mediaItemSelectorViewRenderer.getItemSelectorView() instanceof
					MediaItemSelectorView);

		ItemSelectorViewRenderer flickrItemSelectorViewRenderer =
			itemSelectorViewRenderers.get(1);

		FlickrItemSelectorCriterion flickrItemSelectorCriterion =
			(FlickrItemSelectorCriterion)
				flickrItemSelectorViewRenderer.getItemSelectorCriterion();

		Assert.assertEquals(
			_flickrItemSelectorCriterion.getUser(),
			flickrItemSelectorCriterion.getUser());
		Assert.assertTrue(
			(ItemSelectorView<?>)
				flickrItemSelectorViewRenderer.getItemSelectorView() instanceof
					FlickrItemSelectorView);
		Assert.assertEquals(2, itemSelectorViewRenderers.size());
	}

	protected ItemSelectorRendering getItemSelectorRendering() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory = mock(
			RequestBackedPortletURLFactory.class);

		LiferayPortletURL mockLiferayPortletURL = mock(LiferayPortletURL.class);

		when(
			requestBackedPortletURLFactory.createControlPanelRenderURL(
				Mockito.anyString(), Mockito.any(Group.class),
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			mockLiferayPortletURL
		);

		Map<String, String[]> parameters =
			_itemSelectorImpl.getItemSelectorParameters(
				"itemSelectedEventName", _mediaItemSelectorCriterion,
				_flickrItemSelectorCriterion);

		ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

		themeDisplay.setScopeGroupId(12345);

		when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			new GroupImpl()
		);

		return _itemSelectorImpl.getItemSelectorRendering(
			requestBackedPortletURLFactory, parameters, themeDisplay);
	}

	protected String getItemSelectorURL(
		String itemSelectedEventName,
		ItemSelectorCriterion... itemSelectorCriteria) {

		Map<String, String[]> itemSelectorParameters =
			_itemSelectorImpl.getItemSelectorParameters(
				itemSelectedEventName, itemSelectorCriteria);

		String itemSelectorURL =
			"http://localhost?p_p_state=popup&p_p_mode=view";

		String namespace = PortalUtil.getPortletNamespace(
			ItemSelectorPortletKeys.ITEM_SELECTOR);

		for (String itemSelectorParameterKey :
				itemSelectorParameters.keySet()) {

			itemSelectorURL = HttpUtil.addParameter(
				itemSelectorURL, namespace + itemSelectorParameterKey,
				itemSelectorParameters.get(itemSelectorParameterKey)[0]);
		}

		return itemSelectorURL;
	}

	protected void setUpItemSelectionCriterionHandlers() {
		_itemSelectorImpl.setItemSelectionCriterionHandler(
			new FlickrItemSelectorCriterionHandler());
		_itemSelectorImpl.setItemSelectionCriterionHandler(
			new MediaItemSelectorCriterionHandler());
	}

	private FlickrItemSelectorCriterion _flickrItemSelectorCriterion;
	private ItemSelectorImpl _itemSelectorImpl;
	private MediaItemSelectorCriterion _mediaItemSelectorCriterion;
	private final StubItemSelectorCriterionSerializerImpl
		_stubItemSelectorCriterionSerializer =
			new StubItemSelectorCriterionSerializerImpl();
	private final ItemSelectorReturnType _testFileEntryItemSelectorReturnType =
		new TestFileEntryItemSelectorReturnType();
	private final ItemSelectorReturnType _testStringItemSelectorReturnType =
		new TestStringItemSelectorReturnType();
	private final ItemSelectorReturnType _testURLItemSelectorReturnType =
		new TestURLItemSelectorReturnType();

	private class StubItemSelectorCriterionSerializerImpl
		extends ItemSelectorCriterionSerializerImpl {

		@Override
		public void addItemSelectorReturnType(
			ItemSelectorReturnType itemSelectorReturnType) {

			super.addItemSelectorReturnType(itemSelectorReturnType);
		}

	}

}