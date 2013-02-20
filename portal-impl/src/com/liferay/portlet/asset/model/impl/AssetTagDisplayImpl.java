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
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagDisplay;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class AssetTagDisplayImpl implements AssetTagDisplay {

	public AssetTagDisplayImpl(
		List<AssetTag> tags, int total, int start, int end) {

		_tags = tags;
		_total = total;
		_start = start;
		_end = end;
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

	public List<AssetTag> getTags() {
		return _tags;
	}

	public int getTotal() {
		return _total;
	}

	private int _end;
	private int _start;

	@JSON
	private List<AssetTag> _tags;

	private int _total;

}