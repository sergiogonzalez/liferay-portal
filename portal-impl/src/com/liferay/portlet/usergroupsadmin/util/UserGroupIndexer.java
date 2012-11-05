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

package com.liferay.portlet.usergroupsadmin.util;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Hugo Huijser
 */
public class UserGroupIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {UserGroup.class.getName()};

	public static final String PORTLET_ID = PortletKeys.USER_GROUPS_ADMIN;

	public UserGroupIndexer() {
		setIndexerEnabled(PropsValues.USER_GROUPS_INDEXER_ENABLED);
		setPermissionAware(true);
		setStagingAware(false);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "description", false);
		addSearchTerm(searchQuery, searchContext, "name", false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property property = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(property.eq(companyId));
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		UserGroup userGroup = (UserGroup)obj;

		deleteDocument(userGroup.getCompanyId(), userGroup.getUserGroupId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		UserGroup userGroup = (UserGroup)obj;

		Document document = getBaseModelDocument(PORTLET_ID, userGroup);

		document.addKeyword(Field.COMPANY_ID, userGroup.getCompanyId());
		document.addText(Field.DESCRIPTION, userGroup.getDescription());
		document.addText(Field.NAME, userGroup.getName());
		document.addKeyword(Field.USER_GROUP_ID, userGroup.getUserGroupId());

		return document;
	}

	@Override
	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("description")) {
			return "description";
		}
		else if (orderByCol.equals("name")) {
			return "name";
		}
		else {
			return orderByCol;
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String title = document.get("name");

		String content = null;

		String userGroupId = document.get(Field.USER_GROUP_ID);

		portletURL.setParameter(
			"struts_action", "/users_admin/edit_user_group");
		portletURL.setParameter("userGroupId", userGroupId);

		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		if (obj instanceof List<?>) {
			List<UserGroup> userGroups = (List<UserGroup>)obj;

			for (UserGroup userGroup : userGroups) {
				doReindex(userGroup);
			}
		}
		else if (obj instanceof Long) {
			long userGroupId = (Long)obj;

			UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
				userGroupId);

			doReindex(userGroup);
		}
		else if (obj instanceof long[]) {
			long[] userGroupIds = (long[])obj;

			Map<Long, Collection<Document>> documentsMap =
				new HashMap<Long, Collection<Document>>();

			for (long userGroupId : userGroupIds) {
				UserGroup userGroup = UserGroupLocalServiceUtil.fetchUserGroup(
					userGroupId);

				if (userGroup == null) {
					continue;
				}

				Document document = getDocument(userGroup);

				long companyId = userGroup.getCompanyId();

				Collection<Document> documents = documentsMap.get(companyId);

				if (documents == null) {
					documents = new ArrayList<Document>();

					documentsMap.put(companyId, documents);
				}

				documents.add(document);
			}

			for (Map.Entry<Long, Collection<Document>> entry :
					documentsMap.entrySet()) {

				long companyId = entry.getKey();
				Collection<Document> documents = entry.getValue();

				SearchEngineUtil.updateDocuments(
					getSearchEngineId(), companyId, documents);
			}
		}
		else if (obj instanceof UserGroup) {
			UserGroup userGroup = (UserGroup)obj;

			Document document = getDocument(userGroup);

			SearchEngineUtil.updateDocument(
				getSearchEngineId(), userGroup.getCompanyId(), document);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(classPK);

		doReindex(userGroup);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexUserGroups(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexUserGroups(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			UserGroup.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minUserGroupIdProjection = ProjectionFactoryUtil.min(
			"userGroupId");
		Projection maxUserGroupIdProjection = ProjectionFactoryUtil.max(
			"userGroupId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minUserGroupIdProjection);
		projectionList.add(maxUserGroupIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = UserGroupLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxUserGroupIds = results.get(0);

		if ((minAndMaxUserGroupIds[0] == null) ||
			(minAndMaxUserGroupIds[1] == null)) {

			return;
		}

		long minUserGroupId = (Long)minAndMaxUserGroupIds[0];
		long maxUserGroupId = (Long)minAndMaxUserGroupIds[1];

		long startUserGroupId = minUserGroupId;
		long endUserGroupId = startUserGroupId + DEFAULT_INTERVAL;

		while (startUserGroupId <= maxUserGroupId) {
			reindexUserGroups(companyId, startUserGroupId, endUserGroupId);

			startUserGroupId = endUserGroupId;
			endUserGroupId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexUserGroups(
			long companyId, long startUserGroupId, long endUserGroupId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			UserGroup.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("userGroupId");

		dynamicQuery.add(property.ge(startUserGroupId));
		dynamicQuery.add(property.lt(endUserGroupId));

		addReindexCriteria(dynamicQuery, companyId);

		List<UserGroup> userGroups = UserGroupLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		if (userGroups.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			userGroups.size());

		for (UserGroup userGroup : userGroups) {
			Document document = getDocument(userGroup);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}