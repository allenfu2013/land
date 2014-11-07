package com.datayes.websample.redis.type;

/**
 * Redis operation type
 * @author yong.fu
 *
 */
public enum RedisOpsType {
	GET, //only get value from redis
	GET_AND_PUT, //get value from redis, and put into redis if not in 
	UPDATE //remove it from redis, then put it 
}
