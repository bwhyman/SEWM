package com.se.working.aop;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 基于AOP实现 <br>
 * 基于给定字符串，从httpsession中提取出相应对象 <br>
 * 基于给定字符串从httpsession中提取对象，<br>
 * 必须将从httpsession中注入的对象置于方法的第一位 <br>
 * 默认为user
 * @author BO
 *
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface MySession {
}
