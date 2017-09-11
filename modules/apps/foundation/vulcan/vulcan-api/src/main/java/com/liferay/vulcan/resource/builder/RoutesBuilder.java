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

package com.liferay.vulcan.resource.builder;

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
import com.liferay.vulcan.resource.Routes;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Use instances of this builder to create {@link Routes} of a {@link
 * com.liferay.vulcan.resource.Resource}.
 *
 * @author Alejandro Hernández
 */
@SuppressWarnings("unused")
public interface RoutesBuilder<T, U extends Identifier> {

	/**
	 * Constructs the <code>Routes</code> instance with the information provided
	 * to the builder.
	 *
	 * @return the <code>Routes</code> instance.
	 */
	public Routes<T> build();

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  biFunction the function that will be used to calculate the single
	 *         model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <A> RoutesBuilder<T, U> collectionItem(
		BiFunction<U, A, T> biFunction, Class<A> aClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  decaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @param  fClass the class of the seventh parameter of the single model
	 *         function.
	 * @param  gClass the class of the eighth parameter of the single model
	 *         function.
	 * @param  hClass the class of the ninth parameter of the single model
	 *         function.
	 * @param  iClass the class of the tenth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E, F, G, H, I> RoutesBuilder<T, U> collectionItem(
		DecaFunction<U, A, B, C, D, E, F, G, H, I, T> decaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass,
		Class<I> iClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  enneaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @param  fClass the class of the seventh parameter of the single model
	 *         function.
	 * @param  gClass the class of the eighth parameter of the single model
	 *         function.
	 * @param  hClass the class of the ninth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E, F, G, H> RoutesBuilder<T, U> collectionItem(
		EnneaFunction<U, A, B, C, D, E, F, G, H, T> enneaFunction,
		Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
		Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  function the function that will be used to calculate the single
	 *         model.
	 * @return the updated builder.
	 */
	public RoutesBuilder<T, U> collectionItem(Function<U, T> function);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  heptaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @param  fClass the class of the seventh parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E, F> RoutesBuilder<T, U> collectionItem(
		HeptaFunction<U, A, B, C, D, E, F, T> heptaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Class<F> fClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  hexaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E> RoutesBuilder<T, U> collectionItem(
		HexaFunction<U, A, B, C, D, E, T> hexaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  octaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @param  eClass the class of the sixth parameter of the single model
	 *         function.
	 * @param  fClass the class of the seventh parameter of the single model
	 *         function.
	 * @param  gClass the class of the eighth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <A, B, C, D, E, F, G> RoutesBuilder<T, U> collectionItem(
		OctaFunction<U, A, B, C, D, E, F, G, T> octaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
		Class<F> fClass, Class<G> gClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  pentaFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @param  dClass the class of the fifth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <A, B, C, D> RoutesBuilder<T, U> collectionItem(
		PentaFunction<U, A, B, C, D, T> pentaFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  tetraFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @param  cClass the class of the fourth parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <A, B, C> RoutesBuilder<T, U> collectionItem(
		TetraFunction<U, A, B, C, T> tetraFunction, Class<A> aClass,
		Class<B> bClass, Class<C> cClass);

	/**
	 * Adds a route to a single model function.
	 *
	 * @param  triFunction the function that will be used to calculate the
	 *         single model.
	 * @param  aClass the class of the second parameter of the single model
	 *         function.
	 * @param  bClass the class of the third parameter of the single model
	 *         function.
	 * @return the updated builder.
	 */
	public <A, B> RoutesBuilder<T, U> collectionItem(
		TriFunction<U, A, B, T> triFunction, Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  biFunction the function that will be used to calculate the page.
	 * @param  identifierClass the class of the identifier.
	 * @return the updated builder.
	 */
	public <V extends Identifier> RoutesBuilder<T, U> collectionPage(
		BiFunction<Pagination, V, PageItems<T>> biFunction,
		Class<V> identifierClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  decaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the eighth parameter of the page function.
	 * @param  hClass the class of the ninth parameter of the page function.
	 * @return the updated builder.
	 */
	public <V extends Identifier, A, B, C, D, E, F, G, H> RoutesBuilder<T, U>
		collectionPage(
			DecaFunction<Pagination, V, A, B, C, D, E, F, G, H,
				PageItems<T>> decaFunction, Class<V> identifierClass,
			Class<A> aClass, Class<B> bClass, Class<C> cClass, Class<D> dClass,
			Class<E> eClass, Class<F> fClass, Class<G> gClass, Class<H> hClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  enneaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @param  gClass the class of the eighth parameter of the page function.
	 * @return the updated builder.
	 */
	public <V extends Identifier, A, B, C, D, E, F, G> RoutesBuilder<T, U>
		collectionPage(
			EnneaFunction<Pagination, V, A, B, C, D, E, F, G, PageItems<T>>
				enneaFunction, Class<V> identifierClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass, Class<G> gClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  heptaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @return the updated builder.
	 */
	public <V extends Identifier, A, B, C, D, E> RoutesBuilder<T, U>
		collectionPage(
			HeptaFunction<Pagination, V, A, B, C, D, E, PageItems<T>>
				heptaFunction, Class<V> identifierClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  hexaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @return the updated builder.
	 */
	public <V extends Identifier, A, B, C, D> RoutesBuilder<T, U>
		collectionPage(
			HexaFunction<Pagination, V, A, B, C, D, PageItems<T>> hexaFunction,
			Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
			Class<C> cClass, Class<D> dClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  octaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @param  dClass the class of the fifth parameter of the page function.
	 * @param  eClass the class of the sixth parameter of the page function.
	 * @param  fClass the class of the seventh parameter of the page function.
	 * @return the updated builder.
	 */
	public <V extends Identifier, A, B, C, D, E, F> RoutesBuilder<T, U>
		collectionPage(
			OctaFunction<Pagination, V, A, B, C, D, E, F, PageItems<T>>
				octaFunction, Class<V> identifierClass, Class<A> aClass,
			Class<B> bClass, Class<C> cClass, Class<D> dClass, Class<E> eClass,
			Class<F> fClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  pentaFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @param  cClass the class of the fourth parameter of the page function.
	 * @return the updated builder.
	 */
	public <V extends Identifier, A, B, C> RoutesBuilder<T, U> collectionPage(
		PentaFunction<Pagination, V, A, B, C, PageItems<T>> pentaFunction,
		Class<V> identifierClass, Class<A> aClass, Class<B> bClass,
		Class<C> cClass);

	/**
	 * Adds a route to a collection page function.
	 *
	 * @param  tetraFunction the function that will be used to calculate the
	 *         page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @param  bClass the class of the third parameter of the page function.
	 * @return the updated builder.
	 */
	public <V extends Identifier, A, B> RoutesBuilder<T, U> collectionPage(
		TetraFunction<Pagination, V, A, B, PageItems<T>> tetraFunction,
		Class<V> identifierClass, Class<A> aClass, Class<B> bClass);

	/**
	 * Adds a route to a collection page function.
	 * #filteredCollectionPage(TriFunction,
	 *
	 * @param  triFunction the function that will be used to calculate the page.
	 * @param  identifierClass the class of the identifier.
	 * @param  aClass the class of the second parameter of the page function.
	 * @return the updated builder.
	 */
	public <V extends Identifier, A> RoutesBuilder<T, U> collectionPage(
		TriFunction<Pagination, V, A, PageItems<T>> triFunction,
		Class<V> identifierClass, Class<A> aClass);

}