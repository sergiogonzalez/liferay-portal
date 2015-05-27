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

package com.liferay.item.selector.web.util;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.util.SetUtil;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Iván Zaera
 */
public class ItemSelectorCriterionSerializer<T extends ItemSelectorCriterion> {

	public static final String JSON = "json";

	public ItemSelectorCriterionSerializer(
		T itemSelectorCriterion, String prefix) {

		_itemSelectorCriterion = itemSelectorCriterion;
		_prefix = prefix;

		_initSerializableFields();
	}

	public Map<String, String[]> getProperties() {
		Map<String, String[]> properties = new HashMap<>();

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.include(_serializableFields);

		properties.put(
			_prefix + JSON,
			new String[] {jsonSerializer.serialize(_itemSelectorCriterion)});

		return properties;
	}

	public void setProperties(Map<String, String[]> parameters) {
		try {
			String json = parameters.get(_prefix + JSON)[0];

			JSONDeserializer<Map<String, ?>> jsonDeserializer =
				JSONFactoryUtil.createJSONDeserializer();

			Map<String, ?> map = jsonDeserializer.deserialize(json);

			for (String serializableField : _serializableFields) {
				Class<?> serializableFieldClass = PropertyUtils.getPropertyType(
					_itemSelectorCriterion, serializableField);

				Object value = map.get(serializableField);

				if (serializableFieldClass.isArray() &&
					List.class.isInstance(value)) {

					List<?> list = (List<?>)value;

					value = list.toArray(
						(Object[])Array.newInstance(
							serializableFieldClass.getComponentType(),
							list.size()));
				}
				else if ((serializableFieldClass == Set.class) &&
						 List.class.isInstance(value)) {

					Set<Class<?>> classes = new HashSet<>();

					try {
						for (String className : (List<String>)value) {
							Class<?> clazz = Class.forName(className);

							classes.add(clazz);
						}

						value = classes;
					}
					catch (Exception e) {
						value = SetUtil.fromCollection((List<?>)value);
					}
				}

				PropertyUtils.setProperty(
					_itemSelectorCriterion, serializableField, value);
			}
		}
		catch (IllegalAccessException | InvocationTargetException |
			NoSuchMethodException e) {

			throw new SystemException(e);
		}
	}

	private void _initSerializableFields() {
		List<String> list = new ArrayList<>();

		try {
			Map<String, Object> map = PropertyUtils.describe(
				_itemSelectorCriterion);

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();

				if (_isInternalProperty(key)) {
					continue;
				}

				list.add(key);
			}
		}
		catch (IllegalAccessException | InvocationTargetException |
			NoSuchMethodException e) {

			throw new SystemException(e);
		}

		_serializableFields = list.toArray(new String[list.size()]);
	}

	private boolean _isInternalProperty(String name) {
		if (name.equals("availableReturnTypes") || name.equals("class")) {
			return true;
		}

		return false;
	}

	private final T _itemSelectorCriterion;
	private final String _prefix;
	private String[] _serializableFields;

}