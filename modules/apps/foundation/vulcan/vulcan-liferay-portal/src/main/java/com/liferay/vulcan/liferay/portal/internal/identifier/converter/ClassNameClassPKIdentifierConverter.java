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

package com.liferay.vulcan.liferay.portal.internal.identifier.converter;

import com.liferay.vulcan.identifier.Identifier;
import com.liferay.vulcan.identifier.converter.IdentifierConverter;
import com.liferay.vulcan.liferay.portal.identifier.ClassNameClassPKIdentifier;
import com.liferay.vulcan.liferay.portal.identifier.creator.ClassNameClassPKIdentifierCreator;
import com.liferay.vulcan.liferay.portal.internal.identifier.ClassNameClassPKIdentifierImpl;
import com.liferay.vulcan.resource.Resource;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.wiring.osgi.manager.ResourceManager;

import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * This converter can be used to convert from a generic identifier to a {@link
 * ClassNameClassPKIdentifier}
 *
 * <p>
 * The class {@link ClassNameClassPKIdentifier} can then be provided as a
 * parameter in {@link com.liferay.vulcan.resource.builder.RoutesBuilder}
 * methods.
 * </p>
 *
 * <p>
 * The <code>className</code>/<code>classPK</code> are extracted from the
 * destructuring of the ID in the case this is the identifier of a single
 * resource. In the case of a collection, the <code>className</code> is
 * extracted by finding the <code>Resource's</code> class name matching the
 * <code>type</code> as its path. If no matching resource is found, throws an
 * exception. Secondly, the <code>classPK</code> is extracted by converting the
 * ID to long.
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ClassNameClassPKIdentifierConverter
	implements ClassNameClassPKIdentifierCreator,
			   IdentifierConverter<ClassNameClassPKIdentifier> {

	@Override
	public ClassNameClassPKIdentifier convert(Identifier identifier) {
		String id = identifier.getId();
		String type = identifier.getType();

		String[] components = id.split(":");

		if (components.length == 2) {
			return _getClassNameClassPKIdentifier(
				type, id, components[0], components[1]);
		}

		return _getClassNameClassPKIdentifier(type, id, type, id);
	}

	@Override
	public ClassNameClassPKIdentifier create(String className, long classPK) {
		Optional<Resource<Object, Identifier>> optional =
			_resourceManager.getResourceOptional(className);

		if (!optional.isPresent()) {
			throw new NotFoundException(
				"Unable to get a resource with class name " + className);
		}

		Resource<Object, Identifier> resource = optional.get();

		return new ClassNameClassPKIdentifier() {

			@Override
			public String getClassName() {
				return className;
			}

			@Override
			public long getClassPK() {
				return classPK;
			}

			@Override
			public String getId() {
				return resource.getPath() + ":" + getClassPK();
			}

		};
	}

	private Long _getAsLong(String id) {
		Try<Long> longTry = Try.fromFallible(() -> Long.parseLong(id));

		return longTry.orElseThrow(
			() -> new BadRequestException(
				"Unable to convert " + id + " to a long class PK"));
	}

	private ClassNameClassPKIdentifier _getClassNameClassPKIdentifier(
		String oldType, String oldId, String type, String id) {

		Long classPK = _getAsLong(id);
		String className = _resourceManager.getClassName(type);

		if (className == null) {
			throw new NotFoundException(
				"Unable to get a resource with type " + type);
		}

		return new ClassNameClassPKIdentifierImpl(
			className, classPK, oldType, oldId);
	}

	@Reference
	private ResourceManager _resourceManager;

}