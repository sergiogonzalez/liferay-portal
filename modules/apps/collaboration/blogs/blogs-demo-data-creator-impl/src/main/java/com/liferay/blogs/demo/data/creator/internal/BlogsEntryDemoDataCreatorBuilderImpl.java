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

package com.liferay.blogs.demo.data.creator.internal;

import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreator;
import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreatorBuilder;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.comment.demo.data.creator.MultipleCommentDemoDataCreator;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

/**
 * @author Alejandro Hernández
 */
public class BlogsEntryDemoDataCreatorBuilderImpl
	implements BlogsEntryDemoDataCreatorBuilder {

	public BlogsEntryDemoDataCreatorBuilderImpl(
		BlogsEntryDemoDataCreator blogsEntryDemoDataCreator,
		MultipleCommentDemoDataCreator multipleCommentDemoDataCreator) {

		_blogsEntryDemoDataCreator = blogsEntryDemoDataCreator;
		_multipleCommentDemoDataCreator = multipleCommentDemoDataCreator;
	}

	@Override
	public BlogsEntry build(long userId, long groupId)
		throws IOException, PortalException {

		BlogsEntry blogsEntry = _blogsEntryDemoDataCreator.create(
			userId, groupId);

		if (_addComments) {
			_multipleCommentDemoDataCreator.create(blogsEntry);
		}

		return blogsEntry;
	}

	@Override
	public BlogsEntryDemoDataCreatorBuilder withComments() {
		_addComments = true;

		return this;
	}

	private boolean _addComments;
	private final BlogsEntryDemoDataCreator _blogsEntryDemoDataCreator;
	private final MultipleCommentDemoDataCreator
		_multipleCommentDemoDataCreator;

}