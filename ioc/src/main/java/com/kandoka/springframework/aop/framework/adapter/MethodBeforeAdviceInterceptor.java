package com.kandoka.springframework.aop.framework.adapter;

import com.kandoka.springframework.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Description 方法拦截器的实现类
 * @Author handong3
 * @Date 2022/4/1 11:35
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor() {
    }

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("MethodBeforeAdviceInterceptor#invoke() 执行方法："+methodInvocation.getMethod().getName());
        this.advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(),
                methodInvocation.getThis());
        return methodInvocation.proceed();
    }

    public MethodBeforeAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodBeforeAdvice advice) {
        this.advice = advice;
    }
}
