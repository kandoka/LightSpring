package com.kandoka.springframework.aop;

/**
 * @Description 切点表达式接口
 * @Author handong3
 * @Date 2022/3/26 17:55
 */
public interface Pointcut {
    ClassFilter getClassFilter();
    MethodMatcher getMethodMatcher();
}
