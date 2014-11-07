package com.datayes.websample.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import com.datayes.websample.redis.type.RedisContainerType;
import com.datayes.websample.redis.type.RedisOpsType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
@Inherited
public @interface RedisHashCacheable{

	/**
	 * operation type
	 * 
	 * @return
	 */
	RedisOpsType opsType();

	/**
	 * key for hash
	 * 
	 * @return
	 */
	String key();

	/**
	 * key for field
	 * 
	 * @return
	 */
	String fieldKey() default "";
	
	/**
	 * class type for hash key
	 * @return
	 */
	String keyType() default "";
	
	/**
	 * class type for container type of hash value
	 * only support {@link <a>RedisContainerType.LIST</a>} now
	 * @return
	 */
	RedisContainerType containerType() default RedisContainerType.NONE;
	
	/**
	 * class type for hash value
	 * @return
	 */
	String valueType() default "";

	/**
	 * time to expire, seconds for unit
	 * 
	 * @return
	 */
	long expiration() default -1;

}
