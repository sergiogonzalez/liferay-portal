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

package com.liferay.subscription.internal.model.listener;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ModelListener.class)
public class SubscriptionPortletPreferencesModelListener
	extends BaseModelListener<PortletPreferences> {

	@Override
	public void onAfterRemove(PortletPreferences portletPreferences) {
		clearCache(portletPreferences);

		deleteSubscriptions(portletPreferences);
	}

	/**
	 * @see com.liferay.portal.model.PortletPreferencesModelListener#clearCache(
	 *      PortletPreferences)
	 */
	protected void clearCache(PortletPreferences portletPreferences) {
		if (portletPreferences == null) {
			return;
		}

		try {
			long companyId = 0;

			Layout layout = _layoutLocalService.fetchLayout(
				portletPreferences.getPlid());

			if ((layout != null) && !layout.isPrivateLayout()) {
				companyId = layout.getCompanyId();
			}
			else {
				LayoutRevision layoutRevision =
					_layoutRevisionLocalService.fetchLayoutRevision(
						portletPreferences.getPlid());

				if ((layoutRevision != null) &&
					!layoutRevision.isPrivateLayout()) {

					companyId = layoutRevision.getCompanyId();
				}
			}

			if (companyId > 0) {
				CacheUtil.clearCache(companyId);
			}
		}
		catch (Exception e) {
			CacheUtil.clearCache();
		}
	}

	protected void deleteSubscriptions(PortletPreferences portletPreferences) {
		if (portletPreferences == null) {
			return;
		}

		try {
			_subscriptionLocalService.deleteSubscriptions(
				portletPreferences.getCompanyId(),
				portletPreferences.getModelClassName(),
				portletPreferences.getPortletPreferencesId());
		}
		catch (Exception e) {
			_log.error("Unable to delete subscriptions", e);
		}
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SubscriptionPortletPreferencesModelListener.class);

	private LayoutLocalService _layoutLocalService;
	private LayoutRevisionLocalService _layoutRevisionLocalService;
	private SubscriptionLocalService _subscriptionLocalService;

}