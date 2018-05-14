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

package com.liferay.oauth2.provider.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class OAuth2ApplicationImpl extends OAuth2ApplicationBaseImpl {

	@Override
	public List<GrantType> getAllowedGrantTypesList() {
		Stream<String> stream = Arrays.stream(
			StringUtil.split(getAllowedGrantTypes()));

		List<GrantType> grantTypes = stream.map(
			GrantType::valueOf
		).collect(
			Collectors.toList()
		);

		return grantTypes;
	}

	@Override
	public List<String> getFeaturesList() {
		return Arrays.asList(StringUtil.split(getFeatures()));
	}

	public String getLogoURL(ThemeDisplay themeDisplay) {
		String logoURL = StringPool.BLANK;

		try {
			if (getIconFileEntryId() > 0) {
				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
					getIconFileEntryId());

				logoURL = DLUtil.getImagePreviewURL(fileEntry, themeDisplay);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		if (Validator.isBlank(logoURL)) {
			logoURL = UserConstants.getPortraitURL(
				themeDisplay.getPathImage(), true, 0, null);
		}

		return logoURL;
	}

	@Override
	public List<String> getRedirectURIsList() {
		return Arrays.asList(
			StringUtil.split(getRedirectURIs(), StringPool.NEW_LINE));
	}

	@Override
	public void setAllowedGrantTypesList(
		List<GrantType> allowedGrantTypesList) {

		Stream<GrantType> stream = allowedGrantTypesList.stream();

		String allowedGrantTypes = stream.map(
			GrantType::toString
		).collect(
			Collectors.joining(StringPool.COMMA)
		);

		setAllowedGrantTypes(allowedGrantTypes);
	}

	@Override
	public void setFeaturesList(List<String> featuresList) {
		String features = StringUtil.merge(featuresList);

		setFeatures(features);
	}

	@Override
	public void setRedirectURIsList(List<String> redirectURIsList) {
		String redirectURIs = StringUtil.merge(
			redirectURIsList, StringPool.NEW_LINE);

		setRedirectURIs(redirectURIs);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ApplicationImpl.class);

}