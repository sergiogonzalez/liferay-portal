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

package com.liferay.portal.servlet;

import com.liferay.portal.atom.AtomProvider;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomCollectionAdapterRegistryUtil;

import java.util.List;

import org.apache.abdera.protocol.server.Provider;
import org.apache.abdera.protocol.server.servlet.AbderaServlet;

/**
 * @author Igor Spasic
 */
public class AtomServlet extends AbderaServlet {

	@Override
	protected Provider createProvider() {
		AtomProvider atomProvider = new AtomProvider();

		atomProvider.init(getAbdera(), null);

		List<AtomCollectionAdapter<?>> atomCollectionAdapters =
			AtomCollectionAdapterRegistryUtil.getAtomCollectionAdapters();

		for (AtomCollectionAdapter<?> atomCollectionAdapter :
				atomCollectionAdapters) {

			atomProvider.addCollection(atomCollectionAdapter);
		}

		return atomProvider;
	}

}