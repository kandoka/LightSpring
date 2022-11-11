package com.kandoka.core.beans.factory.config

/**
 *
 *  @Description 定义了一个Bean的单例注册中心
 *  @Author handong3
 *  @Date 2022/11/11 9:36
 *
 */
interface SingletonBeanRegistry {
    fun getSingleton(beanName: String): Any;
    fun registerSingleton(beanName: String, singletonObject: Any);
}