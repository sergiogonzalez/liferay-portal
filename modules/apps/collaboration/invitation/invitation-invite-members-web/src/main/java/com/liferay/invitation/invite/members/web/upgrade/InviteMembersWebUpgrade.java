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

package com.liferay.invitation.invite.members.web.upgrade;

import com.liferay.invitation.invite.members.web.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class InviteMembersWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(UpgradeStepRegistrator.Registry registry) {
		registry.register(
			"com.liferay.invitation.invite.members.web", "0.0.0", "1.0.0",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.invitation.invite.members.web", "0.0.1", "1.0.0",
			new UpgradePortletId());
	}

}