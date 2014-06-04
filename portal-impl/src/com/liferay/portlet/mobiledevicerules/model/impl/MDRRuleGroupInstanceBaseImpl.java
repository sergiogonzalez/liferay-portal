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

package com.liferay.portlet.mobiledevicerules.model.impl;

import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;

/**
 * The extended model base implementation for the MDRRuleGroupInstance service. Represents a row in the &quot;MDRRuleGroupInstance&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MDRRuleGroupInstanceImpl}.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstanceImpl
 * @see com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance
 * @generated
 */
public abstract class MDRRuleGroupInstanceBaseImpl
	extends MDRRuleGroupInstanceModelImpl implements MDRRuleGroupInstance {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a m d r rule group instance model instance should use the {@link MDRRuleGroupInstance} interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			MDRRuleGroupInstanceLocalServiceUtil.addMDRRuleGroupInstance(this);
		}
		else {
			MDRRuleGroupInstanceLocalServiceUtil.updateMDRRuleGroupInstance(this);
		}
	}
}