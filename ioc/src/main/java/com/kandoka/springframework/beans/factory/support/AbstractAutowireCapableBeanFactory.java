package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/15 16:27
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    /**
     * 根据beanDefinition创建一个bean，并且添加到bean缓存中
     * @param beanName
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        //根据beanDefinition创建一个bean
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        //添加创建好的bean到bean缓存中
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 根据对象实例化策略，创建实例
     * @param beanDefinition
     * @param beanName
     * @param args
     * @return
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args){
        Constructor constructor2Use = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        //找到符合的构造方法，构造参数个数一致
        for(Constructor constructor: declaredConstructors){
            if(null != args && constructor.getParameterTypes().length == args.length){
                constructor2Use = constructor;
                break;
            }
        }
        return  getInstantiationStrategy().instantiate(beanDefinition, beanName, constructor2Use, args);
    }

    /**
     * 获取实例化策略
     * @return
     */
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }
}
