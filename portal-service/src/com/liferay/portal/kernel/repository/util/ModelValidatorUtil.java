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

package com.liferay.portal.kernel.repository.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.ContentReference;
import com.liferay.portal.kernel.repository.model.ModelValidator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.util.DLValidatorUtil;

/**
 * @author Adolfo Pérez
 */
public class ModelValidatorUtil {

	public static final <T> ModelValidator<T> compose(
		ModelValidator<T>... modelValidators) {

		return new CompositeModelValidator<>(modelValidators);
	}

	public static final ModelValidator<ContentReference>
		getDefaultDLModelValidator() {

		return compose(
			getDefaultFileNameModelValidator(),
			getDefaultFileExtensionModelValidator(),
			getDefaultFileSizeModelValidator());
	}

	public static final ModelValidator<ContentReference>
		getDefaultFileExtensionModelValidator() {

		return _defaultFileExtensionModelValidator;
	}

	public static final ModelValidator<ContentReference>
		getDefaultFileNameModelValidator() {

		return _defaultFileNameModelValidator;
	}

	public static final ModelValidator<ContentReference>
		getDefaultFileSizeModelValidator() {

		return _defaultFileSizeModelValidator;
	}

	private static final ModelValidator<ContentReference>
		_defaultFileExtensionModelValidator =
			new ModelValidator<ContentReference>() {

				@Override
				public void validate(ContentReference contentReference)
					throws PortalException {

					DLValidatorUtil.validateFileExtension(
						contentReference.getSourceFileName());

					DLValidatorUtil.validateSourceFileExtension(
						contentReference.getExtension(),
						contentReference.getSourceFileName());
				}

			};

	private static final ModelValidator<ContentReference>
		_defaultFileNameModelValidator =
			new ModelValidator<ContentReference>() {

				@Override
				public void validate(ContentReference contentReference)
					throws PortalException {

					if (!Validator.isNull(
							contentReference.getSourceFileName())) {

						DLValidatorUtil.validateFileName(
							contentReference.getSourceFileName());
					}
				}

			};

	private static final ModelValidator<ContentReference>
		_defaultFileSizeModelValidator =
			new ModelValidator<ContentReference>() {

				@Override
				public void validate(ContentReference contentReference)
					throws PortalException {

					DLValidatorUtil.validateFileSize(
						contentReference.getSourceFileName(),
						contentReference.getSize());
				}

			};

	private static class CompositeModelValidator<T>
		implements ModelValidator<T> {

		public CompositeModelValidator(ModelValidator<T>... modelValidators) {
			_modelValidators = modelValidators;
		}

		@Override
		public void validate(T t) throws PortalException {
			for (ModelValidator<T> modelValidator : _modelValidators) {
				modelValidator.validate(t);
			}
		}

		private final ModelValidator<T>[] _modelValidators;

	}

}