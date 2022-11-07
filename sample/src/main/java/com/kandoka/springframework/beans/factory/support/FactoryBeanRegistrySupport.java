package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.FactoryBean;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description FactoryBeanRegistrySupport 类主要处理的就是关于 FactoryBean 此类对象的注册
 *              操作，
 * @Author handong3
 * @Date 2022/2/24 15:54
 */
@Slf4j
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{
    /**
     * Cache of singleton objects created by FactoryBeans: FactoryBean name --> object
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        log.info("【FactoryBean】获取, 从缓存获取，Bean类名：{}", object.getClass().getName());
        return (object != NULL_OBJECT ? object : null);
    }

    /**
     * 具体的获取 FactoryBean#getObject() 方法，因
     * 为既有缓存的处理也有对象的获取，所以额外提供了 getObjectFromFactoryBean
     * 进行逻辑包装
     * @param factory
     * @param beanName
     * @return
     */
    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName)
    {
        log.info("【FactoryBean】开始获取，Bean名称：{}, FactoryBean类名：{}", beanName, factory.getClass().getName());
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object
                        : NULL_OBJECT));
            } else {
                log.info("【FactoryBean】完成获取, 从缓存获取，Bean类名：{}", object.getClass().getName());
            }
            return (object != NULL_OBJECT ? object : null);
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName){
        try {
            Object o = factory.getObject();
            log.info("【FactoryBean】完成获取, 从FactoryBean获取，Bean类名：{}", o.getClass().getName());
            return o;
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
