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

package com.liferay.ip.geocoder.internal;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Julio Camarero
 */
@Meta.OCD(id = "com.liferay.ip.geocoder.internal.IPGeocoderConfiguration")
public interface IPGeocoderConfiguration {

	@Meta.AD(
		description =
			"The path where the database will be stored, with a default path " +
				"of ${java.io.tmpdir}/liferay/geoip/GeoIPCity.dat",
		name = "File Path", required = false
	)
	public String filePath();

	@Meta.AD(
		deflt =
			"http://cdn.mirrors.liferay.com/geolite.maxmind.com/download/" +
				"geoip/database/GeoLiteCity.dat.xz",
		description = "The URL of the Geo IP City database", name = "File URL",
		required = false
	)
	public String fileURL();

}