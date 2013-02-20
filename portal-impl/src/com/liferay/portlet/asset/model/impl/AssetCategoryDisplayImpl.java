/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryDisplay;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class AssetCategoryDisplayImpl implements AssetCategoryDisplay {

	public AssetCategoryDisplayImpl(
		List<AssetCategory> categories, int total, int start, int end) {

		_categories = categories;
		_total = total;
		_start = start;
		_end = end;
	}

	public List<AssetCategory> getCategories() {
		return _categories;
	}

	public int getEnd() {
		return _end;
	}

	public int getPage() {
		if ((_end > 0) && (_start > 0)) {
			return _end / (_end - _start);
		}

		return 0;
	}

	public int getStart() {
		return _start;
	}

	public int getTotal() {
		return _total;
	}

	@JSON
	private List<AssetCategory> _categories;

	private int _end;
	private int _start;
	private int _total;

}