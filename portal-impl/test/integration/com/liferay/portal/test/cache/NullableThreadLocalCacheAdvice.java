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

package com.liferay.portal.test.cache;

import com.liferay.portal.cache.ThreadLocalCacheAdvice;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * @author Miguel Pastor
 */
public class NullableThreadLocalCacheAdvice extends ThreadLocalCacheAdvice {

	@Override
	protected ThreadLocalCachable findAnnotation(
		MethodInvocation methodInvocation) {

		return getNullAnnotation();
	}

}