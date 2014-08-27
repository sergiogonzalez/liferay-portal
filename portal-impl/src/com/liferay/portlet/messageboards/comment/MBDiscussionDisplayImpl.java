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

package com.liferay.portlet.messageboards.comment;

import com.liferay.portal.kernel.comment.DiscussionDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.trash.util.TrashUtil;

/**
 * @author André de Oliveira
 */
public class MBDiscussionDisplayImpl implements DiscussionDisplay {

	public MBDiscussionDisplayImpl(
			String className, long classPK, MBMessageDisplay mbMessageDisplay)
		throws PortalException {

		_className = className;
		_classPK = classPK;
		_mbMessageDisplay = mbMessageDisplay;
	}

	public MBThread getThread() {
		return _mbMessageDisplay.getThread();
	}

	@Override
	public long getThreadId() {
		MBThread mbThread = _mbMessageDisplay.getThread();

		return mbThread.getThreadId();
	}

	public MBTreeWalker getTreeWalker() {
		return _mbMessageDisplay.getTreeWalker();
	}

	@Override
	public boolean isInTrash() throws PortalException {
		return TrashUtil.isInTrash(_className, _classPK);
	}

	private final String _className;
	private final long _classPK;
	private final MBMessageDisplay _mbMessageDisplay;

}