package com.kandoka.springframework.beans.factory.config;

import com.kandoka.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @Description 可获取 BeanPostProcessor、BeanClassLoader 等的一个
 *              配置化接口
 * @Author handong3
 * @Date 2022/2/16 16:20
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

}
