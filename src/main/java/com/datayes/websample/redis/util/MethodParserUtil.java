package com.datayes.websample.redis.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.datayes.websample.redis.annotation.KeyParam;

public class MethodParserUtil {
	public static String parseKey(String key, Method method, Object[] args) {
		// 获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = u.getParameterNames(method);
		// 使用SPEL进行key的解析
		ExpressionParser parser = new SpelExpressionParser();
		// SPEL上下文
		StandardEvaluationContext context = new StandardEvaluationContext();
		// 把方法参数放入SPEL上下文中
		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}

		return parser.parseExpression(key).getValue(context, String.class);
	}
	
	public static String parseKeyParam(Method method, Object[] args) {
		String keys = "";
		Annotation[][] annotations = method.getParameterAnnotations();
		for(int i = 0; i < annotations.length; i++) {
			for (int j = 0; j < annotations[i].length; j++) {
				Annotation annotation = annotations[i][j];
				if (annotation instanceof KeyParam) {
					keys += ((KeyParam) annotation).connector() + args[i].toString();
				}
			}
		}
		return "".equals(keys) ? null : keys;
	}
}
