package com.kandoka.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @Description 包装切面通知信息类
 *              主要用于吧代理、拦截、匹配的各项属性包装导一个类中，方便在Proxy
 *              类中使用
 *              类似于网络请求中将接口的多个参数封装到Json中
 * @Author handong3
 * @Date 2022/3/31 16:36
 */
public class AdvisedSupport {
    private boolean proxyTargetClass = false;
    /**
     * 被代理的类的上层封装
     */
    private TargetSource targetSource;
    /**
     * 被代理的类中方法的拦截器
     */
    private MethodInterceptor methodInterceptor;
    /**
     * 方法匹配器
     */
    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }
    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

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
