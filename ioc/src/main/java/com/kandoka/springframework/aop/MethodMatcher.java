package com.kandoka.springframework.aop;

import java.lang.reflect.Method;

/**
 * @Description 方法匹配接口
 * @Author handong3
 * @Date 2022/3/26 17:56
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
