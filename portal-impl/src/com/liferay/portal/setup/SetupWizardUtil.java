/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.setup;

import com.liferay.portal.dao.jdbc.util.DataSourceSwapper;
import com.liferay.portal.events.StartupAction;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.service.QuartzLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Manuel de la Peña
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class SetupWizardUtil {

	public static boolean isSetupFinished(HttpServletRequest request) {
		if (!PropsValues.SETUP_WIZARD_ENABLED) {
			return true;
		}

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		Boolean setupWizardFinished = (Boolean)servletContext.getAttribute(
			WebKeys.SETUP_WIZARD_FINISHED);

		if (setupWizardFinished != null) {
			return setupWizardFinished;
		}

		return true;
	}

	public static void processSetup(HttpServletRequest request)
		throws Exception {

		UnicodeProperties unicodeProperties =
			PropertiesParamUtil.getProperties(request, _PROPERTIES_PREFIX);

		_processAdminProperties(request, unicodeProperties);
		_processDatabaseProperties(request, unicodeProperties);

		unicodeProperties.put(
			PropsKeys.SETUP_WIZARD_ENABLED, String.valueOf(false));

		HttpSession session = request.getSession();

		session.setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES, unicodeProperties);

		boolean updatedPropertiesFile = _writePropertiesFile(unicodeProperties);

		session.setAttribute(
			WebKeys.SETUP_WIZARD_PROPERTIES_UPDATED, updatedPropertiesFile);

		_reloadServletContext(request, unicodeProperties);
		_resetAdminPassword(request);
	}

	private static String _getParameter(
		HttpServletRequest request, String name, String defaultValue) {

		name = _PROPERTIES_PREFIX.concat(name).concat(StringPool.DOUBLE_DASH);

		return ParamUtil.getString(request, name);
	}

	private static void _processAdminProperties(
			HttpServletRequest request, UnicodeProperties unicodeProperties)
		throws Exception {

		String webId = _getParameter(
			request, PropsKeys.COMPANY_DEFAULT_WEB_ID, "liferay.com");
		PropsValues.COMPANY_DEFAULT_WEB_ID = webId;

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String firstName = _getParameter(
			request, PropsKeys.DEFAULT_ADMIN_FIRST_NAME, "Test");

		PropsValues.DEFAULT_ADMIN_FIRST_NAME = firstName;

		String lastName = _getParameter(
			request, PropsKeys.DEFAULT_ADMIN_LAST_NAME, "Test");

		PropsValues.DEFAULT_ADMIN_LAST_NAME = lastName;

		String adminEmailFromName = fullNameGenerator.getFullName(
			firstName, null, lastName);

		PropsValues.ADMIN_EMAIL_FROM_NAME = adminEmailFromName;

		unicodeProperties.put(
			PropsKeys.ADMIN_EMAIL_FROM_NAME, adminEmailFromName);

		String defaultAdminEmailAddress = _getParameter(
			request, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.com");

		unicodeProperties.put(
			PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS, defaultAdminEmailAddress);

		PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS = defaultAdminEmailAddress;

		ScreenNameGenerator screenNameGenerator =
			ScreenNameGeneratorFactory.getInstance();

		String defaultAdminScreenName = screenNameGenerator.generate(
			0, 0, "test");

		PropsValues.DEFAULT_ADMIN_SCREEN_NAME = defaultAdminScreenName;

		unicodeProperties.put(
			PropsKeys.DEFAULT_ADMIN_SCREEN_NAME, defaultAdminScreenName);
	}

	private static void _processDatabaseProperties(
			HttpServletRequest request, UnicodeProperties unicodeProperties)
		throws Exception {

		boolean defaultDatabase = ParamUtil.getBoolean(
			request, "defaultDatabase", true);

		if (defaultDatabase) {
			return;
		}

		String jdbcDefaultDriverClassName = null;
		String jdbcDefaultURL = null;

		String databaseType = ParamUtil.getString(
			request, "databaseType", "hsql");
		String databaseName = ParamUtil.getString(
			request, "databaseName", "lportal");

		if (databaseType.equals("db2")) {
			jdbcDefaultDriverClassName = "com.ibm.db2.jcc.DB2Driver";

			StringBundler sb = new StringBundler(5);

			sb.append("jdbc:db2://localhost:50000/");
			sb.append(databaseName);
			sb.append(":deferPrepares=false;fullyMaterializeInputStreams=");
			sb.append("true;fullyMaterializeLobData=true;");
			sb.append("progresssiveLocators=2;progressiveStreaming=2;");

			jdbcDefaultURL = sb.toString();
		}
		else if (databaseType.equals("derby")) {
			jdbcDefaultDriverClassName = "org.apache.derby.jdbc.EmbeddedDriver";
			jdbcDefaultURL = "jdbc:derby:" + databaseName;
		}
		else if (databaseType.equals("ingres")) {
			jdbcDefaultDriverClassName = "com.ingres.jdbc.IngresDriver";
			jdbcDefaultURL = "jdbc:ingres://localhost:II7/" + databaseName;
		}
		else if (databaseType.equals("mysql")) {
			jdbcDefaultDriverClassName = "com.mysql.jdbc.Driver";
			jdbcDefaultURL =
				"jdbc:mysql://localhost/" + databaseName +
					"?useUnicode=true&characterEncoding=UTF-8&" +
						"useFastDateParsing=false";
		}
		else if (databaseType.equals("oracle")) {
			jdbcDefaultDriverClassName =
				"oracle.jdbc.this.get_driver().OracleDriver";
			jdbcDefaultURL = "jdbc:oracle:thin:@localhost:1521:xe";
		}
		else if (databaseType.equals("p6spy")) {
			jdbcDefaultDriverClassName = "com.p6spy.engine.spy.P6SpyDriver";
			jdbcDefaultURL =
				"jdbc:mysql://localhost/" + databaseName +
					"?useUnicode=true&characterEncoding=UTF-8" +
						"&useFastDateParsing=false";
		}
		else if (databaseType.equals("postgresql")) {
			jdbcDefaultDriverClassName = "org.postgresql.Driver";
			jdbcDefaultURL = "jdbc:postgresql://localhost:5432/" + databaseName;
		}
		else if (databaseType.equals("sqlserver")) {
			jdbcDefaultDriverClassName = "net.sourceforge.jtds.jdbc.Driver";
			jdbcDefaultURL = "jdbc:jtds:sqlserver://localhost/" + databaseName;
		}
		else if (databaseType.equals("sybase")) {
			jdbcDefaultDriverClassName = "net.sourceforge.jtds.jdbc.Driver";
			jdbcDefaultURL =
				"jdbc:jtds:sybase://localhost:5000/" + databaseName;
		}

		unicodeProperties.put(
			PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME,
			jdbcDefaultDriverClassName);
		unicodeProperties.put(PropsKeys.JDBC_DEFAULT_URL, jdbcDefaultURL);
	}

	private static void _reloadServletContext(
			HttpServletRequest request, UnicodeProperties unicodeProperties)
		throws Exception {

		// Data sources

		Properties jdbcProperties = new Properties();

		jdbcProperties.putAll(unicodeProperties);

		jdbcProperties = PropertiesUtil.getProperties(
			jdbcProperties,"jdbc.default.",true);

		DataSourceSwapper.swapCounterDataSource(jdbcProperties);
		DataSourceSwapper.swapLiferayDataSource(jdbcProperties);

		// Caches

		CacheRegistryUtil.clear();
		MultiVMPoolUtil.clear();
		WebCachePoolUtil.clear();
		CentralizedThreadLocal.clearShortLivedThreadLocals();

		// Startup

		QuartzLocalServiceUtil.checkQuartzTables();

		StartupAction startupAction = new StartupAction();

		startupAction.run(null);

		HttpSession session = request.getSession();

		PortalInstances.reload(session.getServletContext());
	}

	private static void _resetAdminPassword(HttpServletRequest request)
		throws Exception {

		String defaultAdminEmailAddress = _getParameter(
			request, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, "test@liferay.com");

		User user = UserLocalServiceUtil.getUserByEmailAddress(
			PortalUtil.getDefaultCompanyId(), defaultAdminEmailAddress);

		UserLocalServiceUtil.updatePasswordReset(user.getUserId(), true);

		HttpSession session = request.getSession();

		session.setAttribute(WebKeys.SETUP_WIZARD_PASSWORD_UPDATED, true);
	}

	private static boolean _writePropertiesFile(
		UnicodeProperties unicodeProperties) {

		try {
			FileUtil.write(
				PropsValues.LIFERAY_HOME, _PROPERTIES_FILE_NAME,
				unicodeProperties.toString());

			return true;
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			return false;
		}
	}

	private static final String _PROPERTIES_FILE_NAME =
		"portal-setup-wizard.properties";

	private final static String _PROPERTIES_PREFIX = "properties--";

	private static Log _log = LogFactoryUtil.getLog(SetupWizardUtil.class);

}