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

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Alejandro Tardín
 */
@Component(service = FormNavigatorEntryConfigurationRetriever.class)
public class FormNavigatorEntryConfigurationRetriever {

	public Optional<List<String>> getFormNavigatorEntryKeys(
		String formNavigatorId, String categoryKey, String context) {

		return _formNavigatorEntryConfigurationRetrievers.stream().map(
			retriever -> retriever.getFormNavigatorEntryKeys(
				formNavigatorId, categoryKey, context)).reduce(
				Optional.empty(), this::_preferLast);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	public void setFormNavigatorEntryConfigurationHandler(
		FormNavigatorEntryConfigurationHandler
			formNavigatorEntryConfigurationHandler) {

		_formNavigatorEntryConfigurationRetrievers.add(
			formNavigatorEntryConfigurationHandler);
	}

	public void unsetFormNavigatorEntryConfigurationHandler(
		FormNavigatorEntryConfigurationHandler
			formNavigatorEntryConfigurationHandler) {

		_formNavigatorEntryConfigurationRetrievers.remove(
			formNavigatorEntryConfigurationHandler);
	}

	private Optional<List<String>> _preferLast(
		Optional<List<String>> previous, Optional<List<String>> current) {

		if (current.isPresent()) {
			return current;
		}

		return previous;
	}

	private volatile List<FormNavigatorEntryConfigurationHandler>
		_formNavigatorEntryConfigurationRetrievers =
			new CopyOnWriteArrayList<>();

}