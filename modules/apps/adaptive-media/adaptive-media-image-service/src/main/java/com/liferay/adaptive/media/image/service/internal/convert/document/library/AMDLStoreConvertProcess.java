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

package com.liferay.adaptive.media.image.service.internal.convert.document.library;

import com.liferay.adaptive.media.image.internal.util.AMImageUtil;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.convert.documentlibrary.DLStoreConverter;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.util.MaintenanceUtil;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DLStoreConvertProcess.class)
public class AMDLStoreConvertProcess implements DLStoreConvertProcess {

	@Override
	public void migrate(final DLStoreConverter dlStoreConverter)
		throws PortalException {

		int count = _amImageEntryLocalService.getAMImageEntriesCount();

		MaintenanceUtil.appendStatus(
			"Migrating images in " + count + " amImageEntries");

		ActionableDynamicQuery actionableDynamicQuery =
			_amImageEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AMImageEntry>() {

				@Override
				public void performAction(AMImageEntry amImageEntry)
					throws PortalException {

					FileVersion fileVersion = _dlAppService.getFileVersion(
						amImageEntry.getFileVersionId());

					String fileVersionPath = AMImageUtil.getFileVersionPath(
						fileVersion, amImageEntry.getConfigurationUuid());

					Store sourceStore = dlStoreConverter.getSourceStore();

					InputStream is = sourceStore.getFileAsStream(
						amImageEntry.getCompanyId(), CompanyConstants.SYSTEM,
						fileVersionPath);

					Store targetStore = dlStoreConverter.getTargetStore();

					targetStore.addFile(
						amImageEntry.getCompanyId(), CompanyConstants.SYSTEM,
						fileVersionPath, is);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Reference(unbind = "-")
	private DLAppService _dlAppService;

}