/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialActivityService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityService
 * @generated
 */
public class SocialActivityServiceWrapper implements SocialActivityService,
	ServiceWrapper<SocialActivityService> {
	public SocialActivityServiceWrapper(
		SocialActivityService socialActivityService) {
		_socialActivityService = socialActivityService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _socialActivityService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialActivityService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public SocialActivityService getWrappedSocialActivityService() {
		return _socialActivityService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedSocialActivityService(
		SocialActivityService socialActivityService) {
		_socialActivityService = socialActivityService;
	}

	@Override
	public SocialActivityService getWrappedService() {
		return _socialActivityService;
	}

	@Override
	public void setWrappedService(SocialActivityService socialActivityService) {
		_socialActivityService = socialActivityService;
	}

	private SocialActivityService _socialActivityService;
}