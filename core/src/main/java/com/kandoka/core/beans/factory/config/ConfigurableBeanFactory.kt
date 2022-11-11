package com.kandoka.core.beans.factory.config

import com.kandoka.core.beans.factory.HierarchicalBeanFactory
import com.kandoka.core.util.StringValueResolver

/**
 *
 *  @Description Configuration interface to be implemented by most bean factories. Provides
 * facilities to configure a bean factory, in addition to the bean factory
 * client methods in the {@link org.springframework.beans.factory.BeanFactory}
 * interface.
 *
 * <p>This bean factory interface is not meant to be used in normal application
 * code: Stick to {@link org.springframework.beans.factory.BeanFactory} or
 * {@link org.springframework.beans.factory.ListableBeanFactory} for typical
 * needs. This extended interface is just meant to allow for framework-internal
 * plug'n'play and for special access to bean factory configuration methods.
 *  @Author handong3
 *  @Date 2022/11/9 18:27
 *
 */
interface ConfigurableBeanFactory: HierarchicalBeanFactory {

    companion object {
        const val SCOPE_SINGLETON = "singleton";

        const val SCOPE_PROTOTYPE = "prototype";
    }

    fun addBeanPostProcessor(beanPostProcessor: BeanPostProcessor);

    fun destroySingletons();

    fun addEmbeddedValueResolver(valueResolver: StringValueResolver);

    fun resolveEmbeddedValue(value: String): String;
}