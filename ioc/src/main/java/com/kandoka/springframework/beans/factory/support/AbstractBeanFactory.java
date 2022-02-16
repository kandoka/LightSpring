package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.BeanFactory;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;

/**
 * @Description bean工厂，负责bean的定义、注册、获取
 * @Author handong3
 * @Date 2022/2/15 16:23
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * 获取Bean
     * 首先尝试从bean缓存中获取bean <br>
     * 如果缓存中找不到，说明bean未被创建，则先创建bean，<br>
     *      创建bean时会根据对应的beanDefinition创建这个bean<br>
     *          尝试从beanDefinition缓存中获取beanDefinition<br>
     *      创建好bean后，将bean放入bean缓存中，以便以后获取<br>
     * @param name
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;
}
