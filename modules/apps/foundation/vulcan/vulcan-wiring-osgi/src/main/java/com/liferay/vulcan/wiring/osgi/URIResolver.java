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

package com.liferay.vulcan.wiring.osgi;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.contributor.APIContributor;
import com.liferay.vulcan.contributor.ResourceMapper;
import com.liferay.vulcan.representor.ModelRepresentorMapper;
import com.liferay.vulcan.resource.CollectionResource;
import com.liferay.vulcan.resource.Resource;
import com.liferay.vulcan.uri.CollectionResourceURITransformer;
import com.liferay.vulcan.wiring.osgi.internal.GenericUtil;
import com.liferay.vulcan.wiring.osgi.internal.ModelURIFunctions;
import com.liferay.vulcan.wiring.osgi.internal.ServiceReferenceServiceTuple;

import java.net.URI;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.ws.rs.core.UriBuilder;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = URIResolver.class)
public class URIResolver {

	public <T> Optional<String> getCollectionResourceURIOptional(
		Class<T> modelClass) {

		ModelURIFunctions<T> modelURIFunctions =
			(ModelURIFunctions<T>)_modelURIFunctions.get(modelClass.getName());

		Optional<Supplier<Optional<String>>> optional = Optional.of(
			modelURIFunctions.getCollectionResourceURISupplier());

		return optional.flatMap(Supplier::get);
	}

	public <T> Optional<String> getSingleResourceURIOptional(
		Class<T> modelClass, T model) {

		ModelURIFunctions<T> modelURIFunctions =
			(ModelURIFunctions<T>)_modelURIFunctions.get(modelClass.getName());

		Optional<Function<T, Optional<String>>> optional = Optional.of(
			modelURIFunctions.getSingleResourceURIFunction());

		return optional.flatMap(
			singleResourceURIFunction -> singleResourceURIFunction.apply(
				model));
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<APIContributor> serviceReference) {

		APIContributor apiContributor = _bundleContext.getService(
			serviceReference);

		_addAPIContributor(serviceReference, apiContributor);
		_addResourceURIs(apiContributor);
	}

	protected void unsetServiceReference(
		ServiceReference<APIContributor> serviceReference) {

		APIContributor apiContributor = _bundleContext.getService(
			serviceReference);

		_removeAPIContributor(apiContributor);
		_removeResourceURIs(apiContributor);

		TreeSet<ServiceReferenceServiceTuple<APIContributor>>
			serviceReferenceServiceTuples = _apiContributors.get(
				apiContributor.getPath());

		if (!serviceReferenceServiceTuples.isEmpty()) {
			ServiceReferenceServiceTuple<APIContributor>
				serviceReferenceServiceTuple =
					serviceReferenceServiceTuples.first();

			_addResourceURIs(serviceReferenceServiceTuple.getService());
		}
	}

	private void _addAPIContributor(
		ServiceReference<APIContributor> serviceReference,
		APIContributor apiContributor) {

		TreeSet<ServiceReferenceServiceTuple<APIContributor>>
			serviceReferenceServiceTuples = _apiContributors.computeIfAbsent(
				apiContributor.getPath(), name -> new TreeSet<>());

		ServiceReferenceServiceTuple<APIContributor>
			serviceReferenceServiceTuple = new ServiceReferenceServiceTuple<>(
				serviceReference, apiContributor);

		serviceReferenceServiceTuples.add(serviceReferenceServiceTuple);
	}

