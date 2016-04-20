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

package com.liferay.image.editor.capability.resize;

import com.liferay.image.editor.capability.internal.BaseImageEditorCapability;
import com.liferay.image.editor.capability.internal.ImageEditorCapability;

import org.osgi.service.component.annotations.Component;

/**
 * @author Chema Balsas
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.image.editor.capability.category=resize",
		"com.liferay.image.editor.capability.controls=resize",
		"com.liferay.image.editor.capability.icon=resize-full",
		"com.liferay.image.editor.capability.name=Resize",
		"com.liferay.image.editor.capability.type=tool"
	},
	service = ImageEditorCapability.class
)
public class ImageEditorCapabilityResize extends BaseImageEditorCapability {
}