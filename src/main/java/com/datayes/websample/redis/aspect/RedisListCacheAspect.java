package com.datayes.websample.redis.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
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

import com.datayes.websample.redis.annotation.RedisListCacheable;
import com.datayes.websample.redis.type.RedisOpsType;
import com.datayes.websample.redis.util.JSONUtil;
import com.datayes.websample.redis.util.MethodParserUtil;

@Component
@Aspect
public class RedisListCacheAspect {

	@Autowired(required = false)
	RedisTemplate<String, String> redisTemplate;

	private static final Logger LOG = LoggerFactory.getLogger(RedisListCacheAspect.class);

	private static final String REDIS_LIST_CACHEABLE = "@annotation(com.datayes.websample.redis.annotation.RedisListCacheable)";
	private static final String REDIS_STRING_VALUE = "";

	@SuppressWarnings("unchecked")
	@Around(REDIS_LIST_CACHEABLE)
	public Object redisListCacheable(ProceedingJoinPoint pjp) {
		Object ret = null;
		try {
			MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
			Method method = methodSignature.getMethod();
			RedisListCacheable redisListCacheable = method.getAnnotation(RedisListCacheable.class);
			RedisOpsType opsType = redisListCacheable.opsType();
			String key = redisListCacheable.key();
			String valueType = redisListCacheable.valueType();
			long expiration = redisListCacheable.expiration();
			boolean isStringType = REDIS_STRING_VALUE.equals(valueType);
			String keyParam = MethodParserUtil.parseKeyParam(method, pjp.getArgs());
			key += keyParam;
			
			LOG.info("redis list aspect invoked, method:{}, opsType:{}, key:{}, valueType:{}, expiration:{}", 
					method.getName(), opsType, key, valueType, expiration);
			
			if (RedisOpsType.GET == opsType || RedisOpsType.GET_AND_PUT == opsType) {
				boolean existed = redisTemplate.hasKey(key);
				if (existed) {
					Long size = redisTemplate.opsForList().size(key);
					List<String> list = redisTemplate.opsForList().range(key, 0, size);
					if (!isStringType) {
						Class<?> clazz = Class.forName(valueType);
						List<Object> values = new ArrayList<Object>();
						for (String json : list) {
							values.add(JSONUtil.parseJSON(json, clazz));
						}
						return values;
					} else {
						return list;
					}
				} else {
					ret = pjp.proceed();
				}
			} else if (RedisOpsType.UPDATE == opsType) {
				redisTemplate.delete(key);
				ret = pjp.proceed();
			}
			
			if (RedisOpsType.GET_AND_PUT == opsType || RedisOpsType.UPDATE == opsType) {
				List<String> list = null;
				if (!isStringType) {
					list = new ArrayList<String>();
					for (Object object : (List<Object>)ret) {
						list.add(JSONUtil.toJSONString(object));
					}
				} else {
					list = (List<String>) ret;
				}
				String[] values = new String[list.size()];
				redisTemplate.opsForList().rightPushAll(key, list.toArray(values));
				
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
