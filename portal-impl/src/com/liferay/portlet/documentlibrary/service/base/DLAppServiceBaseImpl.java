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

package com.liferay.portlet.documentlibrary.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.persistence.RepositoryEntryPersistence;
import com.liferay.portal.service.persistence.RepositoryPersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryMetadataFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryMetadataPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;
import com.liferay.portlet.trash.service.persistence.TrashEntryPersistence;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the d l app remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.documentlibrary.service.impl.DLAppServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.documentlibrary.service.impl.DLAppServiceImpl
 * @see com.liferay.portlet.documentlibrary.service.DLAppServiceUtil
 * @generated
 */
public abstract class DLAppServiceBaseImpl extends BaseServiceImpl
	implements DLAppService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.documentlibrary.service.DLAppServiceUtil} to access the d l app remote service.
	 */

	/**
	 * Returns the d l app local service.
	 *
	 * @return the d l app local service
	 */
	public com.liferay.portlet.documentlibrary.service.DLAppLocalService getDLAppLocalService() {
		return dlAppLocalService;
	}

	/**
	 * Sets the d l app local service.
	 *
	 * @param dlAppLocalService the d l app local service
	 */
	public void setDLAppLocalService(
		com.liferay.portlet.documentlibrary.service.DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	/**
	 * Returns the d l app remote service.
	 *
	 * @return the d l app remote service
	 */
	public DLAppService getDLAppService() {
		return dlAppService;
	}

	/**
	 * Sets the d l app remote service.
	 *
	 * @param dlAppService the d l app remote service
	 */
	public void setDLAppService(DLAppService dlAppService) {
		this.dlAppService = dlAppService;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the repository local service.
	 *
	 * @return the repository local service
	 */
	public com.liferay.portal.service.RepositoryLocalService getRepositoryLocalService() {
		return repositoryLocalService;
	}

	/**
	 * Sets the repository local service.
	 *
	 * @param repositoryLocalService the repository local service
	 */
	public void setRepositoryLocalService(
		com.liferay.portal.service.RepositoryLocalService repositoryLocalService) {
		this.repositoryLocalService = repositoryLocalService;
	}

	/**
	 * Returns the repository remote service.
	 *
	 * @return the repository remote service
	 */
	public com.liferay.portal.service.RepositoryService getRepositoryService() {
		return repositoryService;
	}

	/**
	 * Sets the repository remote service.
	 *
	 * @param repositoryService the repository remote service
	 */
	public void setRepositoryService(
		com.liferay.portal.service.RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	/**
	 * Returns the repository persistence.
	 *
	 * @return the repository persistence
	 */
	public RepositoryPersistence getRepositoryPersistence() {
		return repositoryPersistence;
	}

	/**
	 * Sets the repository persistence.
	 *
	 * @param repositoryPersistence the repository persistence
	 */
	public void setRepositoryPersistence(
		RepositoryPersistence repositoryPersistence) {
		this.repositoryPersistence = repositoryPersistence;
	}

	/**
	 * Returns the repository entry local service.
	 *
	 * @return the repository entry local service
	 */
	public com.liferay.portal.service.RepositoryEntryLocalService getRepositoryEntryLocalService() {
		return repositoryEntryLocalService;
	}

	/**
	 * Sets the repository entry local service.
	 *
	 * @param repositoryEntryLocalService the repository entry local service
	 */
	public void setRepositoryEntryLocalService(
		com.liferay.portal.service.RepositoryEntryLocalService repositoryEntryLocalService) {
		this.repositoryEntryLocalService = repositoryEntryLocalService;
	}

	/**
	 * Returns the repository entry persistence.
	 *
	 * @return the repository entry persistence
	 */
	public RepositoryEntryPersistence getRepositoryEntryPersistence() {
		return repositoryEntryPersistence;
	}

	/**
	 * Sets the repository entry persistence.
	 *
	 * @param repositoryEntryPersistence the repository entry persistence
	 */
	public void setRepositoryEntryPersistence(
		RepositoryEntryPersistence repositoryEntryPersistence) {
		this.repositoryEntryPersistence = repositoryEntryPersistence;
	}

	/**
	 * Returns the subscription local service.
	 *
	 * @return the subscription local service
	 */
	public com.liferay.portal.service.SubscriptionLocalService getSubscriptionLocalService() {
		return subscriptionLocalService;
	}

	/**
	 * Sets the subscription local service.
	 *
	 * @param subscriptionLocalService the subscription local service
	 */
	public void setSubscriptionLocalService(
		com.liferay.portal.service.SubscriptionLocalService subscriptionLocalService) {
		this.subscriptionLocalService = subscriptionLocalService;
	}

	/**
	 * Returns the subscription persistence.
	 *
	 * @return the subscription persistence
	 */
	public SubscriptionPersistence getSubscriptionPersistence() {
		return subscriptionPersistence;
	}

	/**
	 * Sets the subscription persistence.
	 *
	 * @param subscriptionPersistence the subscription persistence
	 */
	public void setSubscriptionPersistence(
		SubscriptionPersistence subscriptionPersistence) {
		this.subscriptionPersistence = subscriptionPersistence;
	}

	/**
	 * Returns the trash entry local service.
	 *
	 * @return the trash entry local service
	 */
	public com.liferay.portlet.trash.service.TrashEntryLocalService getTrashEntryLocalService() {
		return trashEntryLocalService;
	}

	/**
	 * Sets the trash entry local service.
	 *
	 * @param trashEntryLocalService the trash entry local service
	 */
	public void setTrashEntryLocalService(
		com.liferay.portlet.trash.service.TrashEntryLocalService trashEntryLocalService) {
		this.trashEntryLocalService = trashEntryLocalService;
	}

	/**
	 * Returns the trash entry remote service.
	 *
	 * @return the trash entry remote service
	 */
	public com.liferay.portlet.trash.service.TrashEntryService getTrashEntryService() {
		return trashEntryService;
	}

	/**
	 * Sets the trash entry remote service.
	 *
	 * @param trashEntryService the trash entry remote service
	 */
	public void setTrashEntryService(
		com.liferay.portlet.trash.service.TrashEntryService trashEntryService) {
		this.trashEntryService = trashEntryService;
	}

	/**
	 * Returns the trash entry persistence.
	 *
	 * @return the trash entry persistence
	 */
	public TrashEntryPersistence getTrashEntryPersistence() {
		return trashEntryPersistence;
	}

	/**
	 * Sets the trash entry persistence.
	 *
	 * @param trashEntryPersistence the trash entry persistence
	 */
	public void setTrashEntryPersistence(
		TrashEntryPersistence trashEntryPersistence) {
		this.trashEntryPersistence = trashEntryPersistence;
	}

	/**
	 * Returns the d l app helper local service.
	 *
	 * @return the d l app helper local service
	 */
	public com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService getDLAppHelperLocalService() {
		return dlAppHelperLocalService;
	}

	/**
	 * Sets the d l app helper local service.
	 *
	 * @param dlAppHelperLocalService the d l app helper local service
	 */
	public void setDLAppHelperLocalService(
		com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService dlAppHelperLocalService) {
		this.dlAppHelperLocalService = dlAppHelperLocalService;
	}

	/**
	 * Returns the document library file entry metadata local service.
	 *
	 * @return the document library file entry metadata local service
	 */
	public com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService getDLFileEntryMetadataLocalService() {
		return dlFileEntryMetadataLocalService;
	}

	/**
	 * Sets the document library file entry metadata local service.
	 *
	 * @param dlFileEntryMetadataLocalService the document library file entry metadata local service
	 */
	public void setDLFileEntryMetadataLocalService(
		com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService) {
		this.dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
	}

	/**
	 * Returns the document library file entry metadata persistence.
	 *
	 * @return the document library file entry metadata persistence
	 */
	public DLFileEntryMetadataPersistence getDLFileEntryMetadataPersistence() {
		return dlFileEntryMetadataPersistence;
	}

	/**
	 * Sets the document library file entry metadata persistence.
	 *
	 * @param dlFileEntryMetadataPersistence the document library file entry metadata persistence
	 */
	public void setDLFileEntryMetadataPersistence(
		DLFileEntryMetadataPersistence dlFileEntryMetadataPersistence) {
		this.dlFileEntryMetadataPersistence = dlFileEntryMetadataPersistence;
	}

	/**
	 * Returns the document library file entry metadata finder.
	 *
	 * @return the document library file entry metadata finder
	 */
	public DLFileEntryMetadataFinder getDLFileEntryMetadataFinder() {
		return dlFileEntryMetadataFinder;
	}

	/**
	 * Sets the document library file entry metadata finder.
	 *
	 * @param dlFileEntryMetadataFinder the document library file entry metadata finder
	 */
	public void setDLFileEntryMetadataFinder(
		DLFileEntryMetadataFinder dlFileEntryMetadataFinder) {
		this.dlFileEntryMetadataFinder = dlFileEntryMetadataFinder;
	}

	/**
	 * Returns the document library file rank local service.
	 *
	 * @return the document library file rank local service
	 */
	public com.liferay.portlet.documentlibrary.service.DLFileRankLocalService getDLFileRankLocalService() {
		return dlFileRankLocalService;
	}

	/**
	 * Sets the document library file rank local service.
	 *
	 * @param dlFileRankLocalService the document library file rank local service
	 */
	public void setDLFileRankLocalService(
		com.liferay.portlet.documentlibrary.service.DLFileRankLocalService dlFileRankLocalService) {
		this.dlFileRankLocalService = dlFileRankLocalService;
	}

	/**
	 * Returns the document library file rank persistence.
	 *
	 * @return the document library file rank persistence
	 */
	public DLFileRankPersistence getDLFileRankPersistence() {
		return dlFileRankPersistence;
	}

	/**
	 * Sets the document library file rank persistence.
	 *
	 * @param dlFileRankPersistence the document library file rank persistence
	 */
	public void setDLFileRankPersistence(
		DLFileRankPersistence dlFileRankPersistence) {
		this.dlFileRankPersistence = dlFileRankPersistence;
	}

	/**
	 * Returns the document library file rank finder.
	 *
	 * @return the document library file rank finder
	 */
	public DLFileRankFinder getDLFileRankFinder() {
		return dlFileRankFinder;
	}

	/**
	 * Sets the document library file rank finder.
	 *
	 * @param dlFileRankFinder the document library file rank finder
	 */
	public void setDLFileRankFinder(DLFileRankFinder dlFileRankFinder) {
		this.dlFileRankFinder = dlFileRankFinder;
	}

	/**
	 * Returns the document library folder local service.
	 *
	 * @return the document library folder local service
	 */
	public com.liferay.portlet.documentlibrary.service.DLFolderLocalService getDLFolderLocalService() {
		return dlFolderLocalService;
	}

	/**
	 * Sets the document library folder local service.
	 *
	 * @param dlFolderLocalService the document library folder local service
	 */
	public void setDLFolderLocalService(
		com.liferay.portlet.documentlibrary.service.DLFolderLocalService dlFolderLocalService) {
		this.dlFolderLocalService = dlFolderLocalService;
	}

	/**
	 * Returns the document library folder remote service.
	 *
	 * @return the document library folder remote service
	 */
	public com.liferay.portlet.documentlibrary.service.DLFolderService getDLFolderService() {
		return dlFolderService;
	}

	/**
	 * Sets the document library folder remote service.
	 *
	 * @param dlFolderService the document library folder remote service
	 */
	public void setDLFolderService(
		com.liferay.portlet.documentlibrary.service.DLFolderService dlFolderService) {
		this.dlFolderService = dlFolderService;
	}

	/**
	 * Returns the document library folder persistence.
	 *
	 * @return the document library folder persistence
	 */
	public DLFolderPersistence getDLFolderPersistence() {
		return dlFolderPersistence;
	}

	/**
	 * Sets the document library folder persistence.
	 *
	 * @param dlFolderPersistence the document library folder persistence
	 */
	public void setDLFolderPersistence(DLFolderPersistence dlFolderPersistence) {
		this.dlFolderPersistence = dlFolderPersistence;
	}

	/**
	 * Returns the document library folder finder.
	 *
	 * @return the document library folder finder
	 */
	public DLFolderFinder getDLFolderFinder() {
		return dlFolderFinder;
	}

	/**
	 * Sets the document library folder finder.
	 *
	 * @param dlFolderFinder the document library folder finder
	 */
	public void setDLFolderFinder(DLFolderFinder dlFolderFinder) {
		this.dlFolderFinder = dlFolderFinder;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = InfrastructureUtil.getDataSource();

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLAppLocalService.class)
	protected com.liferay.portlet.documentlibrary.service.DLAppLocalService dlAppLocalService;
	@BeanReference(type = DLAppService.class)
	protected DLAppService dlAppService;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.RepositoryLocalService.class)
	protected com.liferay.portal.service.RepositoryLocalService repositoryLocalService;
	@BeanReference(type = com.liferay.portal.service.RepositoryService.class)
	protected com.liferay.portal.service.RepositoryService repositoryService;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = com.liferay.portal.service.RepositoryEntryLocalService.class)
	protected com.liferay.portal.service.RepositoryEntryLocalService repositoryEntryLocalService;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = com.liferay.portal.service.SubscriptionLocalService.class)
	protected com.liferay.portal.service.SubscriptionLocalService subscriptionLocalService;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = com.liferay.portlet.trash.service.TrashEntryLocalService.class)
	protected com.liferay.portlet.trash.service.TrashEntryLocalService trashEntryLocalService;
	@BeanReference(type = com.liferay.portlet.trash.service.TrashEntryService.class)
	protected com.liferay.portlet.trash.service.TrashEntryService trashEntryService;
	@BeanReference(type = TrashEntryPersistence.class)
	protected TrashEntryPersistence trashEntryPersistence;
	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService.class)
	protected com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService dlAppHelperLocalService;
	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService.class)
	protected com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService;
	@BeanReference(type = DLFileEntryMetadataPersistence.class)
	protected DLFileEntryMetadataPersistence dlFileEntryMetadataPersistence;
	@BeanReference(type = DLFileEntryMetadataFinder.class)
	protected DLFileEntryMetadataFinder dlFileEntryMetadataFinder;
	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLFileRankLocalService.class)
	protected com.liferay.portlet.documentlibrary.service.DLFileRankLocalService dlFileRankLocalService;
	@BeanReference(type = DLFileRankPersistence.class)
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(type = DLFileRankFinder.class)
	protected DLFileRankFinder dlFileRankFinder;
	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLFolderLocalService.class)
	protected com.liferay.portlet.documentlibrary.service.DLFolderLocalService dlFolderLocalService;
	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLFolderService.class)
	protected com.liferay.portlet.documentlibrary.service.DLFolderService dlFolderService;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = DLFolderFinder.class)
	protected DLFolderFinder dlFolderFinder;
	private String _beanIdentifier;
}