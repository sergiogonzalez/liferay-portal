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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the wiki page resource local service. This utility wraps {@link com.liferay.portlet.wiki.service.impl.WikiPageResourceLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageResourceLocalService
 * @see com.liferay.portlet.wiki.service.base.WikiPageResourceLocalServiceBaseImpl
 * @see com.liferay.portlet.wiki.service.impl.WikiPageResourceLocalServiceImpl
 * @generated
 */
public class WikiPageResourceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.wiki.service.impl.WikiPageResourceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the wiki page resource to the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPageResource the wiki page resource
	* @return the wiki page resource that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPageResource addWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addWikiPageResource(wikiPageResource);
	}

	/**
	* Creates a new wiki page resource with the primary key. Does not add the wiki page resource to the database.
	*
	* @param resourcePrimKey the primary key for the new wiki page resource
	* @return the new wiki page resource
	*/
	public static com.liferay.portlet.wiki.model.WikiPageResource createWikiPageResource(
		long resourcePrimKey) {
		return getService().createWikiPageResource(resourcePrimKey);
	}

	/**
	* Deletes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource that was removed
	* @throws PortalException if a wiki page resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPageResource deleteWikiPageResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteWikiPageResource(resourcePrimKey);
	}

	/**
	* Deletes the wiki page resource from the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPageResource the wiki page resource
	* @return the wiki page resource that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPageResource deleteWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteWikiPageResource(wikiPageResource);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.wiki.model.impl.WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.wiki.model.impl.WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.wiki.model.WikiPageResource fetchWikiPageResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchWikiPageResource(resourcePrimKey);
	}

	/**
	* Returns the wiki page resource with the primary key.
	*
	* @param resourcePrimKey the primary key of the wiki page resource
	* @return the wiki page resource
	* @throws PortalException if a wiki page resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPageResource getWikiPageResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPageResource(resourcePrimKey);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the wiki page resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.wiki.model.impl.WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki page resources
	* @param end the upper bound of the range of wiki page resources (not inclusive)
	* @return the range of wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> getWikiPageResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPageResources(start, end);
	}

	/**
	* Returns the number of wiki page resources.
	*
	* @return the number of wiki page resources
	* @throws SystemException if a system exception occurred
	*/
	public static int getWikiPageResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPageResourcesCount();
	}

	/**
	* Updates the wiki page resource in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wikiPageResource the wiki page resource
	* @return the wiki page resource that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPageResource updateWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWikiPageResource(wikiPageResource);
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

	public static com.liferay.portlet.wiki.model.WikiPageResource addPageResource(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPageResource(nodeId, title);
	}

	public static void deletePageResource(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePageResource(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource fetchPageResource(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPageResource(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource getPageResource(
		long pageResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPageResource(pageResourcePrimKey);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource getPageResource(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPageResource(nodeId, title);
	}

	public static long getPageResourcePrimKey(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPageResourcePrimKey(nodeId, title);
	}

	public static WikiPageResourceLocalService getService() {
		if (_service == null) {
			_service = (WikiPageResourceLocalService)PortalBeanLocatorUtil.locate(WikiPageResourceLocalService.class.getName());

			ReferenceRegistry.registerReference(WikiPageResourceLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(WikiPageResourceLocalService service) {
	}

	private static WikiPageResourceLocalService _service;
}