/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.communities.messaging;

import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;

/**
 * @author     Bruno Farache
 * @deprecated As of 6.1.0, replaced by {@link
 *             com.liferay.portal.messaging.LayoutsRemotePublisherMessageListener}
 */
public class LayoutsRemotePublisherMessageListener
	extends com.liferay.portal.messaging.LayoutsRemotePublisherMessageListener {

	public LayoutsRemotePublisherMessageListener() {
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public LayoutsRemotePublisherMessageListener(
		SingleDestinationMessageSender statusSender,
		MessageSender responseSender) {

		super(statusSender, responseSender);
	}

}