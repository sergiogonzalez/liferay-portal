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

package com.liferay.comment.web.internal.portlet;

import com.liferay.comment.web.constants.CommentPortletKeys;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Objects;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/comment.png",
		"javax.portlet.display-name=Comments",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + CommentPortletKeys.COMMENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest",
		"javax.portlet.security-role-ref=power-user",
		"javax.portlet.security-role-ref=user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class CommentPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (Objects.isNull(renderRequest.getAttribute(WebKeys.COMMENT))) {
			setCommentAttribute(renderRequest);
		}

		super.render(renderRequest, renderResponse);
	}

	protected void setCommentAttribute(RenderRequest renderRequest) {
		long commentId = ParamUtil.getLong(renderRequest, "commentId");

		Comment comment = CommentManagerUtil.fetchComment(commentId);

		renderRequest.setAttribute(WebKeys.COMMENT, comment);
	}

}