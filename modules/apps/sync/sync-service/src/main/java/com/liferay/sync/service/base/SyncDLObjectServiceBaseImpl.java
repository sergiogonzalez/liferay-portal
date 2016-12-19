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

package com.liferay.sync.service.base;

import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFolderPersistence;
import com.liferay.document.library.kernel.service.persistence.DLSyncEventPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.OrganizationPersistence;
import com.liferay.portal.kernel.service.persistence.RepositoryPersistence;
import com.liferay.portal.kernel.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.service.SyncDLObjectService;
import com.liferay.sync.service.persistence.SyncDLFileVersionDiffPersistence;
import com.liferay.sync.service.persistence.SyncDLObjectFinder;
import com.liferay.sync.service.persistence.SyncDLObjectPersistence;
import com.liferay.sync.service.persistence.SyncDevicePersistence;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the sync d l object remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.sync.service.impl.SyncDLObjectServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.sync.service.impl.SyncDLObjectServiceImpl
 * @see com.liferay.sync.service.SyncDLObjectServiceUtil
 * @generated
 */
public abstract class SyncDLObjectServiceBaseImpl extends BaseServiceImpl
	implements SyncDLObjectService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.sync.service.SyncDLObjectServiceUtil} to access the sync d l object remote service.
	 */

	/**
	 * Returns the sync device local service.
	 *
	 * @return the sync device local service
	 */
	public com.liferay.sync.service.SyncDeviceLocalService getSyncDeviceLocalService() {
		return syncDeviceLocalService;
	}

	/**
	 * Sets the sync device local service.
	 *
	 * @param syncDeviceLocalService the sync device local service
	 */
	public void setSyncDeviceLocalService(
		com.liferay.sync.service.SyncDeviceLocalService syncDeviceLocalService) {
		this.syncDeviceLocalService = syncDeviceLocalService;
	}

	/**
	 * Returns the sync device remote service.
	 *
	 * @return the sync device remote service
	 */
	public com.liferay.sync.service.SyncDeviceService getSyncDeviceService() {
		return syncDeviceService;
	}

	/**
	 * Sets the sync device remote service.
	 *
	 * @param syncDeviceService the sync device remote service
	 */
	public void setSyncDeviceService(
		com.liferay.sync.service.SyncDeviceService syncDeviceService) {
		this.syncDeviceService = syncDeviceService;
	}

	/**
	 * Returns the sync device persistence.
	 *
	 * @return the sync device persistence
	 */
	public SyncDevicePersistence getSyncDevicePersistence() {
		return syncDevicePersistence;
	}

	/**
	 * Sets the sync device persistence.
	 *
	 * @param syncDevicePersistence the sync device persistence
	 */
	public void setSyncDevicePersistence(
		SyncDevicePersistence syncDevicePersistence) {
		this.syncDevicePersistence = syncDevicePersistence;
	}

	/**
	 * Returns the sync d l file version diff local service.
	 *
	 * @return the sync d l file version diff local service
	 */
	public com.liferay.sync.service.SyncDLFileVersionDiffLocalService getSyncDLFileVersionDiffLocalService() {
		return syncDLFileVersionDiffLocalService;
	}

	/**
	 * Sets the sync d l file version diff local service.
	 *
	 * @param syncDLFileVersionDiffLocalService the sync d l file version diff local service
	 */
	public void setSyncDLFileVersionDiffLocalService(
		com.liferay.sync.service.SyncDLFileVersionDiffLocalService syncDLFileVersionDiffLocalService) {
		this.syncDLFileVersionDiffLocalService = syncDLFileVersionDiffLocalService;
	}

	/**
	 * Returns the sync d l file version diff persistence.
	 *
	 * @return the sync d l file version diff persistence
	 */
	public SyncDLFileVersionDiffPersistence getSyncDLFileVersionDiffPersistence() {
		return syncDLFileVersionDiffPersistence;
	}

	/**
	 * Sets the sync d l file version diff persistence.
	 *
	 * @param syncDLFileVersionDiffPersistence the sync d l file version diff persistence
	 */
	public void setSyncDLFileVersionDiffPersistence(
		SyncDLFileVersionDiffPersistence syncDLFileVersionDiffPersistence) {
		this.syncDLFileVersionDiffPersistence = syncDLFileVersionDiffPersistence;
	}

	/**
	 * Returns the sync d l object local service.
	 *
	 * @return the sync d l object local service
	 */
	public com.liferay.sync.service.SyncDLObjectLocalService getSyncDLObjectLocalService() {
		return syncDLObjectLocalService;
	}

	/**
	 * Sets the sync d l object local service.
	 *
	 * @param syncDLObjectLocalService the sync d l object local service
	 */
	public void setSyncDLObjectLocalService(
		com.liferay.sync.service.SyncDLObjectLocalService syncDLObjectLocalService) {
		this.syncDLObjectLocalService = syncDLObjectLocalService;
	}

	/**
	 * Returns the sync d l object remote service.
	 *
	 * @return the sync d l object remote service
	 */
	public SyncDLObjectService getSyncDLObjectService() {
		return syncDLObjectService;
	}

	/**
	 * Sets the sync d l object remote service.
	 *
	 * @param syncDLObjectService the sync d l object remote service
	 */
	public void setSyncDLObjectService(SyncDLObjectService syncDLObjectService) {
		this.syncDLObjectService = syncDLObjectService;
	}

	/**
	 * Returns the sync d l object persistence.
	 *
	 * @return the sync d l object persistence
	 */
	public SyncDLObjectPersistence getSyncDLObjectPersistence() {
		return syncDLObjectPersistence;
	}

	/**
	 * Sets the sync d l object persistence.
	 *
	 * @param syncDLObjectPersistence the sync d l object persistence
	 */
	public void setSyncDLObjectPersistence(
		SyncDLObjectPersistence syncDLObjectPersistence) {
		this.syncDLObjectPersistence = syncDLObjectPersistence;
	}

	/**
	 * Returns the sync d l object finder.
	 *
	 * @return the sync d l object finder
	 */
	public SyncDLObjectFinder getSyncDLObjectFinder() {
		return syncDLObjectFinder;
	}

	/**
	 * Sets the sync d l object finder.
	 *
	 * @param syncDLObjectFinder the sync d l object finder
	 */
	public void setSyncDLObjectFinder(SyncDLObjectFinder syncDLObjectFinder) {
		this.syncDLObjectFinder = syncDLObjectFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.kernel.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.kernel.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.kernel.service.GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.kernel.service.GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public com.liferay.portal.kernel.service.GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(
		com.liferay.portal.kernel.service.GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the organization local service.
	 *
	 * @return the organization local service
	 */
	public com.liferay.portal.kernel.service.OrganizationLocalService getOrganizationLocalService() {
		return organizationLocalService;
	}

	/**
	 * Sets the organization local service.
	 *
	 * @param organizationLocalService the organization local service
	 */
	public void setOrganizationLocalService(
		com.liferay.portal.kernel.service.OrganizationLocalService organizationLocalService) {
		this.organizationLocalService = organizationLocalService;
	}

	/**
	 * Returns the organization remote service.
	 *
	 * @return the organization remote service
	 */
	public com.liferay.portal.kernel.service.OrganizationService getOrganizationService() {
		return organizationService;
	}

	/**
	 * Sets the organization remote service.
	 *
	 * @param organizationService the organization remote service
	 */
	public void setOrganizationService(
		com.liferay.portal.kernel.service.OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	/**
	 * Returns the organization persistence.
	 *
	 * @return the organization persistence
	 */
	public OrganizationPersistence getOrganizationPersistence() {
		return organizationPersistence;
	}

	/**
	 * Sets the organization persistence.
	 *
	 * @param organizationPersistence the organization persistence
	 */
	public void setOrganizationPersistence(
		OrganizationPersistence organizationPersistence) {
		this.organizationPersistence = organizationPersistence;
	}

	/**
	 * Returns the repository local service.
	 *
	 * @return the repository local service
	 */
	public com.liferay.portal.kernel.service.RepositoryLocalService getRepositoryLocalService() {
		return repositoryLocalService;
	}

	/**
	 * Sets the repository local service.
	 *
	 * @param repositoryLocalService the repository local service
	 */
	public void setRepositoryLocalService(
		com.liferay.portal.kernel.service.RepositoryLocalService repositoryLocalService) {
		this.repositoryLocalService = repositoryLocalService;
	}

	/**
	 * Returns the repository remote service.
	 *
	 * @return the repository remote service
	 */
	public com.liferay.portal.kernel.service.RepositoryService getRepositoryService() {
		return repositoryService;
	}

	/**
	 * Sets the repository remote service.
	 *
	 * @param repositoryService the repository remote service
	 */
	public void setRepositoryService(
		com.liferay.portal.kernel.service.RepositoryService repositoryService) {
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
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the resource permission local service.
	 *
	 * @return the resource permission local service
	 */
	public com.liferay.portal.kernel.service.ResourcePermissionLocalService getResourcePermissionLocalService() {
		return resourcePermissionLocalService;
	}

	/**
	 * Sets the resource permission local service.
	 *
	 * @param resourcePermissionLocalService the resource permission local service
	 */
	public void setResourcePermissionLocalService(
		com.liferay.portal.kernel.service.ResourcePermissionLocalService resourcePermissionLocalService) {
		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	/**
	 * Returns the resource permission remote service.
	 *
	 * @return the resource permission remote service
	 */
	public com.liferay.portal.kernel.service.ResourcePermissionService getResourcePermissionService() {
		return resourcePermissionService;
	}

	/**
	 * Sets the resource permission remote service.
	 *
	 * @param resourcePermissionService the resource permission remote service
	 */
	public void setResourcePermissionService(
		com.liferay.portal.kernel.service.ResourcePermissionService resourcePermissionService) {
		this.resourcePermissionService = resourcePermissionService;
	}

	/**
	 * Returns the resource permission persistence.
	 *
	 * @return the resource permission persistence
	 */
	public ResourcePermissionPersistence getResourcePermissionPersistence() {
		return resourcePermissionPersistence;
	}

	/**
	 * Sets the resource permission persistence.
	 *
	 * @param resourcePermissionPersistence the resource permission persistence
	 */
	public void setResourcePermissionPersistence(
		ResourcePermissionPersistence resourcePermissionPersistence) {
		this.resourcePermissionPersistence = resourcePermissionPersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.kernel.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.kernel.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the d l app local service.
	 *
	 * @return the d l app local service
	 */
	public com.liferay.document.library.kernel.service.DLAppLocalService getDLAppLocalService() {
		return dlAppLocalService;
	}

	/**
	 * Sets the d l app local service.
	 *
	 * @param dlAppLocalService the d l app local service
	 */
	public void setDLAppLocalService(
		com.liferay.document.library.kernel.service.DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	/**
	 * Returns the d l app remote service.
	 *
	 * @return the d l app remote service
	 */
	public com.liferay.document.library.kernel.service.DLAppService getDLAppService() {
		return dlAppService;
	}

	/**
	 * Sets the d l app remote service.
	 *
	 * @param dlAppService the d l app remote service
	 */
	public void setDLAppService(
		com.liferay.document.library.kernel.service.DLAppService dlAppService) {
		this.dlAppService = dlAppService;
	}

	/**
	 * Returns the document library file entry local service.
	 *
	 * @return the document library file entry local service
	 */
	public com.liferay.document.library.kernel.service.DLFileEntryLocalService getDLFileEntryLocalService() {
		return dlFileEntryLocalService;
	}

	/**
	 * Sets the document library file entry local service.
	 *
	 * @param dlFileEntryLocalService the document library file entry local service
	 */
	public void setDLFileEntryLocalService(
		com.liferay.document.library.kernel.service.DLFileEntryLocalService dlFileEntryLocalService) {
		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	/**
	 * Returns the document library file entry remote service.
	 *
	 * @return the document library file entry remote service
	 */
	public com.liferay.document.library.kernel.service.DLFileEntryService getDLFileEntryService() {
		return dlFileEntryService;
	}

	/**
	 * Sets the document library file entry remote service.
	 *
	 * @param dlFileEntryService the document library file entry remote service
	 */
	public void setDLFileEntryService(
		com.liferay.document.library.kernel.service.DLFileEntryService dlFileEntryService) {
		this.dlFileEntryService = dlFileEntryService;
	}

	/**
	 * Returns the document library file entry persistence.
	 *
	 * @return the document library file entry persistence
	 */
	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	/**
	 * Sets the document library file entry persistence.
	 *
	 * @param dlFileEntryPersistence the document library file entry persistence
	 */
	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {
		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	/**
	 * Returns the document library file version local service.
	 *
	 * @return the document library file version local service
	 */
	public com.liferay.document.library.kernel.service.DLFileVersionLocalService getDLFileVersionLocalService() {
		return dlFileVersionLocalService;
	}

	/**
	 * Sets the document library file version local service.
	 *
	 * @param dlFileVersionLocalService the document library file version local service
	 */
	public void setDLFileVersionLocalService(
		com.liferay.document.library.kernel.service.DLFileVersionLocalService dlFileVersionLocalService) {
		this.dlFileVersionLocalService = dlFileVersionLocalService;
	}

	/**
	 * Returns the document library file version remote service.
	 *
	 * @return the document library file version remote service
	 */
	public com.liferay.document.library.kernel.service.DLFileVersionService getDLFileVersionService() {
		return dlFileVersionService;
	}

	/**
	 * Sets the document library file version remote service.
	 *
	 * @param dlFileVersionService the document library file version remote service
	 */
	public void setDLFileVersionService(
		com.liferay.document.library.kernel.service.DLFileVersionService dlFileVersionService) {
		this.dlFileVersionService = dlFileVersionService;
	}

	/**
	 * Returns the document library file version persistence.
	 *
	 * @return the document library file version persistence
	 */
	public DLFileVersionPersistence getDLFileVersionPersistence() {
		return dlFileVersionPersistence;
	}

	/**
	 * Sets the document library file version persistence.
	 *
	 * @param dlFileVersionPersistence the document library file version persistence
	 */
	public void setDLFileVersionPersistence(
		DLFileVersionPersistence dlFileVersionPersistence) {
		this.dlFileVersionPersistence = dlFileVersionPersistence;
	}

	/**
	 * Returns the document library folder local service.
	 *
	 * @return the document library folder local service
	 */
	public com.liferay.document.library.kernel.service.DLFolderLocalService getDLFolderLocalService() {
		return dlFolderLocalService;
	}

	/**
	 * Sets the document library folder local service.
	 *
	 * @param dlFolderLocalService the document library folder local service
	 */
	public void setDLFolderLocalService(
		com.liferay.document.library.kernel.service.DLFolderLocalService dlFolderLocalService) {
		this.dlFolderLocalService = dlFolderLocalService;
	}

	/**
	 * Returns the document library folder remote service.
	 *
	 * @return the document library folder remote service
	 */
	public com.liferay.document.library.kernel.service.DLFolderService getDLFolderService() {
		return dlFolderService;
	}

	/**
	 * Sets the document library folder remote service.
	 *
	 * @param dlFolderService the document library folder remote service
	 */
	public void setDLFolderService(
		com.liferay.document.library.kernel.service.DLFolderService dlFolderService) {
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
	 * Returns the d l sync event local service.
	 *
	 * @return the d l sync event local service
	 */
	public com.liferay.document.library.kernel.service.DLSyncEventLocalService getDLSyncEventLocalService() {
		return dlSyncEventLocalService;
	}

	/**
	 * Sets the d l sync event local service.
	 *
	 * @param dlSyncEventLocalService the d l sync event local service
	 */
	public void setDLSyncEventLocalService(
		com.liferay.document.library.kernel.service.DLSyncEventLocalService dlSyncEventLocalService) {
		this.dlSyncEventLocalService = dlSyncEventLocalService;
	}

	/**
	 * Returns the d l sync event persistence.
	 *
	 * @return the d l sync event persistence
	 */
	public DLSyncEventPersistence getDLSyncEventPersistence() {
		return dlSyncEventPersistence;
	}

	/**
	 * Sets the d l sync event persistence.
	 *
	 * @param dlSyncEventPersistence the d l sync event persistence
	 */
	public void setDLSyncEventPersistence(
		DLSyncEventPersistence dlSyncEventPersistence) {
		this.dlSyncEventPersistence = dlSyncEventPersistence;
	}

	/**
	 * Returns the d l trash local service.
	 *
	 * @return the d l trash local service
	 */
	public com.liferay.document.library.kernel.service.DLTrashLocalService getDLTrashLocalService() {
		return dlTrashLocalService;
	}

	/**
	 * Sets the d l trash local service.
	 *
	 * @param dlTrashLocalService the d l trash local service
	 */
	public void setDLTrashLocalService(
		com.liferay.document.library.kernel.service.DLTrashLocalService dlTrashLocalService) {
		this.dlTrashLocalService = dlTrashLocalService;
	}

	/**
	 * Returns the d l trash remote service.
	 *
	 * @return the d l trash remote service
	 */
	public com.liferay.document.library.kernel.service.DLTrashService getDLTrashService() {
		return dlTrashService;
	}

	/**
	 * Sets the d l trash remote service.
	 *
	 * @param dlTrashService the d l trash remote service
	 */
	public void setDLTrashService(
		com.liferay.document.library.kernel.service.DLTrashService dlTrashService) {
		this.dlTrashService = dlTrashService;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SyncDLObjectService.class.getName();
	}

	protected Class<?> getModelClass() {
		return SyncDLObject.class;
	}

	protected String getModelClassName() {
		return SyncDLObject.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = syncDLObjectPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.sync.service.SyncDeviceLocalService.class)
	protected com.liferay.sync.service.SyncDeviceLocalService syncDeviceLocalService;
	@BeanReference(type = com.liferay.sync.service.SyncDeviceService.class)
	protected com.liferay.sync.service.SyncDeviceService syncDeviceService;
	@BeanReference(type = SyncDevicePersistence.class)
	protected SyncDevicePersistence syncDevicePersistence;
	@BeanReference(type = com.liferay.sync.service.SyncDLFileVersionDiffLocalService.class)
	protected com.liferay.sync.service.SyncDLFileVersionDiffLocalService syncDLFileVersionDiffLocalService;
	@BeanReference(type = SyncDLFileVersionDiffPersistence.class)
	protected SyncDLFileVersionDiffPersistence syncDLFileVersionDiffPersistence;
	@BeanReference(type = com.liferay.sync.service.SyncDLObjectLocalService.class)
	protected com.liferay.sync.service.SyncDLObjectLocalService syncDLObjectLocalService;
	@BeanReference(type = SyncDLObjectService.class)
	protected SyncDLObjectService syncDLObjectService;
	@BeanReference(type = SyncDLObjectPersistence.class)
	protected SyncDLObjectPersistence syncDLObjectPersistence;
	@BeanReference(type = SyncDLObjectFinder.class)
	protected SyncDLObjectFinder syncDLObjectFinder;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameService.class)
	protected com.liferay.portal.kernel.service.ClassNameService classNameService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.GroupLocalService.class)
	protected com.liferay.portal.kernel.service.GroupLocalService groupLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.GroupService.class)
	protected com.liferay.portal.kernel.service.GroupService groupService;
	@ServiceReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.OrganizationLocalService.class)
	protected com.liferay.portal.kernel.service.OrganizationLocalService organizationLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.OrganizationService.class)
	protected com.liferay.portal.kernel.service.OrganizationService organizationService;
	@ServiceReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.RepositoryLocalService.class)
	protected com.liferay.portal.kernel.service.RepositoryLocalService repositoryLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.RepositoryService.class)
	protected com.liferay.portal.kernel.service.RepositoryService repositoryService;
	@ServiceReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourcePermissionLocalService.class)
	protected com.liferay.portal.kernel.service.ResourcePermissionLocalService resourcePermissionLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourcePermissionService.class)
	protected com.liferay.portal.kernel.service.ResourcePermissionService resourcePermissionService;
	@ServiceReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserService.class)
	protected com.liferay.portal.kernel.service.UserService userService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLAppLocalService.class)
	protected com.liferay.document.library.kernel.service.DLAppLocalService dlAppLocalService;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLAppService.class)
	protected com.liferay.document.library.kernel.service.DLAppService dlAppService;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLFileEntryLocalService.class)
	protected com.liferay.document.library.kernel.service.DLFileEntryLocalService dlFileEntryLocalService;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLFileEntryService.class)
	protected com.liferay.document.library.kernel.service.DLFileEntryService dlFileEntryService;
	@ServiceReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLFileVersionLocalService.class)
	protected com.liferay.document.library.kernel.service.DLFileVersionLocalService dlFileVersionLocalService;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLFileVersionService.class)
	protected com.liferay.document.library.kernel.service.DLFileVersionService dlFileVersionService;
	@ServiceReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLFolderLocalService.class)
	protected com.liferay.document.library.kernel.service.DLFolderLocalService dlFolderLocalService;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLFolderService.class)
	protected com.liferay.document.library.kernel.service.DLFolderService dlFolderService;
	@ServiceReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLSyncEventLocalService.class)
	protected com.liferay.document.library.kernel.service.DLSyncEventLocalService dlSyncEventLocalService;
	@ServiceReference(type = DLSyncEventPersistence.class)
	protected DLSyncEventPersistence dlSyncEventPersistence;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLTrashLocalService.class)
	protected com.liferay.document.library.kernel.service.DLTrashLocalService dlTrashLocalService;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLTrashService.class)
	protected com.liferay.document.library.kernel.service.DLTrashService dlTrashService;
}