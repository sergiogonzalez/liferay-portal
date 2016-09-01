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

package com.liferay.item.selector.web.internal.util;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProviderHandler;
import com.liferay.item.selector.web.ItemSelectorCriterionSerializer;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.json.JSONContext;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONDeserializerTransformer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera
 * @author Roberto Díaz
 */
@Component(immediate = true)
public class ItemSelectorCriterionSerializerImpl
	implements ItemSelectorCriterionSerializer {

	@Override
	public <T extends ItemSelectorCriterion> T deserialize(
		Class<T> itemSelectorCriterionClass, String json) {

		JSONDeserializer<T> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		jsonDeserializer.transform(
			_desiredItemSelectorReturnTypesJSONDeserializerTransformer,
			"desiredItemSelectorReturnTypes");

		return jsonDeserializer.deserialize(json, itemSelectorCriterionClass);
	}

	@Override
	public String serialize(ItemSelectorCriterion itemSelectorCriterion) {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.transform(
			_desiredItemSelectorReturnTypesJSONTransformer,
			"desiredItemSelectorReturnTypes");

		jsonSerializer.exclude(_EXCLUDED_FIELD_NAMES);

		return jsonSerializer.serializeDeep(itemSelectorCriterion);
	}

	@Reference(unbind = "-")
	public void setItemSelectorViewReturnTypeProviderHandler(
		ItemSelectorViewReturnTypeProviderHandler
			itemSelectorViewReturnTypeProviderHandler) {

		_itemSelectorViewReturnTypeProviderHandler =
			itemSelectorViewReturnTypeProviderHandler;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_itemSelectorReturnTypeServiceTracker = ServiceTrackerFactory.open(
			bundleContext, ItemSelectorReturnType.class,
			new ItemSelectorReturnTypeServiceTrackerCustomizer());

		_itemSelectorViewServiceTracker = ServiceTrackerFactory.open(
			bundleContext, ItemSelectorView.class,
			new ItemSelectorViewReturnTypeServiceTrackerCustomizer());
	}

	protected void addItemSelectorReturnType(
		ItemSelectorReturnType itemSelectorReturnType) {

		Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass =
			itemSelectorReturnType.getClass();

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			_itemSelectorReturnTypes.get(itemSelectorReturnTypeClass.getName());

		if (itemSelectorReturnTypes == null) {
			itemSelectorReturnTypes = new CopyOnWriteArrayList<>();

			List<ItemSelectorReturnType> previousItemSelectorReturnTypes =
				_itemSelectorReturnTypes.putIfAbsent(
					itemSelectorReturnTypeClass.getName(),
					itemSelectorReturnTypes);

			if (previousItemSelectorReturnTypes != null) {
				itemSelectorReturnTypes = previousItemSelectorReturnTypes;
			}
		}

		itemSelectorReturnTypes.add(itemSelectorReturnType);
	}

	@Deactivate
	protected void deactivate() {
		_itemSelectorReturnTypeServiceTracker.close();

		_itemSelectorViewServiceTracker.close();
	}

	private static final String[] _EXCLUDED_FIELD_NAMES =
		new String[] {"availableItemSelectorReturnTypes", "class"};

	private static final Log _log = LogFactoryUtil.getLog(
		ItemSelectorCriterionSerializerImpl.class);

	private BundleContext _bundleContext;
	private final DesiredItemSelectorReturnTypesJSONDeserializerTransformer
		_desiredItemSelectorReturnTypesJSONDeserializerTransformer =
			new DesiredItemSelectorReturnTypesJSONDeserializerTransformer();
	private final DesiredItemSelectorReturnTypesJSONTransformer
		_desiredItemSelectorReturnTypesJSONTransformer =
			new DesiredItemSelectorReturnTypesJSONTransformer();
	private final ConcurrentMap<String, List<ItemSelectorReturnType>>
		_itemSelectorReturnTypes = new ConcurrentHashMap<>();
	private ServiceTracker<ItemSelectorReturnType, ItemSelectorReturnType>
		_itemSelectorReturnTypeServiceTracker;
	private ItemSelectorViewReturnTypeProviderHandler
		_itemSelectorViewReturnTypeProviderHandler;
	private ServiceTracker<ItemSelectorView, ItemSelectorView>
		_itemSelectorViewServiceTracker;

	private static class DesiredItemSelectorReturnTypesJSONTransformer
		implements JSONTransformer {

		@Override
		public void transform(JSONContext jsonContext, Object object) {
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
				(List<ItemSelectorReturnType>)object;

			StringBundler sb = new StringBundler(
				desiredItemSelectorReturnTypes.size() * 2 + 1);

			sb.append(StringPool.QUOTE);

			for (ItemSelectorReturnType itemSelectorReturnType :
					desiredItemSelectorReturnTypes) {

				Class<? extends ItemSelectorReturnType>
					itemSelectorReturnTypeClass =
						itemSelectorReturnType.getClass();

				sb.append(itemSelectorReturnTypeClass.getName());
				sb.append(StringPool.COMMA);
			}

			if (desiredItemSelectorReturnTypes.isEmpty()) {
				sb.append(StringPool.QUOTE);
			}
			else {
				sb.setStringAt(StringPool.QUOTE, sb.index() - 1);
			}

			jsonContext.write(sb.toString());
		}

	}

	private class DesiredItemSelectorReturnTypesJSONDeserializerTransformer
		implements JSONDeserializerTransformer
			<String, List<ItemSelectorReturnType>> {

		@Override
		public List<ItemSelectorReturnType> transform(String key) {
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
				new ArrayList<>();

			String[] desiredItemSelectorReturnTypeNames = StringUtil.split(key);

			for (String desiredItemSelectorReturnTypeName :
					desiredItemSelectorReturnTypeNames) {

				List<ItemSelectorReturnType> itemSelectorReturnTypes =
					_itemSelectorReturnTypes.get(
						desiredItemSelectorReturnTypeName);

				if (ListUtil.isEmpty(itemSelectorReturnTypes)) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"No return types are registered for " +
								desiredItemSelectorReturnTypeName);
					}

					continue;
				}

				Iterator<ItemSelectorReturnType> iterator =
					itemSelectorReturnTypes.iterator();

				if (iterator.hasNext()) {
					desiredItemSelectorReturnTypes.add(iterator.next());
				}
			}

			if (desiredItemSelectorReturnTypes.isEmpty()) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No valid desired item selector return types found");
				}
			}

			return desiredItemSelectorReturnTypes;
		}

	}

	private class ItemSelectorReturnTypeServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ItemSelectorReturnType, ItemSelectorReturnType> {

		@Override
		public ItemSelectorReturnType addingService(
			ServiceReference<ItemSelectorReturnType> serviceReference) {

			ItemSelectorReturnType itemSelectorReturnType =
				_bundleContext.getService(serviceReference);

			try {
				addItemSelectorReturnType(itemSelectorReturnType);
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}

			return itemSelectorReturnType;
		}

		@Override
		public void modifiedService(
			ServiceReference<ItemSelectorReturnType> serviceReference,
			ItemSelectorReturnType service) {
		}

		@Override
		public void removedService(
			ServiceReference<ItemSelectorReturnType> serviceReference,
			ItemSelectorReturnType service) {

			try {
				ItemSelectorReturnType itemSelectorReturnType =
					_bundleContext.getService(serviceReference);

				Class<? extends ItemSelectorReturnType>
					supportedItemSelectorReturnTypeClass =
						itemSelectorReturnType.getClass();

				List<ItemSelectorReturnType> itemSelectorReturnTypes =
					_itemSelectorReturnTypes.get(
						supportedItemSelectorReturnTypeClass.getName());

				itemSelectorReturnTypes.remove(0);
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

	}

	private class ItemSelectorViewReturnTypeServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer<ItemSelectorView, ItemSelectorView> {

		@Override
		public ItemSelectorView addingService(
			ServiceReference<ItemSelectorView> serviceReference) {

			ItemSelectorView itemSelectorView = _bundleContext.getService(
				serviceReference);

			String itemSelectorViewKey = GetterUtil.getString(
				serviceReference.getProperty("item.selector.view.key"));

			List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
				_itemSelectorViewReturnTypeProviderHandler.
					getSupportedItemSelectorReturnTypes(
						itemSelectorView.
							getSupportedItemSelectorReturnTypes(),
						itemSelectorViewKey);

			for (ItemSelectorReturnType supportedItemSelectorReturnType :
					supportedItemSelectorReturnTypes) {

				addItemSelectorReturnType(supportedItemSelectorReturnType);
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
				List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
					itemSelectorView.getSupportedItemSelectorReturnTypes();

				for (ItemSelectorReturnType supportedItemSelectorReturnType :
						supportedItemSelectorReturnTypes) {

					Class<? extends ItemSelectorReturnType>
						supportedItemSelectorReturnTypeClass =
							supportedItemSelectorReturnType.getClass();

					List<ItemSelectorReturnType> itemSelectorReturnTypes =
						_itemSelectorReturnTypes.get(
							supportedItemSelectorReturnTypeClass.getName());

					itemSelectorReturnTypes.remove(0);
				}
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

	}

}