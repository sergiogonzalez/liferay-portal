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

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.LiferayAntPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayRelengPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.tasks.ReplaceRegexTask;

import groovy.lang.Closure;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.MavenPlugin;
import org.gradle.api.tasks.Upload;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayAntDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, LiferayAntPlugin.class);

		_applyPlugins(project);

		// GRADLE-2427

		_addTaskInstall(project);

		_applyConfigScripts(project);

		final ReplaceRegexTask updateVersionTask = _addTaskUpdateVersion(
			project);

		_configureProject(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					GradleUtil.setProjectSnapshotVersion(project);

					// setProjectSnapshotVersion must be called before
					// configureTaskUploadArchives, because the latter one needs
					// to know if we are publishing a snapshot or not.

					_configureTaskUploadArchives(project, updateVersionTask);
				}

			});
	}

	private Upload _addTaskInstall(Project project) {
		Upload upload = GradleUtil.addTask(
			project, MavenPlugin.INSTALL_TASK_NAME, Upload.class, true);

		Configuration configuration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		upload.setConfiguration(configuration);
		upload.setDescription(
			"Installs the '" + configuration.getName() +
				"' artifacts into the local Maven repository.");

		return upload;
	}

	private ReplaceRegexTask _addTaskUpdateVersion(final Project project) {
		ReplaceRegexTask replaceRegexTask = GradleUtil.addTask(
			project, LiferayRelengPlugin.UPDATE_VERSION_TASK_NAME,
			ReplaceRegexTask.class);

		replaceRegexTask.match(
			"module-incremental-version=(\\d+)",
			"docroot/WEB-INF/liferay-plugin-package.properties");

		replaceRegexTask.setDescription(
			"Updates \"module-incremental-version\" in the " +
				"liferay-plugin-package.properties file.");

		replaceRegexTask.setReplacement(
			new Closure<String>(project) {

				@SuppressWarnings("unused")
				public String doCall(String group) {
					int moduleIncrementalVersion = Integer.parseInt(group);

					return String.valueOf(moduleIncrementalVersion + 1);
				}

			});

		return replaceRegexTask;
	}

	private void _applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/defaults/dependencies" +
				"/config-maven.gradle",
			project);
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, MavenPlugin.class);
	}

	private void _configureProject(Project project) {
		project.setGroup(_GROUP);
	}

	private void _configureTaskUploadArchives(
		Project project, Task updatePluginVersionTask) {

		if (GradleUtil.isSnapshot(project)) {
			return;
		}

		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		uploadArchivesTask.finalizedBy(updatePluginVersionTask);
	}

	private static final String _GROUP = "com.liferay.plugins";

}