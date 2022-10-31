package com.kandoka.springframework.aop;

import java.lang.reflect.Method;

/**
 * @Description 方法前置拦截方法接口
 * @Author handong3
 * @Date 2022/4/1 10:48
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    /**
     * Callback before a given method is invoked
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
