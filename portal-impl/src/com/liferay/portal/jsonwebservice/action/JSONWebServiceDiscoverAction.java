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

package com.liferay.portal.jsonwebservice.action;

import com.liferay.portal.json.data.FileData;
import com.liferay.portal.json.transformer.FlexjsonBeanAnalyzerTransformer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.javadoc.JavadocManagerUtil;
import com.liferay.portal.kernel.javadoc.JavadocMethod;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceNaming;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import flexjson.JSONContext;
import flexjson.PathExpression;

import java.io.File;
import java.io.Serializable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jodd.util.ReflectUtil;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public class JSONWebServiceDiscoverAction implements JSONWebServiceAction {

	public JSONWebServiceDiscoverAction(HttpServletRequest request) {
		_basePath = request.getServletPath();
		_baseURL = String.valueOf(request.getRequestURL());

		ServletContext servletContext = request.getServletContext();

		_contextName = GetterUtil.getString(
			ParamUtil.getString(
				request, "contextName", servletContext.getServletContextName()),
			StringPool.BLANK);
		_jsonWebServiceNaming =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceNaming();
	}

	@Override
	public JSONWebServiceActionMapping getJSONWebServiceActionMapping() {
		return null;
	}

	@Override
	public Object invoke() throws Exception {
		Map<String, Object> resultsMap = new LinkedHashMap<String, Object>();

		resultsMap.put("contextName", _contextName);
		resultsMap.put("basePath", _basePath);
		resultsMap.put("baseURL", _baseURL);
		resultsMap.put("services", _buildJsonWebServiceActionMappingMaps());
		resultsMap.put("types", _buildTypes());
		resultsMap.put("version", ReleaseInfo.getVersion());

		return new DiscoveryContent(resultsMap);
	}

	public static class DiscoveryContent implements JSONSerializable {

		public DiscoveryContent(Map<String, Object> resultsMap) {
			_resultsMap = resultsMap;
		}

		@Override
		public String toJSONString() {
			JSONSerializer jsonSerializer =
				JSONFactoryUtil.createJSONSerializer();

			jsonSerializer.include("types");

			return jsonSerializer.serializeDeep(_resultsMap);
		}

		private Map<String, Object> _resultsMap;

	}

	private List<Map<String, Object>> _buildJsonWebServiceActionMappingMaps()
		throws PortalException {

		List<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceActionMappings(
				_contextName);

		List<Map<String, Object>> jsonWebServiceActionMappingMaps =
			new ArrayList<Map<String, Object>>(
				jsonWebServiceActionMappings.size());

		for (JSONWebServiceActionMapping jsonWebServiceActionMapping :
				jsonWebServiceActionMappings) {

			String path = jsonWebServiceActionMapping.getPath();

			Map<String, Object> jsonWebServiceActionMappingMap =
				new LinkedHashMap<String, Object>();

			if (jsonWebServiceActionMapping.isDeprecated()) {
				jsonWebServiceActionMappingMap.put("deprecated", Boolean.TRUE);
			}

			JavadocMethod javadocMethod =
				JavadocManagerUtil.lookupJavadocMethod(
					jsonWebServiceActionMapping.getRealActionMethod());

			if (javadocMethod != null) {
				String methodComment = javadocMethod.getComment();

				if (methodComment != null) {
					jsonWebServiceActionMappingMap.put(
						"description", javadocMethod.getComment());
				}
			}

			jsonWebServiceActionMappingMap.put(
				"method", jsonWebServiceActionMapping.getMethod());

			jsonWebServiceActionMappingMap.put(
				"name", _getName(jsonWebServiceActionMapping));

			MethodParameter[] methodParameters =
				jsonWebServiceActionMapping.getMethodParameters();

			List<Map<String, String>> parametersList =
				new ArrayList<Map<String, String>>(methodParameters.length);

			for (int i = 0; i < methodParameters.length; i++) {
				MethodParameter methodParameter = methodParameters[i];

				Class<?>[] genericTypes = null;

				try {
					genericTypes = methodParameter.getGenericTypes();
				}
				catch (ClassNotFoundException cnfe) {
					throw new PortalException(cnfe);
				}

				Map<String, String> parameterMap =
					new HashMap<String, String>();

				if (javadocMethod != null) {
					String parameterComment = javadocMethod.getParameterComment(
						i);

					if (!Validator.isBlank(parameterComment)) {
						parameterMap.put("description", parameterComment);
					}
				}

				parameterMap.put("name", methodParameter.getName());
				parameterMap.put(
					"type",
					_formatType(
						methodParameter.getType(), genericTypes, false));

				parametersList.add(parameterMap);
			}

			jsonWebServiceActionMappingMap.put("parameters", parametersList);

			jsonWebServiceActionMappingMap.put("path", path);

			Map<String, String> returnsMap =
				new LinkedHashMap<String, String>();

			if (javadocMethod != null) {
				String returnComment = javadocMethod.getReturnComment();

				if (!Validator.isBlank(returnComment)) {
					returnsMap.put("description", returnComment);
				}
			}

			Method actionMethod = jsonWebServiceActionMapping.getActionMethod();

			returnsMap.put(
				"type",
				_formatType(
					actionMethod.getReturnType(),
					_getGenericReturnTypes(jsonWebServiceActionMapping), true));

			jsonWebServiceActionMappingMap.put("returns", returnsMap);

			jsonWebServiceActionMappingMaps.add(jsonWebServiceActionMappingMap);
		}

		return jsonWebServiceActionMappingMaps;
	}

	private List<Map<String, String>> _buildPropertiesList(Class<?> type) {
		try {
			JSONContext jsonContext = JSONContext.get();

			List<PathExpression> pathExpressions =
				new ArrayList<PathExpression>();

			jsonContext.setPathExpressions(pathExpressions);

			FlexjsonBeanAnalyzerTransformer flexjsonBeanAnalyzerTransformer =
				new FlexjsonBeanAnalyzerTransformer(pathExpressions) {

					@Override
					protected String getTypeName(Class<?> type) {
						return _formatType(type, null, false);
					}

				};

			flexjsonBeanAnalyzerTransformer.transform(type);

			return flexjsonBeanAnalyzerTransformer.getPropertiesList();
		}
		catch (Exception e) {
			return null;
		}
	}

	private List<Map<String, Object>> _buildTypes() {
		List<Map<String, Object>> types = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < _types.size(); i++) {
			Class<?> type = _types.get(i);

			Map<String, Object> map = new LinkedHashMap<String, Object>();

			types.add(map);

			Class<?> modelType = type;

			if (type.isInterface()) {
				try {
					Class<?> clazz = getClass();

					ClassLoader classLoader = clazz.getClassLoader();

					String modelImplClassName =
						_jsonWebServiceNaming.convertModelClassToImplClassName(
							type);

					modelType = classLoader.loadClass(modelImplClassName);
				}
				catch (ClassNotFoundException cnfe) {
				}
			}

			if (modelType.isInterface() ||
				Modifier.isAbstract(modelType.getModifiers())) {

				map.put("interface", Boolean.TRUE);
			}

			List<Map<String, String>> propertiesList = _buildPropertiesList(
				modelType);

			if (propertiesList != null) {
				map.put("properties", propertiesList);
			}

			map.put("type", type.getName());
		}

		return types;
	}

	private String _formatType(
		Class<?> type, Class<?>[] genericTypes, boolean returnType) {

		if (type.isArray()) {
			Class<?> componentType = type.getComponentType();

			return _formatType(componentType, genericTypes, returnType) + "[]";
		}

		if (type.isPrimitive()) {
			return type.getSimpleName();
		}

		if (type.equals(Boolean.class)) {
			return "boolean";
		}
		else if (type.equals(Class.class)) {
			if (!returnType) {
				return "string";
			}
		}
		else if (type.equals(Date.class)) {
			return "long";
		}
		else if (type.equals(File.class)) {
			if (!returnType) {
				return "file";
			}
			else {
				type = FileData.class;
			}
		}
		else if (type.equals(Locale.class) || type.equals(String.class) ||
				 type.equals(TimeZone.class)) {

			return "string";
		}
		else if (type.equals(Object.class) || type.equals(Serializable.class)) {
			return "map";
		}
		else if (ReflectUtil.isSubclass(type, Number.class)) {
			String typeName = null;

			if (type == Character.class) {
				typeName = "char";
			}
			else if (type == Integer.class) {
				typeName = "int";
			}
			else {
				typeName = StringUtil.toLowerCase(type.getSimpleName());
			}

			return typeName;
		}

		String typeName = type.getName();

		if ((type == Collection.class) || (type == List.class) ||
			ReflectUtil.isSubclass(type, List.class)) {

			typeName = "list";
		}
		else if ((type == Map.class) ||
				 ReflectUtil.isSubclass(type, Map.class)) {

			typeName = "map";
		}
		else {
			if (!_types.contains(type)) {
				_types.add(type);
			}
		}

		if (genericTypes == null) {
			return typeName;
		}

		StringBundler sb = new StringBundler(genericTypes.length * 2 + 1);

		sb.append(StringPool.LESS_THAN);

		for (int i = 0; i < genericTypes.length; i++) {
			Class<?> genericType = genericTypes[i];

			if (i != 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(_formatType(genericType, null, returnType));
		}

		sb.append(StringPool.GREATER_THAN);

		return typeName + sb.toString();
	}

	private Class<?>[] _getGenericReturnTypes(
		JSONWebServiceActionMapping jsonWebServiceActionMapping) {

		Method realActionMethod =
			jsonWebServiceActionMapping.getRealActionMethod();

		Type genericReturnType = realActionMethod.getGenericReturnType();

		if (!(genericReturnType instanceof ParameterizedType)) {
			return null;
		}

		ParameterizedType parameterizedType =
			(ParameterizedType)genericReturnType;

		Type[] genericTypes = parameterizedType.getActualTypeArguments();

		Class<?>[] genericReturnTypes = new Class[genericTypes.length];

		for (int i = 0; i < genericTypes.length; i++) {
			Type genericType = genericTypes[i];

			genericReturnTypes[i] = ReflectUtil.getRawType(
				genericType, jsonWebServiceActionMapping.getActionClass());
		}

		return genericReturnTypes;
	}

	private String _getName(
		JSONWebServiceActionMapping jsonWebServiceActionMapping) {

		Class<?> clazz = jsonWebServiceActionMapping.getActionClass();

		String className =
			_jsonWebServiceNaming.convertServiceClassToSimpleName(clazz);

		Method method = jsonWebServiceActionMapping.getRealActionMethod();

		return className.concat(StringPool.POUND).concat(method.getName());
	}

	private String _basePath;
	private String _baseURL;
	private String _contextName;
	private JSONWebServiceNaming _jsonWebServiceNaming;
	private List<Class<?>> _types = new ArrayList<Class<?>>();

}