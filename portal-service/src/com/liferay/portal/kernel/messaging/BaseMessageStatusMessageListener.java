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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public abstract class BaseMessageStatusMessageListener
	implements MessageListener {

	@Override
	public void receive(Message message) {
		MessageStatus messageStatus = new MessageStatus();

		messageStatus.startTimer();

		try {
			doReceive(message, messageStatus);
		}
		catch (Exception e) {
			_log.error(
				"Unable to process request " + message.getDestinationName(), e);

			messageStatus.setException(e);
		}
		finally {
			messageStatus.stopTimer();

			_statusSender.send(messageStatus);
		}
	}

	public void setStatusSender(SingleDestinationMessageSender statusSender) {
		_statusSender = statusSender;
	}

	protected abstract void doReceive(
			Message message, MessageStatus messageStatus)
		throws Exception;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMessageStatusMessageListener.class);

	private SingleDestinationMessageSender _statusSender;

}