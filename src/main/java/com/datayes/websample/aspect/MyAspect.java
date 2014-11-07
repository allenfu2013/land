package com.datayes.websample.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class MyAspect {

	@Around("execution(* com.datayes.websample.service.SampleService.*(..))")
	public Object aspect(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("aspect");
		return pjp.proceed();
	}
}
