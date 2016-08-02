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

package com.liferay.item.selector.bucket.factory;

import com.liferay.item.selector.ItemSelectorView;
import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.internal.ServiceReferenceServiceTupleComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucket;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucketFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.ServiceReference;

/**
 * @author Roberto Díaz
 */
public class ItemSelectorViewServiceTrackerBucketFactory
	implements ServiceTrackerBucketFactory {

	public ItemSelectorViewServiceTrackerBucketFactory() {
		_comparator = Collections.reverseOrder();
	}

	public ItemSelectorViewServiceTrackerBucketFactory(
		Comparator<ServiceReference<ItemSelectorView>> comparator) {

		_comparator = comparator;
	}

	@Override
	public ServiceTrackerBucket create() {
		return new ListServiceTrackerBucket();
	}

	private final Comparator<ServiceReference<ItemSelectorView>> _comparator;

	private class ListServiceTrackerBucket
		implements ServiceTrackerBucket<ItemSelectorView, ItemSelectorView, List<ItemSelectorView>> {

		@Override
		public List<ItemSelectorView> getContent() {
			return _services;
		}

		@Override
		public synchronized boolean isDisposable() {
			return _serviceReferenceServiceTuples.isEmpty();
		}

		@Override
		public synchronized void remove(
			ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
				serviceReferenceServiceTuple) {

			_serviceReferenceServiceTuples.remove(serviceReferenceServiceTuple);

			rebuild();
		}

		@Override
		public synchronized void store(
			ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
				serviceReferenceServiceTuple) {

			_serviceReferenceServiceTuples.add(serviceReferenceServiceTuple);

			rebuild();
		}

		protected void rebuild() {
			_services = new ArrayList<>(_serviceReferenceServiceTuples.size());

			for (ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
					serviceReferenceServiceTuple : _serviceReferenceServiceTuples) {

				ServiceReference<ItemSelectorView> serviceReference =
					serviceReferenceServiceTuple.getServiceReference();

				String overwriteViewKey = GetterUtil.getString(
					serviceReference.getProperty("overwrite.view.key"));

				if (Validator.isNotNull(overwriteViewKey)) {
					_rebuildOverwritableServices(serviceReferenceServiceTuple);
				}
				else {
					_services.add(serviceReferenceServiceTuple.getService());
				}
			}

			_services = Collections.unmodifiableList(_services);
		}

		private ListServiceTrackerBucket() {
			ServiceReferenceServiceTupleComparator<ItemSelectorView>
				serviceReferenceServiceTupleComparator =
					new ServiceReferenceServiceTupleComparator<>(_comparator);

			_serviceReferenceServiceTuples = new TreeSet<>(
				serviceReferenceServiceTupleComparator);
		}

		private void _rebuildOverwritableServices(
			ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
				serviceReferenceServiceTuple) {

			ServiceReference<ItemSelectorView> serviceReference =
				serviceReferenceServiceTuple.getServiceReference();

			String overwriteViewKey = GetterUtil.getString(
				serviceReference.getProperty("overwrite.view.key"));

			int viewOrder = GetterUtil.getInteger(
				serviceReference.getProperty("item.selector.view.order"));

			ItemSelectorView curService =
				serviceReferenceServiceTuple.getService();

			ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
				activeServiceReferenceTuple =
					_activeOverwritableServiceReferencesTuplesMap.get(
						overwriteViewKey);

			if (activeServiceReferenceTuple == null) {
				_services.add(curService);

				_activeOverwritableServiceReferencesTuplesMap.put(
					overwriteViewKey, serviceReferenceServiceTuple);

				return;
			}

			ServiceReference<ItemSelectorView> activedServiceReference =
				activeServiceReferenceTuple.getServiceReference();

			int activeItemSelectorViewOrder = GetterUtil.getInteger(
				activedServiceReference.getProperty(
					"item.selector.view.order"));

			if (activeItemSelectorViewOrder > viewOrder) {
				_services.remove(activeServiceReferenceTuple.getService());

				_services.add(curService);

				_activeOverwritableServiceReferencesTuplesMap.put(
					overwriteViewKey, serviceReferenceServiceTuple);

				return;
			}

			ItemSelectorView activeService =
				activeServiceReferenceTuple.getService();

			if (!_services.contains(activeService)) {
				_services.add(activeService);
			}
		}

		private final Map<String, ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>>
			_activeOverwritableServiceReferencesTuplesMap = new HashMap<>();
		private final Set<ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>>
			_serviceReferenceServiceTuples;
		private List<ItemSelectorView> _services = new ArrayList<>();

	}

}