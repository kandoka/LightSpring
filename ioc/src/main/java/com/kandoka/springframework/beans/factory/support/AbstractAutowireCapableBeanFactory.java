package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/15 16:27
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 根据beanDefinition创建一个bean，并且添加到bean缓存中
     * @param beanName
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        //根据beanDefinition创建一个bean
        Object bean;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        //添加创建好的bean到bean缓存中
        addSingleton(beanName, bean);
        return bean;
    }
}
