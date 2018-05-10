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

package com.liferay.document.library.internal.upgrade.v1_0_0;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Samuel Ziemer
 */
@Component(service = UpgradeDocumentLibrary.class)
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (_store == null) {
			throw new UpgradeException(
				StringBundler.concat(
					"store.type=", PropsValues.DL_STORE_IMPL,
					" is not yet available. Please re-run the upgrade from ",
					"Gogo shell at a later time."));
		}

		_deleteChecksumDirectory();

		_deleteTempDirectory();
	}

	private void _deleteChecksumDirectory() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select distinct companyId from DLFileEntry");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");

				_store.deleteDirectory(companyId, 0, "checksum");
			}
		}
	}

	private void _deleteTempDirectory() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_store.deleteDirectory(0, 0, "liferay_temp/");
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(store.type=*)",
		unbind = "_unsetStore"
	)
	private void _setStore(Store store, Map<String, Object> serviceProperties)
		throws Exception {

		String storeType = (String)serviceProperties.get("store.type");

		if (!storeType.equals(PropsValues.DL_STORE_IMPL)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"store.type=", storeType, " ignored because it is not ",
						"store.type=", PropsValues.DL_STORE_IMPL));
			}

			return;
		}

		_store = store;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"store.type=" + PropsValues.DL_STORE_IMPL + " is available");
		}
	}

	private void _unsetStore(Map<String, Object> serviceProperties) {
		String storeType = (String)serviceProperties.get("store.type");

		if (storeType.equals(PropsValues.DL_STORE_IMPL)) {
			_store = null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibrary.class);

	private Store _store;

}