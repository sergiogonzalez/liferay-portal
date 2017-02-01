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

package com.liferay.flags.taglib.servlet.taglib;

import com.liferay.flags.taglib.internal.servlet.ServletContextUtil;
import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.kernel.util.TrashUtil;

/**
 * @author Julio Camarero
 */
public class FlagsTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

		String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

		boolean inTrash = false;//TrashUtil.isInTrash(this.className, this.classPK);

		String cssClass = randomNamespace;

		if (!inTrash) {
			cssClass = randomNamespace + " flag-enable";
		}

		putValue("elementClasses", cssClass);

/*
		context.put("cssClass", cssClass);
		context.put("data", dataJSON);
		context.put("inTrash", inTrash);
		context.put("id", randomNamespace + "id");
		context.put("signedUser", flagsGroupServiceConfiguration.guestUsersEnabled() || themeDisplay.isSignedIn());
		context.put("uri", editEntryURL.toString());
*/


		putValue("pathThemeImages", themeDisplay.getPathThemeImages());

		setTemplateNamespace("Flags.render");

		return super.doStartTag();
	}

	@Override
	public int doEndTag() {
		try {
			return super.doEndTag();
		}
		catch (javax.servlet.jsp.JspException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getModule() {
		return "flags-taglib/flags/Flags.es";
	}

	public void setClassName(String className) {
		putValue("className", className);
	}

	public void setClassPK(long classPK) {
		putValue("classPK", classPK);
	}

	public void setContentTitle(String contentTitle) {
		putValue("contentTitle", contentTitle);
	}

	public void setLabel(boolean label) {
		putValue("label", label);
	}

	public void setMessage(String message) {
		putValue("message", message);
	}

	public void setReportedUserId(long reportedUserId) {
		putValue("reportedUserId", reportedUserId);
	}

	private boolean _label = true;
}