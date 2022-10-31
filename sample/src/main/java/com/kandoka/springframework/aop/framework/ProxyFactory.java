package com.kandoka.springframework.aop.framework;

import com.kandoka.springframework.aop.AdvisedSupport;

/**
 * @Description 代理工厂，选择JDK或Cglib的方式创建代理类
 * @Author handong3
 * @Date 2022/4/1 11:05
 */
public class ProxyFactory {
    private AdvisedSupport advisedSupport;
    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }
    public Object getProxy() {
        return createAopProxy().getProxy();
    }
    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
