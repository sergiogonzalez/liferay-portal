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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NumericalStringComparator;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.InitUtil;

import java.io.File;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class PluginsSummaryBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		File pluginsDir = new File(System.getProperty("plugins.dir"));

		new PluginsSummaryBuilder(pluginsDir);
	}

	public PluginsSummaryBuilder(File pluginsDir) {
		try {
			_pluginsDir = pluginsDir;

			_createPluginsSummary();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _createPluginsSummary() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_pluginsDir);
		directoryScanner.setExcludes(
			new String[] {"**\\tmp\\**", "**\\tools\\**"});
		directoryScanner.setIncludes(
			new String[] {
				"**\\liferay-plugin-package.properties"
			});

		directoryScanner.scan();

		String[] fileNames = directoryScanner.getIncludedFiles();

		Arrays.sort(fileNames);

		_createPluginsSummary(fileNames);
	}

	private void _createPluginsSummary(String[] fileNames) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("<plugins-summary>\n");

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			_createPluginSummary(sb, fileName);
		}

		for (String author : _distinctAuthors) {
			sb.append("\t<author>");
			sb.append(author);
			sb.append("</author>\n");
		}

		for (String license : _distinctLicenses) {
			sb.append("\t<license>");
			sb.append(license);
			sb.append("</license>\n");
		}

		sb.append("</plugins-summary>");

		FileUtil.write(_pluginsDir + "/summary.xml", sb.toString());
	}

	private void _createPluginSummary(StringBundler sb, String fileName)
		throws Exception {

		String content = FileUtil.read(fileName);

		int x = fileName.indexOf(StringPool.SLASH);

		String type = fileName.substring(0, x);

		if (type.endsWith("s")) {
			type = type.substring(0, type.length() - 1);
		}

		x = fileName.indexOf(StringPool.SLASH, x) + 1;

		int y = fileName.indexOf(StringPool.SLASH, x);

		String artifactId = fileName.substring(x, y);

		Properties properties = PropertiesUtil.load(content);

		String name = _readProperty(properties, "name");
		String tags = _readProperty(properties, "tags");
		String shortDescription = _readProperty(
			properties, "short-description");
		String longDescription = _readProperty(properties, "long-description");
		String changeLog = _readProperty(properties, "change-log");
		String pageURL = _readProperty(properties, "page-url");
		String author = _readProperty(properties, "author");
		String licenses = _readProperty(properties, "licenses");
		String liferayVersions = _readProperty(properties, "liferay-versions");

		_distinctAuthors.add(author);
		_distinctLicenses.add(licenses);

		sb.append("\t<plugin>\n");

		_writeElement(sb, "artifact-id", artifactId, 2);
		_writeElement(sb, "name", name, 2);
		_writeElement(sb, "type", type, 2);
		_writeElement(sb, "tags", tags, 2);
		_writeElement(sb, "short-description", shortDescription, 2);
		_writeElement(sb, "long-description", longDescription, 2);
		_writeElement(sb, "change-log", changeLog, 2);
		_writeElement(sb, "page-url", pageURL, 2);
		_writeElement(sb, "author", author, 2);
		_writeElement(sb, "licenses", licenses, 2);
		_writeElement(sb, "liferay-versions", liferayVersions, 2);

		sb.append("\t\t<releng>\n");
		sb.append(_readReleng(fileName, properties));
		sb.append("\t\t</releng>\n");
		sb.append("\t</plugin>\n");
	}

	private Set<String> _extractTicketIds(File pluginDir, String range)
		throws Exception {

		Set<String> ticketIds = new TreeSet<String>(
			new NumericalStringComparator()
		);

		Runtime runtime = Runtime.getRuntime();

		String command = "git log " + range + " .";

		if (OSDetector.isWindows()) {
			command = "cmd /c " + command;
		}

		Process process = runtime.exec(command, null, pluginDir);

		String content = StringUtil.read(process.getInputStream());

		content = StringUtil.replace(content, "\n", " ");

		for (String ticketIdPrefix : _TICKET_ID_PREFIXES) {
			int x = 0;

			while (true) {
				x = content.indexOf(ticketIdPrefix + "-", x);

				if (x == -1) {
					break;
				}

				int y = x + ticketIdPrefix.length() + 1;

				while (true) {
					if ((y + 1) > content.length()) {
						break;
					}

					if (Character.isDigit(content.charAt(y))) {
						y++;
					}
					else {
						break;
					}
				}

				String ticketId = content.substring(x, y);

				ticketIds.add(ticketId);

				x = y;
			}
		}

		return ticketIds;
	}

	private String _getChangeLogEntry(
		int changeLogVersion, String range, String ticketIdsString) {

		StringBundler sb = new StringBundler(8);

		if (changeLogVersion > 1) {
			sb.append("\n\n");
		}

		sb.append("#\n");
		sb.append("# Module Incremental Version ");
		sb.append(changeLogVersion);
		sb.append("\n#\n");
		sb.append(range);
		sb.append("=");
		sb.append(ticketIdsString);

		return sb.toString();
	}

	private String _readProperty(Properties properties, String key) {
		return GetterUtil.getString(properties.getProperty(key));
	}

	private String _readReleng(
			String fileName, Properties pluginPackageProperties)
		throws Exception {

		int x = fileName.indexOf("WEB-INF");

		String relativeWebInfDirName = fileName.substring(0, x + 8);

		String fullWebInfDirName =
			_pluginsDir + StringPool.SLASH + relativeWebInfDirName;

		String relengPropertiesFileName =
			fullWebInfDirName + "liferay-releng.properties";

		Properties relengProperties = null;

		if (FileUtil.exists(relengPropertiesFileName)) {
			String relengPropertiesContent = FileUtil.read(
				relengPropertiesFileName);

			relengProperties = PropertiesUtil.load(relengPropertiesContent);
		}
		else {
			relengProperties = new Properties();
		}

		String relengPropertiesContent = _updateRelengPropertiesFile(
			relengPropertiesFileName, relengProperties);

		relengProperties = PropertiesUtil.load(relengPropertiesContent);

		StringBundler sb = new StringBundler();

		_writeElement(sb, "bundle", relengProperties, 3);
		_writeElement(sb, "category", relengProperties, 3);
		_writeElement(sb, "demo-url", relengProperties, 3);
		_writeElement(sb, "dependent-apps", relengProperties, 3);

		if (FileUtil.exists(fullWebInfDirName + "releng/icons/90x90.png")) {
			_writeElement(
				sb, "icon", relativeWebInfDirName + "releng/icons/90x90.png",
				3);
		}

		_writeElement(sb, "labs", relengProperties, 3);
		_writeElement(sb, "marketplace", relengProperties, 3);
		_writeElement(sb, "public", relengProperties, 3);

		String fullScreenshotsDirName =
			fullWebInfDirName + "releng/screenshots/";
		String relativeScreenshotsDirName =
			relativeWebInfDirName + "releng/screenshots/";

		if (FileUtil.exists(fullScreenshotsDirName)) {
			String[] screenshotsFileNames = FileUtil.listFiles(
				fullScreenshotsDirName);

			Arrays.sort(screenshotsFileNames);

			for (String screenshotsFileName : screenshotsFileNames) {
				if (screenshotsFileName.equals("Thumbs.db") ||
					screenshotsFileName.endsWith(".png")) {

					FileUtil.delete(
						fullScreenshotsDirName + screenshotsFileName);
				}

				if (!screenshotsFileName.endsWith(".jpg")) {
					continue;
				}

				_writeElement(
					sb, "screenshot",
					relativeScreenshotsDirName + screenshotsFileName, 3);
			}
		}

		_writeElement(sb, "support-url", relengProperties, 3);
		_writeElement(sb, "supported", relengProperties, 3);

		File relengChangeLogFile = new File(
			fullWebInfDirName + "liferay-releng.changelog");

		if (GetterUtil.getBoolean(
				relengProperties.getProperty("marketplace"))) {

			_updateRelengChangeLogFile(
				pluginPackageProperties, relengChangeLogFile, relengProperties);
		}
		else {
			relengChangeLogFile.delete();
		}

		return sb.toString();
	}

	private void _updateRelengChangeLogFile(
			Properties pluginPackageProperties, File relengChangeLogFile,
			Properties relengProperties)
		throws Exception {

		StringBundler sb = new StringBundler();

		int changeLogVersion = 0;

		int moduleIncrementalVersion = GetterUtil.getInteger(
			pluginPackageProperties.getProperty("module-incremental-version"));

		if (!relengChangeLogFile.exists()) {
			FileUtil.write(relengChangeLogFile, "HEAD=");
		}

		String relengChangeLogContent = FileUtil.read(relengChangeLogFile);

		String[] relengChangeLogEntries = StringUtil.split(
			relengChangeLogContent, "\n");

		for (int i = 0; i < relengChangeLogEntries.length; i++) {
			String relengChangeLogEntry = relengChangeLogEntries[i];

			if (Validator.isNull(relengChangeLogEntry) ||
				relengChangeLogEntry.startsWith("#")) {

				continue;
			}

			String[] relengChangeLogEntryParts = StringUtil.split(
				relengChangeLogEntry, "=");

			String range = relengChangeLogEntryParts[0];

			if (range.equals("HEAD")) {
				changeLogVersion++;

				sb.append(
					_getChangeLogEntry(
						changeLogVersion, range, StringPool.BLANK));

				continue;
			}

			File webInfDir = relengChangeLogFile.getParentFile();

			File docrootDir = webInfDir.getParentFile();

			File pluginDir = docrootDir.getParentFile();

			Set<String> ticketIds = _extractTicketIds(pluginDir, range);

			String[] dependentApps = StringUtil.split(
				relengProperties.getProperty("dependent-apps"));

			for (String dependentApp : dependentApps) {
				dependentApp = dependentApp.trim();

				String dependentAppDirName = null;

				if (dependentApp.endsWith("-hook")) {
					dependentAppDirName = "hooks";
				}
				else if (dependentApp.endsWith("-layouttpl")) {
					dependentAppDirName = "layouttpl";
				}
				else if (dependentApp.endsWith("-portlet")) {
					dependentAppDirName = "portlets";
				}
				else if (dependentApp.endsWith("-theme")) {
					dependentAppDirName = "themes";
				}
				else if (dependentApp.endsWith("-web")) {
					dependentAppDirName = "webs";
				}

				File dependentAppDir = new File(
					_pluginsDir, dependentAppDirName + "/" + dependentApp);

				if (!dependentAppDir.exists()) {
					throw new RuntimeException(
						dependentAppDir + " does not exist");
				}

				ticketIds.addAll(_extractTicketIds(dependentAppDir, range));

				/*File dependentAppPluginPackagePropertiesFile = new File(
					dependentAppDir,
					"docroot/WEB-INF/liferay-plugin-package.properties");

				String dependentAppPluginPackagePropertiesContent =
					FileUtil.read(dependentAppPluginPackagePropertiesFile);

				Properties dependentAppPluginPackageProperties =
					PropertiesUtil.load(
						dependentAppPluginPackagePropertiesContent);

				int dependentAppModuleIncrementalVersion =
					GetterUtil.getInteger(
						dependentAppPluginPackageProperties.getProperty(
							"module-incremental-version"));

				if (moduleIncrementalVersion
						!= dependentAppModuleIncrementalVersion) {

					StringBundler msgSB = new StringBundler(5);

					msgSB.append("liferay-plugin-package.properties in ");
					msgSB.append(webInfDir);
					msgSB.append(" and ");
					msgSB.append(
						dependentAppPluginPackagePropertiesFile.getParent());
					msgSB.append(" do not have the same incremental version");

					throw new RuntimeException(msgSB.toString());
				}*/
			}

			String ticketIdsString = StringUtil.merge(
				ticketIds.toArray(new String[ticketIds.size()]), " ");

			changeLogVersion++;

			sb.append(
				_getChangeLogEntry(changeLogVersion, range, ticketIdsString));
		}

		if (moduleIncrementalVersion != changeLogVersion) {
			StringBundler msgSB = new StringBundler(4);

			msgSB.append("liferay-plugin-package.properties and ");
			msgSB.append("liferay-releng.changelog in ");
			msgSB.append(relengChangeLogFile.getParent());
			msgSB.append(" do not have the same incremental version");

			throw new RuntimeException(msgSB.toString());
		}

		FileUtil.write(relengChangeLogFile, sb.toString());
	}

	private String _updateRelengPropertiesFile(
			String relengPropertiesFileName, Properties relengProperties)
		throws Exception {

		StringBundler sb = new StringBundler();

		_writeProperty(sb, relengProperties, "bundle", "false");
		_writeProperty(sb, relengProperties, "category", "");
		_writeProperty(sb, relengProperties, "demo-url", "");
		_writeProperty(sb, relengProperties, "dependent-apps", "");
		_writeProperty(sb, relengProperties, "labs", "true");
		_writeProperty(sb, relengProperties, "marketplace", "false");
		_writeProperty(sb, relengProperties, "public", "true");
		_writeProperty(sb, relengProperties, "support-url", "");
		_writeProperty(sb, relengProperties, "supported", "false");

		String relengPropertiesContent = sb.toString();

		FileUtil.write(relengPropertiesFileName, relengPropertiesContent);

		return relengPropertiesContent;
	}

	private void _writeElement(
		StringBundler sb, String name, Properties properties, int tabsCount) {

		_writeElement(sb, name, _readProperty(properties, name), tabsCount);
	}

	private void _writeElement(
		StringBundler sb, String name, String value, int tabsCount) {

		for (int i = 0; i < tabsCount; i++) {
			sb.append("\t");
		}

		sb.append("<");
		sb.append(name);
		sb.append("><![CDATA[");
		sb.append(value);
		sb.append("]]></");
		sb.append(name);
		sb.append(">\n");
	}

	private void _writeProperty(
		StringBundler sb, Properties properties, String key,
		String defaultValue) {

		String value = GetterUtil.getString(
			properties.getProperty(key), defaultValue);

		if (sb.length() > 0) {
			sb.append(StringPool.NEW_LINE);
		}

		sb.append(key);
		sb.append(StringPool.EQUAL);
		sb.append(value);
	}

	private static final String[] _TICKET_ID_PREFIXES = {"LPS", "SOS"};

	private Set<String> _distinctAuthors = new TreeSet<String>();
	private Set<String> _distinctLicenses = new TreeSet<String>();
	private File _pluginsDir;

}