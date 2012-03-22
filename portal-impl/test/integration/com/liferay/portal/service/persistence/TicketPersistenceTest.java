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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchTicketException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.impl.TicketModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class TicketPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (TicketPersistence)PortalBeanLocatorUtil.locate(TicketPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Ticket ticket = _persistence.create(pk);

		Assert.assertNotNull(ticket);

		Assert.assertEquals(ticket.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Ticket newTicket = addTicket();

		_persistence.remove(newTicket);

		Ticket existingTicket = _persistence.fetchByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertNull(existingTicket);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTicket();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Ticket newTicket = _persistence.create(pk);

		newTicket.setCompanyId(ServiceTestUtil.nextLong());

		newTicket.setCreateDate(ServiceTestUtil.nextDate());

		newTicket.setClassNameId(ServiceTestUtil.nextLong());

		newTicket.setClassPK(ServiceTestUtil.nextLong());

		newTicket.setKey(ServiceTestUtil.randomString());

		newTicket.setType(ServiceTestUtil.nextInt());

		newTicket.setExtraInfo(ServiceTestUtil.randomString());

		newTicket.setExpirationDate(ServiceTestUtil.nextDate());

		_persistence.update(newTicket, false);

		Ticket existingTicket = _persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertEquals(existingTicket.getTicketId(),
			newTicket.getTicketId());
		Assert.assertEquals(existingTicket.getCompanyId(),
			newTicket.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingTicket.getCreateDate()),
			Time.getShortTimestamp(newTicket.getCreateDate()));
		Assert.assertEquals(existingTicket.getClassNameId(),
			newTicket.getClassNameId());
		Assert.assertEquals(existingTicket.getClassPK(), newTicket.getClassPK());
		Assert.assertEquals(existingTicket.getKey(), newTicket.getKey());
		Assert.assertEquals(existingTicket.getType(), newTicket.getType());
		Assert.assertEquals(existingTicket.getExtraInfo(),
			newTicket.getExtraInfo());
		Assert.assertEquals(Time.getShortTimestamp(
				existingTicket.getExpirationDate()),
			Time.getShortTimestamp(newTicket.getExpirationDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Ticket newTicket = addTicket();

		Ticket existingTicket = _persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertEquals(existingTicket, newTicket);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchTicketException");
		}
		catch (NoSuchTicketException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Ticket newTicket = addTicket();

		Ticket existingTicket = _persistence.fetchByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertEquals(existingTicket, newTicket);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Ticket missingTicket = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTicket);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Ticket newTicket = addTicket();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				Ticket.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ticketId",
				newTicket.getTicketId()));

		List<Ticket> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Ticket existingTicket = result.get(0);

		Assert.assertEquals(existingTicket, newTicket);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				Ticket.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ticketId",
				ServiceTestUtil.nextLong()));

		List<Ticket> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Ticket newTicket = addTicket();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				Ticket.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ticketId"));

		Object newTicketId = newTicket.getTicketId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ticketId",
				new Object[] { newTicketId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTicketId = result.get(0);

		Assert.assertEquals(existingTicketId, newTicketId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				Ticket.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ticketId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ticketId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Ticket newTicket = addTicket();

		_persistence.clearCache();

		TicketModelImpl existingTicketModelImpl = (TicketModelImpl)_persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingTicketModelImpl.getKey(),
				existingTicketModelImpl.getOriginalKey()));
	}

	protected Ticket addTicket() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Ticket ticket = _persistence.create(pk);

		ticket.setCompanyId(ServiceTestUtil.nextLong());

		ticket.setCreateDate(ServiceTestUtil.nextDate());

		ticket.setClassNameId(ServiceTestUtil.nextLong());

		ticket.setClassPK(ServiceTestUtil.nextLong());

		ticket.setKey(ServiceTestUtil.randomString());

		ticket.setType(ServiceTestUtil.nextInt());

		ticket.setExtraInfo(ServiceTestUtil.randomString());

		ticket.setExpirationDate(ServiceTestUtil.nextDate());

		_persistence.update(ticket, false);

		return ticket;
	}

	private TicketPersistence _persistence;
}