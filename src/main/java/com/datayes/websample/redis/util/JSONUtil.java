package com.datayes.websample.redis.util;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	public static String toJSONString(Object object) {
		return JSONObject.toJSONString(object);
	}
	
	public static <T> T parseJSON(String json, Class<T> clazz) {
		return JSONObject.parseObject(json, clazz);
	}
}
