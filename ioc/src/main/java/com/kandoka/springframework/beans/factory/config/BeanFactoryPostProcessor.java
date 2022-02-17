package com.kandoka.springframework.beans.factory.config;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @Description Bean工厂后置处理接口。允许在 Bean 对象注册后但未实例化之前，对 Bean 的定义信息 BeanDefinition 执 行修改操作。
 * @Author handong3
 * @Date 2022/2/17 15:23
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修
     * 改 BeanDefinition 属性的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
