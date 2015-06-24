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

package com.liferay.comments.remote.comment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.comments.remote.comment.exception.NoSuchCommentException;
import com.liferay.comments.remote.comment.model.Comment;
import com.liferay.comments.remote.comment.service.persistence.CommentPersistence;
import com.liferay.comments.remote.comment.service.persistence.CommentUtil;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CommentPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = CommentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Comment> iterator = _comments.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Comment comment = _persistence.create(pk);

		Assert.assertNotNull(comment);

		Assert.assertEquals(comment.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Comment newComment = addComment();

		_persistence.remove(newComment);

		Comment existingComment = _persistence.fetchByPrimaryKey(newComment.getPrimaryKey());

		Assert.assertNull(existingComment);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addComment();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Comment newComment = _persistence.create(pk);

		newComment.setUserId(RandomTestUtil.nextLong());

		newComment.setUserName(RandomTestUtil.randomString());

		newComment.setCreateDate(RandomTestUtil.nextDate());

		newComment.setModifiedDate(RandomTestUtil.nextDate());

		newComment.setBody(RandomTestUtil.randomString());

		_comments.add(_persistence.update(newComment));

		Comment existingComment = _persistence.findByPrimaryKey(newComment.getPrimaryKey());

		Assert.assertEquals(existingComment.getCommentId(),
			newComment.getCommentId());
		Assert.assertEquals(existingComment.getUserId(), newComment.getUserId());
		Assert.assertEquals(existingComment.getUserName(),
			newComment.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingComment.getCreateDate()),
			Time.getShortTimestamp(newComment.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingComment.getModifiedDate()),
			Time.getShortTimestamp(newComment.getModifiedDate()));
		Assert.assertEquals(existingComment.getBody(), newComment.getBody());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Comment newComment = addComment();

		Comment existingComment = _persistence.findByPrimaryKey(newComment.getPrimaryKey());

		Assert.assertEquals(existingComment, newComment);
	}

	@Test(expected = NoSuchCommentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Comment> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Comment", "commentId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "body", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Comment newComment = addComment();

		Comment existingComment = _persistence.fetchByPrimaryKey(newComment.getPrimaryKey());

		Assert.assertEquals(existingComment, newComment);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Comment missingComment = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingComment);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Comment newComment1 = addComment();
		Comment newComment2 = addComment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newComment1.getPrimaryKey());
		primaryKeys.add(newComment2.getPrimaryKey());

		Map<Serializable, Comment> comments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, comments.size());
		Assert.assertEquals(newComment1,
			comments.get(newComment1.getPrimaryKey()));
		Assert.assertEquals(newComment2,
			comments.get(newComment2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Comment> comments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(comments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Comment newComment = addComment();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newComment.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Comment> comments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, comments.size());
		Assert.assertEquals(newComment, comments.get(newComment.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Comment> comments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(comments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Comment newComment = addComment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newComment.getPrimaryKey());

		Map<Serializable, Comment> comments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, comments.size());
		Assert.assertEquals(newComment, comments.get(newComment.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Comment newComment = addComment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Comment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commentId",
				newComment.getCommentId()));

		List<Comment> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Comment existingComment = result.get(0);

		Assert.assertEquals(existingComment, newComment);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Comment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commentId",
				RandomTestUtil.nextLong()));

		List<Comment> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Comment newComment = addComment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Comment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("commentId"));

		Object newCommentId = newComment.getCommentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commentId",
				new Object[] { newCommentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommentId = result.get(0);

		Assert.assertEquals(existingCommentId, newCommentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Comment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("commentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commentId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Comment addComment() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Comment comment = _persistence.create(pk);

		comment.setUserId(RandomTestUtil.nextLong());

		comment.setUserName(RandomTestUtil.randomString());

		comment.setCreateDate(RandomTestUtil.nextDate());

		comment.setModifiedDate(RandomTestUtil.nextDate());

		comment.setBody(RandomTestUtil.randomString());

		_comments.add(_persistence.update(comment));

		return comment;
	}

	private List<Comment> _comments = new ArrayList<Comment>();
	private CommentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}