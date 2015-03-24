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

package com.liferay.portal.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.PortletMode;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DefaultLayoutTypeAccessPolicyImpl
	implements LayoutTypeAccessPolicy {

	public static LayoutTypeAccessPolicy create() {
		return _instance;
	}

	@Override
	public void checkAccessAllowedToPortlet(
			HttpServletRequest request, Layout layout, Portlet portlet)
		throws PortalException {

		if (layout.isTypeControlPanel()) {
			isAccessAllowedToControlPanelPortlet(request, layout, portlet);

			return;
		}

		if (isAccessAllowedToLayoutPortlet(request, layout, portlet)) {
			PortalUtil.addPortletDefaultResource(request, portlet);

			if (hasAccessPermission(request, layout, portlet)) {
				return;
			}
		}

		throw new PrincipalException();
	}

	@Override
	public boolean isAddLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		if (layout.isTypeControlPanel()) {
			return false;
		}

		return LayoutPermissionUtil.contains(
			permissionChecker, layout, ActionKeys.ADD_LAYOUT);
	}

	@Override
	public boolean isCustomizeLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		if (layout.isTypeControlPanel()) {
			return false;
		}

		return LayoutPermissionUtil.contains(
			permissionChecker, layout, ActionKeys.CUSTOMIZE);
	}

	@Override
	public boolean isDeleteLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		if (layout.isTypeControlPanel()) {
			return false;
		}

		return LayoutPermissionUtil.contains(
			permissionChecker, layout, ActionKeys.DELETE);
	}

	@Override
	public boolean isUpdateLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		if (layout.isTypeControlPanel()) {
			return false;
		}

		return LayoutPermissionUtil.contains(
			permissionChecker, layout, ActionKeys.UPDATE);
	}

	protected boolean hasAccessPermission(
			HttpServletRequest request, Layout layout, Portlet portlet)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletMode portletMode = PortletMode.VIEW;

		String portletId = portlet.getPortletId();
		String ppid = request.getParameter("p_p_id");
		String ppmode = request.getParameter("p_p_mode");

		if (portletId.equals(ppid) && (ppmode != null)) {
			portletMode = PortletModeFactory.getPortletMode(ppmode);
		}

		return PortletPermissionUtil.hasAccessPermission(
			permissionChecker, themeDisplay.getScopeGroupId(), layout, portlet,
			portletMode);
	}

	protected void isAccessAllowedToControlPanelPortlet(
			HttpServletRequest request, Layout layout, Portlet portlet)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (PortletPermissionUtil.hasControlPanelAccessPermission(
				permissionChecker, themeDisplay.getScopeGroupId(), portlet)) {

			return;
		}

		if (isAccessGrantedByRuntimePortlet(request)) {
			return;
		}

		if (isAccessGrantedByPortletAuthenticationToken(
				request, layout, portlet)) {

			return;
		}

		throw new PrincipalException();
	}

	protected boolean isAccessAllowedToLayoutPortlet(
			HttpServletRequest request, Layout layout, Portlet portlet)
		throws PortalException {

		if (isAccessGrantedByRuntimePortlet(request)) {
			return true;
		}

		if (isAccessGrantedByPortletOnPage(layout, portlet)) {
			return true;
		}

		if (isLayoutConfigurationAllowed(request, layout, portlet)) {
			return true;
		}

		if (isAccessGrantedByPortletAuthenticationToken(
				request, layout, portlet)) {

			return true;
		}

		return false;
	}

	protected boolean isAccessGrantedByPortletAuthenticationToken(
		HttpServletRequest request, Layout layout, Portlet portlet) {

		String portletId = portlet.getPortletId();

		if (!portlet.isAddDefaultResource()) {
			return false;
		}

		if (!_PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED) {
			return true;
		}

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String strutsAction = ParamUtil.getString(
			request, namespace + "struts_action");

		if (Validator.isNull(strutsAction)) {
			strutsAction = ParamUtil.getString(request, "struts_action");
		}

		String requestPortletAuthenticationToken = ParamUtil.getString(
			request, "p_p_auth");

		if (Validator.isNull(requestPortletAuthenticationToken)) {
			HttpServletRequest originalRequest =
				PortalUtil.getOriginalServletRequest(request);

			requestPortletAuthenticationToken = ParamUtil.getString(
				originalRequest, "p_p_auth");
		}

		if (AuthTokenUtil.isValidPortletInvocationToken(
				request, layout.getPlid(), portletId, strutsAction,
				requestPortletAuthenticationToken)) {

			return true;
		}

		return false;
	}

	protected boolean isAccessGrantedByPortletOnPage(
			Layout layout, Portlet portlet)
		throws PortalException {

		String portletId = portlet.getPortletId();

		if (layout.isTypePanel() && isPanelSelectedPortlet(layout, portletId)) {
			return true;
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if ((layoutTypePortlet != null) &&
			layoutTypePortlet.hasPortletId(portletId)) {

			return true;
		}

		return false;
	}

	protected boolean isAccessGrantedByRuntimePortlet(
		HttpServletRequest request) {

		Boolean renderPortletResource = (Boolean)request.getAttribute(
			WebKeys.RENDER_PORTLET_RESOURCE);

		if (renderPortletResource != null) {
			return renderPortletResource;
		}

		return false;
	}

	protected boolean isLayoutConfigurationAllowed(
			HttpServletRequest request, Layout layout, Portlet portlet)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return false;
		}

		String portletId = portlet.getPortletId();

		if (!portletId.equals(PortletKeys.LAYOUTS_ADMIN)) {
			return false;
		}

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		Group group = layout.getGroup();

		if (group.isSite()) {
			if (LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.CUSTOMIZE) ||
				LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.UPDATE)) {

				return true;
			}
		}

		if (group.isCompany()) {
			if (permissionChecker.isCompanyAdmin()) {
				return true;
			}
		}
		else if (group.isLayoutPrototype()) {
			long layoutPrototypeId = group.getClassPK();

			if (LayoutPrototypePermissionUtil.contains(
					permissionChecker, layoutPrototypeId, ActionKeys.UPDATE)) {

				return true;
			}
		}
		else if (group.isLayoutSetPrototype()) {
			long layoutSetPrototypeId = group.getClassPK();

			if (LayoutSetPrototypePermissionUtil.contains(
					permissionChecker, layoutSetPrototypeId,
					ActionKeys.UPDATE)) {

				return true;
			}
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			if (OrganizationPermissionUtil.contains(
					permissionChecker, organizationId, ActionKeys.UPDATE)) {

				return true;
			}
		}
		else if (group.isUserGroup()) {
			long scopeGroupId = themeDisplay.getScopeGroupId();

			if (GroupPermissionUtil.contains(
					permissionChecker, scopeGroupId, ActionKeys.UPDATE)) {

				return true;
			}
		}
		else if (group.isUser()) {
			return true;
		}

		return false;
	}

	protected boolean isPanelSelectedPortlet(Layout layout, String portletId) {
		String panelSelectedPortlets = layout.getTypeSettingsProperty(
			"panelSelectedPortlets");

		if (Validator.isNotNull(panelSelectedPortlets)) {
			String[] panelSelectedPortletsArray = StringUtil.split(
				panelSelectedPortlets);

			return ArrayUtil.contains(panelSelectedPortletsArray, portletId);
		}

		return false;
	}

	private static final boolean _PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED));

	private static final LayoutTypeAccessPolicy _instance =
		new DefaultLayoutTypeAccessPolicyImpl();

}