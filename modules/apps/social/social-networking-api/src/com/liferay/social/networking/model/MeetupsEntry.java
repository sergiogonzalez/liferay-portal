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

package com.liferay.social.networking.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the MeetupsEntry service. Represents a row in the &quot;MeetupsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MeetupsEntryModel
 * @see com.liferay.social.networking.model.impl.MeetupsEntryImpl
 * @see com.liferay.social.networking.model.impl.MeetupsEntryModelImpl
 * @generated
 */
@ProviderType
public interface MeetupsEntry extends MeetupsEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.social.networking.model.impl.MeetupsEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MeetupsEntry, Long> MEETUPS_ENTRY_ID_ACCESSOR = new Accessor<MeetupsEntry, Long>() {
			@Override
			public Long get(MeetupsEntry meetupsEntry) {
				return meetupsEntry.getMeetupsEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MeetupsEntry> getTypeClass() {
				return MeetupsEntry.class;
			}
		};
}