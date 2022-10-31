package com.kandoka.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.PropertyValues;
import com.kandoka.springframework.beans.factory.BeanFactory;
import com.kandoka.springframework.beans.factory.BeanFactoryAware;
import com.kandoka.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.kandoka.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.kandoka.springframework.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @Description 扫描自定义注解类, 是实现接口InstantiationAwareBeanPostProcessor用于在 Bean 对象实例化完成后，设
 *              置属性操作前的处理属性信息的类和操作方法.只有实现了 BeanPostProcessor
 *              接口才有机会在 Bean 的生命周期中处理初始化信息
 * @Author handong3
 * @Date 2022/4/1 16:50
 */
@Slf4j
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    /**
     * 核心方法，用于处理类中含有@Value、@Autowired注解的属性，进行属性信息的提取和设置
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        //1. 处理注解@Value
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declaredFields = clazz.getDeclaredFields();

        for(Field field: declaredFields){
            Value valueAnnotation = field.getAnnotation(Value.class);
            if(null != valueAnnotation) {
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        //2. 处理注解@Autowired
        for(Field field: declaredFields){
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if(null != autowiredAnnotation){
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if(null != qualifierAnnotation){
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                } else {
                  dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }

        return pvs;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
