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

package com.liferay.site.navigation.menu.web.portlet.template;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.NavItem;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplateConstants;
import com.liferay.site.navigation.menu.web.configuration.NavigationMenuConfigurationValues;
import com.liferay.site.navigation.menu.web.configuration.NavigationMenuWebConfiguration;
import com.liferay.site.navigation.menu.web.constants.NavigationMenuPortletKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Juergen Kappler
 */
@Component(
	configurationPid = "com.liferay.site.navigation.menu.web.configuration.NavigationMenuWebConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {"javax.portlet.name=" + NavigationMenuPortletKeys.NAVIGATION},
	service = TemplateHandler.class
)
public class NavigationMenuPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return NavItem.class.getName();
	}

	@Override
	public Map<String, Object> getCustomContextObjects() {
		Map<String, Object> contextObjects = new HashMap<>();

		contextObjects.put("navItem", NavItem.class);

		return contextObjects;
	}

	@Override
	public String getDefaultTemplateKey() {
		return _navigationMenuWebConfiguration.ddmTemplateKeyDefault();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language", locale);

		String portletTitle = PortalUtil.getPortletTitle(
			NavigationMenuPortletKeys.NAVIGATION, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return NavigationMenuPortletKeys.NAVIGATION;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup templateVariableGroup =
			templateVariableGroups.get("fields");

		templateVariableGroup.empty();

		templateVariableGroup.addVariable(
			"bullet-style", String.class, "bulletStyle");
		templateVariableGroup.addVariable(
			"header-type", String.class, "headerType");
		templateVariableGroup.addVariable(
			"included-layouts", String.class, "includedLayouts");
		templateVariableGroup.addVariable(
			"nested-children", String.class, "nestedChildren");
		templateVariableGroup.addVariable(
			"root-layout-level", Integer.class, "rootLayoutLevel");
		templateVariableGroup.addVariable(
			"root-layout-type", String.class, "rootLayoutType");
		templateVariableGroup.addCollectionVariable(
			"navigation-items", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "navigation-item",
			NavItem.class, "navigationEntry", "getName()");

		templateVariableGroups.put(
			"navigation-util", getUtilTemplateVariableGroup());

		return templateVariableGroups;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_navigationMenuWebConfiguration = Configurable.createConfigurable(
			NavigationMenuWebConfiguration.class, properties);
	}

	@Override
	protected String getTemplatesConfigPath() {
		return NavigationMenuConfigurationValues.DISPLAY_TEMPLATES_CONFIG;
	}

	protected TemplateVariableGroup getUtilTemplateVariableGroup() {
		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"navigation-util");

		templateVariableGroup.addVariable("nav-item", NavItem.class, "navItem");

		return templateVariableGroup;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NavigationMenuPortletDisplayTemplateHandler.class);

	private volatile NavigationMenuWebConfiguration
		_navigationMenuWebConfiguration;

}