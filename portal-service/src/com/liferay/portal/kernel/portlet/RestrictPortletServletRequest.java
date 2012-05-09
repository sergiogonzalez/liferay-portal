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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PersistentHttpServletRequestWrapper;
import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class RestrictPortletServletRequest
	extends PersistentHttpServletRequestWrapper {

	public RestrictPortletServletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Object getAttribute(String name) {
		Object value = _attributes.get(name);

		if (value == _nullValue) {
			return null;
		}

		if (value != null) {
			return value;
		}

		return super.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		Enumeration<String> superEnumeration = super.getAttributeNames();

		if (_attributes.isEmpty()) {
			return superEnumeration;
		}

		Set<String> names = new HashSet<String>();

		while (superEnumeration.hasMoreElements()) {
			names.add(superEnumeration.nextElement());
		}

		for (Map.Entry<String, Object> entry : _attributes.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value == null) {
				names.remove(key);
			}
			else {
				names.add(key);
			}
		}

		names.addAll(_attributes.keySet());

		return Collections.enumeration(names);
	}

	public Map<String, Object> getAttributes() {
		return _attributes;
	}

	public void mergeSharedAttributes() {
		ServletRequest servletRequest = getRequest();

		Lock mergeLock = (Lock)servletRequest.getAttribute(
			WebKeys.PARALLEL_RENDERING_MERGE_LOCK);

		if (mergeLock != null) {
			mergeLock.lock();
		}

		try {
			doMergeSharedAttributes(servletRequest);
		}
		finally {
			if (mergeLock != null) {
				mergeLock.unlock();
			}
		}
	}

	@Override
	public void removeAttribute(String name) {
		_attributes.put(name, _nullValue);
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (value == null) {
			value = _nullValue;
		}

		_attributes.put(name, value);
	}

	protected void doMergeSharedAttributes(ServletRequest servletRequest) {
		for (Map.Entry<String, Object> entry : _attributes.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();

			doMergeSharedAttributes(servletRequest, name, value);
		}
	}

	protected void doMergeSharedAttributes(
		ServletRequest servletRequest, String name, Object value) {

		if (name.startsWith(LIFERAY_SHARED_PREFIX)) {
			if (value == _nullValue) {
				servletRequest.removeAttribute(name);

				if (_log.isInfoEnabled()) {
					_log.info("Remove shared attribute " + name);
				}
			}
			else {
				Object masterValue = servletRequest.getAttribute(name);

				if ((masterValue == null) || !(value instanceof Mergeable)) {
					servletRequest.setAttribute(name, value);

					if (_log.isInfoEnabled()) {
						_log.info("Set shared attribute " + name);
					}
				}
				else {
					Mergeable<Object> masterMergeable =
						(Mergeable<Object>)value;
					Mergeable<Object> slaveMergeable = (Mergeable<Object>)value;

					masterMergeable.merge(slaveMergeable);

					if (_log.isInfoEnabled()) {
						_log.info("Merge shared attribute " + name);
					}
				}
			}
		}
		else {
			if ((value != _nullValue) && _log.isDebugEnabled()) {
				_log.debug("Ignore setting restricted attribute " + name);
			}
		}
	}

	private static final String LIFERAY_SHARED_PREFIX = "LIFERAY_SHARED_";

	private static Log _log = LogFactoryUtil.getLog(
		RestrictPortletServletRequest.class);

	private static Object _nullValue = new Object();

	private Map<String, Object> _attributes = new HashMap<String, Object>();

}