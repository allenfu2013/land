package com.datayes.websample.redis.aspect;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
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

import com.datayes.websample.redis.annotation.RedisSetCacheable;
import com.datayes.websample.redis.type.RedisOpsType;
import com.datayes.websample.redis.util.JSONUtil;
import com.datayes.websample.redis.util.MethodParserUtil;

@Component
@Aspect
public class RedisSetCacheAspect {

	@Autowired(required = false)
	RedisTemplate<String, String> redisTemplate;

	private static final Logger LOG = LoggerFactory.getLogger(RedisSetCacheAspect.class);

	private static final String REDIS_SET_CACHEABLE = "@annotation(com.datayes.websample.redis.annotation.RedisSetCacheable)";
	private static final String REDIS_STRING_VALUE = "";
	
	@SuppressWarnings("unchecked")
	@Around(REDIS_SET_CACHEABLE)
	public Object redisSetCacheable(ProceedingJoinPoint pjp) {
		Object ret = null;
		try {
			MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
			Method method = methodSignature.getMethod();
			RedisSetCacheable redisSetCacheable = method.getAnnotation(RedisSetCacheable.class);
			RedisOpsType opsType = redisSetCacheable.opsType();
			String key = redisSetCacheable.key();
			String valueType = redisSetCacheable.valueType();
			long expiration = redisSetCacheable.expiration();
			boolean isStringType = REDIS_STRING_VALUE.equals(valueType);
			String keyParam = MethodParserUtil.parseKeyParam(method, pjp.getArgs());
			if (keyParam != null) {
				key += keyParam;
			}
			
			LOG.info("redis set aspect invoked, method:{}, opsType:{}, valueType:{}, expiration:{}", 
					method.getName(), opsType, valueType, expiration);
			
			if (RedisOpsType.GET == opsType || RedisOpsType.GET_AND_PUT == opsType) {
				boolean existed = redisTemplate.hasKey(key);
				if (existed) {
					Set<String> set = redisTemplate.opsForSet().members(key);
					if (!isStringType) {
						Class<?> clazz = Class.forName(valueType);
						Set<Object> values = new HashSet<Object>();
						for (String json : set) {
							values.add(JSONUtil.parseJSON(json, clazz));
						}
						return values;
					} else {
						return set;
					}
				} else {
					ret = pjp.proceed();
				}
			} else if (RedisOpsType.UPDATE == opsType) {
				redisTemplate.delete(key);
				ret = pjp.proceed();
			}
			
			if (RedisOpsType.GET_AND_PUT == opsType || RedisOpsType.UPDATE == opsType) {
				Set<String> set = null;
				if (!isStringType) {
					set = new HashSet<String>();
					for (Object object : (Set<Object>) ret) {
						set.add(JSONUtil.toJSONString(object));
					}
				} else {
					set = (Set<String>) ret;
				}
				String[] values = new String[set.size()];
				redisTemplate.opsForSet().add(key, set.toArray(values));
				
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
