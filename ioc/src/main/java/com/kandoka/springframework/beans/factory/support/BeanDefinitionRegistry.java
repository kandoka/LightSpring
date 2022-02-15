package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.factory.config.BeanDefinition;

/**
 * @Description BeanDefinition缓存
 * @Author handong3
 * @Date 2022/2/15 16:21
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册 BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
