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
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveIdentifierConverter;
import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveProvider;
import com.liferay.vulcan.function.DecaFunction;
import com.liferay.vulcan.function.EnneaFunction;
import com.liferay.vulcan.function.HeptaFunction;
import com.liferay.vulcan.function.HexaFunction;
import com.liferay.vulcan.function.OctaFunction;
import com.liferay.vulcan.function.PentaFunction;
import com.liferay.vulcan.function.TetraFunction;
import com.liferay.vulcan.function.TriFunction;
import com.liferay.vulcan.identifier.Identifier;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.wiring.osgi.internal.pagination.PageImpl;
import com.liferay.vulcan.wiring.osgi.internal.resource.RoutesImpl;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 */
public class RoutesBuilderImpl<T, U extends Identifier>
	implements RoutesBuilder<T, U> {

	public RoutesBuilderImpl(
		Class<T> modelClass, Class<U> singleModelIdentifierClass,
		Function<Class<?>, Optional<?>> provideClassFunction,
		BiFunction<Class<? extends Identifier>, Identifier,
			Optional<? extends Identifier>> identifierFunction) {

		_modelClass = modelClass;
		_singleModelIdentifierClass = singleModelIdentifierClass;
		_provideClassFunction = provideClassFunction;
		_identifierFunction = identifierFunction;
	}

	@Override
	public Routes<T> build() {
		return _routesImpl;
	}

	public RoutesBuilder<T, U> collectionBinary(
		Map<String, BinaryFunction<T>> binaryFunction) {

		_routesImpl.setBinaryFunction(binaryFunction::get);

		return this;
	}

	@Override
	public <A> RoutesBuilder<T, U> collectionItem(
		BiFunction<U, A, T> biFunction, Class<A> aClass) {

		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);

				return biFunction.apply(id, a);
			});

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T, U> collectionItem(
		DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
		Class<I> iClass) {

		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);
				H h = _provideClass(hClass);
				I i = _provideClass(iClass);

				return decaFunction.apply(id, a, b, c, d, e, f, g, h, i);
			});

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T, U> collectionItem(
		EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass) {

		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);
				H h = _provideClass(hClass);

				return enneaFunction.apply(id, a, b, c, d, e, f, g, h);
			});

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public RoutesBuilder<T, U> collectionItem(Function<U, T> function) {
		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			function);

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public <A, B, C, D, E, F> RoutesBuilder<T, U> collectionItem(
		HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Class<F> fClass) {

		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);

				return heptaFunction.apply(id, a, b, c, d, e, f);
			});

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public <A, B, C, D, E> RoutesBuilder<T, U> collectionItem(
		HexaFunction<U, A, B, C, D, E, T> hexaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass) {

		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);

				return hexaFunction.apply(id, a, b, c, d, e);
			});

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public <A, B, C, D, E, F, G> RoutesBuilder<T, U> collectionItem(
		OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Class<F> fClass, Class<G> gClass) {

		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);
				E e = _provideClass(eClass);
				F f = _provideClass(fClass);
				G g = _provideClass(gClass);

				return octaFunction.apply(id, a, b, c, d, e, f, g);
			});

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public <A, B, C, D> RoutesBuilder<T, U> collectionItem(
		PentaFunction<U, A, B, C, D, T> pentaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass) {

		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);
				D d = _provideClass(dClass);

				return pentaFunction.apply(id, a, b, c, d);
			});

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public <A, B, C> RoutesBuilder<T, U> collectionItem(
		TetraFunction<U, A, B, C, T> tetraFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass) {

		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);
				C c = _provideClass(cClass);

				return tetraFunction.apply(id, a, b, c);
			});

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public <A, B> RoutesBuilder<T, U> collectionItem(
		TriFunction<U, A, B, T> triFunction, Class<A> aClass, Class<B> bClass) {

		Function<Identifier, U> identifierFunction = _convertIdentifier(
			_singleModelIdentifierClass);

		Function<Identifier, T> modelFunction = identifierFunction.andThen(
			id -> {
				A a = _provideClass(aClass);
				B b = _provideClass(bClass);

				return triFunction.apply(id, a, b);
			});

		_routesImpl.setSingleModelFunction(
			identifier -> modelFunction.andThen(
				_createSingleModelFunction(identifier)
			).apply(
				identifier
			));

		return this;
	}

	@Override
	public <V extends Identifier> RoutesBuilder<T, U> collectionPage(
		BiFunction<Pagination, V, PageItems<T>> biFunction,
		Class<V> identifierClass) {

		Function<Identifier, V> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setPageFunction(
			identifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);

					PageItems<T> pageItems = biFunction.apply(
						pagination, identifier);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						identifier);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		collectionPage(
			DecaFunction<Pagination, V, A, B, C, D, E, F, G, H,
				PageItems<T>> decaFunction, Class<V> identifierClass,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass,
			Class<H> hClass) {

		Function<Identifier, V> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setPageFunction(
			identifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);
					H h = _provideClass(hClass);

					PageItems<T> pageItems = decaFunction.apply(
						pagination, identifier, a, b, c, d, e, f, g, h);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						identifier);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D, E, F, G> RoutesBuilder<T, U>
		collectionPage(
			EnneaFunction<Pagination, V, A, B, C, D, E, F, G, PageItems<T>>
				enneaFunction, Class<V> identifierClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass, Class<G> gClass) {

		Function<Identifier, V> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setPageFunction(
			identifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);
					G g = _provideClass(gClass);

					PageItems<T> pageItems = enneaFunction.apply(
						pagination, identifier, a, b, c, d, e, f, g);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						identifier);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D, E> RoutesBuilder<T, U>
		collectionPage(
			HeptaFunction<Pagination, V, A, B, C, D, E, PageItems<T>>
				heptaFunction, Class<V> identifierClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass) {

		Function<Identifier, V> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setPageFunction(
			identifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);

					PageItems<T> pageItems = heptaFunction.apply(
						pagination, identifier, a, b, c, d, e);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						identifier);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D> RoutesBuilder<T, U>
		collectionPage(
			HexaFunction<Pagination, V, A, B, C, D, PageItems<T>> hexaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass) {

		Function<Identifier, V> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setPageFunction(
			identifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);

					PageItems<T> pageItems = hexaFunction.apply(
						pagination, identifier, a, b, c, d);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						identifier);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C, D, E, F> RoutesBuilder<T, U>
		collectionPage(
			OctaFunction<Pagination, V, A, B, C, D, E, F, PageItems<T>>
				octaFunction, Class<V> identifierClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass) {

		Function<Identifier, V> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setPageFunction(
			identifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);
					D d = _provideClass(dClass);
					E e = _provideClass(eClass);
					F f = _provideClass(fClass);

					PageItems<T> pageItems = octaFunction.apply(
						pagination, identifier, a, b, c, d, e, f);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						identifier);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B, C> RoutesBuilder<T, U> collectionPage(
		PentaFunction<Pagination, V, A, B, C, PageItems<T>> pentaFunction,
		Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass) {

		Function<Identifier, V> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setPageFunction(
			identifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);
					C c = _provideClass(cClass);

					PageItems<T> pageItems = pentaFunction.apply(
						pagination, identifier, a, b, c);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						identifier);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A, B> RoutesBuilder<T, U> collectionPage(
		TetraFunction<Pagination, V, A, B, PageItems<T>> tetraFunction,
		Class<V> identifierClass, Class<A> aClass, Class<B> bClass) {

		Function<Identifier, V> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setPageFunction(
			identifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);
					B b = _provideClass(bClass);

					PageItems<T> pageItems = tetraFunction.apply(
						pagination, identifier, a, b);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						identifier);
				}));

		return this;
	}

	@Override
	public <V extends Identifier, A> RoutesBuilder<T, U> collectionPage(
		TriFunction<Pagination, V, A, PageItems<T>> triFunction,
		Class<V> identifierClass, Class<A> aClass) {

		Function<Identifier, V> identifierFunction = _convertIdentifier(
			identifierClass);

		_routesImpl.setPageFunction(
			identifierFunction.andThen(
				identifier -> {
					Pagination pagination = _provideClass(Pagination.class);
					A a = _provideClass(aClass);

					PageItems<T> pageItems = triFunction.apply(
						pagination, identifier, a);

					return new PageImpl<>(
						_modelClass, pageItems.getItems(),
						pagination.getItemsPerPage(),
						pagination.getPageNumber(), pageItems.getTotalCount(),
						identifier);
				}));

		return this;
	}

	private <V extends Identifier> Function<Identifier, V> _convertIdentifier(
		Class<V> identifierClass) {

		return identifier -> {
			Optional<? extends Identifier> optional = _identifierFunction.apply(
				identifierClass, identifier);

			return optional.map(
				convertedIdentifier -> (V)convertedIdentifier
			).orElseThrow(
				() -> new MustHaveIdentifierConverter(identifierClass)
			);
		};
	}

	private Function<T, SingleModel<T>> _createSingleModelFunction(
		Identifier identifier) {

		return t -> new SingleModel<>(t, _modelClass, identifier);
	}

	private <V> V _provideClass(Class<V> clazz) {
		Optional<?> optional = _provideClassFunction.apply(clazz);

		return optional.map(
			provided -> (V)provided
		).orElseThrow(
			() -> new MustHaveProvider(clazz)
		);
	}

	private final BiFunction<Class<? extends Identifier>, Identifier,
		Optional<? extends Identifier>> _identifierFunction;
	private final Class<T> _modelClass;
	private final Function<Class<?>, Optional<?>> _provideClassFunction;
	private final RoutesImpl<T> _routesImpl = new RoutesImpl<>();
	private final Class<U> _singleModelIdentifierClass;

}