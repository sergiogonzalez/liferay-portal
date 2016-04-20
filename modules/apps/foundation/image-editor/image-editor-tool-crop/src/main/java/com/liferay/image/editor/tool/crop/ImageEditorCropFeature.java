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

package com.liferay.image.editor.tool.crop;

import com.liferay.image.editor.api.BaseImageEditorFeature;
import com.liferay.image.editor.api.ImageEditorFeature;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.image.editor.tool.category=crop",
		"com.liferay.image.editor.tool.controls=crop",
		"com.liferay.image.editor.tool.icon=crop",
		"com.liferay.image.editor.tool.name=Crop",
		"com.liferay.image.editor.tool.type=tool"
	},
	service = ImageEditorFeature.class
)
public class ImageEditorCropFeature extends BaseImageEditorFeature {
}