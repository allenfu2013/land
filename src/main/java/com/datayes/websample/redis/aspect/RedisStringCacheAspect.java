package com.datayes.websample.redis.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.datayes.websample.redis.annotation.KeyParam;
import com.datayes.websample.redis.annotation.RedisStringCacheable;
import com.datayes.websample.redis.type.RedisOpsType;
import com.datayes.websample.redis.util.JSONUtil;
import com.datayes.websample.redis.util.MethodParserUtil;

@Component
@Aspect
public class RedisStringCacheAspect {

	@Autowired(required = false)
	RedisTemplate<String, String> redisTemplate;

	private static final Logger LOG = LoggerFactory.getLogger(RedisStringCacheAspect.class);

	private static final String REDIS_VALUE_CACHEABLE = "@annotation(com.datayes.websample.redis.annotation.RedisStringCacheable)";
	private static final String REDIS_STRING_VALUE = "";

	@Around(REDIS_VALUE_CACHEABLE)
	public Object redisValueCacheable(ProceedingJoinPoint pjp) {
		Object ret = null;
		try {
			MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
			Method method = methodSignature.getMethod();
			RedisStringCacheable redisValueCacheable = method.getAnnotation(RedisStringCacheable.class);
			RedisOpsType opsType = redisValueCacheable.opsType();
			String key = redisValueCacheable.key();
			String valueType = redisValueCacheable.valueType();
			long expiration = redisValueCacheable.expiration();
			boolean isStringTpye = REDIS_STRING_VALUE.equals(valueType);
			String keyParam = MethodParserUtil.parseKeyParam(method, pjp.getArgs());
			if (keyParam != null) {
				key += keyParam;
			}
			
			LOG.info("redis value aspect invoked, method:{}, key:{}, valueType:{}, expiration:{}",
					method.getName(), key, valueType, expiration);
			
			if (RedisOpsType.GET == opsType || RedisOpsType.GET_AND_PUT == opsType) {
				String value = redisTemplate.opsForValue().get(key);
				if (value == null) {
					ret = pjp.proceed();
				} else {
					return isStringTpye ? value : JSONUtil.parseJSON(value, Class.forName(valueType));
				}
			} else if (RedisOpsType.UPDATE == opsType) {
				redisTemplate.delete(key);
				ret = pjp.proceed();
			}
			
			if (RedisOpsType.GET_AND_PUT == opsType || RedisOpsType.UPDATE == opsType) {
				String value = isStringTpye ? (String)ret : JSONUtil.toJSONString(ret);
				redisTemplate.opsForValue().set(key, value);
				if (expiration > 0) {
					redisTemplate.expire(key, expiration, TimeUnit.SECONDS);
				}
			}
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
		}
		return ret;
	}
	
}
