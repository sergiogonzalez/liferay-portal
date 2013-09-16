/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.AssertUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 * @author Roberto Díaz
 */
public class ArrayUtilTest {

	@Test
	public void testContainsAllBooleanArray() throws Exception {
		boolean[] array1 = {true};
		boolean[] array2 = {true, false};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllByteArray() throws Exception {
		byte[] array1 = {1, 2};
		byte[] array2 = {1, 2, 3};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllCharArray() throws Exception {
		char[] array1 = {'a', 'b'};
		char[] array2 = {'a', 'b', 'c'};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllDoubleArray() throws Exception {
		double[] array1 = {1.5D, 2.5D};
		double[] array2 = {1.5D, 2.5D, 3.5D};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllFloatArray() throws Exception {
		float[] array1 = {1.5f, 2.5f};
		float[] array2 = {1.5f, 2.5f, 3.5f};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllIntArray() throws Exception {
		int[] array1 = {1, 2};
		int[] array2 = {1, 2, 3};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllLongArray() throws Exception {
		long[] array1 = {1L, 2L};
		long[] array2 = {1L, 2L, 3L};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllShortArray() throws Exception {
		short[] array1 = {1, 2};
		short[] array2 = {1, 2, 3};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAllUserArray() throws Exception {
		User brian = new User("brian", 20);
		User julio = new User("julio", 20);
		User sergio = new User("sergio", 20);

		User[] array1 = {julio, sergio};
		User[] array2 = {brian, julio, sergio};

		Assert.assertFalse(ArrayUtil.containsAll(array1, array2));
		Assert.assertTrue(ArrayUtil.containsAll(array2, array1));
	}

	@Test
	public void testContainsAnyBooleanArray() throws Exception {
		boolean[] array1 = {true, true, true};
		boolean[] array2 = {true, false};
		boolean[] array3 = {false};

		Assert.assertTrue(ArrayUtil.containsAny(array1, array2));
		Assert.assertFalse(ArrayUtil.containsAny(array1, array3));
	}

	@Test
	public void testContainsAnyByteArray() throws Exception {
		byte[] array1 = {1, 2, 3, 4};
		byte[] array2 = {1, 5, 6, 7};
		byte[] array3 = {5, 6, 7};

		Assert.assertTrue(ArrayUtil.containsAny(array1, array2));
		Assert.assertFalse(ArrayUtil.containsAny(array1, array3));
	}

	@Test
	public void testContainsAnyCharArray() throws Exception {
		char[] array1 = {'a', 'b', 'c', 'd'};
		char[] array2 = {'a', 'e', 'f', 'g'};
		char[] array3 = {'e', 'f', 'g'};

		Assert.assertTrue(ArrayUtil.containsAny(array1, array2));
		Assert.assertFalse(ArrayUtil.containsAny(array1, array3));
	}

	@Test
	public void testContainsAnyDoubleArray() throws Exception {
		double[] array1 = {1.5D, 2.5D};
		double[] array2 = {1.5D, 3.5D, 4.5D};
		double[] array3 = {3.5D, 4.5D};

		Assert.assertTrue(ArrayUtil.containsAny(array1, array2));
		Assert.assertFalse(ArrayUtil.containsAny(array1, array3));
	}

	@Test
	public void testContainsAnyFloatArray() throws Exception {
		float[] array1 = {1.5f, 2.5f};
		float[] array2 = {1.5f, 3.5f, 4.5f};
		float[] array3 = {3.5f, 4.5f};

		Assert.assertTrue(ArrayUtil.containsAny(array1, array2));
		Assert.assertFalse(ArrayUtil.containsAny(array1, array3));
	}

	@Test
	public void testContainsAnyIntArray() throws Exception {
		int[] array1 = {1, 2, 3};
		int[] array2 = {1, 4, 5};
		int[] array3 = {4, 5};

		Assert.assertTrue(ArrayUtil.containsAny(array1, array2));
		Assert.assertFalse(ArrayUtil.containsAny(array1, array3));
	}

	@Test
	public void testContainsAnyLongArray() throws Exception {
		long[] array1 = {1L, 2L};
		long[] array2 = {1L, 3L, 4L};
		long[] array3 = {3L, 4L};

		Assert.assertTrue(ArrayUtil.containsAny(array1, array2));
		Assert.assertFalse(ArrayUtil.containsAny(array1, array3));
	}

	@Test
	public void testContainsAnyShortArray() throws Exception {
		short[] array1 = {1, 2};
		short[] array2 = {1, 4, 5};
		short[] array3 = {4, 5};

		Assert.assertTrue(ArrayUtil.containsAny(array1, array2));
		Assert.assertFalse(ArrayUtil.containsAny(array1, array3));
	}

	@Test
	public void testContainsAnyUserArray() throws Exception {
		User brian = new User("brian", 20);
		User eudaldo = new User("eudaldo", 20);
		User julio = new User("julio", 20);
		User sergio = new User("sergio", 20);

		User[] array1 = {brian, julio};
		User[] array2 = {julio, sergio, eudaldo};
		User[] array3 = {sergio, eudaldo};

		Assert.assertTrue(ArrayUtil.containsAny(array1, array2));
		Assert.assertFalse(ArrayUtil.containsAny(array1, array3));
	}

	@Test
	public void testContainsBooleanArray() throws Exception {
		boolean[] array1 = {true, true};

		Assert.assertFalse(ArrayUtil.contains(array1, false));
		Assert.assertTrue(ArrayUtil.contains(array1, true));
	}

	@Test
	public void testContainsByteArray() throws Exception {
		byte[] array1 = {2, 3};

		Assert.assertFalse(ArrayUtil.contains(array1, (byte)1));
		Assert.assertTrue(ArrayUtil.contains(array1, (byte)2));
	}

	@Test
	public void testContainsCharArray() throws Exception {
		char[] array1 = {'b', 'c'};

		Assert.assertFalse(ArrayUtil.contains(array1, 'a'));
		Assert.assertTrue(ArrayUtil.contains(array1, 'b'));
	}

	@Test
	public void testContainsDoubleArray() throws Exception {
		double[] array = {2.5D, 3.5D};

		Assert.assertFalse(ArrayUtil.contains(array, 1.5D));
		Assert.assertTrue(ArrayUtil.contains(array, 2.5D));
	}

	@Test
	public void testContainsFloatArray() throws Exception {
		float[] array = {2.5f, 3.5f};

		Assert.assertFalse(ArrayUtil.contains(array, 1.5f));
		Assert.assertTrue(ArrayUtil.contains(array, 2.5f));
	}

	@Test
	public void testContainsIntArray() throws Exception {
		int[] array = {2, 3};

		Assert.assertFalse(ArrayUtil.contains(array, 1));
		Assert.assertTrue(ArrayUtil.contains(array, 2));
	}

	@Test
	public void testContainsLongArray() throws Exception {
		long[] array = {2L, 3L};

		Assert.assertFalse(ArrayUtil.contains(array, 1L));
		Assert.assertTrue(ArrayUtil.contains(array, 2L));
	}

	@Test
	public void testContainsShortArray() throws Exception {
		short[] array = {2, 3};

		Assert.assertFalse(ArrayUtil.contains(array, (short)1));
		Assert.assertTrue(ArrayUtil.contains(array, (short)2));
	}

	@Test
	public void testContainsUserArray() throws Exception {
		User brian = new User("brian", 20);
		User julio = new User("julio", 20);
		User sergio = new User("sergio", 20);

		User[] array = {julio, sergio};

		Assert.assertFalse(ArrayUtil.contains(array, brian));
		Assert.assertTrue(ArrayUtil.contains(array, julio));
	}

	@Test
	public void testFilterDoubleArray() {
		double[] array = ArrayUtil.filter(
			new double[] {0.1, 0.2, 1.2, 1.3}, _doublePredicateFilter);

		Assert.assertEquals(2, array.length);
		AssertUtils.assertEquals(new double[] {1.2, 1.3}, array);
	}

	@Test
	public void testFilterDoubleEmptyArray() {
		double[] array = ArrayUtil.filter(
			new double[0], _doublePredicateFilter);

		Assert.assertEquals(0, array.length);
		AssertUtils.assertEquals(new double[0], array);
	}

	@Test
	public void testFilterDoubleNullArray() {
		double[] array = null;

		double[] filteredArray = ArrayUtil.filter(
			array, _doublePredicateFilter);

		Assert.assertNull(filteredArray);
	}

	@Test
	public void testFilterIntegerArray() {
		int[] array = ArrayUtil.filter(
			new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, _integerPredicateFilter);

		Assert.assertEquals(5, array.length);
		Assert.assertArrayEquals(new int[] {5, 6, 7, 8, 9}, array);
	}

	@Test
	public void testFilterIntegerEmptyArray() {
		int[] array = ArrayUtil.filter(new int[0], _integerPredicateFilter);

		Assert.assertEquals(0, array.length);
		Assert.assertArrayEquals(new int[0], array);
	}

	@Test
	public void testFilterIntegerNullArray() {
		int[] array = null;

		int[] filteredArray = ArrayUtil.filter(array, _integerPredicateFilter);

		Assert.assertNull(filteredArray);
	}

	@Test
	public void testFilterUserArray() {
		User[] array = ArrayUtil.filter(
			new User[] {new User("james", 17), new User("john", 26)},
			_userPredicateFilter);

		Assert.assertEquals(1, array.length);

		Assert.assertEquals("john", array[0].getName());
		Assert.assertEquals(26, array[0].getAge());
	}

	@Test
	public void testFilterUserEmptyArray() {
		User[] array = ArrayUtil.filter(new User[0], _userPredicateFilter);

		Assert.assertEquals(0, array.length);
	}

	@Test
	public void testFilterUserNullArray() {
		User[] array = ArrayUtil.filter(null, _userPredicateFilter);

		Assert.assertNull(array);
	}

	@Test
	public void testIsEmptyBooleanArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((boolean[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new boolean[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new boolean[] {true, true}));
	}

	@Test
	public void testIsEmptyByteArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((byte[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new byte[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new byte[] {1, 2}));
	}

	@Test
	public void testIsEmptyCharArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((char[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new char[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new char[] {1, 2}));
	}

	@Test
	public void testIsEmptyDoubleArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((double[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new double[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new double[] {1, 2}));
	}

	@Test
	public void testIsEmptyFloatArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((float[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new float[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new float[] {1, 2}));
	}

	@Test
	public void testIsEmptyIntArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((int[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new int[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new int[] {1, 2}));
	}

	@Test
	public void testIsEmptyLongArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((long[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new long[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new long[] {1, 2}));
	}

	@Test
	public void testIsEmptyShortArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((short[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new short[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new short[] {1, 2}));
	}

	@Test
	public void testIsEmptyUserArray() {
		Assert.assertTrue(ArrayUtil.isEmpty((User[])null));
		Assert.assertTrue(ArrayUtil.isEmpty(new User[0]));
		Assert.assertFalse(
			ArrayUtil.isEmpty(
				new User[] {
					new User("brian", 20), new User("julio", 20),
					new User("sergio", 20)
				}));
	}

	@Test
	public void testIsNotEmptyBooleanArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((boolean[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new boolean[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new boolean[] {true, true}));
	}

	@Test
	public void testIsNotEmptyByteArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((byte[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new byte[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new byte[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyCharArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((char[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new char[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new char[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyDoubleArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((double[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new double[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new double[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyFloatArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((float[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new float[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new float[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyIntArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((int[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new int[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new int[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyLongArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((long[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new long[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new long[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyShortArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((short[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new short[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new short[] {1, 2}));
	}

	@Test
	public void testIsNotEmptyUserArray() {
		Assert.assertFalse(ArrayUtil.isNotEmpty((User[])null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new User[0]));
		Assert.assertTrue(
			ArrayUtil.isNotEmpty(
				new User[] {
					new User("brian", 20), new User("julio", 20),
					new User("sergio", 20)
				}));
	}

	@Test
	public void testRemoveAllBooleanArray() {
		boolean[] array1 = {true, true, false, false};
		boolean[] array2 = {true, true};

		boolean[] result = ArrayUtil.removeAll(array1, array2);

		Assert.assertEquals(result[0], false);
		Assert.assertEquals(result[1], false);
	}

	@Test
	public void testRemoveAllByteArray() {
		byte[] array1 = {1, 2, 3, 4};
		byte[] array2 = {1, 2, 5};

		byte[] result = ArrayUtil.removeAll(array1, array2);

		Assert.assertEquals(result[0], 3);
		Assert.assertEquals(result[1], 4);
	}

	@Test
	public void testRemoveAllCharArray() {
		char[] array1 = {'a', 'b', 'c', 'd'};
		char[] array2 = {'a', 'b', 'e'};

		char[] result = ArrayUtil.removeAll(array1, array2);

		Assert.assertEquals(result[0], 'c');
		Assert.assertEquals(result[1], 'd');
	}

	@Test
	public void testRemoveAllDoubleArray() {
		double[] array1 = {1.5D, 2.5D, 3.5D, 4.5D};
		double[] array2 = {1.5D, 2.5D, 5.5D};

		double[] result = ArrayUtil.removeAll(array1, array2);

		Assert.assertTrue(result[0] == 3.5D);
		Assert.assertTrue(result[1] == 4.5D);
	}

	@Test
	public void testRemoveAllFloatArray() {
		float[] array1 = {1.5f, 2.5f, 3.5f, 4.5f};
		float[] array2 = {1.5f, 2.5f, 5.5f};

		float[] result = ArrayUtil.removeAll(array1, array2);

		Assert.assertTrue(result[0] == 3.5f);
		Assert.assertTrue(result[1] == 4.5f);
	}

	@Test
	public void testRemoveAllIntArray() {
		int[] array1 = {1, 2, 3, 4};
		int[] array2 = {1, 2, 5};

		int[] result = ArrayUtil.removeAll(array1, array2);

		Assert.assertEquals(result[0], 3);
		Assert.assertEquals(result[1], 4);
	}

	@Test
	public void testRemoveAllLongArray() {
		long[] array1 = {1L, 2L, 3L, 4L};
		long[] array2 = {1L, 2L, 5L};

		long[] result = ArrayUtil.removeAll(array1, array2);

		Assert.assertEquals(result[0], 3L);
		Assert.assertEquals(result[1], 4L);
	}

	@Test
	public void testRemoveAllShortArray() {
		short[] array1 = {1, 2, 3, 4};
		short[] array2 = {1, 2, 5};

		short[] result = ArrayUtil.removeAll(array1, array2);

		Assert.assertEquals(result[0], 3);
		Assert.assertEquals(result[1], 4);
	}

	@Test
	public void testRemoveAllStringArray() {
		String[] array1 = {"a", "b", "c", "d"};
		String[] array2 = {"c", "d", "e"};

		String[] result = ArrayUtil.removeAll(array1, array2);

		Assert.assertEquals(result[0], "a");
		Assert.assertEquals(result[1], "b");
	}

	@Test
	public void testReverseBooleanArray() throws Exception {
		boolean[] array = new boolean[] {true, true, false};

		ArrayUtil.reverse(array);

		Assert.assertFalse(array[0]);
		Assert.assertTrue(array[1]);
		Assert.assertTrue(array[2]);
	}

	@Test
	public void testReverseCharArray() throws Exception {
		char[] array = new char[] {'a', 'b', 'c'};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new char[] {'c', 'b', 'a'}, array);
	}

	@Test
	public void testReverseDoubleArray() throws Exception {
		double[] array = new double[] {111.0, 222.0, 333.0};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new double[] {333.0, 222.0, 111.0}, array, 0);
	}

	@Test
	public void testReverseIntArray() throws Exception {
		int[] array = new int[] {111, 222, 333};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new int[] {333, 222, 111}, array);
	}

	@Test
	public void testReverseLongArray() throws Exception {
		long[] array = new long[] {111, 222, 333};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new long[] {333, 222, 111}, array);
	}

	@Test
	public void testReverseShortArray() throws Exception {
		short[] array = new short[] {111, 222, 333};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new short[] {333, 222, 111}, array);
	}

	@Test
	public void testReverseStringArray() throws Exception {
		String[] array = new String[] {"aaa", "bbb", "ccc"};

		ArrayUtil.reverse(array);

		Assert.assertArrayEquals(new String[] {"ccc", "bbb", "aaa"}, array);
	}

	@Test
	public void testToDoubleArray() throws Exception {
		List<Double> list = new ArrayList<Double>();

		list.add(1.0);
		list.add(2.0);

		double[] array = ArrayUtil.toDoubleArray(list);

		Assert.assertEquals(array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Double value = list.get(i);

			AssertUtils.assertEquals(value.doubleValue(), array[i]);
		}
	}

	@Test
	public void testToFloatArray() throws Exception {
		List<Float> list = new ArrayList<Float>();

		list.add(1.0F);
		list.add(2.0F);

		float[] array = ArrayUtil.toFloatArray(list);

		Assert.assertEquals(array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Float value = list.get(i);

			AssertUtils.assertEquals(value.floatValue(), array[i]);
		}
	}

	@Test
	public void testToIntArray() throws Exception {
		List<Integer> list = new ArrayList<Integer>();

		list.add(1);
		list.add(2);

		int[] array = ArrayUtil.toIntArray(list);

		Assert.assertEquals(array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Integer value = list.get(i);

			Assert.assertEquals(value.intValue(), array[i]);
		}
	}

	@Test
	public void testToLongArray() throws Exception {
		List<Long> list = new ArrayList<Long>();

		list.add(1L);
		list.add(2L);

		long[] array = ArrayUtil.toLongArray(list);

		Assert.assertEquals(array.length, list.size());

		for (int i = 0; i < list.size(); i++) {
			Long value = list.get(i);

			Assert.assertEquals(value.longValue(), array[i]);
		}
	}

	private PredicateFilter<Double> _doublePredicateFilter =
		new PredicateFilter<Double>() {

			@Override
			public boolean filter(Double d) {
				return d >= 1.1;
			}

		};

	private PredicateFilter<Integer> _integerPredicateFilter =
		new PredicateFilter<Integer>() {

			@Override
			public boolean filter(Integer i) {
				return i >= 5;
			}

		};

	private PredicateFilter<User> _userPredicateFilter =
		new PredicateFilter<User>() {

			@Override
			public boolean filter(User user) {
				return user.getAge() > 18;
			}

		};

	private class User {

		public User(String name, int age) {
			_name = name;
			_age = age;
		}

		public int getAge() {
			return _age;
		}

		public String getName() {
			return _name;
		}

		private int _age;
		private String _name;

	}

}