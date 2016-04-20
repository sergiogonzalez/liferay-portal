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

package com.liferay.image.editor.capability.contrast;

import com.liferay.image.editor.capability.BaseImageEditorCapability;
import com.liferay.image.editor.capability.ImageEditorCapability;
import org.osgi.service.component.annotations.Component;

/**
 * @author Chema Balsas
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.image.editor.capability.category=adjust",
		"com.liferay.image.editor.capability.controls=contrast",
		"com.liferay.image.editor.capability.icon=sun",
		"com.liferay.image.editor.capability.name=Contrast",
		"com.liferay.image.editor.capability.type=tool"
	},
	service = ImageEditorCapability.class
)
public class ImageEditorCapabilityContrast extends BaseImageEditorCapability {
}