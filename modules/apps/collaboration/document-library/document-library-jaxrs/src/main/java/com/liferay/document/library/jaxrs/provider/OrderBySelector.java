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

import java.util.Collection;
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
@FunctionalInterface
public interface OrderBySelector {

	public List<FieldOrder> select(Collection<String> availableFields);

	public final class FieldOrder {

		FieldOrder(String fieldName, boolean ascending) {
			_fieldName = fieldName;
			_ascending = ascending;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			FieldOrder that = (FieldOrder)o;

			if (_ascending != that._ascending) {
				return false;
			}

			return _fieldName.equals(that._fieldName);
		}

		public String getFieldName() {
			return _fieldName;
		}

		public int hashCode() {
			int result = _fieldName.hashCode();
			result = 31 * result + (_ascending ? 1 : 0);
			return result;
		}

		public boolean isAscending() {
			return _ascending;
		}

		private final boolean _ascending;
		private final String _fieldName;

	}

}