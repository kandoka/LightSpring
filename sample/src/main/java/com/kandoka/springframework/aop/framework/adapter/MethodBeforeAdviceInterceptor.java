package com.kandoka.springframework.aop.framework.adapter;

import com.kandoka.springframework.aop.MethodBeforeAdvice;
import com.kandoka.springframework.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Description 方法拦截器的实现类
 * @Author handong3
 * @Date 2022/4/1 11:35
 */
@Slf4j
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor() {
    }

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        log.info("MethodBeforeAdviceInterceptor#invoke() 执行方法：{}", methodInvocation.getMethod().getName());
        //执行前置方法，用于在被拦截的方法执行前，做些别的处理
        this.advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(),
                methodInvocation.getThis());
        //执行被拦截的方法
        return methodInvocation.proceed();
    }

    public MethodBeforeAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodBeforeAdvice advice) {
        this.advice = advice;
    }
}
