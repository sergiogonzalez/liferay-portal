/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.util.comparator;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorAdapter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileShortcut;

/**
 * @author Roberto Díaz
 */
public class DLFileShortcutOrderByComparator
	extends OrderByComparatorAdapter<DLFileShortcut, FileShortcut> {

	public static OrderByComparator<DLFileShortcut> getOrderByComparator(
		OrderByComparator<FileShortcut> orderByComparator) {

		if (orderByComparator == null) {
			return null;
		}

		return new DLFileShortcutOrderByComparator(orderByComparator);
	}

	@Override
	public FileShortcut adapt(DLFileShortcut dlFileShortcut) {
		return new LiferayFileShortcut(dlFileShortcut);
	}

	private DLFileShortcutOrderByComparator(
		OrderByComparator<FileShortcut> orderByComparator) {

		super(orderByComparator);
	}

}