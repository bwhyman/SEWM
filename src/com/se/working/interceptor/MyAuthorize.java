package com.se.working.interceptor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 支持类与方法，默认为teacher权限
 * @author BO
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface MyAuthorize {
	Authorize[] value() default Authorize.TEACHER;
	
	public enum Authorize {
		TEACHER, ADMIN, SUPERADMIN;
	}
}
