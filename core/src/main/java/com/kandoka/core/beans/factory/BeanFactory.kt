package com.kandoka.core.beans.factory

/**
 *
 *  @Description IoC容器接口，主要定义了getBean方法规范
 *  @Author handong3
 *  @Date 2022/11/8 10:59
 *
 */
interface BeanFactory {
    fun getBean(beanName: String): Any;

    fun getBean(beanName: String, vararg args: Any): Any;

    fun <T> getBean(requiredType: Class<T>): T;

    fun <T> getBean(beanName: String, requiredType: Class<T>): T;
}