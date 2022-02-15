package com.kandoka.springframework.beans.factory;

import com.kandoka.springframework.beans.BeansException;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/15 16:09
 */
public interface BeanFactory {

    /**
     * 获取bean
     * @param name
     * @return
     * @throws BeansException
     */
    Object getBean(String name) throws BeansException;

    /**
     * 获取bean，用于实例化拥有构造参数的bean的场景
     * @param name
     * @param args bean的构造参数
     * @return
     * @throws BeansException
     */
//    Object getBean(String name, Object... args) throws BeansException;
}
