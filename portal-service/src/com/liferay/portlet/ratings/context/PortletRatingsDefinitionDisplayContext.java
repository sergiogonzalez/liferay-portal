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

package com.liferay.portlet.ratings.context;

import com.liferay.portlet.ratings.transformer.PortletRatingsDefinitionUtil;
import com.liferay.portlet.ratings.transformer.PortletRatingsDefinitionValues;
import com.liferay.portlet.ratings.RatingsType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class PortletRatingsDefinitionDisplayContext {

	public PortletRatingsDefinitionDisplayContext() {
		_portletRatingDefinitionsMap = _populatePortletRatingDefinitionsMap();
	}

	public Map<String, Map<String, RatingsType>>
		getPortletRatingDefinitionsMap() {

		return Collections.unmodifiableMap(_portletRatingDefinitionsMap);
	}

	private Map<String, Map<String, RatingsType>>
		_populatePortletRatingDefinitionsMap() {

		Map<String, Map<String, RatingsType>> portletRatingDefinitionsMap =
			new HashMap<>();

		Map<String, PortletRatingsDefinitionValues>
			portletRatingsDefinitionValuesMap =
				PortletRatingsDefinitionUtil.
					getPortletRatingsDefinitionBeansMap();

		for (String className : portletRatingsDefinitionValuesMap.keySet()) {
			PortletRatingsDefinitionValues portletRatingsDefinitionValues =
				portletRatingsDefinitionValuesMap.get(className);

			String portletId = portletRatingsDefinitionValues.getPortletId();

			Map<String, RatingsType> ratingsTypeMap =
				portletRatingDefinitionsMap.get(portletId);

			if ((ratingsTypeMap == null) || ratingsTypeMap.isEmpty()) {
				ratingsTypeMap = new HashMap<>();
			}

			ratingsTypeMap.put(
				className,
				portletRatingsDefinitionValues.getDefaultRatingsType());

			portletRatingDefinitionsMap.put(portletId, ratingsTypeMap);
		}

		return portletRatingDefinitionsMap;
	}

	private final Map<String, Map<String, RatingsType>>
		_portletRatingDefinitionsMap;

}