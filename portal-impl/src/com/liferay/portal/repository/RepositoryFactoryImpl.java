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

package com.liferay.portal.repository;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.UndeployedExternalRepositoryException;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.cmis.CMISRepositoryHandler;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.repository.capabilities.BaseCapabilityRepository;
import com.liferay.portal.repository.capabilities.CapabilityLocalRepository;
import com.liferay.portal.repository.capabilities.CapabilityRepository;
import com.liferay.portal.repository.capabilities.ConfigurationCapabilityImpl;
import com.liferay.portal.repository.capabilities.LiferayRepositoryEventTriggerCapability;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.proxy.BaseRepositoryProxyBean;
import com.liferay.portal.repository.registry.RepositoryClassDefinition;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalog;
import com.liferay.portal.service.RepositoryLocalService;

/**
 * @author Adolfo Pérez
 */
public class RepositoryFactoryImpl implements RepositoryFactory {

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		String className = getRepositoryClassName(repositoryId);

		RepositoryClassDefinition repositoryClassDefinition =
			getRepositoryClassDefinition(className);

		CapabilityLocalRepository capabilityLocalRepository =
			repositoryClassDefinition.createCapabilityLocalRepository(
				repositoryId);

		setupCommonCapabilities(
			capabilityLocalRepository, repositoryClassDefinition);

		return capabilityLocalRepository;
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		String className = getRepositoryClassName(repositoryId);

		RepositoryClassDefinition repositoryClassDefinition =
			getRepositoryClassDefinition(className);

		CapabilityRepository capabilityRepository =
			repositoryClassDefinition.createCapabilityRepository(repositoryId);

		setupCommonCapabilities(
			capabilityRepository, repositoryClassDefinition);

		setupCapabilityRepositoryCapabilities(capabilityRepository);

		return capabilityRepository;
	}

	protected CMISRepositoryHandler getCMISRepositoryHandler(
		Repository repository) {

		if (repository instanceof BaseRepositoryProxyBean) {
			BaseRepositoryProxyBean baseRepositoryProxyBean =
				(BaseRepositoryProxyBean)repository;

			ClassLoaderBeanHandler classLoaderBeanHandler =
				(ClassLoaderBeanHandler)ProxyUtil.getInvocationHandler(
					baseRepositoryProxyBean.getProxyBean());

			Object bean = classLoaderBeanHandler.getBean();

			if (bean instanceof CMISRepositoryHandler) {
				return (CMISRepositoryHandler)bean;
			}
		}

		return null;
	}

	protected RepositoryClassDefinition getRepositoryClassDefinition(
			String className)
		throws PortalException {

		RepositoryClassDefinition repositoryDefinition =
			_repositoryClassDefinitionCatalog.getRepositoryClassDefinition(
				className);

		if (repositoryDefinition == null) {
			throw new UndeployedExternalRepositoryException(className);
		}

		return repositoryDefinition;
	}

	protected String getRepositoryClassName(long repositoryId) {
		com.liferay.portal.model.Repository repository =
			_repositoryLocalService.fetchRepository(repositoryId);

		if (repository != null) {
			return repository.getClassName();
		}

		return LiferayRepository.class.getName();
	}

	protected void setupCapabilityRepositoryCapabilities(
		CapabilityRepository capabilityRepository) {

		Repository repository = capabilityRepository.getRepository();

		CMISRepositoryHandler cmisRepositoryHandler = getCMISRepositoryHandler(
			repository);

		if (cmisRepositoryHandler != null) {
			capabilityRepository.addExportedCapability(
				CMISRepositoryHandler.class, cmisRepositoryHandler);
		}
	}

	protected void setupCommonCapabilities(
		BaseCapabilityRepository<?> baseCapabilityRepository,
		RepositoryClassDefinition repositoryClassDefinition) {

		if (!baseCapabilityRepository.isCapabilityProvided(
				ConfigurationCapability.class)) {

			baseCapabilityRepository.addExportedCapability(
				ConfigurationCapability.class,
				new ConfigurationCapabilityImpl(baseCapabilityRepository));
		}

		if (!baseCapabilityRepository.isCapabilityProvided(
				RepositoryEventTriggerCapability.class)) {

			baseCapabilityRepository.addExportedCapability(
				RepositoryEventTriggerCapability.class,
				new LiferayRepositoryEventTriggerCapability(
					repositoryClassDefinition.getRepositoryEventTrigger()));
		}
	}

	@BeanReference(type = RepositoryClassDefinitionCatalog.class)
	private RepositoryClassDefinitionCatalog _repositoryClassDefinitionCatalog;

	@BeanReference(type = RepositoryLocalService.class)
	private RepositoryLocalService _repositoryLocalService;

}