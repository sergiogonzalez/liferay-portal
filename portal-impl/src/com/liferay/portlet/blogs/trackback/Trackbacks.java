package com.liferay.portlet.blogs.trackback;

import com.google.common.base.Function;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.blogs.model.BlogsEntry;

public interface Trackbacks {

	public abstract void addTrackback(
		BlogsEntry entry, ThemeDisplay themeDisplay, String excerpt, String url,
		String blogName, String title,
		Function<String, ServiceContext> serviceContextFunction)
	throws PortalException, SystemException;

}
