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

package com.liferay.portal.kernel.trash;

import java.util.List;

/**
 * Represents the interface for registering handlers for those entities that can
 * be moved to Trash.
 *
 * <p>
 * The entities that can be registered are:
 * </p>
 *
 * <ul>
 * <li>
 * {@link com.liferay.portlet.blogs.trash.BlogsEntryTrashHandler}
 * </li>
 * </ul>
 *
 * @author Alexander Chow
 */
public interface TrashHandlerRegistry {

	/**
	 * Returns the trash handler associated with the class name.
	 *
	 * @param  className class name of the TrashHandler
	 * @return the trash handler associated with the class name
	 */
	public TrashHandler getTrashHandler(String className);

	/**
	 * Returns all of the trash handlers.
	 *
	 * @return the trash handlers
	 */
	public List<TrashHandler> getTrashHandlers();

	/**
	 * Registers the trash handler.
	 *
	 * @param trashHandler the TrashHandler to register
	 */
	public void register(TrashHandler trashHandler);

	/**
	 * Unregisters the trash handler.
	 *
	 * @param trashHandler the trash handler to unregister
	 */
	public void unregister(TrashHandler trashHandler);

}