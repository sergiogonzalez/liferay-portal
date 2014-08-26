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

import java.util.Date;

/**
 * @author André de Oliveira
 */
public interface Comment {

	public String getBody();

	public long getCommentId();

	public Date getCreateDate();

	public long getMessageId();

	public Date getModifiedDate();

	public long getRatingsClassPK();

	public int getStatus();

	public long getUserId();

	public String getUserName();

	public String getUserNameNonAnonymous();

	public Class<?> getWorkflowStatusModelClass();

	public Class<?> getWorkflowStatusModelContextClass();

	public boolean isAnonymous();

	public boolean isApproved();

	public boolean isChildOf(long commentId);

	public boolean isFormatBBCode();

	public boolean isPending();

	public boolean isRoot();

}