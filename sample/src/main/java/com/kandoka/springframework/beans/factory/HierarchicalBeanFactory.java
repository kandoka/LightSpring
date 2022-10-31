package com.kandoka.springframework.beans.factory;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;
import com.kandoka.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @Description 它提供了可以获取父类 BeanFactory
 *              方法，属于是一种扩展工厂的层次子接口。
 * @Author handong3
 * @Date 2022/2/16 16:38
 */
public interface HierarchicalBeanFactory extends BeanFactory {

}
