package com.kandoka.springframework.beans.factory.config;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.BeanFactory;

/**
 * @Description 是一个自动化处理 Bean 工厂配置的接口
 * @Author handong3
 * @Date 2022/2/16 16:20
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
