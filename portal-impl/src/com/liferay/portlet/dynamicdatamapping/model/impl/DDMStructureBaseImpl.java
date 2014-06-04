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

import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

/**
 * The extended model base implementation for the DDMStructure service. Represents a row in the &quot;DDMStructure&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDMStructureImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureImpl
 * @see com.liferay.portlet.dynamicdatamapping.model.DDMStructure
 * @generated
 */
public abstract class DDMStructureBaseImpl extends DDMStructureModelImpl
	implements DDMStructure {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a d d m structure model instance should use the {@link DDMStructure} interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			DDMStructureLocalServiceUtil.addDDMStructure(this);
		}
		else {
			DDMStructureLocalServiceUtil.updateDDMStructure(this);
		}
	}
}