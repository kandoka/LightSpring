package com.kandoka.springframework.context.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.config.BeanPostProcessor;
import com.kandoka.springframework.context.ApplicationContext;
import com.kandoka.springframework.context.ApplicationContextAware;

/**
 * @Description 包装处理器，用于获取ApplicationContext的实例
 *              由于 ApplicationContext 的获取并不能直接在创建 Bean 时候就可以拿到，所以
 *              需要在 refresh 操作时，把 ApplicationContext 写入到一个包装的
 *              BeanPostProcessor 中去，再由AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization
 *              方法调用。
 * @Author handong3
 * @Date 2022/2/24 14:51
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 如果bean实现了ApplicationContextAware接口，
     * 会将ApplicationContext实例通过setApplicationContext方法注入进去
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("["+beanName+"] ApplicationContextAwareProcessor." +
                "postProcessBeforeInitialization() 执行");
        if(bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("["+beanName+"] ApplicationContextAwareProcessor." +
                "postProcessAfterInitialization() 执行");
        return bean;
    }
}
