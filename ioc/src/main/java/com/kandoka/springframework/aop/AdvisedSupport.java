package com.kandoka.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @Description 包装切面通知信息类
 *              主要用于吧代理、拦截、匹配的各项属性包装导一个类中，方便在Proxy
 *              类中使用
 * @Author handong3
 * @Date 2022/3/31 16:36
 */
public class AdvisedSupport {
    private TargetSource targetSource;
    private MethodInterceptor methodInterceptor;
    private MethodMatcher methodMatcher;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
