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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;

/**
 * @author Eduardo Garcia
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.message.boards.internal.model.listener.
 *             LayoutModelListener}
 */
@Deprecated
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) throws ModelListenerException {
		super.onAfterCreate(layout);
	}

	@Override
	public void onBeforeRemove(Layout layout) throws ModelListenerException {
		try {
			CommentManagerUtil.deleteDiscussion(
				Layout.class.getName(), layout.getPlid());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

}