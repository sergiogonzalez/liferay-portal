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

package com.liferay.subscription.service.internal.wrapper;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularDLAppHelperLocalServiceWrapper
	extends DLAppHelperLocalServiceWrapper {

	public ModularDLAppHelperLocalServiceWrapper() {
		super(null);
	}

	public ModularDLAppHelperLocalServiceWrapper(
		DLAppHelperLocalService dlAppHelperLocalService) {

		super(dlAppHelperLocalService);
	}

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		super.deleteFileEntry(fileEntry);

		_subscriptionLocalService.deleteSubscriptions(
			fileEntry.getCompanyId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId());
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	private SubscriptionLocalService _subscriptionLocalService;

}