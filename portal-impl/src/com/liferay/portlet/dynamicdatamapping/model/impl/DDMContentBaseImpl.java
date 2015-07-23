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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalServiceUtil;

/**
 * The extended model base implementation for the DDMContent service. Represents a row in the &quot;DDMContent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDMContentImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMContentImpl
 * @see com.liferay.portlet.dynamicdatamapping.model.DDMContent
 * @generated
 */
@ProviderType
public abstract class DDMContentBaseImpl extends DDMContentModelImpl
	implements DDMContent {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a d d m content model instance should use the {@link DDMContent} interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			DDMContentLocalServiceUtil.addDDMContent(this);
		}
		else {
			DDMContentLocalServiceUtil.updateDDMContent(this);
		}
	}
}