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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Sergio González
 */
public class RenderCommandCache {

	public static final String RENDER_PACKAGE_NAME = "render.package.prefix";

	public static final RenderCommand EMPTY = new RenderCommand() {

		@Override
		public String processCommand(
				RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException {

			return null;
		}

	};

	public RenderCommandCache(String packagePrefix, String portletName) {
		if (Validator.isNotNull(packagePrefix) &&
			!packagePrefix.endsWith(StringPool.PERIOD)) {

			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(render.command.name=*)(javax.portlet.name=" + portletName +
				")(objectClass=" + RenderCommand.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new RenderCommandServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	public RenderCommand getRenderCommand(String renderCommandName) {
		String className = null;

		try {
			RenderCommand renderCommand = _renderCommandCache.get(
				renderCommandName);

			if (renderCommand != null) {
				return renderCommand;
			}

			if (Validator.isNull(_packagePrefix)) {
				return EMPTY;
			}

			StringBundler sb = new StringBundler(4);

			sb.append(_packagePrefix);
			sb.append(Character.toUpperCase(renderCommandName.charAt(0)));
			sb.append(renderCommandName.substring(1));
			sb.append(_RENDER_COMMAND_POSTFIX);

			className = sb.toString();

			renderCommand = (RenderCommand)InstanceFactory.newInstance(
				className);

			_renderCommandCache.put(renderCommandName, renderCommand);

			return renderCommand;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to instantiate RenderCommand " + className);
			}

			_renderCommandCache.put(renderCommandName, EMPTY);

			return EMPTY;
		}
	}

	public boolean isEmpty() {
		return _renderCommandCache.isEmpty();
	}

	private static final String _RENDER_COMMAND_POSTFIX = "RenderCommand";

	private static final Log _log = LogFactoryUtil.getLog(
		RenderCommandCache.class);

	private final Map<String, RenderCommand> _renderCommandCache =
		new ConcurrentHashMap<>();
	private final String _packagePrefix;
	private final ServiceTracker<RenderCommand, RenderCommand> _serviceTracker;

	private class RenderCommandServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<RenderCommand, RenderCommand> {

		@Override
		public RenderCommand addingService(
			ServiceReference<RenderCommand> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			RenderCommand renderCommand = registry.getService(serviceReference);

			String renderCommandName = (String)serviceReference.getProperty(
				"render.command.name");

			_renderCommandCache.put(renderCommandName, renderCommand);

			return renderCommand;
		}

		@Override
		public void modifiedService(
			ServiceReference<RenderCommand> serviceReference,
			RenderCommand renderCommand) {
		}

		@Override
		public void removedService(
			ServiceReference<RenderCommand> serviceReference,
			RenderCommand renderCommand) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String renderCommandName = (String)serviceReference.getProperty(
				"render.command.name");

			_renderCommandCache.remove(renderCommandName);
		}

	}

}