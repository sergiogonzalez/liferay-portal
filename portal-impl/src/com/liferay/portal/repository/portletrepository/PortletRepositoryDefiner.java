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

package com.liferay.portal.repository.portletrepository;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;

/**
 * @author Adolfo Pérez
 */
public class PortletRepositoryDefiner extends BaseRepositoryDefiner {

	@Override
	public String getClassName() {
		return PortletRepository.class.getName();
	}

	@Override
	public boolean isExternalRepository() {
		return false;
	}

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		DocumentRepository documentRepository = capabilityRegistry.getTarget();

		capabilityRegistry.addExportedCapability(
			RelatedModelCapability.class,
			portalCapabilityLocator.getRelatedModelCapability(
				documentRepository));

		capabilityRegistry.addExportedCapability(
			TrashCapability.class,
			portalCapabilityLocator.getTrashCapability(documentRepository));

		capabilityRegistry.addExportedCapability(
			WorkflowCapability.class,
			portalCapabilityLocator.getWorkflowCapability(
				documentRepository, WorkflowCapability.OperationMode.MINIMAL));

		capabilityRegistry.addSupportedCapability(
			ProcessorCapability.class,
			portalCapabilityLocator.getProcessorCapability(documentRepository));
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	@BeanReference(type = PortalCapabilityLocator.class)
	protected PortalCapabilityLocator portalCapabilityLocator;

	private RepositoryFactory _repositoryFactory;

}