package com.kandoka.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/3/31 16:36
 */
public class AdvisedSupport {
    private TargetSource targetSource;
    private MethodInterceptor methodInterceptor;
    private MethodMatcher methodMatcher;
}
