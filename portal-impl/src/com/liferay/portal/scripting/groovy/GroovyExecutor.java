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

package com.liferay.portal.scripting.groovy;

import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.scripting.BaseScriptingExecutor;
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
public class GroovyExecutor extends BaseScriptingExecutor {

	@Override
	public void clearCache() {
		SingleVMPoolUtil.clear(_CACHE_NAME);
	}

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script)
		throws ScriptingException {

		if (allowedClasses != null) {
			throw new ExecutionException(
				"Constrained execution not supported for Groovy");
		}

		Script compiledScript = getCompiledScript(script);

		Binding binding = new Binding(inputObjects);

		compiledScript.setBinding(binding);

		compiledScript.run();

		if (outputNames == null) {
			return null;
		}

		Map<String, Object> outputObjects = new HashMap<String, Object>();

		for (String outputName : outputNames) {
			outputObjects.put(outputName, binding.getVariable(outputName));
		}

		return outputObjects;
	}

	public String getLanguage() {
		return _LANGUAGE;
	}

	protected Script getCompiledScript(String script) {
		if (_groovyShell == null) {
			synchronized (this) {
				if (_groovyShell == null) {
					_groovyShell = new GroovyShell();
				}
			}
		}

		String key = String.valueOf(script.hashCode());

		Script compiledScript = (Script)SingleVMPoolUtil.get(_CACHE_NAME, key);

		if (compiledScript == null) {
			compiledScript = _groovyShell.parse(script);

			SingleVMPoolUtil.put(_CACHE_NAME, key, compiledScript);
		}

		return compiledScript;
	}

	private static final String _CACHE_NAME = GroovyExecutor.class.getName();

	private static final String _LANGUAGE = "groovy";

	private volatile GroovyShell _groovyShell;

}