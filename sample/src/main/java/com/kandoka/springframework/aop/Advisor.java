package com.kandoka.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @Description 访问者接口
 * @Author handong3
 * @Date 2022/4/1 10:52
 */
public interface Advisor {
    Advice getAdvice();
}
