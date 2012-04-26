package com.liferay.portal.security;

import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class SecurityServiceAdvice extends ChainableMethodAdvice {

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {

		Method targetMethod = methodInvocation.getMethod();

		PortalSecurityManager portalSecurityManager =
			PortalSecurityManager.getInstance();

		portalSecurityManager.accept(targetMethod);

		return null;
	}
}