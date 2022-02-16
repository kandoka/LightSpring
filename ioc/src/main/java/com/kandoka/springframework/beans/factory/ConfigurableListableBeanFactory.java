package com.kandoka.springframework.beans.factory;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;
import com.kandoka.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @Description 提供分析和修改 Bean 以及预先实例化的操作接口
 * @Author handong3
 * @Date 2022/2/16 16:38
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
