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

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author André de Oliveira
 */
public interface DiscussionDisplay {

	public DiscussionRootComment createDiscussionRootComment()
		throws PortalException;

	public Comment getParent(Comment comment) throws PortalException;

	public String getRatingsClassName();

	public long getThreadId();

	public String getWorkflowDefinitionLinkClassName();

	public boolean isInTrash() throws PortalException;

	public boolean isInTrash(Comment comment) throws PortalException;

}