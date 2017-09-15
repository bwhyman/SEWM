package com.se.working.aop;

//@Component
//@Aspect
public class MyAspect {

	/**
	 * @args，仅用于拦截参数类型的注释，无法拦截参数的注释！！
	 * 
	 * @annotation，仅用于拦截方法注释 可直接将拦截对象传入 @Before("@annotation(mySession)") public
	 *                       void before(MySession mySession) {}
	 *                       除around外，其他行为可传入JoinPoint类型对象，但无法基于修改继续执行
	 */

	// @Around("@annotation(com.se.working.aop.MySession)")
	// @Around("execution(* com.se.working.controller..*.*(..))")

	/**
	 * 切入Controller MySession方法，从session中取出注释声明的相应对象，替换方法原参数<br>
	 * 
	 * @param joinPoint
	 * @param mySession
	 * @return
	 * @throws Throwable
	 */
	/*@Around("@annotation(mySession)")
	public Object aroundMySessionAttribute(ProceedingJoinPoint joinPoint, MySession mySession) throws Throwable {
		long start = System.nanoTime();
		// 获取方法参数值
		Object[] args = joinPoint.getArgs();
		// 获取方法
		MethodSignature mSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
		Method method = mSignature.getMethod();
		// 获取方法参数
		Parameter[] parameters = method.getParameters();
		// 获取当前线程request对象
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		if (session != null) {
			String[] values = mySession.value();
			for (int i = 0; i < parameters.length; i++) {
				for (String s : values) {
					if (parameters[i].getName().equals(s)) {
						args[i] = session.getAttribute(s);
					}
				}
			}
		}

		long end = System.nanoTime();
		LoggingUtils.info(end - start);
		// 基于修改后的参数值，执行
		return joinPoint.proceed(args);
	}*/
	/*@Around("@annotation(com.se.working.aop.MySession)")
	public Object aroundMySessionAttribute(ProceedingJoinPoint joinPoint) throws Throwable {
		// 获取方法参数值
		Object[] args = joinPoint.getArgs();
		// 获取方法
		 Signature signature = joinPoint.getStaticPart().getSignature();
		 MethodSignature mSignature = null;
		if (signature instanceof MethodSignature) {
			mSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
		} else {
			return joinPoint.proceed(args);
		}
		Method method = mSignature.getMethod();
		// 获取方法参数
		Parameter[] parameters = method.getParameters();
		// 获取当前线程request对象
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		for (int i = 0; i < parameters.length; i++) {
			// 判断参数注释
			if (parameters[i].isAnnotationPresent(MySessionAttribute.class)) {
				HttpSession session = attr.getRequest().getSession();
				if (session != null) {
					// 获取参数的MySessionAttribute注释对象
					MySessionAttribute mAttribute = parameters[i].getAnnotation(MySessionAttribute.class);
					// 基于注释中声明的值查在session中查找相应对象
					Object sObj = session.getAttribute(mAttribute.value());
					if (sObj != null) {
						// 替换方法中参数值对象
						args[i] = sObj;
					}
				}
			}
		}
		// 基于修改后的参数值，执行
		return joinPoint.proceed(args);
	}*/

}
