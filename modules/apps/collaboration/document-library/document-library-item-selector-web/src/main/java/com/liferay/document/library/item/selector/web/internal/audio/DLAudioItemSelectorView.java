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

package com.liferay.document.library.item.selector.web.internal.audio;

import com.liferay.document.library.item.selector.web.internal.BaseDLItemSelectorView;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.audio.criterion.AudioItemSelectorCriterion;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {"item.selector.view.order:Integer=100"},
	service = ItemSelectorView.class
)
public class DLAudioItemSelectorView
	extends BaseDLItemSelectorView<AudioItemSelectorCriterion> {

	@Override
	public Class<AudioItemSelectorCriterion> getItemSelectorCriterionClass() {
		return AudioItemSelectorCriterion.class;
	}

	@Override
	public String[] getMimeTypes() {
		return PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES;
	}

}