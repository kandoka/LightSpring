package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description bean工厂，负责bean的定义、注册、获取，也可进行BeanDefinition缓存的注册、获取
 * @Author handong3
 * @Date 2022/2/15 16:27
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    /**
     * beanDefinition缓存
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 注册beanDefinition到beanDefinition缓存中
     * @param beanName
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * 从beanDefinition缓存中获取beanDefinition
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }
}
