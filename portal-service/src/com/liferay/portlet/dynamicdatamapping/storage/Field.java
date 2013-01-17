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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class Field implements Serializable {

	public Field() {
	}

	public Field(
		long ddmStructureId, String name, List<Serializable> values,
		Locale locale) {

		_ddmStructureId = ddmStructureId;
		_name = name;
		_valuesMap.put(locale, values);
	}

	public Field(
		long ddmStructureId, String name,
		Map<Locale, List<Serializable>> valuesMap, Locale defaultLocale) {

		_ddmStructureId = ddmStructureId;
		_defaultLocale = defaultLocale;
		_name = name;
		_valuesMap = valuesMap;
	}

	public Field(long ddmStructureId, String name, Serializable value) {
		_ddmStructureId = ddmStructureId;
		_name = name;

		setValue(value);
	}

	public Field(String name, Serializable value) {
		this(0, name, value);
	}

	public void addValue(Locale locale, Serializable value) {
		List<Serializable> values = _valuesMap.get(locale);

		if (values == null) {
			values = new ArrayList<Serializable>();

			_valuesMap.put(locale, values);
		}

		values.add(value);
	}

	public void addValues(Locale locale, List<Serializable> values) {
		for (Serializable value : values) {
			addValue(locale, value);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Field)) {
			return false;
		}

		Field field = (Field)obj;

		if ((_ddmStructureId == field._ddmStructureId) &&
			Validator.equals(_name, field._name) &&
			Validator.equals(_valuesMap, field._valuesMap)) {

			return true;
		}

		return false;
	}

	public Set<Locale> getAvailableLocales() {
		return _valuesMap.keySet();
	}

	public String getDataType() throws PortalException, SystemException {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.getFieldDataType(_name);
	}

	public DDMStructure getDDMStructure() throws SystemException {
		return DDMStructureLocalServiceUtil.fetchStructure(_ddmStructureId);
	}

	public long getDDMStructureId() {
		return _ddmStructureId;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public String getName() {
		return _name;
	}

	public String getRenderedValue(Locale locale)
		throws PortalException, SystemException {

		FieldRenderer fieldRenderer = getFieldRenderer();

		return fieldRenderer.render(this, locale);
	}

	public String getRenderedValue(Locale locale, int valueIndex)
		throws PortalException, SystemException {

		FieldRenderer fieldRenderer = getFieldRenderer();

		return fieldRenderer.render(this, locale, valueIndex);
	}

	public String getType() throws PortalException, SystemException {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.getFieldType(_name);
	}

	public Serializable getValue() {
		Locale defaultLocale = getDefaultLocale();

		return getValue(defaultLocale);
	}

	public Serializable getValue(Locale locale) {
		List<Serializable> values = _getValues(locale);

		if (values.isEmpty()) {
			return null;
		}

		try {
			DDMStructure ddmStructure = getDDMStructure();

			if (ddmStructure == null) {
				return values.get(0);
			}

			boolean repeatable = isRepeatable();

			if (repeatable) {
				return FieldConstants.getSerializable(getDataType(), values);
			}

			return values.get(0);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return null;
	}

	public Serializable getValue(Locale locale, int index) {
		List<Serializable> values = _getValues(locale);

		return values.get(index);
	}

	public List<Serializable> getValues(Locale locale) {
		return _getValues(locale);
	}

	public Map<Locale, List<Serializable>> getValuesMap() {
		return _valuesMap;
	}

	public boolean isPrivate() {
		try {
			DDMStructure ddmStructure = getDDMStructure();

			return ddmStructure.isFieldPrivate(_name);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isRepeatable() throws PortalException, SystemException {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.isFieldRepeatable(_name);
	}

	public void setDDMStructureId(long ddmStructureId) {
		_ddmStructureId = ddmStructureId;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValue(Locale locale, Serializable value) {
		Class<?> clazz = value.getClass();

		List<Serializable> values = null;

		if (clazz.isArray()) {
			values = ListUtil.fromArray((Serializable[])value);
		}
		else {
			values = new ArrayList<Serializable>();

			values.add(value);
		}

		_valuesMap.put(locale, values);
	}

	public void setValue(Serializable value) {
		setValue(LocaleUtil.getDefault(), value);
	}

	public void setValues(Locale locale, List<Serializable> values) {
		_valuesMap.put(locale, values);
	}

	public void setValuesMap(Map<Locale, List<Serializable>> valuesMap) {
		_valuesMap = valuesMap;
	}

	protected FieldRenderer getFieldRenderer()
		throws PortalException, SystemException {

		DDMStructure ddmStructure = getDDMStructure();

		String dataType = null;

		if (ddmStructure != null) {
			dataType = getDataType();
		}

		return FieldRendererFactory.getFieldRenderer(dataType);
	}

	private List<Serializable> _getValues(Locale locale) {
		Set<Locale> availableLocales = getAvailableLocales();

		if (!availableLocales.contains(locale)) {
			locale = getDefaultLocale();
		}

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		return _valuesMap.get(locale);
	}

	private static Log _log = LogFactoryUtil.getLog(Field.class);

	private long _ddmStructureId;
	private Locale _defaultLocale;
	private String _name;
	private Map<Locale, List<Serializable>> _valuesMap =
		new HashMap<Locale, List<Serializable>>();

}