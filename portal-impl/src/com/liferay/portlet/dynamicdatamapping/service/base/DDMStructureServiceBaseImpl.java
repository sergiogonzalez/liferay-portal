/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypeFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypePersistence;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLinkLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureService;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateService;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMContentPersistence;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStorageLinkPersistence;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureFinder;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureLinkPersistence;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructurePersistence;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateFinder;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplatePersistence;

import javax.sql.DataSource;

/**
 * The base implementation of the d d m structure remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureServiceImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil
 * @generated
 */
public abstract class DDMStructureServiceBaseImpl extends BaseServiceImpl
	implements DDMStructureService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil} to access the d d m structure remote service.
	 */

	/**
	 * Returns the d d m content local service.
	 *
	 * @return the d d m content local service
	 */
	public DDMContentLocalService getDDMContentLocalService() {
		return ddmContentLocalService;
	}

	/**
	 * Sets the d d m content local service.
	 *
	 * @param ddmContentLocalService the d d m content local service
	 */
	public void setDDMContentLocalService(
		DDMContentLocalService ddmContentLocalService) {
		this.ddmContentLocalService = ddmContentLocalService;
	}

	/**
	 * Returns the d d m content persistence.
	 *
	 * @return the d d m content persistence
	 */
	public DDMContentPersistence getDDMContentPersistence() {
		return ddmContentPersistence;
	}

	/**
	 * Sets the d d m content persistence.
	 *
	 * @param ddmContentPersistence the d d m content persistence
	 */
	public void setDDMContentPersistence(
		DDMContentPersistence ddmContentPersistence) {
		this.ddmContentPersistence = ddmContentPersistence;
	}

	/**
	 * Returns the d d m storage link local service.
	 *
	 * @return the d d m storage link local service
	 */
	public DDMStorageLinkLocalService getDDMStorageLinkLocalService() {
		return ddmStorageLinkLocalService;
	}

	/**
	 * Sets the d d m storage link local service.
	 *
	 * @param ddmStorageLinkLocalService the d d m storage link local service
	 */
	public void setDDMStorageLinkLocalService(
		DDMStorageLinkLocalService ddmStorageLinkLocalService) {
		this.ddmStorageLinkLocalService = ddmStorageLinkLocalService;
	}

	/**
	 * Returns the d d m storage link persistence.
	 *
	 * @return the d d m storage link persistence
	 */
	public DDMStorageLinkPersistence getDDMStorageLinkPersistence() {
		return ddmStorageLinkPersistence;
	}

	/**
	 * Sets the d d m storage link persistence.
	 *
	 * @param ddmStorageLinkPersistence the d d m storage link persistence
	 */
	public void setDDMStorageLinkPersistence(
		DDMStorageLinkPersistence ddmStorageLinkPersistence) {
		this.ddmStorageLinkPersistence = ddmStorageLinkPersistence;
	}

	/**
	 * Returns the d d m structure local service.
	 *
	 * @return the d d m structure local service
	 */
	public DDMStructureLocalService getDDMStructureLocalService() {
		return ddmStructureLocalService;
	}

	/**
	 * Sets the d d m structure local service.
	 *
	 * @param ddmStructureLocalService the d d m structure local service
	 */
	public void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {
		this.ddmStructureLocalService = ddmStructureLocalService;
	}

	/**
	 * Returns the d d m structure remote service.
	 *
	 * @return the d d m structure remote service
	 */
	public DDMStructureService getDDMStructureService() {
		return ddmStructureService;
	}

	/**
	 * Sets the d d m structure remote service.
	 *
	 * @param ddmStructureService the d d m structure remote service
	 */
	public void setDDMStructureService(DDMStructureService ddmStructureService) {
		this.ddmStructureService = ddmStructureService;
	}

	/**
	 * Returns the d d m structure persistence.
	 *
	 * @return the d d m structure persistence
	 */
	public DDMStructurePersistence getDDMStructurePersistence() {
		return ddmStructurePersistence;
	}

	/**
	 * Sets the d d m structure persistence.
	 *
	 * @param ddmStructurePersistence the d d m structure persistence
	 */
	public void setDDMStructurePersistence(
		DDMStructurePersistence ddmStructurePersistence) {
		this.ddmStructurePersistence = ddmStructurePersistence;
	}

	/**
	 * Returns the d d m structure finder.
	 *
	 * @return the d d m structure finder
	 */
	public DDMStructureFinder getDDMStructureFinder() {
		return ddmStructureFinder;
	}

	/**
	 * Sets the d d m structure finder.
	 *
	 * @param ddmStructureFinder the d d m structure finder
	 */
	public void setDDMStructureFinder(DDMStructureFinder ddmStructureFinder) {
		this.ddmStructureFinder = ddmStructureFinder;
	}

	/**
	 * Returns the d d m structure link local service.
	 *
	 * @return the d d m structure link local service
	 */
	public DDMStructureLinkLocalService getDDMStructureLinkLocalService() {
		return ddmStructureLinkLocalService;
	}

	/**
	 * Sets the d d m structure link local service.
	 *
	 * @param ddmStructureLinkLocalService the d d m structure link local service
	 */
	public void setDDMStructureLinkLocalService(
		DDMStructureLinkLocalService ddmStructureLinkLocalService) {
		this.ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}

	/**
	 * Returns the d d m structure link persistence.
	 *
	 * @return the d d m structure link persistence
	 */
	public DDMStructureLinkPersistence getDDMStructureLinkPersistence() {
		return ddmStructureLinkPersistence;
	}

	/**
	 * Sets the d d m structure link persistence.
	 *
	 * @param ddmStructureLinkPersistence the d d m structure link persistence
	 */
	public void setDDMStructureLinkPersistence(
		DDMStructureLinkPersistence ddmStructureLinkPersistence) {
		this.ddmStructureLinkPersistence = ddmStructureLinkPersistence;
	}

	/**
	 * Returns the d d m template local service.
	 *
	 * @return the d d m template local service
	 */
	public DDMTemplateLocalService getDDMTemplateLocalService() {
		return ddmTemplateLocalService;
	}

	/**
	 * Sets the d d m template local service.
	 *
	 * @param ddmTemplateLocalService the d d m template local service
	 */
	public void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {
		this.ddmTemplateLocalService = ddmTemplateLocalService;
	}

	/**
	 * Returns the d d m template remote service.
	 *
	 * @return the d d m template remote service
	 */
	public DDMTemplateService getDDMTemplateService() {
		return ddmTemplateService;
	}

	/**
	 * Sets the d d m template remote service.
	 *
	 * @param ddmTemplateService the d d m template remote service
	 */
	public void setDDMTemplateService(DDMTemplateService ddmTemplateService) {
		this.ddmTemplateService = ddmTemplateService;
	}

	/**
	 * Returns the d d m template persistence.
	 *
	 * @return the d d m template persistence
	 */
	public DDMTemplatePersistence getDDMTemplatePersistence() {
		return ddmTemplatePersistence;
	}

	/**
	 * Sets the d d m template persistence.
	 *
	 * @param ddmTemplatePersistence the d d m template persistence
	 */
	public void setDDMTemplatePersistence(
		DDMTemplatePersistence ddmTemplatePersistence) {
		this.ddmTemplatePersistence = ddmTemplatePersistence;
	}

	/**
	 * Returns the d d m template finder.
	 *
	 * @return the d d m template finder
	 */
	public DDMTemplateFinder getDDMTemplateFinder() {
		return ddmTemplateFinder;
	}

	/**
	 * Sets the d d m template finder.
	 *
	 * @param ddmTemplateFinder the d d m template finder
	 */
	public void setDDMTemplateFinder(DDMTemplateFinder ddmTemplateFinder) {
		this.ddmTemplateFinder = ddmTemplateFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(GroupService groupService) {
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
	 * Returns the group finder.
	 *
	 * @return the group finder
	 */
	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	/**
	 * Sets the group finder.
	 *
	 * @param groupFinder the group finder
	 */
	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
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
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Returns the document library file entry type local service.
	 *
	 * @return the document library file entry type local service
	 */
	public DLFileEntryTypeLocalService getDLFileEntryTypeLocalService() {
		return dlFileEntryTypeLocalService;
	}

	/**
	 * Sets the document library file entry type local service.
	 *
	 * @param dlFileEntryTypeLocalService the document library file entry type local service
	 */
	public void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {
		this.dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	/**
	 * Returns the document library file entry type remote service.
	 *
	 * @return the document library file entry type remote service
	 */
	public DLFileEntryTypeService getDLFileEntryTypeService() {
		return dlFileEntryTypeService;
	}

	/**
	 * Sets the document library file entry type remote service.
	 *
	 * @param dlFileEntryTypeService the document library file entry type remote service
	 */
	public void setDLFileEntryTypeService(
		DLFileEntryTypeService dlFileEntryTypeService) {
		this.dlFileEntryTypeService = dlFileEntryTypeService;
	}

	/**
	 * Returns the document library file entry type persistence.
	 *
	 * @return the document library file entry type persistence
	 */
	public DLFileEntryTypePersistence getDLFileEntryTypePersistence() {
		return dlFileEntryTypePersistence;
	}

	/**
	 * Sets the document library file entry type persistence.
	 *
	 * @param dlFileEntryTypePersistence the document library file entry type persistence
	 */
	public void setDLFileEntryTypePersistence(
		DLFileEntryTypePersistence dlFileEntryTypePersistence) {
		this.dlFileEntryTypePersistence = dlFileEntryTypePersistence;
	}

	/**
	 * Returns the document library file entry type finder.
	 *
	 * @return the document library file entry type finder
	 */
	public DLFileEntryTypeFinder getDLFileEntryTypeFinder() {
		return dlFileEntryTypeFinder;
	}

	/**
	 * Sets the document library file entry type finder.
	 *
	 * @param dlFileEntryTypeFinder the document library file entry type finder
	 */
	public void setDLFileEntryTypeFinder(
		DLFileEntryTypeFinder dlFileEntryTypeFinder) {
		this.dlFileEntryTypeFinder = dlFileEntryTypeFinder;
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
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return DDMStructure.class;
	}

	protected String getModelClassName() {
		return DDMStructure.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = ddmStructurePersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = DDMContentLocalService.class)
	protected DDMContentLocalService ddmContentLocalService;
	@BeanReference(type = DDMContentPersistence.class)
	protected DDMContentPersistence ddmContentPersistence;
	@BeanReference(type = DDMStorageLinkLocalService.class)
	protected DDMStorageLinkLocalService ddmStorageLinkLocalService;
	@BeanReference(type = DDMStorageLinkPersistence.class)
	protected DDMStorageLinkPersistence ddmStorageLinkPersistence;
	@BeanReference(type = DDMStructureLocalService.class)
	protected DDMStructureLocalService ddmStructureLocalService;
	@BeanReference(type = DDMStructureService.class)
	protected DDMStructureService ddmStructureService;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	@BeanReference(type = DDMStructureFinder.class)
	protected DDMStructureFinder ddmStructureFinder;
	@BeanReference(type = DDMStructureLinkLocalService.class)
	protected DDMStructureLinkLocalService ddmStructureLinkLocalService;
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	@BeanReference(type = DDMTemplateLocalService.class)
	protected DDMTemplateLocalService ddmTemplateLocalService;
	@BeanReference(type = DDMTemplateService.class)
	protected DDMTemplateService ddmTemplateService;
	@BeanReference(type = DDMTemplatePersistence.class)
	protected DDMTemplatePersistence ddmTemplatePersistence;
	@BeanReference(type = DDMTemplateFinder.class)
	protected DDMTemplateFinder ddmTemplateFinder;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = GroupLocalService.class)
	protected GroupLocalService groupLocalService;
	@BeanReference(type = GroupService.class)
	protected GroupService groupService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = DLFileEntryTypeLocalService.class)
	protected DLFileEntryTypeLocalService dlFileEntryTypeLocalService;
	@BeanReference(type = DLFileEntryTypeService.class)
	protected DLFileEntryTypeService dlFileEntryTypeService;
	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	@BeanReference(type = DLFileEntryTypeFinder.class)
	protected DLFileEntryTypeFinder dlFileEntryTypeFinder;
	private String _beanIdentifier;
}