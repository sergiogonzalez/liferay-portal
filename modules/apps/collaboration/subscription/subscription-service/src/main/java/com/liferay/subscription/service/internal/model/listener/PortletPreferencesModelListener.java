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

package com.liferay.subscription.service.internal.model.listener;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ModelListener.class)
public class PortletPreferencesModelListener
	extends BaseModelListener<PortletPreferences> {

	@Override
	public void onAfterRemove(PortletPreferences portletPreferences) {
		clearCache(portletPreferences);

		deleteSubscriptions(portletPreferences);
	}

	@Override
	public void onAfterUpdate(PortletPreferences portletPreferences) {
		clearCache(portletPreferences);

		updateLayout(portletPreferences);
	}

	protected void clearCache(PortletPreferences portletPreferences) {
		if (portletPreferences == null) {
			return;
		}

		try {
			long companyId = 0;

			Layout layout = _layoutLocalService.getLayout(
				portletPreferences.getPlid());

			if ((layout != null) && !layout.isPrivateLayout()) {
				companyId = layout.getCompanyId();
			}
			else {
				LayoutRevision layoutRevision =
					_layoutRevisionLocalService.getLayoutRevision(
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
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	protected void updateLayout(PortletPreferences portletPreferences) {
		try {
			if ((portletPreferences.getOwnerType() ==
					PortletKeys.PREFS_OWNER_TYPE_GROUP) &&
				(portletPreferences.getOwnerId() > 0)) {

				Group group = _groupLocalService.fetchGroup(
					portletPreferences.getOwnerId());

				if (group == null) {
					return;
				}

				String className = group.getClassName();

				if (!className.equals(LayoutSetPrototype.class.getName())) {
					return;
				}

				LayoutSetPrototype layoutSetPrototype =
					_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
						group.getClassPK());

				if (layoutSetPrototype == null) {
					return;
				}

				layoutSetPrototype.setModifiedDate(new Date());

				_layoutSetPrototypeLocalService.updateLayoutSetPrototype(
					layoutSetPrototype);
			}
			else if ((portletPreferences.getOwnerType() ==
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT) &&
					 (portletPreferences.getPlid() > 0)) {

				Layout layout = _layoutLocalService.fetchLayout(
					portletPreferences.getPlid());

				if (layout == null) {
					return;
				}

				layout.setModifiedDate(new Date());

				_layoutLocalService.updateLayout(layout);
			}
		}
		catch (Exception e) {
			_log.error("Unable to update the layout's modified date", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesModelListener.class);

	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private final LayoutRevisionLocalService _layoutRevisionLocalService;
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;
	private SubscriptionLocalService _subscriptionLocalService;

}