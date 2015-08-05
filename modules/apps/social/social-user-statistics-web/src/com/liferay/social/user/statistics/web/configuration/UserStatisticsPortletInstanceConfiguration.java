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

package com.liferay.social.user.statistics.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Iván Zaera
 */
@Meta.OCD(
	id = "com.liferay.social.user.statistics.web.configuration.UserStatisticsPortletInstanceConfiguration"
)
public interface UserStatisticsPortletInstanceConfiguration {

	@Meta.AD(
		deflt = "true",
		required = false
	)
	public boolean rankByContribution();

	@Meta.AD(
		deflt = "true",
		required = false
	)
	public boolean rankByParticipation();

	@Meta.AD(
		deflt = "true",
		required = false
	)
	public boolean showHeaderText();

	@Meta.AD(
		deflt = "true",
		required = false
	)
	public boolean showTotals();

	@Meta.AD(
		deflt = "user.achievements",
		required = false
	)
	public String[] displayActivityCounterName();

}
