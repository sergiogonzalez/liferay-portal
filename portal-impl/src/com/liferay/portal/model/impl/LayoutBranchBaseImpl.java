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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.service.LayoutBranchLocalServiceUtil;

/**
 * The extended model base implementation for the LayoutBranch service. Represents a row in the &quot;LayoutBranch&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LayoutBranchImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchImpl
 * @see com.liferay.portal.model.LayoutBranch
 * @generated
 */
public abstract class LayoutBranchBaseImpl extends LayoutBranchModelImpl
	implements LayoutBranch {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout branch model instance should use the {@link LayoutBranch} interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			LayoutBranchLocalServiceUtil.addLayoutBranch(this);
		}
		else {
			LayoutBranchLocalServiceUtil.updateLayoutBranch(this);
		}
	}
}