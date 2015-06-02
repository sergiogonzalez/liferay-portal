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

package com.liferay.service.access.control.profile.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class SACPEntryImpl extends SACPEntryBaseImpl {

	@Override
	public List<String> getAllowedServiceSignaturesList() {
		String[] allowedServiceSignatures = StringUtil.split(
			getAllowedServiceSignatures(), StringPool.NEW_LINE);

		return ListUtil.toList(allowedServiceSignatures);
	}

}