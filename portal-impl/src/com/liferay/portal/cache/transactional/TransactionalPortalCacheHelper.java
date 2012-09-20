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

package com.liferay.portal.cache.transactional;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class TransactionalPortalCacheHelper {

	public static void begin() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		_pushPortalCacheMap();
	}

	public static void commit() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		PortalCacheMap portalCacheMap = _popPortalCacheMap();

		for (Map.Entry
				<PortalCache<? extends Serializable, ?>,
					Map<? extends Serializable, ?>> portalCacheMapEntry :
				portalCacheMap.entrySet()) {

			PortalCache<Serializable, Object> portalCache =
				(PortalCache<Serializable, Object>)portalCacheMapEntry.getKey();

			Map<? extends Serializable, ?> uncommittedMap =
				portalCacheMapEntry.getValue();

			for (Map.Entry<? extends Serializable, ?> uncommittedMapEntry :
					uncommittedMap.entrySet()) {

				portalCache.put(
					uncommittedMapEntry.getKey(),
					uncommittedMapEntry.getValue());
			}
		}

		portalCacheMap.clear();
	}

	public static boolean isEnabled() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return false;
		}

		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		return !portalCacheMaps.isEmpty();
	}

	public static void rollback() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		PortalCacheMap portalCacheMap = _popPortalCacheMap();

		portalCacheMap.clear();
	}

	protected static <K extends Serializable, V> V get(
		PortalCache<K, V> portalCache, K key) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		Map<? extends Serializable, ?> uncommittedMap = portalCacheMap.get(
			portalCache);

		if (uncommittedMap == null) {
			return null;
		}

		return (V)uncommittedMap.get(key);
	}

	protected static <K extends Serializable, V> void put(
		PortalCache<K, V> portalCache, K key, V value) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		Map<Serializable, Object> uncommittedMap =
			(Map<Serializable, Object>)portalCacheMap.get(portalCache);

		if (uncommittedMap == null) {
			uncommittedMap = new HashMap<Serializable, Object>();

			portalCacheMap.put(portalCache, uncommittedMap);
		}

		uncommittedMap.put(key, value);
	}

	protected static <K extends Serializable, V> void remove(
		PortalCache<K, V> portalCache, K key) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		Map<? extends Serializable, ?> uncommittedMap = portalCacheMap.get(
			portalCache);

		if (uncommittedMap != null) {
			uncommittedMap.remove(key);
		}
	}

	protected static <K extends Serializable, V> void removeAll(
		PortalCache<K, V> portalCache) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		Map<? extends Serializable, ?> uncommittedMap = portalCacheMap.get(
			portalCache);

		if (uncommittedMap != null) {
			uncommittedMap.clear();
		}
	}

	private static PortalCacheMap _peekPortalCacheMap() {
		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		return portalCacheMaps.get(portalCacheMaps.size() - 1);
	}

	private static PortalCacheMap _popPortalCacheMap() {
		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		return portalCacheMaps.remove(portalCacheMaps.size() - 1);
	}

	private static void _pushPortalCacheMap() {
		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		portalCacheMaps.add(new PortalCacheMap());
	}

	private static ThreadLocal<List<PortalCacheMap>>
		_portalCacheMapsThreadLocal =
			new InitialThreadLocal<List<PortalCacheMap>>(
				TransactionalPortalCacheHelper.class.getName() +
					"._portalCacheMapsThreadLocal",
				new ArrayList<PortalCacheMap>());

	private static class PortalCacheMap
		extends HashMap
			<PortalCache<? extends Serializable, ?>,
				Map<? extends Serializable, ?>> {
	}

}