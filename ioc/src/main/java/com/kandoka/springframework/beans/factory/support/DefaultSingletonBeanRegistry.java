package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 负责bean单例缓存的注册、获取
 * @Author handong3
 * @Date 2022/2/15 16:24
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * bean缓存，单例模式
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 从bean缓存获取bean
     * @param beanName bean名称
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 添加bean到bean缓存中
     * @param beanName
     * @param singletonObject
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
