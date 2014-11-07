package com.datayes.websample.util;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	public static String toJSONString(Object object) {
		return JSONObject.toJSONString(object);
	}
	
	public static <T> T parseObject(String text, Class<T> clazz) {
		return JSONObject.parseObject(text, clazz);
	}
	
	public static void main(String[] args) {
		String text = JSONUtil.toJSONString(new Long(2));
		Long a = JSONUtil.parseObject(text, Long.class);
		System.out.println(a);
	}
}
