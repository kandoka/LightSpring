package com.kandoka.springframework.beans.factory;

import com.kandoka.springframework.beans.BeansException;

/**
 * @Description BeanFactory的感知接口类，实现此类的类的实例可以感知到所属的BeanFactory
 * @Author handong3
 * @Date 2022/2/24 14:42
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
