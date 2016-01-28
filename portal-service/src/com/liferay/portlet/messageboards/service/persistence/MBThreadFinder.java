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

package com.liferay.portlet.messageboards.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface MBThreadFinder {
	public int countByG_U(long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public int countByG_C(long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public int countByG_U_C(long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public int countByG_U_LPD(long groupId, long userId,
		java.util.Date lastPostDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public int countByG_U_A(long groupId, long userId, boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public int countByS_G_U(long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public int countByG_U_C_A(long groupId, long userId, long[] categoryIds,
		boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public int countByS_G_U_C(long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public int filterCountByG_C(long groupId, long categoryId);

	public int filterCountByG_C(long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public int filterCountByS_G_U_C(long groupId, long userId,
		long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_C_S_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.messageboards.model.MBThread> orderByComparator)
		throws com.liferay.portlet.messageboards.exception.NoSuchThreadException;

	public com.liferay.portlet.messageboards.model.MBThread[] filterFindByG_C_NotS_PrevAndNext(
		long threadId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.messageboards.model.MBThread> orderByComparator)
		throws com.liferay.portlet.messageboards.exception.NoSuchThreadException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> filterFindByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByNoAssets();

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_C(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U_C(
		long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U_LPD(
		long groupId, long userId, java.util.Date lastPostDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U_A(
		long groupId, long userId, boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByS_G_U(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByG_U_C_A(
		long groupId, long userId, long[] categoryIds, boolean anonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition);
}