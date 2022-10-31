package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.core.io.DefaultResourceLoader;
import com.kandoka.springframework.core.io.ResourceLoader;

/**
 * @Description BeanDefinition读取抽象类，从配置读取beanDefinition
 * @Author handong3
 * @Date 2022/2/16 16:20
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }
    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
