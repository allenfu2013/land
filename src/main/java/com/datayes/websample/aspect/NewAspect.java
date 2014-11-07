package com.datayes.websample.aspect;

import org.aspectj.lang.ProceedingJoinPoint;


public class NewAspect {

	public Object aspect1(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("aspect1");
		return pjp.proceed();
	}

}
