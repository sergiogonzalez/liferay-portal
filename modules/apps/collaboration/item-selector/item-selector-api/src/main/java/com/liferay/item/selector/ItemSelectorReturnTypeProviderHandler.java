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

package com.liferay.item.selector;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Roberto Díaz
 */
@Component(service = ItemSelectorReturnTypeProviderHandler.class)
public class ItemSelectorReturnTypeProviderHandler {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, ItemSelectorView.class,
			new ItemSelectorViewServiceTrackerCustomizer());

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ItemSelectorReturnTypeProvider.class,
			"item.selector.view.key");
	}

	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes(
		ItemSelectorView itemSelectorView) {

		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
			ListUtil.copy(
				itemSelectorView.getSupportedItemSelectorReturnTypes());

		Class<? extends ItemSelectorView> itemSelectorViewClass =
			itemSelectorView.getClass();

		String itemSelectorViewKey = _itemSelectorViewKeysMap.get(
			itemSelectorViewClass.getName());

		return getSupportedItemSelectorReturnTypes(
			supportedItemSelectorReturnTypes, itemSelectorViewKey);
	}

	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes(
		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes,
		String itemSelectorViewKey) {

		if (Validator.isNull(itemSelectorViewKey)) {
			return supportedItemSelectorReturnTypes;
		}

		List<ItemSelectorReturnTypeProvider> itemSelectorReturnTypeProviders =
			_serviceTrackerMap.getService(itemSelectorViewKey);

		if (itemSelectorReturnTypeProviders == null) {
			return supportedItemSelectorReturnTypes;
		}

		for (ItemSelectorReturnTypeProvider itemSelectorReturnTypeProvider :
				itemSelectorReturnTypeProviders) {

			supportedItemSelectorReturnTypes =
				itemSelectorReturnTypeProvider.populateItemSelectorReturnTypes(
					supportedItemSelectorReturnTypes);
		}

		return supportedItemSelectorReturnTypes;
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private BundleContext _bundleContext;
	private final Map<String, String> _itemSelectorViewKeysMap =
		new ConcurrentHashMap<>();
	private ServiceTracker<ItemSelectorView, ItemSelectorView> _serviceTracker;
	private ServiceTrackerMap<String, List<ItemSelectorReturnTypeProvider>>
		_serviceTrackerMap;

	private class ItemSelectorViewServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer<ItemSelectorView, ItemSelectorView> {

		@Override
		public ItemSelectorView addingService(
			ServiceReference<ItemSelectorView> serviceReference) {

			ItemSelectorView itemSelectorView = _bundleContext.getService(
				serviceReference);

			String itemSelectorViewKey = GetterUtil.getString(
				serviceReference.getProperty("item.selector.view.key"));

			if (Validator.isNotNull(itemSelectorViewKey)) {
				Class<? extends ItemSelectorView> itemSelectorViewClass =
					itemSelectorView.getClass();

				_itemSelectorViewKeysMap.put(
					itemSelectorViewClass.getName(), itemSelectorViewKey);
			}

			return itemSelectorView;
		}

		@Override
		public void modifiedService(
			ServiceReference<ItemSelectorView> serviceReference,
			ItemSelectorView itemSelectorView) {
		}

		@Override
		public void removedService(
			ServiceReference<ItemSelectorView> serviceReference,
			ItemSelectorView itemSelectorView) {

			try {
				Class<? extends ItemSelectorView> itemSelectorViewClass =
					itemSelectorView.getClass();

				_itemSelectorViewKeysMap.remove(
					itemSelectorViewClass.getName());
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

	}

}