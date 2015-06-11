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

package com.liferay.portlet.documentlibrary.web.messaging;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.model.Repository;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.web.configuration.DLWebConfiguration;
import com.liferay.portlet.documentlibrary.web.constants.DLWebKeys;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.portlet.documentlibrary.web.configuration.DLWebConfiguration",
	property = {"javax.portlet.name=" + DLWebKeys.DOCUMENT_LIBRARY_ADMIN},
	service = SchedulerEntry.class
)
public class TempFileEntriesMessageListener
	extends BaseSchedulerEntryMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		DLWebConfiguration dlWebConfiguration = Configurable.createConfigurable(
			DLWebConfiguration.class, properties);

		schedulerEntry.setTimeUnit(TimeUnit.HOUR);
		schedulerEntry.setTriggerType(TriggerType.SIMPLE);
		schedulerEntry.setTriggerValue(
			dlWebConfiguration.tempFileEntriesCheckInterval());
	}

	protected void deleteExpiredTemporaryFileEntries(Repository repository) {
		LocalRepository localRepository = null;

		try {
			localRepository = RepositoryProviderUtil.getLocalRepository(
				repository.getRepositoryId());
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get implementation for repository " +
						repository.getRepositoryId(),
					pe);
			}

			return;
		}

		try {
			if (localRepository.isCapabilityProvided(
					TemporaryFileEntriesCapability.class)) {

				TemporaryFileEntriesCapability temporaryFileEntriesCapability =
					localRepository.getCapability(
						TemporaryFileEntriesCapability.class);

				temporaryFileEntriesCapability.
					deleteExpiredTemporaryFileEntries();
			}
		}
		catch (Exception pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to delete expired temporary file entries in " +
						"repository " + repository.getRepositoryId(),
					pe);
			}
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			RepositoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					Repository repository = (Repository)object;

					deleteExpiredTemporaryFileEntries(repository);
				}

			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TempFileEntriesMessageListener.class);

}