package com.kandoka.springframework.beans.factory.config;

import com.kandoka.springframework.beans.factory.HierarchicalBeanFactory;
import com.kandoka.springframework.util.StringValueResolver;

/**
 * @Description 可获取 BeanPostProcessor、BeanClassLoader 等的一个
 *              配置化接口
 * @Author handong3
 * @Date 2022/2/16 16:20
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();

    /**
     * Add a String resolver for embedded values such as annotation attributes.
     * @param valueResolver the String resolver to apply to embedded values
     * @since 3.0
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * Resolve the given embedded value, e.g. an annotation attribute.
     * @param value the value to resolve
     * @return the resolved value (may be the original value as-is)
     * @since 3.0
     */
    String resolveEmbeddedValue(String value);
}
