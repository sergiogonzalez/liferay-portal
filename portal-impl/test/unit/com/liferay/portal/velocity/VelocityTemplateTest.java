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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;

import freemarker.core.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author Tina Tian
 */
public class VelocityTemplateTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		InitUtil.initWithSpring();

		_templateContextHelper = new MockTemplateContextHelper();

		_velocityEngine = new VelocityEngine();

		ExtendedProperties extendedProperties = new FastExtendedProperties();

		extendedProperties.setProperty(
			VelocityEngine.RESOURCE_MANAGER_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER));
		extendedProperties.setProperty(
			VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER));
		extendedProperties.setProperty(
			VelocityEngine.RUNTIME_LOG_LOGSYSTEM + ".log4j.category",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER_CATEGORY));

		_velocityEngine.setExtendedProperties(extendedProperties);

		_velocityEngine.init();
	}

	public void testGet() throws Exception {
		Template template = new VelocityTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME), null, null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		Object result = template.get(_TEST_KEY);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(_TEST_VALUE, stringResult);
	}

	public void testPrepare() throws Exception {
		Template template = new VelocityTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME), null, null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		template.prepare(null);

		Object result = template.get(_TEST_VALUE);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(_TEST_VALUE, stringResult);
	}

	public void testProcessTemplate1() throws Exception {
		Template template = new VelocityTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME), null, null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate2() throws Exception {
		Template template = new VelocityTemplate(
			new MockTemplateResource(_WRONG_TEMPLATE_ID), null, null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			template.processTemplate(unsyncStringWriter);

			fail();
		}
		catch (Exception e) {
			if (e instanceof TemplateException) {
				String message = e.getMessage();

				assertTrue(message.contains(_WRONG_TEMPLATE_ID));

				return;
			}

			fail();
		}
	}

	public void testProcessTemplate3() throws Exception {
		Template template = new VelocityTemplate(
			new StringTemplateResource(
				_WRONG_TEMPLATE_ID, _TEST_TEMPLATE_CONTENT),
			null, null, _velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate4() throws Exception {
		Template template = new VelocityTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME),
			new MockTemplateResource(_WRONG_ERROR_TEMPLATE_ID), null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate5() throws Exception {
		Template template = new VelocityTemplate(
			new MockTemplateResource(_WRONG_TEMPLATE_ID),
			new MockTemplateResource(_TEMPLATE_FILE_NAME), null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate6() throws Exception {
		Template template = new VelocityTemplate(
			new MockTemplateResource(_WRONG_TEMPLATE_ID),
			new MockTemplateResource(_WRONG_ERROR_TEMPLATE_ID), null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			template.processTemplate(unsyncStringWriter);

			fail();
		}
		catch (Exception e) {
			if (e instanceof TemplateException) {
				String message = e.getMessage();

				assertTrue(message.contains(_WRONG_ERROR_TEMPLATE_ID));

				return;
			}

			fail();
		}
	}

	public void testProcessTemplate7() throws Exception {
		Template template = new VelocityTemplate(
			new MockTemplateResource(_WRONG_TEMPLATE_ID),
			new StringTemplateResource(
				_WRONG_ERROR_TEMPLATE_ID, _TEST_TEMPLATE_CONTENT),
			null, _velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate8() throws Exception {
		VelocityContext velocityContext = new VelocityContext();

		velocityContext.put(_TEST_KEY, _TEST_VALUE);

		Template template = new VelocityTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME), null,
			velocityContext, _velocityEngine, _templateContextHelper);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	private static final String _TEMPLATE_FILE_NAME = "test.vm";

	private static final String _TEST_KEY = "TEST_KEY";

	private static final String _TEST_TEMPLATE_CONTENT = "$" + _TEST_KEY;

	private static final String _TEST_VALUE = "TEST_VALUE";

	private static final String _WRONG_ERROR_TEMPLATE_ID =
		"WRONG_ERROR_TEMPLATE_ID";

	private static final String _WRONG_TEMPLATE_ID = "WRONG_TEMPLATE_ID";

	private TemplateContextHelper _templateContextHelper;
	private VelocityEngine _velocityEngine;

	private class MockTemplateContextHelper extends TemplateContextHelper {

		@Override
		public Map<String, Object> getHelperUtilities() {
			return Collections.emptyMap();
		}

		@Override
		public Map<String, Object> getRestrictedHelperUtilities() {
			return Collections.emptyMap();
		}

		@Override
		public Set<String> getRestrictedVariables() {
			return Collections.emptySet();
		}

		@Override
		public void prepare(
			TemplateContext templateContext, HttpServletRequest request) {

			String testValue = (String)templateContext.get(_TEST_KEY);

			templateContext.put(testValue, testValue);
		}

	}

	private class MockTemplateResource implements TemplateResource {

		public MockTemplateResource(String templateId) {
			_templateId = templateId;
		}

		public long getLastModified() {
			return _lastModified;
		}

		public Reader getReader() throws IOException {
			if (_templateId.equals(_TEMPLATE_FILE_NAME)) {
				return new StringReader(_TEST_TEMPLATE_CONTENT);
			}

			throw new ParseException(
				"Unable to get reader for template source " + _templateId, 0,
				0);
		}

		public String getTemplateId() {
			return _templateId;
		}

		private long _lastModified = System.currentTimeMillis();
		private String _templateId;

	}

}