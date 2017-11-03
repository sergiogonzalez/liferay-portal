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

package com.liferay.document.library.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the DLContent service. Represents a row in the &quot;DLContent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLContentModel
 * @see com.liferay.portlet.documentlibrary.model.impl.DLContentImpl
 * @see com.liferay.portlet.documentlibrary.model.impl.DLContentModelImpl
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.document.library.content.model.impl.DLContentImpl}
 * @generated
 */
@Deprecated
@ImplementationClassName("com.liferay.portlet.documentlibrary.model.impl.DLContentImpl")
@ProviderType
public interface DLContent extends DLContentModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.documentlibrary.model.impl.DLContentImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLContent, Long> CONTENT_ID_ACCESSOR = new Accessor<DLContent, Long>() {
			@Override
			public Long get(DLContent dlContent) {
				return dlContent.getContentId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLContent> getTypeClass() {
				return DLContent.class;
			}
		};
}