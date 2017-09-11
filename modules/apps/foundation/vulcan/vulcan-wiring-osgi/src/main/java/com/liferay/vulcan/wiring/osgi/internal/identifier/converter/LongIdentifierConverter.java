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

package com.liferay.vulcan.wiring.osgi.internal.identifier.converter;

import com.liferay.vulcan.identifier.Identifier;
import com.liferay.vulcan.identifier.LongIdentifier;
import com.liferay.vulcan.identifier.converter.IdentifierConverter;
import com.liferay.vulcan.result.Try;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;

/**
 * This converter can be used to convert from a generic identifier to a {@link
 * LongIdentifier}
 *
 * The class {@link LongIdentifier} can then be provided as a parameter in
 * {@link com.liferay.vulcan.resource.builder.RoutesBuilder} methods.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class LongIdentifierConverter
	implements IdentifierConverter<LongIdentifier> {

	@Override
	public LongIdentifier convert(Identifier identifier) {
		return new LongIdentifier() {

			@Override
			public long getIdAsLong() {
				Try<Long> longTry = Try.fromFallible(
					() -> Long.parseLong(identifier.getId()));

				return longTry.orElseThrow(BadRequestException::new);
			}

			@Override
			public String getType() {
				return identifier.getType();
			}

		};
	}

}