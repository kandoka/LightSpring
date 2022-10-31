package com.kandoka.springframework.aop;

/**
 * @Description 访问者的一个切面的实现类
 *              Advisor 承担了 Pointcut 和 Advice 的组合，Pointcut 用于获取 JoinPoint，而
 *              Advice 决定于 JoinPoint 执行什么操作。
 * @Author handong3
 * @Date 2022/4/1 10:54
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
