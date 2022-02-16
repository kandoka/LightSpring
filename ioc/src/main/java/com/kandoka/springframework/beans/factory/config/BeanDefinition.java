package com.kandoka.springframework.beans.factory.config;

import com.kandoka.springframework.beans.PropertyValues;

/**
 * @Description Bean定义
 * @Author handong3
 * @Date 2022/2/15 16:12
 */
public class BeanDefinition {

    /**
     * bean的类型
     */
    private Class beanClass;

    /**
     * bean的属性
     */
    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues){
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
