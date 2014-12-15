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

package com.liferay.portal.metadata;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.metadata.RawMetadataProcessor;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tika.metadata.ClimateForcast;
import org.apache.tika.metadata.CreativeCommons;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Geographic;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.MSOffice;
import org.apache.tika.metadata.Message;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.TIFF;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.metadata.TikaMimeKeys;
import org.apache.tika.metadata.XMPDM;

/**
 * @author Alexander Chow
 */
public abstract class BaseRawMetadataProcessor implements RawMetadataProcessor {

	@Override
	public Map<String, Field[]> getFields() {
		return _fields;
	}

	@Override
	public Map<String, DDMFormValues> getRawMetadataMap(
			String extension, String mimeType, File file)
		throws PortalException {

		Metadata metadata = extractMetadata(extension, mimeType, file);

		return createDDMFormValuesMap(metadata, getFields());
	}

	@Override
	public Map<String, DDMFormValues> getRawMetadataMap(
			String extension, String mimeType, InputStream inputStream)
		throws PortalException {

		Metadata metadata = extractMetadata(extension, mimeType, inputStream);

		return createDDMFormValuesMap(metadata, getFields());
	}

	protected DDMFormValues createDDMFormValues(
		Metadata metadata, Field[] fields) {

		DDMFormValues ddmFormValues = new DDMFormValues(new DDMForm());
		
		for (Field field : fields) {
			Class<?> fieldClass = field.getDeclaringClass();

			String fieldClassName = fieldClass.getSimpleName();

			String name = fieldClassName.concat(
				StringPool.UNDERLINE).concat(field.getName());

			String value = getMetadataValue(metadata, field);

			if (value == null) {
				continue;
			}

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();
			
			ddmFormFieldValue.setName(name);
			ddmFormFieldValue.setValue(new UnlocalizedValue(value));
			
			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	protected Map<String, DDMFormValues> createDDMFormValuesMap(
		Metadata metadata, Map<String, Field[]> fieldsMap) {

		Map<String, DDMFormValues> ddmFormValuesMap =
				new HashMap<String, DDMFormValues>();

		if (metadata == null) {
			return ddmFormValuesMap;
		}

		for (String key : fieldsMap.keySet()) {
			Field[] fields = fieldsMap.get(key);

			DDMFormValues ddmFormValues = createDDMFormValues(metadata, fields);

			Set<String> names = 
					ddmFormValues.getDDMFormFieldValuesMap().keySet();

			if (names.isEmpty()) {
				continue;
			}

			ddmFormValuesMap.put(key, ddmFormValues);
		}

		return ddmFormValuesMap;
	}

	protected abstract Metadata extractMetadata(
			String extension, String mimeType, File file)
		throws PortalException;

	protected abstract Metadata extractMetadata(
			String extension, String mimeType, InputStream inputStream)
		throws PortalException;

	protected Object getFieldValue(Metadata metadata, Field field) {
		Object fieldValue = null;

		try {
			fieldValue = field.get(metadata);
		}
		catch (IllegalAccessException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The property " + field.getName() +
						" will not be added to the metatada set");
			}
		}

		return fieldValue;
	}

	protected String getMetadataValue(Metadata metadata, Field field) {
		Object fieldValue = getFieldValue(metadata, field);

		if (fieldValue instanceof String) {
			return metadata.get((String)fieldValue);
		}

		Property property = (Property)fieldValue;

		return metadata.get(property.getName());
	}

	private static void _addFields(Class<?> clazz, List<Field> fields) {
		for (Field field : clazz.getFields()) {
			fields.add(field);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRawMetadataProcessor.class);

	private static final Map<String, Field[]> _fields = new HashMap<>();

	static {
		List<Field> fields = new ArrayList<>();

		_addFields(ClimateForcast.class, fields);
		_addFields(CreativeCommons.class, fields);
		_addFields(DublinCore.class, fields);
		_addFields(Geographic.class, fields);
		_addFields(HttpHeaders.class, fields);
		_addFields(Message.class, fields);
		_addFields(MSOffice.class, fields);
		_addFields(TIFF.class, fields);
		_addFields(TikaMetadataKeys.class, fields);
		_addFields(TikaMimeKeys.class, fields);
		_addFields(XMPDM.class, fields);

		_fields.put(
			TIKA_RAW_METADATA, fields.toArray(new Field[fields.size()]));
	}

}