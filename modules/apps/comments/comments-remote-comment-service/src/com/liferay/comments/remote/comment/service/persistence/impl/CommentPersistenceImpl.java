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

package com.liferay.comments.remote.comment.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.comments.remote.comment.exception.NoSuchCommentException;
import com.liferay.comments.remote.comment.model.Comment;
import com.liferay.comments.remote.comment.model.impl.CommentImpl;
import com.liferay.comments.remote.comment.model.impl.CommentModelImpl;
import com.liferay.comments.remote.comment.service.persistence.CommentPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the comment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CommentPersistence
 * @see com.liferay.comments.remote.comment.service.persistence.CommentUtil
 * @generated
 */
@ProviderType
public class CommentPersistenceImpl extends BasePersistenceImpl<Comment>
	implements CommentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommentUtil} to access the comment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommentModelImpl.ENTITY_CACHE_ENABLED,
			CommentModelImpl.FINDER_CACHE_ENABLED, CommentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommentModelImpl.ENTITY_CACHE_ENABLED,
			CommentModelImpl.FINDER_CACHE_ENABLED, CommentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommentModelImpl.ENTITY_CACHE_ENABLED,
			CommentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public CommentPersistenceImpl() {
		setModelClass(Comment.class);
	}

	/**
	 * Caches the comment in the entity cache if it is enabled.
	 *
	 * @param comment the comment
	 */
	@Override
	public void cacheResult(Comment comment) {
		EntityCacheUtil.putResult(CommentModelImpl.ENTITY_CACHE_ENABLED,
			CommentImpl.class, comment.getPrimaryKey(), comment);

		comment.resetOriginalValues();
	}

	/**
	 * Caches the comments in the entity cache if it is enabled.
	 *
	 * @param comments the comments
	 */
	@Override
	public void cacheResult(List<Comment> comments) {
		for (Comment comment : comments) {
			if (EntityCacheUtil.getResult(
						CommentModelImpl.ENTITY_CACHE_ENABLED,
						CommentImpl.class, comment.getPrimaryKey()) == null) {
				cacheResult(comment);
			}
			else {
				comment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all comments.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(CommentImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the comment.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Comment comment) {
		EntityCacheUtil.removeResult(CommentModelImpl.ENTITY_CACHE_ENABLED,
			CommentImpl.class, comment.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Comment> comments) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Comment comment : comments) {
			EntityCacheUtil.removeResult(CommentModelImpl.ENTITY_CACHE_ENABLED,
				CommentImpl.class, comment.getPrimaryKey());
		}
	}

	/**
	 * Creates a new comment with the primary key. Does not add the comment to the database.
	 *
	 * @param commentId the primary key for the new comment
	 * @return the new comment
	 */
	@Override
	public Comment create(long commentId) {
		Comment comment = new CommentImpl();

		comment.setNew(true);
		comment.setPrimaryKey(commentId);

		return comment;
	}

	/**
	 * Removes the comment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commentId the primary key of the comment
	 * @return the comment that was removed
	 * @throws com.liferay.comments.remote.comment.NoSuchCommentException if a comment with the primary key could not be found
	 */
	@Override
	public Comment remove(long commentId) throws NoSuchCommentException {
		return remove((Serializable)commentId);
	}

	/**
	 * Removes the comment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the comment
	 * @return the comment that was removed
	 * @throws com.liferay.comments.remote.comment.NoSuchCommentException if a comment with the primary key could not be found
	 */
	@Override
	public Comment remove(Serializable primaryKey)
		throws NoSuchCommentException {
		Session session = null;

		try {
			session = openSession();

			Comment comment = (Comment)session.get(CommentImpl.class, primaryKey);

			if (comment == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCommentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(comment);
		}
		catch (NoSuchCommentException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Comment removeImpl(Comment comment) {
		comment = toUnwrappedModel(comment);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(comment)) {
				comment = (Comment)session.get(CommentImpl.class,
						comment.getPrimaryKeyObj());
			}

			if (comment != null) {
				session.delete(comment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (comment != null) {
			clearCache(comment);
		}

		return comment;
	}

	@Override
	public Comment updateImpl(Comment comment) {
		comment = toUnwrappedModel(comment);

		boolean isNew = comment.isNew();

		CommentModelImpl commentModelImpl = (CommentModelImpl)comment;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (comment.getCreateDate() == null)) {
			if (serviceContext == null) {
				comment.setCreateDate(now);
			}
			else {
				comment.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				comment.setModifiedDate(now);
			}
			else {
				comment.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (comment.isNew()) {
				session.save(comment);

				comment.setNew(false);
			}
			else {
				session.merge(comment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(CommentModelImpl.ENTITY_CACHE_ENABLED,
			CommentImpl.class, comment.getPrimaryKey(), comment, false);

		comment.resetOriginalValues();

		return comment;
	}

	protected Comment toUnwrappedModel(Comment comment) {
		if (comment instanceof CommentImpl) {
			return comment;
		}

		CommentImpl commentImpl = new CommentImpl();

		commentImpl.setNew(comment.isNew());
		commentImpl.setPrimaryKey(comment.getPrimaryKey());

		commentImpl.setCommentId(comment.getCommentId());
		commentImpl.setUserId(comment.getUserId());
		commentImpl.setUserName(comment.getUserName());
		commentImpl.setCreateDate(comment.getCreateDate());
		commentImpl.setModifiedDate(comment.getModifiedDate());
		commentImpl.setBody(comment.getBody());

		return commentImpl;
	}

	/**
	 * Returns the comment with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the comment
	 * @return the comment
	 * @throws com.liferay.comments.remote.comment.NoSuchCommentException if a comment with the primary key could not be found
	 */
	@Override
	public Comment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCommentException {
		Comment comment = fetchByPrimaryKey(primaryKey);

		if (comment == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCommentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return comment;
	}

	/**
	 * Returns the comment with the primary key or throws a {@link com.liferay.comments.remote.comment.NoSuchCommentException} if it could not be found.
	 *
	 * @param commentId the primary key of the comment
	 * @return the comment
	 * @throws com.liferay.comments.remote.comment.NoSuchCommentException if a comment with the primary key could not be found
	 */
	@Override
	public Comment findByPrimaryKey(long commentId)
		throws NoSuchCommentException {
		return findByPrimaryKey((Serializable)commentId);
	}

	/**
	 * Returns the comment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the comment
	 * @return the comment, or <code>null</code> if a comment with the primary key could not be found
	 */
	@Override
	public Comment fetchByPrimaryKey(Serializable primaryKey) {
		Comment comment = (Comment)EntityCacheUtil.getResult(CommentModelImpl.ENTITY_CACHE_ENABLED,
				CommentImpl.class, primaryKey);

		if (comment == _nullComment) {
			return null;
		}

		if (comment == null) {
			Session session = null;

			try {
				session = openSession();

				comment = (Comment)session.get(CommentImpl.class, primaryKey);

				if (comment != null) {
					cacheResult(comment);
				}
				else {
					EntityCacheUtil.putResult(CommentModelImpl.ENTITY_CACHE_ENABLED,
						CommentImpl.class, primaryKey, _nullComment);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(CommentModelImpl.ENTITY_CACHE_ENABLED,
					CommentImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return comment;
	}

	/**
	 * Returns the comment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commentId the primary key of the comment
	 * @return the comment, or <code>null</code> if a comment with the primary key could not be found
	 */
	@Override
	public Comment fetchByPrimaryKey(long commentId) {
		return fetchByPrimaryKey((Serializable)commentId);
	}

	@Override
	public Map<Serializable, Comment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Comment> map = new HashMap<Serializable, Comment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Comment comment = fetchByPrimaryKey(primaryKey);

			if (comment != null) {
				map.put(primaryKey, comment);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Comment comment = (Comment)EntityCacheUtil.getResult(CommentModelImpl.ENTITY_CACHE_ENABLED,
					CommentImpl.class, primaryKey);

			if (comment == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, comment);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMENT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (Comment comment : (List<Comment>)q.list()) {
				map.put(comment.getPrimaryKeyObj(), comment);

				cacheResult(comment);

				uncachedPrimaryKeys.remove(comment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(CommentModelImpl.ENTITY_CACHE_ENABLED,
					CommentImpl.class, primaryKey, _nullComment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the comments.
	 *
	 * @return the comments
	 */
	@Override
	public List<Comment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of comments
	 * @param end the upper bound of the range of comments (not inclusive)
	 * @return the range of comments
	 */
	@Override
	public List<Comment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of comments
	 * @param end the upper bound of the range of comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of comments
	 */
	@Override
	public List<Comment> findAll(int start, int end,
		OrderByComparator<Comment> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Comment> list = (List<Comment>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COMMENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMENT;

				if (pagination) {
					sql = sql.concat(CommentModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Comment>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Comment>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the comments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Comment comment : findAll()) {
			remove(comment);
		}
	}

	/**
	 * Returns the number of comments.
	 *
	 * @return the number of comments
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMENT);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the comment persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(CommentImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_COMMENT = "SELECT comment FROM Comment comment";
	private static final String _SQL_SELECT_COMMENT_WHERE_PKS_IN = "SELECT comment FROM Comment comment WHERE commentId IN (";
	private static final String _SQL_COUNT_COMMENT = "SELECT COUNT(comment) FROM Comment comment";
	private static final String _ORDER_BY_ENTITY_ALIAS = "comment.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Comment exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(CommentPersistenceImpl.class);
	private static final Comment _nullComment = new CommentImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Comment> toCacheModel() {
				return _nullCommentCacheModel;
			}
		};

	private static final CacheModel<Comment> _nullCommentCacheModel = new CacheModel<Comment>() {
			@Override
			public Comment toEntityModel() {
				return _nullComment;
			}
		};
}