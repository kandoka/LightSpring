package com.kandoka.springframework.beans.factory;

import com.kandoka.springframework.beans.BeansException;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/15 16:09
 */
public interface BeanFactory {

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
    Object getBean(String name) throws BeansException;

    /**
     * 获取bean，用于实例化拥有构造参数的bean的场景
     * @param name
     * @param args bean的构造参数
     * @return
     * @throws BeansException
     */
    Object getBean(String name, Object... args) throws BeansException;
}
