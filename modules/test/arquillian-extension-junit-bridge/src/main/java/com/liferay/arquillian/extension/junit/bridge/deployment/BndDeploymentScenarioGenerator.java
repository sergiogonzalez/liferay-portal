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

package com.liferay.arquillian.extension.junit.bridge.deployment;

import aQute.bnd.build.Project;
import aQute.bnd.build.ProjectBuilder;
import aQute.bnd.build.Workspace;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Preston Crary
 */
public class BndDeploymentScenarioGenerator
	implements DeploymentScenarioGenerator {

	@Override
	public List<DeploymentDescription> generate(TestClass testClass) {
		File bndFile = new File("bnd.bnd");

		try (Analyzer analyzer = new Analyzer()) {
			File absoluteBndFile = bndFile.getAbsoluteFile();

			File parentBndDir = absoluteBndFile.getParentFile();

			Workspace workspace = new Workspace(parentBndDir);

			Project project = new Project(workspace, parentBndDir);

			Properties properties = new Properties();

			properties.putAll(project.loadProperties(bndFile));

			project.setProperties(properties);

			ProjectBuilder projectBuilder = new ProjectBuilder(project);

			projectBuilder.setBase(parentBndDir);

			String javaClassPathString = System.getProperty("java.class.path");

			String[] javaClassPaths = StringUtil.split(
				javaClassPathString, File.pathSeparatorChar);

			for (String javaClassPath : javaClassPaths) {
				File file = new File(javaClassPath);

				if (file.isDirectory() ||
					StringUtil.endsWith(javaClassPath, ".zip") ||
					StringUtil.endsWith(javaClassPath, ".jar")) {

					projectBuilder.addClasspath(file);
				}
			}

			Jar jar = projectBuilder.build();

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			jar.write(byteArrayOutputStream);

			ZipImporter zipImporter = ShrinkWrap.create(ZipImporter.class);

			zipImporter.importFrom(
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

			JavaArchive javaArchive = zipImporter.as(JavaArchive.class);

			analyzer.setProperties(properties);

			javaArchive.addClass(testClass.getJavaClass());

			ZipExporter zipExporter = javaArchive.as(ZipExporter.class);

			jar = new Jar(
				javaArchive.getName(), zipExporter.exportAsInputStream());

			analyzer.setJar(jar);

			Asset asset = javaArchive.get(_MANIFEST_PATH).getAsset();

			Manifest firstPassManifest = new Manifest(asset.openStream());

			Attributes mainAttributes = firstPassManifest.getMainAttributes();

			mainAttributes.remove(new Name("Import-Package"));

			analyzer.mergeManifest(firstPassManifest);

			Manifest manifest = analyzer.calcManifest();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			manifest.write(baos);

			ByteArrayAsset byteArrayAsset = new ByteArrayAsset(
				baos.toByteArray());

			javaArchive.delete(_MANIFEST_PATH);

			javaArchive.add(byteArrayAsset, _MANIFEST_PATH);

			return Collections.singletonList(
				new DeploymentDescription(javaArchive.getName(), javaArchive));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final String _MANIFEST_PATH = "META-INF/MANIFEST.MF";

}