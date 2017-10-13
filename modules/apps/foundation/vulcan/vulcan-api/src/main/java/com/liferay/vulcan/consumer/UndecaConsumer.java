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

package com.liferay.vulcan.consumer;

import java.util.Objects;

/**
 * Consumer {@link java.util.function.Consumer} with eleven input parameters. As
 * all the consumer interfaces, it receives several arguments and doesn't return
 * anything.
 *
 * Being a functional interface, it can be implemented with a lambda function
 *
 * @author Alejandro Hernández
 * @see    java.util.function.Consumer
 * @review
 */
@FunctionalInterface
public interface UndecaConsumer<A, B, C, D, E, F, G, H, I, J, K> {

	/**
	 * The function to implement (explicitly or with a lambda), that operates
	 * with eleven parameters and returns void
	 *
	 * @param  a the first function argument
	 * @param  b the second function argument
	 * @param  c the third function argument
	 * @param  d the fourth function argument
	 * @param  e the fifth function argument
	 * @param  f the sixth function argument
	 * @param  g the seventh function argument
	 * @param  h the eighth function argument
	 * @param  i the ninth function argument
	 * @param  j the tenth function argument
	 * @param  k the eleventh function argument
	 * @review
	 */
	public void accept(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k);

	/**
	 * Method that creates a lambda function (also a {@code UndecaConsumer})
	 * that executes the {@code accept} method of this instance and then the
	 * {@code accept} method of the {@code after} input parameter when invoked.
	 *
	 * @param  after the {@code UndecaConsumer} to execute after this instance
	 * @return another {@code UndecaConsumer} that executes both inputs (this
	 *         own instance plus the input parameter) in order
	 * @review
	 */
	public default UndecaConsumer<A, B, C, D, E, F, G, H, I, J, K> andThen(
		UndecaConsumer<? super A, ? super B, ? super C, ? super D, ? super E,
			? super F, ? super G, ? super H, ? super I, ? super J, ? super K>
				after) {

		Objects.requireNonNull(after);

		return (a, b, c, d, e, f, g, h, i, j, k) -> {
			accept(a, b, c, d, e, f, g, h, i, j, k);
			after.accept(a, b, c, d, e, f, g, h, i, j, k);
		};
	}

}