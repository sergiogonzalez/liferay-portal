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

package com.liferay.vulcan.wiring.osgi.internal.resource.builder;

import com.liferay.vulcan.binary.BinaryFunction;
import com.liferay.vulcan.identifier.Identifier;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.wiring.osgi.model.RelatedCollection;
import com.liferay.vulcan.wiring.osgi.model.RelatedModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public class RepresentorBuilderImpl<T, U extends Identifier>
	implements RepresentorBuilder<T, U> {

	public RepresentorBuilderImpl(
		Class<T> modelClass,
		Map<String, Function<?, ? extends Identifier>> identifierFunctions,
		BiConsumer<String, Class<?>> addRelatedCollectionBiConsumer,
		Map<String, Function<?, Object>> fieldFunctions,
		List<RelatedModel<?, ?>> embeddedRelatedModels,
		List<RelatedModel<?, ?>> linkedRelatedModels, Map<String, String> links,
		List<RelatedCollection<?, ?>> relatedCollections,
		Map<String, BinaryFunction<?>> binaryFunctions, List<String> types) {

		_modelClass = modelClass;
		_identifierFunctions = identifierFunctions;
		_addRelatedCollectionBiConsumer = addRelatedCollectionBiConsumer;
		_fieldFunctions = fieldFunctions;
		_embeddedRelatedModels = embeddedRelatedModels;
		_linkedRelatedModels = linkedRelatedModels;
		_links = links;
		_relatedCollections = relatedCollections;
		_binaryFunctions = binaryFunctions;
		_types = types;
	}

	@Override
	public FirstStep<T> identifier(Function<T, U> identifierFunction) {
		_identifierFunctions.put(_modelClass.getName(), identifierFunction);

		return new FirstStep<T>() {

			@Override
			public <S> FirstStep<T> addBidirectionalModel(
				String key, String relatedKey, Class<S> modelClass,
				Function<T, Optional<S>> modelFunction) {

				_linkedRelatedModels.add(
					new RelatedModel<>(key, modelClass, modelFunction));

				_addRelatedCollectionBiConsumer.accept(relatedKey, modelClass);

				return this;
			}

			@Override
			public <S> FirstStep<T> addBinary(
				String key, BinaryFunction<T> binaryFunction) {

				_binaryFunctions.put(key, binaryFunction);

				return this;
			}

			@Override
			public <S> FirstStep<T> addEmbeddedModel(
				String key, Class<S> modelClass,
				Function<T, Optional<S>> modelFunction) {

				_embeddedRelatedModels.add(
					new RelatedModel<>(key, modelClass, modelFunction));

				return this;
			}

			@Override
			public FirstStep<T> addField(
				String key, Function<T, Object> fieldFunction) {

				_fieldFunctions.put(key, fieldFunction);

				return this;
			}

			@Override
			public FirstStep<T> addLink(String key, String url) {
				_links.put(key, url);

				return this;
			}

			@Override
			public <S> FirstStep<T> addLinkedModel(
				String key, Class<S> modelClass,
				Function<T, Optional<S>> modelFunction) {

				_linkedRelatedModels.add(
					new RelatedModel<>(key, modelClass, modelFunction));

				return this;
			}

			@Override
			public <S> FirstStep<T> addRelatedCollection(
				String key, Class<S> modelClass) {

				_relatedCollections.add(
					new RelatedCollection<>(key, modelClass));

				return this;
			}

			@Override
			public FirstStep<T> addType(String type) {
				_types.add(type);

				return this;
			}

		};
	}

	private final BiConsumer<String, Class<?>> _addRelatedCollectionBiConsumer;
	private final Map<String, BinaryFunction<?>> _binaryFunctions;
	private final List<RelatedModel<?, ?>> _embeddedRelatedModels;
	private final Map<String, Function<?, Object>> _fieldFunctions;
	private final Map<String, Function<?, ? extends Identifier>>
		_identifierFunctions;
	private final List<RelatedModel<?, ?>> _linkedRelatedModels;
	private final Map<String, String> _links;
	private final Class<T> _modelClass;
	private final List<RelatedCollection<?, ?>> _relatedCollections;
	private final List<String> _types;

}