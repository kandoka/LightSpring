package com.kandoka.springframework.beans.factory.config;

/**
 * @Description 单例缓存 接口
 * @Author handong3
 * @Date 2022/2/15 16:20
 */
public interface SingletonBeanRegistry {

    /**
     * 获取单例Bean
     * @param beanName bean名称
     * @return
     */
    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);
}
