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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.messaging.LayoutsLocalPublisherRequest;
import com.liferay.portal.messaging.LayoutsRemotePublisherRequest;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the layout service.
 *
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class LayoutServiceImpl extends LayoutServiceBaseImpl {

	/**
	 * Adds a layout with additional parameters.
	 *
	 * <p>
	 * This method handles the creation of the layout including its resources,
	 * metadata, and internal data structures. It is not necessary to make
	 * subsequent calls to any methods to setup default groups, resources, ...
	 * etc.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parentLayoutId the primary key of the parent layout (optionally
	 *         {@link
	 *         com.liferay.portal.model.LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param  localeNamesMap the layout's locales and localized names
	 * @param  localeTitlesMap the layout's locales and localized titles
	 * @param  descriptionMap the layout's locales and localized descriptions
	 * @param  keywordsMap the layout's locales and localized keywords
	 * @param  robotsMap the layout's locales and localized robots
	 * @param  type the layout's type (optionally {@link
	 *         com.liferay.portal.model.LayoutConstants#TYPE_PORTLET}). The
	 *         possible types can be found in {@link
	 *         com.liferay.portal.model.LayoutConstants}.
	 * @param  hidden whether the layout is hidden
	 * @param  friendlyURL the layout's friendly URL (optionally {@link
	 *         com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL}
	 *         or {@link
	 *         com.liferay.portal.util.PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL}).
	 *         The default values can be overridden in
	 *         <code>portal-ext.properties</code> by specifying new values for
	 *         the corresponding properties defined in {@link
	 *         com.liferay.portal.util.PropsValues}. To see how the URL is
	 *         normalized when accessed see {@link
	 *         com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 *         String)}.
	 * @param  serviceContext the service context. Must set the universally
	 *         unique identifier (UUID) for the layout. Can set the creation
	 *         date, modification date and the expando bridge attributes for the
	 *         layout. For layouts that belong to a layout set prototype, an
	 *         attribute named 'layoutUpdateable' can be used to specify whether
	 *         site administrators can modify this page within their site.
	 * @return the layout
	 * @throws PortalException if a group with the primary key could not be
	 *         found, if the group did not have permission to manage the layouts
	 *         involved, or if layout values were invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> keywordsMap,
			Map<Locale, String> robotsMap, String type, boolean hidden,
			String friendlyURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			GroupPermissionUtil.check(
				permissionChecker, groupId, ActionKeys.ADD_LAYOUT);
		}
		else {
			LayoutPermissionUtil.check(
				permissionChecker, groupId, privateLayout, parentLayoutId,
				ActionKeys.ADD_LAYOUT);
		}

		return layoutLocalService.addLayout(
			getUserId(), groupId, privateLayout, parentLayoutId, localeNamesMap,
			localeTitlesMap, descriptionMap, keywordsMap, robotsMap, type,
			hidden, friendlyURL, serviceContext);
	}

	/**
	 * Adds a layout with empty maps for descriptions, keywords, and titles ,
	 * and a names map containing a mapping for the default locale as its only
	 * entry.
	 *
	 * <p>
	 * This method handles the creation of the layout including its resources,
	 * metadata, and internal data structures. It is not necessary to make
	 * subsequent calls to any methods to setup default groups, resources, ...
	 * etc.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parentLayoutId the primary key of the parent layout (optionally
	 *         {@link
	 *         com.liferay.portal.model.LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param  name Map the layout's locales and localized names
	 * @param  title Map the layout's locales and localized titles
	 * @param  description Map the layout's locales and localized descriptions
	 * @param  type the layout's type (optionally {@link
	 *         com.liferay.portal.model.LayoutConstants#TYPE_PORTLET}). The
	 *         possible types can be found in {@link
	 *         com.liferay.portal.model.LayoutConstants}.
	 * @param  hidden whether the layout is hidden
	 * @param  friendlyURL the layout's friendly URL (optionally {@link
	 *         com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL}
	 *         or {@link
	 *         com.liferay.portal.util.PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL}).
	 *         The default values can be overridden in
	 *         <code>portal-ext.properties</code> by specifying new values for
	 *         the corresponding properties defined in {@link
	 *         com.liferay.portal.util.PropsValues}. To see how the URL is
	 *         normalized when accessed see {@link
	 *         com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 *         String)}.
	 * @param  serviceContext the service context. Must set the universally
	 *         unique identifier (UUID) for the layout. Can specify the creation
	 *         date, modification date and the expando bridge attributes for the
	 *         layout. For layouts that belong to a layout set prototype, an
	 *         attribute named 'layoutUpdateable' can be used to specify whether
	 *         site administrators can modify this page within their site.
	 * @return the layout
	 * @throws PortalException if a group with the primary key could not be
	 *         found, if the group did not have permission to manage the layouts
	 *         involved, or if layout values were invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			String name, String title, String description, String type,
			boolean hidden, String friendlyURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();

		Locale defaultLocale = LocaleUtil.getDefault();

		localeNamesMap.put(defaultLocale, name);

		return addLayout(
			groupId, privateLayout, parentLayoutId, localeNamesMap,
			new HashMap<Locale, String>(), new HashMap<Locale, String>(),
			new HashMap<Locale, String>(), new HashMap<Locale, String>(), type,
			hidden, friendlyURL, serviceContext);
	}

	/**
	 * Deletes the layout with the primary key, also deleting the layout's child
	 * layouts, and associated resources.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  serviceContext the service context
	 * @throws PortalException if the user did not have permission to delete the
	 *         layout, if a matching layout could not be found , or if some
	 *         other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteLayout(
			long groupId, boolean privateLayout, long layoutId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.DELETE);

		layoutLocalService.deleteLayout(
			groupId, privateLayout, layoutId, serviceContext);
	}

	/**
	 * Deletes the layout with the plid, also deleting the layout's child
	 * layouts, and associated resources.
	 *
	 * @param  plid the primary key of the layout
	 * @param  serviceContext the service context
	 * @throws PortalException if the user did not have permission to delete the
	 *         layout, if a layout with the primary key could not be found , or
	 *         if some other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteLayout(long plid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.DELETE);

		layoutLocalService.deleteLayout(plid, serviceContext);
	}

	/**
	 * Exports the layouts that match the primary keys and the criteria as a
	 * byte array.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutIds the primary keys of the layouts to be exported
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information to export. For information on the keys used in the
	 *         map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  startDate the export's start date
	 * @param  endDate the export's end date
	 * @return the layouts as a byte array
	 * @throws PortalException if a group or any layout with the primary key
	 *         could not be found, if the group did not have permission to
	 *         manage the layouts, or if some other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return layoutLocalService.exportLayouts(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);
	}

	/**
	 * Exports all layouts that match the criteria as a byte array.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information to export. For information on the keys used in the
	 *         map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  startDate the export's start date
	 * @param  endDate the export's end date
	 * @return the layout as a byte array
	 * @throws PortalException if a group with the primary key could not be
	 *         found, if the group did not have permission to manage the
	 *         layouts, or if some other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public byte[] exportLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return layoutLocalService.exportLayouts(
			groupId, privateLayout, parameterMap, startDate, endDate);
	}

	/**
	 * Exports all layouts that match the primary keys and criteria as a file.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutIds the primary keys of the layouts to be exported
	 *         (optionally <code>null</code>)
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information to export. For information on the keys used in the
	 *         map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  startDate the export's start date
	 * @param  endDate the export's end date
	 * @return the layouts as a File
	 * @throws PortalException if a group or any layout with the primary key
	 *         could not be found, it the group did not have permission to
	 *         manage the layouts, or if some other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public File exportLayoutsAsFile(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return layoutLocalService.exportLayoutsAsFile(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);
	}

	/**
	 * Exports the portlet information (categories, permissions, ... etc.) as a
	 * byte array.
	 *
	 * @param  plid the primary key of the layout
	 * @param  groupId the primary key of the group
	 * @param  portletId the primary key of the portlet
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information to export. For information on the keys used in the
	 *         map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  startDate the export's start date
	 * @param  endDate the export's end date
	 * @return the portlet information as a byte array
	 * @throws PortalException if a layout, group, or portlet with the primary
	 *         key could not be found, if the group did not have permission to
	 *         manage the layouts involved, or if some other portal exception
	 *         occurred
	 * @throws SystemException if a system exception occurred
	 */
	public byte[] exportPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return layoutLocalService.exportPortletInfo(
			plid, groupId, portletId, parameterMap, startDate, endDate);
	}

	/**
	 * Exports the portlet information (categories, permissions, ... etc.) as a
	 * file.
	 *
	 * @param  plid the primary key of the layout
	 * @param  groupId the primary key of the group
	 * @param  portletId the primary key of the portlet
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information to export. For information on the keys used in the
	 *         map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  startDate the export's start date
	 * @param  endDate the export's end date
	 * @return the portlet information as a file
	 * @throws PortalException if a layout, group, or portlet with the primary
	 *         key could not be found, it the group did not have permission to
	 *         manage the layouts involved, or if some other portal exception
	 *         occurred
	 * @throws SystemException if a system exception occurred
	 */
	public File exportPortletInfoAsFile(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return layoutLocalService.exportPortletInfoAsFile(
			plid, groupId, portletId, parameterMap, startDate, endDate);
	}

	/**
	 * Returns the primary key of the default layout for the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  scopeGroupId the primary key of the scope group. See {@link
	 *         com.liferay.portal.service.ServiceContext#getScopeGroupId()}.
	 * @param  privateLayout whether the layout is private to the group
	 * @param  portletId the primary key of the portlet
	 * @return Returns the primary key of the default layout group; {@link
	 *         com.liferay.portal.model.LayoutConstants#DEFAULT_PLID} otherwise
	 * @throws PortalException if a group, layout, or portlet with the primary
	 *         key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public long getDefaultPlid(
			long groupId, long scopeGroupId, boolean privateLayout,
			String portletId)
		throws PortalException, SystemException {

		if (groupId <= 0) {
			return LayoutConstants.DEFAULT_PLID;
		}

		PermissionChecker permissionChecker = getPermissionChecker();

		String scopeGroupLayoutUuid = null;

		Group scopeGroup = groupLocalService.getGroup(scopeGroupId);

		if (scopeGroup.isLayout()) {
			Layout scopeGroupLayout = layoutLocalService.getLayout(
				scopeGroup.getClassPK());

			scopeGroupLayoutUuid = scopeGroupLayout.getUuid();
		}

		Map<Long, javax.portlet.PortletPreferences> jxPreferencesMap =
			PortletPreferencesFactoryUtil.getPortletSetupMap(
				scopeGroup.getCompanyId(), groupId,
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, portletId, privateLayout);

		for (Map.Entry<Long, javax.portlet.PortletPreferences> entry :
				jxPreferencesMap.entrySet()) {

			long plid = entry.getKey();

			Layout layout = null;

			try {
				layout = layoutLocalService.getLayout(plid);
			}
			catch (NoSuchLayoutException nsle) {
				continue;
			}

			if (!LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.VIEW)) {

				continue;
			}

			if (!layout.isTypePortlet()) {
				continue;
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (!layoutTypePortlet.hasPortletId(portletId)) {
				continue;
			}

			javax.portlet.PortletPreferences jxPreferences = entry.getValue();

			String scopeType = GetterUtil.getString(
				jxPreferences.getValue("lfrScopeType", null));

			if (scopeGroup.isLayout()) {
				String scopeLayoutUuid = GetterUtil.getString(
					jxPreferences.getValue("lfrScopeLayoutUuid", null));

				if (Validator.isNotNull(scopeType) &&
					Validator.isNotNull(scopeLayoutUuid) &&
					scopeLayoutUuid.equals(scopeGroupLayoutUuid)) {

					return layout.getPlid();
				}
			}
			else if (scopeGroup.isCompany()) {
				if (Validator.isNotNull(scopeType) &&
					scopeType.equals("company")) {

					return layout.getPlid();
				}
			}
			else {
				if (Validator.isNull(scopeType)) {
					return layout.getPlid();
				}
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	/**
	 * Returns the name of the layout.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  languageId the primary key of the language. For more information
	 *         See {@link java.util.Locale}.
	 * @return the layout's name
	 * @throws PortalException if a matching layout could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public String getLayoutName(
			long groupId, boolean privateLayout, long layoutId,
			String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		return layout.getName(languageId);
	}

	/**
	 * Returns the layout references for all the layouts that belong to the
	 * company and belong to the portlet that matches the preferences.
	 *
	 * @param  companyId the primary key of the company
	 * @param  portletId the primary key of the portlet
	 * @param  preferencesKey the portlet's preference key
	 * @param  preferencesValue the portlet's preference value
	 * @return the layout references of the matching layouts
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutReference[] getLayoutReferences(
			long companyId, String portletId, String preferencesKey,
			String preferencesValue)
		throws SystemException {

		return layoutLocalService.getLayouts(
			companyId, portletId, preferencesKey, preferencesValue);
	}

	public List<Layout> getLayouts(long groupId, boolean privateLayout)
		throws PortalException, SystemException {

		List<Layout> layouts = layoutLocalService.getLayouts(
			groupId, privateLayout);

		return filterLayouts(layouts);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws PortalException, SystemException {

		List<Layout> layouts = layoutLocalService.getLayouts(
			groupId, privateLayout, parentLayoutId);

		return filterLayouts(layouts);
	}

	/**
	 * Imports the layouts from the byte array.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information will be imported. For information on the keys used in
	 *         the map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  bytes the byte array with the data
	 * @throws PortalException if a group with the primary key could not be
	 *         found, if the group did not have permission to manage the
	 *         layouts, or if some other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.lar.LayoutImporter
	 */
	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, byte[] bytes)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, bytes);
	}

	/**
	 * Imports the layouts from the file.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information will be imported. For information on the keys used in
	 *         the map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  file the LAR file with the data
	 * @throws PortalException if a group with the primary key could not be
	 *         found, if the group did not have permission to manage the layouts
	 *         and publish, or if some other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.lar.LayoutImporter
	 */
	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, file);
	}

	/**
	 * Imports the layouts from the input stream.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information will be imported. For information on the keys used in
	 *         the map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  is the input stream
	 * @throws PortalException if a group with the primary key could not be
	 *         found, if the group did not have permission to manage the
	 *         layouts, or if some other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.lar.LayoutImporter
	 */
	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, is);
	}

	/**
	 * Imports the portlet information (categories, permissions, ... etc.) from
	 * the file.
	 *
	 * @param  plid the primary key of the layout
	 * @param  groupId the primary key of the group
	 * @param  portletId the primary key of the portlet
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information will be imported. For information on the keys used in
	 *         the map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  file the LAR file with the data
	 * @throws PortalException if a group, layout, or portlet with the primary
	 *         key could not be found, or if the group did not have permission
	 *         to manage the layouts
	 * @throws SystemException if a system exception occurred
	 */
	public void importPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		layoutLocalService.importPortletInfo(
			getUserId(), plid, groupId, portletId, parameterMap, file);
	}

	/**
	 * Imports the portlet information (categories, permissions, ... etc.) from
	 * the input stream.
	 *
	 * @param  plid the primary key of the layout
	 * @param  groupId the primary key of the group
	 * @param  portletId the primary key of the portlet
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information will be imported. For information on the keys used in
	 *         the map see {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	 * @param  is the input stream
	 * @throws PortalException if a group, portlet, or layout with the primary
	 *         key could not be found or if the group did not have permission to
	 *         manage the layouts
	 * @throws SystemException if a system exception occurred
	 */
	public void importPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		layoutLocalService.importPortletInfo(
			getUserId(), plid, groupId, portletId, parameterMap, is);
	}

	/**
	 * Schedules a range of layouts to be published.
	 *
	 * @param  sourceGroupId the primary key of the source group
	 * @param  targetGroupId the primary key of the target group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutIdMap the layouts considered for publishing, specified by
	 *         the layout IDs and booleans indicating whether they have children
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information will be used. See {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  scope the scope of the pages. It can be <code>all-pages</code> or
	 *         <code>selected-pages</code>.
	 * @param  startDate the start date
	 * @param  endDate the end date
	 * @param  groupName the group name (optionally {@link
	 *         com.liferay.portal.kernel.messaging.DestinationNames#LAYOUTS_LOCAL_PUBLISHER}).
	 *         See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	 * @param  cronText the cron text. See {@link
	 *         com.liferay.portal.kernel.cal.RecurrenceSerializer #toCronText}
	 * @param  schedulerStartDate the scheduler start date
	 * @param  schedulerEndDate the scheduler end date
	 * @param  description the scheduler description
	 * @throws PortalException if the group did not have permission to manage
	 *         and publish
	 * @throws SystemException if a system exception occurred
	 */
	public void schedulePublishToLive(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String scope, Date startDate, Date endDate, String groupName,
			String cronText, Date schedulerStartDate, Date schedulerEndDate,
			String description)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId, ActionKeys.PUBLISH_STAGING);

		String jobName = PortalUUIDUtil.generate();

		Trigger trigger = new CronTrigger(
			jobName, groupName, schedulerStartDate, schedulerEndDate, cronText);

		String command = StringPool.BLANK;

		if (scope.equals("all-pages")) {
			command = LayoutsLocalPublisherRequest.COMMAND_ALL_PAGES;
		}
		else if (scope.equals("selected-pages")) {
			command = LayoutsLocalPublisherRequest.COMMAND_SELECTED_PAGES;
		}

		LayoutsLocalPublisherRequest publisherRequest =
			new LayoutsLocalPublisherRequest(
				command, getUserId(), sourceGroupId, targetGroupId,
				privateLayout, layoutIdMap, parameterMap, startDate, endDate);

		SchedulerEngineHelperUtil.schedule(
			trigger, StorageType.PERSISTED, description,
			DestinationNames.LAYOUTS_LOCAL_PUBLISHER, publisherRequest, 0);
	}

	/**
	 * Schedules a range of layouts to be stored.
	 *
	 * @param  sourceGroupId the primary key of the source group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutIdMap the layouts considered for publishing, specified by
	 *         the layout IDs and booleans indicating whether they have children
	 * @param  parameterMap the mapping of parameters indicating which
	 *         information will be used. See {@link
	 *         com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  remoteAddress the remote address
	 * @param  remotePort the remote port
	 * @param  remotePathContext the remote path context
	 * @param  secureConnection whether the connection is secure
	 * @param  remoteGroupId the primary key of the remote group
	 * @param  remotePrivateLayout whether remote group's layout is private
	 * @param  startDate the start date
	 * @param  endDate the end date
	 * @param  groupName the group name. Optionally {@link
	 *         com.liferay.portal.kernel.messaging.DestinationNames#LAYOUTS_LOCAL_PUBLISHER}).
	 *         See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	 * @param  cronText the cron text. See {@link
	 *         com.liferay.portal.kernel.cal.RecurrenceSerializer #toCronText}
	 * @param  schedulerStartDate the scheduler start date
	 * @param  schedulerEndDate the scheduler end date
	 * @param  description the scheduler description
	 * @throws PortalException if a group with the source group primary key was
	 *         not found or if the group did not have permission to publish
	 * @throws SystemException if a system exception occurred
	 */
	public void schedulePublishToRemote(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate,
			String groupName, String cronText, Date schedulerStartDate,
			Date schedulerEndDate, String description)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), sourceGroupId, ActionKeys.PUBLISH_STAGING);

		LayoutsRemotePublisherRequest publisherRequest =
			new LayoutsRemotePublisherRequest(
				getUserId(), sourceGroupId, privateLayout, layoutIdMap,
				parameterMap, remoteAddress, remotePort, remotePathContext,
				secureConnection, remoteGroupId, remotePrivateLayout, startDate,
				endDate);

		String jobName = PortalUUIDUtil.generate();

		Trigger trigger = new CronTrigger(
			jobName, groupName, schedulerStartDate, schedulerEndDate, cronText);

		SchedulerEngineHelperUtil.schedule(
			trigger, StorageType.PERSISTED, description,
			DestinationNames.LAYOUTS_REMOTE_PUBLISHER, publisherRequest, 0);
	}

	/**
	 * Sets the layouts for the group, replacing and prioritizing all layouts of
	 * the parent layout.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parentLayoutId the primary key of the parent layout
	 * @param  layoutIds the primary keys of the layouts
	 * @param  serviceContext the service context
	 * @throws PortalException if a group or layout with the primary key could
	 *         not be found, if the group did not have permission to manage the
	 *         layouts, if no layouts were specified, if the first layout was
	 *         not page-able, if the first layout was hidden, or if some other
	 *         portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		layoutLocalService.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds, serviceContext);
	}

	/**
	 * Deletes the job from the scheduler's queue.
	 *
	 * @param  groupId the primary key of the group
	 * @param  jobName the job name
	 * @param  groupName the group name (optionally {@link
	 *         com.liferay.portal.kernel.messaging.DestinationNames#LAYOUTS_LOCAL_PUBLISHER}).
	 *         See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	 * @throws PortalException if the group did not permission to manage staging
	 *         and publish
	 * @throws SystemException if a system exception occurred
	 */
	public void unschedulePublishToLive(
			long groupId, String jobName, String groupName)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.PUBLISH_STAGING);

		SchedulerEngineHelperUtil.delete(
			jobName, groupName, StorageType.PERSISTED);
	}

	/**
	 * Deletes the job from the scheduler's persistent queue.
	 *
	 * @param  groupId the primary key of the group
	 * @param  jobName the job name
	 * @param  groupName the group name (optionally {@link
	 *         com.liferay.portal.kernel.messaging.DestinationNames#LAYOUTS_LOCAL_PUBLISHER}).
	 *         See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	 * @throws PortalException if a group with the primary key could not be
	 *         found or if the group did not have permission to publish
	 * @throws SystemException if a system exception occurred
	 */
	public void unschedulePublishToRemote(
			long groupId, String jobName, String groupName)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.PUBLISH_STAGING);

		SchedulerEngineHelperUtil.delete(
			jobName, groupName, StorageType.PERSISTED);
	}

	/**
	 * Updates the layout.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  parentLayoutId the primary key of the layout's new parent layout
	 * @param  localeNamesMap the layout's locales and localized names
	 * @param  localeTitlesMap the layout's locales and localized titles
	 * @param  descriptionMap the locales and localized descriptions to merge
	 *         (optionally <code>null</code>)
	 * @param  keywordsMap the locales and localized keywords to merge
	 *         (optionally <code>null</code>)
	 * @param  robotsMap the locales and localized robots to merge (optionally
	 *         <code>null</code>)
	 * @param  type the layout's new type (optionally {@link
	 *         com.liferay.portal.model.LayoutConstants#TYPE_PORTLET})
	 * @param  hidden whether the layout is hidden
	 * @param  friendlyURL the layout's new friendly URL (optionally {@link
	 *         com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL}
	 *         or {@link
	 *         com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL}).
	 *         The default values can be overridden in
	 *         <code>portal-ext.properties</code> by specifying new values for
	 *         the corresponding properties defined in {@link
	 *         com.liferay.portal.util.PropsValues}. To see how the URL is
	 *         normalized when accessed see {@link
	 *         com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 *         String)}.
	 * @param  iconImage whether the icon image will be updated
	 * @param  iconBytes the byte array of the layout's new icon image
	 * @param  serviceContext the service context. Can set the modification date
	 *         and expando bridge attributes for the layout.
	 * @return the updated layout
	 * @throws PortalException if a group or layout with the primary key could
	 *         not be found, if the user did not have permission to update the
	 *         layout, if a unique friendly URL could not be generated, if a
	 *         valid parent layout ID to use could not be found, or if the
	 *         layout parameters were invalid
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> keywordsMap,
			Map<Locale, String> robotsMap, String type, boolean hidden,
			String friendlyURL, Boolean iconImage, byte[] iconBytes,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, localeNamesMap,
			localeTitlesMap, descriptionMap, keywordsMap, robotsMap, type,
			hidden, friendlyURL, iconImage, iconBytes, serviceContext);
	}

	/**
	 * Updates the layout replacing its type settings.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  typeSettings the settings to load the unicode properties object.
	 *         See {@link com.liferay.portal.kernel.util.UnicodeProperties
	 *         #fastLoad(String)}.
	 * @return the updated layout
	 * @throws PortalException if a matching layout could not be found or if the
	 *         user did not have permission to update the layout
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, typeSettings);
	}

	/**
	 * Updates the look and feel of the layout.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  themeId the primary key of the layout's new theme
	 * @param  colorSchemeId the primary key of the layout's new color scheme
	 * @param  css the layout's new CSS
	 * @param  wapTheme whether the theme is for WAP browsers
	 * @return the updated layout
	 * @throws PortalException if a matching layout could not be found, or if
	 *         the user did not have permission to update the layout and
	 *         permission to apply the theme
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		if (Validator.isNotNull(themeId)) {
			pluginSettingLocalService.checkPermission(
				getUserId(), themeId, Plugin.TYPE_THEME);
		}

		return layoutLocalService.updateLookAndFeel(
			groupId, privateLayout, layoutId, themeId, colorSchemeId, css,
			wapTheme);
	}

	/**
	 * Updates the name of the layout matching the group, layout ID, and
	 * privacy.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  name the layout's new name
	 * @param  languageId the primary key of the language. For more information
	 *         see {@link java.util.Locale}.
	 * @return the updated layout
	 * @throws PortalException if a matching layout could not be found, if the
	 *         user did not have permission to update the layout, or if the new
	 *         name was <code>null</code>
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateName(
			groupId, privateLayout, layoutId, name, languageId);
	}

	/**
	 * Updates the name of the layout matching the primary key.
	 *
	 * @param  plid the primary key of the layout
	 * @param  name the name to be assigned
	 * @param  languageId the primary key of the language. For more information
	 *         see {@link java.util.Locale}.
	 * @return the updated layout
	 * @throws PortalException if a layout with the primary key could not be
	 *         found, or if the user did not have permission to update the
	 *         layout, or if the name was <code>null</code>
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updateName(long plid, String name, String languageId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updateName(plid, name, languageId);
	}

	/**
	 * Updates the parent layout ID of the layout matching the group, layout ID,
	 * and privacy.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  parentLayoutId the primary key to be assigned to the parent
	 *         layout
	 * @return the matching layout
	 * @throws PortalException if a valid parent layout ID to use could not be
	 *         found, if a matching layout could not be found, or if the user
	 *         did not have permission to update the layout
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);
	}

	/**
	 * Updates the parent layout ID of the layout matching the primary key. If a
	 * layout matching the parent primary key is found, the layout ID of that
	 * layout is assigned, otherwise {@link
	 * com.liferay.portal.model.LayoutConstants#DEFAULT_PARENT_LAYOUT_ID} is
	 * assigned.
	 *
	 * @param  plid the primary key of the layout
	 * @param  parentPlid the primary key of the parent layout
	 * @return the layout matching the primary key
	 * @throws PortalException if a layout with the primary key could not be
	 *         found, if the user did not have permission to update the layout,
	 *         or if a valid parent layout ID to use could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updateParentLayoutId(plid, parentPlid);
	}

	/**
	 * Updates the priority of the layout matching the group, layout ID, and
	 * privacy.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  priority the layout's new priority
	 * @return the updated layout
	 * @throws PortalException if a matching layout could not be found or if the
	 *         user did not have permission to update the layout
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updatePriority(
			groupId, privateLayout, layoutId, priority);
	}

	/**
	 * Updates the priority of the layout matching the primary key.
	 *
	 * @param  plid the primary key of the layout
	 * @param  priority the layout's new priority
	 * @return the updated layout
	 * @throws PortalException if a layout with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updatePriority(long plid, int priority)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updatePriority(plid, priority);
	}

	protected List<Layout> filterLayouts(List<Layout> layouts)
		throws PortalException, SystemException {

		List<Layout> filteredLayouts = new ArrayList<Layout>();

		for (Layout layout : layouts) {
			if (LayoutPermissionUtil.contains(
					getPermissionChecker(), layout.getPlid(),
					ActionKeys.VIEW)) {

				filteredLayouts.add(layout);
			}
		}

		return filteredLayouts;
	}

}