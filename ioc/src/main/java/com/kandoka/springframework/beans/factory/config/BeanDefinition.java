package com.kandoka.springframework.beans.factory.config;

/**
 * @Description Bean定义
 * @Author handong3
 * @Date 2022/2/15 16:12
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }


    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
