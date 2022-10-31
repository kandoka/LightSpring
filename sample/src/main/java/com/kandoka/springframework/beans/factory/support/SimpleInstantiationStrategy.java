package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Description JDK实例化策略
 * @Author handong3
 * @Date 2022/2/16 14:31
 */
@Slf4j
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args) throws BeansException {
        log.info("["+beanName+"] SimpleInstantiationStrategy.instantiate() 执行");
        Class clazz = beanDefinition.getBeanClass();
        try{
            //如果constructor不为空，就是需要有构造函数的实例化
            if(null != constructor) {
                return clazz.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(args);
            }
            //如果constructor为空则是无构造函数实例化
            else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() +
                    "]", e);
        }
    }
}
