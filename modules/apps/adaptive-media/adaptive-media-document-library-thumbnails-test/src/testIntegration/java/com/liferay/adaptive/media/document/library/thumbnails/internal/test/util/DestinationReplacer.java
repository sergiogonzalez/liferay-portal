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

package com.liferay.adaptive.media.document.library.thumbnails.internal.test.util;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SynchronousDestination;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public class DestinationReplacer implements AutoCloseable {

	public DestinationReplacer(String... destinationNames) {
		MessageBus messageBus = MessageBusUtil.getMessageBus();

		Stream<String> destinationNamesStream = Stream.of(destinationNames);

		_destinations = destinationNamesStream.map(
			messageBus::getDestination
		).collect(
			Collectors.toList()
		);

		for (String destinationName : destinationNames) {
			SynchronousDestination synchronousDestination =
				new SynchronousDestination();

			synchronousDestination.setName(destinationName);

			messageBus.replace(synchronousDestination);
		}
	}

	@Override
	public void close() throws Exception {
		MessageBus messageBus = MessageBusUtil.getMessageBus();

		_destinations.forEach(messageBus::replace);
	}

	private final Collection<Destination> _destinations;

}