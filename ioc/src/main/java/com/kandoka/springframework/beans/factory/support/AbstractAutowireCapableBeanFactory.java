package com.kandoka.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.PropertyValue;
import com.kandoka.springframework.beans.PropertyValues;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;
import com.kandoka.springframework.beans.factory.config.BeanReference;

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
        Object bean;
        try {
            //根据beanDefinition创建一个bean
            bean = createBeanInstance(beanDefinition, beanName, args);

            //给bean填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        //添加创建好的bean到bean缓存中
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * bean属性填充
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition){
        try {
            //循环bean的所有属性，进行属性填充操作
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for(PropertyValue propertyValue: propertyValues.getPropertyValues()){
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 如果A 依赖 B，获取 B 的实例化，可以处理递归依赖
                if(value instanceof BeanReference){
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }

                //进行属性填充，借用hutool工具类的属性填充方法，也可以自己实现
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
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

    /**
     * 设置实例化策略
     * @param instantiationStrategy
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
