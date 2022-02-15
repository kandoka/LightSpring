package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Description bean的实例化策略接口
 * @Author handong3
 * @Date 2022/2/15 18:28
 */
public interface InstantiationStrategy {
    /**
     * 实例化bean
     * @param beanDefinition
     * @param beanName
     * @param constructor 符合入参信息对应的构造函数
     * @param args 构造参数，构造函数的入参信息
     * @return
     * @throws BeansException
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args) throws BeansException;
}
