package com.datayes.websample.redis.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import com.datayes.websample.redis.annotation.RedisHashCacheable;
import com.datayes.websample.redis.type.RedisContainerType;
import com.datayes.websample.redis.type.RedisOpsType;
import com.datayes.websample.redis.util.JSONUtil;
import com.datayes.websample.redis.util.MethodParserUtil;

@Component
@Aspect
public class RedisHashCacheAspect {

	@Autowired(required = false)
	RedisTemplate<String, String> redisTemplate;

	private static final Logger LOG = LoggerFactory.getLogger(RedisHashCacheAspect.class);

	private static final String REDIS_HASH_CACHEABLE = "@annotation(com.datayes.websample.redis.annotation.RedisHashCacheable)";
	private static final String EMPTY_STRING = "";

	@SuppressWarnings("unchecked")
	@Around(REDIS_HASH_CACHEABLE)
	public Object redisHashCacheable(ProceedingJoinPoint pjp) {
		Object ret = null;
		
		try {
			MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
			Method method = methodSignature.getMethod();
			RedisHashCacheable redisHashCacheable = method.getAnnotation(RedisHashCacheable.class);
			RedisOpsType opsType = redisHashCacheable.opsType();
			String key = redisHashCacheable.key();
			String fieldKey = redisHashCacheable.fieldKey();
			String keyType = redisHashCacheable.keyType();
			RedisContainerType containerType = redisHashCacheable.containerType();
			String valueType = redisHashCacheable.valueType();
			long expiration = redisHashCacheable.expiration();
			boolean isKeyStringType = EMPTY_STRING.equals(keyType);
			boolean isValueStringType = EMPTY_STRING.equals(valueType);
			boolean isContainerType = !RedisContainerType.NONE.equals(containerType);
			String keyParam = MethodParserUtil.parseKeyParam(method, pjp.getArgs());
			if (keyParam != null) {
				key += keyParam;
			}
			
			String hashKey = null;
			if (!EMPTY_STRING.equals(fieldKey)) {
				hashKey = MethodParserUtil.parseKey(fieldKey, method, pjp.getArgs());
			}
			
			LOG.info("redis hash aspect invoked, method:{}, opsType:{}, key:{}, fieldKey:{}, hashKey:{}, keyType:{}, containerType:{}, valueType:{}, expiration:{}", 
					method.getName(), opsType, key, fieldKey, hashKey, keyType, containerType, valueType, expiration);
			
			if (RedisOpsType.GET == opsType || RedisOpsType.GET_AND_PUT == opsType) {
				Class<?> keyClazz = isKeyStringType ? String.class : Class.forName(keyType);
				Class<?> valueClazz = isValueStringType ? String.class : Class.forName(valueType);
				
				if (!EMPTY_STRING.equals(fieldKey)) {
					Object value = redisTemplate.opsForHash().get(key, hashKey);
					if (value == null) {
						ret = pjp.proceed();
						if (RedisOpsType.GET_AND_PUT == opsType) {
							redisTemplate.opsForHash().put(key, hashKey, JSONUtil.toJSONString(ret));
						}
					} else {
						if (isContainerType) {
							if (RedisContainerType.LIST == containerType) {
								List<Object> list = JSONUtil.parseJSON(value.toString(), List.class);
								List<Object> returnList = new ArrayList<Object>();
								for (Object object : list) {
									returnList.add(JSONUtil.parseJSON(object.toString(), valueClazz));
								}
								return returnList;
							} else {
								throw new UnsupportedOperationException(containerType + " is not supported now.");
							}
						} else {
							Class returnType = ((MethodSignature) pjp.getSignature()).getReturnType();
							return JSONUtil.parseJSON(value.toString(), returnType);
						}
					}
				} else {
					boolean existed = redisTemplate.hasKey(key);
					if (!existed) {
						ret = pjp.proceed();
						if (RedisOpsType.GET_AND_PUT == opsType) {
							putMapToRedis(key, ret, isKeyStringType, isValueStringType, expiration);
						}
					} else {
						Map<Object, Object> value = redisTemplate.opsForHash().entries(key);
						if (isKeyStringType && isValueStringType) {
							return value;
						} else {
							Map<Object, Object> map = new HashMap<Object, Object>();
							for (Entry<Object, Object> entry : value.entrySet()) {
								Object hKey = entry.getKey();
								Object hValue = entry.getValue();
								if (!isKeyStringType) {
									hKey = JSONUtil.parseJSON(hKey.toString(), keyClazz);
								}
								
								if (isContainerType) {
									if (RedisContainerType.LIST == containerType) {
										List<Object> list = JSONUtil.parseJSON(hValue.toString(), List.class);
										List<Object> returnList = new ArrayList<Object>();
										for (Object object : list) {
											returnList.add(JSONUtil.parseJSON(object.toString(), valueClazz));
										}
										hValue = returnList;
									} else {
										throw new UnsupportedOperationException(containerType + " is not supported for containerType now.");
									}
								} else {
									if (!isValueStringType) {
										hValue = JSONUtil.parseJSON(hValue.toString(), valueClazz);
									}
								}
								map.put(hKey, hValue);
							}
							return map;
						}
					}
				}
			} else if (RedisOpsType.UPDATE == opsType) {
				redisTemplate.delete(key);
				ret = pjp.proceed();
				putMapToRedis(key, ret, isKeyStringType, isValueStringType, expiration);
			}
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
		}
		return ret;
	}
	
	private void putMapToRedis(String key, Object object, boolean isKeyStringType, boolean isValueStringType, long expiration) {
		Map<String, String> map = new HashMap<String, String>();
		for (Entry<Object, Object> entry : ((Map<Object, Object>) object).entrySet()) {
			String hashKey = isKeyStringType? (String)entry.getKey() : JSONUtil.toJSONString(entry.getKey());
			String hashValue = isValueStringType? (String)entry.getValue() : JSONUtil.toJSONString(entry.getValue());
			map.put(hashKey, hashValue);
		}
		redisTemplate.opsForHash().putAll(key, map);
		if (expiration > 0) {
			redisTemplate.expire(key, expiration, TimeUnit.SECONDS);
		}
	}
}