	private <T> void _addCollectionResourceURIs(
		String path, CollectionResource<T> collectionResource) {

		Class<T> modelClass = GenericUtil.getGenericClass(
			collectionResource, CollectionResource.class);

		Function<T, Optional<String>> singleResourceURIFunction = t -> {
			Optional<ModelRepresentorMapper<T>> optional =
				_representorManager.getModelRepresentorMapperOptional(
					modelClass);

			return optional.map(
				modelRepresentorMapper -> {
					String identifier = _representorManager.getIdentifier(
						modelClass, t);

					String transformedPath = path;

					if (_collectionResourceURITransformer != null) {
						transformedPath =
							_collectionResourceURITransformer.
								transformCollectionItemSingleResourceURI(
									path, modelClass, t, collectionResource);
					}

					UriBuilder uriBuilder = UriBuilder.fromPath(
						transformedPath);

					uriBuilder.path(
						CollectionResource.class,
						"getCollectionItemSingleResource");

					URI singleResourceURI = uriBuilder.build(identifier);

					return singleResourceURI.toString();
				});
		};

		Supplier<Optional<String>> collectionResourceURISupplier = () -> {
			Optional<ModelRepresentorMapper<T>> optional =
				_representorManager.getModelRepresentorMapperOptional(
					modelClass);

			return optional.map(
				modelRepresentorMapper -> {
					String transformedPath = path;

					if (_collectionResourceURITransformer != null) {
						transformedPath =
							_collectionResourceURITransformer.transformPageURI(
								path, modelClass, collectionResource);
					}

					UriBuilder uriBuilder = UriBuilder.fromPath(
						transformedPath);

					URI uri = uriBuilder.build();

					return uri.toString();
				});
		};

		_modelURIFunctions.computeIfAbsent(
			modelClass.getName(),
			key -> new ModelURIFunctions<>(
				collectionResourceURISupplier, singleResourceURIFunction));
	}

	private void _addResourceURIs(APIContributor apiContributor) {
		String apiContributorPath = apiContributor.getPath();

		if (apiContributor instanceof ResourceMapper) {
			ResourceMapper resourceMapper = (ResourceMapper)apiContributor;

			resourceMapper.mapResources(
				(path, resource) -> _addResourceURIs(
					apiContributorPath + "/" + path, resource));
		}
		else if (apiContributor instanceof Resource) {
			Resource resource = (Resource)apiContributor;

			_addResourceURIs(apiContributorPath, resource);
		}
	}

	private <T> void _addResourceURIs(String path, Resource<T> resource) {
		if (resource instanceof CollectionResource) {
			CollectionResource<T> collectionResource =
				(CollectionResource<T>)resource;

			_addCollectionResourceURIs(path, collectionResource);
		}
	}

	private void _removeAPIContributor(APIContributor apiContributor) {
		TreeSet<ServiceReferenceServiceTuple<APIContributor>>
			serviceReferenceServiceTuples = _apiContributors.get(
				apiContributor.getPath());

		serviceReferenceServiceTuples.removeIf(
			tuple -> tuple.getService() == apiContributor);
	}

	private <T> void _removeCollectionResourceURIs(
			CollectionResource<T> collectionResource)
		throws IllegalArgumentException {

		Class<Object> genericClass = GenericUtil.getGenericClass(
			collectionResource, CollectionResource.class);

		_modelURIFunctions.remove(genericClass.getName());
	}

	private void _removeResourceURIs(APIContributor apiContributor) {
		if (apiContributor instanceof ResourceMapper) {
			ResourceMapper resourceMapper = (ResourceMapper)apiContributor;

			resourceMapper.mapResources(
				(path, resource) -> _removeResourceURIs(resource));
		}
		else if (apiContributor instanceof Resource) {
			Resource resource = (Resource)apiContributor;

			_removeResourceURIs(resource);
		}
	}

	private <T> void _removeResourceURIs(Resource<T> resource) {
		if (resource instanceof CollectionResource) {
			CollectionResource<T> collectionResource =
				(CollectionResource<T>)resource;

			_removeCollectionResourceURIs(collectionResource);
		}
	}

	private final Map<String,
		TreeSet<ServiceReferenceServiceTuple<APIContributor>>>
			_apiContributors = new ConcurrentHashMap<>();
	private final BundleContext _bundleContext = FrameworkUtil.getBundle(
		URIResolver.class).getBundleContext();

	@Reference(cardinality = OPTIONAL, policyOption = GREEDY)
	private CollectionResourceURITransformer _collectionResourceURITransformer;

	private final Map<String, ModelURIFunctions<?>> _modelURIFunctions =
		new HashMap<>();

	@Reference
	private RepresentorManager _representorManager;

}