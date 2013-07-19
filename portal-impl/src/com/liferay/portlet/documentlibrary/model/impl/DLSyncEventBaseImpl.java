/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.documentlibrary.model.DLSyncEvent;
import com.liferay.portlet.documentlibrary.service.DLSyncEventLocalServiceUtil;

/**
 * The extended model base implementation for the DLSyncEvent service. Represents a row in the &quot;DLSyncEvent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DLSyncEventImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventImpl
 * @see com.liferay.portlet.documentlibrary.model.DLSyncEvent
 * @generated
 */
public abstract class DLSyncEventBaseImpl extends DLSyncEventModelImpl
	implements DLSyncEvent {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a d l sync event model instance should use the {@link DLSyncEvent} interface instead.
	 */
	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			DLSyncEventLocalServiceUtil.addDLSyncEvent(this);
		}
		else {
			DLSyncEventLocalServiceUtil.updateDLSyncEvent(this);
		}
	}
}