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

package com.liferay.document.library.jaxrs.provider;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Variant;

/**
 * @author Carlos Sierra Andrés
 */
public class VariantUtil {

	public static Optional<Variant> safeSelectVariant(
		Request request, List<Variant> variants) {

		try {
			if (variants == null || variants.isEmpty()) {
				return Optional.empty();
			}
			else {
				Variant variant = request.selectVariant(variants);

				if (variant == null) {
					return Optional.empty();
				}
				else {
					return Optional.of(variant);
				}
			}
		}
		catch (Exception e) {
			return Optional.empty();
		}
	}

}