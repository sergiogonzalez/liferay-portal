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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;

import java.util.concurrent.Callable;

/**
 * @author Adolfo Pérez
 */
public class LiferayProcessorCapability implements ProcessorCapability {

	@Override
	public void cleanUp(FileEntry fileEntry) throws PortalException {
		DLProcessorRegistryUtil.cleanUp(fileEntry);
	}

	@Override
	public void cleanUp(FileVersion fileVersion) throws PortalException {
		DLProcessorRegistryUtil.cleanUp(fileVersion);
	}

	@Override
	public void copyPrevious(FileVersion fileVersion) throws PortalException {
		registerDLProcessorCallback(fileVersion.getFileEntry(), fileVersion);
	}

	@Override
	public void generateNew(FileEntry fileEntry) throws PortalException {
		registerDLProcessorCallback(fileEntry, null);
	}

	protected void registerDLProcessorCallback(
		final FileEntry fileEntry, final FileVersion fileVersion) {

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					DLProcessorRegistryUtil.trigger(
						fileEntry, fileVersion, true);

					return null;
				}

			});
	}

}