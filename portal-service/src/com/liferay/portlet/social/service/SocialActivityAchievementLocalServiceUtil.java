/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the social activity achievement local service. This utility wraps {@link com.liferay.portlet.social.service.impl.SocialActivityAchievementLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityAchievementLocalService
 * @see com.liferay.portlet.social.service.base.SocialActivityAchievementLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialActivityAchievementLocalServiceImpl
 * @generated
 */
public class SocialActivityAchievementLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialActivityAchievementLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the social activity achievement to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityAchievement the social activity achievement
	* @return the social activity achievement that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityAchievement addSocialActivityAchievement(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addSocialActivityAchievement(socialActivityAchievement);
	}

	/**
	* Creates a new social activity achievement with the primary key. Does not add the social activity achievement to the database.
	*
	* @param activityAchievementId the primary key for the new social activity achievement
	* @return the new social activity achievement
	*/
	public static com.liferay.portlet.social.model.SocialActivityAchievement createSocialActivityAchievement(
		long activityAchievementId) {
		return getService()
				   .createSocialActivityAchievement(activityAchievementId);
	}

	/**
	* Deletes the social activity achievement with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement that was removed
	* @throws PortalException if a social activity achievement with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityAchievement deleteSocialActivityAchievement(
		long activityAchievementId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteSocialActivityAchievement(activityAchievementId);
	}

	/**
	* Deletes the social activity achievement from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityAchievement the social activity achievement
	* @return the social activity achievement that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityAchievement deleteSocialActivityAchievement(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteSocialActivityAchievement(socialActivityAchievement);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.social.model.SocialActivityAchievement fetchSocialActivityAchievement(
		long activityAchievementId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchSocialActivityAchievement(activityAchievementId);
	}

	/**
	* Returns the social activity achievement with the primary key.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement
	* @throws PortalException if a social activity achievement with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityAchievement getSocialActivityAchievement(
		long activityAchievementId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialActivityAchievement(activityAchievementId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the social activity achievements.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getSocialActivityAchievements(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialActivityAchievements(start, end);
	}

	/**
	* Returns the number of social activity achievements.
	*
	* @return the number of social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public static int getSocialActivityAchievementsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialActivityAchievementsCount();
	}

	/**
	* Updates the social activity achievement in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivityAchievement the social activity achievement
	* @return the social activity achievement that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialActivityAchievement updateSocialActivityAchievement(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateSocialActivityAchievement(socialActivityAchievement);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void addActivityAchievement(long userId, long groupId,
		com.liferay.portlet.social.model.SocialAchievement achievement)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addActivityAchievement(userId, groupId, achievement);
	}

	public static com.liferay.portlet.social.model.SocialActivityAchievement fetchUserAchievement(
		long userId, long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchUserAchievement(userId, groupId, name);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getGroupAchievements(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupAchievements(groupId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getGroupAchievements(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupAchievements(groupId, name);
	}

	public static int getGroupAchievementsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupAchievementsCount(groupId);
	}

	public static int getGroupAchievementsCount(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupAchievementsCount(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getGroupFirstAchievements(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFirstAchievements(groupId);
	}

	public static int getGroupFirstAchievementsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupFirstAchievementsCount(groupId);
	}

	public static int getUserAchievementCount(long userId, long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserAchievementCount(userId, groupId, name);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getUserAchievements(
		long userId, long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserAchievements(userId, groupId, name);
	}

	public static SocialActivityAchievementLocalService getService() {
		if (_service == null) {
			_service = (SocialActivityAchievementLocalService)PortalBeanLocatorUtil.locate(SocialActivityAchievementLocalService.class.getName());

			ReferenceRegistry.registerReference(SocialActivityAchievementLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(SocialActivityAchievementLocalService service) {
	}

	private static SocialActivityAchievementLocalService _service;
}