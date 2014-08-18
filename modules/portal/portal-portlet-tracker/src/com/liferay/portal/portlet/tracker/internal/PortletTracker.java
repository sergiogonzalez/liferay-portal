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

package com.liferay.portal.portlet.tracker.internal;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.EventDefinition;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.security.permission.ResourceActions;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.ResourceActionLocalService;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletBagFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.registry.util.StringPlus;
import com.liferay.util.JS;

import java.io.IOException;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.Portlet;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.apache.jasper.servlet.JspServlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.runtime.HttpServiceRuntime;
import org.osgi.service.http.runtime.HttpServiceRuntimeConstants;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true, service = PortletTracker.class
)
public class PortletTracker
	implements
		ServiceTrackerCustomizer<Portlet, com.liferay.portal.model.Portlet> {

	@Override
	public com.liferay.portal.model.Portlet addingService(
		ServiceReference<Portlet> serviceReference) {

		BundleContext bundleContext = _componentContext.getBundleContext();

		Portlet portlet = bundleContext.getService(serviceReference);

		String portletName = (String)serviceReference.getProperty(
			"javax.portlet.name");

		if (Validator.isNull(portletName)) {
			Class<?> clazz = portlet.getClass();

			portletName = clazz.getName();
		}

		String portletId = JS.getSafeName(portletName);

		if (portletId.length() > _PORTLET_ID_MAX_LENGTH) {
			_log.error(
				"Portlet id " + portletId + " has more than " +
					_PORTLET_ID_MAX_LENGTH + " characters");

			bundleContext.ungetService(serviceReference);

			return null;
		}

		com.liferay.portal.model.Portlet portletModel =
			_portletLocalService.getPortletById(portletId);

		if (portletModel != null) {
			_log.error("Portlet id " + portletId + " is already in use");

			bundleContext.ungetService(serviceReference);

			return null;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Adding " + serviceReference);
		}

		return addingPortlet(serviceReference, portlet, portletName, portletId);
	}

	@Override
	public void modifiedService(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		removedService(serviceReference, portletModel);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		portletModel.setReady(false);

		clearServiceRegistrations(serviceReference.getBundle());

		BundleContext bundleContext = _componentContext.getBundleContext();

		bundleContext.ungetService(serviceReference);

		_portletInstanceFactory.destroy(portletModel);

		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
				company.getCompanyId(), WebKeys.PORTLET_CATEGORY);

			portletCategory.separate(portletModel.getRootPortletId());
		}

		PortletBag portletBag = PortletBagPool.remove(
			portletModel.getRootPortletId());

		if (portletBag != null) {
			portletBag.destroy();
		}
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;

		BundleContext bundleContext = _componentContext.getBundleContext();

		_serviceTracker =
			new ServiceTracker<Portlet, com.liferay.portal.model.Portlet>(
				bundleContext, Portlet.class, this);

		_serviceTracker.open();

		if (_log.isInfoEnabled()) {
			_log.info("Activated");
		}
	}

	protected com.liferay.portal.model.Portlet addingPortlet(
		ServiceReference<Portlet> serviceReference, Portlet portlet,
		String portletName, String portletId) {

		Bundle bundle = serviceReference.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ServiceRegistrations serviceRegistrations = getServiceRegistrations(
			bundle);

		serviceRegistrations._counter++;

		BundlePortletApp bundlePortletApp = createBundlePortletApp(
			bundle, bundleWiring.getClassLoader(), serviceRegistrations);

		com.liferay.portal.model.Portlet portletModel = buildPortletModel(
			bundlePortletApp, portletId);

		portletModel.setPortletName(portletName);

		String displayName = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.display-name"),
			portletName);

		portletModel.setDisplayName(displayName);

		Class<?> portletClazz = portlet.getClass();

		portletModel.setPortletClass(portletClazz.getName());

		collectJxPortletFeatures(serviceReference, portletModel);
		collectLiferayFeatures(serviceReference, portletModel);

		PortletBagFactory portletBagFactory = new BundlePortletBagFactory(
			portlet);

		portletBagFactory.setClassLoader(bundleWiring.getClassLoader());
		portletBagFactory.setServletContext(
			bundlePortletApp.getServletContext());
		portletBagFactory.setWARFile(true);

		try {
			portletBagFactory.create(portletModel);

			checkWebResources(bundle, portletModel, serviceRegistrations);

			List<Company> companies = _companyLocalService.getCompanies();

			deployPortlet(serviceReference, portletModel, companies);

			checkResources(serviceReference, portletModel, companies);

			portletModel.setReady(true);

			if (_log.isInfoEnabled()) {
				_log.info("Added " + serviceReference);
			}

			return portletModel;
		}
		catch (Exception e) {
			_log.error(e, e);

			BundleContext bundleContext = _componentContext.getBundleContext();

			bundleContext.ungetService(serviceReference);

			return null;
		}
	}

	protected com.liferay.portal.model.Portlet buildPortletModel(
		BundlePortletApp bundlePortletApp, String portletId) {

		com.liferay.portal.model.Portlet portletModel = new PortletImpl(
			CompanyConstants.SYSTEM, portletId);

		portletModel.setPluginPackage(bundlePortletApp.getPluginPackage());
		portletModel.setPortletApp(bundlePortletApp);
		portletModel.setRoleMappers(bundlePortletApp.getRoleMappers());

		return portletModel;
	}

	protected void checkResources(
			ServiceReference<Portlet> serviceReference,
			com.liferay.portal.model.Portlet portletModel,
			List<Company> companies)
		throws PortalException {

		List<String> portletActions =
			_resourceActions.getPortletResourceActions(
				portletModel.getPortletId());

		_resourceActionLocalService.checkResourceActions(
			portletModel.getPortletId(), portletActions);

		List<String> modelNames = _resourceActions.getPortletModelResources(
			portletModel.getPortletId());

		for (String modelName : modelNames) {
			List<String> modelActions =
				_resourceActions.getModelResourceActions(modelName);

			_resourceActionLocalService.checkResourceActions(
				modelName, modelActions);
		}

		for (Company company : companies) {
			com.liferay.portal.model.Portlet companyPortletModel =
				_portletLocalService.getPortletById(
					company.getCompanyId(), portletModel.getPortletId());

			_portletLocalService.checkPortlet(companyPortletModel);
		}
	}

	protected void checkWebResources(
		Bundle bundle, com.liferay.portal.model.Portlet portletModel,
		ServiceRegistrations serviceRegistrations) {

		if (serviceRegistrations.
				_staticResourcesServletServiceRegistration != null) {

			return;
		}

		Enumeration<URL> urls = bundle.findEntries(
			"/META-INF/resources", "*.*", true);

		if ((urls == null) || !urls.hasMoreElements()) {
			return;
		}

		BundleContext bundleContext = bundle.getBundleContext();

		createStaticResourceServlet(
			portletModel.getPortletApp(), bundleContext, serviceRegistrations);

		urls = bundle.findEntries("/META-INF/resources", "*.jsp", true);

		if ((urls == null) || !urls.hasMoreElements()) {
			return;
		}

		createJspServlet(
			portletModel.getPortletApp(), bundleContext, serviceRegistrations);
	}

	protected void clearServiceRegistrations(Bundle bundle) {
		ServiceRegistrations serviceRegistrations = _serviceRegistrations.get(
			bundle);

		if (serviceRegistrations == null) {
			return;
		}

		serviceRegistrations._counter--;

		if (serviceRegistrations._counter > 0) {
			return;
		}

		serviceRegistrations = _serviceRegistrations.remove(bundle);

		if (serviceRegistrations._jspServletServiceRegistration != null) {
			serviceRegistrations._jspServletServiceRegistration.unregister();
		}

		if (serviceRegistrations.
				_staticResourcesServletServiceRegistration != null) {

			serviceRegistrations.
				_staticResourcesServletServiceRegistration.unregister();
		}

		if (serviceRegistrations._bundlePortletAppServiceRegistration != null) {
			serviceRegistrations.
				_bundlePortletAppServiceRegistration.unregister();
		}
	}

	protected void collectCacheScope(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {
	}

	protected void collectExpirationCache(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		int expirationCache = GetterUtil.getInteger(
			serviceReference.getProperty("javax.portlet.expiration-cache"));

		portletModel.setExpCache(expirationCache);
	}

	protected void collectInitParams(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Map<String, String> initParams = new HashMap<String, String>();

		for (String initParamKey : serviceReference.getPropertyKeys()) {
			if (!initParamKey.startsWith("javax.portlet.init-param.")) {
				continue;
			}

			initParams.put(
				initParamKey.substring("javax.portlet.init-param.".length()),
				GetterUtil.getString(
					serviceReference.getProperty(initParamKey)));
		}

		portletModel.setInitParams(initParams);
	}

	protected void collectJxPortletFeatures(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		collectCacheScope(serviceReference, portletModel);
		collectExpirationCache(serviceReference, portletModel);
		collectInitParams(serviceReference, portletModel);
		collectPortletInfo(serviceReference, portletModel);
		collectPortletModes(serviceReference, portletModel);
		collectPortletPreferences(serviceReference, portletModel);
		collectResourceBundle(serviceReference, portletModel);
		collectSecurityRoleRefs(serviceReference, portletModel);
		collectSupportedProcessingEvents(serviceReference, portletModel);
		collectSupportedPublicRenderParameters(serviceReference, portletModel);
		collectSupportedPublishingEvents(serviceReference, portletModel);
		collectWindowStates(serviceReference, portletModel);
	}

	protected void collectLiferayFeatures(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		portletModel.setActionTimeout(
			GetterUtil.getInteger(
				get(serviceReference, "action-timeout"),
				portletModel.getActionTimeout()));
		portletModel.setActionURLRedirect(
			GetterUtil.getBoolean(
				get(serviceReference, "action-url-redirect"),
				portletModel.getActionURLRedirect()));
		portletModel.setActive(
			GetterUtil.getBoolean(
				get(serviceReference, "active"), portletModel.isActive()));
		portletModel.setAddDefaultResource(
			GetterUtil.getBoolean(
				get(serviceReference, "add-default-resource"),
				portletModel.isAddDefaultResource()));
		portletModel.setAjaxable(
			GetterUtil.getBoolean(
				get(serviceReference, "ajaxable"), portletModel.isAjaxable()));

		Set<String> autopropagatedParameters = SetUtil.fromCollection(
			StringPlus.asList(
				get(serviceReference, "autopropagated-parameters")));

		portletModel.setAutopropagatedParameters(autopropagatedParameters);

		String controlPanelEntryCategory = GetterUtil.getString(
			get(serviceReference, "control-panel-entry-category"),
			portletModel.getControlPanelEntryCategory());

		if (Validator.equals(controlPanelEntryCategory, "content")) {
			controlPanelEntryCategory =
				PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT;
		}
		else if (Validator.equals(controlPanelEntryCategory, "marketplace")) {
			controlPanelEntryCategory = PortletCategoryKeys.APPS;
		}
		else if (Validator.equals(controlPanelEntryCategory, "portal")) {
			controlPanelEntryCategory = PortletCategoryKeys.USERS;
		}
		else if (Validator.equals(controlPanelEntryCategory, "server")) {
			controlPanelEntryCategory = PortletCategoryKeys.APPS;
		}

		portletModel.setControlPanelEntryCategory(controlPanelEntryCategory);

		portletModel.setControlPanelEntryWeight(
			GetterUtil.getDouble(
				get(serviceReference, "control-panel-entry-weight"),
				portletModel.getControlPanelEntryWeight()));
		portletModel.setCssClassWrapper(
			GetterUtil.getString(
				get(serviceReference, "css-class-wrapper"),
				portletModel.getCssClassWrapper()));
		portletModel.setFacebookIntegration(
			GetterUtil.getString(
				get(serviceReference, "facebook-integration"),
				portletModel.getFacebookIntegration()));
		portletModel.setFooterPortalCss(
			StringPlus.asList(get(serviceReference, "footer-portal-css")));
		portletModel.setFooterPortalJavaScript(
			StringPlus.asList(
				get(serviceReference, "footer-portal-javascript")));
		portletModel.setFooterPortletCss(
			StringPlus.asList(get(serviceReference, "footer-portlet-css")));
		portletModel.setFooterPortletJavaScript(
			StringPlus.asList(
				get(serviceReference, "footer-portlet-javascript")));
		portletModel.setFriendlyURLMapping(
			GetterUtil.getString(
				get(serviceReference, "friendly-url-mapping"),
				portletModel.getFriendlyURLMapping()));
		portletModel.setFriendlyURLRoutes(
			GetterUtil.getString(
				get(serviceReference, "friendly-url-routes"),
				portletModel.getFriendlyURLRoutes()));
		portletModel.setHeaderPortalCss(
			StringPlus.asList(get(serviceReference, "header-portal-css")));
		portletModel.setHeaderPortalJavaScript(
			StringPlus.asList(
				get(serviceReference, "header-portal-javascript")));
		portletModel.setHeaderPortletCss(
			StringPlus.asList(get(serviceReference, "header-portlet-css")));
		portletModel.setHeaderPortletJavaScript(
			StringPlus.asList(
				get(serviceReference, "header-portlet-javascript")));
		portletModel.setIcon(
			GetterUtil.getString(
				get(serviceReference, "icon"), portletModel.getIcon()));
		portletModel.setInclude(
			GetterUtil.getBoolean(
				get(serviceReference, "include"), portletModel.isInclude()));
		portletModel.setInstanceable(
			GetterUtil.getBoolean(
				get(serviceReference, "instanceable"),
				portletModel.isInstanceable()));
		portletModel.setLayoutCacheable(
			GetterUtil.getBoolean(
				get(serviceReference, "layout-cacheable"),
				portletModel.isLayoutCacheable()));
		portletModel.setMaximizeEdit(
			GetterUtil.getBoolean(
				get(serviceReference, "maximize-edit"),
				portletModel.isMaximizeEdit()));
		portletModel.setMaximizeHelp(
			GetterUtil.getBoolean(
				get(serviceReference, "maximize-help"),
				portletModel.isMaximizeHelp()));
		portletModel.setParentStrutsPath(
			GetterUtil.getString(
				get(serviceReference, "parent-struts-path"),
				portletModel.getParentStrutsPath()));
		portletModel.setPopUpPrint(
			GetterUtil.getBoolean(
				get(serviceReference, "pop-up-print"),
				portletModel.isPopUpPrint()));
		portletModel.setPreferencesCompanyWide(
			GetterUtil.getBoolean(
				get(serviceReference, "preferences-company-wide"),
				portletModel.isPreferencesCompanyWide()));
		portletModel.setPreferencesOwnedByGroup(
			GetterUtil.getBoolean(
				get(serviceReference, "preferences-owned-by-group"),
				portletModel.isPreferencesOwnedByGroup()));
		portletModel.setPreferencesUniquePerLayout(
			GetterUtil.getBoolean(
				get(serviceReference, "preferences-unique-per-layout"),
				portletModel.isPreferencesUniquePerLayout()));
		portletModel.setPrivateRequestAttributes(
			GetterUtil.getBoolean(
				get(serviceReference, "private-request-attributes"),
				portletModel.isPrivateRequestAttributes()));
		portletModel.setPrivateSessionAttributes(
			GetterUtil.getBoolean(
				get(serviceReference, "private-session-attributes"),
				portletModel.isPrivateSessionAttributes()));
		portletModel.setRemoteable(
			GetterUtil.getBoolean(
				get(serviceReference, "remoteable"),
				portletModel.isRemoteable()));
		portletModel.setRenderTimeout(
			GetterUtil.getInteger(
				get(serviceReference, "render-timeout"),
				portletModel.getRenderTimeout()));
		portletModel.setRenderWeight(
			GetterUtil.getInteger(
				get(serviceReference, "render-weight"),
				portletModel.getRenderWeight()));

		if (!portletModel.isAjaxable() &&
			(portletModel.getRenderWeight() < 1)) {

			portletModel.setRenderWeight(1);
		}

		portletModel.setRequiresNamespacedParameters(
			GetterUtil.getBoolean(
				get(serviceReference, "requires-namespaced-parameters"),
				portletModel.isRequiresNamespacedParameters()));
		portletModel.setRestoreCurrentView(
			GetterUtil.getBoolean(
				get(serviceReference, "restore-current-view"),
				portletModel.isRestoreCurrentView()));
		portletModel.setScopeable(
			GetterUtil.getBoolean(
				get(serviceReference, "scopeable"),
				portletModel.isScopeable()));
		portletModel.setShowPortletAccessDenied(
			GetterUtil.getBoolean(
				get(serviceReference, "show-portlet-access-denied"),
				portletModel.isShowPortletAccessDenied()));
		portletModel.setShowPortletInactive(
			GetterUtil.getBoolean(
				get(serviceReference, "show-portlet-inactive"),
				portletModel.isShowPortletInactive()));
		portletModel.setStrutsPath(
			GetterUtil.getString(
				get(serviceReference, "struts-path"),
				portletModel.getStrutsPath()));
		portletModel.setSystem(
			GetterUtil.getBoolean(
				get(serviceReference, "system"), portletModel.isSystem()));
		portletModel.setUseDefaultTemplate(
			GetterUtil.getBoolean(
				get(serviceReference, "use-default-template"),
				portletModel.isUseDefaultTemplate()));
		portletModel.setUserPrincipalStrategy(
			GetterUtil.getString(
				get(serviceReference, "user-principal-strategy"),
				portletModel.getUserPrincipalStrategy()));
		portletModel.setVirtualPath(
			GetterUtil.getString(
				get(serviceReference, "virtual-path"),
				portletModel.getVirtualPath()));
	}

	protected void collectPortletInfo(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		String portletInfoTitle = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.info.title"),
			portletModel.getPortletId());
		String portletInfoShortTitle = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.info.short-title"),
			portletModel.getPortletId());
		String portletInfoKeyWords = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.info.keywords"),
			portletModel.getPortletId());
		String portletDescription = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.description"),
			portletModel.getPortletId());

		PortletInfo portletInfo = new PortletInfo(
			portletInfoTitle, portletInfoShortTitle, portletInfoKeyWords,
			portletDescription);

		portletModel.setPortletInfo(portletInfo);
	}

	protected void collectPortletModes(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Map<String, Set<String>> portletModes =
			new HashMap<String, Set<String>>();

		portletModes.put(
			ContentTypes.TEXT_HTML,
			SetUtil.fromArray(new String[] {toLowerCase(PortletMode.VIEW)}));

		List<String> portletModesStrings = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.portletModes"));

		for (String portletModesString : portletModesStrings) {
			String[] portletModesStringParts = StringUtil.split(
				portletModesString, CharPool.SEMICOLON);

			if (portletModesStringParts.length != 2) {
				continue;
			}

			String mimeType = portletModesStringParts[0];

			Set<String> mimeTypePortletModes = new HashSet<String>();

			mimeTypePortletModes.add(toLowerCase(PortletMode.VIEW));
			mimeTypePortletModes.addAll(
				toLowerCaseSet(portletModesStringParts[1]));

			portletModes.put(mimeType, mimeTypePortletModes);
		}

		portletModel.setPortletModes(portletModes);
	}

	protected void collectPortletPreferences(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		String defaultPreferences = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.preferences"));

		if ((defaultPreferences != null) &&
			defaultPreferences.startsWith("classpath:")) {

			Bundle bundle = serviceReference.getBundle();

			URL url = bundle.getResource(
				defaultPreferences.substring("classpath:".length()));

			if (url != null) {
				try {
					defaultPreferences = StringUtil.read(url.openStream());
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		portletModel.setDefaultPreferences(defaultPreferences);
	}

	protected void collectResourceBundle(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		String resourceBundle = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.resource-bundle"),
			portletModel.getResourceBundle());

		portletModel.setResourceBundle(resourceBundle);
	}

	protected void collectSecurityRoleRefs(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Set<String> unlinkedRoles = new HashSet<String>();

		List<String> roleRefs = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.security-role-ref"));

		for (String roleRef : roleRefs) {
			for (String curRoleRef : StringUtil.split(roleRef)) {
				unlinkedRoles.add(curRoleRef);
			}
		}

		portletModel.setUnlinkedRoles(unlinkedRoles);

		portletModel.linkRoles();
	}

	protected void collectSupportedProcessingEvents(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Set<QName> processingEvents = new HashSet<QName>();

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> supportedProcessingEvents = StringPlus.asList(
			serviceReference.getProperty(
				"javax.portlet.supported-processing-event"));

		for (String supportedProcessingEvent : supportedProcessingEvents) {
			String name = supportedProcessingEvent;
			String qname = null;

			String[] parts = StringUtil.split(
				supportedProcessingEvent, StringPool.SEMICOLON);

			if (parts.length == 2) {
				name = parts[0];
				qname = parts[1];
			}

			QName qName = getQName(
				name, qname, portletApp.getDefaultNamespace());

			processingEvents.add(qName);

			Set<EventDefinition> eventDefinitions =
				portletApp.getEventDefinitions();

			for (EventDefinition eventDefinition : eventDefinitions) {
				Set<QName> qNames = eventDefinition.getQNames();

				if (qNames.contains(qName)) {
					processingEvents.addAll(qNames);
				}
			}
		}

		portletModel.setProcessingEvents(processingEvents);
	}

	protected void collectSupportedPublicRenderParameters(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Set<PublicRenderParameter> publicRenderParameters =
			new HashSet<PublicRenderParameter>();

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> supportedPublicRenderParameters = StringPlus.asList(
			serviceReference.getProperty(
				"javax.portlet.supported-public-render-parameter"));

		for (String identifier : supportedPublicRenderParameters) {
			PublicRenderParameter publicRenderParameter =
				portletApp.getPublicRenderParameter(identifier);

			if (publicRenderParameter == null) {
				_log.error(
					"Supported public render parameter references unknown " +
						"identifier " + identifier);

				continue;
			}

			publicRenderParameters.add(publicRenderParameter);
		}

		portletModel.setPublicRenderParameters(publicRenderParameters);
	}

	protected void collectSupportedPublishingEvents(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Set<QName> publishingEvents = new HashSet<QName>();

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> supportedPublishingEvents = StringPlus.asList(
			serviceReference.getProperty(
				"javax.portlet.supported-publishing-event"));

		for (String supportedPublishingEvent : supportedPublishingEvents) {
			String name = supportedPublishingEvent;
			String qname = null;

			String[] parts = StringUtil.split(
				supportedPublishingEvent, StringPool.SEMICOLON);

			if (parts.length == 2) {
				name = parts[0];
				qname = parts[1];
			}

			QName qName = getQName(
				name, qname, portletApp.getDefaultNamespace());

			publishingEvents.add(qName);
		}

		portletModel.setPublishingEvents(publishingEvents);
	}

	protected void collectWindowStates(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Map<String, Set<String>> windowStates =
			new HashMap<String, Set<String>>();

		windowStates.put(
			ContentTypes.TEXT_HTML,
			SetUtil.fromArray(
				new String[] {
					toLowerCase(LiferayWindowState.EXCLUSIVE),
					toLowerCase(LiferayWindowState.POP_UP),
					toLowerCase(WindowState.MAXIMIZED),
					toLowerCase(WindowState.MINIMIZED),
					toLowerCase(WindowState.NORMAL)
				}));

		List<String> windowStatesStrings = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.windowStates"));

		for (String windowStatesString : windowStatesStrings) {
			String[] windowStatesStringParts = StringUtil.split(
				windowStatesString, CharPool.SEMICOLON);

			if (windowStatesStringParts.length != 2) {
				continue;
			}

			String mimeType = windowStatesStringParts[0];

			Set<String> mimeTypeWindowStates = new HashSet<String>();

			mimeTypeWindowStates.add(toLowerCase(WindowState.NORMAL));

			Set<String> windowStatesSet = toLowerCaseSet(
				windowStatesStringParts[1]);

			if (windowStatesSet.isEmpty()) {
				mimeTypeWindowStates.add(
					toLowerCase(LiferayWindowState.EXCLUSIVE));
				mimeTypeWindowStates.add(
					toLowerCase(LiferayWindowState.POP_UP));
				mimeTypeWindowStates.add(toLowerCase(WindowState.MAXIMIZED));
				mimeTypeWindowStates.add(toLowerCase(WindowState.MINIMIZED));
			}
			else {
				mimeTypeWindowStates.addAll(windowStatesSet);
			}

			windowStates.put(mimeType, mimeTypeWindowStates);
		}

		portletModel.setWindowStates(windowStates);
	}

	protected BundlePortletApp createBundlePortletApp(
		Bundle bundle, ClassLoader classLoader,
		ServiceRegistrations serviceRegistrations) {

		BundleContext bundleContext = bundle.getBundleContext();

		if (serviceRegistrations._bundlePortletAppServiceRegistration != null) {
			ServiceReference<?> serviceReference =
				serviceRegistrations.
					_bundlePortletAppServiceRegistration.getReference();

			return (BundlePortletApp)bundleContext.getService(serviceReference);
		}

		com.liferay.portal.model.Portlet portalPortletModel =
			_portletLocalService.getPortletById(
				CompanyConstants.SYSTEM, PortletKeys.PORTAL);

		String contextName = String.valueOf(bundle.getBundleId());

		String contextPath = "/".concat(contextName);

		BundlePortletApp bundlePortletApp = new BundlePortletApp(
			bundle, portalPortletModel, contextName, contextPath,
			_httpServiceEndpoints.get(0));

		ServletContext servletContext =
			(ServletContext)ProxyUtil.newProxyInstance(
				classLoader, new Class<?>[] {ServletContext.class},
				new BundleServletContextInvocationHandler(
					_servletContext, bundlePortletApp, classLoader));

		bundlePortletApp.setServletContext(servletContext);

		serviceRegistrations._configuration =
			ConfigurationFactoryUtil.getConfiguration(classLoader, "portlet");

		readResourceActions(
			serviceRegistrations._configuration,
			bundlePortletApp.getServletContextName(), classLoader);

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, contextPath);

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				ServletContextHelper.class, bundlePortletApp, properties);

		serviceRegistrations._bundlePortletAppServiceRegistration =
			serviceRegistration;

		return bundlePortletApp;
	}

	protected void createJspServlet(
		PortletApp portletApp, BundleContext bundleContext,
		ServiceRegistrations serviceRegistrations) {

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			portletApp.getServletContextName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "jsp");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "*.jsp");
		properties.put(
			"servlet.init.compilerClassName",
			"com.liferay.portal.servlet.jsp.compiler.compiler.JspCompiler");
		properties.put("servlet.init.httpMethods", "GET,POST,HEAD");
		properties.put("servlet.init.keepgenerated", "true");
		properties.put("servlet.init.logVerbosityLevel", "DEBUG");

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				Servlet.class, new JspServlet(), properties);

		serviceRegistrations._jspServletServiceRegistration =
			serviceRegistration;
	}

	protected void createStaticResourceServlet(
		PortletApp portletApp, BundleContext bundleContext,
		ServiceRegistrations serviceRegistrations) {

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			portletApp.getServletContextName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX,
			"/META-INF/resources");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

		ServiceRegistration<Servlet> serviceRegistration =
			bundleContext.registerService(
				Servlet.class, new HttpServlet() {}, properties);

		serviceRegistrations._staticResourcesServletServiceRegistration =
			serviceRegistration;
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;

		_componentContext = null;

		if (_log.isInfoEnabled()) {
			_log.info("Deactivated");
		}
	}

	protected void deployPortlet(
			ServiceReference<Portlet> serviceReference,
			com.liferay.portal.model.Portlet portletModel,
			List<Company> companies)
		throws PortalException {

		String categoryName = (String)serviceReference.getProperty(
			"com.liferay.portal.portlet.display.category");

		if (categoryName == null) {
			categoryName = "category.undefined";
		}

		for (Company company : companies) {
			com.liferay.portal.model.Portlet companyPortletModel =
				(com.liferay.portal.model.Portlet)portletModel.clone();

			companyPortletModel.setCompanyId(company.getCompanyId());

			_portletLocalService.deployRemotePortlet(
				companyPortletModel, new String[] {categoryName}, false);
		}
	}

	protected Object get(
		ServiceReference<Portlet> serviceReference, String property) {

		return serviceReference.getProperty(_NAMESPACE + property);
	}

	protected QName getQName(String name, String uri, String defaultNamespace) {
		if (Validator.isNull(name) && Validator.isNull(uri)) {
			return null;
		}

		if (Validator.isNull(uri)) {
			return SAXReaderUtil.createQName(
				name, SAXReaderUtil.createNamespace(defaultNamespace));
		}

		return SAXReaderUtil.createQName(
			name, SAXReaderUtil.createNamespace(uri));
	}

	protected ServiceRegistrations getServiceRegistrations(Bundle bundle) {
		ServiceRegistrations serviceRegistrations = _serviceRegistrations.get(
			bundle);

		if (serviceRegistrations == null) {
			serviceRegistrations = new ServiceRegistrations();

			_serviceRegistrations.put(bundle, serviceRegistrations);
		}

		return serviceRegistrations;
	}

	protected void readResourceActions(
		Configuration configuration, String servletContextName,
		ClassLoader classLoader) {

		Properties properties = configuration.getProperties();

		String[] resourceActionConfigs = StringUtil.split(
			properties.getProperty(PropsKeys.RESOURCE_ACTIONS_CONFIGS));

		for (String resourceActionConfig : resourceActionConfigs) {
			try {
				ResourceActionsUtil.read(
					servletContextName, classLoader, resourceActionConfig);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Reference
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference
	protected void setHttpServiceRuntime(
		HttpServiceRuntime httpServiceRuntime, Map<String, Object> properties) {

		_httpServiceEndpoints = StringPlus.asList(
			properties.get(
				HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT_ATTRIBUTE));
	}

	@Reference
	protected void setPortletInstanceFactory(
		PortletInstanceFactory portletInstanceFactory) {

		_portletInstanceFactory = portletInstanceFactory;
	}

	@Reference
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference
	protected void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Reference
	protected void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Reference(
		target = "(original.bean=*)"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected String toLowerCase(Object object) {
		String string = String.valueOf(object);

		return StringUtil.toLowerCase(string.trim());
	}

	protected Set<String> toLowerCaseSet(String string) {
		String[] array = StringUtil.split(string);

		for (int i = 0; i < array.length; i++) {
			array[i] = toLowerCase(array[i]);
		}

		return SetUtil.fromArray(array);
	}

	protected void unsetCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = null;
	}

	protected void unsetHttpServiceRuntime(
		HttpServiceRuntime httpServiceRuntime, Map<String, Object> properties) {

		_httpServiceEndpoints = null;
	}

	protected void unsetPortletInstanceFactory(
		PortletInstanceFactory portletInstanceFactory) {

		_portletInstanceFactory = null;
	}

	protected void unsetResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = null;
	}

	protected void unsetResourceActions(ResourceActions resourceActions) {
		_resourceActions = null;
	}

	protected void unsetServletContext(ServletContext servletContext) {
		_servletContext = null;
	}

	private static final String _NAMESPACE = "com.liferay.portlet.";

	private static final int _PORTLET_ID_MAX_LENGTH =
		255 - PortletConstants.INSTANCE_SEPARATOR.length() +
			PortletConstants.USER_SEPARATOR.length() + 39;

	private static Log _log = LogFactoryUtil.getLog(PortletTracker.class);

	private CompanyLocalService _companyLocalService;
	private ComponentContext _componentContext;
	private List<String> _httpServiceEndpoints;
	private PortletInstanceFactory _portletInstanceFactory;
	private PortletLocalService _portletLocalService;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourceActions _resourceActions;
	private Map<Bundle, ServiceRegistrations> _serviceRegistrations =
		new ConcurrentHashMap<Bundle, ServiceRegistrations>();
	private ServiceTracker<Portlet, com.liferay.portal.model.Portlet>
		_serviceTracker;
	private ServletContext _servletContext;

	private class ServiceRegistrations {

		private ServiceRegistration<?> _bundlePortletAppServiceRegistration;
		private Configuration _configuration;
		private int _counter;
		private ServiceRegistration<?> _jspServletServiceRegistration;
		private ServiceRegistration<?>
			_staticResourcesServletServiceRegistration;

	}

}